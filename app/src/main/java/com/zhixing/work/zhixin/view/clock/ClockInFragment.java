package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.dialog.ClockDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.tv_end_clock_start_time)
    TextView tvEndClockStartTime;
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
    @BindView(R.id.tv_work_time_update)
    TextView tvWorkTimeUpdate;
    @BindView(R.id.tv_end_clock_update)
    TextView tvEndClockUpdate;

    public static ClockInFragment getInstance() {
        return new ClockInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_work_time_update, R.id.tv_end_clock_update,R.id.tv_month,R.id.ll_do_clock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_work_time_update:
                break;
            case R.id.tv_end_clock_update:
                break;
            case R.id.tv_month:
                break;
            case R.id.ll_do_clock:
                ClockDialog dialog = new ClockDialog(getActivity());
                dialog.setClockData(0,"08:54");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
        }
    }
}
