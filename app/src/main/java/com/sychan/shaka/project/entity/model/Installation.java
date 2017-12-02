package com.sychan.shaka.project.entity.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobInstallation;

public class Installation extends BmobInstallation implements Parcelable {
    // 设备型号
    private String model = Build.MODEL;
    // 设备SDK版本
    private String sdk = Build.VERSION.SDK;
    // 设备的系统版本
    private String version = Build.VERSION.RELEASE;
    //手机号码
    private String mac;
    //有无手机卡
    private boolean hassimcard;
    //手机号
    private String phone;
    //imei
    private String imei;
    //手机厂商
    private String branch;
    //语言
    private String local;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isHassimcard() {
        return hassimcard;
    }

    public void setHassimcard(boolean hassimcard) {
        this.hassimcard = hassimcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.model);
        dest.writeString(this.sdk);
        dest.writeString(this.version);
        dest.writeString(this.mac);
        dest.writeByte(this.hassimcard ? (byte) 1 : (byte) 0);
        dest.writeString(this.phone);
        dest.writeString(this.imei);
        dest.writeString(this.branch);
        dest.writeString(this.local);
    }

    public Installation() {
    }

    protected Installation(Parcel in) {
        this.model = in.readString();
        this.sdk = in.readString();
        this.version = in.readString();
        this.mac = in.readString();
        this.hassimcard = in.readByte() != 0;
        this.phone = in.readString();
        this.imei = in.readString();
        this.branch = in.readString();
        this.local = in.readString();
    }

    public static final Parcelable.Creator<Installation> CREATOR = new Parcelable.Creator<Installation>() {
        @Override
        public Installation createFromParcel(Parcel source) {
            return new Installation(source);
        }

        @Override
        public Installation[] newArray(int size) {
            return new Installation[size];
        }
    };
}
