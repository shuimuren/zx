package com.zhixing.work.zhixin.view.companyCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyCertificationActivity extends BaseTitleActivity {

    @BindView(R.id.company_iv)
    ImageView companyIv;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.identity_iv)
    ImageView identityIv;
    @BindView(R.id.identity)
    TextView identity;
    @BindView(R.id.ll_identity)
    LinearLayout llIdentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_certification);
        ButterKnife.bind(this);
        setTitle("公司认证");
    }

    @OnClick({R.id.ll_company, R.id.ll_identity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
                startActivity(new Intent(context, EnterpriseCertificationActivity.class));
                break;
            case R.id.ll_identity:
                startActivity(new Intent(context, PersonalQualificationActivity.class));
                break;
        }
    }
}
