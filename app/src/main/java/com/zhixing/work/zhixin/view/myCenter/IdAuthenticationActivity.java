package com.zhixing.work.zhixin.view.myCenter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.LOG;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdAuthenticationActivity extends BaseTitleActivity {


    @BindView(R.id.prompt_hold)
    LinearLayout promptHold;
    @BindView(R.id.rl_hold_id)
    LinearLayout rlHoldId;
    @BindView(R.id.positive_prompt)
    LinearLayout positivePrompt;
    @BindView(R.id.rl_positive_id)
    LinearLayout rlPositiveId;
    @BindView(R.id.back_prompt)
    LinearLayout backPrompt;
    @BindView(R.id.rl_back_id)
    LinearLayout rlBackId;
    @BindView(R.id.hold_iv)
    ImageView holdIv;
    @BindView(R.id.positive_iv)
    ImageView positiveIv;
    @BindView(R.id.back_iv)
    ImageView backIv;
    private StsToken stsToken;
    private File photoFile;
    private String photoPath;

    public static final int REQUEST_CAMERA = 106;
    private int type;
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> imagesList = new ArrayList<AlbumItem>();
    private AlbumItem cardA;
    private AlbumItem cardB;
    private AlbumItem cardC;
    private int isUploadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_authentication);
        ButterKnife.bind(this);
        setTitle("身份认证");
        getOssToken();
        setRightText1("上传");
        initView();

    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardA == null) {
                    AlertUtils.toast(context, "请上传手持身份证");
                    return;
                }
                if (cardB == null) {
                    AlertUtils.toast(context, "请上传身份证正面");
                    return;
                }
                if (cardC == null) {
                    AlertUtils.toast(context, "请上传身份证反面");
                    return;
                }
                imagesList.clear();
                imagesList.add(cardA);
                imagesList.add(cardB);
                imagesList.add(cardC);


                showLoadingDialog("上传中...");
                new Thread(new uploadRunnable()).start();
            }
        });
    }

    private class uploadRunnable implements Runnable {

        @Override
        public void run() {
            isUploadCount = 0;
            for (int i = 0; i < imagesList.size(); i++) {
                AlbumItem albumItem = imagesList.get(i);
                if (null != albumItem) {
                    upload(albumItem);
                }
            }

        }
    }

    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.getOSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == 10000) {
                    stsToken = response.getContent();
                }
            }
        });
    }

    private void upload(final AlbumItem albumItem) {
        String thumbnail = albumItem.getFilePath();
        String resultpath = thumbnail;
        try {
            resultpath = BitmapUtils.bitmapCompress(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.PERSONALIDCARD + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        isUploadCount++;
                        if (isUploadCount==3){
                            hideLoadingDialog();
                            AlertUtils.toast(context,"上传成功");
                        }
                        LOG.i(TAG, "动态图片上传成功：" + objectKey);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {

                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {
                        hideLoadingDialog();
                        LOG.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }

    @OnClick({R.id.rl_hold_id, R.id.rl_positive_id, R.id.rl_back_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_hold_id:
                type = 0;
                SelectImageDialog dialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                takeAPicture();
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_DYNAMIC);
                                break;
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.rl_positive_id:
                SelectImageDialog selectImageDialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                takeAPicture();
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_DYNAMIC);
                                break;
                        }
                    }
                });
                selectImageDialog.show();
                type = 1;
                break;
            case R.id.rl_back_id:
                SelectImageDialog imageDialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                takeAPicture();
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_DYNAMIC);
                                break;
                        }
                    }
                });
                imageDialog.show();
                type = 2;
                break;
        }
    }

    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + "/" + AppUtils.getUUID();
        photoFile = new File(photoPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == SelectImageActivity.REQUEST_DYNAMIC) {
            //通过相册选择图片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            for (int i = 0; i < selectedImages.size(); i++) {
                if (type == 0) {
                    cardA = selectedImages.get(i);
                    holdIv.setVisibility(View.VISIBLE);
                    if (cardA.getThumbnail().isEmpty()) {
                        Glide.with(context).load(cardA.getThumbnail()).into(holdIv);
                    } else {
                        Glide.with(context).load(cardA.getFilePath()).into(holdIv);
                    }
                } else if (type == 1) {
                    positiveIv.setVisibility(View.VISIBLE);
                    cardB = selectedImages.get(i);
                    if (cardB.getThumbnail().isEmpty()) {
                        Glide.with(context).load(cardB.getThumbnail()).into(positiveIv);
                    } else {
                        Glide.with(context).load(cardB.getFilePath()).into(positiveIv);
                    }
                } else {
                    backIv.setVisibility(View.VISIBLE);
                    cardC = selectedImages.get(i);
                    if (cardC.getThumbnail().isEmpty()) {
                        Glide.with(context).load(cardC.getThumbnail()).into(backIv);
                    } else {
                        Glide.with(context).load(cardC.getFilePath()).into(backIv);
                    }

                }
            }


        } else if (requestCode == REQUEST_CAMERA) {
            //使用相机拍照
            final int rotateDegree = BitmapUtils.readPictureDegree(photoFile.getAbsolutePath());
            LOG.i(TAG, "拍照后旋转的角度：" + rotateDegree);
            AlbumItem image = new AlbumItem();
            image.setFilePath(photoFile.getAbsolutePath());
            image.setThumbnail(photoFile.getAbsolutePath());
            if (type == 0) {
                holdIv.setVisibility(View.VISIBLE);
                cardA = image;
                if (cardA.getThumbnail().isEmpty()) {
                    Glide.with(context).load(cardA.getThumbnail()).into(holdIv);
                } else {
                    Glide.with(context).load(cardA.getFilePath()).into(holdIv);
                }
            } else if (type == 1) {
                positiveIv.setVisibility(View.VISIBLE);
                cardB = image;
                if (cardB.getThumbnail().isEmpty()) {
                    Glide.with(context).load(cardB.getThumbnail()).into(positiveIv);
                } else {
                    Glide.with(context).load(cardB.getFilePath()).into(positiveIv);
                }
            } else {
                backIv.setVisibility(View.VISIBLE);
                cardC = image;
                if (cardC.getThumbnail().isEmpty()) {
                    Glide.with(context).load(cardC.getThumbnail()).into(backIv);
                } else {
                    Glide.with(context).load(cardC.getFilePath()).into(backIv);
                }
            }
        }
    }
}
