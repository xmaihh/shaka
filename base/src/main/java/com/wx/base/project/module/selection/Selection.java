package com.wx.base.project.module.selection;

/**
 * Created by alex on 16-11-25.
 */

public abstract class Selection<T,V> {

    public abstract int getParentNodeState(T entity);
    public abstract boolean isLeafSelected(V entity);
    public abstract void triggerParentNode(T entity);
    public abstract void triggerLeafNode(V entity);
}
