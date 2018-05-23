package com.zhixing.work.zhixin.view.score;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.view.card.CardCounterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreActivity extends BaseTitleActivity {

    @BindView(R.id.basics)
    TextView basics;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.evaluating)
    Button evaluating;
    @BindView(R.id.historical_score)
    TextView historicalScore;
    @BindView(R.id.rl_fraction)
    LinearLayout rlFraction;
    @BindView(R.id.basics_bt)
    TextView basicsBt;
    @BindView(R.id.seniority)
    TextView seniority;
    @BindView(R.id.skill)
    TextView skill;
    @BindView(R.id.fate)
    TextView fate;
    @BindView(R.id.career)
    TextView career;
    @BindView(R.id.skill_nm)
    TextView skillNm;
    @BindView(R.id.sincerity_nm)
    TextView sincerityNm;
    @BindView(R.id.connection_nm)
    TextView connectionNm;
    @BindView(R.id.compliance_nm)
    TextView complianceNm;
    @BindView(R.id.performance_nm)
    TextView performanceNm;
    @BindView(R.id.mentality_nm)
    TextView mentalityNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        setTitle("评分");
    }


    @OnClick({R.id.evaluating, R.id.historical_score, R.id.basics_bt, R.id.seniority, R.id.skill, R.id.fate, R.id.career})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.evaluating:
                startActivity(new Intent(context, EvaluationHallActivity.class));
                break;
            case R.id.historical_score:
                break;
            case R.id.basics_bt:
                startActivity(new Intent(context, CardCounterActivity.class));
                break;
            case R.id.seniority:
                break;
            case R.id.skill:
                break;
            case R.id.fate:
                break;
            case R.id.career:
                break;
        }
    }
}
