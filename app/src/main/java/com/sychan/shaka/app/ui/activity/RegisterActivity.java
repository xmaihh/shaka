package com.sychan.shaka.app.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.Installation;
import com.sychan.shaka.project.entity.model.Paypel;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.utils.Code;
import com.sychan.shaka.support.utils.DeviceUtil;
import com.sychan.shaka.support.utils.PhoneUtil;
import com.sychan.shaka.support.utils.RC4;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.utils.loadingUtil;
import com.sychan.shaka.support.widget.TogglePasswordVisibilityEditText;
import com.wx.base.app.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import static com.sychan.shaka.project.config.Constants.Bundle_Data;
import static com.sychan.shaka.project.config.Constants.Bundle_User;

/**
 * 注册账号
 */
public class RegisterActivity extends BaseActivity implements Handler.Callback {
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.et_verify_code)
    EditText etPhoneCodes;
    @BindView(R.id.et_invite_code)
    EditText etInviteCodes;
    @BindView(R.id.iv_show_verify_code)
    ImageView ivShowVerifyCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.toget_passwd)
    TogglePasswordVisibilityEditText etPasswd;

    @BindView(R.id.et_smsCodes)
    EditText etSmscode;
    @BindView(R.id.tv_smsCode)
    TextView tvSmscode;

    loadingUtil loading;
    private boolean ready;

    private String realCode;
    private User user;
    private Installation installation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ivShowVerifyCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode();
        loading = new loadingUtil(mContext);
    }

    @OnClick({R.id.iv_show_verify_code, R.id.btn_register, R.id.tv_smsCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_verify_code:
                ivShowVerifyCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode();
                break;

            case R.id.tv_smsCode:
                if (Build.VERSION.SDK_INT >= 23) {
                    int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                    int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
                    int readSms = checkSelfPermission(Manifest.permission.READ_SMS);
                    int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                    int requestCode = 0;
                    ArrayList<String> permissions = new ArrayList<String>();
                    if (readPhone != PackageManager.PERMISSION_GRANTED) {
                        requestCode |= 1 << 0;
                        permissions.add(Manifest.permission.READ_PHONE_STATE);
                    }
                    if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                        requestCode |= 1 << 1;
                        permissions.add(Manifest.permission.RECEIVE_SMS);
                    }
                    if (readSms != PackageManager.PERMISSION_GRANTED) {
                        requestCode |= 1 << 2;
                        permissions.add(Manifest.permission.READ_SMS);
                    }
                    if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                        requestCode |= 1 << 4;
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                    if (requestCode > 0) {
                        String[] permission = new String[permissions.size()];
                        this.requestPermissions(permissions.toArray(permission), requestCode);
                        return;
                    }
                }
                registerSDK();

                tvSmscode.requestFocus();
                if (PhoneUtil.isChinaPhoneLegal(etMobilePhone.getText().toString().trim())) {
                    //启动获取验证码 86是中国
                    String phone = etMobilePhone.getText().toString().trim();
                    SMSSDK.getVerificationCode("86", phone);//发送短信验证码到手机号
                    timer.start();//使用计时器 设置验证码的时间限制
                    tvSmscode.setEnabled(false);
                } else {
                    ToastUtil.show("请输入正确的手机号码");
                }
                break;

            case R.id.btn_register:
                String phone = etMobilePhone.getText().toString().trim();
                String code = etSmscode.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", phone, code);//提交验证码  在eventHandler里面查看验证结果
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        registerSDK();
    }

    private void registerSDK() {
        // 在尝试读取通信录时以弹窗提示用户（可选功能）
        SMSSDK.setAskPermisionOnReadContact(true);

        final Handler handler = new Handler(this);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;

    }

    @Override
    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        switch (event) {
            case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                    register();
                } else {
                    ToastUtil.show("短信验证码输入错误");
                    ((Throwable) data).printStackTrace();
                    Log.d("521", "handleMessage: " + (Throwable) data);
                }
                break;
            case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                } else {
                    ((Throwable) data).printStackTrace();
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvSmscode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tvSmscode.setEnabled(true);
            tvSmscode.setText("获取验证码");
        }
    };

    private void register() {
        String phoneCode = etPhoneCodes.getText().toString().trim();
        if (phoneCode.equalsIgnoreCase(realCode)) {
            user = new User();
            user.setName(etMobilePhone.getText().toString());
            user.setAge("18");
            user.setMobilePhoneNumber(etMobilePhone.getText().toString().trim());
            user.setMobilePhoneNumberVerified(true);
            user.setUsername(etMobilePhone.getText().toString());
            user.setPassword(etPasswd.getText().toString());
            user.setSecret(etPasswd.getText().toString());
            user.setWechat(etPhoneCodes.getText().toString());
            user.setInvitecode(etInviteCodes.getText().toString());
            installation = new Installation();
            installation.setImei(DeviceUtil.getIMEI(mContext) + ".618");
            installation.setMac(DeviceUtil.getMac() + "");
            installation.setPhone(etMobilePhone.getText().toString());
            installation.setBranch(DeviceUtil.getDeviceBrand() + "");
            installation.setLocal(DeviceUtil.getSystemLanguage() + "");
            user.setPaypel(new Paypel());
            user.setInstallation(new Installation());
            String encode = RC4.encry_RC4_string(String.format("%04d", 1), UUID.randomUUID().toString().replace("-", "").toUpperCase());
            user.setInvitecode(encode);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Bundle_User, etMobilePhone.getText().toString());
                        intent.putExtra(Bundle_Data, bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
            ToastUtil.show(phoneCode + "验证码正确");
        } else {
            ToastUtil.show(phoneCode + "验证码错误");
        }
    }

}
