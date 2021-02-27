package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现自己的负载均衡轮询算法
 */
@Component
public class MyLb implements LoadBalancer {

    /**
     * CAS原子操作
     */
    private AtomicInteger automicInteger = new AtomicInteger(0);

    /**
     * 获取请求次数
     * @return
     */
    private final int getAndIncrement(){
        int current;
        int next;
        do{
            current = this.automicInteger.get();
            //请求次数不能超过Integer最大值
            next = current>2147483647 ? 0 : current + 1;
        }while(!this.automicInteger.compareAndSet(current,next));
        System.out.println("。。。第几次访问：" + next);
        return next;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }


}
