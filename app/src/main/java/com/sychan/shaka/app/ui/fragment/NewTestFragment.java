package com.sychan.shaka.app.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.sychan.shaka.R;
import com.wx.base.app.ui.fragment.BaseFragment;

import butterknife.BindView;

public class NewTestFragment extends BaseFragment {

    @BindView(R.id.image_gallery_view)
    RecyclerView recyclerView;
    @BindView(R.id.buttonPanel)
    Button buttonPanel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_new_test;
    }
}
