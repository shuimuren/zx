package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.ClockDailyBean;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ClockLackFragment extends SupportFragment {

    public static ClockLackFragment getInstance(ClockDailyBean dailyBean, String date) {
        Bundle args = new Bundle();
        args.putSerializable(ClockInfoDetailActivity.INTENT_KEY_DAILY, dailyBean);
        args.putString(ClockInfoDetailActivity.INTENT_KEY_DATE, date);
        ClockLackFragment fragment = new ClockLackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ClockDailyBean mDailyBean;
    private String mDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_lack, container, false);
        getIntentData();
        return view;
    }

    private void getIntentData() {
        mDailyBean = (ClockDailyBean) getArguments().getSerializable(ClockInfoDetailActivity.INTENT_KEY_DAILY);
        mDate = getArguments().getString(ClockInfoDetailActivity.INTENT_KEY_DATE);
    }

    @Override
    public void fetchData() {

    }
}
