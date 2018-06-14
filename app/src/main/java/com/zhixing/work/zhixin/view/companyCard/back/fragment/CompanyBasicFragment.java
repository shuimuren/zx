package com.zhixing.work.zhixin.view.companyCard.back.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.Company;
import com.zhixing.work.zhixin.bean.CompanyCard;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.dialog.CardCompleteDialog;
import com.zhixing.work.zhixin.event.BasicRefreshEvent;
import com.zhixing.work.zhixin.event.BigEventRefreshEvent;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.companyCard.back.CompanyPerfectCardActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 *公司基础资料
 */
public class CompanyBasicFragment extends SupportFragment {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.ll_logo)
    LinearLayout llLogo;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.ll_company_name)
    LinearLayout llCompanyName;
    @BindView(R.id.company_abbreviation)
    TextView companyAbbreviation;
    @BindView(R.id.ll_company_abbreviation)
    LinearLayout llCompanyAbbreviation;
    @BindView(R.id.company_region)
    TextView companyRegion;
    @BindView(R.id.ll_company_region)
    LinearLayout llCompanyRegion;
    @BindView(R.id.company_address)
    TextView companyAddress;
    @BindView(R.id.ll_company_address)
    LinearLayout llCompanyAddress;
    @BindView(R.id.company_financing)
    TextView companyFinancing;
    @BindView(R.id.ll_company_financing)
    LinearLayout llCompanyFinancing;
    @BindView(R.id.company_scale)
    TextView companyScale;
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.company_website)
    TextView companyWebsite;
    @BindView(R.id.ll_company_website)
    LinearLayout llCompanyWebsite;
    @BindView(R.id.senior_editor)
    Button seniorEditor;
    @BindView(R.id.rl_senior_ed)
    RelativeLayout rlSeniorEd;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.perfect_card)
    Button perfectCard;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    private Unbinder unbinder;
    private Thread thread;
    private Company company;
    private String addressct;
    private String region;

    public static CompanyBasicFragment newInstance() {
        Bundle args = new Bundle();
        CompanyBasicFragment fragment = new CompanyBasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_basic, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

    }
    @OnClick({R.id.senior_editor, R.id.perfect_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.senior_editor:
                Intent intent = new Intent(getActivity(), CompanyPerfectCardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", company);
                intent.putExtra("type", "edit");
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.perfect_card:

                startActivity(new Intent(getActivity(), CompanyPerfectCardActivity.class).putExtra("type", "add"));
                break;
        }
    }


    @Override
    public void onSupportVisible() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if (company == null) {

                    getData();

                }
            }
        });
        thread.start();
    }

    private void initView() {


        if (company.getProvince() != null) {
            region = Utils.searchProvincial(company.getProvince());
        }
        if (company.getCity() != null) {
            region = region + Utils.searchCity(company.getCity());
        }
        if (company.getDistrict() != null) {
            region = region + Utils.searchArea(company.getDistrict());
        }
        if (!TextUtils.isEmpty(company.getLogo())) {
            GlideUtils.getInstance().loadCircleUserIconInto(getActivity(), ALiYunFileURLBuilder.getUserIconUrl(company.getLogo()), logo);
        }
        companyRegion.setText(region);
        if (TextUtils.isEmpty(company.getAddress())) {

        } else {
            companyAddress.setText(company.getAddress());
        }

        companyWebsite.setText(company.getWebsiteUrl());

        companyName.setText(company.getFullName());
        companyAbbreviation.setText(company.getShortName());
        if (!TextUtils.isEmpty(company.getNatureOfUnit())) {


        }
        if (!TextUtils.isEmpty(company.getIndustryTypeId())) {


        }
        if (!TextUtils.isEmpty(company.getStaffSize())) {
            companyScale.setText(Utils.getStaffSize(company.getStaffSize()));


        }
        if (!TextUtils.isEmpty(company.getFinancingStage())) {
            companyFinancing.setText(Utils.getFinancingStage(company.getFinancingStage()));
        }



    }

    private void getData() {

        OkUtils.getInstances().httpTokenGet(getActivity(), JavaConstant.Company, JavaParamsUtils.getInstances().getCompanyCard(), new TypeToken<EntityObject<Company>>() {
        }.getType(), new ResultCallBackListener<Company>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(getActivity(), "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<Company> response) {
                if (response.getCode() == 10000) {
                    if (response.getContent() == null) {
                        llEmpty.setVisibility(View.VISIBLE);
                        llData.setVisibility(View.GONE);
                    } else {
                        company = response.getContent();
                        llData.setVisibility(View.VISIBLE);
                        llEmpty.setVisibility(View.GONE);
                        initView();
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBasicRefreshEvent(BasicRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
}
