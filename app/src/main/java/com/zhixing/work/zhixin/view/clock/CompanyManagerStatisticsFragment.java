package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ViewPagerTabIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/7/31.
 * Description:考勤-统计-B端查看
 */

public class CompanyManagerStatisticsFragment extends SupportFragment {

    @BindView(R.id.table_indicator)
    ViewPagerTabIndicator tableIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    private ViewPagerTabIndicator.PageFragmentAdapter  mPageFragmentAdapter;
    public static CompanyManagerStatisticsFragment getInstance() {
        CompanyManagerStatisticsFragment managerStatisticsFragment = new CompanyManagerStatisticsFragment();
        return managerStatisticsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_statistic, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewPagerView();
        return view;
    }

    private void initViewPagerView() {
        String[] tabTexts = new String[]{ResourceUtils.getString(R.string.statistics_by_day),
                ResourceUtils.getString(R.string.statistics_by_month)};
        mPageFragmentAdapter = new ViewPagerTabIndicator.PageFragmentAdapter(getChildFragmentManager(),getActivity());
        mPageFragmentAdapter.addFragment(ClockStatisticsDayFragment.getInstance());
        mPageFragmentAdapter.addFragment(ClockStatisticsMonthFragment.getInstance());
        viewPager.setAdapter(mPageFragmentAdapter);
        tableIndicator.setTabTexts(tabTexts);
        tableIndicator.setWithIndicator(true);
        tableIndicator.setIndicatorGravity(ViewPagerTabIndicator.INDICATOR_BOTTOM);
        tableIndicator.setViewPager(viewPager);
        tableIndicator.setWithDivider(false);
        tableIndicator.setup();

    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
