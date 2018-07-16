package com.zhixing.work.zhixin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.RegularUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseTitleActivity {

    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.tv_login_person)
    TextView tvLoginPerson;
    @BindView(R.id.tv_login_company)
    TextView tvLoginCompany;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.phone_ed)
    ClearEditText phoneEd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.show_password)
    ImageView showPassword;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.rl_login)
    CardView rlLogin;
    @BindView(R.id.password_ed)
    EditText passwordEd;

    private String type;
    private String phone;
    private String password;
    private Boolean isShowPassword = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.login));
        setLeftNotVisible();
        context = this;
        type = RoleConstant.PERSONAL_ROLE;
        initView();
    }


    private void initView() {
        if (!TextUtils.isEmpty(SettingUtils.getPhoneNumber())) {
            phoneEd.setText(SettingUtils.getPhoneNumber());
            phoneEd.setSelection(SettingUtils.getPhoneNumber().length());
        }
    }

    @OnClick({R.id.tv_login_person, R.id.tv_login_company, R.id.btn_login, R.id.cardView, R.id.forget_password, R.id.show_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //切换账户类型切换为个人用户
            case R.id.tv_login_person:
                type = RoleConstant.PERSONAL_ROLE;
                tvLoginPerson.setTextSize(20);
                tvLoginPerson.setTextColor(ResourceUtils.getColor(R.color.login_person_tv));
                tvLoginCompany.setTextSize(16);
                tvLoginCompany.setTextColor(ResourceUtils.getColor(R.color.login_company_tv));
                break;
            //切换账户类型切换为企业
            case R.id.tv_login_company:
                type = RoleConstant.ENTERPRISE_ROLE;
                tvLoginPerson.setTextSize(16);
                tvLoginPerson.setTextColor(ResourceUtils.getColor(R.color.login_company_tv));
                tvLoginCompany.setTextSize(20);
                tvLoginCompany.setTextColor(ResourceUtils.getColor(R.color.login_person_tv));
                break;
            //注册
            case R.id.cardView:
                startActivity(new Intent(context, RegistActivity.class));
                break;
            //忘记密码
            case R.id.forget_password:
                startActivity(new Intent(context, UpDatePwdActivity.class));
                break;
            //显示密码
            case R.id.show_password:
                if (isShowPassword) {// 显示密码
                    showPassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    passwordEd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEd.setSelection(passwordEd.getText().toString().length());
                    isShowPassword = !isShowPassword;
                } else {// 隐藏密码
                    showPassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    passwordEd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEd.setSelection(passwordEd.getText().toString().length());
                    isShowPassword = !isShowPassword;
                }
                break;
            //登录
            case R.id.btn_login:
                phone = phoneEd.getText().toString();
                password = passwordEd.getText().toString();
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
                } else {
                    showLoadingDialog(ResourceUtils.getString(R.string.landing));
                    OkUtils.getInstances().httpPost(context, RequestConstant.GO_LOGIN,
                            JavaParamsUtils.getInstances().Login(phone, password, type), new TypeToken<EntityObject<String>>() {
                            }.getType(), new ResultCallBackListener<String>() {
                                @Override
                                public void onFailure(int errorId, String msg) {
                                    hideLoadingDialog();
                                    AlertUtils.toast(context, msg);
                                }
                                @Override
                                public void onSuccess(EntityObject<String> response) {
                                    hideLoadingDialog();
                                    if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                                        if (!TextUtils.isEmpty(response.getContent())) {
                                            SettingUtils.putToken(response.getContent());
                                            SettingUtils.setHttpRequestHead(response.getContent());
                                            String token = Utils.getFromBase64(response.getContent().substring(response.getContent().indexOf(".") + 1, response.getContent().lastIndexOf(".")));
                                            if (!TextUtils.isEmpty(token)) {
                                                SettingUtils.putTokenBean(token);
                                            }

                                            SettingUtils.putPhoneNumber(phone);
                                            startActivity(new Intent(context, MainActivity.class));
                                            finish();
                                        }
                                    } else {
                                        AlertUtils.toast(context, response.getMessage());
                                    }
                                }
                            });
                    break;

                }
        }
    }


}
