package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.NewMemberInfoBean;
import com.zhixing.work.zhixin.event.HandlerApplyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.HandlerAuditResult;
import com.zhixing.work.zhixin.network.response.JoinDepartmentDetailResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/10.
 * Description: 审批详情
 */

public class AuditDetailActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_AUDIT_ID = "auditId";
    @BindView(R.id.img_user_avatar)
    ImageView imgUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.tv_apply_time)
    TextView tvApplyTime;
    @BindView(R.id.tv_apply_invite)
    TextView tvApplyInvite;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.btn_agree)
    Button btnAgree;

    public static void startAuditDetailActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, AuditDetailActivity.class);
        intent.putExtra(INTENT_KEY_AUDIT_ID, id);
        activity.startActivity(intent);
    }

    private String mUserId;
    private String mAttendanceRuleId;//考勤组Id

    private Subscription mApplyInfoSubscription;
    private Subscription mHandlerApplySubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_detail);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.member_audit_title));
        initView();
    }

    private void initView() {
        mUserId = getIntent().getStringExtra(INTENT_KEY_AUDIT_ID);
        if (TextUtils.isEmpty(mUserId)) {
            this.finish();
            return;
        }
        mApplyInfoSubscription = RxBus.getInstance().toObservable(JoinDepartmentDetailResult.class).subscribe(
                result -> handlerJoinDepartmentDetailResult(result)
        );

        mHandlerApplySubscription = RxBus.getInstance().toObservable(HandlerAuditResult.class).subscribe(
                result -> handlerResult(result)
        );
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_AUDIT_JOIN_DEPARTMENT_DETAIL, mUserId);
    }

    private void handlerResult(HandlerAuditResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            EventBus.getDefault().post(new HandlerApplyEvent(true));
            AlertUtils.show(ResourceUtils.getString(R.string.handler_success));
            this.finish();
        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.handler_fail) + result.Message);
        }
    }

    private void handlerJoinDepartmentDetailResult(JoinDepartmentDetailResult result) {

        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            NewMemberInfoBean bean = result.getContent();
            if (bean != null) {
                GlideUtils.getInstance().loadGlideRoundTransform(this, ResourceUtils.getDrawable(R.drawable.icon_avatar),
                        bean.getAvatar(), imgUserAvatar);
                tvUserName.setText(ZxTextUtils.getTextWithDefault(bean.getRealName()));
                tvUserPhone.setText(ZxTextUtils.getTextWithDefault(bean.getPhoneNum()));
                if (TextUtils.isEmpty(bean.getInviter())) {
                    tvManager.setVisibility(View.GONE);
                    tvApplyInvite.setText("申请入驻:");
                } else {
                    String inviter = bean.getInviter();
                    String inviterRole = bean.getInviterRole();
                    String des = "由 " + inviter + (TextUtils.isEmpty(inviterRole) ? " 管理员" : inviterRole);
                    tvApplyInvite.setVisibility(View.VISIBLE);
                    tvApplyInvite.setText(Utils.changeColor(des, ResourceUtils.getColor(R.color.color_71aae0), 1, inviter.length() + 2));
                    tvApplyInvite.setText("邀请入驻:");
                }
                tvApplyTime.setText(bean.getApplyTime());
                if(bean.getAttendanceRules() != null && bean.getAttendanceRules().size() > 0){
                    mAttendanceRuleId = String.valueOf(bean.getAttendanceRules().get(0).getId());
                    tvDepartment.setText(Utils.stringListToString(bean.getDepartmentName(), "-") + bean.getAttendanceRules().get(0).getName());
                }else{
                    mAttendanceRuleId = "";
                    tvDepartment.setText(Utils.stringListToString(bean.getDepartmentName(), "-"));
                }

            }

        }

    }

    @OnClick({R.id.btn_reject, R.id.btn_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reject:
                handlerApply(false);
                break;
            case R.id.btn_agree:
                handlerApply(true);
                break;
        }
    }

    private void handlerApply(boolean isAgree) {
        Map params = new HashMap();
        params.put(RequestConstant.KEY_ID, mUserId);
        params.put(RequestConstant.KEY_PASSED, isAgree ? "True" : "False");
        params.put(RequestConstant.KEY_ATTENDANCE_RULE_ID, mAttendanceRuleId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_HANDLER_JOIN_DEPARTMENT, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mApplyInfoSubscription, mHandlerApplySubscription);
    }
}

