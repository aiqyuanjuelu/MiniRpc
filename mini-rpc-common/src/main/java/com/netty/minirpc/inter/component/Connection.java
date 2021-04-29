package com.netty.minirpc.connection;

import com.netty.minirpc.entity.Client;
import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.base.Entity;

/**
 * 链接层
 */
public interface Connection extends Entity {

    void start();

    void stop();

    void init();

    void setClient(Client client);

    ChannelHandler getChannelHandler();

    //等待的方法
    void waitForHandler();

}
