package com.zhixing.work.zhixin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.constant.DiscernConstant;
import com.zhixing.work.zhixin.dialog.LoadingDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.LoginResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.RegularUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;


public class LoginActivity extends FragmentActivity {

    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.mark_personal)
    View markPersonal;
    @BindView(R.id.ll_personal)
    LinearLayout llPersonal;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.mark_company)
    View markCompany;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.editTelephone)
    ClearEditText editTelephone;
    @BindView(R.id.editPassword)
    ClearEditText editPassword;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private String defaultRole;
    private Subscription mLoginSubscription;
    private boolean mShowPassword;
    private String phone;
    private LoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mLoginSubscription = RxBus.getInstance().toObservable(LoginResult.class).subscribe(
                result -> handlerLoginResult(result)
        );
        setRoleView();
        mDialog = new LoadingDialog(LoginActivity.this);
    }

    private void setRoleView() {
        if(TextUtils.isEmpty(SettingUtils.getRoleInfo()) || SettingUtils.getRoleInfo().equals(DiscernConstant.PERSONAL_ROLE)){
            defaultRole = DiscernConstant.PERSONAL_ROLE;
            tvPersonal.setSelected(true);
            llPersonal.setSelected(true);
            markPersonal.setVisibility(View.VISIBLE);
            tvCompany.setSelected(false);
            llCompany.setSelected(false);
            markCompany.setVisibility(View.INVISIBLE);
        }else {
            defaultRole = DiscernConstant.ENTERPRISE_ROLE;
            tvPersonal.setSelected(false);
            llPersonal.setSelected(false);
            markPersonal.setVisibility(View.INVISIBLE);
            tvCompany.setSelected(true);
            llCompany.setSelected(true);
            markCompany.setVisibility(View.VISIBLE);
        }
    }

    private void handlerLoginResult(LoginResult result) {
        if(mDialog != null){
            mDialog.dismiss();
        }
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (!TextUtils.isEmpty(result.getContent())) {
                SettingUtils.putToken(result.getContent());
                SettingUtils.setHttpRequestHead(result.getContent());
                String token = Utils.getFromBase64(result.getContent().substring(result.getContent().indexOf(".") + 1, result.getContent().lastIndexOf(".")));
                if (!TextUtils.isEmpty(token)) {
                    SettingUtils.putTokenBean(token);
                }
                SettingUtils.putRole(defaultRole);
                SettingUtils.putPhoneNumber(phone);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }


    @OnClick({R.id.ll_personal, R.id.ll_company, R.id.show_password, R.id.btn_login, R.id.tvForgetPassword,
            R.id.btn_register,R.id.imgWeChat,R.id.imgQQ})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_personal:
                defaultRole = DiscernConstant.PERSONAL_ROLE;
                tvPersonal.setSelected(true);
                llPersonal.setSelected(true);
                markPersonal.setVisibility(View.VISIBLE);
                tvCompany.setSelected(false);
                llCompany.setSelected(false);
                markCompany.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_company:
                defaultRole = DiscernConstant.ENTERPRISE_ROLE;
                tvPersonal.setSelected(false);
                llPersonal.setSelected(false);
                markPersonal.setVisibility(View.INVISIBLE);
                tvCompany.setSelected(true);
                llCompany.setSelected(true);
                markCompany.setVisibility(View.VISIBLE);
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
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.tvForgetPassword:
                Intent intentPassword = new Intent(LoginActivity.this,UpDatePwdActivity.class);
                intentPassword.putExtra(UpDatePwdActivity.KEY_INTENT_ROLE,defaultRole);
                startActivity(intentPassword);
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.imgWeChat:
                break;
            case R.id.imgQQ:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mLoginSubscription);
    }

    private void doLogin() {
        phone = editTelephone.getText().toString();
        String password = editPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            AlertUtils.show(ResourceUtils.getString(R.string.alert_phone_number_is_null));
            return;
        }else if(!RegularUtils.isMobileNo(phone)){
            AlertUtils.show(ResourceUtils.getString(R.string.alert_phone_number_unusable));
            return;
        } else if (TextUtils.isEmpty(password)) {
            AlertUtils.show(ResourceUtils.getString(R.string.alert_pass_word_unusable));
            return;
        }else if(password.length() < 6){
            AlertUtils.show(ResourceUtils.getString(R.string.alert_pass_word_length));
            return;
        }
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PHONE_NUMBER, phone);
        params.put(RequestConstant.KEY_PASSWORD, password);
        params.put(RequestConstant.KEY_USER_ROLE_ENUM, defaultRole);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_LOGIN, params);
        mDialog.setLoadingMessage(ResourceUtils.getString(R.string.loading));
        mDialog.setCancelable(false);
        mDialog.show();
    }
}
