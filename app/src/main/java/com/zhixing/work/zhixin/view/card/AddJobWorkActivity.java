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
import com.zhixing.work.zhixin.bean.JobWorkDetailBean;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.JobWorkDetailResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class AddJobWorkActivity extends BaseTitleActivity {
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.iv_company_name)
    ImageView ivCompanyName;
    @BindView(R.id.rl_company_name)
    RelativeLayout rlCompanyName;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.iv_post)
    ImageView ivPost;
    @BindView(R.id.rl_post)
    RelativeLayout rlPost;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.iv_start_time)
    ImageView ivStartTime;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.work_content)
    TextView workContent;
    @BindView(R.id.iv_work_content)
    ImageView ivWorkContent;
    @BindView(R.id.rl_work_content)
    RelativeLayout rlWorkContent;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.iv_department)
    ImageView ivDepartment;
    @BindView(R.id.rl_department)
    RelativeLayout rlDepartment;

    private static final String INTENT_KEY_ID = "id";
    private static final String INTENT_KEY_STAFF_ID = "staffId";

    public static void startAddJobWorkActivity(Activity activity, String id, String staffId) {
        Intent intent = new Intent(activity, AddJobWorkActivity.class);
        intent.putExtra(INTENT_KEY_ID, id);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    private Subscription mJobWordDetailSubscription;
    private Subscription mUpdateWorkJobSubscription;


    private String mStaffId;
    private String mId;
    private JobWorkDetailBean mJobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_work);
        ButterKnife.bind(this);
        setTitle("工作经历");
        setRightText1("保存");
        registerRequest();
        getIntentData();
        initView();
    }

    private void registerRequest() {
        mJobWordDetailSubscription = RxBus.getInstance().toObservable(JobWorkDetailResult.class).subscribe(
                result -> handlerJobWorkDetail(result)
        );
        mUpdateWorkJobSubscription = RxBus.getInstance().toObservable(UpdateJobResult.class).subscribe(
                result -> handlerUpdateJobWork(result)
        );

    }


    public void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mId = getIntent().getStringExtra(INTENT_KEY_ID);
        mJobBean = new JobWorkDetailBean();
        if (!TextUtils.isEmpty(mId)) {
           getWorkJobDetail();
        }
        mJobBean.setStaffId(mStaffId);
    }


    /**
     * 添加 或 修改 请求结果
     *
     * @param result
     */
    private void handlerUpdateJobWork(UpdateJobResult result) {
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

    /**
     * 工作详细
     *
     * @param result
     */
    private void handlerJobWorkDetail(JobWorkDetailResult result) {
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
        companyName.setText(mJobBean.getCompanyName());
        post.setText(mJobBean.getPostOfDuty());
        startTime.setText(DateFormatUtil.parseDate(mJobBean.getStartDate(), "yyyy-MM"));
        endTime.setText(DateFormatUtil.parseDate(mJobBean.getEndDate(), "yyyy-MM"));
        workContent.setText(mJobBean.getJobContent());
        department.setText(mJobBean.getDepartment());

    }

    /**
     * 获取工作详细
     */
    private void getWorkJobDetail() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_ID,mId);
        map.put(RequestConstant.KEY_STAFF_ID,mStaffId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_WORK_DETAIL, map);
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mJobBean.getCompanyName())) {
                    AlertUtils.toast(context, "公司名称为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getDepartment())) {
                    AlertUtils.toast(context, "工作岗位不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getStartDate())) {
                    AlertUtils.toast(context, "开始时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getEndDate())) {
                    AlertUtils.toast(context, "结束时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mJobBean.getJobContent())) {
                    AlertUtils.toast(context, "工作内容不能为空");
                    return;
                }
                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, mJobBean);
                if (TextUtils.isEmpty(mId)) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_STAFF_WORK, map);
                } else {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_STAFF_WORK,map);
                }

            }
        });


    }

    @OnClick({R.id.rl_company_name, R.id.rl_post, R.id.rl_start_time, R.id.rl_end_time, R.id.rl_work_content, R.id.rl_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_company_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "公司名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_COMPANY_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, companyName.getText().toString())
                );
                break;
            case R.id.rl_post:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "工作岗位").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_POST)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, post.getText().toString())
                );
                break;
            case R.id.rl_start_time:
                final TimePickerView start_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        startTime.setText(time);
                      mJobBean.setStartDate(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("开始时间")
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
                start_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                start_time.show();
                break;
            case R.id.rl_end_time:
                final TimePickerView end_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        endTime.setText(time);
                       mJobBean.setEndDate(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("结束时间")
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
                end_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                end_time.show();
                break;
            case R.id.rl_work_content:

                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "工作内容").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_WORK_CONTENT).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "请输入工作内容").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, workContent.getText().toString()));
                break;
            case R.id.rl_department:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "所属部门").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_DEPARTMENT)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, department.getText().toString())
                );
                break;


        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_COMPANY_NAME: //send the video
                companyName.setText(event.getContent());
                mJobBean.setCompanyName(event.getContent());
                break;
            case ModifyDataActivity.TYPE_POST: //send the video
                post.setText(event.getContent());
                mJobBean.setPostOfDuty(event.getContent());
                break;
            case ModifyDataActivity.TYPE_DEPARTMENT: //send the video
                department.setText(event.getContent());
                mJobBean.setDepartment(event.getContent());
                break;
            case ModifyContentActivity.TYPE_WORK_CONTENT: //send the video
                workContent.setText(event.getContent());
                mJobBean.setJobContent(event.getContent());
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mJobWordDetailSubscription, mUpdateWorkJobSubscription);
    }

}
