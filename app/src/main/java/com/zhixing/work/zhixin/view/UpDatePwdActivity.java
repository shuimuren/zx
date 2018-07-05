package com.zhixing.work.zhixin.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.UpdateBean;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.http.HttpHeadUtils;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UpDatePwdActivity extends BaseTitleActivity {

    @BindView(R.id.tv_update_person)
    TextView tvUpdatePerson;
    @BindView(R.id.tv_update_date_company)
    TextView tvUpdateDateCompany;
    @BindView(R.id.code_ed)
    EditText codeEd;
    @BindView(R.id.btn_update_code)
    TextView btnUpdateCode;
    @BindView(R.id.password_ed)
    EditText passwordEd;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.btn_go_update)
    TextView btnGoUpdate;
    @BindView(R.id.phone_ed)
    EditText phoneEd;
    private String type = RoleConstant.PERSONAL_ROLE;
    private String phone;
    private String password;
    private String confirmpassword;
    private String code;
    private Context context;
    private int time = 60;
    private Timer timer;// 短信重发倒计时
    private Repeat repeat;

    private String ASCII;
    public static String accessId = "7568512625";
    public static String accessSecret = "a02f3025-9c9b-4737-897d-7e94242797a0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date_pwd);
        ButterKnife.bind(this);
        context = this;
        setTitle("忘记密码");
    }

    private static String timers;
    private static String randoms;

    @OnClick({R.id.tv_update_person, R.id.tv_update_date_company, R.id.btn_update_code, R.id.btn_go_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_person:
                type = RoleConstant.PERSONAL_ROLE;
                tvUpdatePerson.setTextSize(20);
                tvUpdatePerson.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                tvUpdateDateCompany.setTextSize(16);
                tvUpdateDateCompany.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                break;
            case R.id.tv_update_date_company:
                type = RoleConstant.ENTERPRISE_ROLE;
                tvUpdatePerson.setTextSize(16);
                tvUpdatePerson.setTextColor(context.getResources().getColor(R.color.login_company_tv));
                tvUpdateDateCompany.setTextSize(20);
                tvUpdateDateCompany.setTextColor(context.getResources().getColor(R.color.login_person_tv));
                break;
            case R.id.btn_update_code:
                phone = phoneEd.getText().toString();
                if (Utils.isMobileNO1(phone)) {
                    timer = new Timer();
                    repeat = new Repeat();
                    timer.schedule(repeat, 0, 1000);
                    btnUpdateCode.setClickable(false);
                    btnUpdateCode.setBackgroundColor(getResources().getColor(R.color.withDrawBankText));
                    OkUtils.getInstances().httpGet(context, RequestConstant.GET_REGISTER_CODE, JavaParamsUtils.getInstances().Short_Message(phone, type, "20"), new TypeToken<EntityObject<Object>>() {
                    }.getType(), new ResultCallBackListener<Object>() {
                        @Override
                        public void onFailure(int errorId, String msg) {
                            AlertUtils.toast(context, msg);
                        }

                        @Override
                        public void onSuccess(EntityObject<Object> response) {
                            AlertUtils.toast(context, "短信处理成功,请耐心等待");
                        }
                    });
                } else {
                    AlertUtils.toast(context, "请输入正确的手机号码");
                }
                break;
            case R.id.btn_go_update:

                timers = DateFormatUtil.getTimeZ();
                randoms = HttpHeadUtils.getRandom();
                phone = phoneEd.getText().toString();
                code = codeEd.getText().toString();
                password = passwordEd.getText().toString();


                confirmpassword = confirmPassword.getText().toString();


                if (!Utils.isMobileNO1(phone)) {
                    AlertUtils.toast(context, "请输入正确的手机号码");
                    return;
                }
                if (!TextUtils.isDigitsOnly(password)) {
                    AlertUtils.toast(context, "密码不能为空");
                    return;
                }
                if (!TextUtils.isDigitsOnly(code)) {
                    AlertUtils.toast(context, "验证码不能为空");
                    return;
                }
                if (!password.equals(confirmpassword)) {
                    AlertUtils.toast(context, "重复密码不一致");
                }
                UpdateBean bean = new UpdateBean();
                bean.PhoneNum = phone;

                bean.NewPassword = password;
                bean.Role = type;
                bean.SmsCode = code;
                RequestBody body = new FormBody.Builder()
                        .add("PhoneNum", phone)
                        .add("NewPassword", password)
                        .add("Role", type)
                        .add("SmsCode", code)
                        .build();
                modifyPassword(body, phone, password, code);
                break;
        }
    }

    class Repeat extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    btnUpdateCode.setText(time + "秒后重新发送");
                    if (time == 0) {
                        setCanGetPin();
                    }
                }
            });
        }
    }

    private void setCanGetPin() {
        btnUpdateCode.setText("获取验证码");
        btnUpdateCode.setClickable(true);
        btnUpdateCode.setBackgroundColor(getResources().getColor(R.color.bcompany));
        btnUpdateCode.setTextColor(getResources().getColor(R.color.white));
        if (repeat != null) {
            repeat.cancel();
            repeat = null;
        }
        if (timer != null) {
            timer = null;
        }
        time = 60;
    }

    private void modifyPassword(RequestBody body, String phone, String password, String code) {


        OkUtils.getInstances().httpatch(body, context, RequestConstant.UPDATE_PASSWORD, JavaParamsUtils.getInstances().upPassword(phone, password, type, code), new TypeToken<EntityObject<Object>>() {
        }.getType(), new ResultCallBackListener<Object>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Object> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    AlertUtils.toast(context, "修改成功");
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });

    }


}
