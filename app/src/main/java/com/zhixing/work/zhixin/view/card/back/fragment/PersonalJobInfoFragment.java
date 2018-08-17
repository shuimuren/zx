package com.zhixing.work.zhixin.view.card.back.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.JobCardBasicInfoBean;
import com.zhixing.work.zhixin.bean.JobCardSeniorInfoBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.common.StaffStatusMenu;
import com.zhixing.work.zhixin.common.StaffTypeMenu;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.event.RefreshStaffInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.JobCardBasicInfoResult;
import com.zhixing.work.zhixin.network.response.JobCardSeniorInfoResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.EditStaffBasicInfoActivity;
import com.zhixing.work.zhixin.view.card.EditStaffSeniorInfoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 个人工作卡牌-工作信息
 */
public class PersonalJobInfoFragment extends SupportFragment {


    @BindView(R.id.tv_basics)
    TextView tvBasics;
    @BindView(R.id.tv_senior)
    TextView tvSenior;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_card_email)
    TextView tvCardEmail;
    @BindView(R.id.tv_card_department)
    TextView tvCardDepartment;
    @BindView(R.id.tv_card_position)
    TextView tvCardPosition;
    @BindView(R.id.tv_card_work_telephone)
    TextView tvCardWorkTelephone;
    @BindView(R.id.tv_card_code)
    TextView tvCardCode;
    @BindView(R.id.tv_card_company_telephone)
    TextView tvCardCompanyTelephone;
    @BindView(R.id.tv_card_work_place)
    TextView tvCardWorkPlace;
    @BindView(R.id.btn_basic_editor)
    Button btnBasicEditor;
    @BindView(R.id.ll_basic)
    LinearLayout llBasic;
    @BindView(R.id.perfect_card)
    Button perfectCard;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_card_work_begin_time)
    TextView tvCardWorkBeginTime;
    @BindView(R.id.tv_card_user_role_type)
    TextView tvCardUserRoleType;
    @BindView(R.id.tv_card_user_status)
    TextView tvCardUserStatus;
    @BindView(R.id.tv_card_test_time)
    TextView tvCardTestTime;
    @BindView(R.id.tv_card_formal_time)
    TextView tvCardFormalTime;
    @BindView(R.id.ll_senior)
    LinearLayout llSenior;
    @BindView(R.id.btn_senior_editor)
    Button btnSeniorEditor;

    private Subscription mPersonalCardBasicInfoSubscription;
    private Subscription mPersonalCardSeniorInfoSubscription;

    private JobCardBasicInfoBean mBasicInfo;
    private JobCardSeniorInfoBean mSeniorInfo;
    private String mStaffId;


    public static PersonalJobInfoFragment newInstance(String staffId) {
        Bundle args = new Bundle();
        args.putString(CardJobMainFragment.INTENT_KEY_STAFF_ID, staffId);
        PersonalJobInfoFragment fragment = new PersonalJobInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_job_info, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        registerRequest();
        mStaffId = getArguments().getString(CardJobMainFragment.INTENT_KEY_STAFF_ID);
        //个人端不可编辑高级信息
        if (SettingUtils.getTokenBean().getStaffRole() == ResultConstant.USER_STAFF_ROLE_EMPLOYEE &&
                SettingUtils.getTokenBean().getRole() == ResultConstant.USER_TYPE_PERSONAL) {
            btnSeniorEditor.setVisibility(View.GONE);
        } else {
            btnBasicEditor.setVisibility(View.VISIBLE);
        }
        onViewClicked(tvBasics);
        return view;
    }

    private void registerRequest() {

        mPersonalCardBasicInfoSubscription = RxBus.getInstance().toObservable(JobCardBasicInfoResult.class).subscribe(
                result -> handlerJobCardBasicInfoResult(result)
        );

        mPersonalCardSeniorInfoSubscription = RxBus.getInstance().toObservable(JobCardSeniorInfoResult.class).subscribe(
                result -> handlerJobCardSeniorInfoResult(result)
        );
    }

    /**
     * 高级信息
     *
     * @param result
     */
    private void handlerJobCardSeniorInfoResult(JobCardSeniorInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mSeniorInfo = result.getContent();
            if (mSeniorInfo != null) {
                tvCardWorkBeginTime.setText(ZxTextUtils.getTextWithDefault(mSeniorInfo.getEntryTime()));
                tvCardUserRoleType.setText(StaffTypeMenu.getName(mSeniorInfo.getStaffType()));
                tvCardUserStatus.setText(StaffStatusMenu.getName(mSeniorInfo.getStaffStatus()));
                tvCardFormalTime.setText(ZxTextUtils.getTextWithDefault(mSeniorInfo.getPositiveTime()));
                tvCardTestTime.setText(String.format("%s月", String.valueOf(mSeniorInfo.getProbation())));
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    /**
     * 基础信息
     *
     * @param result
     */
    private void handlerJobCardBasicInfoResult(JobCardBasicInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mBasicInfo = result.getContent();
            if (mBasicInfo != null) {
                tvCardName.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getRealName()));
                tvCardEmail.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getWorkEmail()));

                if (mBasicInfo.getDepartments() != null && mBasicInfo.getDepartments().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mBasicInfo.getDepartments().size(); i++) {
                        stringBuilder.append(Utils.stringListToString(mBasicInfo.getDepartments().get(i), "-"));
                        if (i != mBasicInfo.getDepartments().size() - 1) {
                            stringBuilder.append("\n");
                        }
                    }
                    tvCardDepartment.setText(ZxTextUtils.getTextWithDefault(stringBuilder.toString()));
                }

                tvCardPosition.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getJobType()));
                tvCardWorkTelephone.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getWorkPhoneNum()));
                tvCardCode.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getWorkNumber()));
                tvCardCompanyTelephone.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getExtPhoneNum()));
                tvCardWorkPlace.setText(ZxTextUtils.getTextWithDefault(mBasicInfo.getOfficeAddress()));
            } else {
                AlertUtils.show(result.Message);
            }
        }

    }


    @Override
    public void fetchData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @OnClick({R.id.tv_basics, R.id.tv_senior, R.id.perfect_card, R.id.btn_basic_editor, R.id.btn_senior_editor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_basics:
                tvBasics.setSelected(true);
                tvSenior.setSelected(false);
                llBasic.setVisibility(View.VISIBLE);
                llSenior.setVisibility(View.GONE);
                if (mBasicInfo == null && !TextUtils.isEmpty(mStaffId)) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_JOB_BASE, mStaffId);
                } else {
                    Logger.i(">>>", "staffId Is null");
                }
                break;
            case R.id.tv_senior:
                tvBasics.setSelected(false);
                tvSenior.setSelected(true);
                llBasic.setVisibility(View.GONE);
                llSenior.setVisibility(View.VISIBLE);
                if (mSeniorInfo == null) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_JOB_ADVANCED, mStaffId);
                }
                break;
            case R.id.perfect_card:

                break;
            case R.id.btn_basic_editor:
                Intent intent = new Intent(getActivity(), EditStaffBasicInfoActivity.class);
                intent.putExtra(EditStaffBasicInfoActivity.INTENT_KEY_STAFF_ID, mBasicInfo);
                startActivity(intent);
                break;
            case R.id.btn_senior_editor:
                Intent intentSenior = new Intent(getActivity(), EditStaffSeniorInfoActivity.class);
                intentSenior.putExtra(EditStaffSeniorInfoActivity.INTENT_KEY_STAFF_BEAN, mSeniorInfo);
                startActivity(intentSenior);

                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mPersonalCardBasicInfoSubscription, mPersonalCardSeniorInfoSubscription);
    }

    @Subscribe
    public void refreshStaffInfo(RefreshStaffInfoEvent event) {
        if (!TextUtils.isEmpty(mStaffId)) {
            if (event.isBasicInfo()) {
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_JOB_BASE, mStaffId);
            } else {
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_JOB_ADVANCED, mStaffId);
            }
        } else {
            Logger.i(">>>", "staffId Is aaa null");
        }

    }
}
