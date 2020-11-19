package com.MusicFiles.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticExecutor {
    private static int cores = Runtime.getRuntime().availableProcessors();
    public static ExecutorService service = Executors.newFixedThreadPool(cores);

    public static void updateExecutorService(int numberOfThreads){
        service.shutdown();
        service = Executors.newFixedThreadPool(numberOfThreads);
    }

}
