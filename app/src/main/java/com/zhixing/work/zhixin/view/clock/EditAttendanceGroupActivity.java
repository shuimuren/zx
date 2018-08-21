package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AttendanceRuleBean;
import com.zhixing.work.zhixin.bean.WifiBean;
import com.zhixing.work.zhixin.common.WorkDayMenu;
import com.zhixing.work.zhixin.dialog.MultiCheckDialog;
import com.zhixing.work.zhixin.event.CreateAttendanceGroupSuccessEvent;
import com.zhixing.work.zhixin.event.WifiListEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.CreateAttendanceRuleResult;
import com.zhixing.work.zhixin.requestbody.AttendanceRuleBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/1.
 * Description: 新建考勤分组-编辑
 */

public class EditAttendanceGroupActivity extends BaseTitleActivity {


    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.ll_work_time)
    LinearLayout llWorkTime;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.ll_work_begin_time)
    LinearLayout llWorkBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_work_end_time)
    LinearLayout llWorkEndTime;
    @BindView(R.id.ll_work_scope_time)
    LinearLayout llWorkScopeTime;
    @BindView(R.id.ll_absenteeism_time)
    LinearLayout llAbsenteeismTime;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.ll_wifi)
    LinearLayout llWifi;
    @BindView(R.id.btn_finish)
    Button btnSave;
    @BindView(R.id.flex_time)
    ClearEditText flexTime;
    @BindView(R.id.absenteeism_time)
    ClearEditText absenteeismTime;

    public static final String INTENT_KEY_NAME = "name";
    public static final String INTENT_KEY_STAFFS = "staffs";
    public static final String INTENT_KEY_RULE_BEAN = "bean";
    public static final String INTENT_KEY_IS_EDIT = "isEdit";

    //1-1440

    private Subscription mCreateAttendanceRuleSubscription;
    private int beginHour, beginMinute, endHour, endMinute;
    private String mName;
    private String mStartTime;
    private String mEndTime;
    private String mFlexTime;
    private String mAbsenteeismTime;
    private List<Integer> mWorkDays;
    private List<Integer> mStaffIds;
    private List<WifiBean> mWifiList; //编辑过程中的wifi列表
    private AttendanceRuleBean mBean;
    private boolean isEdit;

    /**
     * @param activity
     * @param name
     * @param staffs
     * @param bean     修改规则
     * @param isEdit   新建或修改时为 ture,只做查看时为false
     */
    public static void startEditAttendanceGroup(Activity activity, String name, List<Integer> staffs, AttendanceRuleBean bean, boolean isEdit) {
        Intent intent = new Intent(activity, EditAttendanceGroupActivity.class);
        if (bean == null) {
            intent.putExtra(INTENT_KEY_NAME, name);
            intent.putIntegerArrayListExtra(INTENT_KEY_STAFFS, (ArrayList<Integer>) staffs);
        } else {
            intent.putExtra(INTENT_KEY_RULE_BEAN, bean);
            intent.putExtra(INTENT_KEY_IS_EDIT, isEdit);
        }

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendance_group);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.new_add_attendance_group));
        getIntentData();

        registerRequest();
    }

    private void registerRequest() {
        mCreateAttendanceRuleSubscription = RxBus.getInstance().toObservable(CreateAttendanceRuleResult.class).subscribe(
                result -> handlerCreateResult(result)
        );
    }

    private void handlerCreateResult(CreateAttendanceRuleResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RULE);
                AlertUtils.show(ResourceUtils.getString(R.string.create_rule_success));
                CreateAttendanceGroupSuccessEvent event = new CreateAttendanceGroupSuccessEvent();
                event.setCreateSuccess(true);
                EventBus.getDefault().post(event);
                this.finish();
            }
        }
    }

    public void getIntentData() {
        Intent data = getIntent();
        mBean = (AttendanceRuleBean) data.getSerializableExtra(INTENT_KEY_RULE_BEAN);
        isEdit = data.getBooleanExtra(INTENT_KEY_IS_EDIT, true);
        if (mBean == null) {
            mName = data.getStringExtra(INTENT_KEY_NAME);
            mStaffIds = data.getIntegerArrayListExtra(INTENT_KEY_STAFFS);
            beginHour = 9;
            beginMinute = 0;
            endHour = 18;
            endMinute = 0;
            mWifiList = new ArrayList<>();
            mWorkDays = new ArrayList<>();
        } else {
            mName = mBean.getName();
            mWorkDays = getWorkTime();
            mWifiList = mBean.getWifis();
            String beginTime = mBean.getStartTime();
            String endTime = mBean.getEndTime();

            beginHour = Integer.parseInt(beginTime.substring(0, 2));
            beginMinute = Integer.parseInt(beginTime.substring(3, 5));
            endHour = Integer.parseInt(endTime.substring(0, 2));
            endMinute = Integer.parseInt(endTime.substring(3, 5));
            mStaffIds = mBean.getStaffIds();
            tvWorkTime.setText(selectedWorks(mWorkDays));
            tvBeginTime.setText(timeFormat(beginHour, beginMinute));
            tvEndTime.setText(timeFormat(endHour, endMinute));
            flexTime.setText(String.valueOf(mBean.getFlexTime()));
            absenteeismTime.setText(String.valueOf(mBean.getAbsenteeismTime()));
            tvWifi.setText(getWifiText(mWifiList));
            btnSave.setVisibility(isEdit ? View.VISIBLE : View.GONE);
            tvWorkTime.setClickable(isEdit);
            tvBeginTime.setClickable(isEdit);
            tvEndTime.setClickable(isEdit);
            flexTime.setClickable(isEdit);
            absenteeismTime.setClickable(isEdit);
            tvWifi.setClickable(isEdit);
        }

    }


    @OnClick({R.id.ll_work_time, R.id.ll_work_begin_time, R.id.ll_work_end_time, R.id.ll_wifi, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_work_time:
                MultiCheckDialog dialog = new MultiCheckDialog(this) {
                    @Override
                    protected void onSelectDaysConfirmMethod() {
                        dismiss();
                        mWorkDays = onGetDayNameResult();
                        tvWorkTime.setText(selectedWorks(mWorkDays));
                    }
                };
                dialog.setDayRange(mWorkDays);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.ll_work_begin_time:
                TimePickerDialog beginTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginHour = hourOfDay;
                        beginMinute = minute;
                        tvBeginTime.setText(timeFormat(hourOfDay, minute));
                    }
                }, beginHour, beginMinute, true);
                beginTime.setCancelable(true);
                beginTime.show();
                break;
            case R.id.ll_work_end_time:
                TimePickerDialog endTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endHour = hourOfDay;
                        endMinute = minute;
                        tvEndTime.setText(timeFormat(hourOfDay, minute));
                    }
                }, endHour, endMinute, true);
                endTime.setCancelable(true);
                endTime.show();
                break;
            case R.id.ll_wifi:
                SettingWifiActivity.startSettingWifiActivity(this, mWifiList);
                break;
            case R.id.btn_finish:
                mStartTime = tvBeginTime.getText().toString();
                mEndTime = tvEndTime.getText().toString();
                mFlexTime = flexTime.getText().toString();
                mAbsenteeismTime = absenteeismTime.getText().toString();
                if (TextUtils.isEmpty(mStartTime)) {
                    AlertUtils.show("请设置上班时间");
                    return;
                }
                if (TextUtils.isEmpty(mEndTime)) {
                    AlertUtils.show("请设置下班时间");
                    return;
                }
                if (TextUtils.isEmpty(mFlexTime)) {
                    mFlexTime = "0";
                }

                if (TextUtils.isEmpty(mAbsenteeismTime)) {
                    AlertUtils.show("请设置旷工时间标准");
                    return;
                } else {
                    int value = Integer.parseInt(mAbsenteeismTime);
                    if (value < 1 || value > 1440) {
                        AlertUtils.show("时间范围1~1440");
                        return;
                    }
                }

                if ((beginHour * 60 + beginMinute) > (endHour * 60 + endMinute)) {
                    AlertUtils.show("下班时间不能");
                    return;
                }

                if (mWifiList.size() == 0) {
                    AlertUtils.show("请选择打卡Wifi");
                    return;
                }

                AttendanceRuleBody body = new AttendanceRuleBody();
                body.setName(mName);
                body.setStartTime(mStartTime);
                body.setEndTime(mEndTime);
                body.setFlexTime(mFlexTime);
                body.setAbsenteeismTime(mAbsenteeismTime);
                body.setWorkDays(mWorkDays);
                body.setWifis(mWifiList);
                if (mBean == null) {
                    body.setStaffIds(mStaffIds);
                } else {
                    body.setId(String.valueOf(mBean.getId()));
                }

                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, body);
                if (mBean == null) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_CREATE_ATTENDANCE_RULE, map);
                } else {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_ATTENDANCE_RULE, map);
                }


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mCreateAttendanceRuleSubscription);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WifiListSubscribe(WifiListEvent event) {
        mWifiList = event.getWifis();
        tvWifi.setText(getWifiText(mWifiList));
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifi.setText(Utils.stringListToString(wifiList));
//            }
//        });

    }

    private String timeFormat(int hour, int minute) {
        String formatHour = "";
        String formatMinute = "";
        if (hour < 10) {
            formatHour = "0" + hour;
        } else {
            formatHour = String.valueOf(hour);
        }

        if (minute < 10) {
            formatMinute = "0" + minute;
        } else {
            formatMinute = String.valueOf(minute);
        }

        return formatHour + ":" + formatMinute;
    }

    private String selectedWorks(List<Integer> works) {
        StringBuilder sbDayId = new StringBuilder();
        for (int i = 0; i < works.size(); i++) {
            sbDayId.append(WorkDayMenu.getName(works.get(i)) + ",");
        }
        return (sbDayId.length() == 0) ? sbDayId.toString() : sbDayId.substring(0, sbDayId.length() - 1);
    }

    private List<Integer> getWorkTime() {
        List<Integer> list = new ArrayList<>();

        int[] work = new int[]{1, 2, 4, 8, 16, 32, 64};
        for (int i = 0; i < work.length; i++) {
            if ((work[i] & mBean.getWorkDay()) == work[i]) {
                list.add(work[i]);
            }
        }
        return list;
    }

    private String getWifiText(List<WifiBean> wifiBeans) {
        List<String> wifiList = new ArrayList<>();
        for (int i = 0; i < mWifiList.size(); i++) {
            wifiList.add(wifiBeans.get(i).getName());
        }
        return Utils.stringListToString(wifiList);
    }
}
