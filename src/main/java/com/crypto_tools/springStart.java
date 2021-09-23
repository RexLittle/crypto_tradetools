package com.crypto_tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@ComponentScan
@SpringBootApplication
public class springStart {

    public static void main(String[] args) {
        SpringApplication.run(springStart.class,args);
    }
}
