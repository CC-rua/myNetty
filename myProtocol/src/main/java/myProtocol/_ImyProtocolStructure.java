package myProtocol;

import io.netty.buffer.ByteBuf;

/**
 * @description: 协议结构体虚基类
 * @author: ricci
 * @date: 2021-11-17 11:18:32
 */
interface _ImyProtocolStructure {

    /**
     * 协议头部信息，主协议号
     * 用于区分不同协议的字段
     *
     * @return int
     */
    int getMainOrder();

    /**
     * 协议头部信息，次协议号
     * 用于区分不同协议的字段
     *
     * @return int
     */
    int getSubOrder();

    /**
     * 打包协议数据，并加入协议头部信息
     *
     * @return ByteBuf
     */
    ByteBuf makeFullPackage();

    /**
     * 读取一个整包协议，并初始化协议数据
     *
     * @param _buf ByteBuf
     */
    void readFullPackage(ByteBuf _buf);

    /**
     * 打包协议数据，无头部信息
     *
     * @return ByteBuf
     */
    ByteBuf makePackage();

    /**
     * 读取一个协议，并初始化协议数据
     *
     * @param _buf ByteBuf
     */
    void readPackage(ByteBuf _buf);
}
