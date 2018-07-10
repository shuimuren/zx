package com.zhixing.work.zhixin.view.card;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 编辑页面
 */
public class ModifyDataActivity extends BaseTitleActivity {

    @BindView(R.id.edit)
    ClearEditText edit;
    private String title;
    private String type;
    private String content;

    public static final String TYPE = "type";//类型
    public static final String TYPE_TITLE = "title";//标题
    public static final String TYPE_MAILBOX = "mail";//邮箱
    public static final String TYPE_NAME = "name";//名字
    public static final String TYPE_NICKNAME = "nakename";//昵称
    public static final String TYPE_MOTTO = "motto";//座右铭
    public static final String TYPE_SCHOOL = "school";
    public static final String TYPE_CERTIFICATE_NAME = "certificate_name";//证书名称
    public static final String TYPE_COMPANY_NAME = "company_name";//公司名字
    public static final String TYPE_PRODUCT_NAME = "product_name";//产品名字
    public static final String TYPE_MANAGER_NAME = "manager_name";//高管名字
    public static final String TYPE_MANAGER_POSITION = "manager_position";//高管职位
    public static final String TYPE_PRODUCT_WEBSITE = "product_website";//产品官网
    public static final String CORPORATE_ABBREVIATION = "CORPORATE_ABBREVIATION";//公司简称
    public static final String EVENT_NAME = "event_name";//事件名称
    public static final String COMPANY_WEBSITE = "company_website";//公司官网
    public static final String DEPARTMENT_NAME = "department_name";//部门名字
    public static final String TYPE_POST = "post";//工作岗位
    public static final String TYPE_DEPARTMENT = "department";//所属部门
    public static final String TYPE_ID = "id";//身份证号
    public static final String TYPE_MAJOR = "major";
    public static final String TYPE_PROJECT_NAME = "project_name";
    public static final String TYPE_PROJECT_URL = "project_url";
    public static final String TYPE_PROJECT_ROLE = "project_role";
    public static final String TYPE_NATION = "nation";//民族
    public static final String TYPE_CONTENT = "content";//内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_data);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra(TYPE_TITLE);
        type = getIntent().getStringExtra(TYPE);
        context = this;
        content = getIntent().getStringExtra(TYPE_CONTENT);
        if (!TextUtils.isEmpty(content)) {
            edit.setText(content);
            edit.setSelection(content.length());
        }
        setRightText1(ResourceUtils.getString(R.string.save));
        initView();
    }

    private void initView() {
        setTitle(title);
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString();
                if (TextUtils.isEmpty(edit.getText().toString())) {
                   AlertUtils.show("填写不能为空");
                    return;
                } else {
                    if (type.equals(TYPE_MAILBOX)) {
                        if (!Utils.checkEmaile(content)) {
                           AlertUtils.show("请填写正确的邮箱");
                            return;

                        }
                        EventBus.getDefault().post(new ModifyEvent(type, content));
                        finish();
                    } else {

                        if (type.equals(TYPE_ID)) {
                            if (!Utils.isIdCardNo18(content)) {
                               AlertUtils.show("请填写正确身份证号");
                                return;
                            }
                            EventBus.getDefault().post(new ModifyEvent(type, content));
                            finish();
                        } else {
                            EventBus.getDefault().post(new ModifyEvent(type, content));
                            finish();
                        }

                    }


                }

            }
        });

    }


}
