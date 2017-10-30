package com.wx.base.app;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.Stack;

/**
 * Created by alex on 16-11-15.
 */

public class BaseApplication extends MultiDexApplication {
    public static Stack<Activity> activities = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        //Autolayout
        AutoLayoutConifg.getInstance().useDeviceSize();
        if (activities == null) {
            activities = new Stack<Activity>();
        }
    }

    public static void finishActivity() {
        activities.pop().finish();
    }

    public static void addActivity(Activity context) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.push(context);
    }

    public static synchronized Activity currentActivity() {

        return activities.lastElement();
    }

    public static void exit() {
        if (activities == null) {
            return;
        }
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
        activities = null;
        System.exit(0);
    }

    public static void getMainActivity() {
        if (activities == null) {
            return;
        }
        for (int i = activities.size() - 1; i > 1; i--) {
            activities.pop().finish();
        }
    }

    public static void getLoginActivity() {
        if (activities == null) {
            return;
        }
        for (int i = activities.size() - 1; i > 0; i--) {
            if (!activities.lastElement().getClass().getName().equals("com.sychan.shaka.ui.MainActivity")) {
                activities.pop().finish();
            } else {
                activities.lastElement();
                break;
            }
        }

    }
}