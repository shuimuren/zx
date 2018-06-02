package com.zhixing.work.zhixin.view.card.back;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.MainActivity;
import com.zhixing.work.zhixin.view.card.back.fragment.CardMainFragment;

public class CardMainActivity extends SupportActivity {
    public static CardMainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_main);
        setNoTitle();
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, CardMainFragment.newInstance());
        }
        instance = this;

    }
}
