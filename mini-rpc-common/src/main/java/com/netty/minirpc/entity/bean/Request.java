package com.netty.minirpc.entity.bean;

import com.netty.minirpc.entity.base.Message;
import com.netty.minirpc.utils.SerializationUtil;

import java.util.HashMap;
import java.util.Map;

public class Request implements Message {

    //消息id
    private long requestId;

    //请求头附加信息
    private Map<String, Object> attachments = new HashMap<>();

    private Object message;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public int getLength() {
        return SerializationUtil.serialize(this).length;
    }
}
