package com.sychan.shaka.project.entity.model;

import cn.bmob.v3.BmobObject;


public class Bean extends BmobObject {
    private String name;
    private String age;

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    private String score;

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
