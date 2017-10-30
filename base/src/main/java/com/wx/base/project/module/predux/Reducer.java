package com.wx.base.project.module.predux;

/**
 * Created by alex on 16-11-30.
 */

public abstract class Reducer extends DataObserver {

    protected final String TAG = "wx.reducer";

    @Override
    public void onEvent(Object event) {

    }

    public abstract void reduce(Action action);
}
