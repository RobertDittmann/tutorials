package com.dittmannrobert.multithreading.runnable;

public class CreateThreadByImplementingRunnable {
    public static void main(String[] args) {
        final Thread thread1 = new Thread(new FirstRunner());
        final Thread thread2 = new Thread(new SecondRunner());

        thread1.start();
        thread2.start();
    }
}

class FirstRunner implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getClass().getSimpleName() + " " + i);
        }
    }
}

class SecondRunner implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getClass().getSimpleName() + " " + i);
        }
    }
}
