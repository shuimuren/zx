package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;


import butterknife.ButterKnife;


/**
 *主页-应用
 */
public class ApplicationFragment extends BaseMainFragment {



    public static ApplicationFragment newInstance() {
        Bundle args = new Bundle();
        ApplicationFragment fragment = new ApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appicon, container, false);

        ButterKnife.bind(this, view);


        return view;


    }


    @Override
    public void fetchData() {

    }
}
