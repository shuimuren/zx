package com.zhixing.work.zhixin.view.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.util.ScreenUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.CircleAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/7/31.
 * Description:
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

    public static ClockStatisticsDayFragment getInstance() {
        return new ClockStatisticsDayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_statistics_day, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //setRect
        int screenWith = ScreenUtils.getScreenWidth(getActivity());
        int marginTop = Utils.dp2px(getActivity(),30);
        circleView.setRect(screenWith/4,marginTop,screenWith*3/4,marginTop + screenWith/2);
        circleView.render();
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_time, R.id.tv_clock_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                break;
            case R.id.tv_clock_detail:
                startActivity(new Intent(getActivity(),ClockInfoDetailActivity.class));
                break;
        }
    }
}
