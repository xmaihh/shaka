package com.sychan.shaka.project.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by sychan on 2017-11-19.
 * Function：
 */
public class Paypel extends BmobObject implements Parcelable {
    private User user;
    private Integer earnings;
    private Integer totalearnings;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getEarnings() {
        return earnings;
    }

    public void setEarnings(Integer earnings) {
        this.earnings = earnings;
    }

    public Integer getTotalearnings() {
        return totalearnings;
    }

    public void setTotalearnings(Integer totalearnings) {
        this.totalearnings = totalearnings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeValue(this.earnings);
        dest.writeValue(this.totalearnings);
    }

    public Paypel() {
    }

    protected Paypel(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.earnings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalearnings = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Paypel> CREATOR = new Creator<Paypel>() {
        @Override
        public Paypel createFromParcel(Parcel source) {
            return new Paypel(source);
        }

        @Override
        public Paypel[] newArray(int size) {
            return new Paypel[size];
        }
    };
}
