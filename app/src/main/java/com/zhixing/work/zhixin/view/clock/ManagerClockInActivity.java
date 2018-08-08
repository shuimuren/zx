package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ManagerClockInActivity extends SupportActivity {

    @BindView(R.id.fm_personal_clock)
    FrameLayout fmPersonalClock;
    @BindView(R.id.tv_clock_in)
    TextView tvClockIn;
    @BindView(R.id.tv_clock_statistics)
    TextView tvClockStatistics;
    @BindView(R.id.tv_clock_setting)
    TextView tvClockSetting;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_clock_in);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.work_clock_in));
        initView();
    }

    private void initView() {
        tvClockIn.setSelected(true);
        SupportFragment clockInFragment = findFragment(ClockInFragment.class);
        if (findFragment(ClockInFragment.class) == null) {
            mFragments[0] = ClockInFragment.getInstance();
            mFragments[1] = ManagerStatisticsFragment.getInstance();
            mFragments[2] = ClockSettingFragment.getInstance();
            loadMultipleRootFragment(R.id.fm_personal_clock, 0,
                    mFragments[0], mFragments[1], mFragments[2]);
        } else {
            mFragments[0] = clockInFragment;
            mFragments[1] = findFragment(ManagerStatisticsFragment.class);
            mFragments[2] = findFragment(ClockSettingFragment.class);

        }
    }

    @OnClick({R.id.tv_clock_in, R.id.tv_clock_statistics, R.id.tv_clock_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clock_in:
                tvClockIn.setSelected(true);
                tvClockStatistics.setSelected(false);
                tvClockSetting.setSelected(false);
                showHideFragment(mFragments[0]);
                setTitle(ResourceUtils.getString(R.string.work_clock_in));
                break;
            case R.id.tv_clock_statistics:
                tvClockIn.setSelected(false);
                tvClockStatistics.setSelected(true);
                tvClockSetting.setSelected(false);
                showHideFragment(mFragments[1]);
                setTitle(ResourceUtils.getString(R.string.clock_in_statistics));
                break;
            case R.id.tv_clock_setting:
                tvClockSetting.setSelected(true);
                tvClockIn.setSelected(false);
                tvClockStatistics.setSelected(false);
                showHideFragment(mFragments[2]);
                setTitle(ResourceUtils.getString(R.string.attendance_setting_title));
                break;
        }
    }
}
