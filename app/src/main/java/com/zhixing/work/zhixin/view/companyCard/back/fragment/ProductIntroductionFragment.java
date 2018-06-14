package com.zhixing.work.zhixin.view.companyCard.back.fragment;

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
import com.zhixing.work.zhixin.adapter.ProductAdapter;
import com.zhixing.work.zhixin.adapter.ProductListAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.Company;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Product;
import com.zhixing.work.zhixin.event.ProductRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.companyCard.CompanyProductsActivity;
import com.zhixing.work.zhixin.widget.DashlineItemDivider;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

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
 * 公司产品
 */
public class ProductIntroductionFragment extends SupportFragment {


    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.editor)
    Button editor;

    private Unbinder unbinder;
    private Thread thread;
    private List<Product> list = new ArrayList<Product>();

    private ProductAdapter adapter;

    public static ProductIntroductionFragment newInstance() {
        Bundle args = new Bundle();
        ProductIntroductionFragment fragment = new ProductIntroductionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_introduction, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
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

    private void initView() {
        adapter = new ProductAdapter(list, getActivity());
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(getActivity());
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, R.drawable.list_line));


        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    private void getData() {
        OkUtils.getInstances().httpTokenGet(getActivity(), JavaConstant.CompanyProduct, JavaParamsUtils.getInstances().getCompanyProduct(), new TypeToken<EntityObject<List<Product>>>() {
        }.getType(), new ResultCallBackListener<List<Product>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(getActivity(), "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Product>> response) {
                if (response.getCode() == 10000) {
                    if (response.getContent() == null) {

                    } else {
                        list = response.getContent();
                        adapter.setList(list);
                    }
                }
            }
        });
    }

    @OnClick(R.id.editor)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), CompanyProductsActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProductRefreshEvent(ProductRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }
}
