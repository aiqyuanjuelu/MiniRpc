package com.netty.minirpc.entity.bean;

public enum ObjectType {

    REQUEST('1'), RESPONSE('2');

    char type;

    ObjectType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

}
