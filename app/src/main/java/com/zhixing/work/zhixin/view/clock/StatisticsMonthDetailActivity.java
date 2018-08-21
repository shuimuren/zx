package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseListActivity;
import com.zhixing.work.zhixin.bean.StatisticsMonthDataBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.StatisticsMonthDetailResult;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by lhj on 2018/8/3.
 * Description:
 */

public class StatisticsMonthDetailActivity extends BaseListActivity<StatisticsMonthDataBean> {

    public static final String INTENT_KEY_DATE = "date";
    public static final String INTENT_KEY_STATUS = "clockStatus";
    public static final String INTENT_KEY_TOTAL = "total";


    TextView tvStatisticsTotal;
    private Subscription mStatisticsDetailSubscription;

    public static void startMonthDetailActivity(Activity activity, String date, String status, int total) {
        Intent intent = new Intent(activity, StatisticsMonthDetailActivity.class);
        intent.putExtra(INTENT_KEY_DATE, date);
        intent.putExtra(INTENT_KEY_STATUS, status);
        intent.putExtra(INTENT_KEY_TOTAL, total);
        activity.startActivity(intent);
    }

    private String mDate;
    private String mStatus;
    private int mTotal;


    @Override
    protected boolean addDecoration() {
        return true;
    }

    @Override
    protected void setContentViewLayout() {
        setContentView(R.layout.activity_statistic_month_detail);
        tvStatisticsTotal = findViewById(R.id.tv_statistics_total);
        mStatisticsDetailSubscription = RxBus.getInstance().toObservable(StatisticsMonthDetailResult.class).subscribe(
                result -> handlerStatisticDetailResult(result)
        );
    }

    private void handlerStatisticDetailResult(StatisticsMonthDetailResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            onGetListSucceeded(result.getContent().getTotalPage(),result.getContent().getData());
        }else{
            onGetListFailed(result.Message);
        }
    }



    @Override
    protected void dispatchRequest() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_DATE_DATE,mDate);
        map.put(RequestConstant.KEY_CLOCK_STATUS,mStatus);
        map.put(RequestConstant.KEY_PAGE_INDEX,String.valueOf(mPages));
        map.put(RequestConstant.KEY_ROWS_COUNT,String.valueOf(PAGE_SIZE));
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_STATISTICS_MONTH_DETAIL,map);
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
        mTotal = data.getIntExtra(INTENT_KEY_TOTAL,0);
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
                mDate, bean.getStaffId(),bean.getRealName(),bean.getAvatar(),bean.getDepartmentName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mStatisticsDetailSubscription);
    }
}
