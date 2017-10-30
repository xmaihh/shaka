package com.wx.base.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wx.base.R;
import com.wx.base.app.ui.activity.BaseActivity;
import com.wx.base.project.model.action.ClickAction;
import com.wx.base.project.module.predux.Action;
import com.wx.base.project.module.predux.BaseReducer;
import com.wx.base.project.module.predux.Store;
import com.wx.base.support.android.pagestate.PageStatePresenter;
import com.wx.base.support.widget.MenuBar;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/2/26 0026.
 */
public abstract class BaseFragment extends Fragment {
    protected final String TAG = "wx.base";
    protected Context mContext;

    protected ViewGroup smartbar;

    protected Store presenter = new Store();

    protected boolean usePageState = false;

    public Store getStore(){
        return presenter;
    }

    protected PageStatePresenter pageStatePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this.getContext();//this.getActivity();
        this.pageStatePresenter = new PageStatePresenter(getContext());
        this.setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //
        initBundle();

        View view = inflaterView(inflater, container, savedInstanceState);
        bindViews(view);
        ButterKnife.bind(this, view);

        if(usePageState) {

            //FrameLayout frameLayout = new FrameLayout(getContext());
            //frameLayout.addView(view);
            //return frameLayout;
            return pageStatePresenter.initPageState(view);
        }else {
            return view;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //swipeContainer.setEnabled(false);
        setup();
        initViews();
        initDatas();
        renderSmartBar();
    }
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        return inflater.inflate(getLayout(),container,false);
    }

    protected abstract int getLayout();
    protected void initBundle(){}
    @CallSuper
    protected void bindViews(View view){
        smartbar = (ViewGroup) view.findViewById(R.id.smartbar);
    }

    protected void setup(){}
    protected void initViews(){}
    protected void initDatas(){}


    //设置是否使用SmartBar，以及SmartBar的类型
    protected boolean hasSamrtBar = false;
    protected boolean useBackMenu = true;
    protected void setHasSmartBar(boolean hasSB){
        this.hasSamrtBar = hasSB;
    }

    protected void setHasSmartBar(boolean hasSB, boolean useBack){
        this.hasSamrtBar = hasSB;
        this.useBackMenu = useBack;
    }


    protected void renderSmartBar(){
        // 使用Fragment
        if(!hasSamrtBar){
            if(smartbar != null) {
                smartbar.setVisibility(View.GONE);
            }
            return;
        }

        if(smartbar != null){
            smartbar.setVisibility(View.VISIBLE);
            setupSmartBar();
            return;
        }

        // 使用Activity
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).renderSmartBar();
        }
    }

    protected void setupSmartBar(){
        renderMenuBar();
    }

    protected void renderMenuBar(){

        //if(getMenuRes() != 0){
            MenuBar sb = new MenuBar(getContext());
            sb.useBack(useBackMenu);
            sb.getStore().addReducer(new BaseReducer() {
                @Override
                public boolean onReduce(Action action) {
                    ClickAction<MenuItemImpl> action1 = (ClickAction<MenuItemImpl>) action;
                    onOptionsItemSelected(action1.getEntity());
                    return true;
                }
            });
            sb.inflateMenu(getMenuRes());
            smartbar.addView(sb);
        //}
    }

    protected int getMenuRes(){
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        if(getMenuRes() != 0) {
            menu.clear();
            inflater.inflate(getMenuRes(), menu);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void finish(){
        if(getActivity() != null && !getActivity().isFinishing()){
            getActivity().finish();
        }
    }

}
