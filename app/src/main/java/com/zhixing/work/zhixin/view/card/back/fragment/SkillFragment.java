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
 *技能
 */
public class SkillFragment extends SupportFragment {


    public static SkillFragment newInstance() {
        Bundle args = new Bundle();
        SkillFragment fragment = new SkillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void fetchData() {

    }
}
