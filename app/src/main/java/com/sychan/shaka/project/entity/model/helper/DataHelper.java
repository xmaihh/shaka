package com.sychan.shaka.project.entity.model.helper;

import android.util.Log;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by sychan on 2017-11-11.
 * Function：
 */
public class DataHelper {

    public static List<ReleaseTask> getReleaseTask(int page) {

        final List<ReleaseTask> items = new ArrayList<>();
        BmobQuery<ReleaseTask> query = new BmobQuery<>();
        query.addWhereEqualTo("type", 100);
        query.setLimit(5);
        query.findObjects(new FindListener<ReleaseTask>() {
            @Override
            public void done(List<ReleaseTask> list, BmobException e) {
                if (e == null) {
                    Log.d("521", "done: " + list.size());
                    items.addAll(list);
                    Log.d("521", "done:+++++ items" + items.size());
                } else {
                    Log.i("521" , e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        return items;
    }

}
