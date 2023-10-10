package myServer;

import log.RiServerLog;
import ref.RefDataMgr;

/**
 * @description: 服务端启动类
 * @author: ricci
 * @date: 2021-10-23 17:28:16
 */
public class ServerMain {
    public static void main(String[] args) {
        //log 初始化
//        RiServerLog.initALServerLog();
//        //Ref 初始化
//        if (!RefDataMgr.getInstance().init()) {
//            System.out.println("RefDataMgr init fail");
//            return;
//        }
        MyNettyServer myNettyServer = new MyNettyServer(1133);
        myNettyServer.start();
    }
}
