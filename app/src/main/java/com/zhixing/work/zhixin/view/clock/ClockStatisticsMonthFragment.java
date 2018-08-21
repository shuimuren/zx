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
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.StatisticsMonthResult;
import com.zhixing.work.zhixin.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/31.
 * Description: 月统计
 */

public class ClockStatisticsMonthFragment extends SupportFragment {

    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_late_num)
    TextView tvLateNum;
    @BindView(R.id.ll_late)
    LinearLayout llLate;
    @BindView(R.id.tv_before_num)
    TextView tvBeforeNum;
    @BindView(R.id.ll_before)
    LinearLayout llBefore;
    @BindView(R.id.tv_miss_num)
    TextView tvMissNum;
    @BindView(R.id.ll_miss)
    LinearLayout llMiss;
    @BindView(R.id.tv_absenteeism_num)
    TextView tvAbsenteeismNum;
    @BindView(R.id.ll_absenteeism)
    LinearLayout llAbsenteeism;
    Unbinder unbinder;


    private Subscription mStatisticsMonthSubscription;

    private String mMonthData;
    private int mLateTotal, mEarlyTotal, mMissTotal, mAbsenteeismTotal;

    public static ClockStatisticsMonthFragment getInstance() {
        return new ClockStatisticsMonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_statistics_month, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerRequest();
        mMonthData = DateUtils.getCurrentDate().substring(0, 7);
        tvTime.setText(mMonthData);
        return view;
    }

    private void registerRequest() {
        mStatisticsMonthSubscription = RxBus.getInstance().toObservable(StatisticsMonthResult.class).subscribe(
                result -> handlerStaticsMonthResult(result)
        );
    }

    private void handlerStaticsMonthResult(StatisticsMonthResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mLateTotal = result.getContent().getLateCount();
            mEarlyTotal = result.getContent().getEarlyCount();
            mMissTotal = result.getContent().getNotClockIn();
            mAbsenteeismTotal = result.getContent().getAbsenteeismCount();

            tvLateNum.setText(String.format("%s 人", String.valueOf(mLateTotal)));
            tvBeforeNum.setText(String.format("%s 人", String.valueOf(mEarlyTotal)));
            tvMissNum.setText(String.format("%s 人", String.valueOf(mMissTotal)));
            tvAbsenteeismNum.setText(String.format("%s 人", String.valueOf(mAbsenteeismTotal)));
        }

    }

    @Override
    public void fetchData() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RECORD_STATISTICS_MONTH, mMonthData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_left, R.id.img_right, R.id.ll_late, R.id.ll_before, R.id.ll_miss, R.id.ll_absenteeism})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_left:

                break;
            case R.id.img_right:

                break;
            case R.id.ll_late:
                StatisticsMonthDetailActivity.startMonthDetailActivity(getActivity(), mMonthData,
                        ResultConstant.CLOCK_STATUS_LATE, mLateTotal);
                break;
            case R.id.ll_before:
                StatisticsMonthDetailActivity.startMonthDetailActivity(getActivity(), mMonthData,
                        ResultConstant.CLOCK_STATUS_EARLY, mEarlyTotal);
                break;
            case R.id.ll_miss:
                StatisticsMonthDetailActivity.startMonthDetailActivity(getActivity(), mMonthData,
                        ResultConstant.CLOCK_STATUS_MISS, mMissTotal);
                break;
            case R.id.ll_absenteeism:
                StatisticsMonthDetailActivity.startMonthDetailActivity(getActivity(), mMonthData,
                        ResultConstant.CLOCK_STATUS_ABSENTEEISM, mAbsenteeismTotal);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mStatisticsMonthSubscription);
    }
}
