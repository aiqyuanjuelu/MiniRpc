package com.netty.minirpc.encoder;

import com.netty.minirpc.constant.Constant;
import com.netty.minirpc.entity.base.Message;
import com.netty.minirpc.entity.bean.TransferMessage;
import com.netty.minirpc.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Optional;


public class TransferMessageEncoder extends MessageToByteEncoder<TransferMessage<Message>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TransferMessage msg, ByteBuf out) throws Exception {
        //写入魔数
        out.writeLong(Constant.MAGIC_NUMBER_HEADER); //8
        out.writeLong(Constant.MAGIC_NUMBER_BODY);   //8
        //写入版本号
        out.writeShort(Optional.of(msg.getMainVersion()).filter(t -> t != 0).orElse(Constant.MAIN_VERSION));    //2
        out.writeShort(Optional.of(msg.getSubVersion()).filter(t -> t != 0).orElse(Constant.SUB_VERSION));      //2
        out.writeShort(Optional.of(msg.getModifyVersion()).filter(t -> t != 0).orElse(Constant.MODIFY_VERSION));//2
        //写入消息类型
        out.writeChar(msg.getMessageType().getType());  //2
        //写入载体类型
        out.writeChar(msg.getObjectType().getType());   //2

        byte[] msgBytes = SerializationUtil.serialize(msg.getMessage());
        out.writeInt(msgBytes.length);//写入长度         //4
        out.writeBytes(msgBytes);
    }
}
