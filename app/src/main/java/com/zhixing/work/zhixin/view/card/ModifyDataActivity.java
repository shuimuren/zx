package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyDataActivity extends BaseTitleActivity {
    @BindView(R.id.edit)
    EditText edit;
    private String title;
    private Context context;
    private String type;

    public static final String TYPE = "type";//类型
    public static final String TYPE_TITLE = "title";//标题
    public static final String TYPE_MAILBOX = "mail";//邮箱
    public static final String TYPE_NMAE = "name";//名字
    public static final String TYPE_NICKNAME = "nakename";//昵称
    public static final String TYPE_MOTTO = "motto";//座右铭
    public static final String TYPE_SCHOOL = "school";
    public static final String TYPE_CERTIFICATE_NAME = "certificate_name";//证书名称
    public static final String TYPE_COMPANY_NAME = "company_name";//公司名字
    public static final String TYPE_POST = "post";//工作岗位
    public static final String TYPE_DEPARTMENT = "department";//所属部门
    public static final String TYPE_ID = "id";//身份证号
    public static final String TYPE_MAJOR = "major";
    public static final String TYPE_NATION = "nation";//民族

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_data);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        setRightText1("保存");
        initView();
    }

    private void initView() {
        setTitle(title);
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString();
                if (TextUtils.isEmpty(edit.getText().toString())) {
                    AlertUtils.toast(context, "填写不能为空");
                    return;
                } else {
                    if (type.equals(TYPE_MAILBOX)) {
                        if (!Utils.checkEmaile(content)) {
                            AlertUtils.toast(context, "请填写正确的邮箱");
                            return;

                        }
                        EventBus.getDefault().post(new ModifyEvent(type, content));
                        finish();
                    } else {

                        if (type.equals(TYPE_ID)){
                            if (! Utils.isIdCardNo18(content)){
                                AlertUtils.toast(context, "请填写正确身份证号");
                                return;
                            }
                            EventBus.getDefault().post(new ModifyEvent(type, content));
                            finish();
                        }else {
                            EventBus.getDefault().post(new ModifyEvent(type, content));
                            finish();
                        }

                    }


                }

            }
        });

    }


}
