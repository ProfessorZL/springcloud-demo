package com.atguigu.myribbonrule;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRule {

    @Bean
    public RandomRule changeRule(){
        return new RandomRule();
    }


}
