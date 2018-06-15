package com.zhixing.work.zhixin.view.companyCard;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
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
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.UploadImageFinishEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.LOG;
import com.zhixing.work.zhixin.view.authentication.IdAuthenticationActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
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
import okhttp3.FormBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PersonalQualificationActivity extends BaseTitleActivity {

    @BindView(R.id.name_ed)
    TextView nameEd;
    @BindView(R.id.name_right)
    ImageView nameRight;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.id_ed)
    TextView idEd;
    @BindView(R.id.id_right)
    ImageView idRight;
    @BindView(R.id.rl_id)
    RelativeLayout rlId;
    @BindView(R.id.id_photo)
    ImageView idPhoto;
    private int isUploadCount;
    private String upLoadImages;//上传图片组
    private List<AlbumItem> upImages = new ArrayList<>();
    private File photoFile;
    private String photoPath;

    public static final int REQUEST_CAMERA = 106;
    private String IdCard;
    private String Name;
    private StsToken stsToken;
    private List<AlbumItem> selectedImages;
    private AlbumItem albumItem;
    private String ManagerName;
    private String ManagerIdCard;
    private String ManagerIdCardPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_qualification);
        ButterKnife.bind(this);
        setTitle("个人资质认证");
        setRightText1("保存");
        initView();
        getOssToken();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (albumItem == null) {
                            AlertUtils.toast(context, "请上传手持身份证");
                            return;
                        }
                        if (ManagerName == null) {
                            AlertUtils.toast(context, "请输入姓名");
                            return;
                        }
                        if (ManagerIdCard == null) {
                            AlertUtils.toast(context, "请输入身份证号码");
                            return;
                        }


                        final List<String> list = new ArrayList<String>();
                        list.add(albumItem.getFilePath());
                        compressWithLs(list);

                    }
                });

            }
        });

    }

    @OnClick({R.id.rl_name, R.id.rl_id, R.id.id_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写名字").putExtra("type", "name")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nameEd.getText().toString())
                );
                break;
            case R.id.rl_id:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "身份证号码").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_ID)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, idEd.getText().toString())
                );
                break;
            case R.id.id_photo:
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
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_ID: //send the video
                idEd.setText(event.getContent());
                ManagerIdCard = event.getContent();
                break;
            case ModifyDataActivity.TYPE_NAME: //send the video
                nameEd.setText(event.getContent());
                ManagerName = event.getContent();
                break;


        }
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

                                LOG.d("错误", e.toString());
                            }
                        }).launch();

            }
        }).start();

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
                        RequestBody body = new FormBody.Builder()
                                .add("ManagerName", ManagerName)
                                .add("ManagerIdCard", ManagerIdCard)
                                .add("ManagerIdCardPic", objectKey)

                                .build();

                        putCertification(body)
                        LOG.i(TAG, "动态图片上传成功：" + objectKey);
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
                        LOG.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
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

                albumItem = selectedImages.get(i);

                if (albumItem.getThumbnail().isEmpty()) {
                    Glide.with(context).load(albumItem.getThumbnail()).into(idPhoto);
                } else {
                    Glide.with(context).load(albumItem.getFilePath()).into(idPhoto);
                }
            }


        } else if (requestCode == REQUEST_CAMERA) {
            //使用相机拍照
            final int rotateDegree = BitmapUtils.readPictureDegree(photoFile.getAbsolutePath());
            LOG.i(TAG, "拍照后旋转的角度：" + rotateDegree);
            AlbumItem image = new AlbumItem();
            image.setFilePath(photoFile.getAbsolutePath());
            image.setThumbnail(photoFile.getAbsolutePath());


            albumItem = image;
            if (albumItem.getThumbnail().isEmpty()) {
                Glide.with(context).load(albumItem.getThumbnail()).into(idPhoto);
            } else {
                Glide.with(context).load(albumItem.getFilePath()).into(idPhoto);
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

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }


    private void showResult(List<String> photos, File file) {
        int[] thumbSize = computeSize(file.getAbsolutePath());
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);
        LOG.d("图", thumbArg);
        AlbumItem albumItem = new AlbumItem();
        albumItem.setFilePath(file.getAbsolutePath());
        upImages.add(albumItem);
        upload(albumItem, 0);
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

    private void putCertification(RequestBody body) {
        OkUtils.getInstances().httpatch(body, context, JavaConstant.IdCard, JavaParamsUtils.getInstances().IdCard(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                upImages.clear();

                String path = Environment.getExternalStorageDirectory() + "/zhixin/image/";
                deleteDir(path);
                EventBus.getDefault().post(new UploadImageFinishEvent(upLoadImages.toString()));

            }
        });
    }
}