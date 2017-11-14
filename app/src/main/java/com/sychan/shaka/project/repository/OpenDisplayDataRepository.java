package com.sychan.shaka.project.repository;

import android.content.Context;
import android.os.Bundle;

import com.sychan.shaka.project.config.Constants;
import com.sychan.shaka.project.config.SimpleBackHelper;
import com.sychan.shaka.project.config.SimpleBackPage;
import com.sychan.shaka.project.entity.model.ReleaseTask;

/**
 * Created by sychan on 2017-11-13.
 * Function：
 */
public class OpenDisplayDataRepository {

    /**
     * 任务详情页显示
     *
     * @param context
     * @param task
     */
    public static void openOrderDisplayData(Context context, ReleaseTask task) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_RELEASE_TASK, task);
        SimpleBackHelper.showSimpleBack(context, SimpleBackPage.ORDER_DETAIL, bundle);
    }
}
