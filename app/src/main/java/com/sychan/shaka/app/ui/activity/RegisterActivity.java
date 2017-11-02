package com.sychan.shaka.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sychan.shaka.App;
import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.utils.Code;
import com.sychan.shaka.support.widget.TogglePasswordVisibilityEditText;
import com.wx.base.app.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册账号
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_forgetPass_PhoneNum)
    EditText etForgetPassPhoneNum;
    @BindView(R.id.et_phoneCodes)
    EditText etPhoneCodes;
    @BindView(R.id.iv_showCode)
    ImageView ivShowCode;
    @BindView(R.id.but_forgetpass_toSetCodes)
    Button butForgetpassToSetCodes;
    @BindView(R.id.et_passwd)
    TogglePasswordVisibilityEditText etPasswd;


    private String realCode;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode();
    }

    @OnClick({R.id.iv_showCode, R.id.but_forgetpass_toSetCodes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_showCode:
                ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode();
                break;
            case R.id.but_forgetpass_toSetCodes:
                String phoneCode = etPhoneCodes.getText().toString();
                if (phoneCode.equalsIgnoreCase(realCode)) {
                    user = new User();
                    user.setName(etForgetPassPhoneNum.getText().toString());
                    user.setAge("18");
                    user.setUsername(etForgetPassPhoneNum.getText().toString());
                    user.setPassword(etPasswd.getText().toString());
                    user.setWchat(etPhoneCodes.getText().toString());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
                                        .show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                        .show();
                                Logger.e(e.getErrorCode() + e.toString());
                            }
                        }
                    });
                    Toast.makeText(App.currentActivity(), phoneCode + "验证码正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App.currentActivity(), phoneCode + "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
