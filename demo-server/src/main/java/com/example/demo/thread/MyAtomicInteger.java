package com.example.demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInteger {

    public static void main(String[] args) {

    }
}

class MyThread extends Thread{
    AtomicInteger a = new AtomicInteger(0);
    @Override
    public void run() {
        super.run();
        a.incrementAndGet();
    }
}

