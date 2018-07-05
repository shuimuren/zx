package com.zhixing.work.zhixin.view.companyCard;

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
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.PublicEducationAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.BigEventRefreshEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
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
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class AddEventActivity extends BaseTitleActivity {

    @BindView(R.id.event_name)
    TextView eventName;
    @BindView(R.id.event_name_right)
    ImageView eventNameRight;
    @BindView(R.id.rl_event_name)
    RelativeLayout rlEventName;
    @BindView(R.id.event_time)
    TextView eventTime;
    @BindView(R.id.event_time_right)
    ImageView eventTimeRight;
    @BindView(R.id.rl_event_time)
    RelativeLayout rlEventTime;
    @BindView(R.id.event_synopsis)
    TextView eventSynopsis;
    @BindView(R.id.event_synopsis_right)
    ImageView eventSynopsisRight;
    @BindView(R.id.rl_event_synopsis)
    RelativeLayout rlEventSynopsis;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.sava)
    Button sava;
    @BindView(R.id.ll_sava)
    RelativeLayout llSava;


    private List<AlbumItem> publishImages;
    private PublicEducationAdapter adapter;
    private static final int MAX_UPLOAD_IMAGE = 1;//最多上传1张
    public static final int REQUEST_CAMERA = 106;
    private String token = "";
    private File photoFile;
    private String photoPath;
    private int isUploadCount;
    private String upLoadImages = "";//上传图片组
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> upImages = new ArrayList<AlbumItem>();
    private StsToken stsToken;


    private String Name = "";
    private String Date = "";
    private String Intro = "";
    private String Image = "";
    private String type;
    private History history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        setTitle("公司事件");
        publishImages = new ArrayList<>();
        publishImages.add(null);
        type = getIntent().getStringExtra("type");
        initView();
        if (type.equals("edit")) {
            Bundle bundle = getIntent().getExtras();
            history = (History) bundle.get("bean");
            setView();
        }
        getOssToken();
    }
    private void setView() {
        if (!TextUtils.isEmpty(history.getName())) {
            eventName.setText(history.getName());
            Name = history.getName();
        }
        if (!TextUtils.isEmpty(history.getIntro())) {
            eventSynopsis.setText(history.getIntro());
            Intro = history.getIntro();
        }
        if (!TextUtils.isEmpty(history.getDate())) {
            eventTime.setText( DateFormatUtil.parseDate(history.getDate(), "yyyy-mm"));
            Date = history.getDate();
        }
    }




    @OnClick({R.id.rl_event_name, R.id.rl_event_time, R.id.rl_event_synopsis, R.id.sava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_event_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "事件名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.EVENT_NAME).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, eventName.getText().toString()));
                break;
            case R.id.rl_event_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTimeYM(date2);
                        eventTime.setText(time);
                        Date = time;
                    }
                })
                        .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("事件时间")
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
                school_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                school_time.show();
                break;
            case R.id.rl_event_synopsis:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "事件简介").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.EVENT_SYNOPSIS).
                        putExtra(ModifyContentActivity.HINT, "事件简介").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, eventSynopsis.getText().toString()));
                break;
            case R.id.sava:
                if (TextUtils.isEmpty(Intro)) {
                    AlertUtils.toast(context, "事件简介不能为空");
                    return;
                }
                if (TextUtils.isEmpty(Name)) {
                    AlertUtils.toast(context, "事件名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(Date)) {
                    AlertUtils.toast(context, "事件时间不能为空");
                    return;
                }
                showLoading();
                if (null != publishImages && publishImages.size() > 0) {
                    final List<String> list = new ArrayList<String>();
                    for (int i = 0; i < publishImages.size(); i++) {
                        AlbumItem albumItem = publishImages.get(i);
                        if (null != albumItem) {
                            list.add(albumItem.getFilePath());
                        }
                    }
                    if (!list.isEmpty()) {

                        compressWithLs(list);
                    } else {

                        if (type.equals("edit")) {
                            editEvent(history.getId() + "", Name, Date, Intro, Image);
                        } else {
                            addEvent(Name, Date, Intro, Image);
                        }


                    }
                } else {

                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {

            case ModifyDataActivity.EVENT_NAME:
                eventName.setText(event.getContent());
                Name = event.getContent();
                break;
            case ModifyContentActivity.EVENT_SYNOPSIS:
                eventSynopsis.setText(event.getContent());
                Intro = event.getContent();
                break;
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


    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + "/" + AppUtils.getUUID();
        photoFile = new File(photoPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
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
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.COMPANYHISTORY + UUID,
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
                            Logger.i(TAG, "动态图片上传成功：" + upLoadImages);
                            upImages.clear();
                            deleteDir(path);
                            if (type.equals("edit")) {
                                editEvent(history.getId() + "", Name, Date, Intro, upLoadImages);
                            } else {
                                addEvent(Name, Date, Intro, upLoadImages);
                            }


                            upLoadImages = "";
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

    //添加大事件
    private void addEvent(String Name, String Date, String Intro, String Image) {
        OkUtils.getInstances().httpPost(context, RequestConstant.COMPANY_HISTORY, JavaParamsUtils.getInstances().AddCompanyHistory
                (Name, Date, Intro, Image), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "添加成功");
                        EventBus.getDefault().post(new BigEventRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }
            }
        });
    }

    //添加大事件
    private void editEvent(String id, String Name, String Date, String Intro, String Image) {
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("Name", Name)
                .add("Date", Date)
                .add("Image", Image)
                .add("Intro", Intro).build();
        OkUtils.getInstances().httpPut(body,context, RequestConstant.COMPANY_HISTORY, JavaParamsUtils.getInstances().editCompanyHistory
                (id, Name, Date, Intro, Image), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "修改成功");
                        EventBus.getDefault().post(new BigEventRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }
            }
        });
    }
}
