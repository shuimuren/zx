package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.SelectionDepartmentAdapter;
import com.zhixing.work.zhixin.adapter.SelectionDepartmentMemberAdapter;
import com.zhixing.work.zhixin.adapter.SelectionDepartmentSelectedAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberInfoBean;
import com.zhixing.work.zhixin.bean.SelectedDepartmentBean;
import com.zhixing.work.zhixin.common.DepartmentManagerHelper;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/23.
 * Description: 部门设置选择联系人可见
 */

public class SelectionDepartmentMemberActivity extends BaseTitleActivity {

    @BindView(R.id.top)
    View top;
    @BindView(R.id.tvSearchOrCancel)
    TextView tvSearchOrCancel;
    @BindView(R.id.img_all)
    ImageView imgAll;
    @BindView(R.id.recycler_selected)
    RecyclerView recyclerSelected;
    @BindView(R.id.recycler_department)
    RecyclerView recyclerDepartment;
    @BindView(R.id.recycler_member)
    RecyclerView recyclerMember;

    private SelectionDepartmentSelectedAdapter selectedAdapter;
    private SelectionDepartmentMemberAdapter memberAdapter;
    private SelectionDepartmentAdapter departmentAdapter;

    private List<DepartmentMemberInfoBean> staffs;
    private List<ChildDepartmentBean> mDepartments;
    private List<SelectedDepartmentBean> selectedDepartmentBeans;

    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mDepartmentStaffSubscription;//获取部门下员工列表

    private DepartmentManagerHelper mDepartmentManagerHelper;

    private String mDepartmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_department_member);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.choice_member));
        initView();
        registerRequest();
        getData(mDepartmentId);
    }

    private void initView() {
        mDepartmentManagerHelper = DepartmentManagerHelper.getInstance();
        mDepartmentId = String.valueOf(SettingUtils.getTokenBean().getDepartmentId());

        selectedDepartmentBeans = new ArrayList<>();
        selectedAdapter = new SelectionDepartmentSelectedAdapter(SelectionDepartmentMemberActivity.this, selectedDepartmentBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectionDepartmentMemberActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerSelected.setLayoutManager(layoutManager);

        recyclerSelected.setAdapter(selectedAdapter);
        selectedAdapter.setItemClickedListener(new SelectionDepartmentSelectedAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(SelectedDepartmentBean bean) {


            }

            @Override
            public void onDeleteClicked(SelectedDepartmentBean bean) {

            }
        });

        mDepartments = new ArrayList<>();
        departmentAdapter = new SelectionDepartmentAdapter(mDepartments);
        recyclerDepartment.setLayoutManager(new LinearLayoutManager(SelectionDepartmentMemberActivity.this));
        recyclerDepartment.setAdapter(departmentAdapter);
        recyclerDepartment.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentAdapter.setItemSelectorClickedListener(new SelectionDepartmentAdapter.ItemSelectorClickedInterface() {
            @Override
            public void onItemNextClicked(ChildDepartmentBean bean) {
                mDepartmentId = String.valueOf(bean.getDepartmentId());
                getData(mDepartmentId);
            }

            @Override
            public void onItemSelectorClicked(int position, ChildDepartmentBean bean) {
                if (bean.isSelected()) {
                    for (int i = 0; i < selectedDepartmentBeans.size(); i++) {
                        if (selectedDepartmentBeans.get(i).getDepartmentId() == bean.getDepartmentId()) {
                            selectedDepartmentBeans.remove(i);
                        }
                    }

                    selectedAdapter.setData(selectedDepartmentBeans);
                } else {
                    SelectedDepartmentBean departmentBean = new SelectedDepartmentBean(bean.getDepartmentId(), bean.getDepartmentName(),
                            false, "", "");
                    selectedDepartmentBeans.add(departmentBean);
                    selectedAdapter.setData(selectedDepartmentBeans);
                }
                bean.setSelected(!bean.isSelected());
                Logger.i(">>>", "departmentSize>" + mDepartments.size());
                departmentAdapter.setData(mDepartments);
            }
        });

        staffs = new ArrayList<>();
        memberAdapter = new SelectionDepartmentMemberAdapter(SelectionDepartmentMemberActivity.this, staffs);
        recyclerMember.setLayoutManager(new LinearLayoutManager(SelectionDepartmentMemberActivity.this));
        recyclerMember.setAdapter(memberAdapter);
        recyclerMember.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        memberAdapter.setItemClickedListener(new SelectionDepartmentMemberAdapter.ItemClickedInterface() {
            @Override
            public void onItemClicked(int position, DepartmentMemberInfoBean bean) {
                if (bean.isSelected()) {
                    for (int i = 0; i < selectedDepartmentBeans.size(); i++) {
                        if (!TextUtils.isEmpty(selectedDepartmentBeans.get(i).getUserId())
                                &&Integer.parseInt(selectedDepartmentBeans.get(i).getUserId()) == bean.getStaffId()) {
                            selectedDepartmentBeans.remove(i);
                        }
                    }
                    selectedAdapter.setData(selectedDepartmentBeans);
                } else {
                    SelectedDepartmentBean departmentBean = new SelectedDepartmentBean(-1, "",
                            true, bean.getStaffAvatar(), String.valueOf(bean.getStaffId()));
                    selectedDepartmentBeans.add(departmentBean);
                    selectedAdapter.setData(selectedDepartmentBeans);
                }
                bean.setSelected(!bean.isSelected());
                memberAdapter.setData(staffs);
            }
        });

    }

    private void getData(String departmentId) {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, departmentId);
    }

    private void registerRequest() {
        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result));
        mDepartmentStaffSubscription = RxBus.getInstance().toObservable(DepartmentMemberInfoResult.class).subscribe(
                result -> handlerDepartmentMemberInfo(result));
    }

    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                mDepartments = result.getContent().getSubDepartments();
                for (int i = 0; i < mDepartments.size(); i++) {
                    mDepartments.get(i).setMemberTotal(mDepartmentManagerHelper.getStaffTotalByDepartmentId(mDepartments.get(i).getDepartmentId()));
                }
                departmentAdapter.setData(mDepartments);

            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    private void handlerDepartmentMemberInfo(DepartmentMemberInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (result.getContent() != null) {
                    staffs = result.getContent();
                    memberAdapter.setData(staffs);
                }

            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetChildDepartmentSubscription, mDepartmentStaffSubscription);
    }

    @OnClick(R.id.img_all)
    public void onViewClicked() {

    }
}
