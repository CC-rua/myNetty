package myProtocol;


import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @description: 客户端请求连接
 * @author: ricci
 * @date: 2021-11-17 11:34:50
 */
@Data
public class C2S_001_001_R_ClientConnect implements _ImyProtocolStructure {

    @Override
    public int getMainOrder() {
        return 0;
    }

    @Override
    public int getSubOrder() {
        return 0;
    }

    @Override
    public ByteBuf makeFullPackage() {
        return null;
    }

    @Override
    public void readFullPackage(ByteBuf _buf) {

    }

    @Override
    public ByteBuf makePackage() {
        return null;
    }

    @Override
    public void readPackage(ByteBuf _buf) {

    }
}
