package com.netty.minirpc.handler;

import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.bean.MessageType;
import com.netty.minirpc.entity.bean.Request;
import com.netty.minirpc.entity.bean.Response;
import com.netty.minirpc.entity.bean.TransferMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerChanelHandler extends SimpleChannelInboundHandler<TransferMessage> implements ChannelHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferMessage msg) throws Exception {
        TransferMessage<Response> transferMessage = new TransferMessage<>();
        transferMessage.setMessageType(MessageType.CONTENT_MESSAGE);
        Response response = new Response();
        long requestId = ((Request) msg.getMessage()).getRequestId();
        System.out.println("server say : requestId" + requestId);
       /* response.setResponseId(requestId);
        transferMessage.setMessage(response);
        ctx.writeAndFlush(transferMessage);*/
        System.out.println(msg.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        //ctx.close();
    }

}
