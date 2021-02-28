package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Component
public class PaymentFullbackService implements PaymentService{

    @Override
    public String okInfo(Long id) {
        return "okInfo的异常回调";
    }

    @Override
    public String timeout() {
        return "超时的异常回调";
    }

    @Override
    public String timeout(Long id) {
        return "超时的异常回调" + id;
    }
}
