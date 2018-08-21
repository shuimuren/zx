package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.AttendanceRecordsBean;
import com.zhixing.work.zhixin.common.ClockManagerHelper;
import com.zhixing.work.zhixin.common.Day;
import com.zhixing.work.zhixin.common.DayManager;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;
import com.zhixing.work.zhixin.network.response.StaffAttendanceRecordResult;
import com.zhixing.work.zhixin.network.response.StatisticsMonthResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.widget.CalendarView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/30.
 * Description:
 */

public class ClockStatisticsFragment extends SupportFragment {

    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.img_before)
    ImageView imgBefore;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.img_next)
    ImageView imgNext;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.view_start_time)
    View viewStartTime;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_end_time)
    View viewEndTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.tv_work_time_detail)
    TextView tvWorkTimeDetail;
    @BindView(R.id.tv_clock_status_normal)
    TextView tvClockStatusNormal;
    @BindView(R.id.tv_clock_status_miss)
    TextView tvClockStatusMiss;
    @BindView(R.id.tv_clock_status_late)
    TextView tvClockStatusLate;
    @BindView(R.id.ll_start_clock)
    LinearLayout llStartClock;
    @BindView(R.id.tv_end_clock_time)
    TextView tvEndClockTime;
    @BindView(R.id.tv_end_work_time)
    TextView tvEndWorkTime;
    @BindView(R.id.tv_end_work_time_detail)
    TextView tvEndWorkTimeDetail;
    @BindView(R.id.tv_end_clock_status_normal)
    TextView tvEndClockStatusNormal;
    @BindView(R.id.tv_end_clock_update)
    TextView tvEndClockUpdate;
    @BindView(R.id.tv_end_clock_status_miss)
    TextView tvEndClockStatusMiss;
    @BindView(R.id.tv_end_clock_status_late)
    TextView tvEndClockStatusLate;
    @BindView(R.id.layout_time_line)
    LinearLayout layoutTimeLine;
    @BindView(R.id.tv_need_num)
    TextView tvNeedNum;
    @BindView(R.id.tv_complete_num)
    TextView tvCompleteNum;
    @BindView(R.id.tv_rest_num)
    TextView tvRestNum;
    @BindView(R.id.tv_late_num)
    TextView tvLateNum;
    @BindView(R.id.tv_leave_num)
    TextView tvLeaveNum;
    @BindView(R.id.tv_deficiency_num)
    TextView tvDeficiencyNum;
    @BindView(R.id.tv_absenteeism_num)
    TextView tvAbsenteeismNum;
    Unbinder unbinder;

    public static ClockStatisticsFragment getInstance() {
        return new ClockStatisticsFragment();
    }

  //  private Subscription mAttendanceRecordMonthSubscription;//获取月打卡记录
    private Subscription mAttendanceRecordSubscription; //某时段打卡记录
  //  private Subscription mStatisticsMonthSubscription;//月统计
    private AttendanceRecordsBean mAttendanceBean;
    private String mDate;
    private String mStaffId;
    private String mStartDate;
    private String mEndDate;
    private ClockManagerHelper mClockHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerRequest();
        initView();
        return view;
    }

    private void initView() {
        mStaffId = String.valueOf(SettingUtils.getTokenBean().getStaffId());
        mDate = DateUtils.getCurrentDate().substring(0, 7);
        calendarView.setOnSelectChangeListener(new CalendarView.OnSelectChangeListener() {
            @Override
            public void selectChange(CalendarView calendarView, Date date) {
                Logger.i(">>>", "Data>" + date + ">>" + DateUtils.doDate2String(date));
            }
        });
        mClockHelper = ClockManagerHelper.getInstance();
        DayManager.addNomalDays(1); DayManager.addNomalDays(3);
        DayManager.addNomalDays(5);
        DayManager.addNomalDays(7);
        DayManager.addNomalDays(9);
        DayManager.addNomalDays(10);
        DayManager.addNomalDays(15);

        DayManager.addAbnormalDays(2);
        DayManager.addAbnormalDays(4);
        DayManager.addAbnormalDays(6);
        DayManager.addAbnormalDays(8);
        DayManager.addAbnormalDays(11);

        DayManager.addOutDays(12);
        DayManager.addOutDays(13);
        DayManager.addOutDays(14);
        DayManager.addOutDays(15);
    }

    private void registerRequest() {
//        mAttendanceRecordMonthSubscription = RxBus.getInstance().toObservable(AttendanceRecordMonthResult.class).subscribe(
//                result -> handlerAttendanceRecordMonthResult(result)
//        );
        mAttendanceRecordSubscription = RxBus.getInstance().toObservable(StaffAttendanceRecordResult.class).subscribe(
                result -> handlerStaffAttendanceRecordResult(result)
        );
//        mStatisticsMonthSubscription = RxBus.getInstance().toObservable(StatisticsMonthResult.class).subscribe(
//                result -> handlerStaticsMonthResult(result)
//        );
    }

//    //月统计
//    private void handlerStaticsMonthResult(StatisticsMonthResult result) {
//
//    }

    //月统计->个人记录
    private void handlerAttendanceRecordMonthResult(AttendanceRecordMonthResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {

            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    /**
     * 打卡记录
     *
     * @param result
     */
    private void handlerStaffAttendanceRecordResult(StaffAttendanceRecordResult result) {
        hideLoadingDialog();
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getRequestType() != 1) {
                return;
            }
            userName.setText(result.getContent().getStaffName());
            GlideUtils.getInstance().loadPublicCircleWithDefault(getActivity(), ResourceUtils.getDrawable(R.drawable.icon_avatar),
                    result.getContent().getStaffAvatar(), userAvatar);

            if (result.getContent().getAttendanceRecords() != null && result.getContent().getAttendanceRecords().size() > 0) {
                mAttendanceBean = result.getContent().getAttendanceRecords().get(0);
            } else {
                mAttendanceBean = null;
            }
            initClockInView();
            initClockOutView();


        } else {
            AlertUtils.show(result.Message);
        }
    }

    //上班打卡View
    private void initClockInView() {

    }

    //下班打卡View
    private void initClockOutView() {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_before, R.id.img_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_before:

                break;
            case R.id.img_next:

                break;
        }
    }

    private void getAttendanceRecordMonth() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        map.put(RequestConstant.KEY_DATE_DATE, mDate);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RECORD_STATISTICS_MONTH, map);
    }

    //获取打卡记录
    private void getAttendanceRecord() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        map.put(RequestConstant.KEY_START_DATE, mStartDate);
        map.put(RequestConstant.KEY_END_DATE, mEndDate);
        map.put(RequestConstant.KEY_REQUEST_TYPE, "1");
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_USER_ATTENDANCE_RECORD, map);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mAttendanceRecordSubscription);
    }
}
