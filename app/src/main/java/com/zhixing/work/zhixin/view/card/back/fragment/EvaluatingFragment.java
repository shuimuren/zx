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
import com.zhixing.work.zhixin.event.EvaluationEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.response.EvaluateResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.score.EvaluationHallActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

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


    private Context context;
    private Subscription evaluateInfoSubscription;
    private boolean hasTested;

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
        evaluateInfoSubscription = RxBus.getInstance().toObservable(EvaluateResult.class).subscribe(
                result -> handlerEvaluateInfo(result)
        );
        EventBus.getDefault().register(this);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_EVALUATE_INFO);
        return view;
    }

    private void handlerEvaluateInfo(EvaluateResult result) {
        if (result.getContent() != null) {
            hasTested = true;
            tvCompliance.setText(String.valueOf(result.getContent().getComplianceScore()));
            tvConnection.setText(String.valueOf(result.getContent().getConnectionScore()));
            tvMentality.setText(String.valueOf(result.getContent().getMindsetScore()));
            tvPerformance.setText(String.valueOf(result.getContent().getCovenantScore()));
            tvSincerity.setText(String.valueOf(result.getContent().getIntegrityScore()));
            stars.setRating(result.getContent().getTotalScore() / 10f);
        }

    }

    @OnClick({R.id.evaluating, R.id.historical_score, R.id.basics_bt, R.id.seniority, R.id.skill, R.id.fate, R.id.career})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //参加测评
            case R.id.evaluating:
                if(!hasTested){
                    startActivity(new Intent(context, EvaluationHallActivity.class));
                }else {
                    AlertUtils.show(ResourceUtils.getString(R.string.alter_user_had_test_before));
                }

                break;
            //历史评分
            case R.id.historical_score:
                AlertUtils.show("历史评分:" + ResourceUtils.getString(R.string.need_to_do));
                break;
//            case R.id.basics_bt:
//                break;
//            case R.id.seniority:
//                break;
//            case R.id.skill:
//                break;
//            case R.id.fate:
//                break;
//            case R.id.career:
//                break;
        }
    }

    @Override
    public void fetchData() {

    }

    @Subscribe
    public void onEvaluationEvent(EvaluationEvent evaluationEvent){
        if(evaluationEvent.isRefresh()){
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_EVALUATE_INFO);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unsubscribe(evaluateInfoSubscription);
    }
}
