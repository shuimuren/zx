package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceStatisticsAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.bean.EducationBgsBean;
import com.zhixing.work.zhixin.bean.StaffJobAdvanceBean;
import com.zhixing.work.zhixin.bean.StaffJobBaseCardBean;
import com.zhixing.work.zhixin.bean.WorkBgsBean;
import com.zhixing.work.zhixin.event.RefreshStaffJobInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.StaffJobAdvancedResult;
import com.zhixing.work.zhixin.network.response.StaffJobBaseCardResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.EditJobAdvancedActivity;
import com.zhixing.work.zhixin.view.card.EditJobBasisActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 职信卡牌-个人企业卡牌基础基础
 */
public class PersonalJobUserInfoFragment extends SupportFragment {


    @BindView(R.id.basics)
    TextView basics;
    @BindView(R.id.senior)
    TextView senior;
    @BindView(R.id.ll_table_bar)
    LinearLayout llTableBar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.nikeName)
    TextView nikeName;
    @BindView(R.id.ll_niken_ame)
    LinearLayout llNikenAme;
    @BindView(R.id.gendr)
    TextView gendr;
    @BindView(R.id.ll_gendr)
    LinearLayout llGendr;
    @BindView(R.id.date_birth)
    TextView dateBirth;
    @BindView(R.id.ll_date_birth)
    LinearLayout llDateBirth;
    @BindView(R.id.constellation)
    TextView constellation;
    @BindView(R.id.ll_constellation)
    LinearLayout llConstellation;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.ll_weiht)
    LinearLayout llWeiht;
    @BindView(R.id.motto)
    TextView motto;
    @BindView(R.id.ll_motto)
    LinearLayout llMotto;
    @BindView(R.id.first_working_time)
    TextView firstWorkingTime;
    @BindView(R.id.ll_first_working_time)
    LinearLayout llFirstWorkingTime;
    @BindView(R.id.basic_editor)
    Button basicEditor;
    @BindView(R.id.rl_basic_ed)
    RelativeLayout rlBasicEd;
    @BindView(R.id.rl_basics)
    RelativeLayout rlBasics;
    @BindView(R.id.nation)
    TextView nation;
    @BindView(R.id.ll_nation)
    LinearLayout llNation;
    @BindView(R.id.native_place)
    TextView nativePlace;
    @BindView(R.id.ll_native_place)
    LinearLayout llNativePlace;
    @BindView(R.id.political)
    TextView political;
    @BindView(R.id.ll_political)
    LinearLayout llPolitical;
    @BindView(R.id.id_no)
    TextView idNo;
    @BindView(R.id.ll_id_no)
    LinearLayout llIdNo;
    @BindView(R.id.census_register)
    TextView censusRegister;
    @BindView(R.id.ll_census_register)
    LinearLayout llCensusRegister;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.ll_education)
    LinearLayout llEducation;
    @BindView(R.id.work_list_view)
    RecyclerView workListView;
    @BindView(R.id.education_list_view)
    RecyclerView educationListView;
    @BindView(R.id.certificate_list_view)
    RecyclerView certificateListView;
    @BindView(R.id.senior_editor)
    Button seniorEditor;
    @BindView(R.id.rl_senior_ed)
    RelativeLayout rlSeniorEd;
    @BindView(R.id.rl_senior)
    RelativeLayout rlSenior;
    @BindView(R.id.native_marriage)
    TextView nativeMarriage;
    @BindView(R.id.ll_native_marriage)
    LinearLayout llNativeMarriage;

    public static PersonalJobUserInfoFragment newInstance(String staffId) {
        Bundle args = new Bundle();
        args.putString(CardJobMainFragment.INTENT_KEY_STAFF_ID, staffId);
        PersonalJobUserInfoFragment fragment = new PersonalJobUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String mStaffId;

    private boolean isViewCreated;
    //Fragment对用户可见的标记


    private StaffJobBaseCardBean mBasicBean;
    private StaffJobAdvanceBean mAdvanceBean;

    private Subscription mStaffBaseSubscription;
    private Subscription mStaffSeniorSubscription;

    private List<EducationBgsBean> mEducations;
    private List<WorkBgsBean> mWorks;
    private List<CertificateBean> mCertificates;
    private AttendanceStatisticsAdapter mWordAdapter;
    private AttendanceStatisticsAdapter mEducationAdapter;
    private AttendanceStatisticsAdapter mCertificateAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_job_user_info, container, false);
        ButterKnife.bind(this, view);
        isViewCreated = true;
        mStaffId = getArguments().getString(CardJobMainFragment.INTENT_KEY_STAFF_ID);
        if (TextUtils.isEmpty(mStaffId)) {
            getActivity().finish();
        }
        EventBus.getDefault().register(this);
        onViewClicked(basics);
        registerRequest();
        initView();
        return view;
    }

    private void initView() {
        mEducations = new ArrayList<>();
        mWorks = new ArrayList<>();
        mCertificates = new ArrayList<>();

        mWordAdapter = new AttendanceStatisticsAdapter();
        mWordAdapter.setData(mWorks);
        workListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        workListView.setHasFixedSize(true);
        workListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        workListView.setAdapter(mWordAdapter);

        mEducationAdapter = new AttendanceStatisticsAdapter();
        mEducationAdapter.setData(mEducations);
        educationListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        educationListView.setHasFixedSize(true);
        educationListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        educationListView.setAdapter(mEducationAdapter);

        mCertificateAdapter = new AttendanceStatisticsAdapter();
        mCertificateAdapter.setData(mCertificates);
        certificateListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        certificateListView.setHasFixedSize(true);
        certificateListView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        certificateListView.setAdapter(mCertificateAdapter);

    }

    private void registerRequest() {
        mStaffBaseSubscription = RxBus.getInstance().toObservable(StaffJobBaseCardResult.class).subscribe(
                result -> handlerStaffBaseInfo(result)
        );

        mStaffSeniorSubscription = RxBus.getInstance().toObservable(StaffJobAdvancedResult.class).subscribe(
                result -> handlerStaffAdvanceInfo(result)
        );


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isViewCreated) {
            fetchData();
        }
    }

    /**
     * 工作信息高级
     *
     * @param result
     */
    private void handlerStaffAdvanceInfo(StaffJobAdvancedResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mAdvanceBean = result.getContent();
            if (mAdvanceBean != null) {
                initAdvanceInfoView();
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }

    //高级信息UI
    private void initAdvanceInfoView() {
        nation.setText(ZxTextUtils.getTextWithDefault(mAdvanceBean.getNation()));
        if (mAdvanceBean.getNativePlaceProvince() != null) {
            nativePlace.setText(Utils.searchProvincial(mAdvanceBean.getNativePlaceProvince()));
        } else {
            nativePlace.setText("-");
        }
        political.setText(Utils.getStaffPolitical(mAdvanceBean.getPoliticsFace()));
        idNo.setText(ZxTextUtils.getTextWithDefault(mAdvanceBean.getIdCard()));
        if (mAdvanceBean.getHouseholdType() == null) {
            censusRegister.setText("-");
        } else {
            if (mAdvanceBean.getHouseholdType() == 0) {
                censusRegister.setText("农村户口");
            } else {
                censusRegister.setText("城市户口");
            }
        }
        if (mAdvanceBean.getMarried() != null) {
            nativeMarriage.setText(mAdvanceBean.getMarried() == 0 ? "未婚" : "已婚");
        } else {
            nativeMarriage.setText("-");
        }

        String addressct = "";
        if (mAdvanceBean.getProvince() != null) {
            addressct = Utils.searchProvincial(mAdvanceBean.getProvince());
        }
        if (mAdvanceBean.getCity() != null) {
            addressct = addressct + Utils.searchCity(mAdvanceBean.getCity());
        }
        if (mAdvanceBean.getDistrict() != null) {
            addressct = addressct + Utils.searchArea(mAdvanceBean.getDistrict());
        }
        if (TextUtils.isEmpty(mAdvanceBean.getAddress())) {
            address.setText("-");
        } else {
            address.setText(addressct + mAdvanceBean.getAddress());
        }
        education.setText(ZxTextUtils.getTextWithDefault(mAdvanceBean.getEducation()));
        if (mAdvanceBean.getWorkBgs() != null) {
            mWordAdapter.setData(mAdvanceBean.getWorkBgs());
        }
        if (mAdvanceBean.getCertificateBgs() != null) {
            mCertificateAdapter.setData(mAdvanceBean.getCertificateBgs());
        }
        if (mAdvanceBean.getEducation() != null) {
            mEducationAdapter.setData(mAdvanceBean.getEducationBgs());
        }

    }

    //基本信息UI
    private void initBasicInfoView() {
        name.setText(ZxTextUtils.getTextWithDefault(mBasicBean.getRealName()));
        mail.setText(ZxTextUtils.getTextWithDefault(mBasicBean.getEmail()));
        phone.setText(ZxTextUtils.getTextWithDefault(SettingUtils.getPhoneNumber()));
        nikeName.setText(ZxTextUtils.getTextWithDefault(mBasicBean.getNickName()));

        gendr.setText(mBasicBean.getSex() == 0 ? "男" : "女");
        if (!TextUtils.isEmpty(mBasicBean.getBirthday())) {
            dateBirth.setText(DateFormatUtil.parseDate(mBasicBean.getBirthday(), "yyyy-MM-dd"));
        }
        constellation.setText(Arrays.asList(getResources().getStringArray(R.array.constellation)).get(mBasicBean.getConstellation()));
        if (mBasicBean.getStature() == 0) {
            height.setText("-");
        } else {
            height.setText(String.valueOf(mBasicBean.getStature()) + " cm");
        }

        if (mBasicBean.getWeight() == 0) {
            weight.setText("-");
        } else {
            weight.setText(String.valueOf(mBasicBean.getWeight()) + " kg");
        }


        motto.setText(ZxTextUtils.getTextWithDefault(mBasicBean.getMotto()));
        if (!TextUtils.isEmpty(mBasicBean.getFirstWorkTime())) {
            firstWorkingTime.setText(DateFormatUtil.parseDate(mBasicBean.getFirstWorkTime(), "yyyy-MM-dd"));
        }

    }

    /**
     * 工作信息基础
     *
     * @param result
     */
    private void handlerStaffBaseInfo(StaffJobBaseCardResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            mBasicBean = result.getContent();
            if (mBasicBean != null) {
                initBasicInfoView();
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }


    @Override
    public void fetchData() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_PERSON_BASE, mStaffId);
        isViewCreated = false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.basics, R.id.senior, R.id.basic_editor, R.id.senior_editor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.basics:
                basics.setSelected(true);
                senior.setSelected(false);
                rlBasics.setVisibility(View.VISIBLE);
                rlSenior.setVisibility(View.GONE);
                break;
            case R.id.senior:
                basics.setSelected(false);
                senior.setSelected(true);
                rlBasics.setVisibility(View.GONE);
                rlSenior.setVisibility(View.VISIBLE);
                if (mAdvanceBean == null) {
                    MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_PERSON_ADVANCED, mStaffId);
                }
                break;
            case R.id.basic_editor:
                EditJobBasisActivity.startEditJobBasicActivity(getActivity(), mStaffId, mBasicBean);
                break;
            case R.id.senior_editor:
                EditJobAdvancedActivity.startEditJobAdvanceActivity(getActivity(), mStaffId, mAdvanceBean);
                break;
        }
    }

    @Subscribe
    public void RefreshJobInfo(RefreshStaffJobInfoEvent event) {
        if (event.isBasicInfo()) {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_PERSON_BASE, mStaffId);
        } else {
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_PERSON_ADVANCED, mStaffId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mStaffBaseSubscription, mStaffSeniorSubscription);
    }


}
