package com.example.demo.jvm;

public class StaticTest{
    public static void main(String[] args) {
//        SubClass subClass = new SubClass();
//        String str = SubClass.constvalue;
//        System.out.println(str);
        SuperClass superClass = new SuperClass();
    }
}

class SuperClass{
    static {
        System.out.println("super static code");
    }
    static String valuesupper = "value super";
    static final String constvalue = "static const value";
    final String con = "const value";
}

class SubClass extends SuperClass{
    static {
        System.out.println("sub static code");
    }
    static String valuesub = "value sub";
}
