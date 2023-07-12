package com.markerhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.markerhub.mapper")
public class AdminMangerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdminMangerApplication.class, args);
    }

}
