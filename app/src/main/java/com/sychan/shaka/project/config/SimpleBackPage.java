package com.sychan.shaka.project.config;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.fragment.FeedbackFragment;
import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.orderCompleteFragment;
import com.sychan.shaka.app.ui.fragment.orderDetailFragment;
import com.sychan.shaka.app.ui.fragment.orderReviewFragment;
import com.sychan.shaka.app.ui.fragment.releaseTaskFragment;
import com.wx.base.project.module.simpleback.SimpleBackManager;


public enum SimpleBackPage {
    RELEASE_TASK(1, R.string.title_fragment_release_task, releaseTaskFragment.class),
    ORDER_DETAIL(2, R.string.title_fragment_orderDetail, orderDetailFragment.class),
    FEED_BACK(3, R.string.title_fragment_feed_back, FeedbackFragment.class),
    ORDER_REVIEW(4, R.string.title_fragment_order_review, orderReviewFragment.class),
    ORDER_COMPLETE(5, R.string.title_fragment_order_complete, orderCompleteFragment.class);
    private int title;
    private Class<?> clz;
    private int value;

    SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val) {
                return p;
            }
        }
        return null;
    }

    public com.wx.base.project.module.simpleback.SimpleBackPage getPage() {
        return SimpleBackManager.getInstance().getPageByValue(value);
    }

    //最好在Application里面执行初始化
    public static void init() {
        for (SimpleBackPage p : values()) {
            SimpleBackManager.getInstance().addPage(new com.wx.base.project.module.simpleback.SimpleBackPage(
                    p.getValue(),
                    p.getTitle(),
                    p.getClz()
            ));
        }
    }
}
