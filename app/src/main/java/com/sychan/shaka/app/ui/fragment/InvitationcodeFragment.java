package com.sychan.shaka.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.User;
import com.wx.base.app.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * Created by sychan on 2017-11-19.
 * Function：
 */
public class InvitationcodeFragment extends BaseFragment {
    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.tv_earnings)
    TextView tvEarnings;
    @BindView(R.id.tv_invitation_count)
    TextView tvInvitationCount;
    Unbinder unbinder;

    @Override
    protected int getLayout() {
        return R.layout.fragment_invitation_code;
    }

    @Override
    protected void initViews() {
        super.initViews();
        User user = BmobUser.getCurrentUser(User.class);
        tvInvitationCount.setText(getString(R.string.tv_invitation_count) + "0");
        tvInvitationCode.setText(getString(R.string.tv_invitation_code) + user.getInvitecode());
        tvEarnings.setText(getString(R.string.tv_invitation_earnings) + "0");
    }

    @OnClick({R.id.tv_invitation_code, R.id.tv_earnings, R.id.tv_invitation_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_invitation_code:
                break;
            case R.id.tv_earnings:
                break;
            case R.id.tv_invitation_count:
                break;
        }
    }
}
