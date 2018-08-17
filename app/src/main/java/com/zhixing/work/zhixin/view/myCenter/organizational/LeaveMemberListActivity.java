package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseListActivity;
import com.zhixing.work.zhixin.bean.LeaveStaffBean;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.LeaveStaffListResult;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by lhj on 2018/8/10.
 * Description: 离职员工
 */

public class LeaveMemberListActivity extends BaseListActivity<LeaveStaffBean> {


    LinearLayout llEmptyView;
    private Subscription mStaffLeaveSubscription;

    // public static final String INTENT_KEY_AUDIT_STATUS = "auditStatus";

    public static void StartActivityLeaveMemberList(Activity activity) {
        Intent intent = new Intent(activity, LeaveMemberListActivity.class);
        activity.startActivity(intent);
    }

    //private int mStatus;

    @Override
    protected boolean addDecoration() {
        return true;
    }

    @Override
    protected void setContentViewLayout() {
        setContentView(R.layout.activity_new_member_list);
        llEmptyView = findViewById(R.id.ll_empty_view);
    }

    @Override
    protected void dispatchRequest() {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_PAGE_INDEX, mPages);
        map.put(RequestConstant.KEY_ROWS_COUNT, PAGE_SIZE);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_DIMISSION_LIST, map);
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    protected void initView() {
        setTitle("离职员工");
        mStaffLeaveSubscription = RxBus.getInstance().toObservable(LeaveStaffListResult.class).subscribe(
                result -> handlerMemberList(result)
        );
    }

    private void handlerMemberList(LeaveStaffListResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent().getDmsStaffs().size() > 0) {
                llEmptyView.setVisibility(View.GONE);
            } else {
                llEmptyView.setVisibility(View.VISIBLE);
            }
            onGetListSucceeded(result.getContent().getRowsCount(), result.getContent().getDmsStaffs());
        }
    }

    @Override
    public void onItemClicked(LeaveStaffBean bean) {
        super.onItemClicked(bean);
       // AuditDetailActivity.startAuditDetailActivity(this, bean.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mStaffLeaveSubscription);
    }

}
