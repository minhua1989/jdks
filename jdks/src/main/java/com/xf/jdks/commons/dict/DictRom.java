package com.xf.jdks.commons.dict;

import com.xf.jdks.commons.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-10-19.
 */
public class DictRom {

    private String tableName;

    private String codeColumn;

    private String nameColumn;

    private List<DictEntry> entries;

    private boolean isLoaded;

    public Object castValue(Object value) throws Exception {
        Object rs = null;
        if(value!=null){
            String temp = value.toString();
            if(!isLoaded){
                loadByDataBase();
            }
            for(DictEntry entry:entries){
                if(entry.getCode().equalsIgnoreCase(temp)){
                    rs = entry.getName();
                    break;
                }
                if(entry.getName().equals(temp)){
                    rs = entry.getCode();
                    break;
                }
            }
        }
        return rs;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCodeColumn() {
        return codeColumn;
    }

    public void setCodeColumn(String codeColumn) {
        this.codeColumn = codeColumn;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public void loadByDataBase() throws Exception {
        if(!isLoaded) {
            entries = new ArrayList<>();
            String loadSQL = "SELECT " + codeColumn + " , " + nameColumn + " FROM " + tableName;
            Connection conn = DBUtils.GetConnectionOracleMaster();
            PreparedStatement pre = conn.prepareStatement(loadSQL);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                DictEntry entry = new DictEntry();
                entry.setCode(rs.getString(codeColumn.toUpperCase()));
                entry.setName(rs.getString(nameColumn.toUpperCase()));
                entries.add(entry);
            }
            rs.close();
            pre.close();
            conn.close();
            isLoaded = true;
        }
    }
}
