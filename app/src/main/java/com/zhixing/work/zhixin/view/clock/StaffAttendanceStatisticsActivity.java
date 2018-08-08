package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.AttendanceStatisticsAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;

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

    boolean normalIsShow, lateIsShow, beforeIsShow, missIsShow, absenteeismIsShow;

    private AttendanceStatisticsAdapter normalAdapter;
    private AttendanceStatisticsAdapter lateAdapter;
    private AttendanceStatisticsAdapter missAdapter;
    private AttendanceStatisticsAdapter beforeAdapter;
    private AttendanceStatisticsAdapter absenteeismAdapter;

    private Subscription mAttendanceRecordMonthSubscription;

    public static void startStaffAttendanceStatisticsActivity(Activity activity, String date, String id) {
        Intent intent = new Intent(activity, StaffAttendanceStatisticsActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, id);
        intent.putExtra(INTENT_KEY_DATE, date);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance_statistics);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.statistics));
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
                imgNormal.setSelected(lateIsShow);
                recyclerNormal.setVisibility(lateIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_before:
                beforeIsShow = !beforeIsShow;
                imgNormal.setSelected(beforeIsShow);
                recyclerNormal.setVisibility(beforeIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_miss:
                missIsShow = !missIsShow;
                imgNormal.setSelected(missIsShow);
                recyclerNormal.setVisibility(missIsShow ? View.VISIBLE : View.GONE);
                break;
            case R.id.rl_absenteeism:
                absenteeismIsShow = !absenteeismIsShow;
                imgNormal.setSelected(absenteeismIsShow);
                recyclerNormal.setVisibility(absenteeismIsShow ? View.VISIBLE : View.GONE);
                break;
        }
    }
}
