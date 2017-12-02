package com.sychan.shaka.project.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by sychan on 2017-11-21.
 * Function：
 */
public class Feedback extends BmobObject implements Parcelable {
    //反馈内容
    private String content;
    //联系方式
    private String contacts;
    //反馈文件
    private List<File> files;
    //人
    private User user;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.contacts);
        dest.writeList(this.files);
        dest.writeParcelable(this.user, flags);
    }

    public Feedback() {
    }

    protected Feedback(Parcel in) {
        this.content = in.readString();
        this.contacts = in.readString();
        this.files = new ArrayList<File>();
        in.readList(this.files, File.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Feedback> CREATOR = new Parcelable.Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel source) {
            return new Feedback(source);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };
}
