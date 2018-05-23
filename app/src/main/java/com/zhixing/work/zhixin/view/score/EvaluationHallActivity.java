package com.zhixing.work.zhixin.view.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationHallActivity extends BaseTitleActivity {

    @BindView(R.id.start)
    TextView start;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_hall);
        ButterKnife.bind(this);
        context=this;
        setTitle("评测大厅");

    }

    @OnClick(R.id.start)
    public void onViewClicked() {
        startActivity(new Intent(context, InitialEvaluationActivity.class));
    }
}
