package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;

import butterknife.ButterKnife;

/**
 * 个人工作卡牌-职位信息
 */
public class PersonalJobPositionFragment extends SupportFragment {



    public static PersonalJobPositionFragment newInstance(String staffId) {
        Bundle args = new Bundle();
        args.putString(CardJobMainFragment.INTENT_KEY_STAFF_ID,staffId);
        PersonalJobPositionFragment fragment = new PersonalJobPositionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String mStaffId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_position_info, container, false);
        ButterKnife.bind(this, view);
        mStaffId = getArguments().getString(CardJobMainFragment.INTENT_KEY_STAFF_ID);
        return view;
    }






    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
