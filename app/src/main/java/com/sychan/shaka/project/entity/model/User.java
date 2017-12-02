package com.sychan.shaka.project.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser implements Parcelable {
    private String name;         //姓名
    private String age;          //年龄
    private Integer gender;      //性别
    private String phone;        //电话
    private String wechat;       //微信
    private String idcard;       //身份
    private Integer codecount;   //验证码
    private String invitecode;    //邀请码
    private String superiorcode;  //上家邀请码
    private Paypel paypel;        //交易记录
    private String secret;
    private String aapt;
    private Installation installation;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getCodecount() {
        return codecount;
    }

    public void setCodecount(Integer codecount) {
        this.codecount = codecount;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public String getSuperiorcode() {
        return superiorcode;
    }

    public void setSuperiorcode(String superiorcode) {
        this.superiorcode = superiorcode;
    }

    public Paypel getPaypel() {
        return paypel;
    }

    public void setPaypel(Paypel paypel) {
        this.paypel = paypel;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAapt() {
        return aapt;
    }

    public void setAapt(String aapt) {
        this.aapt = aapt;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.age);
        dest.writeValue(this.gender);
        dest.writeString(this.phone);
        dest.writeString(this.wechat);
        dest.writeString(this.idcard);
        dest.writeValue(this.codecount);
        dest.writeString(this.invitecode);
        dest.writeString(this.superiorcode);
        dest.writeParcelable(this.paypel, flags);
        dest.writeString(this.secret);
        dest.writeString(this.aapt);
        dest.writeSerializable(this.installation);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.age = in.readString();
        this.gender = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.wechat = in.readString();
        this.idcard = in.readString();
        this.codecount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.invitecode = in.readString();
        this.superiorcode = in.readString();
        this.paypel = in.readParcelable(Paypel.class.getClassLoader());
        this.secret = in.readString();
        this.aapt = in.readString();
        this.installation = (Installation) in.readSerializable();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
