package com.netty.minirpc.entity.bean;

public enum MessageType {

    HEART_BEAT('1'), CONTENT_MESSAGE('2'), EMPTY_MESSAGE('0');

    char type;

    MessageType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

}
