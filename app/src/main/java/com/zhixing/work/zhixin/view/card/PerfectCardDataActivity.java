package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressBean;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.dialog.MarriageDialog;
import com.zhixing.work.zhixin.dialog.PoliticalStatusDialog;
import com.zhixing.work.zhixin.dialog.ResidenceDialog;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PerfectCardDataActivity extends BaseTitleActivity {


    @BindView(R.id.onea_iv)
    ImageView oneaIv;
    @BindView(R.id.twoa_iv)
    ImageView twoaIv;
    @BindView(R.id.three_iv)
    ImageView threeIv;
    @BindView(R.id.four_iv)
    ImageView fourIv;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.iv_constellation)
    ImageView ivConstellation;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.iv_name)
    ImageView ivName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.rl_constellation)
    RelativeLayout rlConstellation;
    @BindView(R.id.highest_education)
    TextView highestEducation;
    @BindView(R.id.iv_highest_education)
    ImageView ivHighestEducation;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;


    @BindView(R.id.iv_datejob)
    ImageView ivDatejob;
    @BindView(R.id.rl_worktime)
    RelativeLayout rlWorktime;
    @BindView(R.id.iv_Motto)
    ImageView ivMotto;
    @BindView(R.id.all_iv)
    ImageView allIv;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.iv_height)
    ImageView ivHeight;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.iv_weight)
    ImageView ivWeight;
    @BindView(R.id.rl_weight)
    RelativeLayout rlWeight;
    @BindView(R.id.marriage)
    TextView marriage;
    @BindView(R.id.iv_marriage)
    ImageView ivMarriage;
    @BindView(R.id.rl_marriage)
    RelativeLayout rlMarriage;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.iv_id)
    ImageView ivId;
    @BindView(R.id.rl_id)
    RelativeLayout rlId;
    @BindView(R.id.census_register)
    TextView censusRegister;
    @BindView(R.id.iv_datejob3)
    ImageView ivDatejob3;
    @BindView(R.id.rl_census_register)
    RelativeLayout rlCensusRegister;
    @BindView(R.id.iv_datejob3aasd)
    ImageView ivDatejob3aasd;
    @BindView(R.id.political)
    TextView political;
    @BindView(R.id.iv_political)
    ImageView ivPolitical;
    @BindView(R.id.rl_political)
    RelativeLayout rlPolitical;
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
    @BindView(R.id.adress)
    TextView adress;
    @BindView(R.id.iv_adress)
    ImageView ivAdress;
    @BindView(R.id.rl_adress)
    RelativeLayout rlAdress;
    @BindView(R.id.llMore)
    LinearLayout llMore;
    @BindView(R.id.btn_go_compete_edu)
    Button btnGoCompeteEdu;
    @BindView(R.id.rl_motto)
    RelativeLayout rlMotto;
    @BindView(R.id.motto_tx)
    TextView mottoTx;
    @BindView(R.id.is_study_abroad_yes)
    CheckBox isStudyAbroadYes;
    @BindView(R.id.study_abroad_no)
    CheckBox studyAbroadNo;
    @BindView(R.id.work_time)
    TextView workTime;
    @BindView(R.id.constellation_text)
    TextView constellationText;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.weight)
    TextView weight;


    private boolean isOpen = false;
    private Context context;


    private String nikename = "nikename";
    private Map<String, String> dataMap = new HashMap<>();
    private String motto = "motto";
    private String time_birth = "time_birth";
    private String time_work = "time_work";
    private String study_abroad = "study_abroad";
    private String education = "education";
    private String constellation = "constellation";
    private String isstudy_abroad;
    private List<String> constellationList;
    private String[] menuList  ;
    private List<String> list;
    private int constellationType;

    private String Stature = "";
    private String Weight = "";
    private String Married = "";
    private String IdCard = "";
    private String HouseholdType = "";
    private String PoliticsFace = "";
    private String Nation = "";
    private String NativePlaceProvince = "";
    private String NativePlaceCity = "";
    private String Province = "";
    private String City = "";
    private String District = "";
    private String Address = "";
    private String detailedAddress = "";

    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    //完善基础资料
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_card_data);
        ButterKnife.bind(this);
        context = this;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        setTitle("完善卡牌");
    }


    @OnClick({R.id.rl_date, R.id.rl_name, R.id.rl_id, R.id.rl_height, R.id.is_study_abroad_yes, R.id.study_abroad_no, R.id.rl_motto, R.id.rl_constellation, R.id.rl_education, R.id.rl_worktime, R.id.all_iv, R.id.rl_weight, R.id.rl_marriage, R.id.rl_census_register, R.id.rl_political, R.id.rl_nation, R.id.rl_native_place, R.id.rl_adress, R.id.btn_go_compete_edu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_date:
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        date.setText(time);
                        dataMap.put(time_birth, time);
                        isComplete();
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("出生日期")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.rl_name:
                startActivity(new Intent(context, ModifyDataActivity.class).putExtra("title", "填写昵称").
                        putExtra("type", "nakename")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT,name.getText().toString())
                );
                break;
            case R.id.rl_constellation:
                //星座选择
                constellationList = Arrays.asList(getResources().getStringArray(R.array.constellation));
                OptionsPickerView options = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        constellationType = options1;
                        String s = constellationList.get(options1);
                        constellationText.setText(s);
                        dataMap.put(constellation, options1 + "");
                        isComplete();
                    }
                })
                        .setTitleText("星座")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(constellationList);
                options.show();
                break;
            case R.id.rl_education:
//                学历选择
                menuList = getResources().getStringArray(R.array.education);
                list = Arrays.asList(menuList);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = list.get(options1);
                        highestEducation.setText(s);
                        dataMap.put(education, ((options1 + 1) * 10) + "");
                        isComplete();
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
            case R.id.rl_worktime:
                //工作时间
                TimePickerView work_Time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        workTime.setText(time);
                        dataMap.put(time_work, time);
                        isComplete();
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("工作时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                work_Time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                work_Time.show();
                break;
            case R.id.all_iv:
                if (isOpen) {
                    llMore.setVisibility(View.GONE);
                    isOpen = false;
                } else {
                    isOpen = true;
                    llMore.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_weight:
                startActivity(new Intent(context, ModifyUserActivity.class).
                        putExtra(ModifyUserActivity.TYPE_TITLE, "体重").
                        putExtra(ModifyUserActivity.TYPE, ModifyUserActivity.TYPE_WEIGHT).
                        putExtra(ModifyUserActivity.TYPE_COMPANY, "kg").
                        putExtra(ModifyUserActivity.TYPE_CONTENT,weight.getText().toString()));


                break;

            case R.id.rl_height:
                startActivity(new Intent(context, ModifyUserActivity.class).
                        putExtra(ModifyUserActivity.TYPE_TITLE, "身高").
                        putExtra(ModifyUserActivity.TYPE, ModifyUserActivity.TYPE_HEIGHT).
                        putExtra(ModifyUserActivity.TYPE_COMPANY, "cm").
                        putExtra(ModifyUserActivity.TYPE_CONTENT,height.getText().toString()));
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
            case R.id.rl_nation:
                startActivity(new Intent(context, ModifyDataActivity.class)
                        .putExtra("title", "民族").putExtra("type", ModifyDataActivity.TYPE_NATION)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT,nation.getText().toString())
                );
                break;
            case R.id.rl_native_place:

                if (isLoaded) {
                    ShowPickerView();
                } else {

                }

                break;
            case R.id.rl_adress:
                startActivity(new Intent(context,Addressctivity.class ));
                break;
            case R.id.rl_id:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "身份证号码").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_ID)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT,id.getText().toString())
                );
                break;
            case R.id.is_study_abroad_yes:
                studyAbroadNo.setChecked(false);
                isstudy_abroad = "true";
                dataMap.put(study_abroad, isstudy_abroad);
                isComplete();
                break;
            case R.id.study_abroad_no:
                isStudyAbroadYes.setChecked(false);
                isstudy_abroad = "false";
                dataMap.put(study_abroad, isstudy_abroad);
                isComplete();
                break;
            case R.id.rl_motto:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写座右铭").putExtra("type", "motto")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT,mottoTx.getText().toString())
                );
                break;
            case R.id.btn_go_compete_edu:
                RequestBody body = new FormBody.Builder()
                        .add("NickName", dataMap.get("nakename"))
                        .add("Birthday", dataMap.get(time_birth))
                        .add("Constellation", dataMap.get(constellation))
                        .add("Education", dataMap.get(education))
                        .add("StudyAbroad", dataMap.get(study_abroad))
                        .add("FirstWorkTime", dataMap.get(time_work))
                        .add("Motto", dataMap.get("motto"))
                        .add("Stature", Stature)
                        .add("Weight", Weight)
                        .add("Married", Married)
                        .add("IdCard", IdCard)
                        .add("HouseholdType", HouseholdType)
                        .add("PoliticsFace", PoliticsFace)
                        .add("Nation", Nation)
                        .add("NativePlaceProvince", NativePlaceProvince)
                        .add("NativePlaceCity", NativePlaceCity)
                        .add("Province", Province)
                        .add("City", City)
                        .add("District", District)
                        .add("Address", Address)
                        .build();
                Log.d(TAG, body.toString());
                submit(body);


                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_NICKNAME: //send the video
                name.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;
            case ModifyDataActivity.TYPE_MOTTO: //send the video
                mottoTx.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;
            case ModifyDataActivity.TYPE_ID: //send the video
                id.setText(event.getContent());
                IdCard = event.getContent();
            case ModifyDataActivity.TYPE_NATION: //send the video
                nation.setText(event.getContent());
                break;
            case ModifyUserActivity.TYPE_WEIGHT: //send the video

                weight.setText(event.getContent());
                Weight = event.getContent();

                break;
            case ModifyUserActivity.TYPE_HEIGHT: //send the video
                height.setText(event.getContent());
                Stature = event.getContent();
                break;

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressBean(AddressBean event) {

        adress.setText(event.getDetailedAddress());
        Province = event.getProvince();
        City = event.getCity();
        District = event.getDistrict();
        Address = event.getAddress();
        detailedAddress = event.getDetailedAddress();
    }

    //判断是否填写完毕
    private void isComplete() {


        if (dataMap.values().size() >= 7) {
            btnGoCompeteEdu.setEnabled(true);
            btnGoCompeteEdu.setBackgroundResource(R.color.color_71aae0);

        } else {
            btnGoCompeteEdu.setEnabled(false);
            btnGoCompeteEdu.setBackgroundResource(R.color.hardtoast);
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
    private void submit(RequestBody body) {
        OkUtils.getInstances().httput(body, context, JavaConstant.personalinfo, JavaParamsUtils.getInstances().personalinfo(), new TypeToken<EntityObject<Object>>() {
        }.getType(), new ResultCallBackListener<Object>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Object> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    startActivity(new Intent(context, PerfectCardEducationActivity.class));
                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
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

    private void HideKeyboard() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager manager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }, 10);
    }
}
