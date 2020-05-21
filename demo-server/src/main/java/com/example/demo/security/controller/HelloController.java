package com.example.demo.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/hi")
    public String sayHi(){
        return "hi";
    }
}
