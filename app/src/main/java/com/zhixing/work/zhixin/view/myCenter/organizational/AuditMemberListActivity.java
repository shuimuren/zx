package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseListActivity;
import com.zhixing.work.zhixin.bean.NewMemberBean;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.event.HandlerApplyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.AuditMemberResult;
import com.zhixing.work.zhixin.util.ResourceUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/10.
 * Description: 审核列表
 */

public class AuditMemberListActivity extends BaseListActivity<NewMemberBean> {


    LinearLayout llEmptyView;
    private Subscription mJoinDepartmentSubscription;

    public static final String INTENT_KEY_AUDIT_STATUS = "auditStatus";

    public static void StartActivityAuditMemberList(Activity activity, int status) {
        Intent intent = new Intent(activity, AuditMemberListActivity.class);
        intent.putExtra(INTENT_KEY_AUDIT_STATUS, status);
        activity.startActivity(intent);
    }

    private int mStatus;

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
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_AUDIT_JOIN_DEPARTMENT_LIST, mStatus);
    }

    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    protected void initView() {
        mStatus = getIntent().getIntExtra(INTENT_KEY_AUDIT_STATUS, 0);
        switch (mStatus) {
            case ResultConstant.AUDIT_STATUS_WAITING:
                setTitle(ResourceUtils.getString(R.string.new_member_title));
                break;
            case ResultConstant.AUDIT_STATUS_PASS:
                setTitle("");
                break;
            case ResultConstant.AUDIT_STATUS_REJECT:
                setTitle("");
                break;
        }
        mJoinDepartmentSubscription = RxBus.getInstance().toObservable(AuditMemberResult.class).subscribe(
                result -> handlerMemberList(result)
        );
    }

    private void handlerMemberList(AuditMemberResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent().size() > 0) {
                llEmptyView.setVisibility(View.GONE);
            } else {
                llEmptyView.setVisibility(View.VISIBLE);
            }
            onGetListSucceeded(1, result.getContent());
        }
    }

    @Override
    public void onItemClicked(NewMemberBean bean) {
        super.onItemClicked(bean);
        AuditDetailActivity.startAuditDetailActivity(this, bean.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mJoinDepartmentSubscription);
    }

    @Subscribe
    public void handlerEvent(HandlerApplyEvent event) {
        dispatchRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_finish)
    public void onViewClicked() {
        this.finish();
    }
}
