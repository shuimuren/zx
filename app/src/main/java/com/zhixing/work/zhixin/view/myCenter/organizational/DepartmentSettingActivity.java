package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.DeleteDepartmentResult;
import com.zhixing.work.zhixin.network.response.DepartmentSettingInfoResult;
import com.zhixing.work.zhixin.network.response.UpdateChildDepartmentResult;
import com.zhixing.work.zhixin.requestbody.DepartmentBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class DepartmentSettingActivity extends BaseTitleActivity {

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
    @BindView(R.id.department_director)
    TextView departmentDirector;
    @BindView(R.id.department_director_right)
    ImageView departmentDirectorRight;
    @BindView(R.id.rl_department_director)
    RelativeLayout rlDepartmentDirector;
    @BindView(R.id.is_hide)
    CheckBox isHide;
    @BindView(R.id.ll_hide)
    LinearLayout llHide;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.ll_delete)
    RelativeLayout llDelete;


    private static final String INTENT_KEY_DEPARTMENT_ID = "departmentId";
    private static final String INTENT_KEY_PARENT_DEPARTMENT_ID = "parentDepartmentId";

    private DeleteDialog deleteDialog;
    private String mDepartmentId;
    private String mParentDepartmentId; //暂不用
    private Subscription mGetSettingInfoSubscription;
    private Subscription mUpdateSettingInfoSubscription;
    private Subscription mDeleteDepartmentSubscription;
    private Integer mManagerId;

    public static void startDepartmentSetting(Activity activity, String parentDepartmentId, String departmentId) {
        Intent intent = new Intent(activity, DepartmentSettingActivity.class);
        intent.putExtra(INTENT_KEY_DEPARTMENT_ID, departmentId);
        intent.putExtra(INTENT_KEY_PARENT_DEPARTMENT_ID, parentDepartmentId);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_setting);
        ButterKnife.bind(this);
        setTitle("部门设置");
        setRightText1("完成");
        initView();
    }

    private void initView() {
        mDepartmentId = getIntent().getStringExtra(INTENT_KEY_DEPARTMENT_ID);
        //     mParentDepartmentId = getIntent().getStringExtra(INTENT_KEY_PARENT_DEPARTMENT_ID);

        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepartmentBody departmentBody = new DepartmentBody();
                departmentBody.setId(Integer.parseInt(mDepartmentId));
                departmentBody.setParentId(Integer.parseInt(mParentDepartmentId));
                departmentBody.setName(departmentName.getText().toString());
                departmentBody.setHide(isHide.isChecked());
                departmentBody.setManagerId(mManagerId);
                List<Integer> departmentId = new ArrayList<>();
                departmentBody.setDepartmentIds(departmentId);
                List<Integer> staffId = new ArrayList<>();
                departmentBody.setStaffIds(staffId);
                Map params = new HashMap();
                params.put(RequestConstant.KEY_REQUEST_BODY, departmentBody);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_UPDATE_CHILD_DEPARTMENT, params);

            }
        });

        isHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rlSetting.setVisibility(View.VISIBLE);
                } else {
                    rlSetting.setVisibility(View.GONE);
                }
            }
        });
        mGetSettingInfoSubscription = RxBus.getInstance().toObservable(DepartmentSettingInfoResult.class).subscribe(
                result -> handlerDepartmentSettingInfoResult(result)
        );

        mUpdateSettingInfoSubscription = RxBus.getInstance().toObservable(UpdateChildDepartmentResult.class).subscribe(
                result -> handlerUpdateChildDepartment(result)
        );

        mDeleteDepartmentSubscription = RxBus.getInstance().toObservable(DeleteDepartmentResult.class).subscribe(
                result -> handlerDeleteChildDepartment(result)
        );

        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_DEPARTMENT_SETTING_INFO, mDepartmentId);


    }

    private void handlerDeleteChildDepartment(DeleteDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.delete_success));
                this.finish();
            }
        }

    }

    private void handlerUpdateChildDepartment(UpdateChildDepartmentResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT, mDepartmentId);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER, mDepartmentId);
                AlertUtils.show(ResourceUtils.getString(R.string.update_success));
                this.finish();
            }

        } else {
            AlertUtils.show(result.Message);
        }
    }

    private void handlerDepartmentSettingInfoResult(DepartmentSettingInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mParentDepartmentId = String.valueOf(result.getContent().getParentId());
            departmentName.setText(ZxTextUtils.getTextWithDefault(result.getContent().getName()));
            superiorDepartment.setText(ZxTextUtils.getTextWithDefault(result.getContent().getParentName()));
            departmentDirector.setText(ZxTextUtils.getTextNotNull(result.getContent().getManagerName()));
            mManagerId = result.getContent().getManagerId();
            if (result.getContent().isHide()) {
                isHide.setChecked(true);
                rlSetting.setVisibility(View.VISIBLE);
            } else {
                rlSetting.setVisibility(View.GONE);
                isHide.setChecked(false);
            }
        } else {
            AlertUtils.show(result.Message);
        }

    }

    @OnClick({R.id.rl_department_name, R.id.rl_superior_department, R.id.rl_department_director, R.id.is_hide, R.id.rl_setting, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_department_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "部门名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.DEPARTMENT_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, departmentName.getText().toString()));

                break;
            case R.id.rl_superior_department:
                break;
            case R.id.rl_department_director:
                AlertUtils.show("选择部门主管");
                break;
            case R.id.is_hide:
                break;
            case R.id.rl_setting:
                startActivity(new Intent(this,SelectionDepartmentMemberActivity.class));
                break;
            case R.id.delete:
                deleteDialog = new DeleteDialog(context, "是否删除此部门", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {
                            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_DELETE_DEPARTMENT, mDepartmentId);
                        }
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mGetSettingInfoSubscription, mUpdateSettingInfoSubscription, mDeleteDepartmentSubscription);
    }
}
