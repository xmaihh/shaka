package com.sychan.shaka.project.entity.model;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String name;
    private String age;
    private Integer gender;
    private String phone;
    private String wechat;
    private String idcard;
    private Integer codecount;
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
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", wechat='" + wechat + '\'' +
                ", idcard='" + idcard + '\'' +
                ", codecount=" + codecount +
                ", aapt='" + aapt + '\'' +
                ", installation=" + installation +
                '}';
    }
}
