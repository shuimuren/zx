package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.OrdinaryListAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.HonourEventBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/8/27.
 * Description: 荣誉事件
 */

public class HonourEventFragment extends SupportFragment {

    @BindView(R.id.ll_empty_view)
    LinearLayout llEmptyView;
    @BindView(R.id.recycler_honour_list)
    RecyclerView recyclerHonourList;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    Unbinder unbinder;

    private List mHonours;
    private OrdinaryListAdapter<HonourEventBean> mHonourAdapter;

    public static HonourEventFragment getInstance() {
        HonourEventFragment eventFragment = new HonourEventFragment();
        return eventFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_honour_event, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHonours = new ArrayList();
        mHonourAdapter = new OrdinaryListAdapter<>(getActivity(),mHonours);
        recyclerHonourList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerHonourList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        mHonourAdapter.setCallback(new OrdinaryListAdapter.Callback() {
            @Override
            public void onItemClicked(Object bean) {

            }
        });
        recyclerHonourList.setAdapter(mHonourAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHonours.add(new HonourEventBean("科技进步奖","2018-01-15"));
        mHonourAdapter.setData(mHonours);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {

    }
}
