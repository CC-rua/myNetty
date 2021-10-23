package myServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @description: netty 服务端的入站业务处理对象
 * @author: ricci
 * @date: 2021-10-23 16:53:42
 */
public class NettyServerHandler_IN extends ChannelInboundHandlerAdapter {
    /**
     * 当通道数据可读时执行
     *
     * @param ctx 上下文对象，可以从中取得 Pipeline、Channel、客户端地址等
     * @param msg 客户端发来的数据
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketAddress clientAddress = ctx.channel().remoteAddress();
        System.out.println("client address: " + clientAddress);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("client data : " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 当信道读取完毕后执行
     *
     * @param ctx 上下文对象，可以从中取得 Pipeline、Channel、客户端地址等
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("server is ok ...", StandardCharsets.UTF_8);
        //响应客户端
        ctx.writeAndFlush(byteBuf.duplicate());
    }

    /**
     * 发生异常时执行
     *
     * @param ctx   上下文对象，可以从中取得 Pipeline、Channel、客户端地址等
     * @param cause 异常对象
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
