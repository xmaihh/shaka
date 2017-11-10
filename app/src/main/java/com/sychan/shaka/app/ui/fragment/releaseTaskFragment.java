package com.sychan.shaka.app.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.ImagePickerAdapter;
import com.sychan.shaka.project.config.Constants;
import com.sychan.shaka.project.config.orderType;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.entity.model.Task;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.utils.PreferenceUtils;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.utils.loadingUtil;
import com.sychan.shaka.support.widget.AmountView;
import com.sychan.shaka.support.widget.MultiRadioGroupAuto;
import com.wx.base.app.ui.fragment.BaseFragment;
import com.wx.base.support.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.sychan.shaka.project.config.Constants.ACKNOW_ORDER;
import static com.sychan.shaka.project.config.Constants.CLOSING_DEAL;
import static com.sychan.shaka.project.config.Constants.CURRENT_TYPE;
import static com.sychan.shaka.project.config.Constants.IMAGE_ITEM_ADD;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_PREVIEW;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_SELECT;
import static com.sychan.shaka.project.config.Constants.SUBMIT_ORDER;
import static com.sychan.shaka.project.config.Constants.raisePrice;

/**
 * Created by sychan on 2017-11-07.
 * Function：
 */
public class releaseTaskFragment extends BaseFragment implements
        ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.multi_radio_group)
    MultiRadioGroupAuto multiRadioGroup;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_raise_price)
    TextView tvRaisePrice;
    @BindView(R.id.tv_count)
    AmountView tvCount;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.cb_raise_price)
    CheckBox cbRaiseprice;
    @BindView(R.id.edit_dskb_dhz)
    EditText editDskbdhz;
    @BindView(R.id.edit_voter)
    EditText editVoter;
    @BindView(R.id.edit_link)
    EditText editLink;
    @BindView(R.id.edit_remark)
    EditText editRemark;
    @BindView(R.id.image_gallery_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    ArrayList<ImageItem> images = null;

    @BindString(R.string.rb_release_focus)
    String rbFocus;
    @BindString(R.string.rb_release_pilot_vote)
    String rbPilotvote;
    @BindString(R.string.rb_release_tpos_vote)
    String rbTposvote;
    @BindString(R.string.rb_release_pviews)
    String rbPviews;
    @BindString(R.string.rb_release_pviews_thumbs)
    String rbPviewsthumbs;
    @BindString(R.string.rb_release_pviews_thumbs_reviews)
    String rbPviewsthumbsreviews;

    private String[] loanList;
    private String[] loanFeeList;
    private List<String> loanAndFeeList;

    private boolean isVipchecked = false;
    private int amountCount = 1;
    private int totalPrice = 0;
    private loadingUtil lodingdialog;

    @Override
    protected int getLayout() {
        usePageState = true;
        return R.layout.fragment_release_task;
    }

    @Override
    protected void initViews() {
        super.initViews();
        cbRaiseprice.setOnCheckedChangeListener(new CheckBoxListener());
        tvCount.setOnAmountChangeListener(new AmountChangeListener());
        initRadioButton();
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        tvCount.setGoods_storage(1000);
        lodingdialog = new loadingUtil(mContext, R.style.progress_dialog_style);
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
        loanList = new String[]{rbFocus, rbPilotvote, rbTposvote, rbPviews, rbPviewsthumbs, rbPviewsthumbsreviews};
        loanFeeList = "50,80,100,20,30,50".split(",");

        //求最大最小值 (为了保持RadioButton文字长度一致,跟最长的保持一致!)
        int max = loanList[0].length();
        int min = loanList[0].length();
        for (String i : loanList) {
            int j = i.length();
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
            radioButton.setPadding(20, 10, screenWidth / 6, 10);  // 设置文字距离按钮四周的距离
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
            radioButton.setTextSize(DisplayUtil.px2sp(mContext, getResources().getDimension(R.dimen.textsize_m))); //默认单位是 sp
            radioButton.setTextColor(getResources().getColor(R.color.colorSubtext));
            radioButton.setHeight(80); //默认单位是px
            multiRadioGroup.addView(radioButton); //添加到RadioGroup中

        }

        multiRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton clickRadioButton = (RadioButton) group.findViewById(checkedId);

                String tipInfo = "id: " + clickRadioButton.getId() + " text: " + clickRadioButton.getText() +
                        /*"hint: " + clickRadioButton.getHint() +*/ " tag:" + clickRadioButton.getTag();
                System.out.println(tipInfo);

                switch (clickRadioButton.getId()) {
                    case 0:
                        int tp_focus = orderType.TP_FOCUS.getType();
                        tvUnitPrice.setText(String.valueOf(tp_focus));
                        totalPrice = (tp_focus + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_focus);
                        break;
                    case 1:
                        int tp_pilotvote = orderType.TP_PILOTVOTE.getType();
                        tvUnitPrice.setText(String.valueOf(tp_pilotvote));
                        totalPrice = (tp_pilotvote + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_pilotvote);
                        break;
                    case 2:
                        int tp_tposvote = orderType.TP_TPOSVOTE.getType();
                        tvUnitPrice.setText(String.valueOf(tp_tposvote));
                        totalPrice = (tp_tposvote + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_tposvote);
                        break;
                    case 3:
                        int tp_pviews = orderType.TP_PVIEWS.getType();
                        tvUnitPrice.setText(String.valueOf(tp_pviews));
                        totalPrice = (tp_pviews + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_pviews);
                        break;
                    case 4:
                        int tp_pviewsthumbs = orderType.TP_PVIEWSTHUMBS.getType();
                        tvUnitPrice.setText(String.valueOf(tp_pviewsthumbs));
                        totalPrice = (tp_pviewsthumbs + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_pviewsthumbs);
                        break;
                    case 5:
                        int tp_pviewsthumbsreviews = orderType.TP_PVIEWSTHUMBSREVIEWS.getType();
                        tvUnitPrice.setText(String.valueOf(tp_pviewsthumbsreviews));
                        totalPrice = (tp_pviewsthumbsreviews + (isVipchecked ? raisePrice : 0)) * amountCount;
                        tvTotalPrice.setText(String.valueOf(totalPrice));
                        PreferenceUtils.setPrefInt(mContext, CURRENT_TYPE, tp_pviewsthumbsreviews);
                        break;
                    default:
                        break;
                }
            }
        });

        //根据这个来设置默认选中的项, 注意,这个要设置在监听之后!,否则默认点击监听不到!虽然有选中效果
        //参考 http://blog.csdn.net/lzqjfly/article/details/16963645
        //以及http://stackoverflow.com/questions/9175635/how-to-set-radio-button-checked-as-default-in-radiogroup-with-android
        multiRadioGroup.check(0);
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

    @OnClick({R.id.multi_radio_group, R.id.tv_unit_price, R.id.tv_raise_price, R.id.tv_count, R.id.tv_total_price, R.id.edit_dskb_dhz, R.id.edit_voter, R.id.edit_link, R.id.edit_remark, R.id.image_gallery_view, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                ToastUtil.show(" 11 2'34'55+");
                mHandler.obtainMessage(SUBMIT_ORDER).sendToTarget();
                lodingdialog.show();
                btnSubmit.setEnabled(false);
                btnSubmit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSubmit.setEnabled(true);
                    }
                }, 500);
                break;
            default:
                break;
        }
    }

    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isVipchecked = isChecked;
            int currentType = PreferenceUtils.getPrefInt(mContext, CURRENT_TYPE, orderType.TP_FOCUS.getType());
            totalPrice = (currentType + (isVipchecked ? raisePrice : 0)) * amountCount;
            tvTotalPrice.setText(String.valueOf(totalPrice));
            ToastUtil.show("checkBox:" + isChecked);
        }
    }

    class AmountChangeListener implements AmountView.OnAmountChangeListener {

        @Override
        public void onAmountChange(View view, int amount) {
            amountCount = amount;
            int currentType = PreferenceUtils.getPrefInt(mContext, CURRENT_TYPE, orderType.TP_FOCUS.getType());
            totalPrice = (currentType + (isVipchecked ? raisePrice : 0)) * amountCount;
            tvTotalPrice.setText(String.valueOf(totalPrice));
            ToastUtil.show("amount:" + amount);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUBMIT_ORDER:
                    //确认订单
                    acknowOrder();
                    break;
                case ACKNOW_ORDER:
                    //提交订单
                    submitOrder();
                    break;
                case CLOSING_DEAL:
                    //关闭交易
                    lodingdialog.dismiss();
                    break;
                default:
                    break;

            }
        }
    };

    private void acknowOrder() {
        boolean pass = true;
        if (amountCount == 0) {
            ToastUtil.show(getString(R.string.tv_release_count)
                    + getString(R.string.tips_data_invalid));
            pass = false;
        }
        if (TextUtils.isEmpty(editDskbdhz.getText())) {
            ToastUtil.show(getString(R.string.tv_release_company)
                    + getString(R.string.tips_data_invalid));
            pass = false;
        }
        if (TextUtils.isEmpty(editVoter.getText())) {
            ToastUtil.show(getString(R.string.tv_release_voter)
                    + getString(R.string.tips_data_invalid));
            pass = false;
        }
        if (TextUtils.isEmpty(editLink.getText())) {
            ToastUtil.show(getString(R.string.tv_release_link)
                    + getString(R.string.tips_data_invalid));
            pass = false;
        }
        if (null == selImageList || selImageList.size() == 0) {
            ToastUtil.show(
                    getString(R.string.tips_upload_image));
            pass = false;
        }
        mHandler.obtainMessage(pass ? ACKNOW_ORDER : CLOSING_DEAL).sendToTarget();
    }

    private void submitOrder() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final String[] filePaths = new String[selImageList.size()];
                for (int i = 0; i < selImageList.size(); i++) {
                    filePaths[i] = selImageList.get(i).path;
                }
                BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                        //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                        //2、urls-上传文件的完整url地址
                        if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成java.util.Calendar cal=java.util.Calendar.getInstance();
                            ReleaseTask task = new ReleaseTask();
                            task.setPublisher(BmobUser.getCurrentUser(User.class));
                            int type = PreferenceUtils.getPrefInt(mContext, CURRENT_TYPE, orderType.TP_FOCUS.getType());
                            task.setType(type);
                            task.setUnitprice(type);
                            task.setRaiseprice(Constants.raisePrice);
                            task.setCount(amountCount);
                            task.setTotalprice(totalPrice);
                            task.setPublicaccounts(editDskbdhz.getText().toString());
                            task.setVoter(editVoter.getText().toString());
                            task.setUrl(editLink.getText().toString());
                            task.setRemark(editRemark.getText().toString());
                            task.setFiles(files);
                            task.setCreatedat(new Date());
                            java.util.Calendar cal = java.util.Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DATE, 1);
                            task.setDeadline(cal.getTime());
                            task.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        mHandler.obtainMessage(CLOSING_DEAL).sendToTarget();
                                        ToastUtil.show(getString(R.string.toast_success));
                                        finish();
                                    } else {
                                        ToastUtil.show(getString(R.string.toast_error_describe) + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total,
                                           int totalPercent) {
                        //1、curIndex--表示当前第几个文件正在上传
                        //2、curPercent--表示当前上传文件的进度值（百分比）
                        //3、total--表示总的上传文件数
                        //4、totalPercent--表示总的上传进度（百分比）
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        ToastUtil.show(getString(R.string.toast_error_code) + statuscode + "," + getString(R.string.toast_error_describe) + errormsg);
                    }
                });

            }
        });
    }

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        lodingdialog = null;
        super.onDestroy();
    }
}