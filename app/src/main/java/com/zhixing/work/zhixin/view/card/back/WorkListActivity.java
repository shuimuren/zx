package com.zhixing.work.zhixin.view.card.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.WorkListAdapter;
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
import com.zhixing.work.zhixin.view.card.AddWorkActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkListActivity extends BaseTitleActivity {

    @BindView(R.id.work_list)
    RecyclerView workList;
    @BindView(R.id.add_work)
    ImageView addWork;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.rl_add_work)
    RelativeLayout rlAddWork;
    private List<Resume.WrokBackgroundOutputsBean> list = new ArrayList<Resume.WrokBackgroundOutputsBean>();
    private WorkListAdapter workListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);
        setTitle("工作经历");
        getData();
        initView();
    }

    private void initView()

    {
        workListAdapter = new WorkListAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workList.setLayoutManager(commodityLayoutManager);
        workList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        workList.setAdapter(workListAdapter);
        workListAdapter.setOnItemClickListener(new WorkListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, AddWorkActivity.class);
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
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.WorkBackground, JavaParamsUtils.getInstances().getCardAll(), new TypeToken<EntityObject<List<Resume.WrokBackgroundOutputsBean>>>() {
        }.getType(), new ResultCallBackListener<List<Resume.WrokBackgroundOutputsBean>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Resume.WrokBackgroundOutputsBean>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    list = response.getContent();
                    workListAdapter.setList(list);
                }
            }
        });
    }
    @OnClick(R.id.rl_add_work)
    public void onViewClicked() {
        startActivity(new Intent(context, AddWorkActivity.class).putExtra("type", "resume"));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResumeRefreshEvent(ResumeRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
}
