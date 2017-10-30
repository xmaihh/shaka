package com.wx.base.project.module.predux;

/**
 * Created by alex on 16-11-29.
 */

public abstract class DataObserver{

    public abstract void onEvent(Object event);

    public abstract void reduce(Action action);
}
