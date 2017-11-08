package com.sychan.shaka.project.config;

import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.fragment.NewTestFragment;
import com.sychan.shaka.app.ui.fragment.releaseTaskFragment;
import com.wx.base.project.module.simpleback.SimpleBackManager;


public enum SimpleBackPage {
    RELEASE_TASK(1, R.string.title_fragment_release_task, releaseTaskFragment.class),
    GROUP_CREATE(2, R.string.app_name, releaseTaskFragment.class),;
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
