package com.wx.base.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.wx.base.R;
import com.wx.base.app.ui.fragment.BaseFragment;
import com.wx.base.project.module.simpleback.SimpleBackManager;
import com.wx.base.project.module.simpleback.SimpleBackPage;

import java.lang.ref.WeakReference;

/**
 * Created by lenovo on 2015/12/8 0008.
 */
public class SimpleBackActivity extends BaseToolbarActivity {

    protected final String TAG = "wx.ui.simpleback";

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String FRAG_TAG = "FLAG_TAG";
    private WeakReference<Fragment> mFragment;
    //

    @Override
    protected int getLayout() {
        return R.layout.activity_toolbar_container;
    }

    @Override
    protected void init() {
        Intent data = getIntent();
        if(data == null){
            throw new RuntimeException("you must provide a page info");
        }

        int pageValue = data.getIntExtra(BUNDLE_KEY_PAGE,0);
        //SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        SimpleBackPage page = SimpleBackManager.getInstance().getPageByValue(pageValue);
        if(page == null){
            throw new IllegalArgumentException("can't find page by value: " + pageValue);
        }
        setActionBarTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment)page.getClz().newInstance();
            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if(args != null){
                fragment.setArguments(args);
            }
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,fragment,FRAG_TAG)
                    .commit();
            mFragment = new WeakReference<Fragment>(fragment);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("generate fragment error. byy");
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.get() != null
                && mFragment.get() instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) mFragment.get();
            if (!bf.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.i(TAG, " Simple onActivityResult");
        if(mFragment != null && mFragment.get() != null && data != null){
            mFragment.get().onActivityResult(requestCode & 0xffff, resultCode, data);
        }
    }
}
