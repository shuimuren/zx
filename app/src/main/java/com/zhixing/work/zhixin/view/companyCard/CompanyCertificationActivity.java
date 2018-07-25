package com.zhixing.work.zhixin.view.companyCard;

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
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.CompanyCertificationStatusResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 公司认证
 */
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

    private Subscription mCompanyCertificationSubscription;
    private int mBusinessStatus;
    private int mIdCardStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_certification);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.certified_company));
        mBusinessStatus = ResultConstant.AUTHENTICATE_STATUS_NULL;
        mIdCardStatus = ResultConstant.AUTHENTICATE_STATUS_NULL;
        authenticateSetText(companyIv, company, ResultConstant.AUTHENTICATE_STATUS_NULL);
        authenticateSetText(identityIv, identity, ResultConstant.AUTHENTICATE_STATUS_NULL);
        mCompanyCertificationSubscription = RxBus.getInstance().toObservable(CompanyCertificationStatusResult.class).subscribe(
                result -> handlerCompanyCertificationResult(result)
        );
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_COMPANY_CERTIFICATION_STATUS);
    }

    private void handlerCompanyCertificationResult(CompanyCertificationStatusResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                mBusinessStatus = result.getContent().getBusinessLicenseStatus();
                mIdCardStatus = result.getContent().getManagerIdCardStatus();
                authenticateSetText(companyIv, company, result.getContent().getBusinessLicenseStatus());
                authenticateSetText(identityIv, identity, result.getContent().getManagerIdCardStatus());
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    @OnClick({R.id.ll_company, R.id.ll_identity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //公司认证
            case R.id.ll_company:
                switch (mBusinessStatus){
                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
                        startActivity(new Intent(context, EnterpriseCertificationActivity.class));
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:

                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                        AlertUtils.show(ResourceUtils.getString(R.string.audit_ing_please_waite));
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                        break;
                }

                break;
            //身份认证
            case R.id.ll_identity:
                switch (mIdCardStatus){
                    case ResultConstant.AUTHENTICATE_STATUS_NULL:
                        startActivity(new Intent(context, PersonalQualificationActivity.class));
                    case ResultConstant.AUTHENTICATE_STATUS_FAILURE:

                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_ING:
                        AlertUtils.show(ResourceUtils.getString(R.string.audit_ing_please_waite));
                        break;
                    case ResultConstant.AUTHENTICATE_STATUS_SUCCEED:
                        break;
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mCompanyCertificationSubscription);
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
}
