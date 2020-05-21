package com.example.demo.thread;

public class MyJoin {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.start();
        try {
            threadA.join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");

    }
}

class ThreadA extends Thread{
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(2000);
            System.out.println("end threadA");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
