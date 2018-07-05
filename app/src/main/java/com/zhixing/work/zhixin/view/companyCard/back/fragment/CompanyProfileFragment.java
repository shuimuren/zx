package com.zhixing.work.zhixin.view.companyCard.back.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.zhixing.work.zhixin.bean.CompanyIntro;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.IntroRefreshEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.view.companyCard.back.CompanyProfileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 公司简介
 */
public class CompanyProfileFragment extends SupportFragment {
    @BindView(R.id.perfect_profile)
    Button perfectProfile;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.company_bg)
    ImageView companyBg;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.rl_senior_ed)
    RelativeLayout rlSeniorEd;
    @BindView(R.id.profile_editor)
    Button profileEditor;
    @BindView(R.id.company_introduction)
    TextView companyIntroduction;

    private Unbinder unbinder;
    private CompanyIntro companyIntro;
    private Thread thread;

    public static CompanyProfileFragment newInstance() {
        Bundle args = new Bundle();
        CompanyProfileFragment fragment = new CompanyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);
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
        if (unbinder != null) {

            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.perfect_profile, R.id.profile_editor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.perfect_profile:
                startActivity(new Intent(getActivity(), CompanyProfileActivity.class));
                break;
            case R.id.profile_editor:
                startActivity(new Intent(getActivity(), CompanyProfileActivity.class).putExtra("intro",companyIntroduction.getText().toString()));
                break;
        }
    }


    @Override
    public void onSupportVisible() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (companyIntro == null) {
                    getData();
                }
            }
        });
        thread.start();

    }

    private void getData() {
        OkUtils.getInstances().httpTokenGet(getActivity(), RequestConstant.COMPANY_INTRO, JavaParamsUtils.getInstances().getCompanyCard(), new TypeToken<EntityObject<CompanyIntro>>() {
        }.getType(), new ResultCallBackListener<CompanyIntro>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(getActivity(), "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<CompanyIntro> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent() == null) {
                        llEmpty.setVisibility(View.VISIBLE);
                        llData.setVisibility(View.GONE);
                    } else {
                        companyIntro = response.getContent();
                        companyIntroduction.setText(companyIntro.getIntro());
                        GlideUtils.getInstance().loadGlideRoundTransform(getActivity(),
                                ALiYunFileURLBuilder.getUserIconUrl(companyIntro.getImages().get(0)), companyBg);
                        llEmpty.setVisibility(View.GONE);
                        llData.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    //刷新数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIntroRefreshEvent(IntroRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
}
