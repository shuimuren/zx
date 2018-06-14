package com.zhixing.work.zhixin.view.companyCard;

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
import com.zhixing.work.zhixin.adapter.CompayIndustryAdapter;
import com.zhixing.work.zhixin.adapter.IndustryAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.event.CardRefreshEvent;
import com.zhixing.work.zhixin.event.CompanyIndustryEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyIndustryActivity extends BaseTitleActivity {


    List<IndustryType> dataList = new ArrayList<>();
    List<IndustryType> industryTypeList = new ArrayList<>();
    @BindView(R.id.listview)
    RecyclerView listview;
    private CompayIndustryAdapter industryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_industry);
        ButterKnife.bind(this);
        setTitle("行业");
        setRightText1("保存");
        List<IndustryType> list = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getIndustry())) {
            list = gson.fromJson(SettingUtils.getIndustry(), new TypeToken<List<IndustryType>>() {
            }.getType());
        }
        industryTypeList = getIndustryTypeList(list);
        intView();


    }

    private void intView() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        industryAdapter = new CompayIndustryAdapter(industryTypeList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(linearLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(industryAdapter);
        industryAdapter.setOnItemClickListener(new CompayIndustryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IndustryType industryType = industryTypeList.get(position);

                //workCityAdapter.setList(dataList);
                EventBus.getDefault().post(new CompanyIndustryEvent(industryType));
                finish();
            }

            public void onItemLongClick(View view, int position) {
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size() < 1) {
                    AlertUtils.toast(context, "未选择行业");
                    return;
                }

            }
        });
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
}
