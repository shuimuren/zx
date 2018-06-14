package com.zhixing.work.zhixin.view.companyCard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.ManagerListAdapter;
import com.zhixing.work.zhixin.adapter.ProductListAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Manager;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.event.ManagerRefreshEvent;
import com.zhixing.work.zhixin.event.ProductRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.view.card.AddWorkActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyManagerActivity extends BaseTitleActivity {

    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.add)
    Button add;

    private List<Manager> list = new ArrayList<>();
    private ManagerListAdapter managerListAdapter;

    private DeleteDialog deleteDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_manager);
        ButterKnife.bind(this);
        setTitle("公司高管");
        initView();
        getData();
    }


    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.CompanySeniorManager, JavaParamsUtils.getInstances().getCompanySeniorManager(), new TypeToken<EntityObject<List<Manager>>>() {
        }.getType(), new ResultCallBackListener<List<Manager>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Manager>> response) {
                if (response.getCode() == 10000) {
                    list = response.getContent();
                    if (!list.isEmpty()) {
                        managerListAdapter.setList(list);
                    }

                }
            }
        });
    }

    private void initView() {
        managerListAdapter = new ManagerListAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        listview.setAdapter(managerListAdapter);
        managerListAdapter.setOnItemClickListener(new ManagerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, AddCompanyManagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("type", "edit");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view,final int position) {
                deleteDialog = new DeleteDialog(context, "是否删除此公司高管", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {
                            deleteData(list.get(position).getId());
                        }
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();

            }
        });



    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onManagerRefreshEvent(ManagerRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }

    @OnClick(R.id.add)
    public void onViewClicked() {

        startActivity(new Intent(context, AddCompanyManagerActivity.class).putExtra("type", "add"));
    }
    //删除经历
    private void deleteData(int id) {
        OkUtils.getInstances().httpDelete(context, JavaConstant.CompanySeniorManager + "?Id=" + id, JavaParamsUtils.getInstances().deleteProduct(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "删除成功");
                        EventBus.getDefault().post(new ManagerRefreshEvent(true));
                    } else {
                        AlertUtils.toast(context, "删除失败");
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });


    }

}
