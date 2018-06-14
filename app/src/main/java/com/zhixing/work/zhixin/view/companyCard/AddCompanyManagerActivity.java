package com.zhixing.work.zhixin.view.companyCard;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Manager;
import com.zhixing.work.zhixin.bean.Product;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.ManagerRefreshEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ProductRefreshEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.LOG;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddCompanyManagerActivity extends BaseTitleActivity {

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.rl_manager_photo)
    RelativeLayout rlManagerPhoto;
    @BindView(R.id.manager_name)
    TextView managerName;
    @BindView(R.id.iv_manager_name)
    ImageView ivManagerName;
    @BindView(R.id.rl_manager_name)
    RelativeLayout rlManagerName;
    @BindView(R.id.manager_position)
    TextView managerPosition;
    @BindView(R.id.iv_manager_position)
    ImageView ivManagerPosition;
    @BindView(R.id.rl_manager_position)
    RelativeLayout rlManagerPosition;
    @BindView(R.id.manager_introduce)
    TextView managerIntroduce;
    @BindView(R.id.iv_manager_introduce)
    ImageView ivManagerIntroduce;
    @BindView(R.id.rl_manager_introduce)
    RelativeLayout rlManagerIntroduce;
    @BindView(R.id.sava)
    Button sava;


    private String CompanyId = "";
    private String Name = "";
    private String JotTitle = "";
    private String Intro = "";
    private String Avatar = "";
    private String id = "";

    public static final int REQUEST_CAMERA = 10; // 拍照
    private File photoFile;
    private Uri imageUri;
    private List<AlbumItem> selectedImages;//标记选中的图片
    private String upLoadImages = "";//上传图片组
    private File cropFilePath;
    private Uri outPutUri;
    private StsToken stsToken;

    private Token token;
    private String type;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_manager);
        ButterKnife.bind(this);
        getOssToken();
        token = SettingUtils.getTokenBean();
        CompanyId = token.getCompanyId() + "";
        type = getIntent().getStringExtra("type");
        if (type.equals("edit")) {
            Bundle bundle = getIntent().getExtras();
            manager = (Manager) bundle.get("bean");
            initView();
        }
        setTitle("公司高管");
    }


    private void initView() {
        if (!TextUtils.isEmpty(manager.getAvatar())) {
            GlideUtils.getInstance().loadCircleUserIconInto(context, ALiYunFileURLBuilder.getUserIconUrl(manager.getAvatar()), photo);
            Avatar = manager.getAvatar();
        }
        if (!TextUtils.isEmpty(manager.getName())) {
            managerName.setText(manager.getName());
            Name = manager.getName();
        }
        if (!TextUtils.isEmpty(manager.getIntro())) {
            managerIntroduce.setText(manager.getIntro());
            Intro = manager.getIntro();
        }
        if (!TextUtils.isEmpty(manager.getJotTitle())) {
            managerPosition.setText(manager.getJotTitle());
            JotTitle = manager.getJotTitle();
        }
    }

    @OnClick({R.id.rl_manager_photo, R.id.rl_manager_name, R.id.rl_manager_position, R.id.rl_manager_introduce, R.id.sava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_manager_photo:
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
            case R.id.rl_manager_name:

                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "高管姓名").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_MANAGER_NAME).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, managerName.getText().toString()));

                break;
            case R.id.rl_manager_position:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "高管职位").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_MANAGER_POSITION).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, managerPosition.getText().toString()));

                break;
            case R.id.rl_manager_introduce:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "高管介绍").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.MANAGER_INTRODUCE).
                        putExtra(ModifyContentActivity.HINT, "请输入高管介绍").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, managerIntroduce.getText().toString()));
                break;
            case R.id.sava:

                if (TextUtils.isEmpty(Name)) {
                    AlertUtils.toast(context, "高管姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(JotTitle)) {
                    AlertUtils.toast(context, "高管职位不能为空");
                    return;
                }

                if (TextUtils.isEmpty(Avatar)) {
                    AlertUtils.toast(context, "高管头像不能为空");
                    return;
                }
                if (TextUtils.isEmpty(Intro)) {
                    AlertUtils.toast(context, "高管介绍不能为空");
                    return;
                }


                if (type.equals("edit")) {

                    if (cropFilePath == null) {
                        editManager(manager.getId() + "", Name, Avatar, JotTitle, Intro);
                    } else {
                        upload(cropFilePath.getAbsolutePath());
                    }
                } else {
                    showLoading();
                    upload(cropFilePath.getAbsolutePath());
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_MANAGER_NAME: //send the video
                managerName.setText(event.getContent());
                Name = event.getContent();
                break;
            case ModifyDataActivity.TYPE_MANAGER_POSITION: //send the video
                managerPosition.setText(event.getContent());
                JotTitle = event.getContent();
                break;
            case ModifyContentActivity.MANAGER_INTRODUCE: //send the video
                managerIntroduce.setText(event.getContent());
                Intro = event.getContent();
                break;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
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
    protected void onDestroy() {
        super.onDestroy();
        if (cropFilePath != null) {
            if (!TextUtils.isEmpty(cropFilePath.getAbsolutePath())) {
                Utils. deleteDirWihtFile(new File(Environment.getExternalStorageDirectory().getPath()));
            }
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
            BitmapUtils.createPhotoCrop(this, imageUri, outPutUri);
        } else if (requestCode == REQUEST_CAMERA) {
            //拍照照片
            imageUri = Uri.fromFile(photoFile);
            intCropUri();
            BitmapUtils.createPhotoCrop(this, imageUri, outPutUri);
        } else if (requestCode == Constant.IMAGE_CROP) {
            Avatar = cropFilePath.getAbsolutePath();
            GlideUtils.getInstance().loadIconInto(context, cropFilePath.getAbsolutePath(), photo);
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

                }
            }
        });
    }

    //上传头像
    private void upload(final String resultpath) {
        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.companyseniormanager + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        LOG.i(TAG, "动态图片上传成功：" + objectKey);
                        Avatar = objectKey;
                        if (type.equals("edit")) {
                            editManager(manager.getId() + "", Name, Avatar, JotTitle, Intro);
                        } else {
                            addManager(CompanyId, Name, Avatar, JotTitle, Intro);
                        }

                        // upAvatar(body, objectKey);
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


    private void addManager(String CompanyId, String Name, String Avatar,
                            String JotTitle, String Intro) {
        OkUtils.getInstances().httpPost(context, JavaConstant.CompanySeniorManager, JavaParamsUtils.getInstances().CompanySeniorManager
                (CompanyId, Name, Avatar,
                        JotTitle, Intro), new TypeToken<EntityObject<Boolean>>() {
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
                    if (response.getContent()) {
                        AlertUtils.toast(context, "添加成功");
                        EventBus.getDefault().post(new ManagerRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }

            }
        });
    }


    private void editManager(String id, String Name, String Avatar,
                             String JotTitle, String Intro) {
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("Name", Name)
                .add("Avatar", Avatar)
                .add("JotTitle", JotTitle)
                .add("Intro", Intro).build();
        OkUtils.getInstances().httpPut(body, context, JavaConstant.CompanySeniorManager, JavaParamsUtils.getInstances().CompanySeniorManager
                (id, Name, Avatar,
                        JotTitle, Intro), new TypeToken<EntityObject<Boolean>>() {
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
                    if (response.getContent()) {
                        AlertUtils.toast(context, "修改成功");
                        EventBus.getDefault().post(new ManagerRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }

            }
        });
    }

    private void deletePic(String path) {
        if (!TextUtils.isEmpty(path)) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = this.getContentResolver();//cutPic.this是一个上下文
            String url = MediaStore.Images.Media.DATA + "='" + path + "'";
            //删除图片
            contentResolver.delete(uri, url, null);
        }
    }
}