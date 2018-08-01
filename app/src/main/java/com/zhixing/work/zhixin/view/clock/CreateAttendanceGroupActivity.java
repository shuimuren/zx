package com.zhixing.work.zhixin.view.clock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/1.
 * Description: 新建考勤分组
 */

public class CreateAttendanceGroupActivity extends BaseTitleActivity {


    @BindView(R.id.name_edit_text)
    ClearEditText nameEditText;
    @BindView(R.id.ll_add_member)
    LinearLayout llAddMember;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendance_group);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.new_add_attendance_group));
    }


    @OnClick({R.id.ll_add_member, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_member:
                break;
            case R.id.btn_next:
                startActivity(new Intent(this, EditAttendanceGroupActivity.class));
                break;
        }
    }
}
