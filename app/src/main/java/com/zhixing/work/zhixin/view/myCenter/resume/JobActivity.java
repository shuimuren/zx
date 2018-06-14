package com.zhixing.work.zhixin.view.myCenter.resume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.JobAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.JobData;
import com.zhixing.work.zhixin.bean.JobType;
import com.zhixing.work.zhixin.event.JobListEvent;
import com.zhixing.work.zhixin.event.JobRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobActivity extends BaseTitleActivity {

    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.job_list)
    RecyclerView jobList;
    private TagAdapter<JobType> mAdapter;
    private Expect expect;
    private String resumeId;
    private Gson gson = new Gson();
    List<JobType> jobDataList = new ArrayList<>();
    List<JobType> beseList = new ArrayList<>();
    List<JobType> jsonList = new ArrayList<>();
    List<JobType> dataList = new ArrayList<>();
    private JobAdapter jobAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ButterKnife.bind(this);
        resumeId = getIntent().getStringExtra("resumeId");
        Bundle bundle = getIntent().getExtras();
        expect = (Expect) bundle.get("expect");
        setTitle("期望职位");
        setRightText1("完成");
        jsonList = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getJobList())) {
            jsonList = gson.fromJson(SettingUtils.getJobList(), new TypeToken<List<JobType>>() {
            }.getType());
        }
        jobDataList = getJobList(jsonList);

        intView();
        //判断现有的职位
        if (expect.getResumeExpectJobOutputs().isEmpty()) {
            jobAdapter.setList(jobDataList, 3 - dataList.size());
        } else {
            setList(expect.getResumeExpectJobOutputs());
            jobAdapter.setList(jobDataList, 3 - dataList.size());
            mAdapter.notifyDataChanged();
            number.setText(dataList.size() + "/3");
        }
    }

    private void intView() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        jobAdapter = new JobAdapter(jobDataList, context, 3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        jobList.setLayoutManager(linearLayoutManager);
        jobList.setItemAnimator(new DefaultItemAnimator());
        jobList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        jobList.setAdapter(jobAdapter);
        jobAdapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, JobType data, int type) {
                JobType jobType = jobDataList.get(position);

                if (type == 1) {
                    dataList.add(jobType);
                    delete(jobType.getId(), dataList);
                } else {
                    dataList.remove(jobType);
                }
                number.setText(dataList.size() + "/3");
                mAdapter.notifyDataChanged();
                jobAdapter.setList(jobAdapter.getList(), 3 - dataList.size());
                //workCityAdapter.setList(dataList);
            }
            public void onItemLongClick(View view, int position) {
                if (dataList.size() == 3) {
                    AlertUtils.toast(context, "不能超过3个");
                    return;
                }
                JobType jobType = jobDataList.get(position);
                Intent intent = new Intent(context, JobListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("jobType", jobType);
                bundle.putInt("surplus", 3 - dataList.size());
                bundle.putSerializable("dataList", (Serializable) dataList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        idFlowlayout.setAdapter(mAdapter = new TagAdapter<JobType>(dataList) {
            @Override
            public View getView(FlowLayout parent, int position, JobType s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_text,
                        idFlowlayout, false);
                tv.setText(s.getName());
                return tv;
            }


        });

        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                JobType bean = dataList.get(position);


                dataList.remove(position);
                mAdapter.notifyDataChanged();
                getList(bean.getId());
                //view.setVisibility(View.GONE);


                return true;
            }
        });
        //提交数据
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.isEmpty()) {
                    AlertUtils.toast(context, "未选择职位");
                    return;
                }
                addData(gson.toJson(getSubmitData(dataList)));
            }
        });
    }

    private List<JobType> getJobList(List<JobType> dataList) {
        List<JobType> list = new ArrayList<JobType>();
        for (int i = 0; i < dataList.size(); i++) {
            JobType jobType = dataList.get(i);
            list.add(jobType);
            for (int j = 0; j < jobType.getChild().size(); j++) {
                JobType bean = jobType.getChild().get(j);
                list.add(bean);
            }
        }
        return list;
    }

    private List<JobData> getSubmitData(List<JobType> list) {
        List<JobData> dataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JobType data = list.get(i);
            JobData jobData = new JobData();
            jobData.setJobTypeId(data.getId());
            dataList.add(jobData);
        }
        return dataList;
    }
    //删除选中的
    private void getList(int id) {
        for (int i = 0; i < jobDataList.size(); i++) {
            JobType bean = jobDataList.get(i);
            if (id == bean.getId()) {
                bean.setIsSelect(0);
                jobDataList.set(i, bean);
            }
        }
        jobAdapter.setList(jobDataList, 3 - dataList.size());
    }
    private void addData(String json) {
        OkUtils.getInstances().putJson(context, JavaConstant.ExpectJob + "?resumeId=" + resumeId, json, new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response.getCode() == 10000) {
                                if (response.getContent()) {
                                    AlertUtils.toast(context, "添加成功");
                                    EventBus.getDefault().post(new JobRefreshEvent(true));
                                    finish();
                                } else {
                                    AlertUtils.toast(context, response.getMessage());
                                }
                            } else {
                                AlertUtils.toast(context, response.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJobListEvent(JobListEvent event) {
        setjobList(event.getData());
        dataList.clear();
        dataList.addAll(event.getData());
        mAdapter.notifyDataChanged();
        jobAdapter.setList(jobDataList, 3 - dataList.size());
        number.setText(dataList.size() + "/3");
    }

    public void setList(List<Expect.ResumeExpectJobOutputsBean> list) {

        for (int i = 0; i < jobDataList.size(); i++) {
            JobType jobType = jobDataList.get(i);
            for (int j = 0; j < list.size(); j++) {
                Expect.ResumeExpectJobOutputsBean bean = list.get(j);
                if (bean.getJobTypeId() == jobType.getId()) {
                    jobType.setIsSelect(1);
                    jobDataList.set(i, jobType);
                    dataList.add(jobType);
                }


            }
        }


        for (int i = 0; i < jobDataList.size(); i++) {
            JobType jobType = jobDataList.get(i);
            for (int k = 0; k < jobType.getChild().size(); k++) {
                JobType jobType1 = jobType.getChild().get(k);
                for (int j = 0; j < list.size(); j++) {
                    Expect.ResumeExpectJobOutputsBean bean = list.get(j);
                    if (bean.getJobTypeId() == jobType1.getId()) {
                        dataList.add(jobType1);
                    }
                }
            }
        }
    }


    //设置选择返回的数据
    public void setjobList(List<JobType> list) {

        for (int i = 0; i < jobDataList.size(); i++) {
            JobType jobType = jobDataList.get(i);
            for (int j = 0; j < list.size(); j++) {
                JobType bean = list.get(j);
                if (bean.getId() == jobType.getId()) {
                    jobType.setIsSelect(1);
                    jobDataList.set(i, jobType);
                } else {
                    jobType.setIsSelect(0);
                    jobDataList.set(i, jobType);
                }
            }
        }
    }


    private void delete(int id, List<JobType> list) {

        List<JobType> setList = new ArrayList<JobType>();
        setList.addAll(list);
        for (int j = 0; j < setList.size(); j++) {
            JobType bean = setList.get(j);
            if (bean.getParentId() == id) {
                deleteData(bean);
            }
        }
    }
    private void deleteData(JobType jobType) {
        for (int i = 0; i < dataList.size(); i++) {
            JobType bean = dataList.get(i);
            if (jobType.getId() == bean.getId()) {
                dataList.remove(i);
                break;
            }
        }
    }

}