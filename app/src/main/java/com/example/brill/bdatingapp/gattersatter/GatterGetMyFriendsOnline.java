package com.example.brill.bdatingapp.gattersatter;

/**
 * Created by brill on 2/6/2018.
 */

public class GatterGetMyFriendsOnline {


    private String intId,fname;

    public String getIntId() {
        return intId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public GatterGetMyFriendsOnline(String intId, String fname) {
        this.intId = intId;
        this.fname = fname;

    }
}
