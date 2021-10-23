package myServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: ricci
 * @date: 2021-10-23 16:51:22
 */
public class MyNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new NettyServerHandler_IN(), new NettyServerHandler_OUT());
    }
}
