package com.dittmannrobert.multithreading.catchException.executorService;

import java.util.concurrent.*;

public class App {
    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            System.out.println("Started thread");
            throw new RuntimeException("DUMMY EXCEPTION");
        });
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<?> future = executorService.submit(thread); // submit because it returns Future<?>
        executorService.shutdown();
        try {
            future.get(); // important part to catch exception
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("thread with ID: " + e.getClass() + " and name: " + e.getClass().getName() + " throws " + e.getCause() + " with message: " + e.getMessage());
        }
        System.out.println("Finished thread");
    }
}
