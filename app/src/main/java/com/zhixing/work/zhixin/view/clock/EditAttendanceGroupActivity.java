package com.zhixing.work.zhixin.view.clock;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.dialog.MultiCheckDialog;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_scope_time)
    TextView tvScopeTime;
    @BindView(R.id.ll_work_scope_time)
    LinearLayout llWorkScopeTime;
    @BindView(R.id.tv_absenteeism)
    TextView tvAbsenteeism;
    @BindView(R.id.ll_absenteeism_time)
    LinearLayout llAbsenteeismTime;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.ll_wifi)
    LinearLayout llWifi;
    @BindView(R.id.btn_finish)
    Button btnNext;

    private String mDayRange;
    private int beginHour, beginMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attendance_group);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.new_add_attendance_group));
        initView();
    }

    private void initView() {
        mDayRange = "";
        beginHour = 9;
        beginMinute = 0;
        endHour = 18;
        endMinute = 0;
    }


    @OnClick({R.id.ll_work_time, R.id.ll_work_begin_time, R.id.ll_work_end_time, R.id.ll_work_scope_time, R.id.ll_absenteeism_time, R.id.ll_wifi, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_work_time:
                MultiCheckDialog dialog = new MultiCheckDialog(this) {
                    @Override
                    protected void onSelectDaysConfirmMethod() {
                        dismiss();
                        String dayName = onGetDayNameResult();
                        mDayRange = onGetDayIdResult();
                    }
                };
                dialog.setDayRange(mDayRange);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.ll_work_begin_time:
                TimePickerDialog beginTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginHour = hourOfDay;
                        beginMinute = minute;
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
                    }
                }, endHour, endMinute, true);
                endTime.setCancelable(true);
                endTime.show();
                break;
            case R.id.ll_work_scope_time:
                break;
            case R.id.ll_absenteeism_time:
                break;
            case R.id.ll_wifi:
                startActivity(new Intent(this,SettingWifiActivity.class));
                break;
            case R.id.btn_next:
                break;
        }
    }
}
