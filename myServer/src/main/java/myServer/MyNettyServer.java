package myServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: netty 服务端
 * @author: ricci
 * @date: 2021-10-23 16:36:40
 */
public class MyNettyServer {

    private int _m_iPort;

    public MyNettyServer(int _port) {
        _m_iPort = _port;
    }

    public void start() {

        ServerBootstrap bs = new ServerBootstrap();
        //创建 BossGroup 只处理 ACCEPT 连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建 workGroup 处理业务
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            bs.group(bossGroup, workGroup)
                    //设定所需通道实现类
                    .channel(NioServerSocketChannel.class)
                    //设置等待连接的队列容量(当连接请求无法被 channel 消费时用这个队列做缓冲)
                    .option(ChannelOption.SO_BACKLOG, 128)//option 给 ServerSocketChannel 添加配置，抽象类提供的方法
                    //设置连接保持活性
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//option 给 ServerSocketChannel 添加配置，子类提供的方法
                    //handler() 给 BossGroup 设置业务处理器
                    //childHandler() 给 WorkGroup 设置业务处理器
                    .childHandler(new MyNettyServerChannelInitializer())

            ;
            System.out.println("netty is ready...");

            //绑定端口，启动服务器，生成一个 channelFuture 对象，channelFuture 是 Netty 异步模型的一部分

            ChannelFuture channelFuture = bs.bind(_m_iPort).sync();
            //对信道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭两个线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
