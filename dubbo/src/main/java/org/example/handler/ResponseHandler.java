package org.example.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.example.common.NettyClient;

public class ResponseHandler extends ChannelInboundHandlerAdapter {

    private final NettyClient client;

    public ResponseHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        synchronized (client) {
            client.notify();
        }
        client.setChannelHandlerContext(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        client.setMessage(msg);
        synchronized (client) {
            client.notify();
        }
    }
}
