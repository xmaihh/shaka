package com.sychan.shaka.project.config;

/**
 * Created by sychan on 2017-11-09.
 * Function：定义一个任务类型枚举类
 */
public enum orderType {
    // 因为已经定义了带参数的构造器，所以在列出枚举值时必须传入对应的参数

    TP_FOCUS(100),
    TP_PILOTVOTE(200),
    TP_TPOSVOTE(300),
    TP_PVIEWS(400),
    TP_PVIEWSTHUMBS(500),
    TP_PVIEWSTHUMBSREVIEWS(600);

    // 定义一个 private 修饰的实例变量
    private int type;

    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
    orderType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "orderType{" +
                "type=" + type +
                '}';
    }

    public static void main(String[] args) {
        orderType[] type = orderType.values();
        for (orderType s : type) {
            System.out.println("当前任务name" + s.name());
            System.out.println("当前任务ordinal" + s.ordinal());
            System.out.println("当前任务" + s);
        }
    }

}
