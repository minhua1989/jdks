package com.xf.jdks.commons.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by riolee on 16-8-4.
 */
public class FileTypePo {
    private int id;
    private String absPath;
    private String virPath;
    private String suix;
    private String name;
    private String remark;
    private int mod;

    private FileTypePo(){}

    public Map<String,Object> getCurrentMapData(){
        Map<String,Object> rs = new DataMap<>();
        rs.put("id",getId());
        rs.put("abspath",getAbsPath());
        rs.put("virpath",getVirPath());
        rs.put("suix",getSuix());
        rs.put("name",getName());
        rs.put("mod",getMod());
        rs.put("remark",getRemark());
        return rs;
    }

    public static List<FileTypePo> createFileTypePosByList(List<Map<String,Object>> dataList){
        List<FileTypePo> rs = new ArrayList<>();
        for(Map<String,Object> data:dataList){
            rs.add(createFileTypePoByMap(data));
        }
        return rs;
    }
    
    public static FileTypePo createFileTypePoByMap(Map<String,Object> data){
        Integer id = Integer.valueOf(data.get("ID").toString());
        String absPath = (String)data.get("ABSPATH");
        String virPath = (String)data.get("VIRPATH");
        String suix = (String)data.get("SUIX");
        Integer mod = Integer.valueOf(data.get("MOD").toString());
        String name = (String)data.get("NAME");
        String remark = (String)data.get("REMARK");
        FileTypePo rs = new FileTypePo();
        rs.setId(id).setAbsPath(absPath).setVirPath(virPath).setSuix(suix).setMod(mod).setName(name).setRemark(remark);
        return rs;
    }
    
    public int getId() {
        return id;
    }

    public FileTypePo setId(int id) {
        this.id = id;
        return this;
    }

    public String getAbsPath() {
        return absPath;
    }

    public FileTypePo setAbsPath(String absPath) {
        this.absPath = absPath;
        return this;
    }

    public String getVirPath() {
        return virPath;
    }

    public FileTypePo setVirPath(String virPath) {
        this.virPath = virPath;
        return this;
    }

    public String getSuix() {
        return suix;
    }

    public FileTypePo setSuix(String suix) {
        this.suix = suix;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileTypePo setName(String name) {
        this.name = name;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FileTypePo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public int getMod() {
        return mod;
    }

    public FileTypePo setMod(int mod) {
        this.mod = mod;
        return this;
    }
}
