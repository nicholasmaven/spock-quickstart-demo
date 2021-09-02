package com.github.nicholasmaven.spock.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mawen
 */
@SpringBootApplication
@MapperScan("com.shopee.bke.spock.demo.repo")
public class SpockDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpockDemoApplication.class, args);
    }
}
