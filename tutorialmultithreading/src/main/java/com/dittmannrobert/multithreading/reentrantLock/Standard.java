package com.dittmannrobert.multithreading.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Standard {
    private static final Lock lock = new ReentrantLock();
    private static int counter = 0;

    static void increment() {
        lock.lock();
        try {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final Thread t1 = new Thread(Standard::increment);
        final Thread t2 = new Thread(Standard::increment);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }
}
