package com.zhixing.work.zhixin.view.card;

import android.os.Bundle;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.widget.AwardOrPunishmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhj on 2018/8/24.
 * Description: 奖惩详情
 */

public class AwardOrPunishmentDetailActivity extends BaseTitleActivity {

    @BindView(R.id.tv_award)
    TextView tvAward;
    @BindView(R.id.tv_award_time)
    TextView tvAwardTime;
    @BindView(R.id.tv_award_scale)
    TextView tvAwardScale;
    @BindView(R.id.reward_view)
    AwardOrPunishmentView rewardView;
    @BindView(R.id.tv_reward_event_des)
    TextView tvRewardEventDes;

    public static final String INTENT_KEY_IS_AWARD = "isAward";
    private boolean isAward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_or_punishment_detail);
        ButterKnife.bind(this);
        isAward = getIntent().getBooleanExtra(INTENT_KEY_IS_AWARD, false);
        initView();
    }

    private void initView() {
        if(isAward){
            rewardView.setViewStatus(2);
            setTitle("奖励详情");
        }else {
            setTitle("惩罚详情");
            tvAward.setText("违规事件");
            tvAwardScale.setText("获得惩罚");
            tvRewardEventDes.setText("屡次上班迟到,工作不按时完成");
            rewardView.setViewStatus(-1);
        }
    }
}
