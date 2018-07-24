package com.zhixing.work.zhixin.view.companyCard.back;

import android.os.Bundle;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.companyCard.back.fragment.CompanyMainFragment;

public class CompanyCardActivity extends SupportActivity {
    public static CompanyCardActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_card);
        setTitle(ResourceUtils.getString(R.string.zhi_xin_card));
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, CompanyMainFragment.newInstance());
        }
        instance = this;

    }

}
