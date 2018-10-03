package com.dittmannrobert.multithreading.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FirstWorker implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;

    FirstWorker(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            try {
                blockingQueue.put(counter);
                System.out.println("Putting items to the queue ... " + counter);
                counter++;
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondWorker implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;

    SecondWorker(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Integer number = blockingQueue.take();
                System.out.println("Taking items from the queue ... " + number);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        new Thread(new FirstWorker(queue)).start();
        new Thread(new SecondWorker(queue)).start();
    }
}
