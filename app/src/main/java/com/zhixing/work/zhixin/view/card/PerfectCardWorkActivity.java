package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AddEducationAdapter;
import com.zhixing.work.zhixin.adapter.AddWorkAdapter;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Work;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.event.CardRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfectCardWorkActivity extends BaseTitleActivity {

    @BindView(R.id.onea_iv)
    ImageView oneaIv;
    @BindView(R.id.twoa_iv)
    ImageView twoaIv;
    @BindView(R.id.three_iv)
    ImageView threeIv;
    @BindView(R.id.four_iv)
    ImageView fourIv;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.add_work)
    RelativeLayout addWork;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.submit)
    Button submit;

    private AddWorkAdapter workAdapter;
    private Context context;
    private List<Work> list = new ArrayList<Work>();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_card_work);
        ButterKnife.bind(this);
        context = this;
        setTitle("完善卡牌");
        initView();
    }


    private void initView() {

        workAdapter = new AddWorkAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));

        listview.setAdapter(workAdapter);
    }


    @OnClick({R.id.add_work, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_work:
                startActivity(new Intent(context, AddWorkActivity
                        .class).putExtra("type", "card"));
                break;
            case R.id.submit:
                if (list.size() < 1) {
                    AlertUtils.toast(context, "请添加工作经历");
                    return;
                }
                addWork(gson.toJson(list));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWork(Work event) {
        list.add(event);
        workAdapter.setList(list);
    }


    //提交数据
    private void addWork(String json) {
        OkUtils.getInstances().postJson(context, JavaConstant.WorkBackground, json, new TypeToken<EntityObject<Object>>() {
        }.getType(), new ResultCallBackListener<Object>() {
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
            public void onSuccess(final EntityObject<Object> response) {
                hideLoadingDialog();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getCode() == 10000) {

                            AlertUtils.toast(context, "添加成功");
                            EventBus.getDefault().post(new CardRefreshEvent(true));
                            finish();
                        } else {
                            AlertUtils.toast(context, response.getMessage());
                        }


                    }
                });
            }
        });
    }
}
