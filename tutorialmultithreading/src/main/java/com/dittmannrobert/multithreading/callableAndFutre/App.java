package com.dittmannrobert.multithreading.callableAndFutre;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Processor implements Callable<String> {
    private int id;

    Processor(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "ID: " + id;
    }
}

public class App {
    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final List<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = executorService.submit(new Processor(i + 1));
            list.add(future);
        }
        executorService.shutdown();

        for (final Future<String> future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
