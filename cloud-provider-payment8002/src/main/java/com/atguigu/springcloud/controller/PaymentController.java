package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入数据结果：" + result);
        if(result > 0){
            return new CommonResult(200,"添加成功");
        }else{
            return new CommonResult(400,"添加失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult get(@PathVariable("id") Long id){
        Payment payment = paymentService.getById(id);
        log.info("查询操作结果：" + payment);
        if(payment != null){
            return new CommonResult(200,"查询成功:" + serverPort,payment);
        }else{
            return new CommonResult(400,"查询失败",null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        //注册中心注册的服务名称
        List<String> services = discoveryClient.getServices();
        System.out.println("端口：" + serverPort);
        services.forEach(a->{
            //每个服务对应的实例
            List<ServiceInstance> instances = discoveryClient.getInstances(a);
            instances.stream().forEach(b->{
                System.out.println("服务名称：" + b.getServiceId() + "\t实例名称：" + b.getInstanceId()
                        + "\t地址：" + b.getHost() + "\t端口：" + b.getPort());
            });
        });
        return new CommonResult(200,"操作成功",discoveryClient);
    }

    /**
     * 测试自定义负载均衡算法
     */
    @GetMapping("/payment/getPort")
    public String getPort(){
        return serverPort;
    }

    /**
     * 测试openfeign超时控制
     */
    @GetMapping("/payment/timeout")
    public String timeout(){
        try {
            //睡眠3秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}
