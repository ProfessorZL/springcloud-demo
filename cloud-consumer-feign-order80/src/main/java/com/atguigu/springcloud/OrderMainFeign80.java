package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //开启Springboot对openfeign的支持
@EnableHystrix  //开启Springboot对Hystrix的支持
public class OrderMainFeign80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign80.class,args);
    }

}
