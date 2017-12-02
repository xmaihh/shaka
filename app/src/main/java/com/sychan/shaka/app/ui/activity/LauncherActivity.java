package com.sychan.shaka.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sychan.shaka.MainActivity;
import com.sychan.shaka.R;
import com.sychan.shaka.support.utils.ToastUtil;
import com.wx.base.app.ui.activity.BaseActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import uk.co.senab.photoview.log.LoggerDefault;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断用户是否登录
                if (BmobUser.getCurrentUser() != null) {
                    fetchUserInfo();
                } else {
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                }
            }
        }, 500);
    }


    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     *
     * @see cn.bmob.v3.helper.ErrorCode#E9024S
     */
    private void fetchUserInfo() {

        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    Log.d(TAG, "Newest UserInfo is " + s);
                } else {
                    Log.e(TAG, "done: " + e.getErrorCode() + e.getMessage());
                }
            }
        });
    }
}
