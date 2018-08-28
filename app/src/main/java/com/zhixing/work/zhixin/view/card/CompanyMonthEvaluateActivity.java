package com.zhixing.work.zhixin.view.card;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.back.fragment.MonthStatementFragment;
import com.zhixing.work.zhixin.view.card.back.fragment.RankingStatementFragment;
import com.zhixing.work.zhixin.view.card.back.fragment.UserStatementFragment;
import com.zhixing.work.zhixin.widget.ViewPagerTabIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/27.
 * Description: B端职信月评
 */

public class CompanyMonthEvaluateActivity extends SupportActivity {

    @BindView(R.id.table_indicator)
    ViewPagerTabIndicator tableIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerTabIndicator.PageFragmentAdapter  mPageFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_month_evaluate);
        ButterKnife.bind(this);
        initViewPagerView();
    }

    private void initViewPagerView() {
        String[] tabTexts = new String[]{ResourceUtils.getString(R.string.month_statement),
                ResourceUtils.getString(R.string.user_statement),ResourceUtils.getString(R.string.ranking_statement)};
        mPageFragmentAdapter = new ViewPagerTabIndicator.PageFragmentAdapter(getSupportFragmentManager(),this);
        mPageFragmentAdapter.addFragment(MonthStatementFragment.getInstance());
        mPageFragmentAdapter.addFragment(UserStatementFragment.getInstance());
        mPageFragmentAdapter.addFragment(RankingStatementFragment.getInstance());
        viewPager.setAdapter(mPageFragmentAdapter);
        tableIndicator.setTabTexts(tabTexts);
        tableIndicator.setWithIndicator(true);
        tableIndicator.setIndicatorGravity(ViewPagerTabIndicator.INDICATOR_BOTTOM);
        tableIndicator.setViewPager(viewPager);
        tableIndicator.setWithDivider(false);
        tableIndicator.setup();

    }
}
