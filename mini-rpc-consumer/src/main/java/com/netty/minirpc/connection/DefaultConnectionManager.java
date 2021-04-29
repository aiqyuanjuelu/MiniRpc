package com.netty.minirpc.connection;

import com.netty.minirpc.client.MiniClient;
import com.netty.minirpc.entity.Client;

import com.netty.minirpc.entity.component.RegisterConfig;
import com.netty.minirpc.inter.component.Connection;


/**
 * 默认工厂连接器 - netty
 */
public class DefaultConnectionManager extends ConnectionManager {

    public Connection createConnection(RegisterConfig registerConfig) {
        Connection connection = new DefaultConnection();
        Client client = new MiniClient();
        client.setRegisterConfig(registerConfig);
        connection.setComponent(client);
        connections.add(connection);
        super.start(connection);  //启动该逻辑
        return connection;
    }
}
