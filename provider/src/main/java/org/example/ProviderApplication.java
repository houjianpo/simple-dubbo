package org.example;

import org.example.api.HelloService;
import org.example.map.LocalRegister;
import org.example.server.NettyServer;
import org.example.service.impl.HelloServiceImpl;

/**
 * provider启动类
 */
public class ProviderApplication {
    public static void main(String[] args) {
        // 存储服务于名字映射关系
        HelloServiceImpl helloService = new HelloServiceImpl();
        String className = HelloService.class.getName();
        LocalRegister.register(className, helloService);

        // 开启netty服务
        NettyServer nettyServer = new NettyServer();
        System.out.println("provider 端口号9001");
        nettyServer.start(9001);
    }
}
