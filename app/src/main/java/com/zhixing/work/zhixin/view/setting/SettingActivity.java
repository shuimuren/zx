package com.zhixing.work.zhixin.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseTitleActivity {

    @BindView(R.id.rlUserAgreement)
    RelativeLayout rlUserAgreement;
    @BindView(R.id.rlPrivacyStrategy)
    RelativeLayout rlPrivacyStrategy;
    @BindView(R.id.account_security)
    RelativeLayout accountSecurity;
    @BindView(R.id.rlAbout)
    RelativeLayout rlAbout;
    @BindView(R.id.check_version)
    RelativeLayout checkVersion;
    @BindView(R.id.data_text)
    TextView dataText;
    @BindView(R.id.data_clean)
    RelativeLayout dataClean;
    @BindView(R.id.btnLogOut)
    TextView btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTitle("设置");
    }

    @OnClick(R.id.btnLogOut)
    public void onViewClicked() {
        LogOut();

    }


    public void clearLoginState() {
        SettingUtils.putToken(null);
        SettingUtils.putFansAppApiSessionId(null);
        SettingUtils.putFansAppApiSessionId(null);
        SettingUtils.putPhoneNumber(null);
        SettingUtils.putUserId(null);
        SettingUtils.putPass_Id(null);
        SettingUtils.putAvatar(null);


    }

    //退出登录
    private void LogOut() {

        OkUtils.getInstances().httpDelete(context, JavaConstant.outLogin, JavaParamsUtils.getInstances().project(), new TypeToken<EntityObject<Boolean>>() {
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
                        ZxApplication.getInstance().finishAllActivity();
                        startActivity(new Intent(context, LoginActivity.class));
                        clearLoginState();
                    } else {

                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });


    }

}
