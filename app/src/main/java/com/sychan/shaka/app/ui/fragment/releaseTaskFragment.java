package com.sychan.shaka.app.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sychan.shaka.R;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.widget.MultiRadioGroupAuto;
import com.wx.base.app.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sychan on 2017-11-07.
 * Function：
 */
public class releaseTaskFragment extends BaseFragment {


    @BindView(R.id.multi_radio_group)
    MultiRadioGroupAuto multiRadioGroup;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_raise_price)
    TextView tvRaisePrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.edit_dskb_dhz)
    EditText editDskbdhz;
    @BindView(R.id.edit_voter)
    EditText editVoter;
    @BindView(R.id.edit_link)
    EditText editLink;
    @BindView(R.id.edit_remark)
    EditText editRemark;
    @BindView(R.id.image_gallery_view)
    RecyclerView imageGalleryView;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    private String[] loanList;
    private String[] loanFeeList;
    private List<String> loanAndFeeList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_release_task;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRadioButton();
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
            multiRadioGroup.addView(radioButton); //添加到RadioGroup中

        }

        multiRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

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
            case R.id.multi_radio_group:
                break;
            case R.id.tv_unit_price:
                break;
            case R.id.tv_raise_price:
                break;
            case R.id.tv_count:
                break;
            case R.id.tv_total_price:
                break;
            case R.id.edit_dskb_dhz:
                break;
            case R.id.edit_voter:
                break;
            case R.id.edit_link:
                break;
            case R.id.edit_remark:
                break;
            case R.id.image_gallery_view:
                break;
            case R.id.btn_submit:
                break;
            default:
                break;
        }
    }
}
