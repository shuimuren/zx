package com.zhixing.work.zhixin.view.card.back.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 职信卡牌fragment
 */
public class CardMainFragment extends SupportFragment {


    //  private static final int REQ_MSG = 10;

    public static final int FRAGMENT_APTITUDE = 0;//资质
    public static final int FRAGMENT_BASE = 1; //基础
    public static final int FRAGMENT_SKILL = 2;//技能
    public static final int FRAGMENT_LUCK = 3;//缘分
    public static final int FRAGMENT_CAREER = 4;//生涯

    Unbinder unbinder;
    @BindView(R.id.flCardTabContainer)
    FrameLayout flCardTabContainer;
    @BindView(R.id.tvCardBasic)
    TextView tvCardBasic;
    @BindView(R.id.tvCardAptitude)
    TextView tvCardAptitude;
    @BindView(R.id.tvCardSkill)
    TextView tvCardSkill;
    @BindView(R.id.tvCarLuck)
    TextView tvCarLuck;
    @BindView(R.id.tvCardCareer)
    TextView tvCardCareer;

    private SupportFragment[] mFragments = new SupportFragment[5];
    private int mCurrentSelectedPosition;

    public static CardMainFragment newInstance() {
        Bundle args = new Bundle();
        CardMainFragment fragment = new CardMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(EvaluatingFragment.class);
        tvCardAptitude.setSelected(true);
        mCurrentSelectedPosition = 0;
        if (firstFragment == null) {
            mFragments[FRAGMENT_APTITUDE] = EvaluatingFragment.newInstance();
            mFragments[FRAGMENT_BASE] = CardCounterFragment.newInstance();
            mFragments[FRAGMENT_SKILL] = SkillFragment.newInstance();
            mFragments[FRAGMENT_LUCK] = KarmaFragment.newInstance();
            mFragments[FRAGMENT_CAREER] = CareerFragment.newInstance();
            loadMultipleRootFragment(R.id.flCardTabContainer, FRAGMENT_APTITUDE,
                    mFragments[FRAGMENT_APTITUDE],
                    mFragments[FRAGMENT_BASE],
                    mFragments[FRAGMENT_SKILL],
                    mFragments[FRAGMENT_LUCK],
                    mFragments[FRAGMENT_CAREER]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FRAGMENT_BASE] = findChildFragment(CardCounterFragment.class);
            mFragments[FRAGMENT_APTITUDE] = firstFragment;
            mFragments[FRAGMENT_SKILL] = findChildFragment(SkillFragment.class);
            mFragments[FRAGMENT_LUCK] = findChildFragment(KarmaFragment.class);
            mFragments[FRAGMENT_CAREER] = findChildFragment(CareerFragment.class);
        }
    }

    @Override
    public void fetchData() {

    }

    /**
     * 点击各按钮
     *
     * @param view
     */
    @OnClick({R.id.tvCardBasic, R.id.tvCardAptitude, R.id.tvCardSkill, R.id.tvCarLuck, R.id.tvCardCareer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCardBasic:
                onFragmentChanged(FRAGMENT_BASE);
                break;
            case R.id.tvCardAptitude:
                onFragmentChanged(FRAGMENT_APTITUDE);
                break;
            case R.id.tvCardSkill:
                onFragmentChanged(FRAGMENT_SKILL);
                break;
            case R.id.tvCarLuck:
                onFragmentChanged(FRAGMENT_LUCK);
                break;
            case R.id.tvCardCareer:
                onFragmentChanged(FRAGMENT_CAREER);
                break;
        }
    }

    /**
     * 切换选中状态
     *
     * @param selectedPosition
     */
    private void onFragmentChanged(int selectedPosition) {
        tvCardBasic.setSelected(false);
        tvCardBasic.setText(ResourceUtils.getString(R.string.card_basic_default));
        tvCardAptitude.setSelected(false);
        tvCardAptitude.setText(ResourceUtils.getString(R.string.card_aptitude_default));
        tvCardSkill.setSelected(false);
        tvCardSkill.setText(ResourceUtils.getString(R.string.card_shill_default));
        tvCarLuck.setSelected(false);
        tvCarLuck.setText(ResourceUtils.getString(R.string.card_luck_default));
        tvCardCareer.setSelected(false);
        tvCardCareer.setText(ResourceUtils.getString(R.string.card_career_default));
        showHideFragment(mFragments[selectedPosition], mFragments[mCurrentSelectedPosition]);
        switch (selectedPosition) {
            case FRAGMENT_BASE:
                tvCardBasic.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_BASE;
                tvCardBasic.setText(ResourceUtils.getString(R.string.card_basic_selected));
                break;
            case FRAGMENT_APTITUDE:
                tvCardAptitude.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_APTITUDE;
                tvCardAptitude.setText(ResourceUtils.getString(R.string.card_aptitude_selected));
                break;
            case FRAGMENT_SKILL:
                tvCardSkill.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_SKILL;
                tvCardSkill.setText(ResourceUtils.getString(R.string.card_shill_selected));
                break;
            case FRAGMENT_LUCK:
                tvCarLuck.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_LUCK;
                tvCarLuck.setText(ResourceUtils.getString(R.string.card_luck_selected));
                break;
            case FRAGMENT_CAREER:
                tvCardCareer.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_CAREER;
                tvCardCareer.setText(ResourceUtils.getString(R.string.card_career_selected));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
