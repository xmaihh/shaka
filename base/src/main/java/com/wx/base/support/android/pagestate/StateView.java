package com.wx.base.support.android.pagestate;

import android.view.View;

/**
 * Created by alex on 17-1-7.
 */

public class StateView {

    public enum PageState{
        Loading,
        Retry,
        Content,
        Error,
        Empty
    }


    PageState state;
    View view;

    public PageState getState() {
        return state;
    }

    public void setState(PageState state) {
        this.state = state;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
