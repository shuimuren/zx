package com.zhixing.work.zhixin.view.resume;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.IndustryAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Expect;
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.bean.IndustryData;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.bean.WorkCity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndustryActivity extends BaseTitleActivity {

    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.industry_list)
    RecyclerView industryList;

    List<IndustryType> industryTypeList = new ArrayList<>();
    List<IndustryType> dataList = new ArrayList<>();
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    private IndustryAdapter industryAdapter;
    private TagAdapter<IndustryType> mAdapter;
    private Expect expect;
    private String resumeId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry);
        resumeId = getIntent().getStringExtra("resumeId");
        Bundle bundle = getIntent().getExtras();
        expect = (Expect) bundle.get("expect");
        ButterKnife.bind(this);
        setTitle("期望行业");
        setRightText1("完成");
        List<IndustryType> list = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getIndustry())) {
            list = gson.fromJson(SettingUtils.getIndustry(), new TypeToken<List<IndustryType>>() {
            }.getType());
        }
        industryTypeList = getIndustryTypeList(list);
        intView();


        if (expect.getResumeExpectAreaOutputs().isEmpty()) {
            industryAdapter.setList(industryTypeList);
        } else {
            setList(expect.getResumeExpectIndustryOutputs());
            industryAdapter.setList(industryTypeList);
            mAdapter.notifyDataChanged();
            number.setText(dataList.size() + "/3");
        }

    }

    private void intView() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        industryAdapter = new IndustryAdapter(industryTypeList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        industryList.setLayoutManager(linearLayoutManager);
        industryList.setItemAnimator(new DefaultItemAnimator());
        industryList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        industryList.setAdapter(industryAdapter);
        industryAdapter.setOnItemClickListener(new IndustryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, IndustryType data, int type) {
                IndustryType industryType = industryTypeList.get(position);

                if (type == 1) {
                    dataList.add(industryType);
                } else {
                    dataList.remove(industryType);
                }
                number.setText(dataList.size() + "/3");
                mAdapter.notifyDataChanged();

                //workCityAdapter.setList(dataList);
            }

            public void onItemLongClick(View view, int position) {
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        idFlowlayout.setAdapter(mAdapter = new TagAdapter<IndustryType>(dataList) {
            @Override
            public View getView(FlowLayout parent, int position, IndustryType s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_text,
                        idFlowlayout, false);
                tv.setText(s.getName());
                return tv;
            }


        });

        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                IndustryType bean = dataList.get(position);
                dataList.remove(position);
                mAdapter.notifyDataChanged();
                getList(bean.getId());

                //view.setVisibility(View.GONE);
                return true;
            }
        });


        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() < 1) {
                    AlertUtils.toast(context, "未选择行业");
                    return;
                }
                addData(gson.toJson(getCity(dataList)));
            }
        });
    }


    private List<IndustryData> getCity(List<IndustryType> list) {
        List<IndustryData> dataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            IndustryType data = list.get(i);
            IndustryData industryData = new IndustryData();
            industryData.setIndustryTypeId(data.getId());
            dataList.add(industryData);
        }
        return dataList;
    }

    //提交数据
    private void addData(String json) {
        OkUtils.getInstances().putJson(context, JavaConstant.ExpectIndustry + "?resumeId=" + resumeId, json, new TypeToken<EntityObject<Boolean>>() {
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


    private void getList(int id) {

        for (int i = 0; i < industryTypeList.size(); i++) {
            IndustryType bean = industryTypeList.get(i);
            if (id == bean.getId()) {
                bean.setIsSelect(0);
                industryTypeList.set(i, bean);
            }
        }
        industryAdapter.setList(industryTypeList);
    }

    private List<IndustryType> getData(List<IndustryType> list) {
        List<IndustryType> dataList = new ArrayList<>();
        int number = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsSelect() == 1) {
                dataList.add(list.get(i));
            }
        }
        return dataList;
    }

    private List<IndustryType> getIndustryTypeList(List<IndustryType> dataList) {
        List<IndustryType> list = new ArrayList<IndustryType>();
        for (int i = 0; i < dataList.size(); i++) {
            IndustryType industryType = dataList.get(i);
            list.add(industryType);
            for (int j = 0; j < industryType.getChild().size(); j++) {
                IndustryType bean = industryType.getChild().get(j);
                list.add(bean);
            }
        }
        return list;
    }


    public void setList(List<Expect.ResumeExpectIndustryOutputsBean> list) {

        for (int i = 0; i < industryTypeList.size(); i++) {
            IndustryType industryType = industryTypeList.get(i);
            for (int j = 0; j < list.size(); j++) {
                Expect.ResumeExpectIndustryOutputsBean bean = list.get(j);
                if (bean.getIndustryTypeId() == industryType.getId()) {
                    industryType.setIsSelect(1);
                    industryTypeList.set(i, industryType);
                    dataList.add(industryType);
                }
            }
        }

    }
}
