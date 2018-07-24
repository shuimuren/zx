package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ChildDepartmentBean;
import com.zhixing.work.zhixin.bean.DepartmentMemberBean;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.SelectedDepartmentEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AddDepartmentResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 添加子部门
 */
public class AddDepartmentActivity extends BaseTitleActivity {

    @BindView(R.id.department_name)
    TextView departmentName;
    @BindView(R.id.department_name_right)
    ImageView departmentNameRight;
    @BindView(R.id.rl_department_name)
    RelativeLayout rlDepartmentName;
    @BindView(R.id.superior_department)
    TextView superiorDepartment;
    @BindView(R.id.superior_department_right)
    ImageView superiorDepartmentRight;
    @BindView(R.id.rl_superior_department)
    RelativeLayout rlSuperiorDepartment;

    public static final String INTENT_KEY_PARENT_DEPARTMENT_NAME = "name";
    public static final String INTENT_KEY_DEPARTMENT_ID = "departmentId";
    private static final String INTENT_KEY_DEPARTMENT_NAME_LIST = "departmentNameList";
    private static final String INTENT_KEY_DEPARTMENT_LIST = "departmentList";

    private String mParentDepartmentId;
    private String mParentDepartmentName;
    private Subscription mAddDepartmentSubscription;
    private List<ChildDepartmentBean> mDepartments;
    private List<DepartmentMemberBean> mDepartmentMembers;

    public static void startAddDepartmentActivity(Activity activity, String departmentId, String departmentName,
                                                  List<ChildDepartmentBean> departments,
                                                  List<DepartmentMemberBean> departmentMembers) {
        Intent addIntent = new Intent(activity, AddDepartmentActivity.class);
        addIntent.putExtra(AddDepartmentActivity.INTENT_KEY_DEPARTMENT_ID, departmentId);
        addIntent.putExtra(AddDepartmentActivity.INTENT_KEY_PARENT_DEPARTMENT_NAME, departmentName);
        addIntent.putExtra(INTENT_KEY_DEPARTMENT_NAME_LIST, (Serializable) departments);
        addIntent.putExtra(INTENT_KEY_DEPARTMENT_LIST, (Serializable) departmentMembers);
        activity.startActivity(addIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        ButterKnife.bind(this);
        setTitle("添加子部门");
        setRightText1("完成");

        mParentDepartmentId = getIntent().getStringExtra(INTENT_KEY_DEPARTMENT_ID);
        mParentDepartmentName = getIntent().getStringExtra(INTENT_KEY_PARENT_DEPARTMENT_NAME);
        mDepartmentMembers = (List<DepartmentMemberBean>) getIntent().getSerializableExtra(INTENT_KEY_DEPARTMENT_NAME_LIST);
        mDepartments = (List<ChildDepartmentBean>) getIntent().getSerializableExtra(INTENT_KEY_DEPARTMENT_LIST);
        superiorDepartment.setText(mParentDepartmentName);
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(departmentName.getText().toString())) {
                    AlertUtils.toast(context, "部门名字不能为空");
                    return;
                }
                addDepartment(mParentDepartmentId, departmentName.getText().toString());
            }

        });

        mAddDepartmentSubscription = RxBus.getInstance().toObservable(AddDepartmentResult.class).subscribe(
                result -> handlerAddResult(result)
        );
    }

    private void handlerAddResult(AddDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.add_department_success));
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, mParentDepartmentId);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, mParentDepartmentId);
                this.finish();
            } else {
                AlertUtils.show(result.Message);
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    @OnClick({R.id.rl_department_name, R.id.rl_superior_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_department_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "部门名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.DEPARTMENT_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, departmentName.getText().toString()));
                break;
            case R.id.rl_superior_department:
                SelectionDepartmentActivity.startSelectionDepartmentActivity(AddDepartmentActivity.this, mDepartments, mDepartmentMembers);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ONModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.DEPARTMENT_NAME:
                departmentName.setText(event.getContent());
                break;
        }
    }

    private void addDepartment(String ParentId, String Name) {
        Map params = new HashMap();
        params.put(RequestConstant.KEY_PARENT_ID, ParentId);
        params.put(RequestConstant.KEY_NAME, Name);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_DEPARTMENT, params);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mAddDepartmentSubscription);
    }

    @Subscribe
    public void selectedDepartmentEvent(SelectedDepartmentEvent event){
        if(event != null && event.getDepartmentId() != null && event.getDepartmentName() != null){
            mParentDepartmentId = event.getDepartmentId();
            mParentDepartmentName = event.getDepartmentName();
            superiorDepartment.setText(mParentDepartmentName);
        }
    }
}