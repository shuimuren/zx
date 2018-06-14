package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;


/**
 *
 */
public class CardMainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int OTHER = 2;
    public static final int FIND = 3;
    public static final int THIRD = 4;
    private SupportFragment[] mFragments = new SupportFragment[5];

    public TextView basics_bt;
    public TextView seniority;

    public TextView skill;
    public TextView fate;
    public TextView career;
    public static CardMainFragment instance;
    private int prePosition;

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
        SupportFragment firstFragment = findChildFragment(CardCounterFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = CardCounterFragment.newInstance();
            mFragments[SECOND] = EvaluatingFragment.newInstance();
            mFragments[OTHER] = SkillFragment.newInstance();
            mFragments[FIND] = KarmaFragment.newInstance();
            mFragments[THIRD] = CareerFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[OTHER],
                    mFragments[FIND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(EvaluatingFragment.class);
            mFragments[OTHER] = findChildFragment(SkillFragment.class);
            mFragments[FIND] = findChildFragment(KarmaFragment.class);
            mFragments[THIRD] = findChildFragment(CareerFragment.class);
        }
    }

    private void initView(View view) {

        basics_bt = (TextView) view.findViewById(R.id.basics_bt);
        seniority = (TextView) view.findViewById(R.id.seniority);
        skill = (TextView) view.findViewById(R.id.skill);
        fate = (TextView) view.findViewById(R.id.fate);
        career = (TextView) view.findViewById(R.id.career);
        basics_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basics_bt.setBackgroundResource(R.drawable.btn_pre);
                seniority.setBackgroundResource(R.drawable.btn_nor);
                skill.setBackgroundResource(R.drawable.btn_nor);
                fate.setBackgroundResource(R.drawable.btn_nor);
                career.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[FIRST], mFragments[prePosition]);
                prePosition = FIRST;


                basics_bt.setText("基础");
                seniority.setText("资");
                skill.setText("技");
                fate.setText("缘");
                career.setText("生");
            }
        });
        seniority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basics_bt.setBackgroundResource(R.drawable.btn_nor);
                seniority.setBackgroundResource(R.drawable.btn_pre);
                skill.setBackgroundResource(R.drawable.btn_nor);
                fate.setBackgroundResource(R.drawable.btn_nor);
                career.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[SECOND], mFragments[prePosition]);
                prePosition = SECOND;

                basics_bt.setText("基");
                seniority.setText("资质");
                skill.setText("技");
                fate.setText("缘");
                career.setText("生");

            }
        });
        skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basics_bt.setBackgroundResource(R.drawable.btn_nor);
                seniority.setBackgroundResource(R.drawable.btn_nor);
                skill.setBackgroundResource(R.drawable.btn_pre);
                fate.setBackgroundResource(R.drawable.btn_nor);
                career.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[OTHER], mFragments[prePosition]);
                prePosition = OTHER;

                basics_bt.setText("基");
                seniority.setText("资");
                skill.setText("技能");
                fate.setText("缘");
                career.setText("生");

            }
        });
        fate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basics_bt.setBackgroundResource(R.drawable.btn_nor);
                seniority.setBackgroundResource(R.drawable.btn_nor);
                skill.setBackgroundResource(R.drawable.btn_nor);
                fate.setBackgroundResource(R.drawable.btn_pre);
                career.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[FIND], mFragments[prePosition]);
                prePosition = FIND;



                basics_bt.setText("基");
                seniority.setText("资");
                skill.setText("技");
                fate.setText("缘分");
                career.setText("生");

            }
        });
        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basics_bt.setBackgroundResource(R.drawable.btn_nor);
                seniority.setBackgroundResource(R.drawable.btn_nor);
                skill.setBackgroundResource(R.drawable.btn_nor);
                fate.setBackgroundResource(R.drawable.btn_nor);
                career.setBackgroundResource(R.drawable.btn_pre);
                showHideFragment(mFragments[THIRD], mFragments[prePosition]);
                prePosition = THIRD;

                basics_bt.setText("基");
                seniority.setText("资");
                skill.setText("技");
                fate.setText("缘");
                career.setText("生");

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
