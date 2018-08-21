package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceStatisticsAdapter;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.ClockDailyBean;
import com.zhixing.work.zhixin.bean.DailyDetailBean;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.StatisticsDailyDetailResult;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ClockFinishedFragment extends SupportFragment {


    @BindView(R.id.radio_all)
    RadioButton radioAll;
    @BindView(R.id.radio_normal)
    RadioButton radioNormal;
    @BindView(R.id.radio_late)
    RadioButton radioLate;
    @BindView(R.id.radio_absenteeism)
    RadioButton radioAbsenteeism;
    @BindView(R.id.radio_advanced)
    RadioButton radioAdvanced;
//    @BindView(R.id.radio_miss)
//    RadioButton radioMiss;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty)
    LinearLayout llEmptyView;
    Unbinder unbinder;
    private List<DailyDetailBean> mClockedList;
    private List<DailyDetailBean> mNormalList;
    private List<DailyDetailBean> mLateList;
    private List<DailyDetailBean> mAdvancedList;
    private List<DailyDetailBean> mAbsenteeismList;
    private List<DailyDetailBean> mMissList;

    private Subscription mStatisticsDailyDetailSubscription;
    private AttendanceStatisticsAdapter mDailyAdapter;


    public static ClockFinishedFragment getInstance(ClockDailyBean dailyBean, String date) {
        Bundle args = new Bundle();
        args.putSerializable(ClockInfoDetailActivity.INTENT_KEY_DAILY, dailyBean);
        args.putString(ClockInfoDetailActivity.INTENT_KEY_DATE, date);
        ClockFinishedFragment fragment = new ClockFinishedFragment();
        fragment.setArguments(args);
        return fragment;

    }

    private ClockDailyBean mDailyBean;
    private String mDate;
    private int mFilterCode;//0:已打卡,1:正常打卡,2:迟到,3:早退,4:旷工,5:未打卡

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_finished, container, false);
        unbinder = ButterKnife.bind(this, view);
        getIntentData();
        initView();
        return view;
    }


    private void getIntentData() {
        mDailyBean = (ClockDailyBean) getArguments().getSerializable(ClockInfoDetailActivity.INTENT_KEY_DAILY);
        mDate = getArguments().getString(ClockInfoDetailActivity.INTENT_KEY_DATE);
        radioAll.setText(String.format("全部打卡 (%s)", String.valueOf(mDailyBean.getSignedCount())));
        int normal = mDailyBean.getSignedCount() - mDailyBean.getLateCount();
        radioNormal.setText(String.format("正常打卡 (%s)", String.valueOf(normal)));
        radioLate.setText(String.format("迟到 (%s)", String.valueOf(mDailyBean.getLateCount())));
        radioAbsenteeism.setText(String.format("旷工 (%s)", String.valueOf(mDailyBean.getAbsenteeismCount())));
        radioAdvanced.setText(String.format("早退 (%s)", String.valueOf(mDailyBean.getEarlyCount())));
   //     radioMiss.setText(String.format("缺卡 (%s)", String.valueOf(mDailyBean.getNotClockIn())));
    }


    @Override
    public void fetchData() {

    }

    private void getListData() {
        showLoading(ResourceUtils.getString(R.string.loading_default), false);
        Map map = new HashMap();
        map.put(RequestConstant.KEY_DATE_DATE, mDate);
        map.put(RequestConstant.KEY_CLOCK_STATUS, String.valueOf(mFilterCode));
        map.put(RequestConstant.KEY_PAGE_INDEX, "1");
        map.put(RequestConstant.KEY_ROWS_COUNT, "99");
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_ATTENDANCE_STATISTICS_DAILY_DETAIL, map);

    }


    private void initView() {
        mFilterCode = 0;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_all:
                        mFilterCode = 0;
                        if (mClockedList == null) {
                            getListData();
                        } else {
                            if (mClockedList.size() == 0) {
                                llEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                llEmptyView.setVisibility(View.GONE);
                                mDailyAdapter.setData(mClockedList);
                            }

                        }

                        break;
                    case R.id.radio_normal:
                        mFilterCode = 1;
                        if (mNormalList == null) {
                            getListData();
                        } else {
                            if (mNormalList.size() == 0) {
                                llEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                llEmptyView.setVisibility(View.GONE);
                                mDailyAdapter.setData(mNormalList);
                            }

                        }
                        break;
                    case R.id.radio_late:
                        mFilterCode = 2;
                        if (mLateList == null) {
                            getListData();
                        } else {
                            if (mNormalList.size() == 0) {
                                llEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                llEmptyView.setVisibility(View.GONE);
                                mDailyAdapter.setData(mNormalList);
                            }
                        }
                        break;
                    case R.id.radio_advanced:
                        mFilterCode = 3;
                        if (mAdvancedList == null) {
                            getListData();
                        } else {
                            if (mAbsenteeismList.size() == 0) {
                                llEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                llEmptyView.setVisibility(View.GONE);
                                mDailyAdapter.setData(mAbsenteeismList);
                            }
                        }
                        break;
                    case R.id.radio_absenteeism:
                        mFilterCode = 4;
                        if (mAbsenteeismList == null) {
                            getListData();
                        } else {
                            if (mAbsenteeismList.size() == 0) {
                                llEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                llEmptyView.setVisibility(View.GONE);
                                mDailyAdapter.setData(mAbsenteeismList);
                            }

                        }
                        break;
//                    case R.id.radio_miss:
//                        mFilterCode = 5;
//                        if (mMissList == null) {
//                            getListData();
//                        } else {
//                            if (mMissList.size() == 0) {
//                                llEmptyView.setVisibility(View.VISIBLE);
//                            } else {
//                                llEmptyView.setVisibility(View.GONE);
//                                mDailyAdapter.setData(mMissList);
//                            }
//                        }
//                        break;
                }
            }
        });

        mDailyAdapter = new AttendanceStatisticsAdapter(getActivity());
        mDailyAdapter.setData(new ArrayList());
        recyclerView.setAdapter(mDailyAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), RecyclerView.HORIZONTAL));
        mStatisticsDailyDetailSubscription = RxBus.getInstance().toObservable(StatisticsDailyDetailResult.class).subscribe(
                result -> handlerDailyDetailResult(result)
        );
        mDailyAdapter.setOnItemClickListener(new AttendanceStatisticsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object o) {
                DailyDetailBean detailBean = (DailyDetailBean) o;
            }
        });

        getListData();
    }

    private void handlerDailyDetailResult(StatisticsDailyDetailResult result) {
        hideLoadingDialog();

        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                if (result.getContent().getData().size() == 0) {
                    llEmptyView.setVisibility(View.VISIBLE);
                } else {
                    llEmptyView.setVisibility(View.GONE);
                }
                switch (mFilterCode) {
                    case 0:
                        mClockedList = result.getContent().getData();
                        mDailyAdapter.setData(mClockedList);
                        break;
                    case 1:
                        mNormalList = result.getContent().getData();
                        mDailyAdapter.setData(mNormalList);
                        break;
                    case 2:
                        mLateList = result.getContent().getData();
                        mDailyAdapter.setData(mLateList);
                        break;
                    case 3:
                        mAdvancedList = result.getContent().getData();
                        mDailyAdapter.setData(mAdvancedList);
                        break;
                    case 4:
                        mAbsenteeismList = result.getContent().getData();
                        mDailyAdapter.setData(mAbsenteeismList);
                        break;
                    case 5:
                        mMissList = result.getContent().getData();
                        mDailyAdapter.setData(mMissList);
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        RxBus.getInstance().unsubscribe(mStatisticsDailyDetailSubscription);
        unbinder.unbind();
    }
}
