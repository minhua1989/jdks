package com.xf.jdks.commons.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rio-Lee on 2016/8/1.
 */
public class DBUtils {

    //TODO 54年级升级测试
//    private static final String MASTER_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String MASTER_USR = "xjxtsjtest";
//    private static final String MASTER_PWD = "xjxtsjtest";
//    private static final String SB_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String SB_USR = "xjxtsjtest";
//    private static final String SB_PWD = "xjxtsjtest";
//    private static final String XF_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String XF_USR = "xjxtsjtest";
//    private static final String XF_PWD = "xjxtsjtest";

    //TODO 54环境配置
//    private static final String MASTER_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String MASTER_USR = "xjxtuser";
//    private static final String MASTER_PWD = "1qaz2wsx3edc4rfv";
//    private static final String SB_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String SB_USR = "xjxtuser";
//    private static final String SB_PWD = "1qaz2wsx3edc4rfv";
//    private static final String XF_URL = "jdbc:oracle:thin:@172.31.251.54:1521:xjxtdb";
//    private static final String XF_USR = "xjxtuser";
//    private static final String XF_PWD = "1qaz2wsx3edc4rfv";

    //TODO 93环境配置
//    private static final String MASTER_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//    private static final String MASTER_USR = "xjxtuser";
//    private static final String MASTER_PWD = "xjxtuser";
//    private static final String SB_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//    private static final String SB_USR = "xjxtuser";
//    private static final String SB_PWD = "xjxtuser";
//    private static final String XF_URL = "jdbc:oracle:thin:@61.151.214.93:1521:orcl";
//    private static final String XF_USR = "xjxtuser";
//    private static final String XF_PWD = "xjxtuser";

    //TODO 155正式环境配置
//    private static final String MASTER_URL = "jdbc:oracle:thin:@172.31.10.155:1521:xjxtdb";
//    private static final String MASTER_USR = "xjxtuser";
//    private static final String MASTER_PWD = "1qaz2wsx3edc4rfv";
//    private static final String SB_URL = "jdbc:oracle:thin:@172.31.10.153:1521:predb";
//    private static final String SB_USR = "pre_sim_qx";
//    private static final String SB_PWD = "pre_sim_qx#2016";
//    private static final String XF_URL = "jdbc:oracle:thin:@172.31.10.153:1521:predb";
//    private static final String XF_USR = "pre_sim_sj";
//    private static final String XF_PWD = "pre_sim_sj#2016";

    private static String MASTER_URL;
    private static String MASTER_USR;
    private static String MASTER_PWD;
    private static String SB_URL;
    private static String SB_USR;
    private static String SB_PWD;
    private static String XF_URL;
    private static String XF_USR;
    private static String XF_PWD;

    //使用ThreadLocal保存Connection变量
    private static ThreadLocal<Connection> connectionHolder1 = new ThreadLocal<Connection>();//主库连接池
    private static ThreadLocal<Connection> connectionHolder2 = new ThreadLocal<Connection>();//区上报前置库连接池
    private static ThreadLocal<Connection> connectionHolder3 = new ThreadLocal<Connection>();//市下发前置库连接池

    private DBUtils(){}

    /**
     * 公共的执行，新增，修改操作，
     * @param pre sql语句
     * @return
     * @throws SQLException
     */
    public static int executePreparedStatement(PreparedStatement pre) throws SQLException {
        int i = 0;
        try {
            i = pre.executeUpdate();
        } finally {
            pre.close();
        }
        return i;
    }

    public static Connection GetConnectionOracleMaster() throws Exception {
        //ThreadLocal取得当前线程的connection
        Connection conM = connectionHolder1.get();
        try {
            //如果ThreadLocal没有绑定相应的Connection，创建一个新的Connection，
            //并将其保存到本地线程变量中。
            if (conM == null||conM.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            //    conM = DriverManager.getConnection(JdbcProperties.DBUTILS_MASTER_URL,JdbcProperties.DBUTILS_MASTER_USR,JdbcProperties.DBUTILS_MASTER_PWD);
                //将当前线程的Connection设置到ThreadLocal
                connectionHolder1.set(conM);
            }
            System.out.println("区县系统数据库连接成功");
        } catch(ClassNotFoundException e1){
            System.out.println("could not find database driver exception.");
        }catch(SQLException e2){
            e2.printStackTrace();
            System.out.println("could not connect to the database exception.");
        }
        return conM;
    }

    /**
     * 关闭Connection，清除集合中的Connection
     */
    public static void closeConnection(){
        //ThreadLocal取得当前线程的connection
        Connection conM = connectionHolder1.get();
        Connection conSB = connectionHolder2.get();
        Connection conXF = connectionHolder3.get();
        //当前线程的connection不为空时，关闭connection.
        if(conM != null){
            try{
                conM.close();
                //connection关闭之后，要从ThreadLocal的集合中清除Connection
                connectionHolder1.remove();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
        if(conSB != null){
            try{
                conSB.close();
                //connection关闭之后，要从ThreadLocal的集合中清除Connection
                connectionHolder2.remove();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
        if(conXF != null){
            try{
                conXF.close();
                //connection关闭之后，要从ThreadLocal的集合中清除Connection
                connectionHolder3.remove();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        File oldFile = new File("");

    }

}
