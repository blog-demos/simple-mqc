package pers.hai.simple.mqcache.queue;

import pers.hai.simple.mqcache.bean.CacheBean;

/**
 * 历史缓存队列
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class HistoryQueue extends MQQueue {

    public HistoryQueue(int _maxSize) {
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
//        super.visiting(object);
    }
    
    public CacheBean revisiting(CacheBean bean) {
        CacheBean insideBean = get(bean);
        remove(insideBean);
        return insideBean;
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
