package com.netty.minirpc.decode;

import com.netty.minirpc.constant.Constant;
import com.netty.minirpc.entity.base.Message;
import com.netty.minirpc.entity.bean.MessageType;
import com.netty.minirpc.entity.bean.ObjectType;
import com.netty.minirpc.entity.bean.Request;
import com.netty.minirpc.entity.bean.Response;
import com.netty.minirpc.entity.bean.TransferMessage;
import com.netty.minirpc.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TransferMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            if (in.readableBytes() < Constant.MIN_MESSAGE_LENGTH) {
                return;
            }
            if (in.readableBytes() > Constant.MAX_MESSAGE_LENGTH) {
                in.skipBytes(Constant.MAX_MESSAGE_LENGTH);
                //in.discardReadBytes();
                return;
            }
            in.markReaderIndex();//记录包头位置

            // 记录包头开始的index
            int beginReader;

            while (true) {
                // 获取包头开始的index
                beginReader = in.readerIndex();
                // 标记包头开始的index
                in.markReaderIndex();
                // 读到协议的开始标志，结束while循环
                if (in.readLong() == Constant.MAGIC_NUMBER_HEADER) {
                    long messageBody = in.readLong();  //读取到魔数体
                    if (messageBody == Constant.MAGIC_NUMBER_BODY) {
                        break;
                    }
                }
                // 未读到包头，跳过一个字节
                // 每次跳过一个字节后，再去读取包头信息的开始标记
                in.resetReaderIndex();
                in.readLong();
                // 当跳过一个字节后，数据包的长度又变的不满足，此时应该结束，等待后边数据流的到达
                if (in.readableBytes() < Constant.MIN_MESSAGE_LENGTH) {
                    return;
                }
            }

            //从魔数后面开始读取数据
            TransferMessage<Message> transferMessage = this.createTransferMessage(in);
            if (transferMessage == null) {
                in.readerIndex(beginReader);
                return;
            }
            out.add(transferMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private TransferMessage<Message> createTransferMessage(ByteBuf in) {
        TransferMessage<Message> transferMessage = new TransferMessage<>();
        if (in.readableBytes() < Constant.MIN_MESSAGE_LENGTH - 8 * 2) {
            return null;
        }
        short mainVersion = in.readShort();   //2
        short subVersion = in.readShort();    //2
        short modifyVersion = in.readShort(); //2
        char messageType = in.readChar();     //2
        char objectType = in.readChar();      //2
        int messageLength = in.readInt();     //4

        transferMessage.setMainVersion(mainVersion);
        transferMessage.setSubVersion(subVersion);
        transferMessage.setModifyVersion(modifyVersion);
        transferMessage.setLength(messageLength);
        switch (messageType) {
            case '1':
                transferMessage.setMessageType(MessageType.HEART_BEAT);
                break;
            case '2':
                transferMessage.setMessageType(MessageType.CONTENT_MESSAGE);
                break;
            case '0':
                transferMessage.setMessageType(MessageType.EMPTY_MESSAGE);
                break;
            default:
        }
        switch (objectType) {
            case '1':
                transferMessage.setObjectType(ObjectType.REQUEST);
                break;
            case '2':
                transferMessage.setObjectType(ObjectType.RESPONSE);
                break;
            default:
        }
        if (in.readableBytes() < messageLength) {
            return null;
        }
        byte[] dist = new byte[messageLength];
        in.readBytes(dist);
        //将数组转换成request 或者response对象
        if (objectType == ObjectType.REQUEST.getType()) {
            transferMessage.setMessage(SerializationUtil.deserialize(dist, Request.class));
        } else if (objectType == ObjectType.RESPONSE.getType()) {
            transferMessage.setMessage(SerializationUtil.deserialize(dist, Response.class));
        }
        return transferMessage;
    }
}
