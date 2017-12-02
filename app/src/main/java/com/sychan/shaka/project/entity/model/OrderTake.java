package com.sychan.shaka.project.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by sychan on 2017-11-13.
 * Function：接单数据表
 */
public class OrderTake extends BmobObject implements Parcelable {
    //接单人
    private User user;
    //任务
    private ReleaseTask task;
    //任务id
    private String taskId;
    //上传截图
    private List<BmobFile> files;
    //完成时间
    private Date finishDate;
    //目前状态
    private Integer state;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReleaseTask getTask() {
        return task;
    }

    public void setTask(ReleaseTask task) {
        this.task = task;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<BmobFile> getFiles() {
        return files;
    }

    public void setFiles(List<BmobFile> files) {
        this.files = files;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.task, flags);
        dest.writeString(this.taskId);
        dest.writeList(this.files);
        dest.writeLong(this.finishDate != null ? this.finishDate.getTime() : -1);
        dest.writeValue(this.state);
    }

    public OrderTake() {
    }

    protected OrderTake(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.task = in.readParcelable(ReleaseTask.class.getClassLoader());
        this.taskId = in.readString();
        this.files = new ArrayList<BmobFile>();
        in.readList(this.files, BmobFile.class.getClassLoader());
        long tmpFinishDate = in.readLong();
        this.finishDate = tmpFinishDate == -1 ? null : new Date(tmpFinishDate);
        this.state = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<OrderTake> CREATOR = new Creator<OrderTake>() {
        @Override
        public OrderTake createFromParcel(Parcel source) {
            return new OrderTake(source);
        }

        @Override
        public OrderTake[] newArray(int size) {
            return new OrderTake[size];
        }
    };
}
