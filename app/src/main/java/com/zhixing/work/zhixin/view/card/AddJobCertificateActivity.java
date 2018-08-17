package com.zhixing.work.zhixin.view.card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.JobCertificateDetailBean;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.JobCertificateDetailResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class AddJobCertificateActivity extends BaseTitleActivity {


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

    private List<String> list;
    private static final String INTENT_KEY_ID = "id";
    private static final String INTENT_KEY_STAFF_ID = "staffId";

    public static void startAddJobCertificateActivity(Activity activity, String id, String staffId) {
        Intent intent = new Intent(activity, AddJobCertificateActivity.class);
        intent.putExtra(INTENT_KEY_ID, id);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    private Subscription mJobEducationDetailSubscription;
    private Subscription mUpdateEducationJobSubscription;


    private String mStaffId;
    private String mId;
    private JobCertificateDetailBean mJobBean;
    private String achievement;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_certificate);
        ButterKnife.bind(this);
        context = this;
        setTitle("证书信息");
        setRightText1("保存");
        registerRequest();
        getIntentData();
        initView();
    }

    private void registerRequest() {
        mJobEducationDetailSubscription = RxBus.getInstance().toObservable(JobCertificateDetailResult.class).subscribe(
                result -> handlerJobCertificateDetail(result)
        );
        mUpdateEducationJobSubscription = RxBus.getInstance().toObservable(UpdateJobResult.class).subscribe(
                result -> handlerUpdateJobEducation(result)
        );

    }

    private void handlerUpdateJobEducation(UpdateJobResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                if (result.getType() == 1) {
                    AlertUtils.show("添加成功");
                } else if (result.getType() == 2) {
                    AlertUtils.show("修改成功");
                }
                this.finish();
            } else {
                AlertUtils.show("失败:" + result.Message);
            }

        } else {
            AlertUtils.show(result.Message);
        }
    }

    private void handlerJobCertificateDetail(JobCertificateDetailResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mJobBean = result.getContent();
            if (mJobBean != null) {
                initViewData();
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    private void initViewData() {
        certificateName.setText(mJobBean.getCertificateTitle());

        certificateTime.setText(DateFormatUtil.parseDate(mJobBean.getGraduationDate(), "yyyy-MM"));
        if (!TextUtils.isEmpty(mJobBean.getGrade())) {
            achievementEd.setText(mJobBean.getGrade());
            achievementEd.setSelection(mJobBean.getGrade().length());
        }


    }


    public void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mId = getIntent().getStringExtra(INTENT_KEY_ID);
        mJobBean = new JobCertificateDetailBean();
        if (!TextUtils.isEmpty(mId)) {
            getWorkEducationDetail();
        }
        mJobBean.setStaffId(mStaffId);
    }


    public void getWorkEducationDetail() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_ID, mId);
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_CERTIFICATE_DETAIL, map);
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mJobBean.getGraduationDate())) {
                    AlertUtils.toast(context, "时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getCertificateTitle())) {
                    AlertUtils.toast(context, "证书名字不能为空");
                    return;
                }
                achievement = achievementEd.getText().toString();
                mJobBean.setGrade(achievement);

                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, mJobBean);
                if (TextUtils.isEmpty(mId)) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_STAFF_CERTIFICATE, map);
                } else {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_STAFF_CERTIFICATE, map);
                }
            }
        });


    }

    @OnClick({R.id.rl_certificate_time, R.id.rl_certificate_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_certificate_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        certificateTime.setText(time);
                        mJobBean.setGraduationDate(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
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
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_CERTIFICATE_NAME: //send the video
                certificateName.setText(event.getContent());
                mJobBean.setCertificateTitle(event.getContent());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mJobEducationDetailSubscription, mUpdateEducationJobSubscription);
    }
}
