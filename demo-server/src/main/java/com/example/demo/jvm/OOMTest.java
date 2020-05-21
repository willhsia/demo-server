package com.example.demo.jvm;

import java.util.ArrayList;

public class OOMTest {



    public static void main(String[] args) {
        constaintPoolOOM();
    }

    public static void constaintPoolOOM(){
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String str3 = new String("str");
        System.out.println(str3.intern() == str3);
    }

    public static void heapOOM(){
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
            System.out.println(list.size());
        }
    }

    static class OOMObject{}
}
