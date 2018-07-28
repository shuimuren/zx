package com.zhixing.work.zhixin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.companyCard.CompanyCertificationActivity;
import com.zhixing.work.zhixin.view.companyCard.CreateCompanyCardActivity;
import com.zhixing.work.zhixin.view.myCenter.organizational.OrganizationalStructureActivity;
import com.zhixing.work.zhixin.view.setting.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/7/6.
 * Description: 主页-企业我的
 */

public class CompanyCenterFragment extends SupportFragment {

    Unbinder unbinder;

    public static CompanyCenterFragment newInstance() {
        Bundle args = new Bundle();
        CompanyCenterFragment fragment = new CompanyCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_company_authentication, R.id.ll_organize, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_company_authentication:
                if (SettingUtils.createCardBefore()) {
                    startActivity(new Intent(getActivity(), CompanyCertificationActivity.class));
                } else {
                    AlertUtils.show(ResourceUtils.getString(R.string.complete_user_information));
                    CreateCompanyCardActivity.startCompanyCard(getActivity());
                }

                break;
            case R.id.ll_organize:
                if (SettingUtils.createCardBefore()) {
                    startActivity(new Intent(getActivity(), OrganizationalStructureActivity.class));
                } else {
                    AlertUtils.show(ResourceUtils.getString(R.string.complete_user_information));
                    CreateCompanyCardActivity.startCompanyCard(getActivity());
                }

                break;
            case R.id.ll_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
