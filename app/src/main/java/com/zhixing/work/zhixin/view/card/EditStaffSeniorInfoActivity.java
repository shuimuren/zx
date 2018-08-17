package com.zhixing.work.zhixin.view.card;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.JobCardSeniorInfoBean;
import com.zhixing.work.zhixin.common.StaffStatusMenu;
import com.zhixing.work.zhixin.common.StaffTypeMenu;
import com.zhixing.work.zhixin.common.TestTimeEnum;
import com.zhixing.work.zhixin.event.RefreshStaffInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.EditSeniorInfoResult;
import com.zhixing.work.zhixin.requestbody.StaffJobSeniorBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/14.
 * Description:编辑员工个人高级信息
 */

public class EditStaffSeniorInfoActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_STAFF_BEAN = "staffBean";
    @BindView(R.id.tv_card_work_begin_time)
    TextView tvCardWorkBeginTime;
    @BindView(R.id.tv_card_user_role_type)
    TextView tvCardUserRoleType;
    @BindView(R.id.tv_card_user_status)
    TextView tvCardUserStatus;
    @BindView(R.id.tv_card_test_time)
    TextView tvCardTestTime;
    @BindView(R.id.tv_card_formal_time)
    TextView tvCardFormalTime;
    @BindView(R.id.ll_senior)
    LinearLayout llSenior;

    private Subscription mEditSeniorSubscription;
    private String mBeginTime, mFormalTime;
    private int mRoleType, mUserStaus, mTestTime;

    private List<String> mStaffTypeList;
    private List<String> mStaffStatusList;
    private List<String> mTestTimeList;

    private StaffJobSeniorBody body;
    private JobCardSeniorInfoBean mSeniorInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_senior_info);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.senior_title_text));
        setRightText1(ResourceUtils.getString(R.string.save));
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        initViewData();
    }

    private void initViewData() {
        mSeniorInfoBean = (JobCardSeniorInfoBean) getIntent().getSerializableExtra(INTENT_KEY_STAFF_BEAN);
        if (mSeniorInfoBean != null) {
            mBeginTime = mSeniorInfoBean.getEntryTime();
            tvCardWorkBeginTime.setText(ZxTextUtils.getTextNotNull(mBeginTime));
            mFormalTime = mSeniorInfoBean.getPositiveTime();
            tvCardFormalTime.setText(ZxTextUtils.getTextNotNull(mFormalTime));
            mRoleType = mSeniorInfoBean.getStaffType();
            tvCardUserRoleType.setText(StaffTypeMenu.getName(mRoleType));
            mUserStaus = mSeniorInfoBean.getStaffStatus();
            tvCardUserStatus.setText(StaffStatusMenu.getName(mUserStaus));
            mTestTime = mSeniorInfoBean.getProbation();
            tvCardTestTime.setText(TestTimeEnum.getName(mTestTime));
        } else {
            this.finish();
        }
        mEditSeniorSubscription = RxBus.getInstance().toObservable(EditSeniorInfoResult.class).subscribe(
                result -> handlerEditSeniorResult(result)
        );
        mStaffTypeList = new ArrayList<>();
        for (StaffTypeMenu c : StaffTypeMenu.values()) {
            mStaffTypeList.add(c.getName());
        }
        mStaffStatusList = new ArrayList<>();
        for (StaffStatusMenu c : StaffStatusMenu.values()) {
            mStaffStatusList.add(c.getName());
        }
        mTestTimeList = new ArrayList<>();
        for (TestTimeEnum c : TestTimeEnum.values()) {
            mTestTimeList.add(c.getName());
        }
    }

    private void handlerEditSeniorResult(EditSeniorInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.handler_success));
                EventBus.getDefault().post(new RefreshStaffInfoEvent(false));
                this.finish();
            }

        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.handler_fail) + result.Message);
        }
    }

    private void saveData() {
        body = new StaffJobSeniorBody();
        body.setStaffId(String.valueOf(mSeniorInfoBean.getStaffId()));
        body.setEntryTime(mBeginTime);
        body.setPositiveTime(mFormalTime);
        body.setProbation(mTestTime);
        body.setStaffType(mRoleType);
        body.setStaffStatus(mUserStaus);
        Map map = new HashMap();
        map.put(RequestConstant.KEY_REQUEST_BODY, body);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EIDT_COMPANY_STAFF_JOB_ADVANCED, map);
    }

    @OnClick({R.id.tv_card_work_begin_time, R.id.tv_card_user_role_type, R.id.tv_card_user_status, R.id.tv_card_test_time, R.id.tv_card_formal_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_card_work_begin_time:
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        tvCardWorkBeginTime.setText(time);
                        mBeginTime = time;
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setSubmitText("完成")//确认按钮文字
                        .setCancelText("")
                        .setTitleText("入职日期")
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
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.tv_card_user_role_type:
                OptionsPickerView options = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = mStaffTypeList.get(options1);
                        tvCardUserRoleType.setText(s);
                        mRoleType = StaffTypeMenu.getIndex(s);
                    }
                })
                        .setTitleText("员工类型")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(mStaffTypeList);
                options.show();
                break;
            case R.id.tv_card_user_status:
                OptionsPickerView optionsStatus = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = mStaffStatusList.get(options1);
                        tvCardUserStatus.setText(s);
                        mUserStaus = StaffStatusMenu.getIndex(s);
                    }
                })
                        .setTitleText("员工状态")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                optionsStatus.setPicker(mStaffStatusList);
                optionsStatus.show();
                break;
            case R.id.tv_card_test_time:
                OptionsPickerView optionsTest = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = mTestTimeList.get(options1);
                        tvCardTestTime.setText(s);
                        mTestTime = TestTimeEnum.getIndex(s);
                    }
                })
                        .setTitleText("试用期")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                optionsTest.setPicker(mTestTimeList);
                optionsTest.show();
                break;
            case R.id.tv_card_formal_time:
                TimePickerView formalTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        tvCardFormalTime.setText(time);
                        mFormalTime = time;
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("")//取消按钮文字
                        .setSubmitText("完成")//确认按钮文字
                        .setTitleText("转正日期")
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
                formalTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                formalTime.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mEditSeniorSubscription);
    }
}
