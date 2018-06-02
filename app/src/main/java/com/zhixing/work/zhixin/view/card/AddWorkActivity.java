package com.zhixing.work.zhixin.view.card;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Evaluating;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.bean.Work;
import com.zhixing.work.zhixin.dialog.DeleteDialog;
import com.zhixing.work.zhixin.event.CardRefreshEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddWorkActivity extends BaseTitleActivity {

    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.iv_company_name)
    ImageView ivCompanyName;
    @BindView(R.id.rl_company_name)
    RelativeLayout rlCompanyName;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.iv_post)
    ImageView ivPost;
    @BindView(R.id.rl_post)
    RelativeLayout rlPost;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.iv_start_time)
    ImageView ivStartTime;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.work_content)
    TextView workContent;
    @BindView(R.id.iv_work_content)
    ImageView ivWorkContent;
    @BindView(R.id.rl_work_content)
    RelativeLayout rlWorkContent;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.iv_department)
    ImageView ivDepartment;
    @BindView(R.id.rl_department)
    RelativeLayout rlDepartment;
    @BindView(R.id.delete)
    Button delete;


    private String company_name_ct;
    private String post_ct;
    private String start_time_ct;
    private String end_time_ct;
    private String work_content_ct;
    private String department_ct;
    private String type;
    private Gson gson = new Gson();
    private Resume.WrokBackgroundOutputsBean bean;
    private DeleteDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        setTitle("工作经历");
        setRightText1("保存");
        Bundle bundle = getIntent().getExtras();
        bean = (Resume.WrokBackgroundOutputsBean) bundle.get("bean");
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company_name_ct = companyName.getText().toString();
                post_ct = post.getText().toString();
                start_time_ct = startTime.getText().toString();
                end_time_ct = endTime.getText().toString();
                work_content_ct = workContent.getText().toString();
                department_ct = department.getText().toString();
                if (TextUtils.isEmpty(company_name_ct)) {
                    AlertUtils.toast(context, "公司名称为空");
                    return;
                }
                if (TextUtils.isEmpty(post_ct)) {
                    AlertUtils.toast(context, "工作岗位不能为空");
                    return;
                }
                if (TextUtils.isEmpty(start_time_ct)) {
                    AlertUtils.toast(context, "开始时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(end_time_ct)) {
                    AlertUtils.toast(context, "结束时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(work_content_ct)) {
                    AlertUtils.toast(context, "工作内容不能为空");
                    return;
                }

                Work work = new Work(company_name_ct, start_time_ct, end_time_ct, post_ct, department_ct, work_content_ct);
                if (type.equals("card")) {
                    EventBus.getDefault().post(work);

                    finish();
                }
                if (type.equals("resume")) {
                    if (bean != null) {
                        RequestBody body = new FormBody.Builder()
                                .add("Id", bean.getId() + "")
                                .add("CompanyName", work.getCompanyName())
                                .add("StartDate", work.getStartDate())
                                .add("EndDate", work.getEndDate())
                                .add("PostOfDuty", work.getPostOfDuty())
                                .add("Department", work.getDepartment())
                                .add("JobContent", work.getJobContent()).
                                        build();
                        upWork(body);
                    } else {
                        List<Work> list = new ArrayList<Work>();
                        list.add(work);
                        addWork(gson.toJson(list));
                    }
                }

            }
        });
        if (bean != null) {
            companyName.setText(bean.getCompanyName());
            post.setText(bean.getPostOfDuty());

            startTime.setText(DateFormatUtil.parseDate(bean.getStartDate(), "yyyy-MM"));
            endTime.setText(DateFormatUtil.parseDate(bean.getEndDate(), "yyyy-MM"));
            workContent.setText(bean.getJobContent());
            department.setText(bean.getDepartment());
            delete.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.rl_company_name, R.id.rl_post, R.id.rl_start_time, R.id.rl_end_time, R.id.rl_work_content, R.id.rl_department, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_company_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "公司名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_COMPANY_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, companyName.getText().toString())
                );
                break;
            case R.id.rl_post:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "工作岗位").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_POST)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, post.getText().toString())
                );
                break;
            case R.id.rl_start_time:
                final TimePickerView start_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        startTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("开始时间")
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
                start_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                start_time.show();
                break;
            case R.id.rl_end_time:
                final TimePickerView end_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        endTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("结束时间")
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
                end_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                end_time.show();
                break;
            case R.id.rl_work_content:

                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "工作内容").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_WORK_CONTENT).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "请输入工作内容").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, workContent.getText().toString()));
                break;
            case R.id.rl_department:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "所属部门").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_DEPARTMENT)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, department.getText().toString())
                );
                break;
            case R.id.delete:

                deleteDialog = new DeleteDialog(context, "是否删除该工作经验", "", new DeleteDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int index, Dialog dialog) {
                        if (index == 1) {

                            RequestBody body = new FormBody.Builder()
                                    .add("Id", bean.getId() + "").build();
                            deleteData(body);
                        }
                        deleteDialog.dismiss();

                    }
                });
                deleteDialog.show();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_COMPANY_NAME: //send the video
                companyName.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_POST: //send the video
                post.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_DEPARTMENT: //send the video
                department.setText(event.getContent());
                break;
            case ModifyContentActivity.TYPE_WORK_CONTENT: //send the video
                workContent.setText(event.getContent());
                break;

        }
    }

    //提交数据
    private void addWork(String json) {
        OkUtils.getInstances().postJson(context, JavaConstant.WorkBackground, json, new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getCode() == 10000) {
                            if (response.getContent()) {
                                AlertUtils.toast(context, "添加成功");
                                EventBus.getDefault().post(new ResumeRefreshEvent(true));
                                finish();
                            } else {
                                AlertUtils.toast(context, response.getMessage());
                            }

                        } else {
                            AlertUtils.toast(context, response.getMessage());
                        }


                    }
                });
            }
        });
    }


    //更新数据
    private void upWork(RequestBody body) {
        OkUtils.getInstances().httput(body, context, JavaConstant.WorkBackground, JavaParamsUtils.getInstances().upWork(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, final String msg) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AlertUtils.toast(context, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onSuccess(final EntityObject<Boolean> response) {
                hideLoadingDialog();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getCode() == 10000) {
                            if (response.getContent()) {
                                AlertUtils.toast(context, "修改成功");
                                EventBus.getDefault().post(new ResumeRefreshEvent(true));
                                finish();
                            } else {
                                AlertUtils.toast(context, response.getMessage());
                            }

                        } else {
                            AlertUtils.toast(context, response.getMessage());
                        }


                    }
                });
            }
        });
    }

    //删除经历
    private void deleteData(RequestBody body) {

        OkUtils.getInstances().httpDelete(body, context, JavaConstant.WorkBackground + "?Id=" + bean.getId(), JavaParamsUtils.getInstances().deleteWork(bean.getId() + ""), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent()) {
                        AlertUtils.toast(context, "删除成功");
                        EventBus.getDefault().post(new ResumeRefreshEvent(true));
                        finish();
                    } else {
                        AlertUtils.toast(context, "删除失败");
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }
            }
        });


    }

}
