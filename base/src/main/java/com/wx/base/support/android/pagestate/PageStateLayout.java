package com.wx.base.support.android.pagestate;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 17-1-4.
 */

public class PageStateLayout extends FrameLayout {

    List<StateView> stateviews;
    StateView.PageState currentState = StateView.PageState.Content;

    public PageStateLayout(Context context) {
        super(context);
        stateviews = new ArrayList<>();
    }

    public void addStateView(StateView stateView){
        removeState(stateView.getState());
        stateviews.add(stateView);
        this.addView(stateView.getView());
        changeState(stateView.getState());
    }


    private void removeState(StateView.PageState state){
        for(StateView stateView:stateviews){
            if(state == stateView.getState()){
                //存在相同状态，替换
                removeStateView(stateView);
                return;
            }
        }
    }

    private void removeStateView(StateView stateView){
        stateviews.remove(stateView);
        this.removeView(stateView.getView());
    }


    public void changeState(StateView.PageState state){
        if(!checkState(state)){
            return;
        }
        for(StateView stateView:stateviews){
            if(stateView.getState() == state){
                stateView.getView().setVisibility(View.VISIBLE);
            }else{
                stateView.getView().setVisibility(View.GONE);
            }
        }
        this.currentState = state;

    }

    private boolean checkState(StateView.PageState state){
        for(StateView stateView:stateviews){
            if(stateView.getState() == state){
                stateView.getView().setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }
}
