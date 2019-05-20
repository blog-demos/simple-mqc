package pers.hai.simple.mqcache.pack;

import pers.hai.simple.mqcache.interf.Cacheable;

/**
 * 缓存策略包装类
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class CachePack {

    private Cacheable cacheable = null;
    
    public CachePack(Cacheable _cacheable) {
        this.cacheable = _cacheable;
    }
    
    public void offer(Object object) {
        cacheable.offer(object);
    }

    public void visitting(Object object) {
        cacheable.visitting(object);
    }

    public void show() {
        cacheable.show();
    }
}
