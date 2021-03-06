package com.zhixing.work.zhixin.view.clock;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.AttendanceRecordsBean;
import com.zhixing.work.zhixin.bean.WifiBean;
import com.zhixing.work.zhixin.common.HeartBeatTimer;
import com.zhixing.work.zhixin.dialog.ClockDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AttendanceResult;
import com.zhixing.work.zhixin.network.response.StaffAttendanceRecordResult;
import com.zhixing.work.zhixin.network.response.WifiListResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.NetworkUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.SysInfoUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/30.
 * Description: 打卡页面
 */

public class ClockInFragment extends SupportFragment {

    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.rb_text1)
    RadioButton rbText1;
    @BindView(R.id.rb_text2)
    RadioButton rbText2;
    @BindView(R.id.rb_text3)
    RadioButton rbText3;
    @BindView(R.id.rb_text4)
    RadioButton rbText4;
    @BindView(R.id.rb_text5)
    RadioButton rbText5;
    @BindView(R.id.rb_text6)
    RadioButton rbText6;
    @BindView(R.id.rb_text7)
    RadioButton rbText7;
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
    @BindView(R.id.tv_end_clock_status_miss)
    TextView tvEndClockStatusMiss;
    @BindView(R.id.tv_end_clock_status_late)
    TextView tvEndClockStatusLate;
    @BindView(R.id.tv_arrive)
    TextView tvArrive;
    @BindView(R.id.tv_leave)
    TextView tvLeave;
    @BindView(R.id.ll_do_clock)
    LinearLayout llDoClock;
    @BindView(R.id.img_clock_mark)
    ImageView imgClockMark;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    Unbinder unbinder;
    @BindView(R.id.tv_end_clock_update)
    TextView tvEndClockUpdate;
    @BindView(R.id.layout_time_line)
    LinearLayout layoutTimeLine;
    @BindView(R.id.ll_clock)
    LinearLayout llClock;

    public static ClockInFragment getInstance() {
        return new ClockInFragment();
    }

    private static final long DAY_LONG = 60 * 60 * 24 * 1000L;

    private Subscription mWifiListSubscription; //考勤组wifi列表
    private Subscription mAttendanceSubscription; //打卡
    private Subscription mAttendanceRecordSubscription; //某时段打卡记录

    private String mStaffId;
    private String mStartDate;
    private String mEndDate;
    private String mWifiName;
    private String mBssid;
    private String mClientCode;
    private List<WifiBean> mWifiList;
    private List<AttendanceRecordsBean> mAttendanceRecords;
    private AttendanceRecordsBean mAttendanceBean;
    private long mCurrentTime;
    private int mFlexTime; //弹性时间
    private int mAbsenteeismTime; //旷工时间
    private String mCurrentServiceTime;
    private long mServiceTimeLong;
    private boolean mIsStart; //是否为上班打卡


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerRequest();
        initView();
        mServiceTimeLong = System.currentTimeMillis();
        return view;
    }

    private void registerRequest() {
        mWifiListSubscription = RxBus.getInstance().toObservable(WifiListResult.class).subscribe(
                result -> handlerWifiListResult(result)
        );

        mAttendanceSubscription = RxBus.getInstance().toObservable(AttendanceResult.class).subscribe(
                result -> handlerAttendanceResult(result)
        );

        mAttendanceRecordSubscription = RxBus.getInstance().toObservable(StaffAttendanceRecordResult.class).subscribe(
                result -> handlerStaffAttendanceRecordResult(result)
        );

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2000:
                    mServiceTimeLong += 1000;
                    if (isWorkWifi()) {
                        tvLeave.setText(DateUtils.getDfHourMinSec(mServiceTimeLong));
                    }
                    break;
            }
        }
    };

    /**
     * wifi列表
     *
     * @param result
     */
    private void handlerWifiListResult(WifiListResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mCurrentServiceTime = result.dateTime;
            mServiceTimeLong = DateUtils.doDate2Long(mCurrentServiceTime);
            HeartBeatTimer.getInstance().start(1, new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(2000);
                }
            });

            getAttendanceRecord();

            if (result.getContent() != null) {
                mWifiList = result.getContent();
            }


        }
    }

    /**
     * 打卡结果
     *
     * @param result
     */
    private void handlerAttendanceResult(AttendanceResult result) {

        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.Code == NetworkConstant.SUCCESS_CODE && !TextUtils.isEmpty(result.getContent())) {
                String dateTime = result.getContent();
                ClockDialog dialog = new ClockDialog(getActivity());
                dialog.setClockData(mIsStart ? 0 : 1, dateTime.substring((dateTime.length() - 8), dateTime.length()));
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            getAttendanceRecord();
        } else {
            AlertUtils.show("打卡失败:" + result.Message);
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
            if(result.getRequestType() != 0){
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
            if (mAttendanceBean != null) {
                if (isCurrentDate()) { //当前天
                    tvStartTime.setText(String.format("上班时间 %s", mAttendanceBean.getStartTime()));
                    tvEndClockTime.setText(String.format("下班时间 %s", mAttendanceBean.getEndTime()));
                    mFlexTime = mAttendanceBean.getFlexTime(); //活动时间
                    mAbsenteeismTime = mAttendanceBean.getAbsenteeismTime();//旷工时间
                    if (TextUtils.isEmpty(mAttendanceBean.getClockInTime())) {//上班未打卡
                        mIsStart = true;
                    } else {
                        mIsStart = false;
                        setClockInStatus(false);
                    }
                    if (!TextUtils.isEmpty(mAttendanceBean.getClockOutTime())) {
                        setClockOutStatus(false);
                    }

                } else { //非当前天
                    mIsStart = false;
                    if (TextUtils.isEmpty(mAttendanceBean.getClockInTime())) {
                        setClockInStatus(true);
                    } else {
                        setClockInStatus(false);
                    }

                    if (TextUtils.isEmpty(mAttendanceBean.getClockOutTime())) {
                        setClockOutStatus(true);
                    } else {
                        setClockOutStatus(false);
                    }
                }
                if (isCurrentDate() && (TextUtils.isEmpty(mAttendanceBean.getClockInTime()) || TextUtils.isEmpty(mAttendanceBean.getClockOutTime()))) {
                    llClock.setVisibility(View.VISIBLE);
                    if (NetworkUtil.isWifi(getActivity())) {
                        if (isWorkWifi()) {
                            llDoClock.setEnabled(true);
                            imgClockMark.setEnabled(true);
                            tvHint.setText(ResourceUtils.getString(R.string.wifi_usable));
                            tvArrive.setText(mIsStart ? "上班打卡" : "下班打卡");
                            tvLeave.setText(DateUtils.getDfHourMinSec(mServiceTimeLong));
                        } else {
                            llDoClock.setEnabled(false);
                            imgClockMark.setEnabled(false);
                            tvHint.setText(ResourceUtils.getString(R.string.wifi_unusable));
                            tvArrive.setText("请先连接");
                            tvLeave.setText("打卡wifi");
                        }
                    }
                } else {
                    llClock.setVisibility(View.GONE);
                }
            } else {
                setClockInStatus(true);
                setClockOutStatus(true);
            }

        } else {
            AlertUtils.show(result.Message);
        }
    }

    //上班打卡
    private void setClockInStatus(boolean isMiss) {
        tvClockStatusMiss.setVisibility(View.GONE);
        tvClockStatusLate.setVisibility(View.GONE);
        tvClockStatusNormal.setVisibility(View.GONE);
        tvWorkTime.setVisibility(View.GONE);
        tvWorkTimeDetail.setText("");
        tvEndWorkTimeDetail.setText("");
        if (isMiss) {
            tvClockStatusMiss.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.isEmpty(mAttendanceBean.getClockInTime())) {
                tvWorkTime.setVisibility(View.VISIBLE);
                tvWorkTimeDetail.setText(mAttendanceBean.getClockInTime());
                String ruleStartTime = DateUtils.getCurrentDate() + " " + mAttendanceBean.getStartTime();
                String currentStartTime = DateUtils.getCurrentDate() + " " + mAttendanceBean.getClockInTime();

                long startTime = DateUtils.stringDateToLong(ruleStartTime);
                long clockInTime = DateUtils.stringDateToLong(currentStartTime); //打卡时间毫秒值

                if (clockInTime < startTime || (clockInTime - startTime) < mFlexTime * 60 * 1000) {
                    tvClockStatusNormal.setVisibility(View.VISIBLE);
                } else {
                    tvClockStatusLate.setVisibility(View.VISIBLE);
                }
            }

        }


    }

    //下班打卡
    private void setClockOutStatus(boolean isMiss) {
        tvEndClockStatusMiss.setVisibility(View.GONE);
        tvEndClockStatusLate.setVisibility(View.GONE);
        tvEndClockStatusNormal.setVisibility(View.GONE);
        tvEndClockUpdate.setVisibility(View.GONE);
        tvEndWorkTime.setVisibility(View.GONE);

        if (isMiss) {
            tvEndClockStatusMiss.setVisibility(View.VISIBLE);
        } else {
            tvEndClockUpdate.setVisibility(View.VISIBLE);
            tvEndWorkTime.setVisibility(View.VISIBLE);
            tvEndWorkTimeDetail.setText(mAttendanceBean.getClockOutTime());
            tvEndClockStatusMiss.setVisibility(View.GONE);
            tvEndClockStatusLate.setVisibility(View.GONE);
            tvEndClockStatusNormal.setVisibility(View.GONE);

            String ruleStartTime = DateUtils.getCurrentDate() + " " + mAttendanceBean.getStartTime();
            String currentEndTime = DateUtils.getCurrentDate() + " " + mAttendanceBean.getClockOutTime();
            String ruleEndTime = DateUtils.getCurrentDate() + " " + mAttendanceBean.getEndTime();

            long endTime = DateUtils.stringDateToLong(ruleEndTime, DateUtils.DF_DEFAULT); //规定下班时间
            long clockOutTime = DateUtils.stringDateToLong(currentEndTime, DateUtils.DF_DEFAULT); //下班打卡时间毫秒值
            //正常
            //早退
            //旷工
            if (endTime < clockOutTime) {
                tvEndClockStatusNormal.setVisibility(View.VISIBLE);
            } else if (endTime > clockOutTime) {
                tvEndClockStatusLate.setVisibility(View.VISIBLE);
            }
        }


    }

    private void initView() {
        tvMonth.setText(DateUtils.getCurrentDate().substring(5, 7));
        mStaffId = String.valueOf(SettingUtils.getTokenBean().getStaffId());
        mWifiName = NetworkUtil.getWifiSSId(getActivity());
        mBssid = NetworkUtil.getWifiBssId(getActivity());
        mClientCode = SysInfoUtil.getIMEI(getActivity());
        mStartDate = DateUtils.getCurrentDate();
        mEndDate = mStartDate;
        mWifiList = new ArrayList<>();
        mAttendanceRecords = new ArrayList<>();
        mCurrentTime = System.currentTimeMillis();
        rbText1.setText(getDayOfMonth(mCurrentTime - 3 * DAY_LONG));
        rbText2.setText(getDayOfMonth(mCurrentTime - 2 * DAY_LONG));
        rbText3.setText(getDayOfMonth(mCurrentTime - DAY_LONG));
        rbText4.setText(getDayOfMonth(mCurrentTime));
        rbText5.setText(getDayOfMonth(mCurrentTime + DAY_LONG));
        rbText6.setText(getDayOfMonth(mCurrentTime + +2 * DAY_LONG));
        rbText7.setText(getDayOfMonth(mCurrentTime + 3 * +DAY_LONG));
        rbText5.setEnabled(false);
        rbText6.setEnabled(false);
        rbText7.setEnabled(false);
        llDoClock.setEnabled(false);
        imgClockMark.setEnabled(false);
        tvHint.setText(ResourceUtils.getString(R.string.wifi_usable));
        tvArrive.setText("请先连接");
        tvLeave.setText("打卡wifi");

        getWifiList();
    }

    //获取打卡记录
    private void getAttendanceRecord() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        map.put(RequestConstant.KEY_START_DATE, mStartDate);
        map.put(RequestConstant.KEY_END_DATE, mEndDate);
        map.put(RequestConstant.KEY_REQUEST_TYPE,"0");
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_USER_ATTENDANCE_RECORD, map);

    }

    //获取wifi列表
    private void getWifiList() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_USER_ATTENDANCE_WIFI_LIST);
    }

    //员工打卡
    private void staffAttendance() {
        showLoading(ResourceUtils.getString(R.string.loading_default), true);
        Map map = new HashMap();
        String wifiName = mWifiName.substring(1, mWifiName.length() - 1);
        map.put(RequestConstant.KEY_WIFI_NAME, wifiName);
        map.put(RequestConstant.KEY_BSS_ID, mBssid);
        map.put(RequestConstant.KEY_CLIENT_CODE, mClientCode);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_STAFF_ATTENDANCE, map);
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.getInstance().unsubscribe(mAttendanceRecordSubscription, mAttendanceSubscription, mWifiListSubscription);
        HeartBeatTimer.getInstance().shutdown();
    }


    @OnClick({R.id.tv_end_clock_update, R.id.tv_month, R.id.ll_do_clock,
            R.id.rb_text1, R.id.rb_text2, R.id.rb_text3, R.id.rb_text4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_end_clock_update:
                staffAttendance();
                break;
            case R.id.tv_month:

                break;
            case R.id.ll_do_clock:
                staffAttendance();
                break;
            case R.id.rb_text1:
                mStartDate = DateUtils.longToDate(mCurrentTime - 3 * DAY_LONG);
                mEndDate = mStartDate;
                getAttendanceRecord();
                break;
            case R.id.rb_text2:
                mStartDate = DateUtils.longToDate(mCurrentTime - 2 * DAY_LONG);
                mEndDate = mStartDate;
                getAttendanceRecord();
                break;
            case R.id.rb_text3:
                mStartDate = DateUtils.longToDate(mCurrentTime - 1 * DAY_LONG);
                mEndDate = mStartDate;
                getAttendanceRecord();
                break;
            case R.id.rb_text4:
                mStartDate = DateUtils.longToDate(mCurrentTime);
                mEndDate = mStartDate;
                getAttendanceRecord();
                break;
        }
    }

    private String getDayOfMonth(long mill) {
        int dayOfMonth = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mill);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(dayOfMonth);
    }

    /**
     * 判断当前wifi是否为工作wifi
     *
     * @return
     */
    private boolean isWorkWifi() {
        boolean isWifi = false;
        if (TextUtils.isEmpty(mBssid)) {
            return isWifi;
        } else {
            for (int i = 0; i < mWifiList.size(); i++) {
                if (mWifiList.get(i).getBssId().equals(mBssid)) {
                    isWifi = true;
                }
            }
        }
        return isWifi;
    }

    //判断是否为当前天
    private boolean isCurrentDate() {
        if (mStartDate.equals(DateUtils.getCurrentDate())) {
            return true;
        } else {
            return false;
        }
    }

}
