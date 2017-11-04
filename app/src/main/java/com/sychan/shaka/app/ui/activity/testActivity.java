package com.sychan.shaka.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.ImagePickerAdapter;
import com.sychan.shaka.project.config.Constants;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.widget.GlideImageLoader;
import com.sychan.shaka.support.widget.MultiRadioGroupAuto;
import com.sychan.shaka.support.widget.SelectDialog;
import com.wx.base.app.ui.activity.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

import static com.sychan.shaka.project.config.Constants.IMAGE_ITEM_ADD;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_PREVIEW;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_SELECT;


public class testActivity extends Activity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.multi_radio_group)
    MultiRadioGroupAuto rgp;
    @BindView(R.id.image_gallery_view)
    RecyclerView imageGalleryView;
    Unbinder unbinder;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        initRadioButton();
//        initImagePicker();

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        imageGalleryView.setLayoutManager(new GridLayoutManager(this, 3));
        imageGalleryView.setHasFixedSize(true);
        imageGalleryView.setAdapter(adapter);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    /**
     * 动态添加RadioButton
     */
    private void initRadioButton() {
//获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
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

            RadioButton radioButton = new RadioButton(this);
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


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(testActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(testActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
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

//        if (images != null) {
//            for (ImageItem imageItem : images) {
//                String s = imageItem.path;
//                Logger.d(s + "\n");
//            }
//            Logger.d("onClick" + images.size()+images.get(0).path);
//        }

        String picPath = "/storage/emulated/0/MagazineUnlock/magazine-unlock-03-2.3.787-_a47ccbdf81c54b18930d2db81994635b.jpg";
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    ToastUtil.show("上传文件成功:" + bmobFile.getFileUrl());
                } else {
                    ToastUtil.show("上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                //上传进度百分比
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
