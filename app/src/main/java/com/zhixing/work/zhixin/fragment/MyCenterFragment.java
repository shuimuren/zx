package com.zhixing.work.zhixin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.view.authentication.AuthenticationHallActivity;
import com.zhixing.work.zhixin.view.authentication.IdAuthenticationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**

 */
public class MyCenterFragment extends BaseMainFragment {

    @BindView(R.id.ll_authentication)
    LinearLayout llAuthentication;
    private Unbinder unbinder;
    private Context context;

    public static MyCenterFragment newInstance() {
        Bundle args = new Bundle();
        MyCenterFragment fragment = new MyCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_center, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_authentication)
    public void onViewClicked() {
        startActivity(new Intent(context, AuthenticationHallActivity.class));
    }

    }
