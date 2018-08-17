package com.zhixing.work.zhixin.view.card.back;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.EditJobListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.WorkBgsBean;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.JobWorkListResult;
import com.zhixing.work.zhixin.network.response.UpdateJobResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.view.card.AddJobWorkActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 员工工作经验编辑
 */

public class WorkJobListActivity extends BaseTitleActivity {


    @BindView(R.id.work_list)
    RecyclerView workList;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.rl_add_work)
    RelativeLayout rlAddWork;

    public static final String INTENT_KEY_STAFF_ID = "staffId";

    private Subscription mWorkJobListSubscription;
    private Subscription mDeleteJobSubscription;

    private String mStaffId;
    private List<WorkBgsBean> mWorkList;
    private EditJobListAdapter<WorkBgsBean> mAdapter;
    private boolean isEdit;
    private DeleteDialog deleteDialog;

    public static void startWorkJobListActivity(Activity activity, String staffId) {
        Intent intent = new Intent(activity, WorkJobListActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);
        setTitle("工作经验");
        initView();
        registerRequest();
        getIntentData();
        setRightText1("管理");
    }

    private void registerRequest() {
        mWorkJobListSubscription = RxBus.getInstance().toObservable(JobWorkListResult.class).subscribe(
                result -> handlerJobWorkResult(result)
        );

        mDeleteJobSubscription = RxBus.getInstance().toObservable(UpdateJobResult.class).subscribe(
                result -> handlerJobDeleteResult(result)
        );
    }

    private void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_WORK_LIST, mStaffId);
    }

    /**
     * 删除工作经历结果
     */
    private void handlerJobDeleteResult(UpdateJobResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.isContent()) {
                if (result.getType() == 0) {
                    AlertUtils.show("删除成功");
                }

                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_WORK_LIST, mStaffId);
            } else {
                if (result.getType() == 0) {
                    AlertUtils.show("删除失败" + result.Message);
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
    private void handlerJobWorkResult(JobWorkListResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE && result.getContent() != null) {
            mWorkList = result.getContent();
            mAdapter.setData(isEdit, mWorkList);
        } else {
            AlertUtils.show(result.Message);
        }

    }


    private void initView() {
        isEdit = false;
        mWorkList = new ArrayList<>();
        mAdapter = new EditJobListAdapter<WorkBgsBean>(mWorkList);
        workList.setLayoutManager(new LinearLayoutManager(this));
        workList.setAdapter(mAdapter);
        mAdapter.setItemCallBack(new EditJobListAdapter.CallBack<WorkBgsBean>() {
            @Override
            public void itemClicked(WorkBgsBean bean) {
                AddJobWorkActivity.startAddJobWorkActivity(WorkJobListActivity.this, bean.getId(), mStaffId);
            }

            @Override
            public void deleteClicked(WorkBgsBean bean) {
                deleteDialog = new DeleteDialog(context, "是否删除该工作经验", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {
                            deleteJob(bean);
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
                mAdapter.setData(isEdit, mWorkList);
            }
        });

    }

    private void deleteJob(WorkBgsBean bean) {
        Map map = new HashMap();
        map.put(RequestConstant.KEY_ID, bean.getId());
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_DELETE_STAFF_WORK, map);
    }

    @OnClick(R.id.work)
    public void onViewClicked() {
        AddJobWorkActivity.startAddJobWorkActivity(this, "", mStaffId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mDeleteJobSubscription, mWorkJobListSubscription);
    }
}
