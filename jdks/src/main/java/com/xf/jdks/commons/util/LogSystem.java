package com.xf.jdks.commons.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rio-Lee on 2016/7/28.
 * 日志系统
 */
public class LogSystem {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");

    private static final String APPID="web01"; //日志应用ID，每一个应用都是唯一的

    private static String rootPath;//日志根目录

    private static String currentHead="";//批次号前缀

    private static int currentSeq = 1;//批次号序号 1-999999 补0


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        LogSystem.setRootPath("d:/logs");
        for(int i=0;i<1000;i++) {
            LogSystem.writeLog("测试业务"+i, "system", "{这只是一个测试数据}");
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start+"毫秒");
    }

    public static void setRootPath(String rp){
        rootPath = rp;
    }

    /**
     * 写出日志文件
     * @param traceName 业务名称
     * @param user 当前用户
     * @param data 数据JSON字符串
     */
    public static void writeLog(String traceName,String user,String data) throws IOException {
        Date now = new Date();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
        String day = new SimpleDateFormat("yyyyMMdd").format(now);
        //获取批次号
        String batchNo = getCurrentBatchNo();
        //根据当前日期创建文件夹
        File currDir = new File(rootPath+"/"+day);
        if(!currDir.exists())currDir.mkdirs();
        //根据批次号创建文件
        File currFile = new File(currDir,"/"+batchNo+".log");
        if(!currFile.exists())currFile.createNewFile();
        //写入数据
        FileWriter fw = new FileWriter(currFile);
        //构建需要写入的数据
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(batchNo+"\t");//批次号
        dataBuffer.append(traceName+"\t");
        dataBuffer.append(data+"\t");
        dataBuffer.append(user+"\t");
        dataBuffer.append(time);
        try {
            fw.write(dataBuffer.toString());
        }finally {
            fw.close();
        }
    }


    private static synchronized String getCurrentSeq(boolean reset){
        if(reset)currentSeq = 0;
        currentSeq=currentSeq+1;
        String curr = String.valueOf(currentSeq);
        StringBuffer sb = new StringBuffer();
        for(int i=curr.length();i<6;i++){
            sb.append("0");
        }
        sb.append(curr);
        return sb.toString();
    }

    /**
     * @return 获取当前批次号，（同步）
     */
    private static synchronized String getCurrentBatchNo(){
        String batchNo;
        String now = df.format(new Date());
        if(now.equals(currentHead)){
            batchNo = APPID+"_"+currentHead+"_"+getCurrentSeq(false);
        }else {
            batchNo = APPID+"_"+now+"_"+getCurrentSeq(true);
            currentHead = now;
        }
        return batchNo;
    }


}
