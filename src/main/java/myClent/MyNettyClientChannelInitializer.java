package myClent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @description: netty 客户端 信道初始化对象
 * @author: ricci
 * @date: 2021-10-23 16:52:13
 */
public class MyNettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向 pipeline 添加业务处理器
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
