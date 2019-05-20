package pers.hai.simple.mqcache.queue;

import java.util.LinkedList;
import java.util.Queue;

import pers.hai.simple.mqcache.bean.CacheBean;

/**
 * 基础缓存队列类
 *
 * Create Time: 2016/01/08
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
class MQQueue {

    private int maxSize = 0;
    Queue<CacheBean> queue = new LinkedList<>();
    
    protected MQQueue(int _maxSize) {
        maxSize = _maxSize;
    }
    
    public CacheBean offer(CacheBean object) {
        if (queue.size() < maxSize) {
            queue.offer(object);
            return null;
        }
        
        CacheBean pollObject = poll();
        queue.offer(object);
        
        return pollObject;
    }
    
    public CacheBean poll() {
        return queue.poll();
    }
    
    public void visiting(CacheBean object) {
        if (queue == null) {
            throw new NullPointerException("Queue为空");
        }
        
        for (CacheBean item : queue) {
            if (item.equals(object)) {
                queue.remove(item);
                break;
            }
        }
        
        queue.offer(object);
    }
    
    public boolean contains(CacheBean object) {
        if (queue == null) {
            throw new NullPointerException("Queue为空");
        }
        
        for (CacheBean item : queue) {
            if (item.getObject().toString().equals(object.getObject().toString())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 通过一个模糊的CacheBean对象
     * 获得一个具体的CacheBean对象
     * 
     * @param bean
     *      模糊的CacheBean对象
     * @return
     *      具体的CacheBean对象
     */
    public CacheBean get(CacheBean bean) {
        for (CacheBean item : queue) {
            if (item.getObject().equals(bean.getObject())) {
                return item;
            }
        }
        
        return null;
    }
    
    /**
     * 移除某一元素
     * 
     * @param bean
     *      缓存资源
     */
    public void remove(CacheBean bean) {
        for (CacheBean item : queue) {
            if (item.equals(bean)) {
                queue.remove(bean);
                return;
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (CacheBean cacheBean : queue) {
            buffer.append(cacheBean + " ");
        }
        return buffer.toString();
    }
    
    public int getMaxSize() {
        return maxSize;
    }
}
