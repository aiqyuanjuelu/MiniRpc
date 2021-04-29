package com.netty.minirpc.entity.base;


import com.netty.minirpc.entity.bean.MethodInfo;

public interface ChannelHandler extends Entity {

    /**
     * 发送请求
     *
     * @param methodInfo 请求数据
     */
    default void sendRequest(MethodInfo methodInfo) {

    }

    /**
     * 相应请求
     *
     * @param methodInfo 相应数据
     */
    default void sendResponse(MethodInfo methodInfo) {

    }
}
