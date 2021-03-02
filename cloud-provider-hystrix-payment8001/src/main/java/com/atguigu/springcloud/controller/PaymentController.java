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

    /**
     * 服务熔断测试
     * 熔断的流程描述：
     * **# 在一定的时间窗口期，经过一定的请求次数，错误率达到设定值后，会进行服务熔断。#**
     * 即达到服务熔断条件后，会进行服务降级，进而进入服务熔断状态，
     * 此时即使是正常的请求访问，等到熔断慢慢恢复后，调用链路才能恢复正常访问。
     * 服务降级 --> 服务熔断 --> 调用链路恢复
     */
    @GetMapping("/payment/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Long id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("。。。。。。" + result);
        return result;
    }


}
