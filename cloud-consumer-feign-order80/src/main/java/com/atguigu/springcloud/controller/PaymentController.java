package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/feign/{id}")
    public CommonResult<Payment> getById(@PathVariable("id") Long id){
        CommonResult<Payment> result = paymentService.getById(id);
        return result;
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String timeout(){
        return paymentService.timeout();
    }

}
