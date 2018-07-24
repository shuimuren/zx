package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.DepartmentListAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentStaffAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.share.ShareConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class DepartmentManagementActivity extends BaseTitleActivity {

    @BindView(R.id.tvSearchOrCancel)
    TextView tvSearchOrCancel;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.department_list)
    RecyclerView departmentList;
    @BindView(R.id.staff_list)
    RecyclerView staffListView;
    @BindView(R.id.ll_staff_view)
    LinearLayout llStaffView;
    @BindView(R.id.top)
    View top;
    @BindView(R.id.add_user)
    TextView addUser;
    @BindView(R.id.add_department)
    TextView addDepartment;

    public static final String INTENT_KEY_CHILD_DEPARTMENT_LIST = "departmentList";
    public static final String INTENT_KEY_DEPARTMENT_MEMBER_LIST = "departmentMemberList";
    public static final String INTENT_KEY_DEPARTMENT_ID = "departmentId";
    public static final String INTENT_KEY_PARENT_DEPARTMENT_NAME = "departmentName";

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表


    private DepartmentListAdapter mDepartmentListAdapter;
    private DepartmentStaffAdapter mDepartmentStaffAdapter;
    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberInfoBean> staffs;
    private List<DepartmentMemberBean> mDepartmentMembers;
    private String departmentName;
    private String departmentId;
    private DepartmentManagerHelper mDepartmentManagerHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_management);
        ButterKnife.bind(this);
        setTitle("管理部门与成员");
        intiView();
        registerRequest();
        getIntentDate();
    }

    private void intiView() {
        departments = new ArrayList<>();
        mDepartmentMembers = new ArrayList<>();
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentListAdapter = new DepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(DepartmentManagementActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);
        mDepartmentListAdapter.setItemClickedListener(new DepartmentListAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(ChildDepartmentBean bean) {
                DepartmentDetailsActivity.startDepartmentDetailActivity(DepartmentManagementActivity.this, departmentName,
                        departmentId, bean.getDepartmentName(), String.valueOf(bean.getDepartmentId()));
            }
        });

        staffs = new ArrayList<>();
        mDepartmentStaffAdapter = new DepartmentStaffAdapter(this, staffs, true);
        staffListView.setHasFixedSize(true);
        staffListView.setLayoutManager(new LinearLayoutManager(DepartmentManagementActivity.this));
        staffListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListView.setAdapter(mDepartmentStaffAdapter);
        mDepartmentStaffAdapter.setItemClickedListener(new DepartmentStaffAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(DepartmentMemberInfoBean bean) {
                Logger.i(">>>", "点击个人");
            }
        });

    }


    private void registerRequest() {
        mGetAllDepartmentSubscription = RxBus.getInstance().toObservable(AllDepartmentMemberResult.class).subscribe(
                result -> handlerAllDepartmentMember(result));
        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result));
        mDepartmentStaffSubscription = RxBus.getInstance().toObservable(DepartmentMemberInfoResult.class).subscribe(
                result -> handlerDepartmentMemberInfo(result));
    }


    public void getIntentDate() {
        Bundle bundle = getIntent().getExtras();
        departmentName = bundle.getString(INTENT_KEY_PARENT_DEPARTMENT_NAME);
        departmentId = bundle.getString(INTENT_KEY_DEPARTMENT_ID);
        mDepartmentMembers.add(new DepartmentMemberBean(departmentId, departmentName));
        departments = (List<ChildDepartmentBean>) bundle.getSerializable(INTENT_KEY_CHILD_DEPARTMENT_LIST);
        staffs = (List<DepartmentMemberInfoBean>) bundle.getSerializable(INTENT_KEY_DEPARTMENT_MEMBER_LIST);

        companyName.setText(departmentName);
        mDepartmentListAdapter.setData(departments);
        if (staffs.size() > 0) {
            llStaffView.setVisibility(View.VISIBLE);
            mDepartmentStaffAdapter.setData(staffs);
        } else {
            llStaffView.setVisibility(View.GONE);
        }
    }

    private void handlerAllDepartmentMember(AllDepartmentMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {

        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(departmentId)) {
                departments = result.getContent().getSubDepartments();
                for (int i = 0; i < departments.size(); i++) {
                    departments.get(i).setMemberTotal(mDepartmentManagerHelper.getStaffTotalByDepartmentId(departments.get(i).getDepartmentId()));
                }
                mDepartmentListAdapter.setData(departments);

            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void handlerDepartmentMemberInfo(DepartmentMemberInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(departmentId)) {
                if (result.getContent() != null && result.getContent().size() > 0) {
                    llStaffView.setVisibility(View.VISIBLE);
                    mDepartmentStaffAdapter.setData(result.getContent());
                } else {
                    llStaffView.setVisibility(View.GONE);
                }

            }
        } else {
            AlertUtils.show(result.Message);
        }

    }


    @OnClick()
    public void onViewClicked() {
        Map<String, Object> params = new HashMap<>();
        //此处应填写完整参数
        params.put(ShareConstant.PARAM_SHARE_TITLE, "职信科技有限公司");
        params.put(ShareConstant.PARAMS_CONTEXT, DepartmentManagementActivity.this);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDepartmentStaffSubscription, mGetAllDepartmentSubscription, mGetChildDepartmentSubscription);
    }

    @OnClick({R.id.add_user, R.id.add_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                Map<String, Object> params = new HashMap<>();
                //此处应填写完整参数
                params.put(ShareConstant.PARAM_SHARE_TITLE, "职信科技有限公司");
                params.put(ShareConstant.PARAMS_CONTEXT, DepartmentManagementActivity.this);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM, params);
                break;
            case R.id.add_department:
                AddDepartmentActivity.startAddDepartmentActivity(DepartmentManagementActivity.this,
                        departmentId, departmentName, departments, mDepartmentMembers);
                break;
        }
    }

}
