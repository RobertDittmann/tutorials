package com.dittmannrobert.multithreading.join;

public class App {
    public static void main(String[] args) {
        final Thread firstRunner = new FirstRunner();
        final Thread secondRunner = new SecondRunner();

        firstRunner.start();
        secondRunner.start();

        try {
            firstRunner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished tasks of firstRunner");
    }
}

class FirstRunner extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
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


