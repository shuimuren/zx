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
import com.zhixing.work.zhixin.bean.DepartmentInviteBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.event.HandlerApplyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentInviteResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.network.response.NewJoinMemberResult;
import com.zhixing.work.zhixin.share.ShareUtil;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.StaffCardActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
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
    @BindView(R.id.tv_new_add)
    TextView tvNewAdd;
    @BindView(R.id.ll_audit_new_member)
    LinearLayout llAuditNewMember;

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表
    private Subscription mInviteSubscription;//邀请连接
    private Subscription mGetJoinDepartmentApply;
    private String departmentId;

    private DepartmentListAdapter mDepartmentListAdapter;
    private DepartmentStaffAdapter mDepartmentStaffAdapter;
    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberInfoBean> staffs;
    private String departmentName;
    private DepartmentInviteBean inviteBean;

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
                StaffCardActivity.startStaffCardActivity(OrganizationalStructureActivity.this,String.valueOf(bean.getStaffId()));
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
        mInviteSubscription = RxBus.getInstance().toObservable(DepartmentInviteResult.class).subscribe(
                result -> handlerDepartmentInvite(result)
        );

        mGetJoinDepartmentApply = RxBus.getInstance().toObservable(NewJoinMemberResult.class).subscribe(
                result -> handlerJoinMember(result)
        );

        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_DEPARTMENT_INVITE, departmentId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_JOIN_DEPARTMENT_APPLY, departmentId);
    }

    private void handlerJoinMember(NewJoinMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() > 0) {
                llAuditNewMember.setVisibility(View.VISIBLE);
                String des = String.format("新同事审核 (%s)", String.valueOf(result.getContent()));
                tvNewAdd.setText(Utils.changeColor(des, ResourceUtils.getColor(R.color.moremoney), des.indexOf("(") + 1, des.indexOf(")")));
            } else {
                llAuditNewMember.setVisibility(View.GONE);
            }
        }
    }

    private void handlerDepartmentInvite(DepartmentInviteResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            inviteBean = result.getContent();
        }
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

    @OnClick({R.id.ll_audit_new_member, R.id.workmate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_audit_new_member:
                AuditMemberListActivity.StartActivityAuditMemberList(this, ResultConstant.AUDIT_STATUS_WAITING);
                break;
            case R.id.workmate:
                if (inviteBean != null) {
                    Map<String, Object> params = ShareUtil.getInstance().getShareParams(this, "邀请加入公司",
                            inviteBean.getInviter() + "邀请您加入" + inviteBean.getCompanyName(), inviteBean.getUrl(), departmentId, inviteBean);
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM, params);
                } else {
                    AlertUtils.show(ResourceUtils.getString(R.string.get_department_info_error));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDepartmentStaffSubscription, mGetAllDepartmentSubscription,
                mGetChildDepartmentSubscription, mInviteSubscription, mGetJoinDepartmentApply);
    }

    @Subscribe
    public void handlerEvent(HandlerApplyEvent event) {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_JOIN_DEPARTMENT_APPLY, departmentId);
    }
}
