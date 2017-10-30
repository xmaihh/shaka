package com.wx.base.support.utils;

import java.util.List;

/**
 * Created by alex on 17-1-3.
 */

public class ListUtil {

    public static boolean isEmpty(List list){
        if(list != null && list.size() > 0){
            return false;
        }
        return true;
    }
}
