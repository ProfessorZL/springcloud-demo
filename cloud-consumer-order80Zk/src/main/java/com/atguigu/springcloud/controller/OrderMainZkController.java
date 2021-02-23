package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderMainZkController {
    private static final String BASE_URL = "http://cloud-provider-paymentZk8003";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String paymentZk(){
        return restTemplate.getForObject(BASE_URL + "/payment/zk",String.class);
    }

}
