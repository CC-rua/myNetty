package myServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ricci
 * @date: 2021-10-23 16:51:22
 */
public class MyNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                // 心跳时间
                .addLast("idle", new IdleStateHandler(0, 0, 20, TimeUnit.SECONDS))
                // 添加编码、解码器
                .addLast(new HttpServerCodec())
                // 支持参数对象解析， 比如POST参数， 设置聚合内容的最大长度
                .addLast(new HttpObjectAggregator(65536))
                // 支持大数据流写入
                .addLast(new ChunkedWriteHandler())
                // 支持WebSocket数据压缩
                .addLast(new WebSocketServerCompressionHandler())
                .addLast(new WebSocketServerProtocolHandler("/websocket"))
                .addLast(new NettyServerHandler_WebSocket());
//                .addLast(new NettyServerHandler_IN(), new NettyServerHandler_OUT());
    }
}
