package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.DepartmentListAdapter;
import com.zhixing.work.zhixin.adapter.DepartmentMemberTitleAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;
import com.zhixing.work.zhixin.dialog.NewDepartmentDialog;
import com.zhixing.work.zhixin.event.SelectedDepartmentEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AddDepartmentResult;
import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

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
 * 选择上级部分
 */
public class SelectionDepartmentActivity extends BaseTitleActivity {


    @BindView(R.id.departmentTitleList)
    RecyclerView departmentTitleList;
    @BindView(R.id.tv_new_add_department)
    TextView tvNewAddDepartment;
    @BindView(R.id.department_list)
    RecyclerView departmentList;
    @BindView(R.id.ll_list_empty)
    LinearLayout llListEmpty;

    private static final String INTENT_KEY_DEPARTMENT_NAME_LIST = "departmentNameList";
    private static final String INTENT_KEY_DEPARTMENT_LIST = "departmentList";


    private Subscription mGetChildDepartmentSubscription; //获取子部门
    private Subscription mNewAddDepartmentSubscription; //新建子部门


    private DepartmentListAdapter mDepartmentListAdapter; //部门
    private DepartmentMemberTitleAdapter mDepartmentMemberTitleAdapter; // 顶部title


    private List<ChildDepartmentBean> departments;
    private List<DepartmentMemberBean> mDepartmentMembers;

    private String mDepartmentName;
    private String mDepartmentId;
    //    private String mParentDepartmentName;
//    private String mParentDepartmentId;
//    private String mCurrentParentDepartmentId;
    private NewDepartmentDialog newDepartmentDialog;
    private int mCurrentSelectdPosition;
    private ChildDepartmentBean mSelectedDepartment;


    public static void startSelectionDepartmentActivity(Activity activity, List<ChildDepartmentBean> departments,
                                                        List<DepartmentMemberBean> departmentMembers) {
        Intent intent = new Intent(activity, SelectionDepartmentActivity.class);
        intent.putExtra(INTENT_KEY_DEPARTMENT_NAME_LIST, (Serializable) departments);
        intent.putExtra(INTENT_KEY_DEPARTMENT_LIST, (Serializable) departmentMembers);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_department);
        ButterKnife.bind(this);
        setTitle("选择上级部门");
        intiView();
        registerRequest();
        getIntentData();

    }


    public void getIntentData() {
        Intent intent = getIntent();
        departments = (List<ChildDepartmentBean>) intent.getSerializableExtra(INTENT_KEY_DEPARTMENT_LIST);
        mDepartmentMembers = (List<DepartmentMemberBean>) intent.getSerializableExtra(INTENT_KEY_DEPARTMENT_NAME_LIST);
        mDepartmentListAdapter.setData(departments);
        mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
        if (departments.size() > 0) {
            llListEmpty.setVisibility(View.GONE);
            for (int i = 0; i < departments.size(); i++) {
                departments.get(i).setSelected(false);
                departments.get(i).setTypeSelected(1);
            }
        } else {
            llListEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void intiView() {
        departments = new ArrayList<>();
        mDepartmentListAdapter = new DepartmentListAdapter(departments);
        departmentList.setHasFixedSize(true);
        departmentList.setLayoutManager(new LinearLayoutManager(SelectionDepartmentActivity.this));
        departmentList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        departmentList.setAdapter(mDepartmentListAdapter);

        mDepartmentListAdapter.setItemSelectorClickedListener(new DepartmentListAdapter.ItemSelectorClickedInterface() {

            @Override
            public void onItemNextClicked(ChildDepartmentBean bean) {
                mDepartmentId = String.valueOf(bean.getDepartmentId());
                mDepartmentName = bean.getDepartmentName();
                mDepartmentMembers.add(new DepartmentMemberBean(mDepartmentId, mDepartmentName));
                mDepartmentMemberTitleAdapter.setData(mDepartmentMembers);
                upDateDepartmentAndStaff(mDepartmentId);
            }

            @Override
            public void onItemSelectorClicked(int position, ChildDepartmentBean bean) {

                if (!bean.isSelected()) {
                    mSelectedDepartment = bean;
                    for (int i = 0; i < departments.size(); i++) {
                        departments.get(i).setSelected(false);
                    }
                    bean.setSelected(true);
                } else {
                    for (int i = 0; i < departments.size(); i++) {
                        departments.get(i).setSelected(false);
                    }
                    mSelectedDepartment = null;
                }

                mDepartmentListAdapter.notifyDataSetChanged();

            }
        });


        mDepartmentMembers = new ArrayList<>();
        mDepartmentMemberTitleAdapter = new DepartmentMemberTitleAdapter(mDepartmentMembers);
        departmentTitleList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(SelectionDepartmentActivity.this);
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
        departmentTitleList.scrollToPosition(mDepartmentMembers.size());

        setRightText1("确定");
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedDepartment != null){
                    EventBus.getDefault().post(new SelectedDepartmentEvent(String.valueOf(mSelectedDepartment.getDepartmentId()),mSelectedDepartment.getDepartmentName()));
                }
                SelectionDepartmentActivity.this.finish();
            }
        });
    }

    private void registerRequest() {

        mGetChildDepartmentSubscription = RxBus.getInstance().toObservable(ChildDepartmentResult.class).subscribe(
                result -> handlerChildDepartment(result)

        );

        mNewAddDepartmentSubscription = RxBus.getInstance().toObservable(AddDepartmentResult.class).subscribe(
                result -> handlerNewAddDepartment(result)
        );

    }

    private void handlerNewAddDepartment(AddDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.new_add_department_success));
                upDateDepartmentAndStaff(mDepartmentId);
            } else {
                AlertUtils.show(result.Message);
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }


    //刷新部门
    private void upDateDepartmentAndStaff(String departmentId) {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, departmentId);

    }


    private void handlerChildDepartment(ChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getDepartmentId().equals(mDepartmentId)) {
                if (result.getContent().getSubDepartments() != null) {
                    departments = result.getContent().getSubDepartments();
                    if (departments.size() > 0) {
                        llListEmpty.setVisibility(View.GONE);
                    } else {
                        llListEmpty.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < departments.size(); i++) {
                        departments.get(i).setTypeSelected(1);
                        if (mSelectedDepartment != null && mSelectedDepartment.getDepartmentId() == departments.get(i).getDepartmentId()) {
                            departments.get(i).setSelected(true);
                        } else {
                            departments.get(i).setSelected(false);
                        }
                    }
                    mDepartmentListAdapter.setData(departments);

                }

            }
        } else {
            AlertUtils.show(result.Message);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetChildDepartmentSubscription, mNewAddDepartmentSubscription);
    }


    @OnClick(R.id.tv_new_add_department)
    public void onViewClicked() {
        newDepartmentDialog = new NewDepartmentDialog(context, new NewDepartmentDialog.OnItemClickListener() {
            @Override
            public void OnItemClick(int index, Dialog dialog) {

                switch (index) {
                    case NewDepartmentDialog.TYPE_OK:
                        String title = newDepartmentDialog.ed_text.getText().toString();
                        if (TextUtils.isEmpty(title)) {
                            AlertUtils.toast(context, "部门名字不能为空");
                            return;
                        }
                        addDepartment(mDepartmentId, title);
                        newDepartmentDialog.dismiss();

                        break;
                }
            }
        });
        newDepartmentDialog.show();
    }

    private void addDepartment(String ParentId, String Name) {
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PARENT_ID, ParentId);
        params.put(RequestConstant.KEY_NAME, Name);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_DEPARTMENT, params);

    }

}
