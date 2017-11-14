package com.sychan.shaka.support.utils;

import android.content.Context;

import com.sychan.shaka.support.widget.TextMagnifyLoadingDialog;

/**
 * Created by sychan on 2017-11-13.
 * Function：
 */
public class ShowTextMagnifyUtil {
    /**
     * 双击显示文字放大效果
     *
     * @param mContext
     * @param text
     */
    public static void showTextOnDblclick(Context mContext, String text) {
        int count = 0;
        int firClick = 0;
        int secClick = 0;
        count++;
        if (count == 1) {
            firClick = (int) System.currentTimeMillis();
        } else if (count == 2) {
            secClick = (int) System.currentTimeMillis();
            if (secClick - firClick < 500) {
                // 双击事件
                TextMagnifyLoadingDialog dialog = new TextMagnifyLoadingDialog(
                        mContext, text);
                dialog.show();
            }
        }
    }

    /**
     * 单击显示文字放大效果
     *
     * @param mContext
     * @param text
     */
    public static void showTextOnclick(Context mContext, String text) {
        TextMagnifyLoadingDialog dialog = new TextMagnifyLoadingDialog(
                mContext, text);
        dialog.show();
    }
}
