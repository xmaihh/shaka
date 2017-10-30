package com.wx.base.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.wx.base.R;
import com.wx.base.app.ui.fragment.BaseFragment;

import java.lang.ref.WeakReference;

/**
 * Created by alex on 16-12-8.
 */

public abstract class BaseSelectActivity extends BaseToolbarActivity {

    public static final int TYPE_SELECT_SINGLE  = 1;
    public static final int TYPE_SELECT_MULTI   = 2;

    protected int type;
    protected final String TAG = "wx.ui.base.select";

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String FRAG_TAG = "FLAG_TAG";
    private WeakReference<Fragment> mFragment;
    //

    protected Parcelable entry = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_toolbar_container;
    }

    protected abstract Fragment getFragment();

    @Override
    protected void init() {
        Intent data = getIntent();
        if(data == null){
            throw new RuntimeException("you must provide a page info");
        }
        //setActionBarTitle(page.getTitle());

        try {
            Fragment fragment = getFragment();

            entry = getIntent().getParcelableExtra("CURRENT");
            Bundle args = fragment.getArguments();
            //Bundle args = new Bundle();//data.getBundleExtra(BUNDLE_KEY_ARGS);
            args.putParcelable("CURRENT",entry);
            fragment.setArguments(args);

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
        Log.i(TAG, " onActivityResult");
        if(mFragment != null && mFragment.get() != null){
            mFragment.get().onActivityResult(requestCode & 0xffff, resultCode, data);
        }
    }
}
