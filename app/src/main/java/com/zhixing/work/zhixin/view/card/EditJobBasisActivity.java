package com.zhixing.work.zhixin.view.card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.StaffJobBaseCardBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.RefreshStaffJobInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.EditJobBasicResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class EditJobBasisActivity extends BaseTitleActivity {

    @BindView(R.id.name_ed)
    TextView nameEd;
    @BindView(R.id.name_right)
    ImageView nameRight;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.mailbox)
    TextView mailbox;
    @BindView(R.id.mail_right)
    ImageView mailRight;
    @BindView(R.id.rl_mail)
    RelativeLayout rlMail;
    @BindView(R.id.regist_phone_num)
    TextView registPhoneNum;
    @BindView(R.id.nike_name)
    TextView nikeName;
    @BindView(R.id.iv_nike_name)
    ImageView ivNikeName;
    @BindView(R.id.rl_nike_name)
    RelativeLayout rlNikeName;
    @BindView(R.id.card_sex_man)
    CheckBox cardSexMan;
    @BindView(R.id.card_sex_woman)
    CheckBox cardSexWoman;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.iv_height)
    ImageView ivHeight;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.iv_weight)
    ImageView ivWeight;
    @BindView(R.id.rl_weight)
    RelativeLayout rlWeight;
    @BindView(R.id.constellation_text)
    TextView constellationText;
    @BindView(R.id.iv_constellation)
    ImageView ivConstellation;
    @BindView(R.id.rl_constellation)
    RelativeLayout rlConstellation;
    @BindView(R.id.motto_tx)
    TextView mottoTx;
    @BindView(R.id.iv_Motto)
    ImageView ivMotto;
    @BindView(R.id.rl_motto)
    RelativeLayout rlMotto;
    @BindView(R.id.work_time)
    TextView workTime;
    @BindView(R.id.iv_datejob)
    ImageView ivDatejob;
    @BindView(R.id.rl_worktime)
    RelativeLayout rlWorktime;



    private static final String INTENT_KEY_STAFF_ID = "staffId";
    private static final String INTENT_KEY_STAFF_BEAN = "staffBean";

    public static void startEditJobBasicActivity(Activity activity, String staffId, StaffJobBaseCardBean staffBean) {
        Intent intent = new Intent(activity, EditJobBasisActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        intent.putExtra(INTENT_KEY_STAFF_BEAN, staffBean);
        activity.startActivity(intent);
    }

    private Subscription mEditStaffJobBasicInfoSubscription;
    private StaffJobBaseCardBean mStaffBean;
    private String mStaffId;
    private List<String> constellationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_basis);
        ButterKnife.bind(this);
        setTitle("基础信息");
        setRightText1("保存");
        getIntentData();
        initView();
    }

    //获取数据
    private void getIntentData() {

        mStaffBean = (StaffJobBaseCardBean) getIntent().getSerializableExtra(INTENT_KEY_STAFF_BEAN);
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        mStaffBean.setStaffId(mStaffId);
        if (TextUtils.isEmpty(mStaffId) || mStaffBean == null) {
            this.finish();
        } else {
            setView();
        }
    }

    private void setView() {

        nameEd.setText(mStaffBean.getRealName());
        mailbox.setText(mStaffBean.getEmail());
        registPhoneNum.setText(SettingUtils.getPhoneNumber());
        nikeName.setText(mStaffBean.getNickName());
        if (mStaffBean.getSex() == 0) {
            cardSexMan.setChecked(true);
        } else {
            cardSexWoman.setChecked(true);
        }

        if (!TextUtils.isEmpty(mStaffBean.getBirthday())) {
            date.setText(DateFormatUtil.parseDate(mStaffBean.getBirthday(), "yyyy-MM-dd"));
        }

        if (!TextUtils.isEmpty(mStaffBean.getFirstWorkTime())) {
            workTime.setText(DateFormatUtil.parseDate(mStaffBean.getFirstWorkTime(), "yyyy-MM-dd"));
        }
        if (null == mStaffBean.getStature()) {
            height.setText("");
        } else {
            height.setText(mStaffBean.getStature() + "");
        }

        if (null == mStaffBean.getWeight()) {
            weight.setText("");
        } else {
            weight.setText(mStaffBean.getWeight() + "");
        }
        mottoTx.setText(mStaffBean.getMotto());
        constellationText.setText(Arrays.asList(getResources().getStringArray(R.array.constellation)).get(mStaffBean.getConstellation()));

    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put(RequestConstant.KEY_REQUEST_BODY, mStaffBean);
                MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_COMPANYSTAFF_PERSON_BASE, map);

            }
        });
        mEditStaffJobBasicInfoSubscription = RxBus.getInstance().toObservable(EditJobBasicResult.class).subscribe(
                result -> handlerEditResult(result)
        );
    }

    private void handlerEditResult(EditJobBasicResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            Logger.i(">>>", "编辑成功");
            if(result.isContent()){
                AlertUtils.show(ResourceUtils.getString(R.string.update_success));
                EventBus.getDefault().post(new RefreshStaffJobInfoEvent(true));
                this.finish();
            }else{
                AlertUtils.show("操作失败:"+result.Message);
            }
        } else {
            Logger.i(">>>", "失败");
            AlertUtils.show(result.Message);
        }
    }


    @OnClick({R.id.rl_mail, R.id.rl_nike_name, R.id.rl_date, R.id.card_sex_man, R.id.card_sex_woman, R.id.rl_height, R.id.rl_weight, R.id.rl_constellation, R.id.rl_motto, R.id.rl_worktime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mail:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写邮箱").
                        putExtra("type", "mail")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, mailbox.getText().toString())
                );
                break;
            case R.id.rl_nike_name:
                startActivity(new Intent(context, ModifyDataActivity.class).putExtra("title", "填写昵称").
                        putExtra("type", "nakename")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nikeName.getText().toString())
                );
                break;
            case R.id.rl_date:
                TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        date.setText(time);
                        // birthday = time;
                        mStaffBean.setBirthday(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("出生日期")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.rl_height:
                startActivity(new Intent(context, ModifyUserActivity.class).
                        putExtra(ModifyUserActivity.TYPE_TITLE, "身高").
                        putExtra(ModifyUserActivity.TYPE, ModifyUserActivity.TYPE_HEIGHT).
                        putExtra(ModifyUserActivity.TYPE_COMPANY, "cm").
                        putExtra(ModifyUserActivity.TYPE_CONTENT, height.getText().toString()));
                break;
            case R.id.rl_weight:
                startActivity(new Intent(context, ModifyUserActivity.class).
                        putExtra(ModifyUserActivity.TYPE_TITLE, "体重").
                        putExtra(ModifyUserActivity.TYPE, ModifyUserActivity.TYPE_WEIGHT).
                        putExtra(ModifyUserActivity.TYPE_COMPANY, "kg").
                        putExtra(ModifyUserActivity.TYPE_CONTENT, weight.getText().toString()));
                break;
            case R.id.rl_constellation:
                constellationList = Arrays.asList(getResources().getStringArray(R.array.constellation));
                OptionsPickerView options = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = constellationList.get(options1);
                        constellationText.setText(s);
                        mStaffBean.setConstellation(options1);
                    }
                })
                        .setTitleText("星座")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(constellationList);
                options.show();
                break;
            case R.id.rl_motto:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写座右铭").putExtra("type", "motto")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, mottoTx.getText().toString())
                );
                break;
            case R.id.rl_worktime:
                final TimePickerView work_Time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        workTime.setText(time);
                        mStaffBean.setFirstWorkTime(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("工作时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色

                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                work_Time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                work_Time.show();
                break;
            case R.id.card_sex_man:
                cardSexWoman.setChecked(false);
                mStaffBean.setSex(0);
                break;
            case R.id.card_sex_woman:
                cardSexMan.setChecked(false);
                mStaffBean.setSex(1);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_MAILBOX: //send the video
                mailbox.setText(event.getContent());
                mStaffBean.setEmail(event.getContent());
                break;
            case ModifyDataActivity.TYPE_NICKNAME: //send the video
                nikeName.setText(event.getContent());
                mStaffBean.setNickName(event.getContent());
                break;
            case ModifyDataActivity.TYPE_MOTTO: //send the video
                mottoTx.setText(event.getContent());
                mStaffBean.setMotto(event.getContent());
                break;
            case ModifyUserActivity.TYPE_WEIGHT: //send the video
                weight.setText(event.getContent());
                mStaffBean.setWeight(Integer.getInteger(event.getContent()));
                break;
            case ModifyUserActivity.TYPE_HEIGHT: //send the video
                height.setText(event.getContent());
                mStaffBean.setStature(Integer.getInteger(event.getContent()));
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mEditStaffJobBasicInfoSubscription);
    }
}
