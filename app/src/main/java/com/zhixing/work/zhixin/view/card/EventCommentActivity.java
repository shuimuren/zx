package com.zhixing.work.zhixin.view.card;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/27.
 * Description: 主动事件评价
 */

public class EventCommentActivity extends BaseTitleActivity {
    @BindView(R.id.edit_event_name)
    ClearEditText editEventName;
    @BindView(R.id.edit_event_detail)
    ClearEditText editEventDetail;
    @BindView(R.id.edit_leader_comment)
    ClearEditText editLeaderComment;
    @BindView(R.id.radio_one)
    RadioButton radioOne;
    @BindView(R.id.radio_two)
    RadioButton radioTwo;
    @BindView(R.id.radio_three)
    RadioButton radioThree;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.tv_summit)
    TextView tvSummit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_comment);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.event_comment_title));
    }

    @OnClick(R.id.tv_summit)
    public void onViewClicked() {

    }
}
