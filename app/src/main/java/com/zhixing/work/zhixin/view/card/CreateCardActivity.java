package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateCardActivity extends BaseTitleActivity {

    @BindView(R.id.name_ed)
    TextView nameEd;
    @BindView(R.id.card_sex_man)
    CheckBox cardSexMan;
    @BindView(R.id.card_sex_woman)
    CheckBox cardSexWoman;
    @BindView(R.id.regist_phone_num)
    TextView registPhoneNum;
    @BindView(R.id.mailbox)
    TextView mailbox;
    @BindView(R.id.is_student_yes)
    CheckBox isStudentYes;
    @BindView(R.id.card_stu_no)
    CheckBox cardStuNo;
    @BindView(R.id.btn_born_card)
    Button btnBornCard;
    @BindView(R.id.name_right)
    ImageView nameRight;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.mail_right)
    ImageView mailRight;
    @BindView(R.id.rl_mail)
    RelativeLayout rlMail;


    private String name = "name";
    private String phone = "phone";
    private String mail = "mail";
    private String isstudent = "isstudent";
    private String gender = "gendr";
    private Map<String, String> dataMap = new HashMap<>();
    private String nameData;
    private String mailData;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        ButterKnife.bind(this);

        setTitle("创建卡牌");
        context = this;
        initView();
        dataMap.put(isstudent, "20");
    }


    @OnClick({R.id.card_sex_man, R.id.card_sex_woman, R.id.is_student_yes, R.id.card_stu_no, R.id.btn_born_card, R.id.rl_name, R.id.rl_mail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_sex_man:
                cardSexWoman.setChecked(false);
                dataMap.put(gender, "0");
                isComplete();
                break;
            case R.id.card_sex_woman:
                cardSexMan.setChecked(false);
                dataMap.put(gender, "1");
                isComplete();
                break;
            case R.id.is_student_yes:
                cardStuNo.setChecked(false);
                dataMap.put(isstudent, "10");
                isComplete();
                break;
            case R.id.card_stu_no:
                isStudentYes.setChecked(false);

                dataMap.put(isstudent, "20");
                isComplete();
                break;
            case R.id.btn_born_card:
                CreateCard(dataMap.get(name), dataMap.get(mail), dataMap.get(gender), dataMap.get(isstudent));
                break;
            case R.id.rl_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写名字").putExtra("type", "name")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nameEd.getText().toString())
                );
                break;
            case R.id.rl_mail:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写邮箱").
                        putExtra("type", "mail")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, mailbox.getText().toString())
                );
                break;
        }
    }

    private void initView() {
        registPhoneNum.setText(SettingUtils.getPhoneNumber());
        dataMap.put(phone, SettingUtils.getPhoneNumber());

    }

    //判断是否填写完毕
    private void isComplete() {
        if (dataMap.values().size() == 5) {
            btnBornCard.setEnabled(true);
            btnBornCard.setBackgroundResource(R.color.color_71aae0);

        } else {
            btnBornCard.setEnabled(false);
            btnBornCard.setBackgroundResource(R.color.hardtoast);
        }

    }

//创建卡牌
    private void CreateCard(String RealName, String Email, String sex, String UserIdentity) {

        OkUtils.getInstances().httpTokenPost(context, RequestConstant.ADD_PERSONAL_INFO, JavaParamsUtils.getInstances().PersonalInfo(RealName, Email, sex, UserIdentity), new TypeToken<EntityObject<Object>>() {
        }.getType(), new ResultCallBackListener<Object>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);

            }

            @Override
            public void onSuccess(EntityObject<Object> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    AlertUtils.toast(context, "添加成功");
                    SettingUtils.saveCreateCard();
                    EventBus.getDefault().post(new CardCompleteEvent(true));

                    finish();
                } else {
                    AlertUtils.toast(context, response.getMessage());

                }


            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_MAILBOX: //send the video
                mailbox.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;
            case ModifyDataActivity.TYPE_NAME: //send the video
                nameEd.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;

        }


    }
}
