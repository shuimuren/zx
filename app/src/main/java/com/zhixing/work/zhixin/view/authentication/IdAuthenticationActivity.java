package com.zhixing.work.zhixin.view.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ImageUploadOrDownManager;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AuthenticateBody;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.FileUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imagetool.lhj.com.ImageTool;
import rx.Subscription;

/**
 * 2018-07-09 lhj 我的-我的认证-我的认证-身份认证
 */
public class IdAuthenticationActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_ID_AUTHENTICATION = "id";

    @BindView(R.id.editUserName)
    ClearEditText editUserName;
    @BindView(R.id.editUserIdCardCode)
    ClearEditText editUserIdCardCode;
    @BindView(R.id.hold_iv)
    ImageView holdIv;
    @BindView(R.id.positive_iv)
    ImageView positiveIv;
    @BindView(R.id.back_iv)
    ImageView backIv;

    //1:手持身份证,2:身份证正面,3身份证反面
    private Uri holdIdUri, positiveIdUri, backIdUri;
    private ImageTool imageTool;
    private Subscription imageUploadSubscription;//上传图片
    private Subscription submitInfoSubscription;//提交审核
    private int totalUri = 2;
    private int imageMaxSize = 2000;
    private String holdImageUrl, positiveUrl, backUrl;
    private String authenticatesId; //认证ID
    private String userName;
    private String userIdCardCode;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_authentication);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.identity_authentication));
        setRightText1(ResourceUtils.getString(R.string.upload));
        authenticatesId = getIntent().getStringExtra(INTENT_KEY_ID_AUTHENTICATION);
        imageTool = new ImageTool(FileUtil.getDiskCachePath());
        initView();
        imageUploadSubscription = RxBus.getInstance().toObservable(ImageUploadResult.class).subscribe(
                result -> handlerUploadResult(result)
        );
        submitInfoSubscription = RxBus.getInstance().toObservable(SubmitAuthenticateResult.class).subscribe(
                result -> handlerSubmitResult(result)
        );
    }


    private void handlerUploadResult(ImageUploadResult result) {
        if (result.isSuccess()) {
            if (result.getCode() == 1) {
                holdImageUrl = result.getUrl();
            } else if (result.getCode() == 2) {
                positiveUrl = result.getUrl();
            } else if (result.getCode() == 3) {
                backUrl = result.getUrl();
            }
        } else {
            if (result.getCode() == 1) {
                AlertUtils.show("手持身份证上传失败，请重新选择");
            } else if (result.getCode() == 2) {
                AlertUtils.show("身份证正面上传失败，请重新选择");
            } else if (result.getCode() == 3) {
                AlertUtils.show("身份证反面上传失败，请重新选择");
            }
        }

        if (totalUri > 0) {
            totalUri--;
        } else {
            hideLoadingDialog();
        }

        if ((!TextUtils.isEmpty(holdImageUrl)) && (!TextUtils.isEmpty(positiveUrl)) && (!TextUtils.isEmpty(backUrl))) {
            imageUrl = holdImageUrl + "," + positiveUrl + "," + backUrl;
            userName = editUserName.getText().toString();
            userIdCardCode = editUserIdCardCode.getText().toString();
            AuthenticateBody body = new AuthenticateBody();
            AuthenticateBody.InfoBean infoBean = new AuthenticateBody.InfoBean();
            infoBean.setRealName(userName);
            infoBean.setImgUrl(imageUrl);
            infoBean.setIdCard(userIdCardCode);
            body.setInfo(infoBean);
            body.setAuthenticatesId(Integer.parseInt(authenticatesId));
            Map hasMap = new HashMap();
            hasMap.put(RequestConstant.KEY_AUTHENTICATION_INFO, body);
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_AUTHENTICATE_SUBMIT, hasMap);
        }
    }

    /**
     * 处理提交结果
     *
     * @param result
     */
    private void handlerSubmitResult(SubmitAuthenticateResult result) {
        if (result.isContent()) {
            AlertUtils.show(ResourceUtils.getString(R.string.submit_success));
            this.finish();
        } else {
            totalUri = 2;
            AlertUtils.show(ResourceUtils.getString(R.string.submit_failure));
        }
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editUserName.getText().toString())) {
                    AlertUtils.show(ResourceUtils.getString(R.string.alter_input_user_name));
                    return;
                }
                if (TextUtils.isEmpty(editUserIdCardCode.getText().toString())) {
                    AlertUtils.show(ResourceUtils.getString(R.string.alert_input_user_id_card_code));
                    return;
                }
                if (holdIdUri == null) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_upload_photo_id_card));
                    return;
                }

                if (positiveIdUri == null) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_upload_photo_id_card_positive));
                    return;
                }
                if (backIdUri == null) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_upload_photo_id_card_positive));
                    return;
                }
                showLoading(ResourceUtils.getString(R.string.uploading), false);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD,
                        ImageUploadOrDownManager.getUploadHashMap(1,
                                ALiYunFileURLBuilder.PERSONALIDCARD, holdIdUri.getPath(), ALiYunFileURLBuilder.BUCKET_SECTET));
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD,
                        ImageUploadOrDownManager.getUploadHashMap(2,
                                ALiYunFileURLBuilder.PERSONALIDCARD, positiveIdUri.getPath(), ALiYunFileURLBuilder.BUCKET_SECTET));
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD,
                        ImageUploadOrDownManager.getUploadHashMap(3,
                                ALiYunFileURLBuilder.PERSONALIDCARD, backIdUri.getPath(), ALiYunFileURLBuilder.BUCKET_SECTET));

            }
        });
    }

    @OnClick({R.id.rl_hold_id, R.id.rl_positive_id, R.id.rl_back_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_hold_id:

                imageTool.reset().onlyPick(true).maxSize(imageMaxSize).start(this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if (uri != null) {
                            holdIdUri = uri;
                            Glide.with(IdAuthenticationActivity.this).load(uri).into(holdIv);
                            holdIv.setVisibility(View.VISIBLE);
                        } else {
                            holdIv.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case R.id.rl_positive_id:

                imageTool.reset().onlyPick(true).maxSize(imageMaxSize).start(this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if (uri != null) {
                            positiveIdUri = uri;
                            Glide.with(IdAuthenticationActivity.this).load(uri).into(positiveIv);
                            positiveIv.setVisibility(View.VISIBLE);
                        } else {
                            positiveIv.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case R.id.rl_back_id:
                imageTool.reset().onlyPick(true).maxSize(imageMaxSize).start(this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if (uri != null) {
                            backIdUri = uri;
                            Glide.with(IdAuthenticationActivity.this).load(uri).into(backIv);
                            backIv.setVisibility(View.VISIBLE);
                        } else {
                            backIv.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageTool.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(imageUploadSubscription, submitInfoSubscription);
    }
}
