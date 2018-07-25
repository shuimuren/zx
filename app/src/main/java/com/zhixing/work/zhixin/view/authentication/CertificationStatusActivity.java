package com.zhixing.work.zhixin.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/7/25.
 * Description :认证状态
 */

public class CertificationStatusActivity extends BaseTitleActivity {

    private static final String INTENT_KEY_STATUS = "status";
    private static final String INTENT_KEY_CERTIFICATION_TYPE = "type";

//    public static final String INTENT_KEY_IDENTITY_TYPE = "identity";
//    public static final String INTENT_KEY_EDUCATION_TYPE = "education";
//    public static final String INTENT_KEY_WORK_TYPE = "work";
//    public static final String INTENT_KEY_CERTIFICATE = "certificate";
//    public static final String

    public static final int INTENT_IDENTITY_TYPE = 0;
    public static final int INTENT_EDUCATION_TYPE = 1;
    public static final int INTENT_WORK_TYPE = 2;
    public static final int INTENT_CERTIFICATE_TYPE = 3;
    public static final int INTENT_SKILL_TYPE = 4;
    @BindView(R.id.btn_identity_ing)
    Button btnIdentityIng;
    @BindView(R.id.ll_identity_status_ing)
    LinearLayout llIdentityStatusIng;
    @BindView(R.id.btn_identity_failure)
    Button btnIdentityFailure;
    @BindView(R.id.ll_identity_status_failure)
    LinearLayout llIdentityStatusFailure;
    @BindView(R.id.btn_identity_success)
    Button btnIdentitySuccess;
    @BindView(R.id.ll_identity_status_success)
    LinearLayout llIdentityStatusSuccess;
    @BindView(R.id.ll_identity)
    LinearLayoutCompat llIdentity;
    @BindView(R.id.btn_education_ing)
    Button btnEducationIng;
    @BindView(R.id.ll_education_status_ing)
    LinearLayout llEducationStatusIng;
    @BindView(R.id.btn_education_failure)
    Button btnEducationFailure;
    @BindView(R.id.ll_education_status_failure)
    LinearLayout llEducationStatusFailure;
    @BindView(R.id.btn_education_success)
    Button btnEducationSuccess;
    @BindView(R.id.ll_education_status_success)
    LinearLayout llEducationStatusSuccess;
    @BindView(R.id.ll_education)
    LinearLayoutCompat llEducation;
    @BindView(R.id.btn_certificate_ing)
    Button btnCertificateIng;
    @BindView(R.id.ll_certificate_status_ing)
    LinearLayout llCertificateStatusIng;
    @BindView(R.id.btn_certificate_failure)
    Button btnCertificateFailure;
    @BindView(R.id.ll_certificate_status_failure)
    LinearLayout llCertificateStatusFailure;
    @BindView(R.id.btn_certificate_success)
    Button btnCertificateSuccess;
    @BindView(R.id.ll_certificate_status_success)
    LinearLayout llCertificateStatusSuccess;
    @BindView(R.id.ll_certificate)
    LinearLayoutCompat llCertificate;


    public static void startCertificationStatusActivity(Activity activity, int status, int activityType) {
        Intent intent = new Intent(activity, CertificationStatusActivity.class);
        intent.putExtra(INTENT_KEY_STATUS, status);
        intent.putExtra(INTENT_KEY_CERTIFICATION_TYPE, activityType);
        activity.startActivity(intent);
    }

    private int mStatus;//认证状态
    private int mType; //认证类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_status);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mStatus = getIntent().getIntExtra(INTENT_KEY_STATUS, 0);
        mType = getIntent().getIntExtra(INTENT_KEY_CERTIFICATION_TYPE, 0);

        switch (mType) {
            case INTENT_IDENTITY_TYPE:
                setTitle(ResourceUtils.getString(R.string.identity_authentication));
                llIdentity.setVisibility(View.VISIBLE);
                switch (mStatus){
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                        llIdentityStatusIng.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                        llIdentityStatusSuccess.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        llIdentityStatusFailure.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case INTENT_EDUCATION_TYPE:
                setTitle(ResourceUtils.getString(R.string.education_authentication));
                llEducation.setVisibility(View.VISIBLE);
                switch (mStatus){
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                        llEducationStatusIng.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                        llEducationStatusSuccess.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        llEducationStatusFailure.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case INTENT_CERTIFICATE_TYPE:
                setTitle(ResourceUtils.getString(R.string.certificate_authentication));
                llCertificate.setVisibility(View.VISIBLE);
                switch (mStatus){
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                        llCertificateStatusIng.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                        llCertificateStatusSuccess.setVisibility(View.VISIBLE);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        llCertificateStatusFailure.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }


    @OnClick({R.id.btn_identity_ing, R.id.btn_identity_failure, R.id.btn_identity_success, R.id.btn_education_ing,
            R.id.btn_education_failure, R.id.btn_education_success, R.id.btn_certificate_ing, R.id.btn_certificate_failure,
            R.id.btn_certificate_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_identity_ing:
                this.finish();
                break;
            case R.id.btn_identity_failure:
                break;
            case R.id.btn_identity_success:
                this.finish();
                break;
            case R.id.btn_education_ing:
                this.finish();
                break;
            case R.id.btn_education_failure:
                this.finish();
                break;
            case R.id.btn_education_success:
                break;
            case R.id.btn_certificate_ing:
                this.finish();
                break;
            case R.id.btn_certificate_failure:
                break;
            case R.id.btn_certificate_success:
                this.finish();
                break;
        }
    }
}
