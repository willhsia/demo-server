package com.example.demo.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    static  List list = new LinkedList<String>();
    static  Integer count = 0;
    static Integer input = 0;

    static private ReentrantLock lock = new ReentrantLock();
    static Condition A = lock.newCondition();
    static Condition B = lock.newCondition();
    static Condition C = lock.newCondition();
    static Condition D = lock.newCondition();

    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();
        ThreadA1 a = new ThreadA1();
        ThreadB1 b = new ThreadB1();
        ThreadC1 c = new ThreadC1();
        ThreadD1 d = new ThreadD1();
        a.start();
        b.start();
        c.start();
        d.start();

    }

    static class ThreadA1 extends Thread{
        @Override
        public void run() {
            try{
                lock.lock();
                for (int i = 0; i < input; i++) {
                    while (!(count%4==0))
                        A.await();

                    list.add("A");
                    System.out.println("A");
                    count++;
                    B.signal();
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB1 extends Thread{
        @Override
        public void run() {
            try{
                lock.lock();
                for (int i = 0; i < input; i++) {
                    while(!(count%4==1))
                        B.await();

                    list.add("B");
                    System.out.println("B");
                    count++;
                    C.signal();
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    static class ThreadC1 extends Thread{
        @Override
        public void run() {
            try{
                lock.lock();
                for (int i = 0; i < input; i++) {
                    while(!(count%4==2))
                        C.await();
                    list.add("C");
                    System.out.println("C");
                    count++;
                    D.signal();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    static class ThreadD1 extends Thread{
        @Override
        public void run() {
            try{
                lock.lock();
                for (int i = 0; i < input; i++) {
                    while(!(count%4==3))
                        D.await();

                    list.add("D");
                    System.out.println("D");
                    count++;
                    A.signal();
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}