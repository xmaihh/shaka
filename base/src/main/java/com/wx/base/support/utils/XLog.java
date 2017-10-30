package com.wx.base.support.utils;

import android.util.Log;

/**
 * Created by alex on 16-12-19.
 */

public class XLog {


    public static void v(String TAG, String msg){
        Log.v(TAG, msg);
        handleLog(Log.VERBOSE, TAG, msg);
    }

    public static void d(String TAG, String msg){
        Log.d(TAG, msg);
        handleLog(Log.DEBUG, TAG, msg);
    }

    public static void i(String TAG, String msg){
        Log.i(TAG, msg);
        handleLog(Log.INFO, TAG, msg);
    }
    public static void w(String TAG, String msg){
        Log.w(TAG, msg);
        handleLog(Log.WARN, TAG, msg);
    }
    public static void e(String TAG, String msg){
        Log.e(TAG, msg);
        handleLog(Log.ERROR, TAG, msg);
    }

    public static void wtf(String TAG, String msg){
        Log.wtf(TAG, msg);
        handleLog(Log.VERBOSE, TAG, msg);
    }

    public static void handleLog(int level, String TAG, String msg){
        // 发送，归集，保存等操作
    }
}
