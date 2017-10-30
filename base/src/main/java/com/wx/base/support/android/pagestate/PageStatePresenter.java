package com.wx.base.support.android.pagestate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.base.R;

/**
 * Created by alex on 17-1-4.
 */

public class PageStatePresenter {
    Context context;
    PageStateLayout pageStateLayout;
    private LayoutInflater mInflater;

    public PageStatePresenter(Context context){
        this.context = context;
        pageStateLayout = new PageStateLayout(context);
        mInflater = LayoutInflater.from(context);
        useDefaultPageState();
    }


    public void useDefaultPageState(){
        //
        pageStateLayout.addStateView(getLoadingStateView());
        pageStateLayout.addStateView(getEmptyStateView());

    }

    public void setPageStates(){

    }

    public void changeState(StateView.PageState state){
        pageStateLayout.changeState(state);
    }

    public void initPageStateWithoutResult(View root){
        ViewGroup contentParent = (ViewGroup)root.getParent();

        if(contentParent != null){
            contentParent.removeView(root);
            contentParent.addView(initPageState(root));
        }else{
            initPageState(root);
        }
    }

    public View initPageState(View root){
        StateView stateView = new StateView();
        stateView.setState(StateView.PageState.Content);
        stateView.setView(root);
        pageStateLayout.addStateView(stateView);
        return pageStateLayout;
    }


    private StateView getLoadingStateView(){
        StateView stateView = new StateView();
        stateView.setState(StateView.PageState.Loading);
        stateView.setView(mInflater.inflate(R.layout.view_loading,null));
        return stateView;
    }
    private StateView getEmptyStateView(){
        StateView stateView = new StateView();
        stateView.setState(StateView.PageState.Empty);
        stateView.setView(mInflater.inflate(R.layout.view_empty,null));
        return stateView;
    }

}
