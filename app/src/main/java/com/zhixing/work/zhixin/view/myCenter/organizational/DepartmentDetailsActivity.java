package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.DepartmentAdapter;
import com.zhixing.work.zhixin.adapter.StaffAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Department;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Staff;
import com.zhixing.work.zhixin.event.DepartmentRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentDetailsActivity extends BaseTitleActivity {
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.department_name)
    TextView departmentName;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.add_user)
    TextView addUser;
    @BindView(R.id.add_department)
    TextView addDepartment;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.rl_add)
    LinearLayout rlAdd;
    @BindView(R.id.staff_listview)
    RecyclerView staffListview;
    private Department department;
    private List<Department> list = new ArrayList<>();
    private DepartmentAdapter adapter;
    private String name;
    private String ParentId;
    private String SuperiorName;
    private StaffAdapter staffAdapter ;
    private List<Staff> staffList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmentetails);
        ButterKnife.bind(this);
        setTitle("管理部门和成员");
        Bundle bundle = getIntent().getExtras();
        department = (Department) bundle.get("bean");
        name = getIntent().getStringExtra("name");
        ParentId = getIntent().getStringExtra("ParentId");
        SuperiorName = getIntent().getStringExtra("SuperiorName");
        initView();
        getData();
    }

    private void initView() {
        companyName.setText(name);
        adapter = new DepartmentAdapter(list, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(linearLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new DepartmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, DepartmentDetailsActivity.class);
                intent.putExtra("name", name + ">" + list.get(position).getDepartmentName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("ParentId", department.getDepartmentId() + "");

                intent.putExtra("SuperiorName", department.getDepartmentName() + "");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        staffAdapter = new StaffAdapter(staffList, context);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(context);
        LayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        staffListview.setLayoutManager(LayoutManager);
        staffListview.setItemAnimator(new DefaultItemAnimator());
        staffListview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListview.setAdapter(staffAdapter);
        staffAdapter.setOnItemClickListener(new StaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @OnClick({R.id.add_user, R.id.add_department, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                break;
            case R.id.add_department:
                Intent addIntent = new Intent(context, AddDepartmentActivity.class);
                Bundle AddBundle = new Bundle();
                AddBundle.putSerializable("bean", department);
                addIntent.putExtras(AddBundle);
                startActivity(addIntent);
                break;
            case R.id.setting:
                Intent intent = new Intent(context, DepartmentSettingActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("ParentId", ParentId);
                intent.putExtra("SuperiorName", SuperiorName);
                bundle.putSerializable("bean", department);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDepartmentRefreshEvent(DepartmentRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }

    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.Department, JavaParamsUtils.getInstances().getDepartment(department.getDepartmentId() + ""), new TypeToken<EntityObject<List<Department>>>() {
        }.getType(), new ResultCallBackListener<List<Department>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Department>> response) {
                if (response.getCode() == 10000) {
                    if (!response.getContent().isEmpty()) {
                        list = response.getContent();
                        adapter.setList(list);
                    } else {
                        list = response.getContent();
                        adapter.setList(list);
                    }
                }
            }
        });
    }

    private void getStaff() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.Staff, JavaParamsUtils.getInstances().getCompany(), new TypeToken<EntityObject<List<Staff>>>() {
        }.getType(), new ResultCallBackListener<List<Staff>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Staff>> response) {
                if (response.getCode() == 10000) {
                    if (!response.getContent().isEmpty()) {
                        staffList = response.getContent();

                    }
                }
            }
        });
    }
}
