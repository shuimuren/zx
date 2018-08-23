package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.DepartmentListAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentManagerSelectorAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentMemberTitleAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.network.response.SettingRoleResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.setting.ManagerSettingActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/23.
 * Description: 选择管理员/HR
 */

public class SelectionDepartmentManagerActivity extends BaseTitleActivity {


    @BindView(R.id.top)
    View top;
    @BindView(R.id.tvSearchOrCancel)
    TextView tvSearchOrCancel;
    @BindView(R.id.departmentTitleList)
    RecyclerView departmentTitleList;
    @BindView(R.id.department_list)
    RecyclerView departmentList;
    @BindView(R.id.staff_list)
    RecyclerView staffListView;
    @BindView(R.id.ll_staff_view)
    LinearLayout llStaffView;

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表
    private Subscription mSetUserRoleSubscription; //设置角色

    private DepartmentListAdapter mDepartmentListAdapter;
    private DepartmentManagerSelectorAdapter mDepartmentStaffAdapter;
    private DepartmentMemberTitleAdapter mDepartmentMemberTitleAdapter; // 顶部title

    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberInfoBean> staffs;
    private List<DepartmentMemberBean> mDepartmentMembers;


    private String mDepartmentName;
    private String mDepartmentId;
    private String mStaffRole;
    private DepartmentMemberInfoBean mSelectorMemberInfoBean;

    private DepartmentManagerHelper mDepartmentManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_department_manager);
        ButterKnife.bind(this);
        int role = getIntent().getIntExtra(ManagerSettingActivity.STAFF_ROLE, 1);
        mStaffRole = String.valueOf(role);
        if (role == ResultConstant.USER_STAFF_ROLE_HR) {
            setTitle(ResourceUtils.getString(R.string.setting_hr_title));
        } else if (role == ResultConstant.USER_STAFF_ROLE_MANAGER) {
            setTitle(ResourceUtils.getString(R.string.setting_manager_title));
        }
        setTitle("选择人员");
        setRightText1("确定");
        mDepartmentId = String.valueOf(SettingUtils.getTokenBean().getDepartmentId());
        intiView();
        registerRequest();
    }

    private void intiView() {

        mDepartmentMembers = new ArrayList<>();
        mDepartmentMemberTitleAdapter = new DepartmentMemberTitleAdapter(mDepartmentMembers);
        LinearLayoutManager manager = new LinearLayoutManager(SelectionDepartmentManagerActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        departmentTitleList.setLayoutManager(manager);
        departmentTitleList.setAdapter(mDepartmentMemberTitleAdapter);
        mDepartmentMemberTitleAdapter.setItemClickedListener(((position, bean) -> {
            mDepartmentId = bean.getDepartmentId();
            mDepartmentName = bean.getDepartmentName();
            mDepartmentMembers.subList(position + 1, mDepartmentMembers.size()).clear();
            mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
            upDateDepartmentAndStaff(mDepartmentId);
        }));

        departments = new ArrayList<>();
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentListAdapter = new DepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(SelectionDepartmentManagerActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);
        mDepartmentListAdapter.setItemClickedListener(new DepartmentListAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(ChildDepartmentBean bean) {
                if (mSelectorMemberInfoBean != null) {
                    AlertUtils.show("您已选中了一个成员");
                    return;
                }
                mDepartmentId = String.valueOf(bean.getDepartmentId());
                mDepartmentName = bean.getDepartmentName();
                upDateTitleView(mDepartmentId, mDepartmentName);
                upDateDepartmentAndStaff(mDepartmentId);
            }
        });

        staffs = new ArrayList<>();
        mDepartmentStaffAdapter = new DepartmentManagerSelectorAdapter(this, staffs);
        staffListView.setHasFixedSize(true);
        staffListView.setLayoutManager(new LinearLayoutManager(SelectionDepartmentManagerActivity.this));
        staffListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListView.setAdapter(mDepartmentStaffAdapter);
        mDepartmentStaffAdapter.setItemClickedListener(new DepartmentManagerSelectorAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(DepartmentMemberInfoBean bean) {
                //   StaffCardActivity.startStaffCardActivity(SelectionDepartmentManagerActivity.this, String.valueOf(bean.getStaffId()));
            }

            @Override
            public void selectClicked(int position, DepartmentMemberInfoBean bean) {
                for (int i = 0; i < staffs.size(); i++) {
                    staffs.get(i).setSelected(false);
                }
                if (bean.isSelected()) {
                    bean.setSelected(false);
                    mSelectorMemberInfoBean = null;
                } else {
                    bean.setSelected(true);
                    mSelectorMemberInfoBean = bean;
                }
                //mDepartmentStaffAdapter.setData(staffs);
                mDepartmentStaffAdapter.notifyDataSetChanged();
            }
        });

        mSetUserRoleSubscription = RxBus.getInstance().toObservable(SettingRoleResult.class).subscribe(
                result -> handlerSettingResult(result)
        );

        setRightClickListener(v -> {
            if (mSelectorMemberInfoBean == null) {
                AlertUtils.show("您还没有选中成员");
            } else {
                setUserRole(String.valueOf(mSelectorMemberInfoBean.getStaffId()), mStaffRole);
            }
        });

    }

    private void handlerSettingResult(SettingRoleResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.handler_success));
                this.finish();
            }
        }
    }

    private void registerRequest() {
        showLoading();

        mGetAllDepartmentSubscription = RxBus.getInstance().toObservable(AllDepartmentMemberResult.class).subscribe(
                result -> handlerAllDepartmentMember(result));

        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result));

        mDepartmentStaffSubscription = RxBus.getInstance().toObservable(DepartmentMemberInfoResult.class).subscribe(
                result -> handlerDepartmentMemberInfo(result)
        );

        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER);

    }

    //刷新数据
    private void upDateTitleView(String departmentId, String departmentName) {
        mDepartmentMembers.add(new DepartmentMemberBean(departmentId, departmentName));
        mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
    }

    //公司下所有成员
    private void handlerAllDepartmentMember(AllDepartmentMemberResult result) {
        hideLoadingDialog();
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                mDepartmentManagerHelper.setDepartmentStaffsBeans(result.getContent());
                upDateDepartmentAndStaff(mDepartmentId);
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    //子部门结果
    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (mDepartmentId.equals(String.valueOf(SettingUtils.getTokenBean().getDepartmentId())) && mDepartmentMembers.size() == 0) {
                    upDateTitleView(mDepartmentId, result.getContent().getCurrentDepartmentName());
                }
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

    //成员结果
    private void handlerDepartmentMemberInfo(DepartmentMemberInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (result.getContent() != null) {
                    staffs = result.getContent();
                    mDepartmentStaffAdapter.setData(staffs);
                }
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    //设置角色
    private void setUserRole(String staffId, String staffRole) {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_STAFF_ID, staffId);
        map.put(RequestConstant.KEY_STAFF_ROLE, staffRole);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SET_USER_ROLE, map);
    }

    //刷新部门
    private void upDateDepartmentAndStaff(String departmentId) {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, departmentId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDepartmentStaffSubscription, mGetAllDepartmentSubscription,
                mGetChildDepartmentSubscription, mSetUserRoleSubscription);
    }
}
