package myServer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @description: netty 服务端的出站业务处理对象
 * @author: ricci
 * @date: 2021-10-23 17:00:18
 */
public class NettyServerHandler_OUT extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("outbound read...");
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("outbound write...");
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("outbound flush...");
    }
}
