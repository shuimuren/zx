package com.zhixing.work.zhixin.view.resume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/*简历设置*/
public class PrivacySettingsActivity extends BaseTitleActivity {

    @BindView(R.id.ll_shielding_company)
    LinearLayout llShieldingCompany;
    @BindView(R.id.public_evaluation)
    CheckBox publicEvaluation;
    @BindView(R.id.ll_public_evaluation)
    LinearLayout llPublicEvaluation;
    @BindView(R.id.hidden_resume)
    CheckBox hiddenResume;
    @BindView(R.id.ll_hidden_resume)
    LinearLayout llHiddenResume;
    private Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);
        ButterKnife.bind(this);
        setTitle("隐私设置");
        Bundle bundle = getIntent().getExtras();
        resume = (Resume) bundle.get("bean");
        initView();
    }

    private void initView() {
        if (resume == null) {
            return;
        }
        if (resume.isHiddenResume()) {
            hiddenResume.setChecked(resume.isHiddenResume());
        }
        if (resume.isHiddenEvaluate()) {
            publicEvaluation.setChecked(resume.isHiddenEvaluate());
        }

    }

    //修改简历设置
    private void upUserData(final String type, final String value) {
        final RequestBody body = new FormBody.Builder()
                .add("Id", resume.getId() + "")
                .add(type, value).build();
        OkUtils.getInstances().httpatch(body, context, JavaConstant.Resume, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
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
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            EventBus.getDefault().post(new ResumeRefreshEvent(true));
                            switch (type) {
                                case MyResumeActivity.HIDDENRESUME:
                                    hiddenResume.setChecked(Boolean.parseBoolean(value));
                                    break;
                                case MyResumeActivity.HIDDENEVALUATE:
                                    publicEvaluation.setChecked(Boolean.parseBoolean(value));
                                    break;
                            }

                        }
                    } else {

                        switch (type) {
                            case MyResumeActivity.HIDDENRESUME:
                                hiddenResume.setChecked(!Boolean.parseBoolean(value));
                                break;
                            case MyResumeActivity.HIDDENEVALUATE:
                                publicEvaluation.setChecked(!Boolean.parseBoolean(value));
                                break;
                        }
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {

                    switch (type) {
                        case MyResumeActivity.HIDDENRESUME:
                            hiddenResume.setChecked(!Boolean.parseBoolean(value));
                            break;
                        case MyResumeActivity.HIDDENEVALUATE:
                            publicEvaluation.setChecked(!Boolean.parseBoolean(value));
                            break;
                    }
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
    }

    @OnClick({R.id.ll_shielding_company, R.id.public_evaluation, R.id.hidden_resume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shielding_company:
                startActivity(new Intent(context, ShieldingCompanyActivity.class));
                break;
            case R.id.public_evaluation:
                upUserData(MyResumeActivity.HIDDENEVALUATE, publicEvaluation.isChecked() + "");
                break;
            case R.id.hidden_resume:
                upUserData(MyResumeActivity.HIDDENRESUME, hiddenResume.isChecked() + "");
                break;
        }
    }
}
