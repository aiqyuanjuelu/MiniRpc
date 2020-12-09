package com.netty.minirpc.server;

import com.netty.minirpc.decode.TransferMessageDecoder;
import com.netty.minirpc.encoder.TransferMessageEncoder;
import com.netty.minirpc.entity.Server;
import com.netty.minirpc.handler.ServerChanelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MiniServer implements Server {

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    public static void main(String[] args) throws InterruptedException {
        MiniServer miniServer = new MiniServer();
        miniServer.createServer("127.0.0.1", 8088);
    }

    public void createServer(String host, int port) throws InterruptedException {

        if (bossGroup == null && workerGroup == null) {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 3068)
                    .childOption(ChannelOption.TCP_NODELAY, true)//TCP无延时
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//清除死连接，维持活跃的
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new TransferMessageDecoder())
                                    .addLast(new TransferMessageEncoder())
                                    .addLast("server-channel-handler", new ServerChanelHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            future.channel().closeFuture().sync();
        }
    }
}
