package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "org.example.dao")
@EnableScheduling
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
