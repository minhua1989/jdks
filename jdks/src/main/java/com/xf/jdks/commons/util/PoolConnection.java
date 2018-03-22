package com.xf.jdks.commons.util;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by root on 16-11-4.
 */
public class PoolConnection implements Connection {

    public PoolConnection(Connection con,int currentIndex){
        this.con = con;
        this.currentIndex = currentIndex;
    }

    private Connection con = null;

    private int currentIndex = -1;

    private boolean closeFlg = false;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public boolean isCloseFlg() {
        return closeFlg;
    }

    public void setCloseFlg(boolean closeFlg) {
        this.closeFlg = closeFlg;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return this.con.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.con.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return this.con.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return this.con.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.con.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return this.con.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        this.con.commit();
    }

    @Override
    public void rollback() throws SQLException {
        this.con.rollback();
    }

    @Override
    public void close() throws SQLException {
        this.con.close();
        this.closeFlg = true;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isCloseFlg();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.con.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        this.con.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return this.con.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        this.con.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return this.con.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        this.con.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return this.con.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return this.con.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.con.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.con.createStatement(resultSetType,resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.con.prepareStatement(sql,resultSetType,resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.con.prepareCall(sql,resultSetType,resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.con.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        this.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        this.con.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.con.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return this.con.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return this.con.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        this.con.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        this.con.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.con.createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.con.prepareStatement(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this.con.prepareCall(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return this.con.prepareStatement(sql,autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return this.con.prepareStatement(sql,columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return this.con.prepareStatement(sql,columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return this.con.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return this.con.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return this.con.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return this.con.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return this.con.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this.con.setClientInfo(name,value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.con.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return this.con.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return this.con.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return this.con.createArrayOf(typeName,elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this.con.createStruct(typeName,attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        this.con.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return this.con.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        this.con.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        this.con.setNetworkTimeout(executor,milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return this.con.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.con.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.con.isWrapperFor(iface);
    }
}
