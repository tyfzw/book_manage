package com.tyfzw.booksmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.tyfzw.booksmanage.infrastructure.gateway.database.dao"})
@EnableScheduling
public class BooksManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksManageApplication.class, args);
    }

}
