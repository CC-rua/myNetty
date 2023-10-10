package myClent;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;

/**
 * @description: netty 客户端 信道初始化对象
 * @author: ricci
 * @date: 2021-10-23 16:52:13
 */
public class MyNettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向 pipeline 添加业务处理器
        WebSocketClientHandshaker handsShaker = WebSocketClientHandshakerFactory.newHandshaker(
                new URI("ws://127.0.0.1:6790/websocket"), WebSocketVersion.V13, null, false,
                new DefaultHttpHeaders());
        ch.pipeline()
                .addLast(new HttpClientCodec())
                .addLast(WebSocketClientCompressionHandler.INSTANCE)
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(65536))
                .addLast(new WebSocketClientProtocolHandler(handsShaker))
                .addLast(new NettyClientHandler_WebSocket());

    }
}
