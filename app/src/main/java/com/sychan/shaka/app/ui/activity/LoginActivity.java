package com.sychan.shaka.app.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.widget.TogglePasswordVisibilityEditText;
import com.wx.base.app.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录账号
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_forgetPass_PhoneNum)
    EditText etForgetPassPhoneNum;
    @BindView(R.id.et_passwd)
    TogglePasswordVisibilityEditText etPasswd;
    @BindView(R.id.but_forgetpass_toSetCodes)
    Button butForgetpassToSetCodes;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_title, R.id.but_forgetpass_toSetCodes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
            case R.id.but_forgetpass_toSetCodes:
                user = new User();
                user.setUsername(etForgetPassPhoneNum.getText().toString());
                user.setPassword(etPasswd.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                                    .show();
                            user = User.getCurrentUser(User.class);
                            Logger.d(user.getUsername());
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
