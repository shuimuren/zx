package com.zhixing.work.zhixin.view.card;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.OrdinaryListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.MonthStatisticsBean;
import com.zhixing.work.zhixin.dialog.AttendanceRemindDialog;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/24.
 * Description: 员工月度卡牌
 */

public class StaffMonthCardActivity extends BaseTitleActivity {

    @BindView(R.id.tv_get_score)
    TextView tvGetScore;
    @BindView(R.id.tv_score_total)
    TextView tvScoreTotal;
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.recycler_month)
    RecyclerView recyclerMonth;

    private OrdinaryListAdapter<MonthStatisticsBean> mMonthAdapter;
    private List<MonthStatisticsBean> monthStatisticsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_month_card);
        ButterKnife.bind(this);
        setTitle("职信科技公司");
        initView();
    }

    private void initView() {
        monthStatisticsBeans = new ArrayList<>();
        mMonthAdapter = new OrdinaryListAdapter<>(this, monthStatisticsBeans);
        recyclerMonth.setHasFixedSize(true);
        recyclerMonth.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerMonth.setAdapter(mMonthAdapter);
        mMonthAdapter.setCallback(new OrdinaryListAdapter.Callback() {
            @Override
            public void onItemClicked(Object bean) {
                AttendanceRemindDialog dialog = new AttendanceRemindDialog(StaffMonthCardActivity.this, new AttendanceRemindDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Dialog dialog) {
                        AlertUtils.show("提醒Hr");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        simulationData();
    }

    //模式数据
    private void simulationData() {
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 7.5f, 1));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 6.5f, 2));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 8.7f, 3));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 9.25f, 4));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 10.0f, 5));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 7.0f, 6));
        monthStatisticsBeans.add(new MonthStatisticsBean(0, 8.2f, 7));
        monthStatisticsBeans.add(new MonthStatisticsBean(1, 0, 8));
        Collections.reverse(monthStatisticsBeans);
        mMonthAdapter.setData(monthStatisticsBeans);
        tvGetScore.setText("85");
        tvScoreTotal.setText("/120");
        tvTime.setText("2018年");
        imgRight.setEnabled(false);
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
