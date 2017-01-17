package transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import packet.Packet;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 编解码 Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class PacketCodec extends ByteToMessageCodec<Packet> {
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        oos.flush();
        byte[] bytes = bos.toByteArray();
        out.writeBytes(bytes);
        oos.close();
        bos.close();
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBufInputStream bis = new ByteBufInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Packet msg = (Packet) ois.readObject();
        ois.close();
        bis.close();
        out.add(msg);
    }
}