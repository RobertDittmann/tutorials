package com.dittmannrobert.multithreading.waitNotify;

public class App {
    public static void main(String[] args) {
        final Processor processor = new Processor();
        final Thread t1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t2 = new Thread(() -> {
            try {
                processor.consume();
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

class Processor {
    void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("method produce()");
            wait();
            System.out.println("again method produce");
        }
    }

    void consume() throws InterruptedException {
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("method consume()");
            notify();
            Thread.sleep(5000);
        }
    }
}
