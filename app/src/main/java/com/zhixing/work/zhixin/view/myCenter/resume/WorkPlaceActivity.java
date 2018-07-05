package com.zhixing.work.zhixin.view.myCenter.resume;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.HotCityAdapter;
import com.zhixing.work.zhixin.adapter.WorkCityAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.bean.WorkCity;
import com.zhixing.work.zhixin.event.JobRefreshEvent;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkPlaceActivity extends BaseTitleActivity {


    @BindView(R.id.location_city)
    CheckBox locationCity;
    @BindView(R.id.hot_list)
    RecyclerView hotList;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.listview)
    RecyclerView listview;
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private HotCityAdapter hotCityAdapter;
    private WorkCityAdapter workCityAdapter;
    private ArrayList<HotCity> cityList = new ArrayList<HotCity>();
    private List<HotCity> dataList = new ArrayList<HotCity>();
    private String resumeId;

    private Expect expect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_place);
        ButterKnife.bind(this);
        setTitle("工作地点");
        resumeId = getIntent().getStringExtra("resumeId");
        Bundle bundle = getIntent().getExtras();
        expect = (Expect) bundle.get("expect");
        setRightText1("保存");
        initView();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initView() {
        hotCityAdapter = new HotCityAdapter(cityList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotList.setLayoutManager(linearLayoutManager);
        hotList.setItemAnimator(new DefaultItemAnimator());
        hotList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        hotList.setAdapter(hotCityAdapter);
        hotCityAdapter.setOnItemClickListener(new HotCityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<HotCity> list) {
                HotCity city = cityList.get(position);
                dataList = getData(list);
                number.setText(dataList.size() + "/3");
                workCityAdapter.setList(dataList);
            }

            public void onItemLongClick(View view, int position) {
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        workCityAdapter = new WorkCityAdapter(dataList, context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        listview.setLayoutManager(gridLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.gray_line));
        listview.setAdapter(workCityAdapter);
        workCityAdapter.setOnItemClickListener(new WorkCityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HotCity bean = dataList.get(position);
                dataList.remove(position);
                workCityAdapter.setList(dataList);
                getList(bean.getId());

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() < 1) {
                    AlertUtils.toast(context, "未选择城市");
                    return;
                }
                addCity(gson.toJson(getCity(dataList)));
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
        String JsonData = Utils.getJson(this, "hotcity.json");//获取assets目录下的json文件数据
        cityList = gson.fromJson(JsonData, new TypeToken<List<HotCity>>() {
        }.getType());
        if (expect.getResumeExpectAreaOutputs() == null) {
            hotCityAdapter.setList(cityList);
        } else {
            setCityList(expect.getResumeExpectAreaOutputs());
            hotCityAdapter.setList(cityList);
            workCityAdapter.setList(dataList);
            number.setText(dataList.size() + "/3");
        }


        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    private void getList(int id) {

        for (int i = 0; i < cityList.size(); i++) {
            HotCity bean = cityList.get(i);
            if (id == bean.getId()) {
                bean.setIsSelect(0);
                cityList.set(i, bean);
            }
        }
        hotCityAdapter.setList(cityList);
    }


    private List<HotCity> getData(List<HotCity> list) {
        List<HotCity> dataList = new ArrayList<>();
        int number = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelect() == 1) {
                dataList.add(list.get(i));
            }
        }
        return dataList;
    }

    private List<WorkCity> getCity(List<HotCity> list) {
        List<WorkCity> dataList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            HotCity hotCity = list.get(i);
            WorkCity city = new WorkCity();
            city.setAreaId(hotCity.getId());
            dataList.add(city);

        }
        return dataList;
    }


    //提交数据
    private void addCity(String json) {
        OkUtils.getInstances().putJson(context, RequestConstant.EXPECT_AREA + "?resumeId=" + resumeId, json, new TypeToken<EntityObject<Boolean>>() {
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
                            if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
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

    public void setCityList(List<Expect.ResumeExpectAreaOutputsBean> list) {

        for (int i = 0; i < cityList.size(); i++) {
            HotCity hotCity = cityList.get(i);
            for (int j = 0; j < list.size(); j++) {
                Expect.ResumeExpectAreaOutputsBean bean = list.get(j);
                if (bean.getAreaId() == hotCity.getId()) {
                    hotCity.setIsSelect(1);
                    cityList.set(i, hotCity);
                    dataList.add(hotCity);
                }
            }
        }

    }
}
