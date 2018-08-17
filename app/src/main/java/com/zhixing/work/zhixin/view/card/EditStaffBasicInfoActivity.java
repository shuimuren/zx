package com.zhixing.work.zhixin.view.card;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.JobCardBasicInfoBean;
import com.zhixing.work.zhixin.event.RefreshStaffInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.EditBasicInfoResult;
import com.zhixing.work.zhixin.requestbody.StaffJobBasicBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/14.
 * Description: 编辑员工个人基础信息
 */

public class EditStaffBasicInfoActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_STAFF_ID = "staffBean";
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.edit_emil)
    ClearEditText editEmil;
    @BindView(R.id.tv_card_department)
    TextView tvCardDepartment;
    @BindView(R.id.edit_position)
    ClearEditText editPosition;
    @BindView(R.id.edit_telephone)
    ClearEditText editTelephone;
    @BindView(R.id.edit_card_code)
    ClearEditText editCardCode;
    @BindView(R.id.edit_company_telephone)
    ClearEditText editCompanyTelephone;
    @BindView(R.id.edit_work_place)
    ClearEditText editWorkPlace;
    @BindView(R.id.ll_basic)
    LinearLayout llBasic;

    private Subscription mEditStaffBasicInfoSubscription;

    private JobCardBasicInfoBean mBasicInfoBean;
    private String mStaffName, mStaffEmil, mStaffPosition, mStaffTelephone, mStaffCardCode,
            mCompanyTelephone, mStaffWorkPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_basic_info);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.basic_info_title));
        setRightText1(ResourceUtils.getString(R.string.save));
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSaveEdit();
            }
        });
        initViewData();
    }

    private void doSaveEdit() {
        StaffJobBasicBody basicBody = new StaffJobBasicBody();
        mStaffName = tvCardName.getText().toString();
        mStaffEmil = editEmil.getText().toString();
        mStaffPosition = editPosition.getText().toString();
        mStaffTelephone = editTelephone.getText().toString();
        mStaffCardCode = editCardCode.getText().toString();
        mCompanyTelephone = editCompanyTelephone.getText().toString();
        mStaffWorkPlace = editWorkPlace.getText().toString();
        if (!TextUtils.isEmpty(mCompanyTelephone)) {
            basicBody.setExtPhoneNum(mCompanyTelephone);
        }
        if (!TextUtils.isEmpty(mStaffEmil)) {
            if (Utils.isEmail(mStaffEmil)) {
                basicBody.setWorkEmail(mStaffEmil);
            } else {
                AlertUtils.show(ResourceUtils.getString(R.string.alert_telephone));
                return;
            }
        }

        basicBody.setJobType(mStaffPosition);
        basicBody.setOfficeAddress(mStaffWorkPlace);
        basicBody.setRealName(mStaffName);
        basicBody.setStaffId(String.valueOf(mBasicInfoBean.getStaffId()));

        if (!TextUtils.isEmpty(mStaffTelephone)) {
            if (Utils.isMobileNO(mStaffTelephone)) {
                basicBody.setWorkPhoneNum(mStaffTelephone);
            } else {
                AlertUtils.show(ResourceUtils.getString(R.string.alert_telephone));
                return;
            }

        }
        basicBody.setWorkNumber(mStaffCardCode);
        Map map = new HashMap();

        map.put(RequestConstant.KEY_REQUEST_BODY, basicBody);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_COMPANY_STAFF_JOB_BASE, map);
    }

    private void initViewData() {
        mBasicInfoBean = (JobCardBasicInfoBean) getIntent().getSerializableExtra(INTENT_KEY_STAFF_ID);
        if (mBasicInfoBean != null) {
            tvCardName.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getRealName()));
            editEmil.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getWorkEmail()));

            if (mBasicInfoBean.getDepartments() != null && mBasicInfoBean.getDepartments().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < mBasicInfoBean.getDepartments().size(); i++) {
                    stringBuilder.append(Utils.stringListToString(mBasicInfoBean.getDepartments().get(i), "-"));
                    if (i != mBasicInfoBean.getDepartments().size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                tvCardDepartment.setText(ZxTextUtils.getTextWithDefault(stringBuilder.toString()));
            }

            editPosition.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getJobType()));
            editTelephone.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getWorkPhoneNum()));
            editCardCode.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getWorkNumber()));
            editCompanyTelephone.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getExtPhoneNum()));
            editWorkPlace.setText(ZxTextUtils.getTextNotNull(mBasicInfoBean.getOfficeAddress()));
        } else {
            this.finish();
            return;
        }

        mEditStaffBasicInfoSubscription = RxBus.getInstance().toObservable(EditBasicInfoResult.class).subscribe(
                result -> handlerEditResult(result)
        );
    }

    private void handlerEditResult(EditBasicInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.handler_success));
                EventBus.getDefault().post(new RefreshStaffInfoEvent(true));
                this.finish();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mEditStaffBasicInfoSubscription);
    }
}
