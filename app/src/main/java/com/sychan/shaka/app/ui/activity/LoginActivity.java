package com.sychan.shaka.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sychan.shaka.MainActivity;
import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.widget.TogglePasswordVisibilityEditText;
import com.wx.base.app.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.sychan.shaka.project.config.Constants.Bundle_Data;
import static com.sychan.shaka.project.config.Constants.Bundle_User;

/**
 * 登录账号
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_mobile_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_passwd)
    TogglePasswordVisibilityEditText etPasswd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Bundle_Data);
        if (bundle != null) {
            etLoginPhone.setText(bundle.getString(Bundle_User, ""));
        }
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
            case R.id.btn_login:
                user = new User();
                user.setUsername(etLoginPhone.getText().toString());
                user.setPassword(etPasswd.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                                    .show();
                            user = User.getCurrentUser(User.class);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

}
