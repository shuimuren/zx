package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.CertificateAdapter;
import com.zhixing.work.zhixin.adapter.EducationAdapter;
import com.zhixing.work.zhixin.adapter.WorkAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressBean;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.dialog.MarriageDialog;
import com.zhixing.work.zhixin.dialog.PoliticalStatusDialog;
import com.zhixing.work.zhixin.dialog.ResidenceDialog;
import com.zhixing.work.zhixin.event.CardBackEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.back.CertificateListActivity;
import com.zhixing.work.zhixin.view.card.back.EducationListActivity;
import com.zhixing.work.zhixin.view.card.back.WorkListActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AdvancedInformationActivity extends BaseTitleActivity {

    @BindView(R.id.nation)
    TextView nation;
    @BindView(R.id.iv_nation)
    ImageView ivNation;
    @BindView(R.id.rl_nation)
    RelativeLayout rlNation;
    @BindView(R.id.native_place)
    TextView nativePlace;
    @BindView(R.id.iv_native_place)
    ImageView ivNativePlace;
    @BindView(R.id.rl_native_place)
    RelativeLayout rlNativePlace;
    @BindView(R.id.political)
    TextView political;
    @BindView(R.id.iv_political)
    ImageView ivPolitical;
    @BindView(R.id.rl_political)
    RelativeLayout rlPolitical;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.iv_id)
    ImageView ivId;
    @BindView(R.id.rl_id)
    RelativeLayout rlId;
    @BindView(R.id.census_register)
    TextView censusRegister;

    @BindView(R.id.rl_census_register)
    RelativeLayout rlCensusRegister;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.iv_adress)
    ImageView ivAdress;
    @BindView(R.id.rl_adress)
    RelativeLayout rlAdress;
    @BindView(R.id.highest_education)
    TextView highestEducation;
    @BindView(R.id.iv_highest_education)
    ImageView ivHighestEducation;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.iv_work)
    ImageView ivWork;
    @BindView(R.id.rlwork)
    RelativeLayout rlwork;
    @BindView(R.id.work_list)
    RecyclerView workList;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.rleducation)
    RelativeLayout rleducation;
    @BindView(R.id.education_list)
    RecyclerView educationList;
    @BindView(R.id.iv_certificate)
    ImageView ivCertificate;
    @BindView(R.id.rlcertificate)
    RelativeLayout rlcertificate;
    @BindView(R.id.certificate_list)
    RecyclerView certificateList;
    @BindView(R.id.iv_census_register)
    ImageView ivCensusRegister;
    @BindView(R.id.marriage)
    TextView marriage;
    @BindView(R.id.iv_marriage)
    ImageView ivMarriage;
    @BindView(R.id.rl_marriage)
    RelativeLayout rlMarriage;
    @BindView(R.id.is_study_abroad_yes)
    CheckBox isStudyAbroadYes;
    @BindView(R.id.study_abroad_no)
    CheckBox studyAbroadNo;
    private CardBack cardBack;
    private List<CardBack.WorkBackgroundOutputsBean> list = new ArrayList<CardBack.WorkBackgroundOutputsBean>();
    private List<CardBack.EducationBackgroundOutputsBean> edList = new ArrayList<CardBack.EducationBackgroundOutputsBean>();
    private List<CardBack.CertificateBackgroundOutputsBean> cfList = new ArrayList<CardBack.CertificateBackgroundOutputsBean>();
    private String addressct = "";
    private String PoliticsFace = "";

    private WorkAdapter workAdapter;
    private EducationAdapter educationAdapter;
    private CertificateAdapter certificateAdapter;

    private String NativePlaceProvince = "";
    private String NativePlaceCity = "";
    private String Province = "";

    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String HouseholdType = "";

    private String City = "";
    private String District = "";
    private String Address = "";
    private String detailedAddress = "";
    private String Education = "";
    private String[] menuList;
    private List<String> StringList;
    private String IdCard = "";
    private String Nation = "";
    private String Married = "";
    private String StudyAbroad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_information);
        ButterKnife.bind(this);
        setTitle("高级信息");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        setRightText1("保存");
        getData();
        initAdapter();
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
                    cfList = cardBack.getCertificateBackgroundOutputs();
                    workAdapter.setList(list);
                    educationAdapter.setList(edList);
                    certificateAdapter.setList(cfList);
                    initView();
                }
            }
        });
    }

    //设置参数
    private void initView() {
        nation.setText(cardBack.getNation());
        if (cardBack.getNativePlaceProvince() != null) {
            nativePlace.setText(Utils.searchProvincial(cardBack.getNativePlaceProvince()));
            NativePlaceProvince = cardBack.getNativePlaceProvince() + "";
        }
        if (cardBack.getNativePlaceCity() != null) {
            NativePlaceCity = cardBack.getNativePlaceCity() + "";
        }
        political.setText(cardBack.getPoliticsFace());
        if (!TextUtils.isEmpty(cardBack.getPoliticsFace())) {
            PoliticsFace = Utils.getPolitical(cardBack.getPoliticsFace());
        }

        id.setText(cardBack.getIdCard());
        if (cardBack.getHouseholdType() == null) {

        } else {
            if (cardBack.getHouseholdType() == 0) {
                HouseholdType = "0";
                censusRegister.setText("农村户口");
            } else {
                HouseholdType = "1";
                censusRegister.setText("城市户口");
            }
        }
        if (cardBack.getProvince() != null) {
            addressct = Utils.searchProvincial(cardBack.getProvince());
            Province = cardBack.getProvince() + "";
        }
        if (cardBack.getCity() != null) {
            City = cardBack.getCity() + "";
            addressct = addressct + Utils.searchCity(cardBack.getCity());
        }
        if (cardBack.getDistrict() != null) {
            District = cardBack.getDistrict() + "";
            addressct = addressct + Utils.searchArea(cardBack.getDistrict());
        }
        if (TextUtils.isEmpty(cardBack.getAddress())) {
            address.setText(addressct);
        } else {
            Address = cardBack.getAddress();
            address.setText(addressct + cardBack.getAddress());
        }
        if (!TextUtils.isEmpty(cardBack.getStudyAbroad())) {
            StudyAbroad = cardBack.getStudyAbroad();
            if (cardBack.getStudyAbroad().equals("true")) {
                isStudyAbroadYes.setChecked(true);
            } else {
                studyAbroadNo.setChecked(true);
            }
        }

        highestEducation.setText(cardBack.getEducation());
        if (TextUtils.isEmpty(cardBack.getEducation())) {
            Education = Utils.getEducationId(cardBack.getEducation());
        }
        if (cardBack.getMarried() != null) {
            switch (cardBack.getMarried()) {
                case 0:
                    marriage.setText("未婚");
                    break;
                case 1:
                    marriage.setText("已婚");
                    break;
                case 2:
                    marriage.setText("保密");
                    break;
            }
            Married = cardBack.getMarried() + "";
        }
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody body = new FormBody.Builder()
                        .add("Nation", Nation)
                        .add("NativePlaceProvince", NativePlaceProvince)
                        .add("NativePlaceCity", NativePlaceCity)
                        .add("PoliticsFace", PoliticsFace)
                        .add("IdCard", IdCard)
                        .add("HouseholdType", HouseholdType)
                        .add("Education", Education)
                        .add("Province", Province)
                        .add("City", City)
                        .add("District", District)
                        .add("Married", Married)
                        .add("StudyAbroad", StudyAbroad)
                        .add("Address", Address)
                        .build();
                submit(body);

            }
        });

    }

    private void initAdapter() {
        workAdapter = new WorkAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workList.setLayoutManager(commodityLayoutManager);
        workList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        workList.setAdapter(workAdapter);


        educationAdapter = new EducationAdapter(edList, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        educationList.setLayoutManager(layoutManager);
        educationList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        educationList.setAdapter(educationAdapter);


        certificateAdapter = new CertificateAdapter(cfList, context);
        LinearLayoutManager cflayoutManager = new LinearLayoutManager(context);
        cflayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        certificateList.setLayoutManager(cflayoutManager);
        certificateList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        certificateList.setAdapter(certificateAdapter);
    }

    @OnClick({R.id.rl_nation, R.id.rl_native_place, R.id.is_study_abroad_yes, R.id.study_abroad_no, R.id.rl_marriage, R.id.rl_political, R.id.rl_id, R.id.rl_census_register, R.id.rl_adress, R.id.rl_education, R.id.rlwork, R.id.rleducation, R.id.rlcertificate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_nation:
                startActivity(new Intent(context, ModifyDataActivity.class)
                        .putExtra("title", "民族").putExtra("type", ModifyDataActivity.TYPE_NATION)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nation.getText().toString())
                );
                break;
            case R.id.rl_native_place:

                if (isLoaded) {
                    ShowPickerView();
                } else {

                }
                break;
            case R.id.rl_marriage:
                MarriageDialog dialog = new MarriageDialog(context, new MarriageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(MarriageDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case MarriageDialog.TYPE_UNMARRIED:
                                Married = "0";
                                marriage.setText("未婚");
                                break;
                            case MarriageDialog.TYPE_MARRIED:
                                Married = "1";
                                marriage.setText("已婚");
                                break;
                            case MarriageDialog.TYPE_SECRECY:
                                marriage.setText("保密");
                                Married = "2";
                                break;
                        }
                    }
                });
                dialog.show();

                break;

            case R.id.is_study_abroad_yes:
                studyAbroadNo.setChecked(false);
                StudyAbroad = "true";

                break;
            case R.id.study_abroad_no:
                isStudyAbroadYes.setChecked(false);
                StudyAbroad = "false";

                break;
            case R.id.rl_political:
                PoliticalStatusDialog politicalStatusDialog = new PoliticalStatusDialog(context, new PoliticalStatusDialog.OnItemClickListener() {
                    @Override
                    public void onClick(PoliticalStatusDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case PoliticalStatusDialog.TYPE_CITIZEN:
                                PoliticsFace = "0";
                                political.setText("普通公民");
                                break;
                            case PoliticalStatusDialog.TYPE_PARTY_MEMBER_OF_CPC:
                                PoliticsFace = "1";
                                political.setText("中共党员");
                                break;
                            case PoliticalStatusDialog.TYPE_LEAGUE_MEMBER:
                                PoliticsFace = "2";
                                political.setText("共青团员");
                                break;
                            case PoliticalStatusDialog.TYPE_DEMOCRATIC_PROGRESSIVE_PARTY:
                                PoliticsFace = "3";
                                political.setText("民族党派人士");
                                break;
                            case PoliticalStatusDialog.TYPE_NON_PARTISAN:
                                PoliticsFace = "4";
                                political.setText("无党派民主人士");
                                break;
                        }
                    }
                });
                politicalStatusDialog.show();
                break;
            case R.id.rl_id:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "身份证号码").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_ID)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, id.getText().toString())
                );
                break;
            case R.id.rl_census_register:
                ResidenceDialog residenceDialog = new ResidenceDialog(context, new ResidenceDialog.OnItemClickListener() {
                    @Override
                    public void onClick(ResidenceDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case ResidenceDialog.TYPE_COUNTRYSIDE:
                                HouseholdType = "0";
                                censusRegister.setText("农村户口");
                                break;
                            case ResidenceDialog.TYPE_CITY:
                                HouseholdType = "1";
                                censusRegister.setText("城市户口");
                                break;

                        }
                    }
                });
                residenceDialog.show();
                break;
            case R.id.rl_adress:
                startActivity(new Intent(context, Addressctivity.class));
                break;
            case R.id.rl_education:
//                学历选择
                menuList = getResources().getStringArray(R.array.education);
                StringList = Arrays.asList(menuList);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = StringList.get(options1);
                        Education = ((options1 + 1) * 10) + "";
                        highestEducation.setText(s);
                    }
                })
                        .setTitleText("学历")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
            case R.id.rlwork:
                startActivity(new Intent(context, WorkListActivity.class));
                break;
            case R.id.rleducation:
                startActivity(new Intent(context, EducationListActivity.class));
                break;
            case R.id.rlcertificate:
                startActivity(new Intent(context, CertificateListActivity.class));
                break;
        }
    }

    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx;
                //int id3 = options1Items.get(options1).getChild().get(options2).getChild().get(options3).getId();
                if (options1Items.get(options1).getChild().size() == 0) {
                    tx = options1Items.get(options1).getName();
                    NativePlaceProvince = options1Items.get(options1).getId() + "";
                } else {
                    tx = options1Items.get(options1).getName() + options2Items.get(options1).get(options2);
                    NativePlaceProvince = options1Items.get(options1).getId() + "";
                    NativePlaceCity = options1Items.get(options1).getChild().get(options2).getId() + "";
                }
                nativePlace.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//三级选择器
        pvOptions.show();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了


                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:

                    break;

            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressBean(AddressBean event) {

        address.setText(event.getDetailedAddress());
        Province = event.getProvince();
        City = event.getCity();
        District = event.getDistrict();
        Address = event.getAddress();
        detailedAddress = event.getDetailedAddress();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {

            case ModifyDataActivity.TYPE_ID: //send the video
                id.setText(event.getContent());
                IdCard = event.getContent();


            case ModifyDataActivity.TYPE_NATION: //send the video
                nation.setText(event.getContent());
                Nation = event.getContent();
                break;


        }
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = Utils.getJson(this, "Area.json");//获取assets目录下的json文件数据

        ArrayList<AddressJson> jsonBean = gson.fromJson(JsonData, new TypeToken<List<AddressJson>>() {
        }.getType());

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            if (jsonBean.get(i).getChild().size() == 0) {
                CityList.add("");
                ArrayList<String> City_AreaList = new ArrayList<>();
                City_AreaList.add("");
                Province_AreaList.add(City_AreaList);
            }
            for (int c = 0; c < jsonBean.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getChild().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChild().get(c).getName() == null
                        || jsonBean.get(i).getChild().get(c).getChild().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getChild().get(c).getChild().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getChild().get(c).getChild().get(d).getName();

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }


            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    private void submit(RequestBody body) {

        OkUtils.getInstances().httput(body, context, JavaConstant.personalinfo, JavaParamsUtils.getInstances().personalinfo(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "修改成功");
                        EventBus.getDefault().post(new CardBackEvent(true));
                        finish();
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });
    }
}
