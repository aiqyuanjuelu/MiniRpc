package com.netty.minirpc.handler;

import com.netty.minirpc.entity.bean.MessageType;
import com.netty.minirpc.entity.bean.Request;
import com.netty.minirpc.entity.bean.Response;
import com.netty.minirpc.entity.bean.TransferMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientChanelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 2000; i++) {
            TransferMessage<Request> transferMessage = new TransferMessage<>();
            transferMessage.setMessageType(MessageType.CONTENT_MESSAGE);
            Request request = new Request();
            request.setMessage("AAAA");
            request.setRequestId(i);
            transferMessage.setMessage(request);
            ctx.writeAndFlush(transferMessage);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(((Response)((TransferMessage)msg).getMessage()).getResponseId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
