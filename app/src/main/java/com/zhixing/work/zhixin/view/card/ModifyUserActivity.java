package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.util.AlertUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyUserActivity extends BaseTitleActivity {

    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.company)
    TextView company;


    private String title;
    private Context context;
    private String  content;
    private String type;
    public static final String TYPE = "type";//类型
    public static final String TYPE_TITLE = "title";//标题
    public static final String TYPE_COMPANY = "company";//单位
    public static final String TYPE_CONTENT = "content";//内容
    public static final String TYPE_HEIGHT = "height";//身高
    public static final String TYPE_WEIGHT = "weight";//单位

    private String company_ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra(TYPE_TITLE);
        type = getIntent().getStringExtra(TYPE);
        content=getIntent().getStringExtra(TYPE_CONTENT);
        if (!TextUtils.isEmpty(content)){
            edit.setText(content);
            edit.setSelection(content.length());
        }
        company_ct = getIntent().getStringExtra(TYPE_COMPANY);
        setRightText1("保存");
        initView();
    }

    private void initView() {
        setTitle(title);
        company.setText(company_ct);
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
