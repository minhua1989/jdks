package com.xf.jdks.dao.bean;

import com.xf.jdks.commons.util.Format;
import com.xf.jdks.dao.pojo.LoginInfo;

import java.util.UUID;

/**
 * Created by root on 16-10-24.
 */
public class DBLog {

    private String userId;

    private String traceName;

    private String exeSql;

    private String clientHost;

    public InsertParams getInsertParams(){
        InsertParams ins = InsertParams.createInsertParams("t_operator_log","id","userid","tracename","exetime","exesql","clienthost");
        ins.addValues(DataValue.createByObject(getId()),DataValue.createByObject(getUserId()),DataValue.createByObject(getTraceName()),DataValue.createByObject(getExeTime()),DataValue.createByObject(getExeSql()),DataValue.createByObject(getClientHost()));
        return ins;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public DBLog (){super();}

    public DBLog(LoginInfo loginInfo){
        this.userId = loginInfo.getId();
    }

    public String getId(){
        return UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getExeTime() {
        return Format.getIndexDateTime();
    }

    public String getExeSql() {
        return exeSql;
    }

    public void setExeSql(String exeSql,int mod) {
        switch (mod){
            default:break;
            case 0:
                this.exeSql = "INSERT INTO "+exeSql;
                break;
            case 1:
                this.exeSql = "DELETE FROM "+exeSql;
                break;
            case 2:
                this.exeSql = "UPDATE "+exeSql;
                break;
            case 3:
                this.exeSql = "SELECT "+exeSql;
                break;
        }
    }
}
