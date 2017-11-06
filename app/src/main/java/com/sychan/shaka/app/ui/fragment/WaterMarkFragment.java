package com.sychan.shaka.app.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sychan.shaka.R;
import com.sychan.shaka.support.utils.ImageWatermark;
import com.wx.base.app.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class WaterMarkFragment extends BaseFragment {
    @BindView(R.id.sour_pic_title)
    TextView sourPicTitle;
    @BindView(R.id.sour_pic)
    ImageView mSourImage;
    @BindView(R.id.watermark_pic_title)
    TextView watermarkPicTitle;
    @BindView(R.id.wartermark_pic)
    ImageView mWartermarkImage;
    @BindView(R.id.btn_translate)
    Button btnTranslate;
    private Bitmap waterBitmap;
    private Bitmap sourBitmap;

    @Override
    protected int getLayout() {
        return R.layout.fragment_test_watermark;
    }

    @Override
    protected void initViews() {
        super.initViews();

        sourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sour_pic);
        mSourImage.setImageBitmap(sourBitmap);

        waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weixin);
        mWartermarkImage.setImageBitmap(waterBitmap);

    }

    @OnClick(R.id.btn_translate)
    public void onViewClicked() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap watermarkBitmap = ImageWatermark.createWaterMaskCenter(sourBitmap, waterBitmap);
                watermarkBitmap = ImageWatermark.createWaterMaskLeftBottom(mContext, watermarkBitmap, waterBitmap, 0, 0);
                watermarkBitmap = ImageWatermark.createWaterMaskRightBottom(mContext, watermarkBitmap, waterBitmap, 0, 0);
                watermarkBitmap = ImageWatermark.createWaterMaskLeftTop(mContext, watermarkBitmap, waterBitmap, 0, 0);
                watermarkBitmap = ImageWatermark.createWaterMaskRightTop(mContext, watermarkBitmap, waterBitmap, 0, 0);

                Bitmap textBitmap = ImageWatermark.drawTextToLeftTop(mContext, watermarkBitmap, "左上角", 16, Color.RED, 0, 0);
                textBitmap = ImageWatermark.drawTextToRightBottom(mContext, textBitmap, "右下角", 16, Color.RED, 0, 0);
                textBitmap = ImageWatermark.drawTextToRightTop(mContext, textBitmap, "右上角", 16, Color.RED, 0, 0);
                textBitmap = ImageWatermark.drawTextToLeftBottom(mContext, textBitmap, "左下角", 16, Color.RED, 0, 0);
                textBitmap = ImageWatermark.drawTextToCenter(mContext, textBitmap, "中间", 16, Color.RED);
                watermarkPicTitle.setText("加水印后");
                mWartermarkImage.setImageBitmap(textBitmap);
            }
        }, 500);
    }
}
