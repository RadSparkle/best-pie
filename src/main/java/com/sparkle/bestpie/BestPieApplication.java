package com.sparkle.bestpie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BestPieApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestPieApplication.class, args);
    }

}
