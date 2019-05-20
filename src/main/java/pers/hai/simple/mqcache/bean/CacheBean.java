package pers.hai.simple.mqcache.bean;

/**
 * 对缓存资源进行二次封装
 *
 * Create Time: 2016/01/08
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class CacheBean {

    private Object object;
    
    private int times;
    
    private long lastVisitTime;
    
    public CacheBean(Object object) {
        this.object = object;
        initEvent();
    }
    
    private void initEvent() {
        times = 0;
        lastVisitTime = System.currentTimeMillis();
    }
    
    public Object getObject() {
        return object;
    }
    
    public void setTimes(int times) {
        this.times = times;
    }
    
    public int getTimes() {
        return times;
    }
    
    public void setLastVisitTime(long lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }
    
    public long getLastVisitTime() {
        return lastVisitTime;
    }
    
    @Override
    public String toString() {
        return "(" + object + ", " + times + ")";
    }
}
