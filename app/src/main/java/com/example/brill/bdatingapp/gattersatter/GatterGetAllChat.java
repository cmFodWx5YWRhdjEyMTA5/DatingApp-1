package com.example.brill.bdatingapp.gattersatter;

/**
 * Created by brill on 2/6/2018.
 */

public class GatterGetAllChat {


    private String userId,usenderid,recid,msg,readstatus,cdate;

    public GatterGetAllChat(String userId, String usenderid, String recid, String msg, String readstatus, String cdate) {
        this.userId = userId;
        this.usenderid = usenderid;
        this.recid = recid;
        this.msg = msg;
        this.readstatus = readstatus;
        this.cdate = cdate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsenderid() {
        return usenderid;
    }

    public void setUsenderid(String usenderid) {
        this.usenderid = usenderid;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
