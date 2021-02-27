package com.atguigu.springcloud.utils;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

    @Bean
//    @LoadBalanced   //提供负载均衡,如果自己实现的负载均衡算法就不需要次注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
