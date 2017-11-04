package com.sychan.shaka.project.entity.model;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Task extends BmobObject {
    private String title;
    private String content;
    private User publisher;
    private boolean isPublish;
    private List<BmobFile> files;


    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public List<BmobFile> getFiles() {
        return files;
    }

    public void setFiles(List<BmobFile> files) {
        this.files = files;
    }

}
