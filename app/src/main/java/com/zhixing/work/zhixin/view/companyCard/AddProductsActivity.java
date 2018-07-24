package com.zhixing.work.zhixin.view.companyCard;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ProductRefreshEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.AppUtils;
import com.zhixing.work.zhixin.util.FileUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imagetool.lhj.com.ImageTool;
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



    private Uri outPutUri;
    private StsToken stsToken;

    private String Name = "";
    private String Logo = "";
    private String Url = "";
    private String Intro = "";
    private String type;
    private Product product;
    private ImageTool mImageTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        ButterKnife.bind(this);
        setTitle("公司产品");
        type = getIntent().getStringExtra("type");
        mImageTool = new ImageTool(FileUtil.getDiskCachePath());
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

    }

    @OnClick({R.id.rl_company_logo, R.id.rl_product_name, R.id.rl_product_website, R.id.rl_product_description, R.id.sava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_company_logo:
                if(mImageTool != null){
                    mImageTool.reset().setAspectX_Y(1,1).start(AddProductsActivity.this, new ImageTool.ResultListener() {
                        @Override
                        public void onResult(String error, Uri uri, Bitmap bitmap) {
                            if(uri != null){
                                outPutUri = uri;
                                GlideUtils.getInstance().loadIconInto(context, outPutUri.getPath(), companyLogo);
                            }
                        }
                    });
                }

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
                    if (outPutUri != null) {
                        upload(outPutUri.getPath());
                    } else {
                        editProduct(product.getId() + "", Name, Logo, Url, Intro);
                    }
                } else {
                    if (outPutUri != null) {
                        upload(outPutUri.getPath());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageTool.onActivityResult(requestCode,resultCode,data);
    }


    /*保存界面状态，如果activity意外被系统killed，返回时可以恢复状态值*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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

    //上传头像
    private void upload(final String resultpath) {

        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.COMPANYPRODUCT + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        Logger.i(TAG, "动态图片上传成功：" + objectKey);
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
                        Logger.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }

    private void addProduct(String Name, String Logo,
                            String Url, String Intro) {
        OkUtils.getInstances().httpPost(context, RequestConstant.COMPANY_PRODUCT, JavaParamsUtils.getInstances().CompanyProduct(Name, Logo,
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
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
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
        OkUtils.getInstances().httpPut(body, context, RequestConstant.COMPANY_PRODUCT, JavaParamsUtils.getInstances().editCompanyProduct(id, Name, Logo,
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
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
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
