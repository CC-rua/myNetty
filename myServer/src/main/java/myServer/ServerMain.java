package myServer;

/**
 * @description: 服务端启动类
 * @author: ricci
 * @date: 2021-10-23 17:28:16
 */
public class ServerMain {
    public static void main(String[] args) {
        MyNettyServer myNettyServer = new MyNettyServer(1133);
        myNettyServer.start();
    }
}
