package com.netty.minirpc.inter.component;

import com.netty.minirpc.entity.base.Entity;

/**
 * 对象代理
 */
public interface ObjectProxy<T> extends Entity {

    T getProxy(Class<T> clazz);

}
