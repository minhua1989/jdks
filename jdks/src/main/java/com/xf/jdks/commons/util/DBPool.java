package com.xf.jdks.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by root on 16-9-21.
 */
public class DBPool {
    private DBPool() {
    }

    private static final int MASTER_MOD = 0;


    private static String msurl;
    private static String msusr;
    private static String mspwd;
    private static String msmin;
    private static String msmax;
    private static String mstimeout;
 

    private static void loadConfigs() throws IOException {
    //    loadJdbcSource();//加载配置文件参数
        Properties p = new Properties();
        InputStream is = DBPool.class.getClassLoader().getResourceAsStream("pool.properties");
        p.load(is);
        loadMaster(p);

    }

 

    private static void loadMaster(Properties p) {
        msURL = p.getProperty(msurl);
        msUSR = p.getProperty(msusr);
        msPWD = p.getProperty(mspwd);
        msMin = Integer.valueOf(p.getProperty(msmin));
        msMax = Integer.valueOf(p.getProperty(msmax));
        msTimeOut = Integer.valueOf(p.getProperty(mstimeout));
    }

   
    private static void initConnections() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        initMaster();
 
    }


    private static void initMaster() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        msConnList = new ArrayList<>();
        msActiveStatus = new ArrayList<>();
        for (int i = 0; i < msMax; i++) {
            ThreadLocal<Connection> thread = new ThreadLocal<>();
            msConnList.add(thread);
            msActiveStatus.add(false);
        }
        for (int i = 0; i < msMin; i++) {
            reloadConnection(i, MASTER_MOD);
        }
    }

 
    public static void reloadConnection(int index, int type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection conn;
        switch (type) {
            default:
                break;
            case MASTER_MOD:
                Class.forName("oracle.jdbc.OracleDriver").newInstance();
                conn = DriverManager.getConnection(msURL, msUSR, msPWD);
                msConnList.get(index).set(conn);
                break;
          
        }
    }

    public static void printCurrentDBStatus() {
//        getActiveConnectionCountForMaster();
//        getMaxConnectionCountForMaster();
//        getActiveConnectionCountForSB();
//        getMaxConnectionCountForSB();
//        getActiveConnectionCountForXF();
//        getMaxConnectionCountForXF();
        System.out.println("JDBC数据连接池初始化成功：主库信息（活动连接/最大连接）：" + getActiveConnectionCountForMaster() + "/" + getMaxConnectionCountForMaster() + "   上报库信息（活动连接/最大连接）：" +
                getActiveConnectionCountForSB() + "/" + getMaxConnectionCountForSB() + " 下发库信息（活动连接/最大连接）：" +
                getActiveConnectionCountForXF() + "/" + getMaxConnectionCountForXF());
    }

    static {
        try {
            loadConfigs();
            initConnections();
            printCurrentDBStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getActiveConnectionCountForMaster() {
        int rs = 0;
        if(msConnList!=null) {
            for (ThreadLocal<Connection> curr : msConnList) {
                Connection conn = curr.get();
                try {
                    if (conn != null && !conn.isClosed()) rs++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    private static int getActiveConnectionCountForXF() {
        int rs = 0;
        if(xfConnList!=null) {
            for (ThreadLocal<Connection> curr : xfConnList) {
                Connection conn = curr.get();
                try {
                    if (conn != null && !conn.isClosed()) rs++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    private static int getActiveConnectionCountForSB() {
        int rs = 0;
        if(sbConnList!=null) {
            for (ThreadLocal<Connection> curr : sbConnList) {
                Connection conn = curr.get();
                try {
                    if (conn != null && !conn.isClosed()) rs++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    private static int getMaxConnectionCountForMaster() {
        int count=0;
        if(msConnList!=null){
            count=msConnList.size();
        }
        return count;
    }

    private static int getMaxConnectionCountForXF() {
        int count=0;
        if(xfConnList!=null){
            count=xfConnList.size();
        }
        return count;
    }

    private static int getMaxConnectionCountForSB() {
        int count=0;
        if(sbConnList!=null){
            count=sbConnList.size();
        }
        return count;
    }

    private static String msURL;
    private static String msUSR;
    private static String msPWD;
    private static Integer msMin;
    private static Integer msMax;
    private static Integer msTimeOut;
    private static String sbURL;
    private static String sbUSR;
    private static String sbPWD;
    private static Integer sbMin;
    private static Integer sbMax;
    private static Integer sbTimeOut;
    private static String xfURL;
    private static String xfUSR;
    private static String xfPWD;
    private static Integer xfMin;
    private static Integer xfMax;
    private static Integer xfTimeOut;
    private static List<ThreadLocal<Connection>> msConnList;
    private static List<ThreadLocal<Connection>> sbConnList;
    private static List<ThreadLocal<Connection>> xfConnList;

    private static List<Boolean> msActiveStatus;
    private static List<Boolean> sbActiveStatus;
    private static List<Boolean> xfActiveStatus;

    private static void flushActiveStatus() {
        if(msActiveStatus!=null) {
            for (int i = 0, len = msActiveStatus.size(); i < len; i++) {
                Connection tmp = msConnList.get(i).get();
                try {
                    if (tmp == null || tmp.isClosed()) {
                        msActiveStatus.set(i, false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msActiveStatus.set(i, false);
                }
            }
        }
        if(sbActiveStatus!=null) {
            for (int i = 0, len = sbActiveStatus.size(); i < len; i++) {
                Connection tmp = sbConnList.get(i).get();
                try {
                    if (tmp == null || tmp.isClosed()) {
                        sbActiveStatus.set(i, false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sbActiveStatus.set(i, false);
                }
            }
        }
        if(xfActiveStatus!=null) {
            for (int i = 0, len = xfActiveStatus.size(); i < len; i++) {
                Connection tmp = xfConnList.get(i).get();
                try {
                    if (tmp == null || tmp.isClosed()) {
                        xfActiveStatus.set(i, false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    xfActiveStatus.set(i, false);
                }
            }
        }
    }

    public static synchronized Connection getMasterConnection() throws InterruptedException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        flushActiveStatus();
        int currIdx = -1;
        if(msActiveStatus!=null) {
            for (int i = 0, len = msActiveStatus.size(); i < len; i++) {
                if (!msActiveStatus.get(i)) {
                    currIdx = i;
                    break;
                }
            }
        }
        if (currIdx < 0) {
            Thread.sleep(msTimeOut);
            System.out.println("waiting for getMasterConnection");
            return getMasterConnection();
        }
        Connection conn = msConnList.get(currIdx).get();
        if (conn == null || conn.isClosed()) {
            reloadConnection(currIdx, MASTER_MOD);
            conn = msConnList.get(currIdx).get();
        }
        msActiveStatus.set(currIdx, true);
        System.out.println("成功获取主库连接，当前连接索引为：" + currIdx);
        printCurrentDBStatus();
        return conn;
    }

  

}
