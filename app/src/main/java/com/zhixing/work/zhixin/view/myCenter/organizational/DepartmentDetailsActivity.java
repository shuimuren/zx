package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.DepartmentListAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentMemberTitleAdapter;
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
import com.zhixing.work.zhixin.network.response.DeleteDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.network.response.UpdateChildDepartmentResult;
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

public class DepartmentDetailsActivity extends BaseTitleActivity {


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
    @BindView(R.id.add_user)
    TextView addUser;
    @BindView(R.id.add_department)
    TextView addDepartment;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.rl_add)
    LinearLayout rlAdd;

    private static final String INTENT_KEY_DEPARTMENT_NAME = "departmentName";
    private static final String INTENT_KEY_DEPARTMENT_ID = "departmentId";
    private static final String INTENT_KEY_PARENT_DEPARTMENT_NAME = "parentDepartmentName";
    private static final String INTENT_KEY_PARENT_DEPARTMENT_ID = "parentDepartmentName";

    private Subscription mGetAllDepartmentSubscription; //获取公司下所有员工
    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表

    private DepartmentListAdapter mDepartmentListAdapter; //部门
    private DepartmentStaffAdapter mDepartmentStaffAdapter; //人员
    private DepartmentMemberTitleAdapter mDepartmentMemberTitleAdapter; // 顶部title
    private Subscription mDeleteDepartmentSubscription;//删除子部门;
    private Subscription mUpdateSettingInfoSubscription;//更新子部门

    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberInfoBean> staffs;
    private List<DepartmentMemberBean> mDepartmentMembers;

    private String mDepartmentName;
    private String mDepartmentId;
    private String mParentDepartmentName;
    private String mParentDepartmentId;
    private String mCurrentParentDepartmentId;
    private DepartmentManagerHelper mDepartmentManagerHelper;


    public static void startDepartmentDetailActivity(Activity activity, String parentDepartmentName,
                                                     String parentDepartmentId, String departmentName,
                                                     String departmentId) {
        Intent intent = new Intent(activity, DepartmentDetailsActivity.class);
        intent.putExtra(INTENT_KEY_DEPARTMENT_NAME, departmentName);
        intent.putExtra(INTENT_KEY_DEPARTMENT_ID, departmentId);
        intent.putExtra(INTENT_KEY_PARENT_DEPARTMENT_ID, parentDepartmentId);
        intent.putExtra(INTENT_KEY_PARENT_DEPARTMENT_NAME, parentDepartmentName);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmentetails);
        ButterKnife.bind(this);
        setTitle("管理部门和成员");
        intiView();
        registerRequest();
        getIntentData();
    }

    public void getIntentData() {
        Intent intent = getIntent();
        mDepartmentId = intent.getStringExtra(INTENT_KEY_DEPARTMENT_ID);
        mDepartmentName = intent.getStringExtra(INTENT_KEY_DEPARTMENT_NAME);
        mParentDepartmentId = intent.getStringExtra(INTENT_KEY_PARENT_DEPARTMENT_ID);
        mParentDepartmentName = intent.getStringExtra(INTENT_KEY_PARENT_DEPARTMENT_NAME);
        mCurrentParentDepartmentId = mParentDepartmentId;
        mDepartmentMembers.add(new DepartmentMemberBean(mParentDepartmentId, mParentDepartmentName));
        mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
        //  mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
        upDateDepartmentAndStaff(mDepartmentId);
    }

    private void intiView() {
        departments = new ArrayList<>();
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentListAdapter = new DepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(DepartmentDetailsActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);
        mDepartmentListAdapter.setItemClickedListener(new DepartmentListAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(ChildDepartmentBean bean) {
                Logger.i(">>>", "点击组织");
                mCurrentParentDepartmentId = String.valueOf(bean.getDepartmentId());
                mDepartmentId = String.valueOf(bean.getDepartmentId());
                mDepartmentName = bean.getDepartmentName();
                mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
                mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                upDateDepartmentAndStaff(mDepartmentId);
            }
        });

        staffs = new ArrayList<>();
        mDepartmentStaffAdapter = new DepartmentStaffAdapter(this, staffs, false);
        staffListView.setHasFixedSize(true);
        staffListView.setLayoutManager(new LinearLayoutManager(DepartmentDetailsActivity.this));
        staffListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        staffListView.setAdapter(mDepartmentStaffAdapter);
        mDepartmentStaffAdapter.setItemClickedListener(new DepartmentStaffAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(DepartmentMemberInfoBean bean) {
                Logger.i(">>>", "点击个人");
            }
        });

        mDepartmentMembers = new ArrayList<>();
        mDepartmentMemberTitleAdapter = new DepartmentMemberTitleAdapter(mDepartmentMembers);
        // departmentTitleList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(DepartmentDetailsActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        departmentTitleList.setLayoutManager(manager);
        departmentTitleList.setAdapter(mDepartmentMemberTitleAdapter);
        mDepartmentMemberTitleAdapter.setItemClickedListener(((position, bean) -> {
            Logger.i(">>>", "点击了Title");
            if (position == 0) {
                this.finish();
            } else {
                mCurrentParentDepartmentId = mDepartmentMembers.get(position - 1).getDepartmentId();
                mDepartmentId = bean.getDepartmentId();
                mDepartmentName = bean.getDepartmentName();
                mDepartmentMembers.subList(position + 1, mDepartmentMembers.size()).clear();
                mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                upDateDepartmentAndStaff(mDepartmentId);
            }

        }));
        departmentTitleList.scrollToPosition(mDepartmentMembers.size());

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

        mDeleteDepartmentSubscription = RxBus.getInstance().toObservable(DeleteDepartmentResult.class).subscribe(
                result -> handlerDeleteChildDepartment(result)
        );

        mUpdateSettingInfoSubscription = RxBus.getInstance().toObservable(UpdateChildDepartmentResult.class).subscribe(
                result -> handlerUpdateChildDepartment(result)
        );
        //   MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER);

    }

    private void handlerUpdateChildDepartment(UpdateChildDepartmentResult result) {
//        mCurrentParentDepartmentId = String.valueOf(bean.getDepartmentId());
//        mDepartmentId = String.valueOf(bean.getDepartmentId());
//        mDepartmentName = bean.getDepartmentName();
//        mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
//        mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
        upDateDepartmentAndStaff(mDepartmentId);
    }

    private void handlerDeleteChildDepartment(DeleteDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE && result.isContent()) {
            int mDepartmentMemberSize = mDepartmentMembers.size();
            if (mDepartmentMemberSize > 2) {
                mCurrentParentDepartmentId = mDepartmentMembers.get(mDepartmentMemberSize - 2).getDepartmentId();
                mDepartmentId = mDepartmentMembers.get(mDepartmentMemberSize - 2).getDepartmentId();
                mDepartmentName = mDepartmentMembers.get(mDepartmentMemberSize - 2).getDepartmentName();
                mDepartmentMembers.remove(mDepartmentMemberSize - 1);
                mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                upDateDepartmentAndStaff(mDepartmentId);
            }
        }

    }


    //刷新部门
    private void upDateDepartmentAndStaff(String departmentId) {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, departmentId);
    }


    private void handlerAllDepartmentMember(AllDepartmentMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {

        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
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
            if (result.getDepartmentId().equals(mDepartmentId)) {
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


    @OnClick({R.id.add_user, R.id.add_department, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                Map<String, Object> params = new HashMap<>();
                //此处应填写完整参数
                params.put(ShareConstant.PARAM_SHARE_TITLE, "职信科技有限公司");
                params.put(ShareConstant.PARAMS_CONTEXT, DepartmentDetailsActivity.this);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SHOW_SHARE_PLATFORM, params);
                break;
            case R.id.add_department:
                AddDepartmentActivity.startAddDepartmentActivity(DepartmentDetailsActivity.this,
                        mDepartmentId, mDepartmentName, departments, mDepartmentMembers);
                break;
            case R.id.setting:
                DepartmentSettingActivity.startDepartmentSetting(DepartmentDetailsActivity.this, mCurrentParentDepartmentId, mDepartmentId);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetAllDepartmentSubscription, mGetChildDepartmentSubscription,
                mDepartmentStaffSubscription, mDeleteDepartmentSubscription, mUpdateSettingInfoSubscription);
    }


}
