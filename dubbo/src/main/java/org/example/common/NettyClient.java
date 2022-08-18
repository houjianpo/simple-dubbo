package org.example.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Setter;
import org.example.handler.ResponseHandler;

/**
 * consumer只能拿到到HelloService接口，那么实例化的方法可以采用jdk动态代理生成代理实现，而代理的实际执行方式是通过netty网络发送请求给provider
 * <p>
 * 首先还是在dubbo框架中封装一个netty的客户端供consumer发起请求
 */
@Setter
public class NettyClient {

    /**
     * 管道上下文
     */
    private volatile ChannelHandlerContext channelHandlerContext;

    /**
     * 返回消息暂存
     */
    private Object message;

    public void start(String hostName, Integer port) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            channel.pipeline().addLast("encoder", new ObjectEncoder());
                            channel.pipeline().addLast(new ResponseHandler(NettyClient.this));
                        }
                    });
            bootstrap.connect(hostName, port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送远程调用
     */
    public synchronized String send(String hostName, Integer port, Invocation invocation) {
        start(hostName, port);

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 发送数据
        channelHandlerContext.writeAndFlush(invocation);

        // 等待
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 返回数据
        return message.toString();
    }
}
