package org.example.factory;

import org.example.common.Invocation;
import org.example.common.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实现创建代理
 */
public class ProxyFactory {

    /**
     * 根据接口创建代理，jdk动态代理
     */
    public static <T> T getProxy(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 请求封装成对象
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);
                NettyClient nettyClient = new NettyClient();

                // 发起网络请求
                String response = nettyClient.send("127.0.0.1", 9001, invocation);
                return response;
            }
        });
    }
}
