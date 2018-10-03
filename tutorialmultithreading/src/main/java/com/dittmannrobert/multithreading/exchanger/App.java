package com.dittmannrobert.multithreading.exchanger;

import java.util.concurrent.Exchanger;

class FirstThread implements Runnable {
    private final Exchanger<Integer> exchanger;
    private int counter;

    FirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            counter++;
            System.out.println(getClass().getSimpleName() + " increments the counter: " + counter);
            try {
                counter = exchanger.exchange(counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondThread implements Runnable {
    private final Exchanger<Integer> exchanger;
    private int counter;

    SecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            counter--;
            System.out.println(getClass().getSimpleName() + " decrements the counter: " + counter);
            try {
                counter = exchanger.exchange(counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(new FirstThread(exchanger)).start();
        new Thread(new SecondThread(exchanger)).start();
    }
}
