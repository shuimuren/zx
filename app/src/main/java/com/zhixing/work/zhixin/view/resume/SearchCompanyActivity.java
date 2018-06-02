package com.zhixing.work.zhixin.view.resume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;

public class SearchCompanyActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_company);
        setNoTitle();
    }
}
