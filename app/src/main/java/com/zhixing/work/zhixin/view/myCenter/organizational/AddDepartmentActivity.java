package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Department;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.DepartmentRefreshEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDepartmentActivity extends BaseTitleActivity {

    @BindView(R.id.department_name)
    TextView departmentName;
    @BindView(R.id.department_name_right)
    ImageView departmentNameRight;
    @BindView(R.id.rl_department_name)
    RelativeLayout rlDepartmentName;
    @BindView(R.id.superior_department)
    TextView superiorDepartment;
    @BindView(R.id.superior_department_right)
    ImageView superiorDepartmentRight;
    @BindView(R.id.rl_superior_department)
    RelativeLayout rlSuperiorDepartment;
    private Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        ButterKnife.bind(this);
        setTitle("添加子部门");
        setRightText1("完成");
        Bundle bundle = getIntent().getExtras();
        department = (Department) bundle.get("bean");
        superiorDepartment.setText(department.getDepartmentName());
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(departmentName.getText().toString())) {
                    AlertUtils.toast(context, "部门名字不能为空");
                    return;
                }
                showLoading();
                addDepartment(department.getDepartmentId() + "",departmentName.getText().toString());
            }

        });
    }

    @OnClick({R.id.rl_department_name, R.id.rl_superior_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_department_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "部门名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.DEPARTMENT_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, departmentName.getText().toString()));
                break;
            case R.id.rl_superior_department:
                //startActivity(new Intent(context, SelectionDepartmentActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ONModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.DEPARTMENT_NAME:
                departmentName.setText(event.getContent());
                break;


        }
    }

    private void addDepartment(String ParentId, String Name
    ) {
        OkUtils.getInstances().httpPost(context, RequestConstant.DEPARTMENT, JavaParamsUtils.getInstances().addDepartment(ParentId, Name
        ), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "添加成功");
                        EventBus.getDefault().post(new DepartmentRefreshEvent(true));
                        finish();
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }


            }
        });
    }
}