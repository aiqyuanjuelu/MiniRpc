package com.netty.minirpc.entity.base;

public interface Message extends Point {
    /**
     * 消息体的长度
     *
     */
    default int getLength(){
        return 0;
    }
}
