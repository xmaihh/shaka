package com.sychan.shaka.project.config;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by lenovo on 2016/1/1 0001.
 */
public class SimpleBackHelper {

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBack(context,
                page.getPage()
        );
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBack(
                context, page.getPage(),
                args
        );
    }

    public static void showSimpleBackForResult(Fragment fragment,
                                               int requestCode, SimpleBackPage page) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBackForResult(fragment, requestCode,
                page.getPage()
        );
    }

    public static void showSimpleBackForResult(Fragment fragment,
                                               int requestCode, SimpleBackPage page, Bundle args) {

        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBackForResult(fragment, requestCode, page.getPage(), args);
    }

    public static void showSimpleBackForResult(Activity context,
                                               int requestCode, SimpleBackPage page, Bundle args) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBackForResult(context, requestCode, page.getPage(), args);
    }

    public static void showSimpleBackForResult(Activity context,
                                               int requestCode, SimpleBackPage page) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBackForResult(context, requestCode, page.getPage());
    }


    public static void showSimpleBack(Context context, SimpleBackPage page, int flag) {
        com.wx.base.project.module.simpleback.SimpleBackHelper.showSimpleBack(context,
                page.getPage(), flag);
    }
}
