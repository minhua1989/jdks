package com.xf.jdks.commons.global;

import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.dao.bean.InsertParams;
import com.xf.jdks.exceptions.FileTypeNotFoundException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by riolee on 16-8-4.
 */
public class FileInfoPo {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private String id;
    private FileTypePo type;
    private String name;
    private String lastModiTime;

    /**
     * @return 获取当前文件对应的数据库表记录
     */
    public Map<String,Object> getMapData(){
        Map<String,Object> rs = new DataMap<>();
        rs.put("id",getId());
        rs.put("type",getTypeId());
        rs.put("name",getName());
        rs.put("lastmoditime",getLastModiTime());
        return rs;
    }

    public static FileInfoPo createFileInfoByMapData(Map<String,Object> data) throws FileTypeNotFoundException {
        FileInfoPo rs = new FileInfoPo();
        rs.id = (String) data.get("id");
        rs.type = FileIOComponent.searchFileTypeByTypeId(Integer.valueOf(data.get("type").toString()));
        rs.name = (String)data.get("name");
        return rs;
    }

    public void setName(String name){
        this.name = name;
    }

    public File getFileObject(){
        return new File(getFileAbsPath());
    }

    public InsertParams getCurrentInsertParams(){
        InsertParams ip = InsertParams.createInsertParams("T_FILEMGRINFO","id","type","name","lastmoditime");
        ip.setValues(getId(),getTypeId(),getName(),getLastModiTime());
        return ip;
    }

    private FileInfoPo(){}

    /**
     * 创建一个新文件
     * @param typeId 类型ID
     * @throws FileTypeNotFoundException 当类型ID不合法时报出此异常信息
     */
    public FileInfoPo(int typeId) throws FileTypeNotFoundException {
        this(FileIOComponent.searchFileTypeByTypeId(typeId));
    }

    private FileInfoPo(FileTypePo type){
        this.id = UUID.randomUUID().toString();
        this.type = type;
        updateModifyTime();
        File dir = new File(getDirAbsPath());
        if(!dir.exists())dir.mkdirs();
    }

    private void updateModifyTime(){
        this.lastModiTime = FORMAT.format(new Date());
    }

    public String getId(){return this.id;}

    public int getTypeId(){
        return this.type.getId();
    }

    public String getDirAbsPath(){
        return this.type.getAbsPath();
    }

    public String getFileAbsPath(){
        return this.type.getAbsPath()+"/"+getName();
    }

    public String getFileVirPath(){
        return this.type.getVirPath()+"/"+getName();
    }

    public String getName(){
        return this.name;
    }

    public String getLastModiTime(){
        return this.lastModiTime;
    }

}
