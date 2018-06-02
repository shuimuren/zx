package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AddCertificateAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Certificate;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfectCardCertificateActivity extends BaseTitleActivity {


    @BindView(R.id.onea_iv)
    ImageView oneaIv;
    @BindView(R.id.twoa_iv)
    ImageView twoaIv;
    @BindView(R.id.three_iv)
    ImageView threeIv;
    @BindView(R.id.four_iv)
    ImageView fourIv;
    @BindView(R.id.not_certificate)
    CheckBox notCertificate;

    @BindView(R.id.btn_work)
    Button btnWork;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.add_certificate)
    RelativeLayout addCertificate;


    private List<Certificate> list = new ArrayList<Certificate>();
    private Gson gson = new Gson();

    private AddCertificateAdapter adapter;

    public static PerfectCardCertificateActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_card_certificate);
        ButterKnife.bind(this);
        initView();
        instance = this;
        setTitle("完善卡牌");


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCertificate(Certificate event) {
        list.add(event);
        adapter.setList(list);

    }

    private void initView() {
        adapter = new AddCertificateAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        //addressAdapter.setOnItemClickListener(this);
        listview.setAdapter(adapter);
    }

    //提交数据
    private void addCertificate(String json) {
        OkUtils.getInstances().postJson(context, JavaConstant.CertificateBackground, json, new TypeToken<EntityObject<Boolean>>() {
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
                if (response.getCode() == 10000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (response.getContent()) {

                                    AlertUtils.toast(context, "添加成功");
                                    finish();
                                    startActivity(new Intent(context, PerfectCardWorkActivity.class));
                                } else {
                                    AlertUtils.toast(context, response.getMessage());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AlertUtils.toast(context, response.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }


            }
        });

    }

    @OnClick({R.id.add_certificate, R.id.btn_work, R.id.not_certificate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_certificate:
                startActivity(new Intent(context, AddCertificateActivity.class).putExtra("type", "card"));

                break;
            case R.id.not_certificate:
                if (notCertificate.isChecked()) {
                    llData.setVisibility(View.GONE);
                } else {
                    llData.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_work:

                if (notCertificate.isChecked()) {
                    startActivity(new Intent(context, PerfectCardWorkActivity.class));
                    finish();
                } else {
                    if (list.size() < 1) {
                        AlertUtils.toast(context, "请添加证书");
                        return;
                    }
                    addCertificate(gson.toJson(list));
                }


                break;
        }
    }
}
