package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")   //要调用的服务实例
public interface PaymentService {

    @GetMapping("/payment/get/{id}")//要调用的服务提供者的接口
    CommonResult<Payment> getById(@PathVariable("id") Long id);

    @GetMapping("/payment/timeout")
    String timeout();

}

