package com.zhixing.work.zhixin.view.card.back.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.OrdinaryListAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.StaffCareerBean;
import com.zhixing.work.zhixin.view.card.CareerDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 生涯
 */
public class CareerFragment extends SupportFragment {


    @BindView(R.id.career_recycler_view)
    RecyclerView careerRecyclerView;
    
    private List<StaffCareerBean> mCareerList;
    private OrdinaryListAdapter<StaffCareerBean> mAdapter;

    public static CareerFragment newInstance() {
        Bundle args = new Bundle();
        CareerFragment fragment = new CareerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_career, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mCareerList = new ArrayList<>();
        mAdapter = new OrdinaryListAdapter<>(getActivity(),mCareerList);
        careerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        careerRecyclerView.setHasFixedSize(true);
        careerRecyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(new OrdinaryListAdapter.Callback() {
            @Override
            public void onItemClicked(Object bean) {
                startActivity(new Intent(getActivity(), CareerDetailActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCareerList.add(new StaffCareerBean(7.5f,"职信（深圳）科技公司","123"));
        mCareerList.add(new StaffCareerBean(8.5f,"未来科技有限公司","123"));
        mCareerList.add(new StaffCareerBean(9.0f,"掌图科技股份有限公司","123"));
        mAdapter.setData(mCareerList);
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       
    }

}
