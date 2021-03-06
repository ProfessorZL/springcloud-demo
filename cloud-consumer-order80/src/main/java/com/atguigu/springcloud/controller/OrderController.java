package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.atguigu.springcloud.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private static final String BASE_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 自定义的负载均衡算法
     */
    @Autowired
    private LoadBalancer loadBalancer;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(BASE_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(BASE_URL + "/payment/get/" + id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/discovery")
    public Object discovery(){
        return restTemplate.getForObject(BASE_URL + "/payment/discovery",CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity
                = restTemplate.getForEntity(BASE_URL + "/payment/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){//请求成功状态码
            return entity.getBody();
        }
        return new CommonResult<>(404,"请求失败");
    }

    @GetMapping("/consumer/payment/postForEntity")
    public CommonResult<Payment> postForEntity(){
        Payment payment = new Payment(8l,"008");
        ResponseEntity<CommonResult> entity
                = restTemplate.postForEntity(BASE_URL + "/payment/create",payment, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){//请求成功状态码
            return entity.getBody();
        }
        return new CommonResult<>(404,"创建失败");
    }

    @GetMapping("/consumer/payment/myLb")
    public String myLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance nowInstance = loadBalancer.instance(instances);
        URI uri = nowInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/getPort",String.class);
    }

}
