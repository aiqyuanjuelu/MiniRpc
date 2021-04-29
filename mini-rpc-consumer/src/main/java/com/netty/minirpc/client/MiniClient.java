package com.netty.minirpc.client;

import com.netty.minirpc.decode.TransferMessageDecoder;
import com.netty.minirpc.encoder.TransferMessageEncoder;
import com.netty.minirpc.entity.Client;
import com.netty.minirpc.entity.base.Component;
import com.netty.minirpc.entity.component.RegisterConfig;
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

    private ClientChanelHandler clientChanelHandler;

    private EventLoopGroup boss = null;
    private final Bootstrap bootstrap = new Bootstrap();
    private RegisterConfig registerConfig;  //注册中心地址
    private volatile int status = STATUS_STOP;

    @Override
    public int start() {
        init();
        this.run(registerConfig.getIp(), registerConfig.getPort());
        return 0;
    }

    @Override
    public int stop() {
        return 0;
    }

    public int init() {
        status = STATUS_STARING;
        boss = new NioEventLoopGroup();
        try {
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
        } catch (Exception e) {
            boss.shutdownGracefully();
        }
        return Component.START_SUCCESS;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void run(String ip, int port) {
        try {
            ChannelFuture f = bootstrap.connect(ip, port).sync();
            f.addListener(channel -> {
                if (channel.isSuccess()) {
                    clientChanelHandler = ((ChannelFuture) channel).channel().pipeline().get(ClientChanelHandler.class);
                    status = STATUS_RUNNING;
                }
            });
            if (f.channel().isActive()) {
                System.out.println("连接成功-----------");
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            boss.shutdownGracefully();
        }
    }

    @Override
    public ClientChanelHandler getChannelHandler() {
        return clientChanelHandler;
    }


    public void setRegisterConfig(RegisterConfig registerConfig) {
        this.registerConfig = registerConfig;
    }
}
