package com.sychan.shaka.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sychan.shaka.App;
import com.sychan.shaka.MainActivity;
import com.wx.base.app.ui.activity.BaseActivity;

import cn.bmob.v3.BmobUser;

public class LauncherActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断用户是否登录
                if (BmobUser.getCurrentUser() != null) {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                }
            }
        }, 500);
    }
}
