package org.example;

import org.example.api.HelloService;
import org.example.factory.ProxyFactory;

/**
 * consumer调用
 */
public class ConsumerApplication {
    public static void main(String[] args) {
        // 远程调用provider的sayHello方法
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        System.out.println(helloService.sayHello("world"));
    }
}
