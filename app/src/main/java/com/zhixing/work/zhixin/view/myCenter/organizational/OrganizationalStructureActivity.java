package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.StaffAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Department;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Staff;
import com.zhixing.work.zhixin.bean.StaffList;
import com.zhixing.work.zhixin.bean.Staffs;
import com.zhixing.work.zhixin.dialog.ShareDialog;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 组织架构
 */
public class OrganizationalStructureActivity extends BaseTitleActivity {

    @BindView(R.id.departmentList)
    RecyclerView departmentList;
    @BindView(R.id.workmate)
    TextView workmate;
    @BindView(R.id.rl_add_workmate)
    LinearLayout rlAddWorkmate;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.staff_listview)
    RecyclerView staffListview;
    private List<Department> list = new ArrayList<>();
    private List<Staff> staffList = new ArrayList<>();
    private List<Staffs> numberList = new ArrayList<>();
    private Department department;

    private StaffAdapter adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizational_structure);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.organizational_structure));
        setRightText1("管理");
        getData();
        getAllnumber();
        initView();
    }
    private void initView() {
        adapter = new StaffAdapter(staffList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        staffListview.setLayoutManager(linearLayoutManager);
        staffListview.setItemAnimator(new DefaultItemAnimator());
        staffListview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListview.setAdapter(adapter);
        adapter.setOnItemClickListener(new StaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (department != null) {
                    Intent intent = new Intent(context, DepartmentManagementActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(DepartmentManagementActivity.INTENT_KEY_NAME, department.getDepartmentName());
                    bundle.putSerializable(DepartmentManagementActivity.INTENT_KEY_BEAN, department);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.DEPARTMENT, JavaParamsUtils.getInstances().getCompany(), new TypeToken<EntityObject<List<Department>>>() {
        }.getType(), new ResultCallBackListener<List<Department>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, ResourceUtils.getString(R.string.server_error));
            }
            @Override
            public void onSuccess(EntityObject<List<Department>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (!response.getContent().isEmpty()) {
                        list = response.getContent();
                        department = list.get(0);
                        companyName.setText(department.getDepartmentName());
                        getStaff();
                    }
                }
            }
        });
    }
    private void getAllnumber() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.DEPARTMENT_MEMBER, JavaParamsUtils.getInstances().getCompany(), new TypeToken<EntityObject<List<Staffs>>>() {
        }.getType(), new ResultCallBackListener<List<Staffs>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }
            @Override
            public void onSuccess(EntityObject<List<Staffs>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (!response.getContent().isEmpty()) {
                        numberList = response.getContent();
                        try {
                            List<StaffList> data = Utils.getLeftTrees(numberList);
                            SettingUtils.putStaffList(gson.toJson(data));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    private void getStaff() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.STAFF, JavaParamsUtils.getInstances().getSetff(department.getDepartmentId() + ""), new TypeToken<EntityObject<List<Staff>>>() {
        }.getType(), new ResultCallBackListener<List<Staff>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Staff>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (!response.getContent().isEmpty()) {
                        staffList = response.getContent();
                    }
                }
            }
        });
    }

    @OnClick(R.id.rl_add_workmate)
    public void onViewClicked() {
        ShareDialog imageDialog = new ShareDialog(context, new ShareDialog.OnItemClickListener() {
            @Override
            public void onClick(ShareDialog dialog, int index) {
                dialog.dismiss();

            }
        });
        imageDialog.show();
    }
}
