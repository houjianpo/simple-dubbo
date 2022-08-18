package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.common.Invocation;
import org.example.map.LocalRegister;

import java.lang.reflect.Method;

public class RequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;
        // 根据className获取寄存的服务对象
        Object serviceImpl = LocalRegister.get(invocation.getClassName());
        // 通过methodName等信息获取对应的方法
        Method method = serviceImpl.getClass().getMethod(invocation.getMethodName(), invocation.getParamTypes());
        // 调用方法
        Object result = method.invoke(serviceImpl, invocation.getArgs());
        // 返回服务结果
        ctx.writeAndFlush(result);
    }

}
