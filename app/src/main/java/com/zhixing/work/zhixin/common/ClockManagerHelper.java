package com.zhixing.work.zhixin.common;

import android.text.TextUtils;

import com.zhixing.work.zhixin.bean.AttendanceRecordsBean;
import com.zhixing.work.zhixin.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhj on 2018/8/20.
 * Description: 打卡统计辅助类
 */

public class ClockManagerHelper {

    private List<AttendanceRecordsBean> mAttendanceRecords;
    private List<AttendanceRecordsBean> mAttendanceLateDays;//迟到
    private List<AttendanceRecordsBean> mAttendanceEarlyDays;//早退
    private List<AttendanceRecordsBean> mAttendanceNotClock;//缺卡
    private List<AttendanceRecordsBean> mAttendanceAbsenteeism;//旷工
    private int mFlexTime; //弹性时间
    private int mAbsenteeismTime; //旷工时间


    private ClockManagerHelper() {
        mAttendanceRecords = new ArrayList<>();
        mAttendanceLateDays = new ArrayList<>();
        mAttendanceEarlyDays = new ArrayList<>();
        mAttendanceNotClock = new ArrayList<>();
        mAttendanceAbsenteeism = new ArrayList<>();
    }

    public static ClockManagerHelper getInstance() {
        return ClockManagerHelperHolder.managerHelper;
    }

    private static class ClockManagerHelperHolder {
        public static ClockManagerHelper managerHelper = new ClockManagerHelper();
    }

    public void setAttendanceRecords(List<AttendanceRecordsBean> attendanceRecords) {
        this.mAttendanceRecords = attendanceRecords;
    }

    public void setRuleTime(int flexTime, int AbsenteeismTime) {
        this.mFlexTime = flexTime;
        this.mAbsenteeismTime = AbsenteeismTime;
    }

    private void resolverRecord() {
        int size = mAttendanceRecords.size();
        Logger.i(">>>", "size>" + size);
        for (int i = 0; i < size; i++) {
            AttendanceRecordsBean bean = mAttendanceRecords.get(i);
            //缺卡
            if (TextUtils.isEmpty(bean.getClockInTime()) || TextUtils.isEmpty(bean.getClockOutTime())) {

            }
            String ruleStartTime = bean.getClockDate().substring(0, 10) + " " + bean.getStartTime();
            String currentStartTime = bean.getClockDate().substring(0, 10) + " " + bean.getClockInTime();

            long startTime = DateUtils.stringDateToLong(ruleStartTime);
            long clockInTime = DateUtils.stringDateToLong(currentStartTime); //打卡时间毫秒值

            String currentEndTime = bean.getClockDate().substring(0, 10) + " " + bean.getClockOutTime();
            String ruleEndTime = bean.getClockDate().substring(0, 10) + " " + bean.getEndTime();

            long endTime = DateUtils.stringDateToLong(ruleEndTime, DateUtils.DF_DEFAULT); //规定下班时间
            long clockOutTime = DateUtils.stringDateToLong(currentEndTime, DateUtils.DF_DEFAULT); //下班打卡时间毫秒值

//            if (clockInTime < startTime || (clockInTime - startTime) < mFlexTime * 60 * 1000) {
//                tvClockStatusNormal.setVisibility(View.VISIBLE);
//            } else {
//                tvClockStatusLate.setVisibility(View.VISIBLE);
//            }
            //迟到
//            if () {
//                continue;
//            }
//            //
//            if () {
//                continue;
//            }
//            //缺卡
//            if () {
//                continue;
//            }
//            //旷工
//            if () {
//                continue;
//            }
        }
    }

    /**
     * 通过日期查找记录
     *
     * @return
     */
    public AttendanceRecordsBean getRecordsByDate(String date) {
        int size = mAttendanceRecords.size();
        AttendanceRecordsBean bean = null;
        for (int i = 0; i < size; i++) {
            if (date.equals(mAttendanceRecords.get(i).getClockDate().substring(0, 10))) {
                bean = mAttendanceRecords.get(i);
                break;
            }
        }
        return bean;
    }


}
