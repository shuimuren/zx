package com.zhixing.work.zhixin.view.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.reflect.TypeToken;
import com.xmd.file.provider.FileProvider7;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.PublicEducationAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.CertificationBean;
import com.zhixing.work.zhixin.bean.CertificationBody;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.AppUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;
import com.zhixing.work.zhixin.widget.ClearEditText;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 证书认证
 */
public class CertificateCertificationActivity extends BaseTitleActivity {

    @BindView(R.id.certificate_time)
    TextView certificateTime;
    @BindView(R.id.iv_certificate_time)
    ImageView ivCertificateTime;
    @BindView(R.id.rl_certificate_time)
    RelativeLayout rlCertificateTime;
    @BindView(R.id.certificate_name)
    TextView certificateName;
    @BindView(R.id.iv_certificate_name)
    ImageView ivCertificateName;
    @BindView(R.id.rl_certificate_name)
    RelativeLayout rlCertificateName;
    @BindView(R.id.achievement_ed)
    ClearEditText achievementEd;
    @BindView(R.id.iv_achievement)
    ImageView ivAchievement;
    @BindView(R.id.rl_achievement)
    RelativeLayout rlAchievement;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.submit)
    Button submit;

    public static final String INTENT_KEY_CERITIFICATION = "intentKey";
    private String achievement;
    private String time;
    private String name;
    private List<AlbumItem> publishImages;
    private PublicEducationAdapter adapter;
    private static final int MAX_UPLOAD_IMAGE = 3;//最多上传3张
    public static final int REQUEST_CAMERA = 106;
    private String token = "";
    private File photoFile;
    private String photoPath;
    private int isUploadCount;
    private String upLoadImages;//上传图片组
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> upImages = new ArrayList<>();
    private StsToken stsToken;
    private int count;
    private Context context;
    private int authenticatesId;
    private Subscription certificateSubmitSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_certification);
        ButterKnife.bind(this);
        context = this;
        setTitle(ResourceUtils.getString(R.string.certificate_authentication));
        publishImages = new ArrayList<>();
        publishImages.add(null);
        getOssToken();
        initView();
        authenticatesId = getIntent().getIntExtra(INTENT_KEY_CERITIFICATION, 0);
        certificateSubmitSubscription = RxBus.getInstance().toObservable(SubmitAuthenticateResult.class).subscribe(
                result -> handlerSubmitResult(result)
        );
    }

    private void handlerSubmitResult(SubmitAuthenticateResult result) {
        if (result.isContent()) {
            AlertUtils.show(ResourceUtils.getString(R.string.submit_success));
            CertificateCertificationActivity.this.finish();
        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.submit_failure));
        }
    }


    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.GET_OSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    stsToken = response.getContent();
                }
            }
        });
    }

    private void initView() {
        adapter = new PublicEducationAdapter(this, publishImages);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        listview.setLayoutManager(gridLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.publish_line));
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new PublicEducationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlbumItem item = publishImages.get(position);
                if (item == null) {
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
                                    intent.putExtra(SelectImageActivity.LIMIT, MAX_UPLOAD_IMAGE + 1 - publishImages.size());
                                    startActivityForResult(intent, SelectImageActivity.REQUEST_DYNAMIC);
                                    break;
                            }

                        }
                    });
                    dialog.show();

                } else {
                    if (publishImages.get(publishImages.size() - 1) == null) {
                        publishImages.remove(item);
                    } else {
                        publishImages.remove(item);
                        publishImages.add(null);
                    }
                    Logger.i(TAG, "images.SIZE:" + publishImages.size());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
                        //  .setTargetDir(getPath())
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


    /**********************
     * 以下为上传代码
     *********************************/
    private class uploadRunnable implements Runnable {

        @Override
        public void run() {
            isUploadCount = 0;
            for (int i = 0; i < upImages.size(); i++) {
                AlbumItem albumItem = upImages.get(i);
                if (null != albumItem) {
                    upload(albumItem, i);
                }
            }
        }
    }


    //上传阿里云
    private void upload(final AlbumItem albumItem, final int
            index) {
        String thumbnail = albumItem.getFilePath();
        String resultpath = thumbnail;
        String UUID = AppUtils.getUUID();
        final int size = publishImages.size();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.PERSONALCERTIFICATE + UUID,
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
                        if (isUploadCount == size - (publishImages.get(size - 1) == null ? 1 : 0)) {
                            hideLoadingDialog();
                            Logger.i(TAG, "动态图片上传成功：" + objectKey);
                            upImages.clear();
                            deleteDir(path);
                            submitDataToService();
                            // EventBus.getDefault().post(new UploadImageFinishEvent(upLoadImages.toString()));
                        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == SelectImageActivity.REQUEST_DYNAMIC) {
            //通过相册选择图片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            for (int i = 0; i < selectedImages.size(); i++) {
                SortImage(selectedImages.get(i));
            }
        } else if (requestCode == REQUEST_CAMERA) {
            //使用相机拍照
            final int rotateDegree = BitmapUtils.readPictureDegree(photoFile.getAbsolutePath());
            Logger.i(TAG, "拍照后旋转的角度：" + rotateDegree);
            AlbumItem image = new AlbumItem();
            image.setFilePath(photoFile.getAbsolutePath());
            image.setThumbnail(photoFile.getAbsolutePath());
            SortImage(image);
        }
    }

    private void SortImage(AlbumItem item) {
        // 将每一张图片插入null的最前面
        publishImages.add(publishImages.size() - 1, item);
        if (publishImages.size() == MAX_UPLOAD_IMAGE + 1) {
            publishImages.remove(publishImages.size() - 1);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putString("msg_con", htmlsource);
        if (photoFile != null) {
            if (photoFile.getAbsolutePath() != null) {
                savedInstanceState.putString("msg_camera_picname", photoFile.getAbsolutePath());
            }
        }
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        photoFile = new File(savedInstanceState.getString("msg_camera_picname"));
    }

    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + "/" + AppUtils.getUUID();
        photoFile = new File(photoPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(CertificateCertificationActivity.this, photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
    }

    @OnClick({R.id.rl_certificate_time, R.id.rl_certificate_name, R.id.rl_achievement, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_certificate_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        certificateTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                school_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                school_time.show();
                break;
            case R.id.rl_certificate_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "证书名字").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_CERTIFICATE_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, certificateName.getText().toString())
                );
                break;
            case R.id.rl_achievement:
                break;
            case R.id.submit:

                time = certificateTime.getText().toString();
                name = certificateName.getText().toString();
                achievement = achievementEd.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    AlertUtils.toast(context, "时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    AlertUtils.toast(context, "证书名字不能为空");
                    return;
                }

                if (null != publishImages && publishImages.size() > 1) {
                    final List<String> list = new ArrayList<String>();
                    for (int i = 0; i < publishImages.size(); i++) {
                        AlbumItem albumItem = publishImages.get(i);
                        if (null != albumItem) {
                            list.add(albumItem.getFilePath());
                        }
                    }
                    compressWithLs(list);
                } else {
                    AlertUtils.toast(context, "还未选择图片");
                }

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_CERTIFICATE_NAME: //send the video
                certificateName.setText(event.getContent());
                break;
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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onTradeAreaEvent(UploadImageFinishEvent event) {
//        AlertUtils.toast(context, "资料上传成功,请耐心等待审核");
//        finish();
//    }

    //提交服务器审核
    private void submitDataToService() {
        CertificationBody body = new CertificationBody();
        body.setAuthenticatesId(authenticatesId);
        CertificationBean bean = new CertificationBean();
        bean.setCertificateTitle(name);
        bean.setGrade(achievement);
        bean.setImgUrl(upLoadImages);
        bean.setGraduationDate(time);
        body.setInfo(bean);
        HashMap params = new HashMap();
        params.put(RequestConstant.KEY_AUTHENTICATION_INFO, body);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_CERTIFICATION_AUTHENTICATE_SUBMIT, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(certificateSubmitSubscription);
    }
}
