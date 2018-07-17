package com.zhixing.work.zhixin.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.UpDateInfoBody;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.SmsCodeResult;
import com.zhixing.work.zhixin.network.response.UpdatePasswordResult;
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
 * 更新密码
 */
public class UpDatePwdActivity extends BaseTitleActivity {

    public static final String KEY_INTENT_ROLE = "intentRole";
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
    @BindView(R.id.btn_update)
    Button btnLogin;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private Subscription mGetVerificationCodeSubscription;
    private Subscription mUpdatePasswordSubscription;
    private String mUserRole;
    private String mPhoneNumber;
    private String mPassword;
    private String mSmsCode;
    private String mSmsCodeTypeEnum; //短信类型
    private boolean mShowPassword ;
    private int time = 60;
    private Timer timer;// 短信重发倒计时
    private Repeat repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_pwd);
        ButterKnife.bind(this);
        context = this;
        setTitle(ResourceUtils.getString(R.string.update_password));
        mUserRole = getIntent().getStringExtra(KEY_INTENT_ROLE);
        mSmsCodeTypeEnum = RoleConstant.SMS_CODE_UPDATE;
        mShowPassword = false;
        mGetVerificationCodeSubscription = RxBus.getInstance().toObservable(SmsCodeResult.class).subscribe(
                result -> handlerSmsCodeResult(result)
        );
        mUpdatePasswordSubscription = RxBus.getInstance().toObservable(UpdatePasswordResult.class).subscribe(
                updateResult -> handlerUpdateResult(updateResult)
        );
    }

    /**
     * 获取验证码结果
     * @param result
     */
    private void handlerSmsCodeResult(SmsCodeResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            if(result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.get_sms_code_success));
                timer = new Timer();
                repeat = new Repeat();
                timer.schedule(repeat, 0, 1000);
                getVerificationCode.setEnabled(false);
            }else {
                AlertUtils.show(result.Message);
            }
        }else {
            AlertUtils.show(result.Message);
        }
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
        getVerificationCode.setBackgroundColor(getResources().getColor(R.color.bcompany));
        getVerificationCode.setTextColor(getResources().getColor(R.color.white));
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
     * 更新密码结果
     * @param result
     */
    private void handlerUpdateResult(UpdatePasswordResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            if(result.isContent()){
                AlertUtils.show(ResourceUtils.getString(R.string.update_password_success));
                this.finish();
            }else{
               AlertUtils.show(result.Message);
            }
        }else {
            AlertUtils.show(result.Message);
        }
    }


    @OnClick({R.id.get_verification_code, R.id.btn_update, R.id.btn_cancel,R.id.show_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verification_code:
                getVerificationCode();
                break;
            case R.id.btn_update:
                updatePassWord();
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
            case R.id.btn_cancel:
                this.finish();
                break;
        }
    }

    private void updatePassWord() {
        mPhoneNumber = editTelephone.getText().toString();
        mPassword = editPassword.getText().toString();
        mSmsCode = verificationCode.getText().toString();
        if(!Utils.isMobileNO1(mPhoneNumber)){
            AlertUtils.show(ResourceUtils.getString(R.string.alert_phone_number_unusable));
            return;
        }
        if(TextUtils.isEmpty(mPassword) || mPassword.length() < 6){
            AlertUtils.show(ResourceUtils.getString(R.string.password_hint));
            return;
        }
        if(TextUtils.isEmpty(mSmsCode) || mSmsCode.length() < 6){
            AlertUtils.show(ResourceUtils.getString(R.string.verification_code_is_null));
            return;
        }
        UpDateInfoBody  infoBody = new UpDateInfoBody();
        infoBody.setPhoneNum(mPhoneNumber);
        infoBody.setNewPassword(mPassword);
        infoBody.setRole(mUserRole);
        infoBody.setSmsCode(mSmsCode);
        Map params = new HashMap();
        params.put(RequestConstant.KEY_UPDATE_PSAAWORD_BODY,infoBody);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_UPDATE_PASSWORD,params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetVerificationCodeSubscription,mUpdatePasswordSubscription);
    }

    /**
     * 获取验证码
     */
    public void getVerificationCode() {
        mPhoneNumber = editTelephone.getText().toString();
        if(!Utils.isMobileNO1(mPhoneNumber)){
            AlertUtils.show(ResourceUtils.getString(R.string.alert_phone_number_unusable));
            return;
        }
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PHONE_NUMBER,mPhoneNumber);
        params.put(RequestConstant.KEY_USER_ROLE_ENUM,mUserRole);
        params.put(RequestConstant.KEY_SMS_CODE_TYPE_ENUM,mSmsCodeTypeEnum);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_VERIFICATION_CODE,params);
    }
}
