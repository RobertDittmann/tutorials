package com.dittmannrobert.multithreading.volatileExample;

public class App {
    public static void main(String[] args) {
        final Worker worker = new Worker();
        final Thread t1 = new Thread(worker);
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.setTerminated();
        System.out.println("Finished...");
    }
}

class Worker implements Runnable {
    private volatile boolean terminated = false;

    @Override
    public void run() {
        while (!terminated) {
            System.out.println(getClass().getSimpleName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void setTerminated() {
        this.terminated = true;
    }
}
