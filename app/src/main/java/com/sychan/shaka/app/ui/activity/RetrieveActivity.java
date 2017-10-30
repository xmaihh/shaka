package com.sychan.shaka.app.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
 * 找回密码
 */
public class RetrieveActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_forgetPass_PhoneNum)
    EditText etForgetPassPhoneNum;
    @BindView(R.id.et_phoneCodes)
    EditText etPhoneCodes;
    @BindView(R.id.iv_showCode)
    ImageView ivShowCode;
    @BindView(R.id.but_forgetpass_toSetCodes)
    Button butForgetpassToSetCodes;


    private String realCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    Toast.makeText(App.currentActivity(), phoneCode + "验证码正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(App.currentActivity(), phoneCode + "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
