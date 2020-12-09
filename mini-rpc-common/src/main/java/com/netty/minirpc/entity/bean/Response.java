package com.netty.minirpc.entity.bean;

import com.netty.minirpc.entity.base.Message;

public class Response implements Message {

    //响应消息id
    private long responseId;

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }
}
