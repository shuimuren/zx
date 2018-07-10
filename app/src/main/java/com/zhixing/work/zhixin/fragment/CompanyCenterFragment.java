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
import com.zhixing.work.zhixin.view.companyCard.CompanyCertificationActivity;
import com.zhixing.work.zhixin.view.myCenter.organizational.OrganizationalStructureActivity;
import com.zhixing.work.zhixin.view.setting.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhj on 2018/7/6.
 * Description: 企业我的
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
                startActivity(new Intent(getActivity(), CompanyCertificationActivity.class));
                break;
            case R.id.ll_organize:
                startActivity(new Intent(getActivity(), OrganizationalStructureActivity.class));
                break;
            case R.id.ll_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
