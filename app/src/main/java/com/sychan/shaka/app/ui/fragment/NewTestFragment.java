package com.sychan.shaka.app.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.sychan.shaka.R;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.entity.model.helper.DataHelper;
import com.sychan.shaka.support.utils.ToastUtil;
import com.wx.base.app.ui.fragment.BaseFragment;
import com.wx.base.support.android.pagestate.StateView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewTestFragment extends BaseFragment {

    @BindView(R.id.image_gallery_view)
    RecyclerView recyclerView;
    @BindView(R.id.buttonPanel)
    Button buttonPanel;
    Unbinder unbinder;

    @Override
    protected int getLayout() {
        usePageState = true;
        return R.layout.fragment_new_test;
    }

    @Override
    protected void initViews() {
        super.initViews();
        pageStatePresenter.changeState(StateView.PageState.Empty);
    }

    @OnClick(R.id.buttonPanel)
    public void onViewClicked() {
        if (DataHelper.getReleaseTask(1) != null) {
            List<ReleaseTask> list = DataHelper.getReleaseTask(1);
            ToastUtil.show(list.size() + "  " + list.toString());
        }
    }
}
