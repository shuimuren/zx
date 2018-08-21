package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceStatisticsAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/2.
 * Description: 考勤统计-个人
 */

public class StaffAttendanceStatisticsActivity extends BaseTitleActivity {

    @BindView(R.id.img_staff_avatar)
    ImageView imgStaffAvatar;
    @BindView(R.id.tv_staff_name)
    TextView tvStaffName;
    @BindView(R.id.tv_staff_department)
    TextView tvStaffDepartment;
    @BindView(R.id.tv_normal_total)
    TextView tvNormalTotal;
    @BindView(R.id.img_normal)
    ImageView imgNormal;
    @BindView(R.id.rl_normal)
    RelativeLayout rlNormal;
    @BindView(R.id.recycler_normal)
    RecyclerView recyclerNormal;
    @BindView(R.id.tv_late_total)
    TextView tvLateTotal;
    @BindView(R.id.img_late)
    ImageView imgLate;
    @BindView(R.id.rl_late)
    RelativeLayout rlLate;
    @BindView(R.id.recycler_late)
    RecyclerView recyclerLate;
    @BindView(R.id.tv_normal_before)
    TextView tvNormalBefore;
    @BindView(R.id.img_before)
    ImageView imgBefore;
    @BindView(R.id.rl_before)
    RelativeLayout rlBefore;
    @BindView(R.id.recycler_before)
    RecyclerView recyclerBefore;
    @BindView(R.id.tv_miss_total)
    TextView tvMissTotal;
    @BindView(R.id.img_miss)
    ImageView imgMiss;
    @BindView(R.id.rl_miss)
    RelativeLayout rlMiss;
    @BindView(R.id.recycler_miss)
    RecyclerView recyclerMiss;
    @BindView(R.id.tv_absenteeism_total)
    TextView tvAbsenteeismTotal;
    @BindView(R.id.img_absenteeism)
    ImageView imgAbsenteeism;
    @BindView(R.id.rl_absenteeism)
    RelativeLayout rlAbsenteeism;
    @BindView(R.id.recycler_absenteeism)
    RecyclerView recyclerAbsenteeism;

    public static final String INTENT_KEY_STAFF_ID = "staffId";
    public static final String INTENT_KEY_DATE = "date";
    public static final String INTENT_KEY_NAME = "name";
    public static final String INTENT_KEY_AVATAR = "avatar";
    public static final String INTENT_KEY_DEPARTMENT = "department";

    boolean normalIsShow, lateIsShow, beforeIsShow, missIsShow, absenteeismIsShow;

    private AttendanceStatisticsAdapter normalAdapter;
    private AttendanceStatisticsAdapter lateAdapter;
    private AttendanceStatisticsAdapter missAdapter;
    private AttendanceStatisticsAdapter beforeAdapter;
    private AttendanceStatisticsAdapter absenteeismAdapter;

    private Subscription mAttendanceRecordMonthSubscription;
    private String mDate;
    private String mStaffId;
    private String mName;
    private String mAvatarUrl;
    private String mDepartment;

    public static void startStaffAttendanceStatisticsActivity(Activity activity, String date, String id,
                                                              String name, String avatarUrl, String department) {
        Intent intent = new Intent(activity, StaffAttendanceStatisticsActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, id);
        intent.putExtra(INTENT_KEY_DATE, date);
        intent.putExtra(INTENT_KEY_NAME, name);
        intent.putExtra(INTENT_KEY_AVATAR, avatarUrl);
        intent.putExtra(INTENT_KEY_DEPARTMENT, department);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance_statistics);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.statistics));
        getIntentData();
        initView();
        registerRequest();
    }


    private void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mDate = getIntent().getStringExtra(INTENT_KEY_DATE);
        mName = getIntent().getStringExtra(INTENT_KEY_NAME);
        mAvatarUrl = getIntent().getStringExtra(INTENT_KEY_AVATAR);
        mDepartment = getIntent().getStringExtra(INTENT_KEY_DEPARTMENT);
    }


    private void initView() {
        tvStaffName.setText(mName);
        GlideUtils.getInstance().loadPublicRoundTransformWithDefault(this, ResourceUtils.getDrawable(R.drawable.icon_avatar),
                mAvatarUrl, imgStaffAvatar);
        tvStaffDepartment.setText(mDepartment);

        normalAdapter = new AttendanceStatisticsAdapter(this);
        recyclerNormal.setLayoutManager(new LinearLayoutManager(this));
        recyclerNormal.setHasFixedSize(true);
        recyclerNormal.setAdapter(normalAdapter);

        lateAdapter = new AttendanceStatisticsAdapter(this);
        recyclerLate.setLayoutManager(new LinearLayoutManager(this));
        recyclerLate.setHasFixedSize(true);
        recyclerLate.setAdapter(lateAdapter);

        missAdapter = new AttendanceStatisticsAdapter(this);
        recyclerMiss.setLayoutManager(new LinearLayoutManager(this));
        recyclerMiss.setHasFixedSize(true);
        recyclerMiss.setAdapter(missAdapter);

        beforeAdapter = new AttendanceStatisticsAdapter(this);
        recyclerBefore.setLayoutManager(new LinearLayoutManager(this));
        recyclerBefore.setHasFixedSize(true);
        recyclerBefore.setAdapter(beforeAdapter);

        absenteeismAdapter = new AttendanceStatisticsAdapter(this);
        recyclerAbsenteeism.setLayoutManager(new LinearLayoutManager(this));
        recyclerAbsenteeism.setHasFixedSize(true);
        recyclerAbsenteeism.setAdapter(absenteeismAdapter);

    }

    private void registerRequest() {
        mAttendanceRecordMonthSubscription = RxBus.getInstance().toObservable(AttendanceRecordMonthResult.class).subscribe(
                result -> handlerRecordMonth(result)
        );

        Map map = new HashMap();
        map.put(RequestConstant.KEY_STAFF_ID, mStaffId);
        map.put(RequestConstant.KEY_DATE_DATE, mDate);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_STAFF_ATTENDANCE_RECORD_MONTH, map);
    }

    private void handlerRecordMonth(AttendanceRecordMonthResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() != null) {
                if (result.getContent().getAttendanceDays() != null) {
                    normalAdapter.setData(result.getContent().getAttendanceDays());
                    tvNormalTotal.setText(String.format("%s 次", String.valueOf(result.getContent().getAttendanceDays().size())));
                } else {
                    tvNormalTotal.setText("0 次");
                }
                if (result.getContent().getLateDays() != null) {
                    lateAdapter.setData(result.getContent().getLateDays());
                    List<AttendanceRecordMonthResult.ContentBean.LateDaysBean>  list = result.getContent().getLateDays();
                    int size = list.size();
                    if(size>0){
                        int totalMinute = 0;
                        for (int i = 0; i <size ; i++) {
                            totalMinute += list.get(i).getMinutes();
                        }
                        tvLateTotal.setText(String.format("%s次,共计%s分钟",String.valueOf(size),String.valueOf(totalMinute)));
                    }else{
                        tvLateTotal.setText("0 次");
                    }

                } else {
                    tvLateTotal.setText("0 次");
                }
                if (result.getContent().getNotClockInDays() != null) {
                    missAdapter.setData(result.getContent().getNotClockInDays());
                    tvMissTotal.setText(String.format("%s 次", String.valueOf(result.getContent().getNotClockInDays().size())));
                } else {
                    tvMissTotal.setText("0 次");
                }
                if (result.getContent().getEarlyDays() != null) {
                    beforeAdapter.setData(result.getContent().getEarlyDays());
                    tvNormalBefore.setText(String.format("%s 次", String.valueOf(result.getContent().getEarlyDays().size())));
                } else {
                    tvNormalBefore.setText("0 次");
                }
                if (result.getContent().getAttendanceDays() != null) {
                    absenteeismAdapter.setData(result.getContent().getAbsenteeismDays());
                    tvAbsenteeismTotal.setText(String.format("%s 次", String.valueOf(result.getContent().getAbsenteeismDays().size())));
                } else {
                    tvAbsenteeismTotal.setText("0 次");
                }
            }
        }


    }

    @OnClick({R.id.rl_normal, R.id.rl_late, R.id.rl_before, R.id.rl_miss, R.id.rl_absenteeism})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_normal:
                normalIsShow = !normalIsShow;
                imgNormal.setSelected(normalIsShow);
                recyclerNormal.setVisibility(normalIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_late:
                lateIsShow = !lateIsShow;
                imgLate.setSelected(lateIsShow);
                recyclerLate.setVisibility(lateIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_before:
                beforeIsShow = !beforeIsShow;
                imgBefore.setSelected(beforeIsShow);
                recyclerBefore.setVisibility(beforeIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_miss:
                missIsShow = !missIsShow;
                imgMiss.setSelected(missIsShow);
                recyclerMiss.setVisibility(missIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_absenteeism:
                absenteeismIsShow = !absenteeismIsShow;
                imgAbsenteeism.setSelected(absenteeismIsShow);
                recyclerAbsenteeism.setVisibility(absenteeismIsShow ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mAttendanceRecordMonthSubscription);
    }
}

