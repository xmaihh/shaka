package com.sychan.shaka.project.entity.model;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 发布任务
 */

public class ReleaseTask extends BmobObject {
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
    public String toString() {
        return "ReleaseTask{" +
                "title='" + title + '\'' +
                ", publisher=" + publisher +
                ", ispublish=" + ispublish +
                ", type=" + type +
                ", unitprice=" + unitprice +
                ", raiseprice=" + raiseprice +
                ", count=" + count +
                ", totalprice=" + totalprice +
                ", publicaccounts='" + publicaccounts + '\'' +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                ", createdat=" + createdat +
                ", deadline=" + deadline +
                ", finishDate=" + finishDate +
                ", files=" + files +
                '}';
    }
}
