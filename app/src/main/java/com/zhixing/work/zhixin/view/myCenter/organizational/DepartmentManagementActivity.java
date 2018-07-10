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
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentManagementActivity extends BaseTitleActivity {

    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.add_user)
    TextView addUser;
    @BindView(R.id.add_department)
    TextView addDepartment;
    @BindView(R.id.rl_add)
    LinearLayout rlAdd;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.staff_listview)
    RecyclerView staffListview;

    public static final String INTENT_KEY_NAME = "name";
    public static final String INTENT_KEY_BEAN = "bean";

    private List<Department> list = new ArrayList<>();
    private DepartmentAdapter adapter;
    private Department department;
    private String name;
    private StaffAdapter staffAdapter;
    private List<Staff> staffList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_management);
        ButterKnife.bind(this);
        setTitle("管理部门与成员");
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString(INTENT_KEY_NAME);
        department = (Department) bundle.get(INTENT_KEY_BEAN);
        initView();
        getData();
        getStaff();
    }
    private void initView() {
        title.setText(name);
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
                intent.putExtra("ParentId", department.getDepartmentId() + "");
                intent.putExtra("SuperiorName", department.getDepartmentName() + "");

                bundle.putSerializable("bean", list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                Math.floor(60.5);
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

    @OnClick({R.id.add_user, R.id.add_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                break;
            case R.id.add_department:
                if (department != null) {
                    Intent intent = new Intent(context, AddDepartmentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", department);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDepartmentRefreshEvent(DepartmentRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
    private void getStaff() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.STAFF, JavaParamsUtils.getInstances().getSetff(department.getDepartmentId()+""), new TypeToken<EntityObject<List<Staff>>>() {
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
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.DEPARTMENT, JavaParamsUtils.getInstances().getDepartment(department.getDepartmentId() + ""), new TypeToken<EntityObject<List<Department>>>() {
        }.getType(), new ResultCallBackListener<List<Department>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }
            @Override
            public void onSuccess(EntityObject<List<Department>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (!response.getContent().isEmpty()) {
                        list = response.getContent();
                        adapter.setList(list);
                    } else {
                        list.clear();
                        adapter.setList(list);
                    }
                }
            }
        });
    }
}
