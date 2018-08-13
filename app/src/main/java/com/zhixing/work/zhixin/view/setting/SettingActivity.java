package com.zhixing.work.zhixin.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置页面
 */
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
    public void onLogOutViewClicked() {
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
//        OkUtils.getInstances().httpDelete(context, RequestConstant.LOG_OUT, JavaParamsUtils.getInstances().project(), new TypeToken<EntityObject<Boolean>>() {
//        }.getType(), new ResultCallBackListener<Boolean>() {
//            @Override
//            public void onFailure(int errorId, String msg) {
//                hideLoadingDialog();
//                AlertUtils.toast(context, msg);
//            }
//
//            @Override
//            public void onSuccess(EntityObject<Boolean> response) {
//                hideLoadingDialog();
//                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
//                    if (response.getContent()) {
//                        ZxApplication.getInstance().finishAllActivity();
//                        startActivity(new Intent(context, LoginActivity.class));
//                        clearLoginState();
//                    } else {
//                      //  AlertUtils.show();
//                    }
//                } else {
//                    AlertUtils.toast(context, response.getMessage());
//                }
//            }
//        });
        ZxApplication.getInstance().finishAllActivity();
        startActivity(new Intent(context, LoginActivity.class));
        clearLoginState();

    }

    @OnClick({R.id.rlUserAgreement, R.id.rlPrivacyStrategy, R.id.account_security, R.id.rlAbout, R.id.check_version, R.id.data_clean})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //用户协议
            case R.id.rlUserAgreement:
                break;
            //隐私策略
            case R.id.rlPrivacyStrategy:
                break;
            //修改密码
            case R.id.account_security:
                break;
            //关于职信
            case R.id.rlAbout:
                AlertUtils.show(ResourceUtils.getString(R.string.about_zhi_xin));
                break;
            //检查更新
            case R.id.check_version:
                AlertUtils.show("检查更新");
                break;
            //清除缓存
            case R.id.data_clean:
                break;
        }
    }

}
