package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceDepartmentListAdapter;
import com.zhixing.work.zhixin.adapter.AttendanceDepartmentStaffAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentMemberTitleAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;
import com.zhixing.work.zhixin.dialog.EditAttendanceMemberDialog;
import com.zhixing.work.zhixin.event.SelectedStaffsEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;
import com.zhixing.work.zhixin.network.response.CheckStaffRuleResult;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.network.response.EditAttendanceMemberResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/2.
 * Description:选择参与人员
 */

public class SelectAttendanceMemberActivity extends BaseTitleActivity {

    @BindView(R.id.departmentTitleList)
    RecyclerView departmentTitleList;
    @BindView(R.id.image_check)
    ImageView imageCheck;
    @BindView(R.id.department_list)
    RecyclerView departmentList;
    @BindView(R.id.staff_list)
    RecyclerView staffListView;

    private static final String INTENT_KEY_MEMBER_LIST = "memberList";
    private static final String INTENT_KEY_RULE_ID = "ruleId";

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表


    private AttendanceDepartmentListAdapter mDepartmentListAdapter;
    private AttendanceDepartmentStaffAdapter mDepartmentStaffAdapter;
    private DepartmentMemberTitleAdapter mDepartmentMemberTitleAdapter; // 顶部title

    private List<ChildDepartmentBean> departments; //部门列表
    private List<DepartmentMemberInfoBean> staffs; //成员列表
    private List<DepartmentMemberBean> mDepartmentMembers;//标题导航

    private String mDepartmentName;
    private String mDepartmentId;
    private boolean isFirstCreate;
    private DepartmentManagerHelper mDepartmentManagerHelper;
    private boolean isSelectAll;
    private String mRuleId;

    private Subscription mCheckStaffSubscription;
    private Subscription mEditMemberSubscription;


    public static void startSelectAttendanceActivity(Activity activity, String ruleId, List<Integer> memberList) {
        Intent intent = new Intent(activity, SelectAttendanceMemberActivity.class);
        if (memberList != null) {
            intent.putIntegerArrayListExtra(INTENT_KEY_MEMBER_LIST, (ArrayList<Integer>) memberList);
            intent.putExtra(INTENT_KEY_RULE_ID, ruleId);
        }
        activity.startActivity(intent);
    }

    private List<Integer> mSelectedMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attendance_member);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.clock_member));
        mDepartmentId = String.valueOf(SettingUtils.getTokenBean().getDepartmentId());
        isFirstCreate = true;
        intiView();
        registerRequest();
    }

    private void registerRequest() {
        mGetAllDepartmentSubscription = RxBus.getInstance().toObservable(AllDepartmentMemberResult.class).subscribe(
                result -> handlerAllDepartmentMember(result));
        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result));
        mDepartmentStaffSubscription = RxBus.getInstance().toObservable(DepartmentMemberInfoResult.class).subscribe(
                result -> handlerDepartmentMemberInfo(result));
        mCheckStaffSubscription = RxBus.getInstance().toObservable(CheckStaffRuleResult.class).subscribe(
                result -> handlerCheckStaffRuleResult(result)
        );
        mEditMemberSubscription = RxBus.getInstance().toObservable(EditAttendanceMemberResult.class).subscribe(
                result -> handlerEditMemberResult(result)
        );
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER);

    }

    private void handlerEditMemberResult(EditAttendanceMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE && result.isContent()) {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RULE);
            AlertUtils.show(ResourceUtils.getString(R.string.update_success));
            this.finish();
        }
    }

    private void handlerCheckStaffRuleResult(CheckStaffRuleResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() == null || result.getContent().size() == 0) {
                updateMember();
            } else {
                EditAttendanceMemberDialog dialog = new EditAttendanceMemberDialog(this, new EditAttendanceMemberDialog.OnItemClickListener() {
                    @Override
                    public void dialogMakeSure() {
                        updateMember();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
                dialog.setText(result.getContent());
            }
        }
    }

    private void updateMember() {
        if (!TextUtils.isEmpty(mRuleId)) {
            Map map = new HashMap();

            map.put(RequestConstant.KEY_ID, mRuleId);
            List<Integer> list = new ArrayList<>();
            list.addAll(mDepartmentManagerHelper.getSelectedDepartmentStaffs());
            Integer[] integers = new Integer[list.size()];
            map.put(RequestConstant.KEY_REQUEST_ARRAY, list.toArray(integers));
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_ATTENDANCE_MEMBER, map);
        } else {
            SelectedStaffsEvent event = new SelectedStaffsEvent();
            event.setIntegers(mDepartmentManagerHelper.getSelectedDepartmentStaffs());
            EventBus.getDefault().post(event);
            this.finish();
        }
    }

    /**
     * 公司组织架构全部信息
     *
     * @param result
     */
    private void handlerAllDepartmentMember(AllDepartmentMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                mDepartmentManagerHelper.setDepartmentStaffsBeans(result.getContent());
                upDateFromCache();
            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    /**
     * 公司子部门信息
     *
     * @param result
     */
    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (isFirstCreate) {
                    mDepartmentName = result.getContent().getCurrentDepartmentName();
                    mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
                    mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                }
                isFirstCreate = false;
                if (result.getContent().getSubDepartments() != null) {
                    departments = result.getContent().getSubDepartments();
                    mDepartmentManagerHelper.setCacheDepartmentChild(mDepartmentId, departments);
                    mDepartmentListAdapter.setData(mDepartmentManagerHelper.getDepartmentChildList(mDepartmentId));
                }
            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    /**
     * 公司子部门下员工
     *
     * @param result
     */
    private void handlerDepartmentMemberInfo(DepartmentMemberInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (result.getContent() != null) {
                    staffListView.setVisibility(View.VISIBLE);
                    mDepartmentManagerHelper.setCacheDepartmentMember(mDepartmentId, result.getContent());
                    mDepartmentStaffAdapter.setData(mDepartmentManagerHelper.getDepartmentMemberList(mDepartmentId));
                }
            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void intiView() {
        setRightText1(ResourceUtils.getString(R.string.btn_make_sure));
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确定保存数据
                if (mDepartmentManagerHelper.getSelectedDepartmentStaffs().size() > 0) {
                    Integer[] array = mDepartmentManagerHelper.getSelectedDepartmentStaffs().toArray(new Integer[0]);
                    Map map = new HashMap();
                    map.put(RequestConstant.KEY_STAFF_IDS, array);
                    map.put(RequestConstant.KEY_ATTENDANCE_RULE_ID, TextUtils.isEmpty(mRuleId) ? "0" : mRuleId);
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_CHECK_STAFF_ATTENDANCE_RULE, map);
                } else {
                    AlertUtils.show(ResourceUtils.getString(R.string.select_staff_before));
                }

            }
        });
        mSelectedMembers = new ArrayList<>();
        mSelectedMembers.addAll(getIntent().getIntegerArrayListExtra(INTENT_KEY_MEMBER_LIST));
        mRuleId = getIntent().getStringExtra(INTENT_KEY_RULE_ID);
        //已分组组织
        departments = new ArrayList<>();
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentManagerHelper.ClearCache();
        mDepartmentManagerHelper.setEditSelectedMembers(mSelectedMembers);
        mDepartmentListAdapter = new AttendanceDepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(SelectAttendanceMemberActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);
        mDepartmentListAdapter.setItemSelectorClickedListener(new AttendanceDepartmentListAdapter.ItemSelectorClickedInterface() {
            @Override
            public void onItemNextClicked(ChildDepartmentBean bean) {
                if (mDepartmentManagerHelper.getStaffTotalByDepartmentId(bean.getDepartmentId()) > 0) {
                    mDepartmentId = String.valueOf(bean.getDepartmentId());
                    mDepartmentName = bean.getDepartmentName();
                    mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
                    mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                    isSelectAll = false;
                    imageCheck.setSelected(isSelectAll);
                    upDateFromCache();
                } else {
                    AlertUtils.show("该部门下已无可选择员工");
                }

            }

            @Override
            public void onItemSelectorClicked(int position, ChildDepartmentBean bean) {
                bean.setSelected(!bean.isSelected());
                mDepartmentListAdapter.notifyItemChanged(position);
                mDepartmentManagerHelper.setDepartmentChildSelected(String.valueOf(bean.getDepartmentId()), bean.isSelected());
            }
        });

        //无分组员工
        staffs = new ArrayList<>();
        mDepartmentStaffAdapter = new AttendanceDepartmentStaffAdapter(this, staffs, false);
        staffListView.setHasFixedSize(true);
        staffListView.setLayoutManager(new LinearLayoutManager(SelectAttendanceMemberActivity.this));
        staffListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListView.setAdapter(mDepartmentStaffAdapter);
        mDepartmentStaffAdapter.setItemClickedListener(new AttendanceDepartmentStaffAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(int position, DepartmentMemberInfoBean bean) {
                bean.setSelected(!bean.isSelected());
                mDepartmentStaffAdapter.notifyItemChanged(position);
                mDepartmentManagerHelper.setDepartmentMemberSelected(String.valueOf(bean.getStaffId()), bean.isSelected());
            }
        });

        //顶部导航
        mDepartmentMembers = new ArrayList<>();
        mDepartmentMemberTitleAdapter = new DepartmentMemberTitleAdapter(mDepartmentMembers);
        departmentTitleList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(SelectAttendanceMemberActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        departmentTitleList.setLayoutManager(manager);
        departmentTitleList.setAdapter(mDepartmentMemberTitleAdapter);
        mDepartmentMemberTitleAdapter.setItemClickedListener(((position, bean) -> {
            mDepartmentId = bean.getDepartmentId();
            mDepartmentName = bean.getDepartmentName();
            mDepartmentMembers.subList(position + 1, mDepartmentMembers.size()).clear();
            mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
            isSelectAll = false;
            imageCheck.setSelected(isSelectAll);
            upDateFromCache();

        }));
    }

    @OnClick(R.id.image_check)
    public void onViewClicked() {
        isSelectAll = !isSelectAll;
        imageCheck.setSelected(isSelectAll);
        mDepartmentManagerHelper.setAllDepartmentSelected(mDepartmentId, isSelectAll);
        upDateFromCache();
    }

    private void upDateFromCache() {
        upDataDepartmentChildList();
        upDataDepartmentMemberList();
    }

    private void upDataDepartmentChildList() {
        departments = mDepartmentManagerHelper.getDepartmentChildList(mDepartmentId);
        if (departments != null) {
            mDepartmentListAdapter.setData(departments);
        }
    }

    private void upDataDepartmentMemberList() {
        staffs = mDepartmentManagerHelper.getDepartmentMemberList(mDepartmentId);
        if (staffs != null) {
            mDepartmentStaffAdapter.setData(staffs);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetAllDepartmentSubscription, mGetChildDepartmentSubscription,
                mDepartmentStaffSubscription, mCheckStaffSubscription, mEditMemberSubscription);
    }
}
