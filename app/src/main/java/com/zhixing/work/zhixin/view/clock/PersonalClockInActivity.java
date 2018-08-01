package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/7/30.
 * Description: 个人上班打卡
 */

public class PersonalClockInActivity extends SupportActivity {

    @BindView(R.id.fm_personal_clock)
    FrameLayout fmPersonalClock;
    @BindView(R.id.tv_clock_in)
    TextView tvClockIn;
    @BindView(R.id.tv_clock_statistics)
    TextView tvClockStatistics;
    private Token mToken;

    private SupportFragment[] mFragments = new SupportFragment[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_clock_in);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.work_clock_in));
        mToken = SettingUtils.getTokenBean();
        initView();
    }

    private void initView() {
        tvClockIn.setSelected(true);
        SupportFragment clockInFragment = findFragment(ClockInFragment.class);
        if (findFragment(ClockInFragment.class) == null) {
            mFragments[0] = ClockInFragment.getInstance();
            mFragments[1] = ClockStatisticsFragment.getInstance();
            loadMultipleRootFragment(R.id.fm_personal_clock, 0,
                    mFragments[0],
                    mFragments[1]);
        }else{
            mFragments[0] = clockInFragment;
            mFragments[1] = findFragment(ClockStatisticsFragment.class);
        }
    }


    @OnClick({R.id.tv_clock_in, R.id.tv_clock_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clock_in:
                tvClockIn.setSelected(true);
                tvClockStatistics.setSelected(false);
                showHideFragment(mFragments[0], mFragments[1]);
                break;
            case R.id.tv_clock_statistics:
                tvClockIn.setSelected(false);
                tvClockStatistics.setSelected(true);
                showHideFragment(mFragments[1], mFragments[0]);
                break;
        }
    }
}
