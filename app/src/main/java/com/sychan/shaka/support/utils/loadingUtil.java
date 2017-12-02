package com.sychan.shaka.support.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import com.sychan.shaka.R;

/**
 * Created by sychan on 2017-11-10.
 * Function：
 */
public class loadingUtil extends AlertDialog {

    private Context mContext;

    public loadingUtil(@NonNull Context context) {
        super(context, R.style.progress_dialog_style);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mContext = context;
    }

    public loadingUtil(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mContext = context;
    }

    @Override
    public synchronized void show() {
        super.show();
        setContentView(R.layout.view_loading_dialog);
    }

    @Override
    public synchronized void dismiss() {
        super.dismiss();
    }
}
