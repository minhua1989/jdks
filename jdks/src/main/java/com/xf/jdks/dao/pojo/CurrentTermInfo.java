package com.xf.jdks.dao.pojo;

import java.util.Map;

/**
 * Created by root on 16-8-26.
 */
public class CurrentTermInfo{
    private String termcode;
    private String termname;
    private String studyyears_upp;
    private String studyyears_low;
    private String termflg;
    private String currentterm;

    public CurrentTermInfo(Map<String,Object> data){
        String termcode = (String)data.get("termcode");
        String termname = (String) data.get("termname");
        String studyyears_upp = data.get("studyyears_upp").toString();
        String studyyears_low = data.get("studyyears_low").toString();
        String termflg = (String) data.get("termflg");
        String currentterm =  data.get("currentterm").toString();
        this.termcode = termcode;
        this.termname = termname;
        this.studyyears_low = studyyears_low;
        this.studyyears_upp = studyyears_upp;
        this.termflg = termflg;
        this.currentterm = currentterm;
    }

    public String getTermcode() {
        return termcode;
    }

    public void setTermcode(String termcode) {
        this.termcode = termcode;
    }

    public String getTermname() {
        return termname;
    }

    public void setTermname(String termname) {
        this.termname = termname;
    }

    public String getStudyyears_upp() {
        return studyyears_upp;
    }

    public void setStudyyears_upp(String studyyears_upp) {
        this.studyyears_upp = studyyears_upp;
    }

    public String getStudyyears_low() {
        return studyyears_low;
    }

    public void setStudyyears_low(String studyyears_low) {
        this.studyyears_low = studyyears_low;
    }

    public String getTermflg() {
        return termflg;
    }

    public void setTermflg(String termflg) {
        this.termflg = termflg;
    }

    public String getCurrentterm() {
        return currentterm;
    }

    public void setCurrentterm(String currentterm) {
        this.currentterm = currentterm;
    }
}
