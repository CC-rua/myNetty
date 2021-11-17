package myClent;

/**
 * @description: 启动类
 * @author: ricci
 * @date: 2021-10-23 17:22:30
 */
public class ClientMain {
    public static void main(String[] args) {
        MyNettyClient myNettyClient = new MyNettyClient("127.0.0.1", 1133);
        myNettyClient.start();
    }
}
