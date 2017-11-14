package com.sychan.shaka.project.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 发布任务
 */

public class ReleaseTask extends BmobObject implements Parcelable {

    private String title;           //发布标题
    private User publisher;         //发布人
    private Boolean ispublish;      //允许发布
    private Integer type;           //类型
    private Integer unitprice;      //单价
    private Integer raiseprice;     //加价
    private Integer count;          //数量
    private Integer totalprice;     //总价
    private String publicaccounts;  //公众号
    private String voter;           //被投人
    private String url;             //链接
    private String remark;          //备注
    private Date createdat;         //创建日期
    private Date deadline;          //截至时间
    private Date finishDate;        //完成时间
    private List<BmobFile> files;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Boolean getIspublish() {
        return ispublish;
    }

    public void setIspublish(Boolean ispublish) {
        this.ispublish = ispublish;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Integer unitprice) {
        this.unitprice = unitprice;
    }

    public Integer getRaiseprice() {
        return raiseprice;
    }

    public void setRaiseprice(Integer raiseprice) {
        this.raiseprice = raiseprice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public String getPublicaccounts() {
        return publicaccounts;
    }

    public void setPublicaccounts(String publicaccounts) {
        this.publicaccounts = publicaccounts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<BmobFile> getFiles() {
        return files;
    }

    public void setFiles(List<BmobFile> files) {
        this.files = files;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.publisher);
        dest.writeValue(this.ispublish);
        dest.writeValue(this.type);
        dest.writeValue(this.unitprice);
        dest.writeValue(this.raiseprice);
        dest.writeValue(this.count);
        dest.writeValue(this.totalprice);
        dest.writeString(this.publicaccounts);
        dest.writeString(this.voter);
        dest.writeString(this.url);
        dest.writeString(this.remark);
        dest.writeLong(this.createdat != null ? this.createdat.getTime() : -1);
        dest.writeLong(this.deadline != null ? this.deadline.getTime() : -1);
        dest.writeLong(this.finishDate != null ? this.finishDate.getTime() : -1);
        dest.writeList(this.files);
    }

    public ReleaseTask() {
    }

    protected ReleaseTask(Parcel in) {
        this.title = in.readString();
        this.publisher = (User) in.readSerializable();
        this.ispublish = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.unitprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.raiseprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.publicaccounts = in.readString();
        this.voter = in.readString();
        this.url = in.readString();
        this.remark = in.readString();
        long tmpCreatedat = in.readLong();
        this.createdat = tmpCreatedat == -1 ? null : new Date(tmpCreatedat);
        long tmpDeadline = in.readLong();
        this.deadline = tmpDeadline == -1 ? null : new Date(tmpDeadline);
        long tmpFinishDate = in.readLong();
        this.finishDate = tmpFinishDate == -1 ? null : new Date(tmpFinishDate);
        this.files = new ArrayList<BmobFile>();
        in.readList(this.files, BmobFile.class.getClassLoader());
    }

    public static final Creator<ReleaseTask> CREATOR = new Creator<ReleaseTask>() {
        @Override
        public ReleaseTask createFromParcel(Parcel source) {
            return new ReleaseTask(source);
        }

        @Override
        public ReleaseTask[] newArray(int size) {
            return new ReleaseTask[size];
        }
    };
}
