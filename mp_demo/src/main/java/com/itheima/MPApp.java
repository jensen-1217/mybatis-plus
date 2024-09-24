package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author by itheima
 * @Date 2021/11/12
 * @Description
 */
@SpringBootApplication
@MapperScan(basePackages ="com.itheima.mapper")
public class MPApp {
    /**
     * springboot启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MPApp.class, args);
    }

}
