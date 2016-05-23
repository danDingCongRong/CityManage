package cn.edu.bupt.citymanageapp.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class ThreadPool {

    private static ThreadPool instance;

    private ExecutorService executorService;

    private ThreadPool() {
        executorService = Executors.newFixedThreadPool(4);
    }

    public static ThreadPool getInstance() {
        if (null == instance) {
            instance = new ThreadPool();
        }

        return instance;
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void submit(Runnable runnable) {
        executorService.submit(runnable);
    }

}
