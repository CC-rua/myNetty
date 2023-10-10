package myClent;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.StandardCharsets;

/**
 * @description: 客户端消息处理器
 * @author: ricci
 * @date: 2021-10-23 16:32:23
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private String _m_sEcho = "Hi!\n";//响应字符
    //netty 提供的 byteBuffer 有零拷贝的功能，更加泛用 Unpooled 是 netty 提供的操作缓冲区的工具类
//    final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(_m_sEcho, StandardCharsets.UTF_8));

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        buf.writeInt(2);
        ctx.writeAndFlush(buf.duplicate());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client accept :" + msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("事件触发- channel id {" + ctx.channel().id() + "} evt:{" + evt+ "}");
    }
}
