package com.wx.base.project.model.action;

import com.wx.base.project.module.predux.Action;

/**
 * Created by alex on 16-11-30.
 */

public class ItemChagneAction extends Action{

    public static final String TYPE_CHANGED = "TYPE_CHANGED";
    public static final String TYPE_INSERT = "TYPE_INSERT";
    public static final String TYPE_RANGE_CHANGED = "TYPE_RANGE_CHANGED";
    public static final String TYPE_REMOVED = "TYPE_REMOVED";
    public static final String TYPE_MOVED = "TYPE_MOVED";

    int AdapterType;

    int positionStart;
    int positionDest;
    int itemCount;

    public int getAdapterType() {
        return AdapterType;
    }

    public void setAdapterType(int adapterType) {
        AdapterType = adapterType;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(int positionStart) {
        this.positionStart = positionStart;
    }

    public int getPositionDest() {
        return positionDest;
    }

    public void setPositionDest(int positionDest) {
        this.positionDest = positionDest;
    }
}
