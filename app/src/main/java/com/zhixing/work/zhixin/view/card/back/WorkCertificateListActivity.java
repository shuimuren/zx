package com.zhixing.work.zhixin.view.card.back;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.EditJobListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.CertificateWorkListResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.AddJobCertificateActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 员工证书列表
 */

public class WorkCertificateListActivity extends BaseTitleActivity {


    public static final String INTENT_KEY_STAFF_ID = "staffId";
    @BindView(R.id.certificate_list)
    RecyclerView certificateList;

    private Subscription mWorkCertificateListSubscription;
    private Subscription mDeleteCertificateSubscription;

    private String mStaffId;
    private List<CertificateBean> mCertificateList;
    private EditJobListAdapter<CertificateBean> mAdapter;
    private boolean isEdit;
    private DeleteDialog deleteDialog;

    public static void startWorkCertificateListActivity(Activity activity, String staffId) {
        Intent intent = new Intent(activity, WorkCertificateListActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        ButterKnife.bind(this);
        setTitle("证书信息");
        initView();
        registerRequest();
        getIntentData();
        setRightText1("管理");
    }

    private void registerRequest() {
        mWorkCertificateListSubscription = RxBus.getInstance().toObservable(CertificateWorkListResult.class).subscribe(
                result -> handlerCertificateWorkResult(result)
        );

        mDeleteCertificateSubscription = RxBus.getInstance().toObservable(UpdateJobResult.class).subscribe(
                result -> handlerCertificateDeleteResult(result)
        );
    }

    private void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_CERTIFICATE_LIST, mStaffId);
    }

    /**
     * 删除工作经历结果
     */
    private void handlerCertificateDeleteResult(UpdateJobResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                if (result.getType() == 0) {
                    AlertUtils.show("删除成功");
                } else {
                    AlertUtils.show("操作成功");
                }

                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_CERTIFICATE_LIST, mStaffId);
            } else {
                if (result.getType() == 0) {
                    AlertUtils.show("删除失败" + result.Message);
                } else {
                    AlertUtils.show("操作失败" + result.Message);
                }

            }

        } else {
            AlertUtils.show(result.Message);
        }
    }

    /**
     * 获取列表结果
     *
     * @param result
     */
    private void handlerCertificateWorkResult(CertificateWorkListResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE && result.getContent() != null) {
            mCertificateList = result.getContent();
            mAdapter.setData(isEdit, mCertificateList);
        } else {
            AlertUtils.show(result.Message);
        }

    }


    private void initView() {
        isEdit = false;
        mCertificateList = new ArrayList<>();
        mAdapter = new EditJobListAdapter<CertificateBean>(mCertificateList);
        certificateList.setLayoutManager(new LinearLayoutManager(this));
        certificateList.setAdapter(mAdapter);
        mAdapter.setItemCallBack(new EditJobListAdapter.CallBack<CertificateBean>() {
            @Override
            public void itemClicked(CertificateBean bean) {
                AddJobCertificateActivity.startAddJobCertificateActivity(WorkCertificateListActivity.this, bean.getId(), mStaffId);
            }

            @Override
            public void deleteClicked(CertificateBean bean) {
                deleteDialog = new DeleteDialog(context, "是否删除该证书", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {
                            deleteCertificate(bean);
                        }
                        deleteDialog.dismiss();

                    }
                });
                deleteDialog.show();


            }
        });

        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightText1(isEdit ? "管理" : "取消");
                isEdit = !isEdit;
                mAdapter.setData(isEdit, mCertificateList);
            }
        });

    }

    private void deleteCertificate(CertificateBean bean) {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_ID, bean.getId());
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_DELETE_STAFF_CERTIFICATE, map);
    }

    @OnClick(R.id.certificate)
    public void onViewClicked() {
        AddJobCertificateActivity.startAddJobCertificateActivity(this, "", mStaffId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDeleteCertificateSubscription, mWorkCertificateListSubscription);
    }
}
