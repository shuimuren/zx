package com.zhixing.work.zhixin.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.JudgeTelephoneUsableResult;
import com.zhixing.work.zhixin.network.response.RegisterResult;
import com.zhixing.work.zhixin.network.response.SmsCodeResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/16.
 * Description:
 */

public class RegisterActivity extends BaseTitleActivity {

    @BindView(R.id.tv_personal_register)
    TextView tvPersonalRegister;
    @BindView(R.id.tv_company_register)
    TextView tvCompanyRegister;
    @BindView(R.id.editTelephone)
    ClearEditText editTelephone;
    @BindView(R.id.verification_code)
    ClearEditText verificationCode;
    @BindView(R.id.get_verification_code)
    TextView getVerificationCode;
    @BindView(R.id.editPassword)
    ClearEditText editPassword;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.btn_make_sure)
    Button btnMakeSure;
    @BindView(R.id.regist_check)
    CheckBox registCheck;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;


    private Subscription mVerifyCodeSubscription;
    private Subscription mRegisterSubscription;
    private Subscription mTelephoneUsableSubscription;
    private String mUserRole;
    private String mPhoneNumber;
    private String mPassword;
    private String mSmsCode;
    private String mSmsCodeTypeEnum; //短信类型
    private boolean mShowPassword;
    private int time = 60;
    private Timer timer;// 短信重发倒计时
    private Repeat repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = this;
        setTitle(ResourceUtils.getString(R.string.register));
        initView();
    }

    private void initView() {
        mSmsCodeTypeEnum = RoleConstant.SMS_CODE_REGISTER;
        mUserRole = RoleConstant.PERSONAL_ROLE;
        tvPersonalRegister.setSelected(true);
        tvCompanyRegister.setSelected(false);
        mShowPassword = false;
        String s = ResourceUtils.getString(R.string.agreement);
        tvProtocol.setText(Utils.changeColor(s, ResourceUtils.getColor(R.color.login_company_tv), 1, s.length() - 1));
        mVerifyCodeSubscription = RxBus.getInstance().toObservable(SmsCodeResult.class).subscribe(
                result -> handlerVerifyCodeResult(result)
        );

        mRegisterSubscription = RxBus.getInstance().toObservable(RegisterResult.class).subscribe(
                registerResult -> handlerRegisterResult(registerResult)
        );

        mTelephoneUsableSubscription = RxBus.getInstance().toObservable(JudgeTelephoneUsableResult.class).subscribe(
                judgeResult -> handlerJudgeResult(judgeResult)
        );

    }

    private void handlerJudgeResult(JudgeTelephoneUsableResult judgeResult) {
        if (judgeResult.Code == NetworkConstant.SUCCESS_CODE) {
            if (judgeResult.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.register_false));
            } else {
                getVerificationCode();
            }
        }
    }

    private void handlerRegisterResult(RegisterResult result) {
        hideLoadingDialog();
        if (TextUtils.isEmpty(result.getContent())) {
            AlertUtils.show(result.Message);
        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.register_success));
            this.finish();

        }
    }

    private void handlerVerifyCodeResult(SmsCodeResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.get_sms_code_success));
                timer = new Timer();
                repeat = new Repeat();
                timer.schedule(repeat, 0, 1000);
                getVerificationCode.setEnabled(false);
            } else {
                AlertUtils.show(result.Message);
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    @OnClick({R.id.tv_personal_register, R.id.tv_company_register, R.id.get_verification_code,
            R.id.show_password, R.id.btn_make_sure, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_personal_register:
                mUserRole = RoleConstant.PERSONAL_ROLE;
                tvPersonalRegister.setSelected(true);
                tvCompanyRegister.setSelected(false);
                break;
            case R.id.tv_company_register:
                mUserRole = RoleConstant.ENTERPRISE_ROLE;
                tvPersonalRegister.setSelected(false);
                tvCompanyRegister.setSelected(true);
                break;
            case R.id.get_verification_code:
                judeTelephone();
                break;
            case R.id.show_password:
                if (mShowPassword) {// 显示密码
                    showPassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editPassword.setSelection(editPassword.getText().toString().length());
                    mShowPassword = !mShowPassword;
                } else {// 隐藏密码
                    showPassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editPassword.setSelection(editPassword.getText().toString().length());
                    mShowPassword = !mShowPassword;
                }
                break;
            case R.id.btn_make_sure:
                togoRegister();
                break;
            case R.id.tv_protocol:
                break;
        }
    }

    private void judeTelephone() {
        mPhoneNumber = editTelephone.getText().toString();
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PHONE_NUMBER, mPhoneNumber);
        params.put(RequestConstant.KEY_ROLE, mUserRole);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_TELEPHONE_USABLE, params);
    }

    private void togoRegister() {
        mPhoneNumber = editTelephone.getText().toString();
        mSmsCode = verificationCode.getText().toString();
        mPassword = editPassword.getText().toString();
        if (!Utils.isMobileNO1(mPhoneNumber)) {
            AlertUtils.toast(context, ResourceUtils.getString(R.string.alert_phone_number_unusable));
            return;
        }
        if (TextUtils.isEmpty(mPassword) ||mPassword.length() < 6) {
            AlertUtils.toast(context, ResourceUtils.getString(R.string.password_hint));
            return;
        }

        if (!TextUtils.isDigitsOnly(mSmsCode)) {
            AlertUtils.toast(context, ResourceUtils.getString(R.string.verification_code_is_null));
            return;
        }
        if (!registCheck.isChecked()) {
            AlertUtils.toast(context, ResourceUtils.getString(R.string.agree_agreement));
            return;
        }
        showLoadingDialog(ResourceUtils.getString(R.string.registering));
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PHONE_NUMBER, mPhoneNumber);
        params.put(RequestConstant.KEY_PASSWORD, mPassword);
        params.put(RequestConstant.KEY_ROLE, mUserRole);
        params.put(RequestConstant.KEY_VERIFY_CODE, mSmsCode);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_REGISTER, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mRegisterSubscription, mVerifyCodeSubscription, mTelephoneUsableSubscription);
    }

    class Repeat extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    getVerificationCode.setText(time + "秒后重新发送");
                    if (time == 0) {
                        setCanGetPin();
                    }
                }
            });
        }
    }

    private void setCanGetPin() {
        getVerificationCode.setText(ResourceUtils.getString(R.string.get_verification_code));
        getVerificationCode.setEnabled(true);
        if (repeat != null) {
            repeat.cancel();
            repeat = null;
        }
        if (timer != null) {
            timer = null;
        }
        time = 60;
    }

    /**
     * 获取验证码
     */
    public void getVerificationCode() {
        mPhoneNumber = editTelephone.getText().toString();
        if (!Utils.isMobileNO1(mPhoneNumber)) {
            AlertUtils.show(ResourceUtils.getString(R.string.alert_phone_number_unusable));
            return;
        }
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PHONE_NUMBER, mPhoneNumber);
        params.put(RequestConstant.KEY_USER_ROLE_ENUM, mUserRole);
        params.put(RequestConstant.KEY_SMS_CODE_TYPE_ENUM, mSmsCodeTypeEnum);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_VERIFICATION_CODE, params);
    }
}
