package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.utils.CommonResult;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//配置全局的服务降级，如果没有特别指定服务降级处理方法，就用此全局处理
@DefaultProperties(defaultFallback = "globalHandle")
public class OrderController {

    @Autowired
    private PaymentService paymentService;

//    @GetMapping("/consumer/payment/feign/{id}")
//    public CommonResult<Payment> getById(@PathVariable("id") Long id){
//        CommonResult<Payment> result = paymentService.getById(id);
//        return result;
//    }

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String okInfo(@PathVariable("id") Long id){
        String result = paymentService.okInfo(id);
        return result;
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String timeout(){
        return paymentService.timeout();
    }

    @GetMapping("/consumer/payment/feign/timeoutInfo/{id}")
//    @HystrixCommand(fallbackMethod = "timeoutHandle",commandProperties = {
//            //指定当前线程的超时时间是5秒，如果超过就报错
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand
    public String tomeoutInfo(@PathVariable("id") Long id){
        //测试超时
        String reuslt = paymentService.timeout(id);
        //测试异常
//        int num = 10/0;
        return reuslt;
    }

    public String timeoutHandle(Long id){
        return "消费者线程：" + Thread.currentThread().getName() + "\t 超时了，系统繁忙";
    }

    public String globalHandle(){
        return "全局的服务降级处理";
    }
}
