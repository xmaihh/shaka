package com.wx.base.app.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.wx.base.R;


/**
 * Created by lenovo on 2015/8/3 0003.
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    protected final String TAG = "wx.ui.base.activity.toolbar";
    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";

    private boolean _isVisible;

    protected LayoutInflater mInflater;


    public Toolbar toolbar;
    protected TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle();
        //
        setContentView(getLayout());
        //
        name = (TextView)findViewById(R.id.name);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        initActionBar(toolbar);
        setHomeEnabled(true);

        setup();
        init();
        initViews();
        initDatas();
    }

    protected abstract int getLayout();
    protected abstract void init();

    protected void initBundle(){}
    protected void setup(){}
    protected void initViews(){}
    protected void initDatas(){}


    @Override
    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            if(name != null){
                name.setText(title);
            }
        }
    }

}
