package com.sychan.shaka.app.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sychan.shaka.R;
import com.sychan.shaka.support.widget.LineCircle;
import com.wx.base.app.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by sychan on 2017-11-14.
 * Function：
 */
public class WindFragment extends BaseFragment {
    @BindView(R.id.min_temp)
    EditText minTemp;
    @BindView(R.id.max_temp)
    EditText maxTemp;
    @BindView(R.id.current_temp)
    EditText currentTemp;
    @BindView(R.id.lindCircle)
    LineCircle lindCircle;
    Unbinder unbinder;

    private static final int TAG = 1;
    private int min, max, center1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_windtest;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TAG:
                    lindCircle.setAngle(min, max);
                    lindCircle.setMinMaxTem(min, max);
                    lindCircle.setCenterTemper(center1);
            }
        }
    };

    @OnClick({R.id.min_temp, R.id.max_temp, R.id.current_temp, R.id.lindCircle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.min_temp:
                break;
            case R.id.max_temp:
                break;
            case R.id.current_temp:
                break;
            case R.id.lindCircle:
                min = Integer.parseInt(minTemp.getText().toString());
                max = Integer.parseInt(maxTemp.getText().toString());
                center1 = Integer.parseInt(currentTemp.getText().toString());

                Message msg = new Message();
                msg.what = TAG;
                mHandler.sendMessage(msg);

                break;
        }
    }
}
