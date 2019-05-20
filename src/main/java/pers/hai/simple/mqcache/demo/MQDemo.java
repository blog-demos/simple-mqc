package pers.hai.simple.mqcache.demo;

import pers.hai.simple.mqcache.bean.CacheBean;
import pers.hai.simple.mqcache.queue.CacheQueue;

/**
 * 测试类
 *
 * Create Time: 2016/01/08
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class MQDemo {

    public static void main(String[] args) {
        CacheBean cacheBean1 = new CacheBean("A");
        cacheBean1.setTimes(5);
        
        CacheBean cacheBean2 = new CacheBean("B");
        cacheBean2.setTimes(3);
        
        CacheBean cacheBean3 = new CacheBean("A");
        cacheBean3.setTimes(4);
        
        CacheQueue queue = new CacheQueue(5);
        queue.offer(cacheBean1);
        queue.offer(cacheBean2);
        
        System.out.println(cacheBean1.equals(cacheBean3));
    }
}
