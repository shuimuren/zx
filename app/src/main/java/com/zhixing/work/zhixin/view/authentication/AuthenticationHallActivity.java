package com.zhixing.work.zhixin.view.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_hall);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.user_attestation));
    }

    @OnClick({R.id.ll_identity, R.id.ll_education, R.id.ll_work, R.id.ll_certificate, R.id.ll_skill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //身份认证
            case R.id.ll_identity:
                startActivity(new Intent(context, IdAuthenticationActivity.class));
                break;
            //学历认证
            case R.id.ll_education:
                startActivity(new Intent(context, EducationCertificationActivity.class));
                break;
            //工作认证
            case R.id.ll_work:
                AlertUtils.show("工作认证");
                break;
            //证书认证
            case R.id.ll_certificate:
                startActivity(new Intent(context, CertificateCertificationActivity.class));
                break;
            //技能认证
            case R.id.ll_skill:
                AlertUtils.show("技能认证");
                break;
        }
    }
}
