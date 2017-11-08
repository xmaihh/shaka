package com.sychan.shaka.app.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.orhanobut.logger.Logger;
import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.ImagePickerAdapter;
import com.sychan.shaka.project.config.Constants;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.widget.MultiRadioGroupAuto;
import com.wx.base.app.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_PREVIEW;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_SELECT;

public class NewTaskFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.multi_radio_group)
    MultiRadioGroupAuto rgp;
    @BindView(R.id.image_gallery_view)
    RecyclerView imageGalleryView;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private ImagePickerAdapter adapter;
    //当前选择的所有图片
    private ArrayList<ImageItem> selImageList;
    //允许选择图片最大数
    private int maxImgCount = 9;

    private String[] loanList;
    private String[] loanFeeList;
    private List<String> loanAndFeeList;
    ArrayList<ImageItem> images = null;


    @Override
    protected int getLayout() {
        return R.layout.fragment_new_task;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRadioButton();


        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        imageGalleryView.setLayoutManager(new GridLayoutManager(mContext, 4));
        imageGalleryView.setHasFixedSize(true);
        imageGalleryView.setAdapter(adapter);
    }


    /**
     * 动态添加RadioButton
     */
    private void initRadioButton() {
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        loanAndFeeList = new ArrayList<>();
        loanList = "800,1000,1600,200,300,500,700".split(",");
        loanFeeList = "50,80,100,20,30,50,70".split(",");

        //求最大最小值 (为了保持RadioButton文字长度一致,跟最长的保持一致!)
        int max = Integer.parseInt(loanList[0]);
        int min = Integer.parseInt(loanList[0]);
        for (String i : loanList) {
            int j = Integer.parseInt(i);
            max = max > j ? max : j;
            min = min < j ? min : j;
        }

        String maxS = String.valueOf(max);
        int maxLen = maxS.length();

        for (int i = 0; i < loanList.length; i++) {
            loanAndFeeList.add(loanList[i] + "," + loanFeeList[i]);
        }

        int len = loanAndFeeList.size();

        for (int j = 0; j < len; j++) {

            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setPadding(20, 0, screenWidth / 6, 0);  // 设置文字距离按钮四周的距离
//            radioButton.setButtonDrawable(R.drawable.ic_radio_button_checked_black_24dp);

            String newLoanList = loanList[j];
            if (loanList[j].length() < maxLen) {
                newLoanList = newLoanList + appendLength(maxLen - loanList[j].length());

                // 实现 TextView同时显示两种风格文字 http://txlong-onz.iteye.com/blog/1142781
                SpannableStringBuilder sb = new SpannableStringBuilder(newLoanList);
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.WHITE);
                sb.setSpan(fcs, loanList[j].length(), maxLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                radioButton.setText(sb);
            } else {
                newLoanList = loanList[j];
                radioButton.setText(newLoanList);
            }

            radioButton.setId(j); //设置RadioButton的id
            radioButton.setTag(loanAndFeeList.get(j)); //tag用于存储RadioButton的一些数据
            radioButton.setTextSize(13); //默认单位是 sp
            radioButton.setHeight(50); //默认单位是px
            rgp.addView(radioButton); //添加到RadioGroup中

        }

        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton clickRadioButton = (RadioButton) group.findViewById(checkedId);

                String tipInfo = "id: " + clickRadioButton.getId() + " text: " + clickRadioButton.getText() +
                        /*"hint: " + clickRadioButton.getHint() +*/ " tag:" + clickRadioButton.getTag();
                System.out.println(tipInfo);

                ToastUtil.show(tipInfo);
            }
        });

        //根据这个来设置默认选中的项, 注意,这个要设置在监听之后!,否则默认点击监听不到!虽然有选中效果
        //参考 http://blog.csdn.net/lzqjfly/article/details/16963645
        //以及http://stackoverflow.com/questions/9175635/how-to-set-radio-button-checked-as-default-in-radiogroup-with-android
        rgp.check(0);
    }

    /**
     * 补全长度,保持最长的长度
     *
     * @param count 字符串长度
     * @return 补全后的长度
     * 这里长度不够的就用 "s" 占位, 赋值的时候将字体设置白色!
     */
    public String appendLength(int count) {
        String st = "";
        if (count < 0) {
            count = 0;
        }
        for (int i = 0; i < count; i++) {
            st = st + "s";
        }
        return st;
    }

    @Override

    public void onItemClick(View view, int position) {
        switch (position) {
            case Constants.IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                getActivity().startActivityForResult(intent1, Constants.REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                getActivity().startActivityForResult(intentPreview, Constants.REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    Logger.d("onActivityResult" + selImageList.size());
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }



    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        Logger.d("onClick");
        if (images != null) {
            Logger.d(images.size());
        }

    }
}
