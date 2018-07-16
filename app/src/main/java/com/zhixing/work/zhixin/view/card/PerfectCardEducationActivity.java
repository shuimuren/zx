package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AddEducationAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 教育经历
 */
public class PerfectCardEducationActivity extends BaseTitleActivity {


    @BindView(R.id.onea_iv)
    ImageView oneaIv;
    @BindView(R.id.twoa_iv)
    ImageView twoaIv;
    @BindView(R.id.three_iv)
    ImageView threeIv;
    @BindView(R.id.four_iv)
    ImageView fourIv;
    @BindView(R.id.add_edcation)
    RelativeLayout addEdcation;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.rl_go_certificate)
    RelativeLayout rlGoCertificate;
    private List<Education> list = new ArrayList<Education>();
    private AddEducationAdapter addEducationAdapter;

    private Gson gson = new Gson();
    public static PerfectCardEducationActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_card_education);
        ButterKnife.bind(this);
        initView();
        setTitle("完善卡牌");
    }

    private void initView() {

        addEducationAdapter = new AddEducationAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        //addressAdapter.setOnItemClickListener(this);
        listview.setAdapter(addEducationAdapter);
    }

    @OnClick({R.id.add_edcation, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_edcation:
                startActivity(new Intent(context, AddEducationActivity.class).putExtra("type", "card"));
                break;
            case R.id.btn_next:
                if (list.size() < 1) {
                    AlertUtils.toast(context, "请添加教育经历");
                    return;
                }

                new Thread() {
                    @Override
                    public void run() {

                        addEducation(gson.toJson(list));
                    }
                }.start();


                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEducation(Education event) {
        list.add(event);
        addEducationAdapter.setList(list);
    }

    //提交数据
    private void addEducation(String json) {
        OkUtils.getInstances().postJson(context, RequestConstant.ADD_EDUCATION_EXPERIENCE, json, new TypeToken<EntityObject<Object>>() {
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
                        try {
                            if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                                AlertUtils.toast(context, "添加成功");
                                startActivity(new Intent(context, PerfectCardCertificateActivity.class));
                                finish();
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
}
