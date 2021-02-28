package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String getPort(@PathVariable("id") Long id){
        String result = paymentService.okInfo(id);
        log.info("正常的调用。。。。" + result);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String timeout(@PathVariable("id") Long id){
        String result = paymentService.tomeoutInfo(id);
        log.info("超时的调用。。。" + result);
        return result;
    }

}
