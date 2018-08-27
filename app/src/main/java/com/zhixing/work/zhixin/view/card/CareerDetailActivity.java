package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.OrdinaryListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AbilityBean;
import com.zhixing.work.zhixin.bean.CareerAwardPunishment;
import com.zhixing.work.zhixin.bean.EvaluateBean;
import com.zhixing.work.zhixin.dialog.StaffInfoDialog;
import com.zhixing.work.zhixin.widget.EvaluatingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/23.
 * Description:生涯详情
 */

public class CareerDetailActivity extends BaseTitleActivity {

    @BindView(R.id.img_user_avatar)
    ImageView imgUserAvatar;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.tv_user_summary)
    TextView tvUserSummary;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.basics)
    TextView basics;
    @BindView(R.id.evaluateView)
    EvaluatingView evaluateView;
    @BindView(R.id.tvSincerity)
    TextView tvSincerity;
    @BindView(R.id.tvConnection)
    TextView tvConnection;
    @BindView(R.id.tvCompliance)
    TextView tvCompliance;
    @BindView(R.id.tvPerformance)
    TextView tvPerformance;
    @BindView(R.id.tvMentality)
    TextView tvMentality;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.rl_fraction)
    LinearLayout rlFraction;
    @BindView(R.id.tv_hornor_more)
    TextView tvHornorMore;
    @BindView(R.id.recycler_honor_view)
    RecyclerView recyclerAwardView;
    @BindView(R.id.ll_honor)
    LinearLayout llHonor;
    @BindView(R.id.tv_discipline_more)
    TextView tvDisciplineMore;
    @BindView(R.id.recycler_discipline_view)
    RecyclerView recyclerPunishmentView;
    @BindView(R.id.ll_discipline)
    LinearLayout llDiscipline;
    @BindView(R.id.tv_month_more)
    TextView tvMonthMore;


    private List<CareerAwardPunishment> mAwardList;
    private List<CareerAwardPunishment> mPunishmentList;
    private OrdinaryListAdapter<CareerAwardPunishment> mAwardAdapter;
    private OrdinaryListAdapter<CareerAwardPunishment> mPunishmentAdapter;
    private String mCompanyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_detail);
        ButterKnife.bind(this);
        mCompanyName = "科技公司";
        initView();
        simulationData();
    }

    private void initView() {
        setTitle(mCompanyName);

        mAwardList = new ArrayList<>();
        mPunishmentList = new ArrayList<>();
        mAwardAdapter = new OrdinaryListAdapter<>(this, mAwardList);
        recyclerAwardView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAwardView.setHasFixedSize(true);
        recyclerAwardView.setAdapter(mAwardAdapter);

        mPunishmentAdapter = new OrdinaryListAdapter<>(this, mPunishmentList);
        recyclerPunishmentView.setLayoutManager(new LinearLayoutManager(this));
        recyclerPunishmentView.setHasFixedSize(true);
        recyclerPunishmentView.setAdapter(mPunishmentAdapter);
    }

    private void simulationData() {
        /**
         * QuestionResultId : 1005
         * QuizTime : 50
         * StartTime : 2018-07-07 14:41:14
         * EndTime : 2018-07-07 14:41:39
         * MindsetScore : 40
         * CovenantScore : 30.0000019
         * ComplianceScore : 60.0000038
         * ConnectionScore : 60.0000038
         * IntegrityScore : 70
         * TotalScore : 52
         */
        EvaluateBean evaluateBean = new EvaluateBean(1000,50,"2018-07-07 14:41:14","2018-07-07 14:41:39",
                40f,88f,72f,60f,80f,55f);
        tvCompliance.setText(String.valueOf(evaluateBean.getComplianceScore()));
        tvConnection.setText(String.valueOf(evaluateBean.getConnectionScore()));
        tvMentality.setText(String.valueOf(evaluateBean.getMindsetScore()));
        tvPerformance.setText(String.valueOf(evaluateBean.getCovenantScore()));
        tvSincerity.setText(String.valueOf(evaluateBean.getIntegrityScore()));
        stars.setRating(evaluateBean.getTotalScore() / 10f);
        //int psychology, int integrity, int agreement, int compliance, int connection
        AbilityBean bean = new AbilityBean((int)evaluateBean.getIntegrityScore(),(int)evaluateBean.getComplianceScore(),
                (int)evaluateBean.getConnectionScore(),(int) evaluateBean.getCovenantScore(),(int)evaluateBean.getMindsetScore());

        evaluateView.setData(bean);
        
        mAwardList.add(new CareerAwardPunishment("2017优秀员工", "2018-05-20", 3));
        mAwardList.add(new CareerAwardPunishment("2017突出贡献奖", "2017-12-20", 3));
        mAwardList.add(new CareerAwardPunishment("2016优秀员工,拾金不昧,创新个人", "2018-05-20", 2));
        mAwardAdapter.setData(mAwardList);

        mPunishmentList.add(new CareerAwardPunishment("2018工作滞后", "2018-02-03", -1));
        mPunishmentAdapter.setData(mPunishmentList);

    }


    @OnClick({R.id.tv_open, R.id.btn_edit, R.id.tv_hornor_more, R.id.tv_discipline_more, R.id.tv_month_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open:
                StaffInfoDialog dialog = new StaffInfoDialog(this);
                dialog.show();
                break;
            case R.id.btn_edit:
                break;
            case R.id.tv_hornor_more:
                Intent hornorIntent = new Intent(this,AwardOrPunishmentListActivity.class);
                hornorIntent.putExtra(AwardOrPunishmentListActivity.INTENT_KEY_IS_AWARD,true);
                startActivity(hornorIntent);
                break;
            case R.id.tv_discipline_more:
                Intent disciplineIntent = new Intent(this,AwardOrPunishmentListActivity.class);
                disciplineIntent.putExtra(AwardOrPunishmentListActivity.INTENT_KEY_IS_AWARD,false);
                startActivity(disciplineIntent);
                break;
            case R.id.tv_month_more:
                startActivity(new Intent(this,StaffMonthCardActivity.class));
                break;
        }
    }
}
