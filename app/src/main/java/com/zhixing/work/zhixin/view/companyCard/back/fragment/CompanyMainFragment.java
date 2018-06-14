package com.zhixing.work.zhixin.view.companyCard.back.fragment;

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
public class CompanyMainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;
    public static final int PROFILE = 0;
    public static final int BASIC = 1;
    public static final int PRODUCT = 2;
    public static final int EXECUTIVE = 3;
    public static final int EVENT = 4;
    private SupportFragment[] mFragments = new SupportFragment[5];

    public TextView profile;
    public TextView basic;

    public TextView product;
    public TextView executive;
    public TextView event;
    public static CompanyMainFragment instance;
    private int prePosition;

    public static CompanyMainFragment newInstance() {
        Bundle args = new Bundle();
        CompanyMainFragment fragment = new CompanyMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_card, container, false);
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
        SupportFragment firstFragment = findChildFragment(CompanyProfileFragment.class);
        if (firstFragment == null) {
            mFragments[PROFILE] = CompanyProfileFragment.newInstance();
            mFragments[BASIC] = CompanyBasicFragment.newInstance();
            mFragments[PRODUCT] = ProductIntroductionFragment.newInstance();
            mFragments[EXECUTIVE] = ExecutiveIntroductionFragment.newInstance();
            mFragments[EVENT] = BigEventFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, PROFILE,
                    mFragments[PROFILE],
                    mFragments[BASIC],
                    mFragments[PRODUCT],
                    mFragments[EXECUTIVE],
                    mFragments[EVENT]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[PROFILE] = firstFragment;
            mFragments[BASIC] = findChildFragment(CompanyBasicFragment.class);
            mFragments[PRODUCT] = findChildFragment(ProductIntroductionFragment.class);
            mFragments[EXECUTIVE] = findChildFragment(ExecutiveIntroductionFragment.class);
            mFragments[EVENT] = findChildFragment(BigEventFragment.class);
        }
    }

    private void initView(View view) {

        profile = (TextView) view.findViewById(R.id.profile);
        basic = (TextView) view.findViewById(R.id.basic);
        product = (TextView) view.findViewById(R.id.product);
        executive = (TextView) view.findViewById(R.id.executive);
        event = (TextView) view.findViewById(R.id.event);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.btn_pre);
                basic.setBackgroundResource(R.drawable.btn_nor);
                product.setBackgroundResource(R.drawable.btn_nor);
                executive.setBackgroundResource(R.drawable.btn_nor);
                event.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[PROFILE], mFragments[prePosition]);
                prePosition = PROFILE;
                profile.setText("公司简介");
                basic.setText("基");
                product.setText("产");
                executive.setText("高");
                event.setText("大");
            }
        });
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.btn_nor);
                basic.setBackgroundResource(R.drawable.btn_pre);
                product.setBackgroundResource(R.drawable.btn_nor);
                executive.setBackgroundResource(R.drawable.btn_nor);
                event.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[BASIC], mFragments[prePosition]);
                prePosition = BASIC;
                profile.setText("公");
                basic.setText("基础信息");
                product.setText("产");
                executive.setText("高");
                event.setText("大");

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.btn_nor);
                basic.setBackgroundResource(R.drawable.btn_nor);
                product.setBackgroundResource(R.drawable.btn_pre);
                executive.setBackgroundResource(R.drawable.btn_nor);
                event.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[PRODUCT], mFragments[prePosition]);
                prePosition = PRODUCT;

                profile.setText("公");
                basic.setText("基");
                product.setText("产品介绍");
                executive.setText("高");
                event.setText("大");

            }
        });
        executive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.btn_nor);
                basic.setBackgroundResource(R.drawable.btn_nor);
                product.setBackgroundResource(R.drawable.btn_nor);
                executive.setBackgroundResource(R.drawable.btn_pre);
                event.setBackgroundResource(R.drawable.btn_nor);
                showHideFragment(mFragments[EXECUTIVE], mFragments[prePosition]);
                prePosition = EXECUTIVE;
                profile.setText("公");
                basic.setText("基");
                product.setText("产");
                executive.setText("高管介绍");
                event.setText("大");

            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.btn_nor);
                basic.setBackgroundResource(R.drawable.btn_nor);
                product.setBackgroundResource(R.drawable.btn_nor);
                executive.setBackgroundResource(R.drawable.btn_nor);
                event.setBackgroundResource(R.drawable.btn_pre);
                showHideFragment(mFragments[EVENT], mFragments[prePosition]);
                prePosition = EVENT;
                profile.setText("公");
                basic.setText("基");
                product.setText("产");
                executive.setText("高");
                event.setText("大事件");

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
