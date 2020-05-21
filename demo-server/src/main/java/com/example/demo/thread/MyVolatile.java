package com.example.demo.thread;

public class MyVolatile {
    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        MyThreadA threadA = new MyThreadA(myService);
        ThreadB threadB = new ThreadB(myService);
        threadA.start();
        Thread.sleep(2000);
        threadB.start();

        System.out.println("starting set stoped");
    }

}

class MyService  {
    private  boolean flag = true;

    public void runMethod(){
        while (flag){

        }
        System.out.println("has stoped");
    }

    public void stopMethod(){
        flag = false;
        System.out.println("set toped");
    }
}

class MyThreadA extends Thread{
    private MyService service;
    public MyThreadA(MyService service){
        this.service=service;
    }
    @Override
    public void run() {
        super.run();
        service.runMethod();
    }
}

class ThreadB extends Thread{
    private MyService myService;
    public ThreadB(MyService service){
        this.myService = service;
    }

    @Override
    public void run() {
        super.run();
        this.myService.stopMethod();
    }
}
