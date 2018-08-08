package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.event.CreateAttendanceGroupSuccessEvent;
import com.zhixing.work.zhixin.event.SelectedStaffsEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_member)
    TextView tvMember;

    private int selectedTotal;
    private String name;
    private List<Integer> selectedStaffs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendance_group);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.new_add_attendance_group));
        initView();
    }

    private void initView() {
        selectedStaffs = new ArrayList<>();
    }


    @OnClick({R.id.ll_add_member, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_member:
                SelectAttendanceMemberActivity.startSelectAttendanceActivity(this,null,selectedStaffs);
                break;
            case R.id.btn_next:
                name = nameEditText.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    AlertUtils.show(ResourceUtils.getString(R.string.input_attendance_group_name));
                    return;
                }

                if (selectedTotal == 0) {
                    AlertUtils.show(ResourceUtils.getString(R.string.select_staff_before));
                    return;
                }
                List<Integer> Staffs = new ArrayList<>();
                Staffs.addAll(selectedStaffs);
                EditAttendanceGroupActivity.startEditAttendanceGroup(this, name, Staffs,null,true);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void subscribeSelectedStaffs(SelectedStaffsEvent event) {
        selectedStaffs.addAll(event.getIntegers());
        selectedTotal = event.getIntegers().size();
        tvMember.setText(String.format("%s个成员", selectedTotal));
    }

    @Subscribe
    public void subscribeCreateResult(CreateAttendanceGroupSuccessEvent event) {
        if (event.isCreateSuccess) {
            this.finish();
        }
    }


}
