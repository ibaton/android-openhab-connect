package se.treehou.ng.ohcommunicator.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private ThreadPool() {}

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Get thread pool used for submitting tasks.
     *
     * @return get thread pool used for submitting tasks..
     */
    public static ExecutorService instance(){
        return executorService;
    }
}
