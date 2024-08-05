package com.example.meetpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.example.meetpro.mappers"})
@SpringBootApplication
public class MeetProApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetProApplication.class, args);
    }

}
