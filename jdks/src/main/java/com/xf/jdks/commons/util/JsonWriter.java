package com.xf.jdks.commons.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-10-20.
 */
public class JsonWriter {

    private JsonWriter() {
    }

    public static JsonWriter createNewInstance(String absPath){
        JsonWriter rs = new JsonWriter();
        rs.setAbsPath(absPath);
        return rs;
    }

    private String absPath;

    private JSONObject current;

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public JSONObject getCurrent() throws IOException {
        readForFile();
        return current;
    }

    public void setCurrent(JOPlus current) {
        this.current = current;
    }

    public void pushLog(JOPlus student) throws IOException {
       getCurrent().getJSONArray("log").add(student);
       writeData();
    }

    public List<Map<String,Object>> castLogToList() throws IOException {
        List<Map<String,Object>> rs = new ArrayList<>();
        JSONArray logArr = getCurrent().getJSONArray("log");
        if(logArr!=null){
            for(int i=0,len=logArr.size();i<len;i++){
                rs.add(logArr.getJSONObject(i));
            }
        }
        writeData();
        return rs;
    }

    public boolean checkCurrentStudentProcStatus(String studentId) throws IOException {
        boolean isDone = false;
        JSONArray logList = getCurrent().getJSONArray("log");
        for(int i=0,len=logList.size();i<len;i++){
            JSONObject curr = logList.getJSONObject(i);
            String logStudentId = curr.getString("studentid");
            if(logStudentId.equalsIgnoreCase(studentId)){
                isDone = true;
                break;
            }
        }
        return isDone;
    }

    private void readForFile() throws IOException {
        File file = new File(absPath);
        BufferedReader reader =new BufferedReader(new FileReader(file));
        StringBuffer data = new StringBuffer();
        String temp = null;
        while ((temp = reader.readLine()) != null) {
            data.append(temp);
        }
        this.current = JSONObject.parseObject(data.toString());
        reader.close();
    }

    public void writeData() throws IOException {
        File file = new File(absPath);
        if(!file.exists())file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(this.current.toJSONString());
        writer.close();
    }
}
