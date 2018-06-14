package com.zhixing.work.zhixin.view.card.back.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.EducationAdapter;
import com.zhixing.work.zhixin.adapter.WorkAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.CardBackEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.AdvancedInformationActivity;
import com.zhixing.work.zhixin.view.card.EditorialBasisActivity;
import com.zhixing.work.zhixin.view.card.PerfectCardDataActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CardCounterFragment extends SupportFragment {
    @BindView(R.id.basics)
    TextView basics;
    @BindView(R.id.senior)
    TextView senior;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.nikename)
    TextView nikename;
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
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.senior_editor)
    Button seniorEditor;
    @BindView(R.id.rl_senior_ed)
    RelativeLayout rlSeniorEd;
    @BindView(R.id.rl_senior)
    RelativeLayout rlSenior;
    @BindView(R.id.basics_bt)
    TextView basicsBt;
    @BindView(R.id.seniority)
    TextView seniority;
    @BindView(R.id.skill)
    TextView skill;
    @BindView(R.id.fate)
    TextView fate;
    @BindView(R.id.career)
    TextView career;
    @BindView(R.id.education_list)
    RecyclerView educationList;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.perfect_card)
    Button perfectCard;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private CardBack cardBack;
    private List<CardBack.WorkBackgroundOutputsBean> list = new ArrayList<CardBack.WorkBackgroundOutputsBean>();
    private List<CardBack.EducationBackgroundOutputsBean> edList = new ArrayList<CardBack.EducationBackgroundOutputsBean>();
    private ArrayList<AddressJson> provincialList = new ArrayList<AddressJson>();
    private ArrayList<AddressJson.ChildBeanX> cityList = new ArrayList<AddressJson.ChildBeanX>();
    private ArrayList<AddressJson.ChildBeanX.ChildBean> areaList = new ArrayList<AddressJson.ChildBeanX.ChildBean>();
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private String addressct = "";

    private WorkAdapter workAdapter;
    private EducationAdapter educationAdapter;
    private Context context;
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private Unbinder unbinder;

    public static CardCounterFragment newInstance() {
        Bundle args = new Bundle();
        CardCounterFragment fragment = new CardCounterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_card_counter, container, false);
        ButterKnife.bind(this, view);
        basics.setSelected(true);
        context = getActivity();
        isViewCreated = true;

        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
     /*   mHandler.sendEmptyMessage(MSG_LOAD_DATA);*/
        initAdapter();
        return view;
    }


    @Override
    public void fetchData() {
        getData();
    }


    private void initAdapter() {
        workAdapter = new WorkAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(workAdapter);


        educationAdapter = new EducationAdapter(edList, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        educationList.setLayoutManager(layoutManager);
        educationList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        educationList.setAdapter(educationAdapter);
    }

    @OnClick({R.id.basics, R.id.senior, R.id.basic_editor, R.id.perfect_card ,R.id.senior_editor, R.id.basics_bt, R.id.seniority, R.id.skill, R.id.fate, R.id.career})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.basics:
                rlBasics.setVisibility(View.VISIBLE);
                rlSenior.setVisibility(View.GONE);
                basics.setSelected(true);
                senior.setSelected(false);
                break;
            case R.id.senior:
                rlBasics.setVisibility(View.GONE);
                rlSenior.setVisibility(View.VISIBLE);
                senior.setSelected(true);
                basics.setSelected(false);
                break;
            case R.id.basic_editor:
                startActivity(new Intent(context, EditorialBasisActivity.class));
                break;
            case R.id.senior_editor:
                startActivity(new Intent(context, AdvancedInformationActivity.class));
                break;
            case R.id.basics_bt:
                break;
            case R.id.seniority:

                break;
            case R.id.skill:
                break;
            case R.id.fate:
                break;
            case R.id.career:
                break;

            case R.id.perfect_card:
                startActivity(new Intent(context, PerfectCardDataActivity.class));
                break;
        }
    }

    //获取数据
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.card, JavaParamsUtils.getInstances().getCardAll(), new TypeToken<EntityObject<CardBack>>() {
        }.getType(), new ResultCallBackListener<CardBack>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<CardBack> response) {
                if (response.getCode() == 10000) {
                    cardBack = response.getContent();
                    list = cardBack.getWorkBackgroundOutputs();
                    edList = cardBack.getEducationBackgroundOutputs();
                    if (cardBack != null) {
                        if (cardBack.getCertificateBackgroundOutputs() != null && !cardBack.getCertificateBackgroundOutputs().isEmpty()) {
                            llData.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                        } else {

                            llData.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                    workAdapter.setList(list);
                    educationAdapter.setList(edList);
                    initView();
                }
            }
        });
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            getData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }


    //设置参数
    private void initView() {
        name.setText(cardBack.getRealName());
        mail.setText(cardBack.getEmail());
        phone.setText(SettingUtils.getPhoneNumber());
        nikename.setText(cardBack.getNickName());
        gendr.setText(cardBack.getSex());
        if (!TextUtils.isEmpty(cardBack.getBirthday())) {
            dateBirth.setText(DateFormatUtil.parseDate(cardBack.getBirthday(), "yyyy-MM-dd"));
        }
        constellation.setText(cardBack.getConstellation());
        height.setText(cardBack.getStature() + "cm");
        weight.setText(cardBack.getWeight() + "kg");
        motto.setText(cardBack.getMotto());
        if (!TextUtils.isEmpty(cardBack.getFirstWorkTime())) {
            firstWorkingTime.setText(DateFormatUtil.parseDate(cardBack.getFirstWorkTime(), "yyyy-MM-dd"));
        }

        nation.setText(cardBack.getNation());
        if (cardBack.getNativePlaceProvince() != null) {
            nativePlace.setText(Utils.searchProvincial(cardBack.getNativePlaceProvince()));
        }

        political.setText(cardBack.getPoliticsFace());

        idNo.setText(cardBack.getIdCard());
        if (cardBack.getHouseholdType() == null) {

        } else {
            if (cardBack.getHouseholdType() == 0) {
                censusRegister.setText("农村户口");
            } else {
                censusRegister.setText("城市户口");
            }
        }
        if (cardBack.getProvince() != null) {
            addressct = Utils.searchProvincial(cardBack.getProvince());
        }
        if (cardBack.getCity() != null) {
            addressct = addressct + Utils.searchCity(cardBack.getCity());
        }
        if (cardBack.getDistrict() != null) {
            addressct = addressct + Utils.searchArea(cardBack.getDistrict());
        }
        if (TextUtils.isEmpty(cardBack.getAddress())) {
            address.setText(addressct);
        } else {
            address.setText(addressct + cardBack.getAddress());
        }
        education.setText(cardBack.getEducation());

    }

    //刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardBackEvent(CardBackEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("TAG", "CardCounterFragment" + " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);

        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onSupportInvisible() {
        if (cardBack == null) {
            getData();
        }
    }

    @Override
    public void onSupportVisible() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if (cardBack == null) {

                    getData();

                }
            }
        });
        thread.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
