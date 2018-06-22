package com.zhixing.work.zhixin.view.myCenter.resume;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.LOG;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PersonalDataActivity extends BaseTitleActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.llAvatar)
    LinearLayout llAvatar;
    @BindView(R.id.nick)
    TextView nick;
    @BindView(R.id.llnick)
    LinearLayout llnick;
    @BindView(R.id.date_birth)
    TextView dateBirth;
    @BindView(R.id.ll_date_birth)
    LinearLayout llDateBirth;
    @BindView(R.id.first_working_time)
    TextView firstWorkingTime;
    @BindView(R.id.ll_first_working_time)
    LinearLayout llFirstWorkingTime;

    private String name = "";
    private String date_birth = "";
    private String workingTime = "";
    public static final int REQUEST_CAMERA = 10; // 拍照

    private File photoFile;
    private Uri imageUri;
    private List<AlbumItem> selectedImages;//标记选中的图片
    private String upLoadImages = "";//上传图片组
    private File cropFilePath;
    private Uri outPutUri;
    private String token = "";
    private Resume resume;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private StsToken stsToken;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        setTitle("个人资料");
        context = this;
        Bundle bundle = getIntent().getExtras();
        resume = (Resume) bundle.get("bean");
        getOssToken();

    }

    private void initView() {

        String url = ALiYunOssFileLoader.gteSecret(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, resume.getAvatar());
        GlideUtils.getInstance().loadCircleUserIconInto(context, url, image);
        nick.setText(resume.getPersonalInfo().getRealName());
        if (!TextUtils.isEmpty(resume.getPersonalInfo().getFirstWorkTime())){

            firstWorkingTime.setText(DateFormatUtil.parseDate(resume.getPersonalInfo().getFirstWorkTime(), "yyyy-MM-dd"));
        }if (!TextUtils.isEmpty(resume.getPersonalInfo().getBirthday())){

            dateBirth.setText(DateFormatUtil.parseDate(resume.getPersonalInfo().getBirthday(), "yyyy-MM"));
        }




    }

    @OnClick({R.id.llAvatar, R.id.llnick, R.id.ll_date_birth, R.id.ll_first_working_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAvatar:
                SelectImageDialog imageDialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();

                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                photoFile = new File(Constant.CACHE_DIR_IMAGE + "/" + System.currentTimeMillis() + ".jpg");
                                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
                                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                startActivityForResult(intentCamera, REQUEST_CAMERA);
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_AVATAR);
                                break;
                        }
                    }
                });
                imageDialog.show();
                break;
            case R.id.llnick:
                startActivity(new Intent(context, ModifyDataActivity.class)
                        .putExtra("title", "填写名字").putExtra("type", "name")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT,nick.getText().toString())
                );
                break;
            case R.id.ll_date_birth:
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        date_birth = time;

                        if (date_birth.equals(dateBirth.getText().toString())) {
                            return;
                        }
                        RequestBody body = new FormBody.Builder()
                                .add("RealName", "")
                                .add("FirstWorkTime", "")
                                .add("Birthday", date_birth)
                                .build();
                        showLoadingDialog("");
                        modifyData(body, 2);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("出生日期")
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
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.ll_first_working_time:

                final TimePickerView work_Time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTimeYM(date2);
                        workingTime = time;

                        if (workingTime.equals(firstWorkingTime.getText().toString())) {
                            return;
                        }
                        RequestBody body = new FormBody.Builder()
                                .add("RealName", "")
                                .add("FirstWorkTime", workingTime)
                                .add("Birthday", "")
                                .build();
                        showLoadingDialog("");
                        modifyData(body, 3);
                    }
                })
                        .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("工作时间")
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
                        .build();
                work_Time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                work_Time.show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_NAME: //send the video
                name = event.getContent();
                if (name.equals(nick.getText().toString())) {
                    return;
                }
                RequestBody body = new FormBody.Builder()
                        .add("RealName", name)
                        .add("FirstWorkTime", "")
                        .add("Birthday", "")
                        .build();
                modifyData(body, 1);
                break;
        }
    }
//修改数据
    private void modifyData(final RequestBody body, final int type) {
        OkUtils.getInstances().httpatch(body, context, JavaConstant.ResumePersonalInfo, JavaParamsUtils.getInstances().ResumePersonalInfo(name, workingTime, date_birth), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            EventBus.getDefault().post(new ResumeRefreshEvent(true));
                            switch (type) {
                                case 1:
                                    nick.setText(name);
                                    break;
                                case 2:
                                    dateBirth.setText(date_birth);
                                    break;
                                case 3:
                                    firstWorkingTime.setText(workingTime);
                                    break;
                            }
                        }
                    } else {
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
    }


    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cropFilePath != null) {
            if (!TextUtils.isEmpty(cropFilePath.getAbsolutePath())) {
                Utils. deleteDirWihtFile(new File(Environment.getExternalStorageDirectory().getPath()));
            }
        }
    }

    /**
     * 初始化剪裁图片的输出Uri
     */
    private void intCropUri() {
        if (outPutUri == null) {
            cropFilePath = new File(Environment.getExternalStorageDirectory().getPath(), "cutImage.png");
            try {
                cropFilePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outPutUri = Uri.fromFile(cropFilePath);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == SelectImageActivity.REQUEST_AVATAR) {
            //从相册选照片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            AlbumItem albumItem = selectedImages.get(0);
            imageUri = Uri.fromFile(new File(albumItem.getFilePath()));
            intCropUri();
            BitmapUtils.createPhotoCrop(PersonalDataActivity.this, imageUri, outPutUri);
        } else if (requestCode == REQUEST_CAMERA) {
            //拍照照片
            imageUri = Uri.fromFile(photoFile);
            intCropUri();
            BitmapUtils.createPhotoCrop(PersonalDataActivity.this, imageUri, outPutUri);
        } else if (requestCode == Constant.IMAGE_CROP) {
            showLoading();
            upload(cropFilePath.getAbsolutePath());

        }

    }


    /*保存界面状态，如果activity意外被系统killed，返回时可以恢复状态值*/
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
                    initView();
                }
            }
        });
    }

    //上传头像
    private void upload(final String resultpath) {

        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.PERSONALPORTRAIT + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        LOG.i(TAG, "动态图片上传成功：" + objectKey);
                        RequestBody body = new FormBody.Builder()
                                .add("Id", resume.getId() + "")
                                .add("Avatar", objectKey)
                                .build();

                        upAvatar(body, objectKey);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {

                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {

                        LOG.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }

    //修改头像
    private void upAvatar(RequestBody body, final String key) {
        OkUtils.getInstances().httpatch(body, context, JavaConstant.Resume, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            EventBus.getDefault().post(new ResumeRefreshEvent(true));
                            String url = ALiYunOssFileLoader.gteSecret(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, key);

                            GlideUtils.getInstance().loadCircleUserIconInto(context, url, image);

                        }
                    } else {
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });

    }


}
