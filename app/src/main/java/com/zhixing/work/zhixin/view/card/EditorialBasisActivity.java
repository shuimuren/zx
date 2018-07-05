package com.zhixing.work.zhixin.view.card;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.CardBack;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.CardBackEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class EditorialBasisActivity extends BaseTitleActivity {

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
    private List<String> constellationList;

    private String constellationType;


    private String Stature = "";
    private String Weight = "";
    private String nike_name = "";
    private String mail = "";
    private String motto = "";
    private String work_time = "";
    private String gender = "";
    private String birthday = "";
    private CardBack cardBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_basis);
        ButterKnife.bind(this);
        setTitle("基础信息");
        setRightText1("保存");
        getData();
        initView();
    }

    //获取数据
    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.GET_CARD_INFO, JavaParamsUtils.getInstances().getCardAll(), new TypeToken<EntityObject<CardBack>>() {
        }.getType(), new ResultCallBackListener<CardBack>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<CardBack> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    cardBack = response.getContent();
                    setView();
                }
            }
        });


    }

    private void setView() {

        nameEd.setText(cardBack.getRealName());
        mailbox.setText(cardBack.getEmail());
        registPhoneNum.setText(SettingUtils.getPhoneNumber());
        nikeName.setText(cardBack.getNickName());
        if (cardBack.getSex().equals("男")) {
            cardSexMan.setChecked(true);
            gender = "0";
        } else {
            cardSexWoman.setChecked(true);
            gender = "1";
        }

        if (!TextUtils.isEmpty(cardBack.getBirthday())) {
            date.setText(DateFormatUtil.parseDate(cardBack.getBirthday(), "yyyy-MM-dd"));
        }

        if (!TextUtils.isEmpty(cardBack.getFirstWorkTime())) {
            workTime.setText(DateFormatUtil.parseDate(cardBack.getFirstWorkTime(), "yyyy-MM-dd"));
        }

        height.setText(cardBack.getStature()+"");
        weight.setText(cardBack.getWeight()+"");
        Stature = cardBack.getStature() + "";
        Weight = cardBack.getWeight() + "";
        mottoTx.setText(cardBack.getMotto());
        motto = cardBack.getMotto();
        constellationText.setText(cardBack.getConstellation());
        constellationType = Utils.getConstellation(cardBack.getConstellation());

    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = mailbox.getText().toString();
                nike_name = nikeName.getText().toString();
                birthday = date.getText().toString();
                motto = mottoTx.getText().toString();
                work_time = workTime.getText().toString();

                RequestBody body = new FormBody.Builder()

                        .add("Birthday", date.getText().toString())
                        .add("Motto", mottoTx.getText().toString())
                        .add("Weight", Weight)
                        .add("Stature", Stature)
                        .add("Email", mailbox.getText().toString())
                        .add("Sex", gender)
                        .add("Constellation", constellationType)
                        .add("FirstWorkTime", workTime.getText().toString())
                        .add("NickName", nikeName.getText().toString())
                        .build();
                Log.d(TAG, body.toString());
                submit(body);

            }
        });
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
                        birthday = time;
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
                        constellationType = options1 + "";
                        String s = constellationList.get(options1);
                        constellationText.setText(s);
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
                        work_time = time;
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
                gender = "0";
                break;
            case R.id.card_sex_woman:
                cardSexMan.setChecked(false);
                gender = "1";
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_MAILBOX: //send the video
                mailbox.setText(event.getContent());
                mail = event.getContent();
                break;
            case ModifyDataActivity.TYPE_NICKNAME: //send the video
                nikeName.setText(event.getContent());
                nike_name = event.getContent();
                break;
            case ModifyDataActivity.TYPE_MOTTO: //send the video
                mottoTx.setText(event.getContent());
                motto = event.getContent();
                break;
            case ModifyUserActivity.TYPE_WEIGHT: //send the video

                weight.setText(event.getContent());
                Weight = event.getContent();

                break;
            case ModifyUserActivity.TYPE_HEIGHT: //send the video
                height.setText(event.getContent());
                Stature = event.getContent();
                break;

        }


    }


    private void submit(RequestBody body) {

        OkUtils.getInstances().httpPut(body, context, RequestConstant.EDIT_PERSONAL_INFO, JavaParamsUtils.getInstances().personalinfo(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "修改成功");
                        EventBus.getDefault().post(new CardBackEvent(true));
                        finish();
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });
    }
}
