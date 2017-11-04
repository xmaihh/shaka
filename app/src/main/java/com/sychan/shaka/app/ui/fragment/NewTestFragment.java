package com.sychan.shaka.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.ImagePickerAdapter;
import com.sychan.shaka.project.entity.model.Task;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.utils.ToastUtil;
import com.wx.base.app.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.sychan.shaka.project.config.Constants.IMAGE_ITEM_ADD;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_PREVIEW;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_SELECT;

public class NewTestFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.image_gallery_view)
    RecyclerView recyclerView;
    @BindView(R.id.buttonPanel)
    Button buttonPanel;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    @Override
    protected int getLayout() {
        return R.layout.fragment_new_test;
    }


    @Override
    protected void initViews() {
        super.initViews();
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:

                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
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

    ArrayList<ImageItem> images = null;

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

    @OnClick(R.id.buttonPanel)
    public void onViewClicked() {

        if (selImageList != null && selImageList.size() > 0) {
            final String[] filePaths = new String[selImageList.size()];
            for (int i = 0; i < selImageList.size(); i++) {
                filePaths[i] = selImageList.get(i).path;
            }
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                        //do something
                        Task task = new Task();
                        task.setFiles(files);
                        task.setContent("内容内容");
                        task.setTitle("标题标题");
                        task.setPublisher(BmobUser.getCurrentUser(User.class));
                        task.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    ToastUtil.show("创建数据成功：");
                                } else {
                                    ToastUtil.show("失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }

                        });
                        ToastUtil.show("图片上传成功");
                    }
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    ToastUtil.show("错误码" + statuscode + ",错误描述：" + errormsg);
                }
            });
        }


    }
}
