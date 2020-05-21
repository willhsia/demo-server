package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.security.mapper")
public class DemoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServerApplication.class, args);
	}

}
