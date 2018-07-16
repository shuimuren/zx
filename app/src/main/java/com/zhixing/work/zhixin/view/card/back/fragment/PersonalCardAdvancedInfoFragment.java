package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;

/**
 * Created by lhj on 2018/7/14.
 * Description:高级信息
 */

public class PersonalCardAdvancedInfoFragment extends SupportFragment {

    public static PersonalCardAdvancedInfoFragment newInstance() {

        PersonalCardAdvancedInfoFragment fragment = new PersonalCardAdvancedInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_card_advanced_info,container,false);
        return view;
    }

    @Override
    public void fetchData() {
        
    }
}
