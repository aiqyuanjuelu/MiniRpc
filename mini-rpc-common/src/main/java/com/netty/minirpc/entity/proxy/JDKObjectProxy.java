package com.netty.minirpc.entity.proxy;

import com.netty.minirpc.entity.bean.MethodInfo;
import com.netty.minirpc.inter.component.ObjectProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 默认的代理对象
 *
 * @param <T> - 需要代理的接口
 */
public class JDKObjectProxy<T> implements ObjectProxy<T> {

    @Override
    @SuppressWarnings("all")
    public T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                (proxy, method, args) -> {
                    wrapConnectionInfoInvoker(clazz, method, args);  //开始发送网络请求
                    return null;//method.invoke(proxy, args);
                });
    }

    public void wrapConnectionInfoInvoker(Class<T> clazz, Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setClassName(clazz.getName());
        methodInfo.setMethodName(method.getName());
        //methodInfo.setInterfaceName(clazz.getAnnotatedInterfaces());
        System.out.println(methodInfo);
    }


}
