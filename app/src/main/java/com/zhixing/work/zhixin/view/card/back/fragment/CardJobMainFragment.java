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
public class CardJobMainFragment extends SupportFragment {


    //  private static final int REQ_MSG = 10;
    Unbinder unbinder;
    public static final int FRAGMENT_POSITION = 0;//职位
    public static final int FRAGMENT_JOB = 1; //工作
    public static final int FRAGMENT_USER_INFO = 2;//个人信息

    @BindView(R.id.flCardTabContainer)
    FrameLayout flCardTabContainer;
    @BindView(R.id.tv_card_position)
    TextView tvCardPosition;
    @BindView(R.id.tv_card_job)
    TextView tvCardJob;
    @BindView(R.id.tv_card_user_info)
    TextView tvCardUserInfo;

    public static final String INTENT_KEY_STAFF_ID = "staffId";
    private SupportFragment[] mFragments = new SupportFragment[3];
    private int mCurrentSelectedPosition;
    private String mStaffId;

    public static CardJobMainFragment newInstance(String staffId) {
        CardJobMainFragment fragment = new CardJobMainFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_KEY_STAFF_ID, staffId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_job_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mStaffId = getArguments().getString(INTENT_KEY_STAFF_ID);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(PersonalJobInfoFragment.class);
        tvCardJob.setSelected(true);
        mCurrentSelectedPosition = 0;
        if (firstFragment == null) {
            mFragments[FRAGMENT_POSITION] = PersonalJobPositionFragment.newInstance(mStaffId);
            mFragments[FRAGMENT_JOB] = PersonalJobInfoFragment.newInstance(mStaffId);
            mFragments[FRAGMENT_USER_INFO] = PersonalJobUserInfoFragment.newInstance(mStaffId);


            loadMultipleRootFragment(R.id.flCardTabContainer, FRAGMENT_JOB,
                    mFragments[FRAGMENT_POSITION],
                    mFragments[FRAGMENT_JOB],
                    mFragments[FRAGMENT_USER_INFO]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FRAGMENT_POSITION] = findChildFragment(PersonalJobPositionFragment.class);
            mFragments[FRAGMENT_JOB] = firstFragment;
            mFragments[FRAGMENT_USER_INFO] = findChildFragment(PersonalJobUserInfoFragment.class);
        }
    }

    @Override
    public void fetchData() {

    }

    /**
     * 点击各按钮
     *
     * @param view //
     */
    @OnClick({R.id.tv_card_position, R.id.tv_card_job, R.id.tv_card_user_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_card_position:
                onFragmentChanged(FRAGMENT_POSITION);
                break;
            case R.id.tv_card_job:
                onFragmentChanged(FRAGMENT_JOB);
                break;
            case R.id.tv_card_user_info:
                onFragmentChanged(FRAGMENT_USER_INFO);
                break;
        }
    }

    /**
     * 切换选中状态
     *
     * @param selectedPosition
     */
    private void onFragmentChanged(int selectedPosition) {
        tvCardPosition.setSelected(false);
        tvCardPosition.setText(ResourceUtils.getString(R.string.personal_job_card_position_normal));
        tvCardJob.setSelected(false);
        tvCardJob.setText(ResourceUtils.getString(R.string.personal_job_card_job_normal));
        tvCardUserInfo.setSelected(false);
        tvCardUserInfo.setText(ResourceUtils.getString(R.string.personal_job_card_user_normal));
        showHideFragment(mFragments[selectedPosition], mFragments[mCurrentSelectedPosition]);
        switch (selectedPosition) {
            case FRAGMENT_POSITION:
                tvCardPosition.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_POSITION;
                tvCardPosition.setText(ResourceUtils.getString(R.string.personal_job_card_position_selected));
                break;
            case FRAGMENT_JOB:
                tvCardJob.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_JOB;
                tvCardJob.setText(ResourceUtils.getString(R.string.personal_job_card_job_selected));
                break;
            case FRAGMENT_USER_INFO:
                tvCardUserInfo.setSelected(true);
                mCurrentSelectedPosition = FRAGMENT_USER_INFO;
                tvCardUserInfo.setText(ResourceUtils.getString(R.string.personal_job_card_user_selected));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
