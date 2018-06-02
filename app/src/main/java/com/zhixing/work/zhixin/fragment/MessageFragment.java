package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 */
public class MessageFragment extends BaseMainFragment {



    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        ButterKnife.bind(this, view);


        return view;


    }


    @Override
    public void fetchData() {

    }
}
