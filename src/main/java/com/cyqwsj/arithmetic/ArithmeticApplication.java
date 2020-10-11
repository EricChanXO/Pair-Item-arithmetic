package com.cyqwsj.arithmetic;

import com.cyqwsj.arithmetic.controller.MainController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ArithmeticApplication implements CommandLineRunner {

    @Resource
    private MainController mainController;

    public static void main(String[] args) {
        SpringApplication.run(ArithmeticApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainController.mainConsole();
    }
}
