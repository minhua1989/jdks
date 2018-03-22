package com.xf.jdks.commons.ext;

import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.commons.global.DataMap;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.commons.util.ExportExcel;
import com.xf.jdks.exceptions.FileTypeNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-10-21.
 */
public class DBList extends ArrayList<DBMap>{

    private String uniqueKey;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public DBMap searchForUniqueKey(String value){
        if(uniqueKey==null)return null;
        return searchForCustom(uniqueKey,value);
    }

    public DBMap searchForCustom(String column,String value){
        for(int i=0,len=super.size();i<len;i++){
            Object keyValue = get(i).get(column);
            if(keyValue!=null&&keyValue.toString().equalsIgnoreCase(value))return get(i);
        }
        return null;
    }

    public List<String> getColumnValueList(String column){
        List<String> rs = new ArrayList<>();
        for (int i=0,len=size();i<len;i++){
            Object value = get(i).get(column);
            rs.add(value==null?"":value.toString());
        }
        return rs;
    }

    public void updateDBMap(String unique,DBMap db){
        Object pkValue = db.get(unique);
        if(pkValue!=null){
           for (int i=0,len=size();i<len;i++){
               Object value = get(i).get(unique);
               if(value!=null&&pkValue.toString().equalsIgnoreCase(value.toString()))set(i,db);
           }
        }
    }

    public void updateDBMap(DBMap db){
        if(uniqueKey!=null){
            updateDBMap(uniqueKey,db);
        }
    }

    public void removeDBMap(String conditionColumn,String conditionValue){
        int index = -1;
        for(int i=0,len=size();i<len;i++){
            Object value = get(i).get(conditionColumn);
            if(value!=null&&value.equals(conditionValue)){
                index = i;
                break;
            }
        }
        if(index>=0){
            remove(index);
        }
    }

    public void removeDBMap(String conditionValue){
        if(uniqueKey!=null){
            removeDBMap(uniqueKey,conditionValue);
        }
    }

    public void removeDBMap(String conditionColumn,Object conditionValue){
        if(conditionValue!=null){
            removeDBMap(conditionColumn,conditionValue.toString());
        }
    }

    public void removeDBMap(Object conditionValue){
        if(conditionValue!=null)removeDBMap(conditionValue.toString());
    }

    public List<Map<String,Object>> toBaseList(){
        List<Map<String,Object>> rs = new ArrayList<>();
        for(int i=0,len=size();i<len;i++){
            Map<String,Object> item = new DataMap<>();
            for(Object key:get(i).keySet()){
                item.put(key.toString(),get(i).get(key));
            }
            rs.add(item);
        }
        return rs;
    }

    public FileInfoPo exportToExcel(String fileName,String excelHead) throws IOException, FileTypeNotFoundException {
        return exportToExcel(FileIOComponent.EXPORT_TYPE,fileName,excelHead);
    }

    public FileInfoPo exportToExcel(int ioType,String fileName,String excelHead) throws FileTypeNotFoundException, IOException {
        FileInfoPo io = FileIOComponent.createTempFileForNameAndType(ioType,fileName);
        ExportExcel export = ExportExcel.createExportExcel(io.getFileAbsPath(),excelHead);
        export.setCurrentData(toBaseList());
        export.writeCurrentData();
        return io;
    }

    public void reloadForBaseList(List<Map<String,Object>> list){
        if(list==null)return;
        this.clear();
        for(int i=0,len=list.size();i<len;i++){
            this.add(i,DBMap.createByMap(list.get(i)));
        }
    }
}
