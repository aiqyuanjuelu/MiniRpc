package com.netty.minirpc.entity.bean;

import com.netty.minirpc.entity.base.Entity;
import com.netty.minirpc.entity.base.Message;

public class TransferMessage<T extends Message> implements Entity {

    private short mainVersion;

    private short subVersion;

    private short modifyVersion;

    //消息类型
    private MessageType messageType;

    //消息长度
    private int length;

    //消息类型
    private ObjectType objectType;

    //消息id
    private long requestId;

    private T message;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getLength() {
        this.length = message.getLength();
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public short getMainVersion() {
        return mainVersion;
    }

    public void setMainVersion(short mainVersion) {
        this.mainVersion = mainVersion;
    }

    public short getSubVersion() {
        return subVersion;
    }

    public void setSubVersion(short subVersion) {
        this.subVersion = subVersion;
    }

    public short getModifyVersion() {
        return modifyVersion;
    }

    public void setModifyVersion(short modifyVersion) {
        this.modifyVersion = modifyVersion;
    }

    public ObjectType getObjectType() {
        if (message.getClass() == Request.class) {
            objectType = ObjectType.REQUEST;
        } else if (message.getClass() == Response.class)
            objectType = ObjectType.RESPONSE;
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "TransferMessage{" +
                "mainVersion=" + mainVersion +
                ", subVersion=" + subVersion +
                ", modifyVersion=" + modifyVersion +
                ", messageType=" + messageType +
                ", length=" + length +
                ", objectType=" + objectType +
                ", requestId=" + requestId +
                ", message=" + message +
                '}';
    }
}
