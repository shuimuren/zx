package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.bean.ClockDailyBean;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ScrollableViewPager;
import com.zhixing.work.zhixin.widget.ViewPagerTabIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/31.
 * Description: 打卡明细
 */

public class ClockInfoDetailActivity extends SupportActivity {

    @BindView(R.id.tab_indicator)
    ViewPagerTabIndicator tableIndicator;
    @BindView(R.id.viewPager)
    ScrollableViewPager viewPager;

    public static final String INTENT_KEY_DAILY = "dailyBean";
    public static final String INTENT_KEY_DATE = "date";
    private ViewPagerTabIndicator.PageFragmentAdapter mPageFragmentAdapter;

    private ClockDailyBean mDailyBean;
    private String mDate;

    public static void startClockInfoDetailActivity(Activity activity, ClockDailyBean dailyBean, String date) {
        Intent intent = new Intent(activity, ClockInfoDetailActivity.class);
        intent.putExtra(INTENT_KEY_DAILY, dailyBean);
        intent.putExtra(INTENT_KEY_DATE, date);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_info_detail);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.clock_info_detail));
        getIntentData();
        initViewPagerView();

    }

    public void getIntentData() {
        mDailyBean = (ClockDailyBean) getIntent().getSerializableExtra(INTENT_KEY_DAILY);
        mDate = getIntent().getStringExtra(INTENT_KEY_DATE);
    }

    private void initViewPagerView() {
        String[] tabTexts = new String[]{String.format("已打卡 (%s)", String.valueOf(mDailyBean.getSignedCount())),
                String.format("应到但未打卡 (%s)", String.valueOf(mDailyBean.getTotal() - mDailyBean.getSignedCount()))};
        mPageFragmentAdapter = new ViewPagerTabIndicator.PageFragmentAdapter(getSupportFragmentManager(), ClockInfoDetailActivity.this);
        mPageFragmentAdapter.addFragment(ClockFinishedFragment.getInstance(mDailyBean,mDate));
        mPageFragmentAdapter.addFragment(ClockLackFragment.getInstance(mDailyBean,mDate));
        viewPager.setAdapter(mPageFragmentAdapter);
        tableIndicator.setTabTexts(tabTexts);
        tableIndicator.setWithIndicator(true);
        tableIndicator.setIndicatorGravity(ViewPagerTabIndicator.INDICATOR_BOTTOM);
        tableIndicator.setViewPager(viewPager);
        tableIndicator.setWithDivider(false);
        tableIndicator.setup();
    }


}
