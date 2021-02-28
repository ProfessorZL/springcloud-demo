package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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

}
