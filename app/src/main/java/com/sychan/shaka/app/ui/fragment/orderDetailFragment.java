package com.sychan.shaka.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sychan.shaka.R;
import com.sychan.shaka.app.ui.adapter.ImagePickerAdapter;
import com.sychan.shaka.project.config.Constants;
import com.sychan.shaka.project.config.orderType;
import com.sychan.shaka.project.entity.model.OrderTake;
import com.sychan.shaka.project.entity.model.ReleaseTask;
import com.sychan.shaka.project.entity.model.User;
import com.sychan.shaka.support.utils.ClipboardUtil;
import com.sychan.shaka.support.utils.PreferenceUtils;
import com.sychan.shaka.support.utils.ShowTextMagnifyUtil;
import com.sychan.shaka.support.utils.ToastUtil;
import com.sychan.shaka.support.utils.loadingUtil;
import com.wx.base.app.ui.fragment.BaseFragment;
import com.wx.base.support.android.pagestate.StateView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static android.app.Activity.RESULT_CANCELED;
import static com.sychan.shaka.project.config.Constants.CLOSING_DEAL;
import static com.sychan.shaka.project.config.Constants.CURRENT_TYPE;
import static com.sychan.shaka.project.config.Constants.DOWNLOAD_IMAGE;
import static com.sychan.shaka.project.config.Constants.IMAGE_ITEM_ADD;
import static com.sychan.shaka.project.config.Constants.LOADING_IMAGE;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_PREVIEW;
import static com.sychan.shaka.project.config.Constants.REQUEST_CODE_SELECT;
import static com.sychan.shaka.project.config.Constants.SUBMIT_IT_NOW;

/**
 * Created by sychan on 2017-11-13.
 * Function：
 */
public class orderDetailFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.tv_dsk_bdhz)
    TextView tvDskBdhz;
    @BindView(R.id.tv_voter)
    TextView tvVoter;
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.image_proof)
    RecyclerView imageProof;
    @BindView(R.id.submit_image)
    TextView submitImage;
    @BindView(R.id.image_gallery_view)
    RecyclerView imageGalleryView;
    @BindView(R.id.btn_accept_it_now)
    Button btnAcceptItNow;
    @BindView(R.id.btn_submit_it_now)
    Button btnSubmitItNow;

    ReleaseTask task;
    private boolean accept;
    private loadingUtil lodingdialog;
    private ArrayList<ImageItem> filesPath;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    ArrayList<ImageItem> images = null;

    @Override
    protected void initBundle() {
        super.initBundle();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable(Constants.BUNDLE_RELEASE_TASK);
            Log.d(TAG, "bundle argument = " + parcelable.getClass().getSimpleName());
            if (parcelable instanceof ReleaseTask) {
                task = (ReleaseTask) parcelable;
            }
        }
    }

    @Override
    protected int getLayout() {
        usePageState = true;
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initViews() {
        super.initViews();
        lodingdialog = new loadingUtil(mContext, R.style.progress_dialog_style);
        accept = btnSubmitItNow.getVisibility() == View.VISIBLE;
        if (accept) {
            btnAcceptItNow.setVisibility(View.GONE);
            btnSubmitItNow.setVisibility(View.VISIBLE);
            submitImage.setVisibility(View.VISIBLE);
            imageGalleryView.setVisibility(View.VISIBLE);
        } else {
            btnAcceptItNow.setVisibility(View.VISIBLE);
            btnSubmitItNow.setVisibility(View.GONE);
            submitImage.setVisibility(View.GONE);
            imageGalleryView.setVisibility(View.GONE);
        }
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        imageGalleryView.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageGalleryView.setHasFixedSize(true);
        imageGalleryView.setAdapter(adapter);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        if (task == null) {
            pageStatePresenter.changeState(StateView.PageState.Empty);
        } else {
            tvDskBdhz.setText(getString(R.string.tv_order_detail_company) + task.getPublicaccounts());
            tvVoter.setText(getString(R.string.tv_order_detail_voter) + task.getVoter());
            tvLink.setText(getString(R.string.tv_order_detail_link) + task.getUrl());
            tvPay.setText(getString(R.string.tv_order_detail_pay) + String.valueOf(task.getTotalprice()));
            tvRemark.setText(getString(R.string.tv_order_detail_remark) + task.getRemark());
            mHandler.obtainMessage(DOWNLOAD_IMAGE).sendToTarget();
        }
    }

    @OnClick({R.id.tv_link, R.id.tv_remark, R.id.btn_accept_it_now, R.id.btn_submit_it_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_link:
                if (task != null) {
                    ClipboardUtil.copy(task.getUrl(), mContext);
                    ToastUtil.show(getString(R.string.toast_on_the_clipboard));
                }
                break;
            case R.id.tv_remark:
                if (task != null) {
                    ShowTextMagnifyUtil.showTextOnclick(mContext, task.getRemark());
                }
                break;
            case R.id.btn_accept_it_now:
                btnAcceptItNow.setVisibility(View.GONE);
                btnSubmitItNow.setVisibility(View.VISIBLE);
                submitImage.setVisibility(View.VISIBLE);
                imageGalleryView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit_it_now:
                mHandler.obtainMessage(SUBMIT_IT_NOW).sendToTarget();
                break;
            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_IMAGE:
                    downloadImage();
                    break;
                case LOADING_IMAGE:
                    loadingImage();
                    break;
                case SUBMIT_IT_NOW:
                    submitItnow();
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

    private void downloadImage() {
        Log.d("521", "downloadImage: ");
        lodingdialog.show();
        final List<BmobFile> files = task.getFiles();
        final int size = files.size();

        filesPath = new ArrayList<>();
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        for (BmobFile file : files) {
//            File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
            Log.d("521", "downloadImage: " + file.getUrl());
            BmobFile bmobfile = new BmobFile(file.getFilename(), file.getGroup(), file.getUrl());
            bmobfile.download(new DownloadFileListener() {
                @Override
                public void done(String savePath, BmobException e) {
                    Log.d("521", "done: ");
                    if (e == null) {
                        ImageItem item = new ImageItem();
                        item.path = savePath;
                        filesPath.add(item);
                        if (filesPath.size() == size) {
                            mHandler.obtainMessage(LOADING_IMAGE).sendToTarget();
                        }
                    } else {
                        Log.d(TAG, "done: " + e.getMessage() + e.getErrorCode());
                        ToastUtil.show(getString(R.string.toast_error_describe) + e.getMessage() + "," + e.getErrorCode());
                    }
                }

                @Override
                public void onProgress(Integer value, long newworkSpeed) {
                    Log.i("image", "下载进度：" + value + "," + newworkSpeed);
                }
            });
        }
    }

    private void loadingImage() {
        final ImagePickerAdapter proofAdapter = new ImagePickerAdapter(mContext, filesPath, 9);
        imageProof.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageProof.setHasFixedSize(true);
        imageProof.setAdapter(proofAdapter);
        proofAdapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case IMAGE_ITEM_ADD:
//                        //打开选择,本次允许选择的数量
//                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                        Intent intent1 = new Intent(mContext, ImageGridActivity.class);
//                        getActivity().startActivityForResult(intent1, REQUEST_CODE_SELECT);
                        break;
                    default:
                        //打开预览
                        Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) proofAdapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                        getActivity().startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                        break;
                }
            }
        });
        mHandler.obtainMessage(CLOSING_DEAL).sendToTarget();
    }

    private void submitItnow() {
        if (null == selImageList || selImageList.size() == 0) {
            ToastUtil.show(
                    getString(R.string.tips_upload_image));
        } else {
            lodingdialog.show();
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
                                OrderTake take = new OrderTake();
                                take.setUser(BmobUser.getCurrentUser(User.class));
                                take.setFiles(files);
                                take.setFinishDate(new Date());
                                ReleaseTask releasetask = task;
                                take.setTaskId(releasetask.getObjectId());
                                take.setTask(releasetask);
                                take.save(new SaveListener<String>() {
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
    }

    private void downloadFile(BmobFile file) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Log.d("521", "开始下载...");
            }

            @Override
            public void done(String savePath, BmobException e) {
                if (e == null) {
                    Log.d("521", "下载成功,保存路径:" + savePath);
                } else {
                    Log.d("521", "下载失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("521", "下载进度：" + value + "," + newworkSpeed);
            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                getActivity().startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                getActivity().startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                //添加图片返回
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        Log.d("521", "images: 数量" + images.size());
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
    }

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }


}
