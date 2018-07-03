package com.zhixing.work.zhixin.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.UploadImageFinishEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class IdAuthenticationActivity extends BaseTitleActivity {
    //身份证验证
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

    public static final int REQUEST_CAMERA = 106;
    private int type;
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> imagesList = new ArrayList<AlbumItem>();
    private AlbumItem cardA;
    private AlbumItem cardB;
    private AlbumItem cardC;
    private int isUploadCount;
    private String upLoadImages;//上传图片组
    private List<AlbumItem> upImages = new ArrayList<>();
    private File photoFile;
    private String photoPath;

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


                if (null != imagesList && imagesList.size() > 1) {
                    final List<String> list = new ArrayList<String>();
                    for (int i = 0; i < imagesList.size(); i++) {
                        AlbumItem albumItem = imagesList.get(i);
                        if (null != albumItem) {
                            list.add(albumItem.getFilePath());
                        }
                    }
                    compressWithLs(list);
                } else {
                    AlertUtils.toast(context, "还未选择图片");
                }


            }
        });
    }


    //压缩图片
    //* 压缩图片 Listener 方式

    private void compressWithLs(final List<String> photos) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Luban.with(context)
                        .load(photos)
                        .ignoreBy(100)
                        .setTargetDir(getPath())
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                showLoadingDialog("上传中...");
                            }

                            @Override
                            public void onSuccess(File file) {
                                Log.i(TAG, file.getAbsolutePath());
                                showResult(photos, file);
                            }

                            @Override
                            public void onError(Throwable e) {

                                Logger.d("错误", e.toString());
                            }
                        }).launch();

            }
        }).start();

    }

    private class uploadRunnable implements Runnable {

        @Override
        public void run() {
            isUploadCount = 0;
            for (int i = 0; i < upImages.size(); i++) {
                AlbumItem albumItem = upImages.get(i);
                if (null != albumItem) {
                    upload(albumItem,i);
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

    private void upload(final AlbumItem albumItem, final int index) {
        String thumbnail = albumItem.getFilePath();
        String resultpath = thumbnail;
        String UUID = AppUtils.getUUID();

        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.PERSONALIDCARD + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        isUploadCount++;

                        if (index == 0) {
                            upLoadImages = "";
                            upLoadImages = objectKey;
                        } else {
                            upLoadImages = upLoadImages + "," + objectKey;
                        }
                        String path = Environment.getExternalStorageDirectory() + "/zhixin/image/";
                        if (isUploadCount == 3) {
                            hideLoadingDialog();
                            upImages.clear();
                            deleteDir(path);
                            EventBus.getDefault().post(new UploadImageFinishEvent(upLoadImages));
                        }
                        Logger.i(TAG, "动态图片上传成功：" + objectKey);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {

                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {
                        hideLoadingDialog();
                        upImages.clear();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertUtils.toast(context, "动态图片上传失败");
                            }
                        });
                        Logger.i(TAG, "动态图片上传失败：" + objectKey);
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
            Logger.i(TAG, "拍照后旋转的角度：" + rotateDegree);
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


    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/zhixin/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


    private void showResult(List<String> photos, File file) {

        int[] thumbSize = computeSize(file.getAbsolutePath());
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);
        Logger.d("图", thumbArg);
        AlbumItem albumItem = new AlbumItem();
        albumItem.setFilePath(file.getAbsolutePath());
        upImages.add(albumItem);
        if (upImages.size() == photos.size()) {
            new Thread(new uploadRunnable()).start();
        }
    }

    private int[] computeSize(String srcImg) {
        int[] size = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg, options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        return size;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        //dir.delete();// 删除目录本身
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTradeAreaEvent(UploadImageFinishEvent event) {
        AlertUtils.toast(context, "资料上传成功,请耐心等待审核");
        finish();
    }
}
