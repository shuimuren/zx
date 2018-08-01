package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ScrollableViewPager;
import com.zhixing.work.zhixin.widget.ViewPagerTabIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ClockInfoDetailActivity extends SupportActivity {

    @BindView(R.id.tab_indicator)
    ViewPagerTabIndicator tableIndicator;
    @BindView(R.id.viewPager)
    ScrollableViewPager viewPager;

    private ViewPagerTabIndicator.PageFragmentAdapter mPageFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_info_detail);
        ButterKnife.bind(this);
        initViewPagerView();

    }

    private void initViewPagerView() {
        String[] tabTexts = new String[]{ResourceUtils.getString(R.string.clock_finished),
                ResourceUtils.getString(R.string.clock_lack)};
        mPageFragmentAdapter = new ViewPagerTabIndicator.PageFragmentAdapter(getSupportFragmentManager(), ClockInfoDetailActivity.this);
        mPageFragmentAdapter.addFragment(ClockFinishedFragment.getInstance());
        mPageFragmentAdapter.addFragment(ClockLackFragment.getInstance());
        viewPager.setAdapter(mPageFragmentAdapter);
        tableIndicator.setTabTexts(tabTexts);
        tableIndicator.setWithIndicator(true);
        tableIndicator.setIndicatorGravity(ViewPagerTabIndicator.INDICATOR_BOTTOM);
        tableIndicator.setViewPager(viewPager);
        tableIndicator.setWithDivider(false);
        tableIndicator.setup();

    }
}
