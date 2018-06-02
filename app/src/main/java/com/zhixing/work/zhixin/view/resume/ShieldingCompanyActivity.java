package com.zhixing.work.zhixin.view.resume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//屏蔽公司
public class ShieldingCompanyActivity extends BaseTitleActivity {

    @BindView(R.id.ll_prompt)
    LinearLayout llPrompt;
    @BindView(R.id.add_company)
    LinearLayout addCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shielding_company);
        ButterKnife.bind(this);
        setTitle("屏蔽公司");
        setRightText1("帮助");
        initView();
    }

    private void initView() {

        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ShieldedHelpActivity.class));
            }
        });
    }

    @OnClick(R.id.add_company)
    public void onViewClicked() {
        startActivity(new Intent(context,SearchCompanyActivity.class));
    }
}
