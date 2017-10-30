package com.wx.base.support.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by alex on 16-12-19.
 */

public class ActivityUtil {

    public static void startActivity(Context context, Class clz){
        Intent intent = new Intent(context, clz);
        //intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        if(!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

}
