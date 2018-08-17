package com.zhixing.work.zhixin.view.card.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.EducationListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.AddEducationActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EducationListActivity extends BaseTitleActivity {


    @BindView(R.id.education_list)
    RecyclerView educationList;
    @BindView(R.id.tv_education)
    TextView tvEducation;

    private List<Resume.EducationOutputsBean> list = new ArrayList<>();

    private EducationListAdapter educationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list);
        ButterKnife.bind(this);
        setTitle("教育经历");
        getData();
        initView();
    }

    private void initView() {
        educationListAdapter = new EducationListAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        educationList.setLayoutManager(commodityLayoutManager);
        educationList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        educationList.setAdapter(educationListAdapter);
        educationListAdapter.setOnItemClickListener(new EducationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, AddEducationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    //获取数据
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.ADD_EDUCATION_EXPERIENCE, JavaParamsUtils.getInstances().getCardAll(), new TypeToken<EntityObject<List<Resume.EducationOutputsBean>>>() {
        }.getType(), new ResultCallBackListener<List<Resume.EducationOutputsBean>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Resume.EducationOutputsBean>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    list = response.getContent();
                    educationListAdapter.setList(list);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResumeRefreshEvent(ResumeRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }

    @OnClick(R.id.tv_education)
    public void onViewClicked() {
        startActivity(new Intent(context, AddEducationActivity.class).putExtra("type", "resume"));
    }
}
