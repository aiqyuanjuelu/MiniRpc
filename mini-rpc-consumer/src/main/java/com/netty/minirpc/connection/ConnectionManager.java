package com.netty.minirpc.connection;

import com.netty.minirpc.entity.base.ChannelHandler;
import com.netty.minirpc.entity.base.Entity;
import com.netty.minirpc.entity.component.RegisterConfig;
import com.netty.minirpc.inter.component.Connection;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class ConnectionManager implements Entity {

    protected CopyOnWriteArrayList<Connection> connections;

    //拿到发送请求的链接
    public ConnectionManager() {
        connections = new CopyOnWriteArrayList<>();
    }

    /**
     * 负载均衡选择入口
     *
     * @return 可用的连接器
     */
    public ChannelHandler chooseServerHandler() {
        Connection connection = connections.get(0);
        connection.waitForHandler();
        return connection.getChannelHandler();
    }

    private void startConnections() {
        connections.forEach(this::start);
    }

    protected void start(Connection connection) {
        Optional.ofNullable(connection).ifPresent(Connection::start);
    }

    public abstract Connection createConnection(RegisterConfig registerConfig);
}
