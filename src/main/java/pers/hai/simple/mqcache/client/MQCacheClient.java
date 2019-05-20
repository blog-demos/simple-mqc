package pers.hai.simple.mqcache.client;

import pers.hai.simple.mqcache.impl.MultiQueueCache;
import pers.hai.simple.mqcache.pack.CachePack;

/**
 * 客户端调用
 *
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 * 
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class MQCacheClient {

    public static void main(String[] args) {
        MQCacheClient client = new MQCacheClient();
        client.test();
    }
    
    private void test() {
        try {
            CachePack cachePack = new CachePack(new MultiQueueCache());
            cachePack.offer("A");
            Thread.sleep(10);
            cachePack.offer("B");
            Thread.sleep(10);
            cachePack.offer("C");
            Thread.sleep(10);
            cachePack.offer("D");
            Thread.sleep(10);
            cachePack.offer("E");
            Thread.sleep(10);

            cachePack.visitting("B");
            cachePack.visitting("A");
            cachePack.visitting("C");
            cachePack.visitting("E");
            cachePack.visitting("B");
            cachePack.visitting("A");
            cachePack.visitting("B");
            cachePack.visitting("A");
            cachePack.visitting("C");

            cachePack.offer("F");
            Thread.sleep(10);

            cachePack.offer("G");
            Thread.sleep(10);

            cachePack.offer("H");
            Thread.sleep(10);

            cachePack.offer("I");
            Thread.sleep(10);

            cachePack.visitting("G");

            cachePack.offer("J");
            Thread.sleep(10);

            cachePack.visitting("A");

            cachePack.show();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
