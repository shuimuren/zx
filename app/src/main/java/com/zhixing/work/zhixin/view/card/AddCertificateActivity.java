package com.zhixing.work.zhixin.view.card;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Certificate;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddCertificateActivity extends BaseTitleActivity {


    @BindView(R.id.certificate_time)
    TextView certificateTime;
    @BindView(R.id.iv_certificate_time)
    ImageView ivCertificateTime;
    @BindView(R.id.rl_certificate_time)
    RelativeLayout rlCertificateTime;
    @BindView(R.id.certificate_name)
    TextView certificateName;
    @BindView(R.id.iv_certificate_name)
    ImageView ivCertificateName;
    @BindView(R.id.rl_certificate_name)
    RelativeLayout rlCertificateName;
    @BindView(R.id.achievement_ed)
    ClearEditText achievementEd;
    @BindView(R.id.rl_achievement)
    RelativeLayout rlAchievement;
    @BindView(R.id.delete)
    Button delete;


    private String achievement;
    private String time;
    private String name;
    private String type;
    private Gson gson = new Gson();
    private Resume.CertificateOutputsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_certificate);
        ButterKnife.bind(this);
        setTitle("证书");
        setRightText1("保存");
        context = this;
        type = getIntent().getStringExtra("type");
        Bundle bundle = getIntent().getExtras();
        bean = (Resume.CertificateOutputsBean) bundle.get("bean");
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = certificateTime.getText().toString();
                name = certificateName.getText().toString();
                achievement = achievementEd.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    AlertUtils.toast(context, "时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    AlertUtils.toast(context, "证书名字不能为空");
                    return;
                }

                Certificate certificate = new Certificate(name, time, achievement);
                if (type.equals("card")) {
                    EventBus.getDefault().post(certificate);
                    finish();
                }

                if (type.equals("resume")) {
                    if (bean != null) {
                        RequestBody body = new FormBody.Builder()
                                .add("Id", bean.getId() + "")
                                .add("CertificateTitle", certificate.getCertificateTitle())
                                .add("GraduationDate", certificate.getGraduationDate())
                                .add("Grade", certificate.getGrade())

                                .build();
                        upCertificate(body);
                    } else {
                        List<Certificate> list = new ArrayList<Certificate>();
                        list.add(certificate);
                        addCertificate(gson.toJson(list));
                    }

                }

            }
        });

        if (bean != null) {
            certificateName.setText(bean.getCertificateTitle());

            certificateTime.setText(DateFormatUtil.parseDate(bean.getGraduationDate(), "yyyy-MM"));
            if (!TextUtils.isEmpty(bean.getGrade())) {
                achievementEd.setText(bean.getGrade());
                achievementEd.setSelection(bean.getGrade().length());
            }

            delete.setVisibility(View.VISIBLE);

        }
    }

    @OnClick({R.id.rl_certificate_time, R.id.rl_certificate_name, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_certificate_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        certificateTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                school_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                school_time.show();
                break;
            case R.id.rl_certificate_name:

                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "证书名字").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_CERTIFICATE_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, certificateName.getText().toString())
                );

                break;
            case R.id.delete:
                RequestBody body = new FormBody.Builder()
                        .add("Id", bean.getId() + "").build();
                deleteData(body);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_CERTIFICATE_NAME: //send the video
                certificateName.setText(event.getContent());
                break;
        }
    }


    //更新数据
    private void upCertificate(RequestBody body) {
        OkUtils.getInstances().httput(body, context, JavaConstant.CertificateBackground, JavaParamsUtils.getInstances().upCertificate(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getCode() == 10000) {
                            if (response.getContent()) {
                                AlertUtils.toast(context, "修改成功");
                                EventBus.getDefault().post(new ResumeRefreshEvent(true));
                                finish();
                            } else {
                                AlertUtils.toast(context, response.getMessage());
                            }

                        } else {
                            AlertUtils.toast(context, response.getMessage());
                        }


                    }
                });
            }
        });
    }

    //提交数据
    private void addCertificate(String json) {
        OkUtils.getInstances().postJson(context, JavaConstant.CertificateBackground, json, new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response.getCode() == 10000) {
                                if (response.getContent()) {
                                    AlertUtils.toast(context, "添加成功");
                                    EventBus.getDefault().post(new ResumeRefreshEvent(true));
                                    finish();
                                } else {
                                    AlertUtils.toast(context, response.getMessage());
                                }
                            } else {
                                AlertUtils.toast(context, response.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });

    }

    //删除经历
    private void deleteData(RequestBody body) {
        OkUtils.getInstances().httpDelete(body, context, JavaConstant.CertificateBackground + "?Id=" + bean.getId(), JavaParamsUtils.getInstances().deleteWork(bean.getId() + ""), new TypeToken<EntityObject<Boolean>>() {
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
                        AlertUtils.toast(context, "删除成功");
                        EventBus.getDefault().post(new ResumeRefreshEvent(true));
                        finish();
                    } else {
                        AlertUtils.toast(context, "删除失败");
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });


    }
}
