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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    public static ClockStatisticsMonthFragment getInstance() {
        return new ClockStatisticsMonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_statistics_month, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {

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
                break;
            case R.id.ll_before:
                break;
            case R.id.ll_miss:
                break;
            case R.id.ll_absenteeism:
                break;
        }
    }
}
