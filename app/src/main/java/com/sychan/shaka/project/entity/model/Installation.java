package com.sychan.shaka.project.entity.model;

import android.os.Build;

import cn.bmob.v3.BmobInstallation;

public class Installation extends BmobInstallation {

    private String model = Build.MODEL; // 设备型号
    private String sdk = Build.VERSION.SDK;// 设备SDK版本
    private String version = Build.VERSION.RELEASE; // 设备的系统版本
    private String mac;
    private boolean hassimcard;
    private String phone;
    private String imei;

    public String getModel() {
        return model;
    }

    public String getSdk() {
        return sdk;
    }

    public String getVersion() {
        return version;
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
}
