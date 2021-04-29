package com.netty.minirpc.inter.component;

import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.base.Component;
import com.netty.minirpc.entity.base.Entity;

/**
 * 链接层
 */
public interface Connection extends Entity {

    void start();

    void stop();

    void init();

    /**
     * 当前连接管理的主机信息
     *
     * @param client 主机信息
     */
    void setComponent(Component client);

    /**
     * 获取主机的链接器
     *
     * @return
     */
    ChannelHandler getChannelHandler();


    //等待的方法
    void waitForHandler();

}
