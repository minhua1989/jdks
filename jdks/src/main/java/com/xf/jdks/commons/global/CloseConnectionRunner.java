package com.xf.jdks.commons.global;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by root on 16-9-21.
 */
public class CloseConnectionRunner implements Runnable {
    private Connection connection;
    private int timeout;

    public CloseConnectionRunner(Connection connection, int timeout) {
        this.connection = connection;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
            if(connection!=null&&!connection.isClosed()) {
                connection.close();
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
    }
}
