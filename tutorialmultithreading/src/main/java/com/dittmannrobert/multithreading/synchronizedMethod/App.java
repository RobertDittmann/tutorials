package com.dittmannrobert.multithreading.synchronizedMethod;

public class App {

    private static int counter = 0;

    static synchronized void increment() {
        counter++;
    }

    static void process() {
        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });

        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        process();
        System.out.println(counter);
    }
}
