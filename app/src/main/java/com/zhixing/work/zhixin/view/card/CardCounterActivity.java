package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AddWorkAdapter;
import com.zhixing.work.zhixin.adapter.WorkAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressBean;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.Card;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.score.ScoreActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardCounterActivity extends BaseTitleActivity {
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
    @BindView(R.id.rl_basics)
    LinearLayout rlBasics;
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
    @BindView(R.id.rl_senior)
    LinearLayout rlSenior;
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
    private CardBack cardBack;
    private List<CardBack.WorkBackgroundOutputsBean> list = new ArrayList<CardBack.WorkBackgroundOutputsBean>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_counter);
        ButterKnife.bind(this);
        basics.setSelected(true);
        initAdapter();
     /*   mHandler.sendEmptyMessage(MSG_LOAD_DATA);*/
        getData();
        setTitle("职信卡牌");
    }

    private void initAdapter() {
        workAdapter = new WorkAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(workAdapter);
    }
    @OnClick({R.id.basics, R.id.senior, R.id.basic_editor, R.id.senior_editor, R.id.basics_bt, R.id.seniority, R.id.skill, R.id.fate, R.id.career})
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
                break;
            case R.id.senior_editor:
                break;
            case R.id.basics_bt:
                break;
            case R.id.seniority:
                startActivity(new Intent(context, ScoreActivity.class));
                break;
            case R.id.skill:
                break;
            case R.id.fate:
                break;
            case R.id.career:
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
                    workAdapter.setList(list);
                    initView();
                }
            }
        });
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
        address.setText(addressct + cardBack.getAddress());
        education.setText(cardBack.getEducation());

    }


}
