package com.zhixing.work.zhixin.view.myCenter.resume;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.Salary;
import com.zhixing.work.zhixin.event.JobRefreshEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.Utils;

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

public class JobIntensionActivity extends BaseTitleActivity {
    @BindView(R.id.occupation)
    TextView occupation;
    @BindView(R.id.lloccupation)
    LinearLayout lloccupation;
    @BindView(R.id.industry)
    TextView industry;
    @BindView(R.id.llindustry)
    LinearLayout llindustry;
    @BindView(R.id.salary)
    TextView salary;
    @BindView(R.id.llsalary)
    LinearLayout llsalary;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.llcity)
    LinearLayout llcity;
    private List<String> constellationList;
    private List<String> educationList;
    private String resumeId;
    private Expect expect;
    private List<Expect.ResumeExpectAreaOutputsBean> cityList = new ArrayList<Expect.ResumeExpectAreaOutputsBean>();
    private List<Expect.ResumeExpectIndustryOutputsBean> IndustryList = new ArrayList<Expect.ResumeExpectIndustryOutputsBean>();
    private List<Expect.ResumeExpectJobOutputsBean> jobList = new ArrayList<Expect.ResumeExpectJobOutputsBean>();
    private List<Salary> options1Items = new ArrayList<Salary>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private String StartSalary = "0";
    private String EndSalary = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_intension);
        ButterKnife.bind(this);
        setTitle("管理求职意向");
        resumeId = getIntent().getStringExtra("resumeId");
        StartSalary = getIntent().getStringExtra("StartSalary");
        EndSalary = getIntent().getStringExtra("EndSalary");

        if (!TextUtils.isEmpty(StartSalary)) {
            if (StartSalary.equals("0")) {
                salary.setText("面议");

            } else {
                salary.setText(StartSalary + "k-" + EndSalary + "K");
            }
        }


        options1Items = getSalary();
        getData();
    }

    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.Expect, JavaParamsUtils.getInstances().Expect(resumeId), new TypeToken<EntityObject<Expect>>() {
        }.getType(), new ResultCallBackListener<Expect>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<Expect> response) {
                if (response.getCode() == 10000) {
                    expect = response.getContent();
                    cityList = expect.getResumeExpectAreaOutputs();
                    IndustryList = expect.getResumeExpectIndustryOutputs();
                    jobList = expect.getResumeExpectJobOutputs();

                    if (expect != null) {
                        initView();
                    }
                }
            }
        });
    }

    private void initView() {
        if (!cityList.isEmpty()) {

            city.setText(getCityString(cityList));
        }
        if (!IndustryList.isEmpty()) {

            industry.setText(getIndustryString(IndustryList));
        }
        if (!jobList.isEmpty()) {
            occupation.setText(getJobString(jobList));
        }
    }

    @OnClick({R.id.lloccupation, R.id.llindustry, R.id.llsalary, R.id.llcity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lloccupation:
                Intent intentJob = new Intent(context, JobActivity.class);
                Bundle bundleJob = new Bundle();
                bundleJob.putSerializable("expect", expect);
                intentJob.putExtra("resumeId", resumeId);
                intentJob.putExtras(bundleJob);
                startActivity(intentJob);

                break;
            case R.id.llindustry:
                Intent intent = new Intent(context, IndustryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("expect", expect);
                intent.putExtra("resumeId", resumeId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.llsalary:
                constellationList = Arrays.asList(getResources().getStringArray(R.array.constellation));
                educationList = Arrays.asList(getResources().getStringArray(R.array.education));
                OptionsPickerView options = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        //int id3 = options1Items.get(options1).getChild().get(options2).getChild().get(options3).getId();
                        if (options2Items.get(options1).get(option2).equals("")) {

                            StartSalary = "0";
                            EndSalary = "0";
                            final RequestBody body = new FormBody.Builder()
                                    .add("Id", resumeId)
                                    .add("StartSalary", StartSalary)
                                    .add("EndSalary", EndSalary)
                                    .build();
                            upUserData(body);
                        } else {
                            StartSalary = options1Items.get(options1).getName();
                            EndSalary = options2Items.get(options1).get(option2);
                            final RequestBody body = new FormBody.Builder()
                                    .add("Id", resumeId)
                                    .add("StartSalary", StartSalary)
                                    .add("EndSalary", EndSalary)
                                    .build();
                            upUserData(body);
                        }
                    }
                })
                        .setTitleText("薪资要求(月薪,单位:千元)")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setContentTextSize(20)//滚轮文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(options1Items, options2Items);
                options.show();
                break;
            case R.id.llcity:
                Intent intent1 = new Intent(context, WorkPlaceActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("expect", expect);
                intent1.putExtra("resumeId", resumeId);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }

    private String getCityString(List<Expect.ResumeExpectAreaOutputsBean> list) {

        String city = "";
        for (int i = 0; i < list.size(); i++) {

            if (i == list.size() - 1) {
                city = city + Utils.searchHotCity(list.get(i).getAreaId());
            } else {
                city = city + Utils.searchHotCity(list.get(i).getAreaId()) + "+";
            }
        }

        return city;
    }


    private String getIndustryString(List<Expect.ResumeExpectIndustryOutputsBean> list) {

        String industry = "";
        for (int i = 0; i < list.size(); i++) {

            if (i == list.size() - 1) {
                industry = industry + Utils.searchIndustry(list.get(i).getIndustryTypeId());
            } else {
                industry = industry + Utils.searchIndustry(list.get(i).getIndustryTypeId()) + "/";
            }
        }

        return industry;
    }

    private String getJobString(List<Expect.ResumeExpectJobOutputsBean> list) {

        String industry = "";
        for (int i = 0; i < list.size(); i++) {

            if (i == list.size() - 1) {
                industry = industry + Utils.searchjob(list.get(i).getJobTypeId());
            } else {
                industry = industry + Utils.searchjob(list.get(i).getJobTypeId()) + "/";
            }
        }

        return industry;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJobRefreshEvent(JobRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }

    private List<Salary> getSalary() {
        List<Salary> list = new ArrayList<>();

        for (int i = 0; i < 51; i++) {
            ArrayList<String> highestList = new ArrayList<String>();
            if (i == 0) {
                Salary salary = new Salary();
                salary.setName("面议");
                Salary.HighestBean highestBean = new Salary.HighestBean();
                highestBean.setName("");
                List<Salary.HighestBean> highestBeanList = new ArrayList<>();
                highestBeanList.add(highestBean);
                salary.setHighest(highestBeanList);
                list.add(salary);
                highestList.add("");
            } else {
                Salary salary = new Salary();
                salary.setName(i + "");
                List<Salary.HighestBean> highestBeanList = new ArrayList<>();
                for (int j = i + 1; j < (i * 2) + 1; j++) {
                    Salary.HighestBean highestBean = new Salary.HighestBean();
                    highestBean.setName(j + "");
                    highestBeanList.add(highestBean);
                    highestList.add(j + "");
                }
                salary.setHighest(highestBeanList);
                list.add(salary);
            }
            options2Items.add(highestList);
        }
        for (int i = 60; i < 251; i++) {
            ArrayList<String> highestList = new ArrayList<String>();
            if (i % 10 == 0) {
                Salary salary = new Salary();
                salary.setName(i + "");
                List<Salary.HighestBean> highestBeanList = new ArrayList<>();
                if (i < 130) {
                    for (int j = i + 1; j < (i * 2) + 1; j++) {
                        if (j % 10 == 0) {
                            Salary.HighestBean highestBean = new Salary.HighestBean();
                            highestBean.setName(j + "");
                            highestBeanList.add(highestBean);
                            highestList.add(j + "");
                        }

                    }
                    salary.setHighest(highestBeanList);
                    list.add(salary);
                } else {
                    for (int j = i + 1; j < 260 + 1; j++) {
                        if (j % 10 == 0) {
                            Salary.HighestBean highestBean = new Salary.HighestBean();
                            highestBean.setName(j + "");
                            highestBeanList.add(highestBean);
                            highestList.add(j + "");
                        }

                    }
                    salary.setHighest(highestBeanList);
                    list.add(salary);
                }


                options2Items.add(highestList);
            }


        }
        return list;
    }


    private void upUserData(RequestBody body) {

        OkUtils.getInstances().httpatch(body, context, JavaConstant.Resume, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
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
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            if (StartSalary.equals("0")) {
                                salary.setText("面议");
                            } else {
                                salary.setText(StartSalary + "k-" + EndSalary + "K");
                            }
                            EventBus.getDefault().post(new ResumeRefreshEvent(true));
                        }
                    } else {
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
    }
}
