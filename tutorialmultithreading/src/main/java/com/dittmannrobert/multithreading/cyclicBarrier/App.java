package com.dittmannrobert.multithreading.cyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        final ExecutorService service = Executors.newFixedThreadPool(5);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("All the task are finished."));

        for (int i = 0; i < 5; i++) {
            service.execute(new Barrier(i + 1, cyclicBarrier));
        }
        service.shutdown();
    }
}

class Barrier implements Runnable {
    private final int id;
    private final CyclicBarrier cyclicBarrier;
    private final Random random;

    Barrier(int id, CyclicBarrier cyclicBarrier) {
        this.id = id;
        this.cyclicBarrier = cyclicBarrier;
        this.random = new Random();
    }

    private void doWork() {
        System.out.println("Thread with ID: " + id + " starts task ...");
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread with ID: " + id + " finished task ...");
        try {
            cyclicBarrier.await();
            System.out.println("Action executed after all tasks finished - for task " + id);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doWork();
    }

    @Override
    public String toString() {
        return "CyclicBarier{" +
                "id=" + id +
                '}';
    }
}
