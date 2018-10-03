package com.dittmannrobert.multithreading.thread;

public class CreateThreadByExtendingThread {
    public static void main(String[] args) {
        final Thread firstRunner = new FirstRunner();
        final Thread secondRunner = new SecondRunner();

        firstRunner.start();
        secondRunner.start();
    }
}

class FirstRunner extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getClass().getSimpleName() + " " + i);
        }
    }
}

class SecondRunner extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getClass().getSimpleName() + " " + i);
        }
    }
}
