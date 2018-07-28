package com.zhixing.work.zhixin.view.authentication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.MultiImageAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.CertificationBean;
import com.zhixing.work.zhixin.bean.CertificationBody;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imagetool.lhj.com.selector.MultiImageSelector;
import imagetool.lhj.com.selector.MultiImageSelectorActivity;
import rx.Subscription;

/**
 * 我的-我的认证-我的认证-证书认证
 */
public class CertificateCertificationActivity extends BaseTitleActivity {


    public static final String INTENT_KEY_CERTIFICATION = "intentKey";
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
    @BindView(R.id.recyclerImage)
    RecyclerView recyclerImage;
    @BindView(R.id.submit)
    Button submit;

    public static final int REQUEST_CODE_CHOICE_IMAGE = 0x1234;
    private int authenticatesId;
    private String mAchievement; //成绩
    private String mTime;  //时间
    private String mName; //名称
    private String mUpLoadImages;//上传图片组
    private Subscription certificateSubmitSubscription;
    private Subscription uploadImageSubmitSubscription;
    private List<String> mImageData;
    private MultiImageAdapter mMultiAdapter;
    private int mTotalImages;
    private List<String> mUploadImagesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_certification);
        ButterKnife.bind(this);
        context = this;
        setTitle(ResourceUtils.getString(R.string.certificate_authentication));
        initView();
        authenticatesId = getIntent().getIntExtra(INTENT_KEY_CERTIFICATION, 0);
        registerSubscription();
    }

    private void registerSubscription() {

        certificateSubmitSubscription = RxBus.getInstance().toObservable(SubmitAuthenticateResult.class).subscribe(
                result -> handlerSubmitResult(result)
        );

        uploadImageSubmitSubscription = RxBus.getInstance().toObservable(ImageUploadResult.class).subscribe(
                result -> handlerImageUploadToAli(result)
        );
    }

    private void handlerImageUploadToAli(ImageUploadResult result) {
        if (result.getCode() == ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_CERTIFICATE) {
            mTotalImages--;
            if (result.isSuccess()) {
                mUploadImagesArray.add(result.getUrl());
            } else {
                Logger.i(">>>", "上传失败");
            }
            if (mTotalImages == 0) {
                mUpLoadImages = Utils.listToString(mUploadImagesArray);
                submitDataToService();
            }
        }
    }

    private void initView() {
        mImageData = new ArrayList<String>();
        mUploadImagesArray = new ArrayList<>();
        mMultiAdapter = new MultiImageAdapter(CertificateCertificationActivity.this, mImageData, 3);
        recyclerImage.setLayoutManager(new GridLayoutManager(CertificateCertificationActivity.this, 3));
        recyclerImage.setAdapter(mMultiAdapter);
        mMultiAdapter.setOnItemClickedListener(new MultiImageAdapter.OnItemClickedListener() {
            @Override
            public void addImage() {
                MultiImageSelector.create()
                        .multi()
                        .count(3)
                        .origin((ArrayList<String>) mImageData)
                        .start(CertificateCertificationActivity.this, REQUEST_CODE_CHOICE_IMAGE);
            }

            @Override
            public void itemClicked(int position) {
                mImageData.remove(position);
                mMultiAdapter.setData(mImageData);
            }
        });
    }

    private void handlerSubmitResult(SubmitAuthenticateResult result) {
        hideLoadingDialog();
        if (result.isContent()) {
            AlertUtils.show(ResourceUtils.getString(R.string.submit_success));
            CertificateCertificationActivity.this.finish();
        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.submit_failure));
        }
    }


    //提交服务器审核
    private void submitDataToService() {
        CertificationBody body = new CertificationBody();
        body.setAuthenticatesId(authenticatesId);
        CertificationBean bean = new CertificationBean();
        bean.setCertificateTitle(mName);
        bean.setGrade(mAchievement);
        bean.setImgUrl(mUpLoadImages);
        bean.setGraduationDate(mTime);
        body.setInfo(bean);
        HashMap params = new HashMap();
        params.put(RequestConstant.KEY_AUTHENTICATION_INFO, body);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_CERTIFICATION_AUTHENTICATE_SUBMIT, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(certificateSubmitSubscription, uploadImageSubmitSubscription);
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
                mTime = certificateTime.getText().toString();
                mName = certificateName.getText().toString();
                mAchievement = achievementEd.getText().toString();
                if (TextUtils.isEmpty(mTime)) {
                    AlertUtils.toast(context, "时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mName)) {
                    AlertUtils.toast(context, "证书名字不能为空");
                    return;
                }
                mTotalImages = mImageData.size();
                if (mTotalImages > 0) {
                    uploadImagesToAli();
                } else {
                    AlertUtils.toast(context, "还未选择图片");
                }
                break;

        }
    }

    /**
     * 上传图片到阿里云
     */
    private void uploadImagesToAli() {
        showLoadingDialog(ResourceUtils.getString(R.string.dialog_summit_ing));
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE, ALiYunFileURLBuilder.PERSONALCERTIFICATE);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_CODE, String.valueOf(ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_CERTIFICATE));
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_SECTET);
        for (int i = 0; i < mImageData.size(); i++) {
            params.put(ALiYunFileURLBuilder.KEY_FILE_PATH, mImageData.get(i));
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD, params);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOICE_IMAGE) {
            ArrayList<String> result = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            mImageData = result;
            mMultiAdapter.setData(mImageData);
        }
    }
}
