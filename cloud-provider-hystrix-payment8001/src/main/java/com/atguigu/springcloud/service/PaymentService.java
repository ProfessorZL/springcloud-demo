package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String okInfo(Long id){
        return "线程：" + Thread.currentThread().getName() + "\t" + "ok_info,id：" + id;
    }


    //fallbackMethod指定要回调的方法
    @HystrixCommand(fallbackMethod = "timeoutHandle",commandProperties = {
            //指定当前线程的超时时间是5秒，如果超过就报错
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String tomeoutInfo(Long id){
        //测试超时
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //测试异常
//        int num = 10/0;
        return "线程：" + Thread.currentThread().getName() + "\t" + "timeout_info,id：" + id;
    }

    public String timeoutHandle(Long id){
        return "线程：" + Thread.currentThread().getName() + "\t 超时了，系统繁忙";
    }


    /**
     * 服务熔断测试
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //下面的几个注解中的配置意思是：在10s的时间窗口期，请求次数达到10次，失败率达到百分之60,就进入熔断方法
        @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启熔断
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Long id){

        if(id < 0){
            throw new RuntimeException("....id不能为负数");
        }
        String serialNum = IdUtil.randomUUID();
        return Thread.currentThread().getName() + "\t 调用成功,流水号是：" + serialNum;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Long id){
        return "id不能为负数，请稍后再试o(╥﹏╥)o，id：" + id;
    }
}
