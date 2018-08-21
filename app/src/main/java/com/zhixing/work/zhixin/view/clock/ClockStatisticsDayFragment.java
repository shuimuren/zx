package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.ClockDailyBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.DateTimePickDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.StatisticsDailyResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ScreenUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.CircleAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/31.
 * Description:日统计
 */

public class ClockStatisticsDayFragment extends SupportFragment {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.circle_view)
    CircleAnimation circleView;
    @BindView(R.id.tv_scale)
    TextView tvScale;
    @BindView(R.id.tv_clock_detail)
    TextView tvClockDetail;
    @BindView(R.id.tv_late_num)
    TextView tvLateNum;
    @BindView(R.id.tv_late)
    TextView tvLate;
    @BindView(R.id.tv_miss_num)
    TextView tvMissNum;
    @BindView(R.id.tv_miss)
    TextView tvMiss;
    Unbinder unbinder;

    private Subscription mClockStatisticsDaySubscription;
    private String mDate;
    private ClockDailyBean mDailyBean;

    public static ClockStatisticsDayFragment getInstance() {
        return new ClockStatisticsDayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_statistics_day, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerRequest();
        initView();
        return view;
    }

    private void registerRequest() {
        mClockStatisticsDaySubscription = RxBus.getInstance().toObservable(StatisticsDailyResult.class).subscribe(
                result -> handlerStatisticsDay(result)
        );
    }

    private void handlerStatisticsDay(StatisticsDailyResult result) {
        hideLoadingDialog();
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mDailyBean = result.getContent();
            tvScale.setText(String.format("%s / %s", String.valueOf(result.getContent().getSignedCount()),
                    String.valueOf(result.getContent().getTotal())));
            tvLateNum.setText(String.valueOf(result.getContent().getLateCount()));
            tvMissNum.setText(String.valueOf(result.getContent().getAbsenteeismCount()));
            if (result.getContent().getLateCount() > 0) {
                tvLate.setEnabled(true);
                tvLateNum.setEnabled(true);
            } else {
                tvLate.setEnabled(false);
                tvLateNum.setEnabled(false);
            }

            if (result.getContent().getAbsenteeismCount() > 0) {
                tvMiss.setEnabled(true);
                tvMissNum.setEnabled(true);
            } else {
                tvMissNum.setEnabled(false);
                tvMiss.setEnabled(false);
            }
            if (result.getContent().getSignedCount() > 0 && result.getContent().getTotal() > 0) {
                circleView.setAngle(0, result.getContent().getSignedCount() * 360 / result.getContent().getTotal());
                circleView.refresh();
            } else {
                circleView.setAngle(0, 0);
                circleView.refresh();
            }


        } else {
            AlertUtils.show(result.Message);
        }
    }

    private void initView() {
        int screenWith = ScreenUtils.getScreenWidth(getActivity());
        int marginTop = Utils.dp2px(getActivity(), 30);
        circleView.setRect(screenWith / 4, marginTop, screenWith * 3 / 4, marginTop + screenWith / 2);
        circleView.render();
        mDate = DateUtils.getCurrentDate();
        tvTime.setText(mDate);
        getDate(mDate);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void getDate(String date) {
        showLoading(ResourceUtils.getString(R.string.loading_default), false);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RECORD_STATISTICS_DAILY, date);
    }

    @Override
    public void fetchData() {
        Logger.i(">>>", "fetchData>");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.getInstance().unsubscribe(mClockStatisticsDaySubscription);
    }

    @OnClick({R.id.tv_time, R.id.tv_clock_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                DateTimePickDialog dataPickDialogStr = new DateTimePickDialog(getActivity(), tvTime.getText().toString());
                dataPickDialogStr.dateTimePicKDialog(tvTime);
                dataPickDialogStr.setButtonClickListener(new DateTimePickDialog.OnButtonClickListener() {
                    @Override
                    public void onPositive(String date) {
                        mDate = tvTime.getText().toString();
                        getDate(mDate);
                    }
                });
                break;
            case R.id.tv_clock_detail:
                if (mDailyBean != null) {
                    ClockInfoDetailActivity.startClockInfoDetailActivity(getActivity(), mDailyBean, mDate);
                } else {
                    AlertUtils.show("数据异常,请稍后重试");
                }
                break;
        }
    }
}
