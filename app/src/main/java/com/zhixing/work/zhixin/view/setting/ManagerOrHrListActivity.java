package com.zhixing.work.zhixin.view.setting;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseListActivity;
import com.zhixing.work.zhixin.bean.HrBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.CompanyManagerListResult;
import com.zhixing.work.zhixin.network.response.SettingRoleResult;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.myCenter.organizational.SelectionDepartmentManagerActivity;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by lhj on 2018/8/22.
 * Description: 管理员或Hr列表
 */

public class ManagerOrHrListActivity extends BaseListActivity<HrBean> {

    LinearLayout llListEmpty;
    private Subscription mCompanyManagerListSubscription;
    private Subscription mSetUserRoleSubscription;
    private String mStaffRole;
    private DeleteDialog deleteDialog;

    @Override
    protected boolean addDecoration() {
        return true;
    }

    @Override
    protected void setContentViewLayout() {
        setContentView(R.layout.activity_manager_or_hr_list);
        llListEmpty = findViewById(R.id.ll_list_empty);
        mStaffRole = String.valueOf(getIntent().getIntExtra(ManagerSettingActivity.STAFF_ROLE,1));
    }

    @Override
    protected void dispatchRequest() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_MANAGER_LIST,mStaffRole);
    }


    @Override
    protected void initView() {
        int role = getIntent().getIntExtra(ManagerSettingActivity.STAFF_ROLE,1);
        mStaffRole = String.valueOf(role);
        if(role == ResultConstant.USER_STAFF_ROLE_HR){
            setTitle(ResourceUtils.getString(R.string.setting_hr_title));
        }else if(role == ResultConstant.USER_STAFF_ROLE_MANAGER){
            setTitle(ResourceUtils.getString(R.string.setting_manager_title));
        }
        setRightText1(ResourceUtils.getString(R.string.add));
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent managerIntent = new Intent(ManagerOrHrListActivity.this,SelectionDepartmentManagerActivity.class);
                managerIntent.putExtra(ManagerSettingActivity.STAFF_ROLE, role);
                startActivity(managerIntent);
            }
        });

        mCompanyManagerListSubscription = RxBus.getInstance().toObservable(CompanyManagerListResult.class).subscribe(
                result -> handlerManagerListResult(result)
        );

        mSetUserRoleSubscription = RxBus.getInstance().toObservable(SettingRoleResult.class).subscribe(
                result -> handlerSettingResult(result)
        );
    }

    private void handlerSettingResult(SettingRoleResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            if(result.isContent()){
                dispatchRequest();
            }
        }
    }

    private void handlerManagerListResult(CompanyManagerListResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            if(result.getContent() != null && result.getContent().size()>0){
                llListEmpty.setVisibility(View.GONE);
                onGetListSucceeded(1,result.getContent());
            }else{
                llListEmpty.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public void onDeleteClicked(HrBean bean) {
        if(!TextUtils.isEmpty(bean.getStaffId()) && !TextUtils.isEmpty(bean.getStaffRole())){
            deleteDialog = new DeleteDialog(context, "是否删除该成员", "", new DeleteDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(int index, Dialog dialog) {
                    if (index == 1) {
                        setUserRole(bean.getStaffId(),bean.getStaffRole());
                    }
                    deleteDialog.dismiss();

                }
            });
            deleteDialog.show();


        }

    }

    private void setUserRole(String staffId,String staffRole){
            Map map = new HashMap();
            map.put(RequestConstant.KEY_STAFF_ID,staffId);
            map.put(RequestConstant.KEY_STAFF_ROLE,staffRole);
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_SET_USER_ROLE,map);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mCompanyManagerListSubscription,mSetUserRoleSubscription);
    }
}
