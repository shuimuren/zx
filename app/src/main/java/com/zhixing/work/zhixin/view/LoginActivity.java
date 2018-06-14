package com.zhixing.work.zhixin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
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
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle("登录");
        setLeftNotVisible();
        context = this;
        type = "10";
        initView();
    }


    private void initView() {
        if (!TextUtils.isEmpty(SettingUtils.getPhoneNumber())) {
            phoneEd.setText(SettingUtils.getPhoneNumber());
            phoneEd.setSelection(SettingUtils.getPhoneNumber().length());

        }
    }

    @OnClick({R.id.tv_login_person, R.id.tv_login_company, R.id.btn_login, R.id.register, R.id.forget_password, R.id.show_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_person:
                type = "10";
                tvLoginPerson.setTextSize(20);
                tvLoginPerson.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                tvLoginCompany.setTextSize(16);
                tvLoginCompany.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                break;
            case R.id.tv_login_company:
                type = "20";
                tvLoginPerson.setTextSize(16);
                tvLoginPerson.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                tvLoginCompany.setTextSize(20);
                tvLoginCompany.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                break;
            case R.id.register:
                startActivity(new Intent(context, RegistActivity.class));
                break;
            case R.id.forget_password:
                startActivity(new Intent(context, UpDatePwdActivity.class));
                break;

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
            case R.id.btn_login:
                phone = phoneEd.getText().toString();
                password = passwordEd.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    AlertUtils.toast(context, "手机号不能为空");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    AlertUtils.toast(context, "密码不能为空");
                    return;
                } else {
                    showLoadingDialog("登陆中");
                    OkUtils.getInstances().httpPost(context, JavaConstant.goLogin,
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
                                    if (response.getCode() == 10000) {
                                        if (!TextUtils.isEmpty(response.getContent())) {
                                            SettingUtils.putToken(response.getContent());

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
