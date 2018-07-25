package com.zhixing.work.zhixin.view.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.response.AuthenticateListResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 我的认证
 */
public class AuthenticationHallActivity extends BaseTitleActivity {

    @BindView(R.id.identity_iv)
    ImageView identityIv;
    @BindView(R.id.identity)
    TextView identity;
    @BindView(R.id.ll_identity)
    LinearLayout llIdentity;
    @BindView(R.id.education_iv)
    ImageView educationIv;
    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.ll_education)
    LinearLayout llEducation;
    @BindView(R.id.work_iv)
    ImageView workIv;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.ll_work)
    LinearLayout llWork;
    @BindView(R.id.certificate_iv)
    ImageView certificateIv;
    @BindView(R.id.certificate)
    TextView certificate;
    @BindView(R.id.ll_certificate)
    LinearLayout llCertificate;
    @BindView(R.id.skill_iv)
    ImageView skillIv;
    @BindView(R.id.skill)
    TextView skill;
    @BindView(R.id.ll_skill)
    LinearLayout llSkill;

    private Subscription authenticationListSubscription;
    private int identityStatus, educationStatus, workStatus, certificateStatus,skillStatus;
    private int idCardId;
    private int educationId;
    private int certificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_hall);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.user_attestation));
        authenticationListSubscription = RxBus.getInstance().toObservable(AuthenticateListResult.class).subscribe(
                result -> handlerAuthenticateListResult(result)
        );
        authenticateSetText(identityIv, identity, ResultConstant.AUTHENTICATE_STATUS_NULL);
        authenticateSetText(educationIv, education, ResultConstant.AUTHENTICATE_STATUS_NULL);
        authenticateSetText(workIv, work, ResultConstant.AUTHENTICATE_STATUS_NULL);
        authenticateSetText(certificateIv, certificate, ResultConstant.AUTHENTICATE_STATUS_NULL);
        getListData();
    }


    @OnClick({R.id.ll_identity, R.id.ll_education, R.id.ll_work, R.id.ll_certificate, R.id.ll_skill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //身份认证
            case R.id.ll_identity:
                switch (identityStatus) {
                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
                        Intent intent = new Intent(context, IdAuthenticationActivity.class);
                        intent.putExtra(IdAuthenticationActivity.INTENT_KEY_ID_AUTHENTICATION, String.valueOf(idCardId));
                        startActivity(intent);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        gotoCertificationStatusActivity(identityStatus,CertificationStatusActivity.INTENT_IDENTITY_TYPE);
                        break;
                }

                break;
            //学历认证
            case R.id.ll_education:
                switch (educationStatus) {
                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
                        Intent educationIntent = new Intent(context, EducationCertificationActivity.class);
                        educationIntent.putExtra(EducationCertificationActivity.INTENT_KEY_ID, educationId);
                        startActivity(educationIntent);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        gotoCertificationStatusActivity(educationStatus,CertificationStatusActivity.INTENT_EDUCATION_TYPE);
                        break;
                }

                break;
            //工作认证
            case R.id.ll_work:
                AlertUtils.show("工作认证" + ResourceUtils.getString(R.string.need_to_do));
//                switch (workStatus) {
//                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
//                        Intent certificationIntent = new Intent(context, CertificateCertificationActivity.class);
//                        certificationIntent.putExtra(CertificateCertificationActivity.INTENT_KEY_CERITIFICATION, certificationId);
//                        startActivity(certificationIntent);
//                        break;
//                    case ResultConstant.AUTHENTICATE_STATUS_ING:
//                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
//                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
//                        gotoCertificationStatusActivity(workStatus,CertificationStatusActivity.INTENT_WORK_TYPE);
//                        break;
//                }
                break;
            //证书认证
            case R.id.ll_certificate:
                switch (certificateStatus) {
                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
                        Intent certificationIntent = new Intent(context, CertificateCertificationActivity.class);
                        certificationIntent.putExtra(CertificateCertificationActivity.INTENT_KEY_CERITIFICATION, certificationId);
                        startActivity(certificationIntent);
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                        gotoCertificationStatusActivity(certificateStatus,CertificationStatusActivity.INTENT_CERTIFICATE_TYPE);
                        break;
                }
                break;
            //技能认证
            case R.id.ll_skill:
                AlertUtils.show("技能认证");
//                switch (skillStatus) {
//                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
//                        Intent certificationIntent = new Intent(context, CertificateCertificationActivity.class);
//                        certificationIntent.putExtra(CertificateCertificationActivity.INTENT_KEY_CERITIFICATION, certificationId);
//                        startActivity(certificationIntent);
//                        break;
//                    case ResultConstant.AUTHENTICATE_STATUS_ING:
//                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
//                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
//                        gotoCertificationStatusActivity(skillStatus,CertificationStatusActivity.INTENT_SKILL_TYPE);
//                        break;
//                }
                break;
        }
    }

    public void getListData() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_PERSONAL_AUTHENTICATES);
    }

    private void gotoCertificationStatusActivity(int status, int type) {
        CertificationStatusActivity.startCertificationStatusActivity(AuthenticationHallActivity.this, status, type);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_PERSONAL_AUTHENTICATES);
    }

    private void handlerAuthenticateListResult(AuthenticateListResult result) {
        if (result != null && result.getContent() != null) {
            for (int i = 0; i < result.getContent().size(); i++) {
                switch (result.getContent().get(i).getAuthenticatesType()) {
                    case ResultConstant.AUTHENTICATES_TYPE_IDENTITY_CARD:
                        authenticateSetText(identityIv, identity, result.getContent().get(i).getList().get(0).getAuthenticatesStatus());
                        idCardId = result.getContent().get(i).getList().get(0).getAuthenticatesId();
                        identityStatus = result.getContent().get(i).getList().get(0).getAuthenticatesStatus();
                        break;
                    case ResultConstant.AUTHENTICATES_TYPE_EDUCATION:
                        authenticateSetText(educationIv, education, result.getContent().get(i).getList().get(0).getAuthenticatesStatus());
                        educationId = result.getContent().get(i).getList().get(0).getAuthenticatesId();
                        educationStatus = result.getContent().get(i).getList().get(0).getAuthenticatesStatus();
                        break;
                    case ResultConstant.AUTHENTICATES_TYPE_WORK:
                        authenticateSetText(workIv, work, result.getContent().get(i).getList().get(0).getAuthenticatesStatus());
                        workStatus = result.getContent().get(i).getList().get(0).getAuthenticatesStatus();
                        break;
                    case ResultConstant.AUTHENTICATES_TYPE_CERTIFICATE:
                        authenticateSetText(certificateIv, certificate, result.getContent().get(i).getList().get(0).getAuthenticatesStatus());
                        certificationId = result.getContent().get(i).getList().get(0).getAuthenticatesId();
                        certificateStatus = result.getContent().get(i).getList().get(0).getAuthenticatesStatus();
                        break;
                }
            }
        }
    }

    private void authenticateSetText(ImageView im, TextView tv, int status) {
        switch (status) {
            case ResultConstant.AUTHENTICATE_STATUS_NULL:
                tv.setText(ResourceUtils.getString(R.string.authenticate_status_null));
                tv.setTextColor(ResourceUtils.getColor(R.color.color_default));
                im.setEnabled(false);
                break;
            case ResultConstant.AUTHENTICATE_STATUS_ING:
                tv.setText(ResourceUtils.getString(R.string.authenticate_status_ing));
                tv.setTextColor(ResourceUtils.getColor(R.color.color_waiting));
                im.setEnabled(false);
                break;
            case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                tv.setText(ResourceUtils.getString(R.string.authenticate_status_success));
                tv.setTextColor(ResourceUtils.getColor(R.color.color_success));
                im.setEnabled(true);
                break;
            case ResultConstant.AUTHENTICATE_STATUS_FAILURE:
                tv.setText(ResourceUtils.getString(R.string.authenticate_status_failure));
                tv.setTextColor(ResourceUtils.getColor(R.color.color_failure));
                im.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(authenticationListSubscription);
    }
}
