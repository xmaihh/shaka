package com.sychan.shaka.support.utils;

import android.widget.Toast;

import com.sychan.shaka.App;

public class ToastUtil {
    private static Toast toast;

    public static void show(String content) {
        if (toast == null) {
            toast = Toast.makeText(App.getAppContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
