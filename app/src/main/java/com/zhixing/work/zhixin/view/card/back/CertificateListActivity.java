package com.zhixing.work.zhixin.view.card.back;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.CertificateListAdapter;
import com.zhixing.work.zhixin.adapter.EducationListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.AddCertificateActivity;
import com.zhixing.work.zhixin.view.card.AddEducationActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificateListActivity extends BaseTitleActivity {

    @BindView(R.id.certificate_list)
    RecyclerView certificateList;
    @BindView(R.id.add_certificate)
    ImageView addCertificate;
    @BindView(R.id.certificate)
    TextView certificate;
    @BindView(R.id.ll_add_certificate)
    RelativeLayout llAddCertificate;
    private  List<Resume.CertificateOutputsBean>list=new ArrayList<>();

    private CertificateListAdapter certificateListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        ButterKnife.bind(this);
        setTitle("证书经历");


    getData();
    initView();
}
    private void initView()
    {
        certificateListAdapter = new CertificateListAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        certificateList.setLayoutManager(commodityLayoutManager);
        certificateList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        certificateList.setAdapter(certificateListAdapter);
        certificateListAdapter.setOnItemClickListener(new CertificateListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, AddCertificateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    //获取数据
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.CertificateBackground, JavaParamsUtils.getInstances().getCardAll(), new TypeToken<EntityObject<List<Resume.CertificateOutputsBean>>>() {
        }.getType(), new ResultCallBackListener<List<Resume.CertificateOutputsBean>>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<List<Resume.CertificateOutputsBean>> response) {
                if (response.getCode() == 10000) {
                    list = response.getContent();
                    certificateListAdapter.setList(list);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResumeRefreshEvent(ResumeRefreshEvent event) {
        if (event.getRefresh()) {
            getData();
        }
    }



    @OnClick(R.id.ll_add_certificate)
    public void onViewClicked() {
        startActivity(new Intent(context, AddCertificateActivity.class).putExtra("type", "resume"));
    }
}
