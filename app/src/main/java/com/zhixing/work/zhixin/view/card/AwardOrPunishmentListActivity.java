package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AwardOrPunishmentAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AwardOrPunishmentBean;
import com.zhixing.work.zhixin.bean.CareerAwardPunishment;
import com.zhixing.work.zhixin.common.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/24.
 * Description:荣誉或者惩罚列表
 */

public class AwardOrPunishmentListActivity extends BaseTitleActivity {


    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    public static final String INTENT_KEY_IS_AWARD = "isAward";

    private List<AwardOrPunishmentBean> mAwardList;
    private AwardOrPunishmentAdapter awardOrPunishmentAdapter;

    private boolean isAward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_or_punishment);
        ButterKnife.bind(this);
        isAward = getIntent().getBooleanExtra(INTENT_KEY_IS_AWARD, false);
        initView();

    }

    private void initView() {
        mAwardList = new ArrayList<>();
        awardOrPunishmentAdapter = new AwardOrPunishmentAdapter(this, mAwardList);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        recyclerList.setHasFixedSize(true);
        recyclerList.setAdapter(awardOrPunishmentAdapter);
        awardOrPunishmentAdapter.setCallback(new AwardOrPunishmentAdapter.Callback() {
            @Override
            public void onItemClicked(CareerAwardPunishment bean) {
                Intent intent = new Intent(AwardOrPunishmentListActivity.this, AwardOrPunishmentDetailActivity.class);
                intent.putExtra(AwardOrPunishmentDetailActivity.INTENT_KEY_IS_AWARD, isAward);
                startActivity(intent);

            }
        });
        if (isAward) {
            setTitle("荣誉展示");
            simulationAwardData();
        } else {
            setTitle("惩罚展示");
            simulationPunishmentData();
        }
    }

    private void simulationAwardData() {
        CareerAwardPunishment careerBean1 = new CareerAwardPunishment("8月优秀员工", "2018-05-20", 3);
        CareerAwardPunishment careerBean2 = new CareerAwardPunishment("8月优秀个人", "2017-12-20", 3);
        CareerAwardPunishment careerBean3 = new CareerAwardPunishment("2016优秀员工,拾金不昧,创新个人", "2018-05-20", 2);
        List<CareerAwardPunishment> list = new ArrayList<>();
        list.add(careerBean1);
        list.add(careerBean2);
        list.add(careerBean3);
        AwardOrPunishmentBean bean8 = new AwardOrPunishmentBean("2017-8", list);
        mAwardList.add(bean8);

        CareerAwardPunishment careerBean4 = new CareerAwardPunishment("7月优秀员工", "2018-05-20", 3);
        List<CareerAwardPunishment> list2 = new ArrayList<>();
        list2.add(careerBean4);

        AwardOrPunishmentBean bean7 = new AwardOrPunishmentBean("2017-7", list2);
        mAwardList.add(bean7);
        awardOrPunishmentAdapter.setData(mAwardList);
    }

    private void simulationPunishmentData() {
        CareerAwardPunishment careerBean1 = new CareerAwardPunishment("8月最差考核员工", "2018-08-1", -2);
        CareerAwardPunishment careerBean2 = new CareerAwardPunishment("8月工作未达标个人", "2018-08-30", -1);
        List<CareerAwardPunishment> list = new ArrayList<>();
        list.add(careerBean1);
        list.add(careerBean2);
        AwardOrPunishmentBean bean8 = new AwardOrPunishmentBean("2017-8", list);
        mAwardList.add(bean8);

        CareerAwardPunishment careerBean4 = new CareerAwardPunishment("7月份工作不饱和", "2018-07-30", -1);
        List<CareerAwardPunishment> list2 = new ArrayList<>();
        list2.add(careerBean4);

        AwardOrPunishmentBean bean7 = new AwardOrPunishmentBean("2017-7", list2);
        mAwardList.add(bean7);
        awardOrPunishmentAdapter.setData(mAwardList);
    }
}
