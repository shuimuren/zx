package com.zhixing.work.zhixin.view.resume;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.JobAdapter;
import com.zhixing.work.zhixin.adapter.JobListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.JobType;
import com.zhixing.work.zhixin.event.JobListEvent;
import com.zhixing.work.zhixin.event.JobRefreshEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobListActivity extends BaseTitleActivity {


    @BindView(R.id.listview)
    RecyclerView listview;
    private int surplus;


    private JobListAdapter jobAdapter;

    private List<JobType> list = new ArrayList<>();
    private List<JobType> dataList = new ArrayList<>();

    private JobType jobType;
    private int number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        ButterKnife.bind(this);
        setTitle("期望职业");
        setRightText1("保存");

        Bundle bundle = getIntent().getExtras();
        jobType = (JobType) bundle.get("jobType");
        dataList = (List<JobType>) bundle.get("dataList");
        surplus = bundle.getInt("surplus");
        number = 3 - surplus;
        list = jobType.getChild();
        getList(dataList);
        initView();

        jobAdapter.setList(list, surplus);
    }

    private void getList(List<JobType> dataList) {

        if (dataList.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            JobType jobType = list.get(i);
            for (int j = 0; j < dataList.size(); j++) {
                JobType bean = dataList.get(j);
                if (bean.getId() == jobType.getId()) {
                    jobType.setIsSelect(1);
                    surplus++;
                    list.set(i, jobType);
                }
            }
        }
    }

    private void initView() {
        jobAdapter = new JobListAdapter(list, context, surplus);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(linearLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(jobAdapter);
        jobAdapter.setOnItemClickListener(new JobListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, JobType data, int type) {
                JobType jobType = list.get(position);
                if (type == 1) {
                    dataList.add(jobType);
                    deleteList(jobType.getParentId(), dataList);
                } else {
                    delete(jobType);
                }
                //workCityAdapter.setList(dataList);
            }

            public void onItemLongClick(View view, int position) {
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() <= number) {
                    AlertUtils.toast(context, "未选择职位");
                    return;
                }
                EventBus.getDefault().post(new JobListEvent(setDataList(dataList)));
                finish();
            }
        });
    }

    public List<JobType> setDataList(List<JobType> dataList) {
        List<JobType> setList = new ArrayList<JobType>();
        setList = dataList;
        for (int i = 0; i < list.size(); i++) {
            JobType jobType = list.get(i);
            for (int j = 0; j < dataList.size(); j++) {
                JobType bean = dataList.get(j);
                if (bean.getId() == jobType.getParentId()) {
                    setList.remove(j);
                } else {

                }
            }
        }
        return setList;
    }

    private void delete(JobType jobType) {
        for (int i = 0; i < dataList.size(); i++) {
            JobType bean = dataList.get(i);
            if (jobType.getId() == bean.getId()) {
                dataList.remove(i);
                break;
            }
        }
    }


    private void deleteList(int id, List<JobType> list) {

        List<JobType> setList = new ArrayList<JobType>();
        setList.addAll(list);
        for (int j = 0; j < setList.size(); j++) {
            JobType bean = setList.get(j);
            if (bean.getId() == id) {
                deleteData(bean);
            }
        }
    }

    private void deleteData(JobType jobType) {
        for (int i = 0; i < dataList.size(); i++) {
            JobType bean = dataList.get(i);
            if (jobType.getId() == bean.getId()) {
                dataList.remove(i);
                surplus++;
                number--;
                jobAdapter.setList(jobAdapter.getList(), surplus);
                break;
            }
        }
    }
}
