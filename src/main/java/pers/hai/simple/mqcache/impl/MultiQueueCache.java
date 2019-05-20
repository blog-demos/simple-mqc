package pers.hai.simple.mqcache.impl;

import java.util.ArrayList;
import java.util.List;

import pers.hai.simple.mqcache.bean.CacheBean;
import pers.hai.simple.mqcache.interf.Cacheable;
import pers.hai.simple.mqcache.queue.CacheQueue;
import pers.hai.simple.mqcache.queue.HistoryQueue;
import pers.hai.simple.mqcache.thread.CacheThread;

/**
 * MQ缓存算法主体部分
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class MultiQueueCache implements Cacheable {

    private static final int MQ_LIST_SIZE = 3; // 多级缓存队列的级数
    private int maxCacheQueueSize = 0;
    private int maxHistoryQueueSize = 0;
    
    private List<CacheQueue> cacheQueueList = null;
    private int timesDistance = 3; //  每个缓存队列里面保存的次数
    private HistoryQueue historyQueue = null;
    
    public MultiQueueCache() {
        this(5, 5);
    }
    
    public MultiQueueCache(int _maxCacheQueueSize, int _maxHistoryQueueSize) {
        this.maxCacheQueueSize = _maxCacheQueueSize;
        this.maxHistoryQueueSize = _maxHistoryQueueSize;
        
        initEvent();
    }
    
    @Override
    public void offer(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        
        CacheBean cacheBean = new CacheBean(object);
        cacheBean.setTimes(1);
        cacheBean.setLastVisitTime(System.currentTimeMillis());
        
        CacheQueue firstQueue = cacheQueueList.get(0);
        CacheBean pollObject = firstQueue.offer(cacheBean);
        if (pollObject == null) {
            return;
        }
        
        historyQueue.offer(pollObject);
    }

    @Override
    public void visitting(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        
        CacheBean cacheBean = new CacheBean(object);
        
        // 先在缓存队列里找
        CacheBean tmpBean = null;
        int currentLevel = 0;
        boolean needUp = false;
        for (CacheQueue cacheQueue : cacheQueueList) {
            if (cacheQueue.contains(cacheBean)) {
                tmpBean = cacheQueue.get(cacheBean);
                if (tmpBean.getTimes() < timesDistance * (currentLevel + 1)) {
                    tmpBean.setTimes(tmpBean.getTimes() + 1);
                    cacheQueue.visiting(tmpBean);
                    return;
                } else {
                    tmpBean.setTimes(tmpBean.getTimes() + 1);
                    cacheQueue.remove(tmpBean);
                    needUp = true;
                }
                break;
            }
            
            currentLevel++;
        }
        
        // 是否需要升级
        if (needUp) {
            int times = tmpBean.getTimes();
            times = times > cacheQueueList.size() * timesDistance ? cacheQueueList.size() * timesDistance : times;
            CacheQueue queue = cacheQueueList.get(times / timesDistance);
            queue.offer(tmpBean);
            return;
        }
        
        // 如果数据在history中被重新访问，则重新计算其优先级，移到目标队列的头部
        if (historyQueue.contains(cacheBean)) {
            CacheBean reVisitBean = historyQueue.revisiting(cacheBean);
            reVisitBean.setTimes(reVisitBean.getTimes() + 1);
            System.out.println(reVisitBean);
            
            int times = reVisitBean.getTimes();
            times = times > cacheQueueList.size() * timesDistance ? cacheQueueList.size() * timesDistance : times;
            CacheQueue queue = cacheQueueList.get(times / timesDistance);
            
            CacheBean cb = queue.offer(reVisitBean);
            if (cb != null) {
                historyQueue.offer(cb);
            }
            
            return;
        }
    }

    @Override
    public void show() {
        StringBuffer buffer = new StringBuffer();
        int currentLevel = 0;
        buffer.append("Cache:\n");
        for (CacheQueue cacheQueue : cacheQueueList) {
            buffer.append("Q" + currentLevel + ":" + cacheQueue + "\n");
            currentLevel++;
        }
        buffer.append("History:\n" + historyQueue);
        
        System.out.println(buffer.toString());
    }

    private void initEvent() {
        cacheQueueList = new ArrayList<>(MQ_LIST_SIZE);
        for (int i = 0; i < MQ_LIST_SIZE; i++) {
            cacheQueueList.add(new CacheQueue(maxCacheQueueSize));
        }
        
        historyQueue = new HistoryQueue(maxHistoryQueueSize);
        
        startCheckThread();
    }
    
    private void startCheckThread() {
        new CacheThread(cacheQueueList, historyQueue).start();
    }
}
