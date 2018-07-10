package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Department;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.ModifyDepartment;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentSettingActivity extends BaseTitleActivity {

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
    @BindView(R.id.department_director)
    TextView departmentDirector;
    @BindView(R.id.department_director_right)
    ImageView departmentDirectorRight;
    @BindView(R.id.rl_department_director)
    RelativeLayout rlDepartmentDirector;
    @BindView(R.id.is_hide)
    CheckBox isHide;
    @BindView(R.id.ll_hide)
    LinearLayout llHide;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.ll_delete)
    RelativeLayout llDelete;
    private Department department;
    private String ParentId;
    private String SuperiorName;

    private String ManagerId = "";
    private List<Integer> DepartmentIds;
    private List<Integer> StaffIds;

    private DeleteDialog deleteDialog;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_setting);
        ButterKnife.bind(this);
        setTitle("部门设置");
        Bundle bundle = getIntent().getExtras();
        department = (Department) bundle.get("bean");
        departmentName.setText(department.getDepartmentName());
        ParentId = getIntent().getStringExtra("ParentId");
        SuperiorName = getIntent().getStringExtra("SuperiorName");
        superiorDepartment.setText(SuperiorName);
        setRightText1("完成");
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyDepartment modifyDepartment = new ModifyDepartment();
                modifyDepartment.setId(department.getDepartmentId());
                modifyDepartment.setParentId(Integer.parseInt(ParentId));
                modifyDepartment.setName(departmentName.getText().toString());
                modifyDepartment.setManagerId(ManagerId);
                modifyDepartment.setHide(isHide.isChecked() + "");
                modifyDepartment.setDepartmentIds(DepartmentIds);
                modifyDepartment.setStaffIds(StaffIds);
                modify(gson.toJson(modifyDepartment));
            }
        });
    }

    @OnClick({R.id.rl_department_name, R.id.rl_superior_department, R.id.rl_department_director, R.id.is_hide, R.id.rl_setting, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_department_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "部门名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.DEPARTMENT_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, departmentName.getText().toString()));

                break;
            case R.id.rl_superior_department:
                break;
            case R.id.rl_department_director:
                break;
            case R.id.is_hide:
                break;
            case R.id.rl_setting:
                break;
            case R.id.delete:
                deleteDialog = new DeleteDialog(context, "是否删除此部门", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {
                            deleteData(department.getDepartmentId());
                        }
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();

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

    //修改
    private void modify(String json) {

        OkUtils.getInstances().putJson(context, RequestConstant.DEPARTMENT, json, new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                                AlertUtils.toast(context, "修改成功");
                                EventBus.getDefault().post(new DepartmentRefreshEvent(true));
                                finish();
                            } else {
                                AlertUtils.toast(context, "修改失败");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });


    }

    //删除经历
    private void deleteData(int id) {
        OkUtils.getInstances().httpDelete(context, RequestConstant.DEPARTMENT + "?departmentId=" + id, JavaParamsUtils.getInstances().deleteProduct(), new TypeToken<EntityObject<Boolean>>() {
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
                        AlertUtils.toast(context, "删除成功");
                        EventBus.getDefault().post(new DepartmentRefreshEvent(true));
                        finish();
                    } else {
                        AlertUtils.toast(context, "删除失败");
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });


    }

}
