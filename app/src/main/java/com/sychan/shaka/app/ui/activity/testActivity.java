package com.sychan.shaka.app.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import com.sychan.shaka.R;
import com.sychan.shaka.support.utils.ImageCompress;
import com.wx.base.app.ui.activity.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sychan on 2017-11-29.
 * Function：
 */
public class testActivity extends BaseActivity {
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        String filePath = "/storage/emulated/0/image.jpg";
        long newName = System.currentTimeMillis();
        String targePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shaka/" + newName + ".jpg";
        Log.d("521", "onViewClicked: " + xxSize(filePath));
        String s = ImageCompress.compressImage(filePath, targePath, 30);
        Log.d("521", "onViewClicked: " + xxSize(s));

    }

    private String xxSize(String filePath) {
        File dF = new File(filePath);
        FileInputStream fis;
        int fileLen = 0;
        try {
            fis = new FileInputStream(dF);
            fileLen = fis.available(); //这就是文件大小
            Log.d("521", filePath + "\n " + fileLen);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLen + "\t";

    }
}