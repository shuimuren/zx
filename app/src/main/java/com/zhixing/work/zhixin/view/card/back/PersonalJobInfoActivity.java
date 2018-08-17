package com.zhixing.work.zhixin.view.card.back;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.back.fragment.CardJobMainFragment;

/**
 * Created by lhj on 2018/8/13.
 * Description: 个人工作卡牌信息
 */

public class PersonalJobInfoActivity extends SupportActivity {

    public static void startPersonalJobInfoActivity(Activity activity, String staffId) {
        Intent intent = new Intent(activity, PersonalJobInfoActivity.class);
        intent.putExtra(CardJobMainFragment.INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_job_info);
        setTitle(ResourceUtils.getString(R.string.personal_info_title));
        String staffId = getIntent().getStringExtra(CardJobMainFragment.INTENT_KEY_STAFF_ID);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, CardJobMainFragment.newInstance(staffId));
        }
    }
}