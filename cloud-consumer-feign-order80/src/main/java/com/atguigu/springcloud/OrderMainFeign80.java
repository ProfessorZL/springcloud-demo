package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //开启对openfeign注解的支持
public class OrderMainFeign80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign80.class,args);
    }

}
