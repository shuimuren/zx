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
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Certificate;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.sava)
    Button sava;


    private String achievement;
    private String time;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_certificate);
        ButterKnife.bind(this);
        setTitle("证书");

    }

    @OnClick({R.id.rl_certificate_time, R.id.rl_certificate_name, R.id.sava})
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
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_CERTIFICATE_NAME));

                break;
            case R.id.sava:
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

                Certificate certificate = new Certificate(name,time,achievement);
                EventBus.getDefault().post(certificate);
                finish();

                break;
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
}
