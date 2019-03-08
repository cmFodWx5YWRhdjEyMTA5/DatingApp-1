package com.example.brill.bdatingapp.gattersatter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brill on 2/6/2018.
 */

public class GatterGetAllUserInfo implements Parcelable {



   private String  userId, uname,gender,birthday,city,profile,photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9;

    public GatterGetAllUserInfo(String userId, String uname, String gender, String birthday, String city, String profile, String photo1, String photo2, String photo3, String photo4, String photo5, String photo6, String photo7, String photo8, String photo9) {
        this.profile = profile;
        this.city = city;
        this.birthday = birthday;
        this.gender = gender;
        this.uname = uname;
        this.userId = userId;
        this.photo1=photo1;
        this.photo2=photo2;
        this.photo3=photo3;
        this.photo4=photo4;
        this.photo5=photo5;
        this.photo6=photo6;
        this.photo7=photo7;
        this.photo8=photo8;
        this.photo9=photo9;
    }

    protected GatterGetAllUserInfo(Parcel in) {
        userId = in.readString();
        uname = in.readString();
        gender = in.readString();
        birthday = in.readString();
        city = in.readString();
        profile = in.readString();
        photo1 = in.readString();
        photo2 = in.readString();
        photo3 = in.readString();
        photo4 = in.readString();
        photo5 = in.readString();
        photo6 = in.readString();
        photo7 = in.readString();
        photo8 = in.readString();
        photo9 = in.readString();
    }

    public static final Creator<GatterGetAllUserInfo> CREATOR = new Creator<GatterGetAllUserInfo>() {
        @Override
        public GatterGetAllUserInfo createFromParcel(Parcel in) {
            return new GatterGetAllUserInfo(in);
        }

        @Override
        public GatterGetAllUserInfo[] newArray(int size) {
            return new GatterGetAllUserInfo[size];
        }
    };

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

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public void setPhoto5(String photo5) {
        this.photo5 = photo5;
    }

    public String getPhoto6() {
        return photo6;
    }

    public void setPhoto6(String photo6) {
        this.photo6 = photo6;
    }

    public String getPhoto7() {
        return photo7;
    }

    public void setPhoto7(String photo7) {
        this.photo7 = photo7;
    }

    public String getPhoto8() {
        return photo8;
    }

    public void setPhoto8(String photo8) {
        this.photo8 = photo8;
    }

    public String getPhoto9() {
        return photo9;
    }

    public void setPhoto9(String photo9) {
        this.photo9 = photo9;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(uname);
        parcel.writeString(gender);
        parcel.writeString(birthday);
        parcel.writeString(city);
        parcel.writeString(profile);
        parcel.writeString(photo1);
        parcel.writeString(photo2);
        parcel.writeString(photo3);
        parcel.writeString(photo4);
        parcel.writeString(photo5);
        parcel.writeString(photo6);
        parcel.writeString(photo7);
        parcel.writeString(photo8);
        parcel.writeString(photo9);
    }
}
