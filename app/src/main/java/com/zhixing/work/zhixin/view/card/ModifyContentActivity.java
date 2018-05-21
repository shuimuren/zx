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
    private String type;
    public static final String TYPE = "type";//类型
    public static final String TYPE_TITLE = "title";//标题
    public static final String TYPE_WORK_CONTENT = "work_content";//工作内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_content);
        ButterKnife.bind(this);


        title = getIntent().getStringExtra(TYPE_TITLE);
        type = getIntent().getStringExtra(TYPE);
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
