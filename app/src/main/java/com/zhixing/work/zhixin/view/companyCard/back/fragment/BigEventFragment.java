package com.zhixing.work.zhixin.view.companyCard.back.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.CompanyEventAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.dialog.CompanyEventDialog;
import com.zhixing.work.zhixin.event.BigEventRefreshEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.companyCard.BigEventActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 公司大事件
 */
public class BigEventFragment extends SupportFragment {
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.editor)
    Button editor;
    private Thread thread;
    private Unbinder unbinder;
    private List<History> list = new ArrayList<>();
    private CompanyEventAdapter eventAdapter;
    private Context context;

    public static BigEventFragment newInstance() {
        Bundle args = new Bundle();
        BigEventFragment fragment = new BigEventFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_big_event, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);

        context = getActivity();

        EventBus.getDefault().register(this);
        initView();

        return view;
    }

    private void initView() {
        // adapter = new BigEventListAdapter(list, context);

        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        eventAdapter = new CompanyEventAdapter(list, new CompanyEventAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(History history) {
                CompanyEventDialog dialog = new CompanyEventDialog(getActivity(),history);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        listview.setAdapter(eventAdapter);
    }

    @Override
    public void fetchData() {
    }


    @Override
    public void onSupportVisible() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if (list.isEmpty()) {

                    getData();

                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.editor)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), BigEventActivity.class));
    }


    private void getData() {

        OkUtils.getInstances().httpTokenGet(getActivity(), RequestConstant.COMPANY_HISTORY, JavaParamsUtils.getInstances().getCompanyCard(), new TypeToken<EntityObject<List<History>>>() {
        }.getType(), new ResultCallBackListener<List<History>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(getActivity(), "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<History>> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent() == null) {

                    } else {
                        list = response.getContent();
                        eventAdapter.setData(list);
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBigEventRefreshEvent(BigEventRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
}
