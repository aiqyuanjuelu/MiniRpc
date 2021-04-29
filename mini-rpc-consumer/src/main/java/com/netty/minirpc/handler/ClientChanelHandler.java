package com.netty.minirpc.handler;

import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.bean.MessageType;
import com.netty.minirpc.entity.bean.MethodInfo;
import com.netty.minirpc.entity.bean.Request;
import com.netty.minirpc.entity.bean.Response;
import com.netty.minirpc.entity.bean.TransferMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientChanelHandler extends ChannelInboundHandlerAdapter implements ChannelHandler {

    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(((Response)((TransferMessage)msg).getMessage()).getResponseId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    //外部调用的入口
    public void sendRequest(MethodInfo methodInfo){
        TransferMessage<Request> transferMessage = new TransferMessage<>();
        transferMessage.setMessageType(MessageType.CONTENT_MESSAGE);
        Request request = new Request();
        request.setMessage(methodInfo);
        request.setRequestId(1);
        transferMessage.setMessage(request);
        channel.writeAndFlush(transferMessage);
    }

}
