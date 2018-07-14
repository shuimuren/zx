package com.zhixing.work.zhixin.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.common.ScreenUtil;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistActivity extends BaseTitleActivity {

    @BindView(R.id.tv_regist_personl_account)
    TextView tvRegistPersonlAccount;
    @BindView(R.id.tv_regist_company_account)
    TextView tvRegistCompanyAccount;
    @BindView(R.id.regist_phone_num_ed)
    ClearEditText registPhoneNumEd;
    @BindView(R.id.btn_regist_get_code)
    TextView btnRegistGetCode;
    @BindView(R.id.password_ed)
    ClearEditText passwordEd;
    @BindView(R.id.iv_hide_pwd)
    RadioButton ivHidePwd;
    @BindView(R.id.btn_go_regist)
    TextView btnGoRegist;
    @BindView(R.id.regist_check)
    CheckBox registCheck;
    @BindView(R.id.code_ed)
    ClearEditText codeEd;
    private String type = RoleConstant.PERSONAL_ROLE;
    private String phone;
    private String password;
    private String code;
    private Context context;
    private int time = 60;
    private Timer timer;// 短信重发倒计时
    private Repeat repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setOrientation(this, ScreenUtil.ORIENTATION_HEIGHT);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        context = this;
        setTitle(ResourceUtils.getString(R.string.register));
        initView();
    }

    private void initView() {
        registPhoneNumEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEd.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = registPhoneNumEd.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    if (Utils.isMobileNO1(content)) {
                        isRegist(content);
                    } else {

                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_regist_personl_account, R.id.tv_regist_company_account, R.id.btn_regist_get_code, R.id.btn_go_regist, R.id.regist_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist_personl_account:
                type = RoleConstant.PERSONAL_ROLE;
                tvRegistPersonlAccount.setTextSize(20);
                tvRegistPersonlAccount.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                tvRegistCompanyAccount.setTextSize(16);
                tvRegistCompanyAccount.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                break;
            case R.id.tv_regist_company_account:
                type = RoleConstant.ENTERPRISE_ROLE;
                tvRegistPersonlAccount.setTextSize(16);
                tvRegistPersonlAccount.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                tvRegistCompanyAccount.setTextSize(20);
                tvRegistCompanyAccount.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                break;
            case R.id.btn_regist_get_code:
                phone = registPhoneNumEd.getText().toString();
                if (Utils.isMobileNO1(phone)) {
                    timer = new Timer();
                    repeat = new Repeat();
                    timer.schedule(repeat, 0, 1000);
                    btnRegistGetCode.setClickable(false);
                    btnRegistGetCode.setBackgroundColor(getResources().getColor(R.color.withDrawBankText));
                    OkUtils.getInstances().httpGet(context, RequestConstant.GET_REGISTER_CODE, JavaParamsUtils.getInstances().Short_Message(phone, type, "10"), new TypeToken<EntityObject<Object>>() {
                    }.getType(), new ResultCallBackListener<Object>() {
                        @Override
                        public void onFailure(int errorId, String msg) {
                            AlertUtils.toast(context, msg);
                        }

                        @Override
                        public void onSuccess(EntityObject<Object> response) {
                            AlertUtils.toast(context, ResourceUtils.getString(R.string.get_success_and_waiting));
                        }
                    });
                } else {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_phone_number_unusable));
                }
                break;
            case R.id.btn_go_regist:
                phone = registPhoneNumEd.getText().toString();
                code = codeEd.getText().toString();
                password = passwordEd.getText().toString();
                if (!Utils.isMobileNO1(phone)) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_phone_number_unusable));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_pass_word_unusable));
                    return;
                }

                if (!TextUtils.isDigitsOnly(code)) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.verification_code_is_null));
                    return;
                }
                if (!registCheck.isChecked()) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.agree_agreement));
                    return;
                }
                showLoading();
                Regist(phone, password, code);

                break;
            case R.id.regist_check:
                break;
        }
    }

    //验证码等待
    class Repeat extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    btnRegistGetCode.setText(time + ResourceUtils.getString(R.string.send_again));
                    if (time == 0) {
                        setCanGetPin();
                    }
                }
            });
        }
    }

    private void setCanGetPin() {
        btnRegistGetCode.setText(ResourceUtils.getString(R.string.get_verification_code));
        btnRegistGetCode.setClickable(true);
        btnRegistGetCode.setBackgroundColor(getResources().getColor(R.color.bcompany));
        btnRegistGetCode.setTextColor(getResources().getColor(R.color.white));
        if (repeat != null) {
            repeat.cancel();
            repeat = null;
        }
        if (timer != null) {
            timer = null;
        }
        time = 60;
    }

    //判断账号是否注册
    private void isRegist(String phone) {
        OkUtils.getInstances().httpGet(context, RequestConstant.IS_REGISTER, JavaParamsUtils.getInstances().isRegist(phone, type), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getContent()) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.account_unusable));
                }


            }
        });
    }

    //注册账号
    private void Regist(String phone, String password, String code) {
        OkUtils.getInstances().httpPost(context, RequestConstant.GO_REGISTER, JavaParamsUtils.getInstances().Registered(phone, password, type, code), new TypeToken<EntityObject<String>>() {
        }.getType(), new ResultCallBackListener<String>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<String> response) {
                hideLoadingDialog();
                if (!TextUtils.isEmpty(response.getContent())) {
                    AlertUtils.toast(context, ResourceUtils.getString(R.string.register_success));
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }

            }
        });
    }
}
