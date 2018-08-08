package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseListActivity;
import com.zhixing.work.zhixin.bean.StatisticsMonthDataBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;

/**
 * Created by lhj on 2018/8/3.
 * Description:
 */

public class StatisticsMonthDetailActivity extends BaseListActivity<StatisticsMonthDataBean> {

    public static final String INTENT_KEY_DATE = "date";
    public static final String INTENT_KEY_STATUS = "clockStatus";
    public static final String INTENT_KEY_TOTAL = "total";

    @BindView(R.id.tv_statistics_total)
    TextView tvStatisticsTotal;


    public static void startMonthDetailActivity(Activity activity, String date, String status, String total) {
        Intent intent = new Intent(activity, StatisticsMonthDetailActivity.class);
        intent.putExtra(INTENT_KEY_DATE, date);
        intent.putExtra(INTENT_KEY_STATUS, status);
        intent.putExtra(INTENT_KEY_TOTAL, total);
        activity.startActivity(intent);
    }

    private String mDate;
    private String mStatus;
    private String mTotal;


    @Override
    protected boolean addDecoration() {
        return true;
    }

    @Override
    protected void setContentViewLayout() {
        setContentView(R.layout.activity_statistic_month_detail);
    }

    @Override
    protected void dispatchRequest() {

    }

    @Override
    protected void initView() {
        getIntentData();
        setViewData();
    }

    public void getIntentData() {
        Intent data = getIntent();
        mDate = data.getStringExtra(INTENT_KEY_DATE);
        mStatus = data.getStringExtra(INTENT_KEY_STATUS);
        mTotal = data.getStringExtra(INTENT_KEY_TOTAL);
    }

    private void setViewData() {
        switch (mStatus) {
            case ResultConstant.CLOCK_STATUS_LATE:
                setTitle(ResourceUtils.getString(R.string.attendance_late));
                tvStatisticsTotal.setText(String.format("%s 迟到 共%s人", mDate, mTotal));
                break;
            case ResultConstant.CLOCK_STATUS_EARLY:
                setTitle(ResourceUtils.getString(R.string.attendance_before));
                tvStatisticsTotal.setText(String.format("%s 早退 共%s人", mDate, mTotal));
                break;
            case ResultConstant.CLOCK_STATUS_MISS:
                setTitle(ResourceUtils.getString(R.string.attendance_miss));
                tvStatisticsTotal.setText(String.format("%s 缺卡 共%s人", mDate, mTotal));
                break;
            case ResultConstant.CLOCK_STATUS_ABSENTEEISM:
                setTitle(ResourceUtils.getString(R.string.attendance_absenteeism));
                tvStatisticsTotal.setText(String.format("%s 旷工 共%s人", mDate, mTotal));
                break;
        }
    }

    @Override
    public void onItemClicked(StatisticsMonthDataBean bean) {
        StaffAttendanceStatisticsActivity.startStaffAttendanceStatisticsActivity(this,
                mDate, bean.getStaffId());
    }
}
