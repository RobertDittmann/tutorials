package com.dittmannrobert.multithreading.catchException.thread;

public class App {
    public static void main(String[] args) {
        // create UncaughtExceptionHandler
        final Thread.UncaughtExceptionHandler handler = (t, e) -> System.out
                .println("Uncaught exception: " + e + " in thread " + t);

        final Thread thread = new Thread(() -> {
            System.out.println("doing smth in thread");
            throw new RuntimeException("throws DUMMY RuntimeException");
        });
        // set UncaughtExceptionHandler
        thread.setUncaughtExceptionHandler(handler);
        System.out.println("Before THREAD start");
        thread.start();
        try {
            thread.join();
            System.out.println("After THREAD end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
