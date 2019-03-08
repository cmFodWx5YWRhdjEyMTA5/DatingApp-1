package com.example.brill.bdatingapp.gattersatter;

/**
 * Created by brill on 2/6/2018.
 */

public class GatterGetLikesUserList {


    private String userId, uname,gender,birthday,city,profile;

    public GatterGetLikesUserList(String userId, String uname, String gender, String birthday, String city, String profile) {
        this.profile = profile;
        this.city = city;
        this.birthday = birthday;
        this.gender = gender;
        this.uname = uname;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }




}
