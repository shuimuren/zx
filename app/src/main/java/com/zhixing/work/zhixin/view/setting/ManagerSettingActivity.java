package com.zhixing.work.zhixin.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/22.
 * Description: B端管理员设置
 */

public class ManagerSettingActivity extends BaseTitleActivity {

    @BindView(R.id.ll_manager_setting)
    LinearLayout llManagerSetting;
    @BindView(R.id.ll_hr_setting)
    LinearLayout llHrSetting;

    public static final String STAFF_ROLE = "StaffRole";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_setting);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.manager_setting_title));
    }

    @OnClick({R.id.ll_manager_setting, R.id.ll_hr_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_manager_setting:
                Intent managerIntent = new Intent(this,ManagerOrHrListActivity.class);
                managerIntent.putExtra(STAFF_ROLE, ResultConstant.USER_STAFF_ROLE_MANAGER);
                startActivity(managerIntent);
                break;
            case R.id.ll_hr_setting:
                Intent HrIntent = new Intent(this,ManagerOrHrListActivity.class);
                HrIntent.putExtra(STAFF_ROLE,ResultConstant.USER_STAFF_ROLE_HR);
                startActivity(HrIntent);
                break;
        }
    }
}
