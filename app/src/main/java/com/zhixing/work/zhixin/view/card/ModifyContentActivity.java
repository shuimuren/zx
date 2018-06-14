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

public class ModifyContentActivity extends BaseTitleActivity {
    @BindView(R.id.edit)
    EditText edit;
    private String title;
    private Context context;
    private String content;
    private String hint;
    private String type;
    public static final String TYPE = "type";//类型
    public static final String HINT = "hint";//提示
    public static final String TYPE_TITLE = "title";//标题
    public static final String TYPE_CONTENT = "content";//内容

    public static final String TYPE_WORK_CONTENT = "work_content";//工作内容
    public static final String TYPE_EXPLAIN = "explain";//说明
    public static final String TYPE_PROJECT_DESCRIBE = "project_describe";//项目描述
    public static final String TYPE_PROJECT_ACHIEVEMENT = "project_achievement";//项目描述
    public static final String TYPE_ADVANTAGE = "type_advantage";//我的优势
    public static final String CORPORATE_NAME = "corporate_name";//公司名字
    public static final String PRODUCT_DESCRIPTION = "product_description";//公司名字
    public static final String MANAGER_INTRODUCE = "manager_introduce";//高管介绍
    public static final String EVENT_SYNOPSIS = "event_synopsis";//事件简介

    public static final String CORPORATE_INTRODUCE = "corporate_introduce";//公司介绍
    public static final String CORPORATE_ADDRESS = "corporate_address";//公司地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_content);
        ButterKnife.bind(this);
        context = this;

        title = getIntent().getStringExtra(TYPE_TITLE);
        hint = getIntent().getStringExtra(HINT);
        content = getIntent().getStringExtra(TYPE_CONTENT);
        type = getIntent().getStringExtra(TYPE);

        if (TextUtils.isEmpty(content)) {
            edit.setHint(hint);
            edit.setHintTextColor(getResources().getColor(R.color.hint));
        } else {

            edit.setText(content);
            edit.setSelection(content.length());

        }
        setTitle(title);

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

                    EventBus.getDefault().post(new ModifyEvent(type, content));
                    finish();


                }

            }
        });
    }
}
