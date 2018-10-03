package com.dittmannrobert.multithreading.synchronizedBlock;

public class App {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private static int counter1 = 0;
    private static int counter2 = 0;

    static void incrementCounter1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    static void incrementCounter2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    static void compute() {
        for (int i = 0; i < 100; i++) {
            incrementCounter1();
            incrementCounter2();
        }
    }

    static void process() {
        final Thread t1 = new Thread(App::compute);
        final Thread t2 = new Thread(App::compute);
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
        System.out.println("counter1: " + counter1 + " - counter2: " + counter2);
    }
}
