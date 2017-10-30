package com.wx.base.project.model.action;

import android.view.View;

import com.wx.base.project.module.predux.Action;

/**
 * Created by alex on 16-11-30.
 */

public class ClickAction<T> extends Action {
    View view;
    T entity;
    int position;

    public ClickAction(){
        setType("CLICK");
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
