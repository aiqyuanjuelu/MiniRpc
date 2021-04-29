package com.netty.minirpc.connection;

import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.base.Component;
import com.netty.minirpc.inter.component.Connection;

public class DefaultConnection implements Connection {

    private Component component;

    @Override
    public void start() {
        //开启线程
        new Thread(() -> component.start()).start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void init() {
        component.init();
    }

    public void setComponent(Component client) {
        this.component = client;
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return component.getChannelHandler();
    }

    @Override
    public void waitForHandler() {
        while (component.getStatus() != Component.STATUS_RUNNING) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
        }
    }
}
