package com.wx.base.project.model;

import android.support.v4.app.Fragment;

/**
 * Created by lenovo on 2015/8/12 0012.
 */
public class ResourceMap {

    protected int type;             //资源类型
    protected int icon;
    protected String name;          //资源名称
    protected Fragment fragment;    //资源Fragment

    public ResourceMap(String name){
        this.name = name;
    }
    public ResourceMap(String name, Fragment fragment){
        this.name = name;
        this.fragment = fragment;
    }

    public ResourceMap(int icon, String name, Fragment fragment){
        this(name,fragment);
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
