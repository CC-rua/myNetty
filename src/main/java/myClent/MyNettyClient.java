package myClent;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description: netty 客户端
 * @author: ricci
 * @date: 2021-10-18 09:38:07
 */
public class MyNettyClient {
    private String _m_sHost;//地址

    private int _m_iPort;//端口

    public MyNettyClient(String _host, int _port) {
        _m_sHost = _host;
        _m_iPort = _port;
    }

    public void start() {

        //netty 提供的启动器
        Bootstrap bs = new Bootstrap();
        //BossGroup 处理线程池 关注注册事件的线程
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bs.group(group)
                    //说明客户端通道的实现类
                    .channel(NioSocketChannel.class)
                    //通道初始化对象
                    .handler(new MyNettyClientChannelInitializer());
            System.out.println("client is ready...");
            //启动客户端去连接服务端
            ChannelFuture future = bs.connect(_m_sHost, _m_iPort).sync();
            //对信道关闭做监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
