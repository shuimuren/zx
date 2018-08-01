package com.zhixing.work.zhixin.view.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Description: 考勤设置
 */

public class ClockSettingFragment extends SupportFragment {

    @BindView(R.id.tv_add_group)
    TextView tvAddGroup;
    @BindView(R.id.ll_group_empty)
    LinearLayout llGroupEmpty;
    Unbinder unbinder;

    public static ClockSettingFragment getInstance() {
        ClockSettingFragment fragment = new ClockSettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_setting, container, false);
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

    @OnClick(R.id.tv_add_group)
    public void onViewClicked() {
     startActivity(new Intent(getActivity(),CreateAttendanceGroupActivity.class));
    }
}
