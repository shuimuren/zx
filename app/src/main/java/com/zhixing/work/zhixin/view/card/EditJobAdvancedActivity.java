package com.zhixing.work.zhixin.view.card;

import android.app.Activity;
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
import com.zhixing.work.zhixin.adapter.AttendanceStatisticsAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressBean;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.bean.EducationBgsBean;
import com.zhixing.work.zhixin.bean.StaffJobAdvanceBean;
import com.zhixing.work.zhixin.bean.WorkBgsBean;
import com.zhixing.work.zhixin.dialog.MarriageDialog;
import com.zhixing.work.zhixin.dialog.PoliticalStatusDialog;
import com.zhixing.work.zhixin.dialog.ResidenceDialog;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.RefreshStaffJobInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.EditJobAdvancedResult;
import com.zhixing.work.zhixin.network.response.StaffJobAdvancedResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.back.CertificateListActivity;
import com.zhixing.work.zhixin.view.card.back.WorkEducationListActivity;
import com.zhixing.work.zhixin.view.card.back.WorkJobListActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class EditJobAdvancedActivity extends BaseTitleActivity {

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
    RecyclerView workListView;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.rleducation)
    RelativeLayout rleducation;
    @BindView(R.id.education_list)
    RecyclerView educationListView;
    @BindView(R.id.iv_certificate)
    ImageView ivCertificate;
    @BindView(R.id.rlcertificate)
    RelativeLayout rlcertificate;
    @BindView(R.id.certificate_list)
    RecyclerView certificateListView;
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


    private AttendanceStatisticsAdapter workAdapter;
    private AttendanceStatisticsAdapter educationAdapter;
    private AttendanceStatisticsAdapter certificateAdapter;


    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private String addressct = "";
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private String[] menuList;
    private List<String> StringList;
    private static final String INTENT_KEY_STAFF_ID = "staffId";
    private static final String INTENT_KEY_STAFF_BEAN = "staffBean";
    private StaffJobAdvanceBean mStaffAdvanceBean;
    private List<WorkBgsBean> workList;
    private List<EducationBgsBean> educationList;
    private List<CertificateBean> certificateList;
    private Subscription mEditStaffJobAdvancedInfoSubscription;
    private Subscription mUpdateJobSubscription;
    private Subscription mStaffSeniorSubscription;
    private String mStaffId;

    public static void startEditJobAdvanceActivity(Activity activity, String staffId, StaffJobAdvanceBean staffBean) {
        Intent intent = new Intent(activity, EditJobAdvancedActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        intent.putExtra(INTENT_KEY_STAFF_BEAN, staffBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_advanced);
        ButterKnife.bind(this);
        setTitle("高级信息");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        setRightText1("保存");
        getData();
        initAdapter();
    }

    //获取数据
    private void getData() {
        mStaffAdvanceBean = (StaffJobAdvanceBean) getIntent().getSerializableExtra(INTENT_KEY_STAFF_BEAN);
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mStaffAdvanceBean.setStaffId(mStaffId);
        if (mStaffAdvanceBean == null) {
            this.finish();
        }
        initView();
    }

    //设置参数
    private void initView() {
        mEditStaffJobAdvancedInfoSubscription = RxBus.getInstance().toObservable(EditJobAdvancedResult.class).subscribe(
                result -> handlerEditResult(result)
        );
        mUpdateJobSubscription = RxBus.getInstance().toObservable(UpdateJobResult.class).subscribe(
                result -> handlerJobDeleteResult(result)
        );
        mStaffSeniorSubscription = RxBus.getInstance().toObservable(StaffJobAdvancedResult.class).subscribe(
                result -> handlerStaffAdvanceInfo(result)
        );
        workList = mStaffAdvanceBean.getWorkBgs();
        educationList = mStaffAdvanceBean.getEducationBgs();
        certificateList = mStaffAdvanceBean.getCertificateBgs();
        nation.setText(mStaffAdvanceBean.getNation());
        if (mStaffAdvanceBean.getNativePlaceProvince() != null) {
            nativePlace.setText(Utils.searchProvincial(mStaffAdvanceBean.getNativePlaceProvince()));
        }
        if (mStaffAdvanceBean.getPoliticsFace() != null) {
            political.setText(Utils.getStaffPolitical(mStaffAdvanceBean.getPoliticsFace()));
        }
        id.setText(mStaffAdvanceBean.getIdCard());
        if (mStaffAdvanceBean.getHouseholdType() == null) {

        } else {
            if (mStaffAdvanceBean.getHouseholdType() == 0) {
                censusRegister.setText("农村户口");
            } else {
                censusRegister.setText("城市户口");
            }
        }
        if (mStaffAdvanceBean.getProvince() != null) {
            addressct = Utils.searchProvincial(mStaffAdvanceBean.getProvince());
        }
        if (mStaffAdvanceBean.getCity() != null) {
            addressct = addressct + Utils.searchCity(mStaffAdvanceBean.getCity());
        }
        if (mStaffAdvanceBean.getDistrict() != null) {
            addressct = addressct + Utils.searchArea(mStaffAdvanceBean.getDistrict());
        }
        if (TextUtils.isEmpty(mStaffAdvanceBean.getAddress())) {
            address.setText(addressct);
        } else {
            address.setText(addressct + mStaffAdvanceBean.getAddress());
        }
        if (mStaffAdvanceBean.isStudyAbroad()) {
            isStudyAbroadYes.setChecked(true);
        } else {
            studyAbroadNo.setChecked(true);
        }

        highestEducation.setText(mStaffAdvanceBean.getEducation());
        if (mStaffAdvanceBean.getMarried() != null) {
            switch (mStaffAdvanceBean.getMarried()) {
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

        }


        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, mStaffAdvanceBean);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_COMPANY_STAFF_PERSON_ADVANCED, map);
            }
        });

    }

    private void handlerJobDeleteResult(UpdateJobResult result) {
        if(result.Code == NetworkConstant.SUCCESS_CODE){
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_PERSON_ADVANCED, mStaffId);
        }
    }

    private void handlerStaffAdvanceInfo(StaffJobAdvancedResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            educationAdapter.setData(result.getContent().getEducationBgs());
        }
    }

    private void handlerEditResult(EditJobAdvancedResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.update_success));
                EventBus.getDefault().post(new RefreshStaffJobInfoEvent(false));
                this.finish();
            } else {
                AlertUtils.show("操作失败:" + result.Message);
            }
        } else {
            AlertUtils.show(result.Message);
        }
    }


    private void initAdapter() {
        workAdapter = new AttendanceStatisticsAdapter();
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workListView.setLayoutManager(commodityLayoutManager);
        workListView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        workListView.setAdapter(workAdapter);
        workAdapter.setData(workList);

        educationAdapter = new AttendanceStatisticsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        educationListView.setLayoutManager(layoutManager);
        educationListView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        educationListView.setAdapter(educationAdapter);
        educationAdapter.setData(educationList);


        certificateAdapter = new AttendanceStatisticsAdapter();
        LinearLayoutManager cflayoutManager = new LinearLayoutManager(context);
        cflayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        certificateListView.setLayoutManager(cflayoutManager);
        certificateListView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        certificateListView.setAdapter(certificateAdapter);
        certificateAdapter.setData(certificateList);
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
                                mStaffAdvanceBean.setMarried(0);
                                marriage.setText("未婚");
                                break;
                            case MarriageDialog.TYPE_MARRIED:
                                mStaffAdvanceBean.setMarried(1);
                                marriage.setText("已婚");
                                break;
                            case MarriageDialog.TYPE_SECRECY:
                                marriage.setText("保密");
                                mStaffAdvanceBean.setMarried(2);
                                break;
                        }
                    }
                });
                dialog.show();

                break;

            case R.id.is_study_abroad_yes:
                studyAbroadNo.setChecked(false);
                mStaffAdvanceBean.setStudyAbroad(true);

                break;
            case R.id.study_abroad_no:
                isStudyAbroadYes.setChecked(false);
                mStaffAdvanceBean.setStudyAbroad(false);
                break;
            case R.id.rl_political:
                PoliticalStatusDialog politicalStatusDialog = new PoliticalStatusDialog(context, new PoliticalStatusDialog.OnItemClickListener() {
                    @Override
                    public void onClick(PoliticalStatusDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case PoliticalStatusDialog.TYPE_CITIZEN:
                                political.setText("普通公民");
                                mStaffAdvanceBean.setPoliticsFace(0);
                                break;
                            case PoliticalStatusDialog.TYPE_PARTY_MEMBER_OF_CPC:
                                mStaffAdvanceBean.setPoliticsFace(1);
                                political.setText("中共党员");
                                break;
                            case PoliticalStatusDialog.TYPE_LEAGUE_MEMBER:
                                mStaffAdvanceBean.setPoliticsFace(2);
                                political.setText("共青团员");
                                break;
                            case PoliticalStatusDialog.TYPE_DEMOCRATIC_PROGRESSIVE_PARTY:
                                mStaffAdvanceBean.setPoliticsFace(3);
                                political.setText("民族党派人士");
                                break;
                            case PoliticalStatusDialog.TYPE_NON_PARTISAN:
                                mStaffAdvanceBean.setPoliticsFace(4);
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
                                censusRegister.setText("农村户口");
                                mStaffAdvanceBean.setHouseholdType(0);
                                break;
                            case ResidenceDialog.TYPE_CITY:
                                censusRegister.setText("城市户口");
                                mStaffAdvanceBean.setHouseholdType(1);
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
                        mStaffAdvanceBean.setEducation(((options1 + 1) * 10) + "");
                        highestEducation.setText(s);
                    }
                })
                        .setTitleText("学历")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                pvOptions.setPicker(educationList);
                pvOptions.show();
                break;
            case R.id.rlwork:
                WorkJobListActivity.startWorkJobListActivity(this, mStaffId);
                break;
            case R.id.rleducation:
                WorkEducationListActivity.startWorkEducationListActivity(this,mStaffId);
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
                    mStaffAdvanceBean.setNativePlaceProvince(options1Items.get(options1).getId());
                } else {
                    tx = options1Items.get(options1).getName() + options2Items.get(options1).get(options2);
                    mStaffAdvanceBean.setNativePlaceProvince(options1Items.get(options1).getId());
                    mStaffAdvanceBean.setNativePlaceCity(options1Items.get(options1).getChild().get(options2).getId());
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
        mStaffAdvanceBean.setProvince(Integer.getInteger(event.getProvince()));
        mStaffAdvanceBean.setCity(Integer.getInteger(event.getCity()));
        mStaffAdvanceBean.setDistrict(Integer.getInteger(event.getDistrict()));
        mStaffAdvanceBean.setAddress(event.getDetailedAddress());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_ID: //send the video
                id.setText(event.getContent());
                mStaffAdvanceBean.setIdCard(event.getContent());

            case ModifyDataActivity.TYPE_NATION: //send the video
                nation.setText(event.getContent());
                mStaffAdvanceBean.setNation(event.getContent());
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mEditStaffJobAdvancedInfoSubscription,mStaffSeniorSubscription,mUpdateJobSubscription);
    }
}
