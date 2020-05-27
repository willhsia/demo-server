package com.example.demo.thread;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < n; i++) {
            executor.execute(new Task("A"));
            executor.execute(new Task("B"));
            executor.execute(new Task("C"));
            executor.execute(new Task("D"));
        }
        executor.shutdown();
    }

    static class Task implements Runnable{
        private String output;
        public Task(String output){
            this.output = output;
        }
        @Override
        public void run() {
            System.out.print(this.output);
        }
    }
}
