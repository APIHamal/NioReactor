package com.lizhengpeng.nio.reactor.multi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiChannelHandler implements Runnable {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(16);
    private volatile Runnable actualTask;

    public MultiChannelHandler(Runnable runnable){
        this.actualTask = runnable;
    }

    @Override
    public void run() {
        executorService.execute(actualTask);
    }
}
