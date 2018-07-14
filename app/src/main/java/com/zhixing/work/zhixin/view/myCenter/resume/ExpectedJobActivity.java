package com.zhixing.work.zhixin.view.myCenter.resume;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.ExpectedJobAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.ExpectedJobBean;
import com.zhixing.work.zhixin.bean.JobChildBean;
import com.zhixing.work.zhixin.bean.JobParentBean;
import com.zhixing.work.zhixin.bean.JobSelectedBean;
import com.zhixing.work.zhixin.common.JobManagerHelper;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.ExpectedJobResult;
import com.zhixing.work.zhixin.requestbody.ExpectedJobBodyBean;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/11.
 * Description:
 */

public class ExpectedJobActivity extends BaseTitleActivity {
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tagLayout)
    TagFlowLayout tagLayout;
    @BindView(R.id.jobList)
    RecyclerView jobList;

    private ExpectedJobAdapter expectedJobAdapter;
    private JobManagerHelper jobManagerHelper;
    private String resumeId;
    private Expect expect;
    private TagAdapter<JobSelectedBean> mTagAdapter;
    private List<JobSelectedBean> mSelectJobs;
    private List<ExpectedJobBean> jobBeanList; //过度类
    private Subscription submitSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expectd_job);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.expected_job_title));
        setRightText1(ResourceUtils.getString(R.string.finish));
        initData();
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<JobSelectedBean> selectedList = jobManagerHelper.getJobSelectedBeanList();
                ExpectedJobBodyBean[] bodyBeans = new ExpectedJobBodyBean[selectedList.size()];
                if(selectedList.size() == 0){
                    AlertUtils.show(ResourceUtils.getString(R.string.alter_selected_null));
                    return;
                }
                for (int i = 0; i < selectedList.size(); i++) {
                    ExpectedJobBodyBean bean = new ExpectedJobBodyBean(selectedList.get(i).getId());
                    bodyBeans[i] = bean;
                }
                Map<String,Object> params = new HashMap<>();
                params.put(RequestConstant.KEY_RESUME_ID,resumeId);
                params.put(RequestConstant.KEY_REQUEST_BODY ,bodyBeans);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_UPDATE_EXPECTED_INFO,params);

            }
        });
        submitSubscription = RxBus.getInstance().toObservable(ExpectedJobResult.class).subscribe(
                handler -> submitResult(handler)
        );


    }

    private void submitResult(ExpectedJobResult handler) {
        if (NetworkConstant.SUCCESS_CODE == handler.Code) {
            if (handler.isContent()) {
                AlertUtils.show(ResourceUtils.getString(R.string.handler_success));
            }
            this.finish();
        } else {
            AlertUtils.show(handler.Message);
        }
    }


    private void initData() {
        jobBeanList = new ArrayList<>();
        jobManagerHelper = JobManagerHelper.getJobManagerHelperInstance();
        jobManagerHelper.clearSelectedData();
        resumeId = getIntent().getStringExtra("resumeId");
        Bundle bundle = getIntent().getExtras();
        expect = (Expect) bundle.get("expect");
        mSelectJobs = jobManagerHelper.getJobSelectedBeanList();
        initNumText();
        mTagAdapter = new TagAdapter<JobSelectedBean>(mSelectJobs) {
            @Override
            public View getView(FlowLayout parent, int position, JobSelectedBean jobSelectedBean) {
                TextView tv = (TextView) LayoutInflater.from(ExpectedJobActivity.this).inflate(R.layout.item_text, tagLayout, false);
                tv.setText(jobSelectedBean.getName());
                return tv;
            }
        };

        tagLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ExpectedJobBean job = jobBeanList.get(position);
                jobManagerHelper.removeSelectedJobBean(mSelectJobs.size(), job.getId(), job.getPosition(), job.getGroupPosition(), job.getChildPosition());
                jobBeanList.remove(position);
                expectedJobAdapter.notifyDataSetChanged();
                mTagAdapter.notifyDataChanged();
                initNumText();
                return true;
            }
        });

        tagLayout.setAdapter(mTagAdapter);

        expectedJobAdapter = new ExpectedJobAdapter(ExpectedJobActivity.this, jobManagerHelper.getJobChoice());
        jobList.setLayoutManager(new LinearLayoutManager(ExpectedJobActivity.this));
        jobList.setAdapter(expectedJobAdapter);
        jobList.setHasFixedSize(false);
        expectedJobAdapter.setViewChangedListener(new ExpectedJobAdapter.OnViewChangedListener() {
            @Override
            public void selectedGroup(int currentPosition, JobParentBean parentBean, int groupPosition, boolean isChecked) {
                initNumText();
                mTagAdapter.notifyDataChanged();
                if (!isChecked) {
                    ExpectedJobBean bean = new ExpectedJobBean(parentBean.getId(), currentPosition, groupPosition, -1);
                    jobBeanList.add(bean);
                } else {
                    for (int i = 0; i < jobBeanList.size(); i++) {
                        if (jobBeanList.get(i).getId() == parentBean.getId()) {
                            jobBeanList.remove(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void selectedChild(int currentPosition, JobChildBean childBean, int groupPosition, int childPosition, boolean isChecked) {
                initNumText();
                mTagAdapter.notifyDataChanged();
                if (!isChecked) {
                    ExpectedJobBean bean = new ExpectedJobBean(childBean.getId(), currentPosition, groupPosition, childPosition);
                    jobBeanList.add(bean);
                } else {
                    for (int i = 0; i < jobBeanList.size(); i++) {
                        if (jobBeanList.get(i).getId() == childBean.getId()) {
                            jobBeanList.remove(i);
                            break;
                        }
                    }
                }

            }

        });
    }

    private void initNumText() {
        tvNumber.setText(String.format("%s/3", mSelectJobs.size()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(submitSubscription);
    }
}
