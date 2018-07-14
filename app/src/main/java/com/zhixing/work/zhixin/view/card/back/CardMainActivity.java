package com.zhixing.work.zhixin.view.card.back;

import android.os.Bundle;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.back.fragment.CardMainFragment;

public class CardMainActivity extends SupportActivity {

    public static CardMainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_main);
        setTitle(ResourceUtils.getString(R.string.zhi_xin_card));
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, CardMainFragment.newInstance());
        }
        instance = this;

    }
}
