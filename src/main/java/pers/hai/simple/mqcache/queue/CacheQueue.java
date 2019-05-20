package pers.hai.simple.mqcache.queue;

import pers.hai.simple.mqcache.bean.CacheBean;

/**
 * 各级缓存队列
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class CacheQueue extends MQQueue {

    public CacheQueue(int _maxSize) {
        super(_maxSize);
    }

    @Override
    public CacheBean offer(CacheBean object) {
        return super.offer(object);
    }
    
    @Override
    public CacheBean poll() {
        return super.poll();
    }
    
    @Override
    public void visiting(CacheBean object) {
        super.visiting(object);
    }
    
    @Override
    public boolean contains(CacheBean object) {
        return super.contains(object);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
