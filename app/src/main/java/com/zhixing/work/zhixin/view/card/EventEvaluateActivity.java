package com.zhixing.work.zhixin.view.card;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.back.fragment.HonourEventFragment;
import com.zhixing.work.zhixin.view.card.back.fragment.PunishmentEventFragment;
import com.zhixing.work.zhixin.widget.ViewPagerTabIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/27.
 * Description: 主动事件
 */

public class EventEvaluateActivity extends SupportActivity {

    @BindView(R.id.table_indicator)
    ViewPagerTabIndicator tableIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerTabIndicator.PageFragmentAdapter  mPageFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_evluate);
        ButterKnife.bind(this);
        initViewPagerView();
    }

    private void initViewPagerView() {
        String[] tabTexts = new String[]{ResourceUtils.getString(R.string.event_honour),
                ResourceUtils.getString(R.string.event_punishment)};
        Drawable[] icons = new Drawable[]{ResourceUtils.getDrawable(R.drawable.selector_honour),
                ResourceUtils.getDrawable(R.drawable.selector_punishment)};
        mPageFragmentAdapter = new ViewPagerTabIndicator.PageFragmentAdapter(getSupportFragmentManager(),this);
        mPageFragmentAdapter.addFragment(HonourEventFragment.getInstance());
        mPageFragmentAdapter.addFragment(PunishmentEventFragment.getInstance());
        viewPager.setAdapter(mPageFragmentAdapter);
        tableIndicator.setTabTexts(tabTexts);
        tableIndicator.setWithIndicator(true);
        tableIndicator.setIndicatorGravity(ViewPagerTabIndicator.INDICATOR_BOTTOM);
        tableIndicator.setViewPager(viewPager);
        tableIndicator.setWithDivider(false);
        tableIndicator.setTabIcons(icons);
        tableIndicator.setDrawbleDirection(ViewPagerTabIndicator.DRAWABLE_LEFT);
        tableIndicator.setup();

    }
}
