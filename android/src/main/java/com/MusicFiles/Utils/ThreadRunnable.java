package com.MusicFiles.Utils;

import java.util.concurrent.ExecutorService;


public class ThreadRunnable implements Runnable {

    private Runnable task;

    public ThreadRunnable(Runnable task) {
        this.task = task;
    }

    public void run() {
        this.task.run();
    }

    public void execute(ExecutorService service) {
        service.submit(this.task);
    }
}

