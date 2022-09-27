package com.jasonli.homework.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 4268287310462519889L;

    public String status;

    private String msg;

    private ArrayList<String> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
