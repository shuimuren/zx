package com.zhixing.work.zhixin.view.card.back.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.OrdinaryListAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.MonthStatisticsBean;
import com.zhixing.work.zhixin.dialog.AttendanceRemindDialog;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.MonthStatementDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/8/27.
 * Description: B端-月报表
 */

public class MonthStatementFragment extends SupportFragment {

    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    Unbinder unbinder;

    public static MonthStatementFragment getInstance() {
        MonthStatementFragment fragment = new MonthStatementFragment();
        return fragment;
    }

    private OrdinaryListAdapter<MonthStatisticsBean> mMonthAdapter;
    private List<MonthStatisticsBean> monthStatisticsBeans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_month_statement, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void fetchData() {

    }

    private void initView() {
        monthStatisticsBeans = new ArrayList<>();
        mMonthAdapter = new OrdinaryListAdapter<MonthStatisticsBean>(getActivity(), monthStatisticsBeans);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerList.setAdapter(mMonthAdapter);
        //已审核:0,待审核:1,待完成:2,已完成:3;
        mMonthAdapter.setCallback(new OrdinaryListAdapter.Callback() {
            @Override
            public void onItemClicked(Object bean) {
                MonthStatisticsBean statisticsBean = (MonthStatisticsBean) bean;
                if (statisticsBean.getStatus() == 1) {
                    AttendanceRemindDialog dialog = new AttendanceRemindDialog(getActivity(), new AttendanceRemindDialog.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Dialog dialog) {
                            AlertUtils.show("提醒Hr");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else if(statisticsBean.getStatus() == 3){
                    MonthStatementDetailActivity.startMonthStatementActivity(getActivity(),String.valueOf(statisticsBean.getMonth()));
                }
            }
        });
        simulationData();
    }
    //已审核:0,待审核:1,待完成:2,已完成:3;

    //模式数据
    private void simulationData() {
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 7.5f, 1));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 6.5f, 2));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 8.7f, 3));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 9.25f, 4));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 10.0f, 5));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 7.0f, 6));
        monthStatisticsBeans.add(new MonthStatisticsBean(3, 8.2f, 7));
        monthStatisticsBeans.add(new MonthStatisticsBean(2, 0, 8));
        Collections.reverse(monthStatisticsBeans);
        mMonthAdapter.setData(monthStatisticsBeans);
        tvTime.setText("2018年");
        imgRight.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_left, R.id.img_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_left:

                break;
            case R.id.img_right:

                break;
        }
    }
}
