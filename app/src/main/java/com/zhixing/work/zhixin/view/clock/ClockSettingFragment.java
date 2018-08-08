package com.zhixing.work.zhixin.view.clock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceRuleListAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.AttendanceRuleBean;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.AttendanceRuleResult;
import com.zhixing.work.zhixin.network.response.DeleteAttendanceRuleResult;
import com.zhixing.work.zhixin.util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/31.
 * Description: 考勤设置
 */

public class ClockSettingFragment extends SupportFragment {

    @BindView(R.id.tv_add_group)
    TextView tvAddGroup;
    @BindView(R.id.ll_group_empty)
    LinearLayout llGroupEmpty;
    @BindView(R.id.recycler_rule)
    RecyclerView recyclerRule;
    Unbinder unbinder;

    public static ClockSettingFragment getInstance() {
        ClockSettingFragment fragment = new ClockSettingFragment();
        return fragment;
    }

    private Subscription mRuleListSubscription;
    private Subscription mDeleteRuleSubscription;
    private AttendanceRuleListAdapter mRuleAdapter;
    private List<AttendanceRuleBean> mRuleList;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        registerRequest();
        mContext = getActivity();
        initView();
        return view;
    }

    private void registerRequest() {
        mRuleListSubscription = RxBus.getInstance().toObservable(AttendanceRuleResult.class).subscribe(
                result -> handlerAttendanceRuleResult(result)
        );

        mDeleteRuleSubscription = RxBus.getInstance().toObservable(DeleteAttendanceRuleResult.class).subscribe(
                result -> handlerDeleteAttendanceRuleResult(result)
        );
    }

    private void handlerDeleteAttendanceRuleResult(DeleteAttendanceRuleResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE && result.isContent()) {
            AlertUtils.show("考勤组删除成功");
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RULE);
        }
    }

    private void initView() {
        mRuleList = new ArrayList<>();
        mRuleAdapter = new AttendanceRuleListAdapter(mRuleList);
        mRuleAdapter.setCallBack(new AttendanceRuleListAdapter.ClickCallBack() {
            @Override
            public void itemClick(AttendanceRuleBean bean) {
                EditAttendanceGroupActivity.startEditAttendanceGroup(getActivity(),null,null,bean,false);
            }

            @Override
            public void changeRule(AttendanceRuleBean bean) {
                EditAttendanceGroupActivity.startEditAttendanceGroup(getActivity(),null,null,bean,true);
            }

            @Override
            public void changeMember(AttendanceRuleBean bean) {
               // Logger.i(">>>", "changeMember");
                SelectAttendanceMemberActivity.startSelectAttendanceActivity(getActivity(),String.valueOf(bean.getId()),bean.getStaffIds());
            }

            @Override
            public void deleteRule(AttendanceRuleBean bean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("提示");
                builder.setMessage("确认删除该考勤分组?删除后,分组内人员将无考勤规则!");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_DELETE_ATTENDANCE_RULE, String.valueOf(bean.getId()));
                    }
                });
                builder.create();
                builder.show();

            }
        });
        recyclerRule.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerRule.setItemAnimator(new DefaultItemAnimator());
        recyclerRule.setHasFixedSize(true);
        recyclerRule.setAdapter(mRuleAdapter);


    }

    private void handlerAttendanceRuleResult(AttendanceRuleResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent().size() > 0) {
                llGroupEmpty.setVisibility(View.GONE);
                mRuleAdapter.setData(result.getContent());
            } else {
                llGroupEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_RULE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_add_group)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), CreateAttendanceGroupActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mRuleListSubscription,mDeleteRuleSubscription);
    }
}
