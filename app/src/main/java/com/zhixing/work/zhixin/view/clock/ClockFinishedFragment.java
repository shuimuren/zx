package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ClockFinishedFragment extends SupportFragment {

    public static ClockFinishedFragment  getInstance(){
        return new ClockFinishedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_finished,container,false);
        return view;
    }

    @Override
    public void fetchData() {

    }
}
