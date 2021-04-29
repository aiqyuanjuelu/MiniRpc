package com.netty.minirpc.connection;

import com.netty.minirpc.client.MiniClient;
import com.netty.minirpc.entity.Client;
import com.netty.minirpc.entity.RegisterConfig;
import com.netty.minirpc.entity.base.Entity;
import com.netty.minirpc.handler.ClientChanelHandler;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


public class ConnectionManager implements Entity {

    private static ConnectionManager connectionManager;

    private final CopyOnWriteArrayList<Connection> connections;

    //拿到发送请求的链接
    private ConnectionManager() {
        connections = new CopyOnWriteArrayList<>();
        //this.startConnections();
    }

    public static synchronized ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    /**
     * 负载均衡选择入口
     *
     * @return 可用的连接器
     */
    public ClientChanelHandler chooseServerHandler() {
        Connection connection = connections.get(0);
        connection.waitForHandler();
        return connection.getChannelHandler();
    }

    private void startConnections() {
        connections.forEach(this::start);
    }

    private void start(Connection connection) {
        Optional.ofNullable(connection).ifPresent(Connection::start);
    }

    public Connection createConnection(RegisterConfig registerConfig) {
        Connection connection = new DefaultConnection();
        Client client = new MiniClient();
        client.setRegisterConfig(registerConfig);
        connection.setClient(client);
        connections.add(connection);
        this.start(connection);  //启动该逻辑
        return connection;
    }
}
