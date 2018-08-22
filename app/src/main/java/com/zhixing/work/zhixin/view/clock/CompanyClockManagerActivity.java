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
 * Description: 考勤打卡-管理员角色/HR
 */

public class CompanyClockManagerActivity extends SupportActivity {

    @BindView(R.id.fm_personal_clock)
    FrameLayout fmPersonalClock;
    @BindView(R.id.tv_clock_statistics)
    TextView tvClockStatistics;
    @BindView(R.id.tv_clock_setting)
    TextView tvClockSetting;
    private SupportFragment[] mFragments = new SupportFragment[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_clock_in);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.clock_in_statistics));
        initView();
    }

    private void initView() {
        tvClockStatistics.setSelected(true);
        SupportFragment managerStatisticsFragment = findFragment(CompanyManagerStatisticsFragment.class);
        if (managerStatisticsFragment == null) {
            mFragments[0] = CompanyManagerStatisticsFragment.getInstance();
            mFragments[1] = ClockSettingFragment.getInstance();
            loadMultipleRootFragment(R.id.fm_personal_clock, 0,
                    mFragments[0], mFragments[1]);
        } else {
            mFragments[0] = findFragment(CompanyManagerStatisticsFragment.class);
            mFragments[1] = findFragment(ClockSettingFragment.class);

        }
    }

    @OnClick({R.id.tv_clock_statistics, R.id.tv_clock_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clock_statistics:
                tvClockStatistics.setSelected(true);
                tvClockSetting.setSelected(false);
                showHideFragment(mFragments[0]);
                setTitle(ResourceUtils.getString(R.string.clock_in_statistics));
                break;
            case R.id.tv_clock_setting:
                tvClockSetting.setSelected(true);
                tvClockStatistics.setSelected(false);
                showHideFragment(mFragments[1]);
                setTitle(ResourceUtils.getString(R.string.attendance_setting_title));
                break;
        }
    }
}
