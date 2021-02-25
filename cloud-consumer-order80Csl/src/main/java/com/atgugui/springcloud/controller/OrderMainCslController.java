package com.atgugui.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderMainCslController {

    private static final String BASE_URL = "http://cloud-provider-paymentCsl8004";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String paymentZk(){
        return restTemplate.getForObject(BASE_URL + "/payment/consul",String.class);
    }


}
