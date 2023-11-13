package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "org.example.dao")
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
