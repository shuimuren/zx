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
import com.zhixing.work.zhixin.bean.Product;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
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

public class AddProductsActivity extends BaseTitleActivity {


    @BindView(R.id.company_logo)
    ImageView companyLogo;
    @BindView(R.id.iv_company_logo)
    ImageView ivCompanyLogo;
    @BindView(R.id.rl_company_logo)
    RelativeLayout rlCompanyLogo;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.iv_product_name)
    ImageView ivProductName;
    @BindView(R.id.rl_product_name)
    RelativeLayout rlProductName;
    @BindView(R.id.product_website)
    TextView productWebsite;
    @BindView(R.id.iv_product_website)
    ImageView ivProductWebsite;
    @BindView(R.id.rl_product_website)
    RelativeLayout rlProductWebsite;
    @BindView(R.id.product_description)
    TextView productDescription;
    @BindView(R.id.iv_product_description)
    ImageView ivProductDescription;
    @BindView(R.id.rl_product_description)
    RelativeLayout rlProductDescription;
    @BindView(R.id.sava)
    Button sava;


    public static final int REQUEST_CAMERA = 10; // 拍照
    private File photoFile;
    private Uri imageUri;
    private List<AlbumItem> selectedImages;//标记选中的图片
    private String upLoadImages = "";//上传图片组
    private File cropFilePath;
    private Uri outPutUri;
    private StsToken stsToken;

    private String Name = "";
    private String Logo = "";
    private String Url = "";
    private String Intro = "";
    private String type;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        ButterKnife.bind(this);
        setTitle("公司产品");
        type = getIntent().getStringExtra("type");
        if (type.equals("edit")) {
            Bundle bundle = getIntent().getExtras();
            product = (Product) bundle.get("bean");
            initView();
        }
        getOssToken();
    }

    private void initView() {
        if (!TextUtils.isEmpty(product.getLogo())) {
            GlideUtils.getInstance().loadCircleUserIconInto(context, ALiYunFileURLBuilder.getUserIconUrl(product.getLogo()), companyLogo);
            Logo = product.getLogo();
        }
        if (!TextUtils.isEmpty(product.getName())) {
            productName.setText(product.getName());
            Name = product.getName();
        }

        if (!TextUtils.isEmpty(product.getIntro())) {
            productDescription.setText(product.getIntro());
            Intro = product.getIntro();
        }

        if (!TextUtils.isEmpty(product.getUrl())) {
            productWebsite.setText(product.getUrl());
            Url = product.getUrl();
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

    @OnClick({R.id.rl_company_logo, R.id.rl_product_name, R.id.rl_product_website, R.id.rl_product_description, R.id.sava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_company_logo:
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
            case R.id.rl_product_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "产品名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_PRODUCT_NAME).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, productName.getText().toString()));
                break;
            case R.id.rl_product_website:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "公司官网").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_PRODUCT_WEBSITE).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, productWebsite.getText().toString()));
                break;
            case R.id.rl_product_description:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "产品描述").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.PRODUCT_DESCRIPTION).
                        putExtra(ModifyContentActivity.HINT, "产品描述").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, productDescription.getText().toString()));
                break;
            case R.id.sava:
                if (TextUtils.isEmpty(Name)) {
                    AlertUtils.toast(context, "产品名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(Intro)) {
                    AlertUtils.toast(context, "产品描述不能为空");
                    return;
                }
                showLoading();

                if (type.equals("edit")) {
                    if (cropFilePath != null) {
                        upload(cropFilePath.getAbsolutePath());
                    } else {
                        editProduct(product.getId() + "", Name, Logo, Url, Intro);
                    }
                } else {
                    if (cropFilePath != null) {
                        upload(cropFilePath.getAbsolutePath());
                    } else {
                        addProduct(Name, Logo, Url, Intro);
                    }
                }


                break;
        }
    }

    //设置数据显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_PRODUCT_NAME: //send the video
                productName.setText(event.getContent());
                Name = event.getContent();
                break;
            case ModifyDataActivity.TYPE_PRODUCT_WEBSITE: //send the video
                productWebsite.setText(event.getContent());
                Url = event.getContent();
                break;

            case ModifyContentActivity.PRODUCT_DESCRIPTION: //send the video
                productDescription.setText(event.getContent());
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

            GlideUtils.getInstance().loadIconInto(context, cropFilePath.getAbsolutePath(), companyLogo);
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
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.COMPANYPRODUCT + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        LOG.i(TAG, "动态图片上传成功：" + objectKey);
                        if (type.equals("edit")) {
                            editProduct(product.getId() + "", Name, objectKey, Url, Intro);
                        } else {
                            addProduct(Name, objectKey, Url, Intro);
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

    private void addProduct(String Name, String Logo,
                            String Url, String Intro) {
        OkUtils.getInstances().httpPost(context, JavaConstant.CompanyProduct, JavaParamsUtils.getInstances().CompanyProduct(Name, Logo,
                Url, Intro), new TypeToken<EntityObject<Boolean>>() {
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
                        EventBus.getDefault().post(new ProductRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }
            }
        });
    }

    private void editProduct(String id, String Name, String Logo,
                             String Url, String Intro) {
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("Name", Name)
                .add("Logo", Logo)
                .add("Url", Url)
                .add("Intro", Intro).build();
        OkUtils.getInstances().httpPut(body, context, JavaConstant.CompanyProduct, JavaParamsUtils.getInstances().editCompanyProduct(id, Name, Logo,
                Url, Intro), new TypeToken<EntityObject<Boolean>>() {
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
                        EventBus.getDefault().post(new ProductRefreshEvent(true));
                    }
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }
            }
        });
    }


}
