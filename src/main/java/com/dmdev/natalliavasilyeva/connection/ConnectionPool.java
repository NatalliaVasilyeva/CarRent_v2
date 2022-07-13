package com.dmdev.natalliavasilyeva.connection;

import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.connection.utils.DatabasePropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String DATABASE_POOL_SIZE = "db.poolsize";
    private final Integer POOL_SIZE = Integer.parseInt(DatabasePropertyUtils.get(DATABASE_POOL_SIZE));
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> releaseConnections;
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            lock.lock();
            if (instance != null) {
                throw new UnsupportedOperationException();
            } else {
                init();
            }
        } finally {
            lock.unlock();
        }
    }

    private void init() {
        freeConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        releaseConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        ProxyConnection connection;

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                connection = createConnection();
                freeConnections.offer(connection);
            } catch (MissingResourceException e) {
                throw new RuntimeException("Exception during database initialization ", e);
            } catch (SQLException e) {
                try {
                    throw new ConnectionPoolException(String.format("Connection has not been created: %s", e.getMessage()), e);
                } catch (ConnectionPoolException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            releaseConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(String.format("Problem with connection take from pool %s", e.getMessage()), e);
        }
        return connection;
    }

    public void closeConnection(ProxyConnection proxyConnection) {
        releaseConnections.remove(proxyConnection);
        if (freeConnections.offer(proxyConnection)) {
            logger.info("Connection successfully returned");
        }
    }

    public void closeConnectionsQueue(List<ProxyConnection> queue) throws SQLException {
        for (ProxyConnection proxyConnection : queue) {
            if (!proxyConnection.getAutoCommit()) {
                proxyConnection.commit();
            }
            proxyConnection.realClose();
        }
    }

    public void destroyConnectionPool() throws SQLException {
        for (ProxyConnection proxyConnection : freeConnections) {
            proxyConnection.realClose();
        }
        for (ProxyConnection proxyConnection : releaseConnections) {
            proxyConnection.realClose();
        }
    }

    private ProxyConnection createConnection() throws SQLException {
        ConnectionCreator connectionCreator = ConnectionCreator.getInstance();
        ProxyConnection connection = new ProxyConnection(connectionCreator.openConnection());
        logger.info("Connection has been created.");
        return connection;
    }
}