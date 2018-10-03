package com.dittmannrobert.multithreading.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WithCondition {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    static void producer() throws InterruptedException {
        lock.lock();
        System.out.println("producer method");
        condition.await();
        System.out.println("producer again");
        lock.unlock();
    }

    static void consumer() throws InterruptedException {
        lock.lock();
        Thread.sleep(2000);
        System.out.println("consumer method");
        condition.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> {
            try {
                producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t2 = new Thread(() -> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}
