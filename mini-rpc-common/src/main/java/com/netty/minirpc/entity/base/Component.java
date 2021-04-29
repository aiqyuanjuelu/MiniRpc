package com.netty.minirpc.entity.base;


public interface Component extends Entity {
    /**
     * 服务启动正常
     */
    int START_SUCCESS = 1100;
    /**
     * 服务启动失败
     */
    int START_FAIL = 1100;

    /**
     * 服务停止正常
     */
    int STOP_SUCCESS = 2100;
    /**
     * 服务停止失败
     */
    int STOP_FAIL = 2100;

    /**
     * 服务正在运行
     */
    int STATUS_RUNNING = 3100;
    /**
     * 服务启动失败
     */
    int STATUS_STOP = 3200;

    /**
     * 服务正在启动
     */
    int STATUS_STARING = 3300;

    //启动服务
    int start();

    //停止服务
    int stop();

    int init();

    //判断当前状态
    int getStatus();

    ChannelHandler getChannelHandler();

}
