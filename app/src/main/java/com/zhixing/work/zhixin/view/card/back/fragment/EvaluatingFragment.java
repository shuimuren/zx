package com.zhixing.work.zhixin.view.card.back.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.view.card.back.fragment.CardCounterFragment;
import com.zhixing.work.zhixin.view.score.EvaluationHallActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资质
 */
public class EvaluatingFragment extends SupportFragment {

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

    private Context context;


    public static EvaluatingFragment newInstance() {
        Bundle args = new Bundle();
        EvaluatingFragment fragment = new EvaluatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_score, container, false);

        ButterKnife.bind(this, view);
        basics.setSelected(true);
        context = getActivity();


        return view;
    }

    @OnClick({R.id.evaluating, R.id.historical_score, R.id.basics_bt, R.id.seniority, R.id.skill, R.id.fate, R.id.career})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //参加测评
            case R.id.evaluating:
                startActivity(new Intent(context, EvaluationHallActivity.class));
                break;
            //历史评分
            case R.id.historical_score:
                break;
            case R.id.basics_bt:

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

    @Override
    public void fetchData() {

    }
}
