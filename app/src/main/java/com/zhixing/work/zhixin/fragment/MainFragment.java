package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.event.TabSelectedEvent;
import com.zhixing.work.zhixin.widget.BottomBar;
import com.zhixing.work.zhixin.widget.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

/**
 * 主页
 */
public class MainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;
    public static final int MAIN_FRAGMENT_CODE = 0; //正聊
    public static final int SCORE_FRAGMENT_CODE = 1; //评分
    public static final int APPLICATION_FRAGMENT_CODE = 2;//应用
    public static final int COMMUNITY_FRAGMENT_CODE = 3; //社区
    public static final int MY_CENTER_FRAGMENT_CODE = 4; //我的
    private SupportFragment[] mFragments = new SupportFragment[5];
    public BottomBar mBottomBar;
    public ImageView car;
    public static MainFragment instance;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        instance = this;
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(MessageFragment.class);
        if (firstFragment == null) {
            mFragments[MAIN_FRAGMENT_CODE] = MessageFragment.newInstance();
            mFragments[SCORE_FRAGMENT_CODE] = ScoreFragment.newInstance();
            mFragments[APPLICATION_FRAGMENT_CODE] = AppiconFragment.newInstance();
            mFragments[COMMUNITY_FRAGMENT_CODE] = CommunityFragment.newInstance();
            mFragments[MY_CENTER_FRAGMENT_CODE] = MyCenterFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container,MAIN_FRAGMENT_CODE,
                    mFragments[MAIN_FRAGMENT_CODE],
                    mFragments[SCORE_FRAGMENT_CODE],
                    mFragments[APPLICATION_FRAGMENT_CODE],
                    mFragments[COMMUNITY_FRAGMENT_CODE],
                    mFragments[MY_CENTER_FRAGMENT_CODE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[MAIN_FRAGMENT_CODE] = firstFragment;
            mFragments[SCORE_FRAGMENT_CODE] = findChildFragment(ScoreFragment.class);
            mFragments[APPLICATION_FRAGMENT_CODE] = findChildFragment(AppiconFragment.class);
            mFragments[COMMUNITY_FRAGMENT_CODE] = findChildFragment(CommunityFragment.class);
            mFragments[MY_CENTER_FRAGMENT_CODE] = findChildFragment(MyCenterFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.message_bg, getString(R.string.message)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.score_bg, getString(R.string.score)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.appicon_bg, getString(R.string.application)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.career_bg, getString(R.string.career)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.mycenter_bg, getString(R.string.my_center)));
        mBottomBar.setCurrentItem(SCORE_FRAGMENT_CODE);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                BottomBarTab tab = mBottomBar.getItem(SCORE_FRAGMENT_CODE);
//                if (position == THIRD) {
//                    MainActivity.instance.setStatusBarColor(R.color.color_ff6671);
//                    car.setVisibility(View.GONE);
//                    // tab.setUnreadCount(0);
//                } else if (position == FIND) {
//                    MainActivity.instance.setStatusBarColor(R.color.main_color);
//                    car.setVisibility(View.VISIBLE);
//                } else {
//                    car.setVisibility(View.GONE);
//                    MainActivity.instance.setStatusBarColor(R.color.main_color);
//                    // tab.setUnreadCount(tab.getUnreadCount() + 1);
//                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }


}
