package com.sychan.shaka;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sychan.shaka.support.utils.ImagePickerUtil;
import com.wx.base.app.BaseApplication;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/

public class App extends BaseApplication {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "7d152f76660474edee4fc4a1bcf2911e");

        //TX Crash收集
//        CrashReport.initCrashReport(getApplicationContext(), "803534fc5e", true);
        //Logger 日志收集
        Logger.addLogAdapter(new AndroidLogAdapter());
        //配置图片选择器
        ImagePickerUtil.ImagePickerIntance();

        //设置BmobConfig
        BmobConfig config = new BmobConfig.Builder(instance)
                .setApplicationId("7d152f76660474edee4fc4a1bcf2911e")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(512 * 1024)
                .build();
        Bmob.initialize(config);

    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

}
