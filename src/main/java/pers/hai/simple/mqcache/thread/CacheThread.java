package pers.hai.simple.mqcache.thread;

import java.util.List;

import pers.hai.simple.mqcache.queue.CacheQueue;
import pers.hai.simple.mqcache.queue.HistoryQueue;

/**
 * Create Time: 2016/01/07
 * Last Modify: 2019/05/20
 *
 * @author Q-WHai
 * @see <a href="https://github.com/qwhai">https://github.com/qwhai</a>
 */
public class CacheThread {

    private Thread thread = null;
    private List<CacheQueue> cacheQueueList = null;
    private HistoryQueue historyQueue = null;
    
    public CacheThread(List<CacheQueue> _cacheQueueList, HistoryQueue _historyQueue) {
        cacheQueueList = _cacheQueueList;
        historyQueue = _historyQueue;
        newThread();
    }

    public void start() {
        if (thread == null) {
            throw new NullPointerException();
        }
        
        thread.start();
    }
    
    private void newThread() {
        if (thread == null) {
            thread = new Thread(new CheckRunnable(cacheQueueList, historyQueue));
        }
    }
}
