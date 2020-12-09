package com.netty.minirpc.client;

import com.netty.minirpc.decode.TransferMessageDecoder;
import com.netty.minirpc.encoder.TransferMessageEncoder;
import com.netty.minirpc.entity.Client;
import com.netty.minirpc.handler.ClientChanelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MiniClient implements Client {

    public static void main(String[] args) {
        MiniClient miniClient = new MiniClient();
        miniClient.run("127.0.0.1", 8088);
    }

    private EventLoopGroup boss = null;

    public void run(String ip, int port) {
        boss = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TransferMessageDecoder())
                                    .addLast(new TransferMessageEncoder())
                                    .addLast("client-channel-handler", new ClientChanelHandler());
                        }
                    });
            ChannelFuture f = bootstrap.connect(ip, port).sync();
            if (f.channel().isActive()) {
                System.out.println("连接成功-----------");
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            boss.shutdownGracefully();
        }
    }
}
