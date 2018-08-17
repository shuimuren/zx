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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.JobEducationDetailBean;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.JobEducationDetailResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class AddJobEducationActivity extends BaseTitleActivity {


    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.graduate_college)
    TextView graduateCollege;
    @BindView(R.id.iv_graduate_college)
    ImageView ivGraduateCollege;
    @BindView(R.id.rl_graduate_college)
    RelativeLayout rlGraduateCollege;
    @BindView(R.id.school_time)
    TextView schoolTime;
    @BindView(R.id.iv_school_time)
    ImageView ivSchoolTime;
    @BindView(R.id.rl_school_time)
    RelativeLayout rlSchoolTime;
    @BindView(R.id.graduation_time)
    TextView graduationTime;
    @BindView(R.id.iv_graduation_time)
    ImageView ivGraduationTime;
    @BindView(R.id.rl_graduation_time)
    RelativeLayout rlGraduationTime;
    @BindView(R.id.major)
    TextView major;
    @BindView(R.id.iv_major)
    ImageView ivMajor;
    @BindView(R.id.rl_major)
    RelativeLayout rlMajor;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.iv_explain)
    ImageView ivExplain;
    @BindView(R.id.rl_explain)
    RelativeLayout rlExplain;
    //
    private List<String> list;
    private static final String INTENT_KEY_ID = "id";
    private static final String INTENT_KEY_STAFF_ID = "staffId";

    public static void startAddJobEducationActivity(Activity activity, String id, String staffId) {
        Intent intent = new Intent(activity, AddJobEducationActivity.class);
        intent.putExtra(INTENT_KEY_ID, id);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    private Subscription mJobEducationDetailSubscription;
    private Subscription mUpdateEducationJobSubscription;


    private String mStaffId;
    private String mId;
    private JobEducationDetailBean mJobBean;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_education);
        ButterKnife.bind(this);
        context = this;
        setTitle("教育经历");
        setRightText1("保存");
        registerRequest();
        getIntentData();
        initView();
    }

    private void registerRequest() {
        mJobEducationDetailSubscription = RxBus.getInstance().toObservable(JobEducationDetailResult.class).subscribe(
                result -> handlerJobEducationDetail(result)
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

    private void handlerJobEducationDetail(JobEducationDetailResult result) {
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
        education.setText(Utils.getEducation(mJobBean.getEducation()));
        graduateCollege.setText(mJobBean.getSchool());
        schoolTime.setText(DateFormatUtil.parseDate(mJobBean.getStartDate(), "yyyy-MM"));
        graduationTime.setText(DateFormatUtil.parseDate(mJobBean.getEndDate(), "yyyy-MM"));
        major.setText(mJobBean.getMajor());
        explain.setText(mJobBean.getExperience());
        rlExplain.setVisibility(View.VISIBLE);

    }


    public void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mId = getIntent().getStringExtra(INTENT_KEY_ID);
        mJobBean = new JobEducationDetailBean();
        if (!TextUtils.isEmpty(mId)) {
            getWorkEducationDetail();
        }
        mJobBean.setStaffId(mStaffId);
    }


    public void getWorkEducationDetail() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_ID, mId);
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_EDUCATION_DETAIL, map);
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mJobBean.getEducation() == null) {
                    AlertUtils.toast(context, "学历信息不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getSchool())) {
                    AlertUtils.toast(context, "毕业院校不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getStartDate())) {
                    AlertUtils.toast(context, "入学时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getEndDate())) {
                    AlertUtils.toast(context, "毕业时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getMajor())) {
                    AlertUtils.toast(context, "专业信息不能为空");
                    return;
                }

                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, mJobBean);
                if (TextUtils.isEmpty(mId)) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_STAFF_EDUCATION, map);
                } else {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_STAFF_EDUCATION,map);
                }
            }
        });


    }

    @OnClick({R.id.rl_education, R.id.rl_graduate_college, R.id.rl_school_time, R.id.rl_graduation_time, R.id.rl_major, R.id.rl_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_education:
                list = Arrays.asList(getResources().getStringArray(R.array.education));
                final OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = list.get(options1);
                        mJobBean.setEducation((options1 + 1) * 10);
                        education.setText(s);
                    }
                })
                        .setTitleText("学历")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
            case R.id.rl_graduate_college:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "毕业院校").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_SCHOOL)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, graduateCollege.getText().toString()));
                break;
            case R.id.rl_school_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        schoolTime.setText(time);
                        mJobBean.setStartDate(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("入校时间")
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
            case R.id.rl_graduation_time:
                final TimePickerView graduation_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        graduationTime.setText(time);
                        mJobBean.setEndDate(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("毕业时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                graduation_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                graduation_time.show();
                break;
            case R.id.rl_major:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "专业").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_MAJOR)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, major.getText().toString())
                );
                break;
            case R.id.rl_explain:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "说明").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_EXPLAIN).
                        putExtra(ModifyContentActivity.HINT, "请输入说明").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, explain.getText().toString()));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_SCHOOL: //send the video
                graduateCollege.setText(event.getContent());
                mJobBean.setSchool(event.getContent());
                break;
            case ModifyDataActivity.TYPE_MAJOR: //send the video
                major.setText(event.getContent());
                mJobBean.setMajor(event.getContent());
                break;
            case ModifyContentActivity.TYPE_EXPLAIN: //send the video
                explain.setText(event.getContent());
                mJobBean.setExperience(event.getContent());
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mJobEducationDetailSubscription,mUpdateEducationJobSubscription);
    }
}
