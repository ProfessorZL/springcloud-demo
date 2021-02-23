package com.atguigu.springcloud.util;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

    @Bean
    @LoadBalanced   //不加此注解调用不到zk中的服务提供者
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
