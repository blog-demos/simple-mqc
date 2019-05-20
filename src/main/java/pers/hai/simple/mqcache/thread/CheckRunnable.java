package pers.hai.simple.mqcache.thread;

import java.util.List;

import pers.hai.simple.mqcache.bean.CacheBean;
import pers.hai.simple.mqcache.queue.CacheQueue;
import pers.hai.simple.mqcache.queue.HistoryQueue;

/**
 * 检查各个级别的缓存队列是否有可降低优先级的资源
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 *
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class CheckRunnable implements Runnable {

    private List<CacheQueue> cacheQueueList = null;
    private HistoryQueue historyQueue = null;
    
    public CheckRunnable(List<CacheQueue> _cacheQueueList, HistoryQueue _historyQueue) {
        cacheQueueList = _cacheQueueList;
        historyQueue = _historyQueue;
    }
    
    @Override
    public void run() {
        try {
            do {
                Thread.sleep(100);
                check();
            } while (true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void check() {
        if (cacheQueueList == null) {
            return;
        }
        
        int size = cacheQueueList.size();
        for (int i = 1; i < size; i++) {
            CacheQueue currentQueue = cacheQueueList.get(i);
            CacheBean lastBean = currentQueue.poll();
            if (lastBean == null) {
                continue;
            }
            CacheQueue lastQueue = cacheQueueList.get(i - 1);
            CacheBean pollBean = lastQueue.offer(lastBean);
            
            if (pollBean != null) {
                historyQueue.offer(pollBean);
            }
        }
    }
}
