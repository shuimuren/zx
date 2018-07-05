package com.zhixing.work.zhixin.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.PublicEducationAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.UploadImageFinishEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.AppUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EducationCertificationActivity extends BaseTitleActivity {

    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.graduate_college)
    TextView graduateCollege;
    @BindView(R.id.iv_graduate_college)
    ImageView ivGraduateCollege;
    @BindView(R.id.rl_graduate_college)
    RelativeLayout rlGraduateCollege;
    @BindView(R.id.graduation_time)
    TextView graduationTime;
    @BindView(R.id.iv_graduation_time)
    ImageView ivGraduationTime;
    @BindView(R.id.rl_graduation_time)
    RelativeLayout rlGraduationTime;
    @BindView(R.id.major)
    TextView major;
    @BindView(R.id.iv_major)
    ImageView ivMajor;
    @BindView(R.id.rl_major)
    RelativeLayout rlMajor;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.submit)
    Button submit;

    private List<String> list;

    private int education_type;
    private String education_ct;
    private String graduate_college_ct;
    private String school_time_ct;
    private String graduation_time_ct;
    private String major_ct;


    private List<AlbumItem> publishImages;
    private PublicEducationAdapter adapter;
    private static final int MAX_UPLOAD_IMAGE = 3;//最多上传3张
    public static final int REQUEST_CAMERA = 106;
    private String token = "";
    private File photoFile;
    private String photoPath;
    private int isUploadCount;
    private String upLoadImages = "";//上传图片组
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> upImages = new ArrayList<AlbumItem>();
    private StsToken stsToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educationcertification);
        ButterKnife.bind(this);
        setTitle("学历认证");
        publishImages = new ArrayList<>();
        publishImages.add(null);
        getOssToken();
        initView();
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


    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + "/" + AppUtils.getUUID();
        photoFile = new File(photoPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
    }

    @OnClick({R.id.rl_education, R.id.rl_graduate_college, R.id.rl_graduation_time, R.id.rl_major, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_education:

                list = Arrays.asList(getResources().getStringArray(R.array.education));
                final OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = list.get(options1);
                        education_type = (options1 + 1) * 10;
                        education.setText(s);
                    }
                })
                        .setTitleText("学历")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
            case R.id.rl_graduate_college:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "毕业院校").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_SCHOOL));

                break;
            case R.id.rl_graduation_time:

                final TimePickerView graduation_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        graduationTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("毕业时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                graduation_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                graduation_time.show();
                break;
            case R.id.rl_major:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "专业").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_MAJOR));
                break;
            case R.id.submit:
                education_ct = education.getText().toString();
                graduate_college_ct = graduateCollege.getText().toString();

                graduation_time_ct = graduationTime.getText().toString();
                major_ct = major.getText().toString();

                if (TextUtils.isEmpty(education_ct)) {
                    AlertUtils.toast(context, "学历信息不能为空");
                    return;
                }
                if (TextUtils.isEmpty(graduate_college_ct)) {
                    AlertUtils.toast(context, "毕业院校不能为空");
                    return;
                }

                if (TextUtils.isEmpty(graduation_time_ct)) {
                    AlertUtils.toast(context, "毕业时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(major_ct)) {
                    AlertUtils.toast(context, "专业信息不能为空");
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
                                Logger.i(TAG, file.getAbsolutePath());
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
    private void upload(final AlbumItem albumItem, final int index) {
        String thumbnail = albumItem.getFilePath();
        String resultpath = thumbnail;

        String UUID = AppUtils.getUUID();
        final int size = publishImages.size();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.PERSONALEDUCATION + UUID,
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
                            Logger.i(TAG, "动态图片上传成功：" + upLoadImages);
                            upImages.clear();
                            deleteDir(path);
                            EventBus.getDefault().post(new UploadImageFinishEvent(upLoadImages.toString()));
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_SCHOOL: //send the video
                graduateCollege.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_MAJOR: //send the video
                major.setText(event.getContent());
                break;

        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTradeAreaEvent(UploadImageFinishEvent event) {
        AlertUtils.toast(context, "资料上传成功,请耐心等待审核");
        finish();
    }
}
