package com.minirpc;


import com.netty.minirpc.connection.ConnectionManager;
import com.netty.minirpc.connection.DefaultConnectionManager;
import com.netty.minirpc.entity.bean.MethodInfo;
import com.netty.minirpc.entity.component.RegisterConfig;
import com.netty.minirpc.entity.proxy.JDKObjectProxy;
import com.netty.minirpc.inter.component.ObjectProxy;
import com.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Test;

public class TestConnection {

    @Test
    public void testConnectionManager(){
        ConnectionManager instance = new DefaultConnectionManager() ;
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setClassName("com.tydic");
        methodInfo.setMethodName("Hello");
        methodInfo.setInterfaceName("com.tydic.AA.HELLO");
        RegisterConfig registerConfig = new RegisterConfig();
        registerConfig.setIp("127.0.0.1");
        registerConfig.setPort(8088);
        instance.createConnection(registerConfig);
        instance.chooseServerHandler().sendRequest(methodInfo);
    }

    @Test
    public void testObjectProxy() {
        ObjectProxy<UserServiceImpl> objectProxy = new JDKObjectProxy<>();
        UserService proxy = objectProxy.getProxy(UserServiceImpl.class);
        proxy.getUserInfo();

    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(5000);
    }

}
