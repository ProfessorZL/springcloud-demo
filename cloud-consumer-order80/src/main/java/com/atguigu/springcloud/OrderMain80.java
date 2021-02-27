package com.atguigu.springcloud;

import com.atguigu.myribbonrule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//切换Ribbon默认负载均衡轮询算法，参数name服务提供者实例名,configuration 替换的负载均衡算法
//如果使用自己实现的负载均衡算法，就不需要@RibbonClient注解
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MyRule.class)
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }

}
