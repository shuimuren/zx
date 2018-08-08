package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.content.Intent;
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
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 组织架构
 */
public class OrganizationalStructureActivity extends BaseTitleActivity {


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

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表
    private String departmentId;

    private DepartmentListAdapter mDepartmentListAdapter;
    private DepartmentStaffAdapter mDepartmentStaffAdapter;
    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberInfoBean> staffs;
    private String departmentName;
    private DepartmentManagerHelper mDepartmentManagerHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizational_structure);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.organizational_structure));
        setRightText1("管理");
        departmentId = String.valueOf(SettingUtils.getTokenBean().getDepartmentId());
        intiView();
        registerRequest();
    }

    private void intiView() {
        departments = new ArrayList<>();
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentListAdapter = new DepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(OrganizationalStructureActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);
        mDepartmentListAdapter.setItemClickedListener(new DepartmentListAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(ChildDepartmentBean bean) {
                DepartmentDetailsActivity.startDepartmentDetailActivity(OrganizationalStructureActivity.this, departmentName,
                        departmentId, bean.getDepartmentName(), String.valueOf(bean.getDepartmentId()));
            }
        });

        staffs = new ArrayList<>();
        mDepartmentStaffAdapter = new DepartmentStaffAdapter(this, staffs, false);
        staffListView.setHasFixedSize(true);
        staffListView.setLayoutManager(new LinearLayoutManager(OrganizationalStructureActivity.this));
        staffListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListView.setAdapter(mDepartmentStaffAdapter);
        mDepartmentStaffAdapter.setItemClickedListener(new DepartmentStaffAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(DepartmentMemberInfoBean bean) {
                Logger.i(">>>", "点击个人");
            }
        });

        setRightClickListener(v -> {
            Intent intent = new Intent(context, DepartmentManagementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DepartmentManagementActivity.INTENT_KEY_DEPARTMENT_ID, String.valueOf(SettingUtils.getTokenBean().getDepartmentId()));
            bundle.putString(DepartmentManagementActivity.INTENT_KEY_PARENT_DEPARTMENT_NAME, departmentName);
            bundle.putSerializable(DepartmentManagementActivity.INTENT_KEY_CHILD_DEPARTMENT_LIST, (Serializable) departments);
            bundle.putSerializable(DepartmentManagementActivity.INTENT_KEY_DEPARTMENT_MEMBER_LIST, (Serializable) staffs);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    private void registerRequest() {
        mGetAllDepartmentSubscription = RxBus.getInstance().toObservable(AllDepartmentMemberResult.class).subscribe(
                result -> handlerAllDepartmentMember(result));
        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result)

        );
        mDepartmentStaffSubscription = RxBus.getInstance().toObservable(DepartmentMemberInfoResult.class).subscribe(
                result -> handlerDepartmentMemberInfo(result)
        );
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER);

    }

    private void handlerAllDepartmentMember(AllDepartmentMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                mDepartmentManagerHelper.setDepartmentStaffsBeans(result.getContent());
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, departmentId);
            }

        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(departmentId)) {
                departmentName = result.getContent().getCurrentDepartmentName();
                companyName.setText(ZxTextUtils.getTextNotNull(departmentName));
                if (result.getContent().getSubDepartments() != null) {
                    departments = result.getContent().getSubDepartments();
                    for (int i = 0; i < departments.size(); i++) {
                       departments.get(i).setMemberTotal(mDepartmentManagerHelper.getStaffTotalByDepartmentId(departments.get(i).getDepartmentId()));
                    }
                    mDepartmentListAdapter.setData(departments);
                }

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

/*
 public static final String PARAMS_CONTEXT = "shareContext";
    public static final String PARAM_SHARE_THUMBNAIL = "p_share_thumbnail";
    public static final String PARAM_SHARE_TITLE = "p_share_title";
    public static final String PARAM_SHARE_URL = "p_share_url";
    public static final String PARAM_SHARE_DESCRIPTION = "p_share_desc";
    public static final String PARAM_SHARE_COMPANY_NAME = "company_name"; //公司名称
    public static final String PARAM_SHARE_MANAGER_NAME = "manager_name";//管理员名称
 */
    @OnClick(R.id.workmate)
    public void onViewClicked() {
        Map<String, Object> params = new HashMap<>();
        //此处应填写完整参数

        params.put(ShareConstant.PARAMS_CONTEXT, OrganizationalStructureActivity.this);
        params.put(ShareConstant.PARAM_SHARE_TITLE, departmentName);
        params.put(ShareConstant.PARAM_SHARE_URL,"www.baidu.com");
        params.put(ShareConstant.PARAM_SHARE_DESCRIPTION,"入职邀请测试");
        params.put(ShareConstant.PARAM_SHARE_COMPANY_NAME,"职信科技");
        params.put(ShareConstant.PARAM_SHARE_MANAGER_NAME,"aaa");

        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDepartmentStaffSubscription, mGetAllDepartmentSubscription, mGetChildDepartmentSubscription);
    }
}
