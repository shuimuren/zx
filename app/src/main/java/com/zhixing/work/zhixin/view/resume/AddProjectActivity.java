package com.zhixing.work.zhixin.view.resume;

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
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Project;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

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

public class AddProjectActivity extends BaseTitleActivity {

    @BindView(R.id.project_name)
    TextView projectName;
    @BindView(R.id.iv_project_name)
    ImageView ivProjectName;
    @BindView(R.id.rl_project_name)
    RelativeLayout rlProjectName;
    @BindView(R.id.project_url)
    TextView projectUrl;
    @BindView(R.id.iv_project_url)
    ImageView ivProjectUrl;
    @BindView(R.id.rl_project_url)
    RelativeLayout rlProjectUrl;
    @BindView(R.id.project_describe)
    TextView projectDescribe;
    @BindView(R.id.iv_project_describe)
    ImageView ivProjectDescribe;
    @BindView(R.id.rl_project_describe)
    RelativeLayout rlProjectDescribe;
    @BindView(R.id.project_achievement)
    TextView projectAchievement;
    @BindView(R.id.iv_project_achievement)
    ImageView ivProjectAchievement;
    @BindView(R.id.rl_project_achievement)
    RelativeLayout rlProjectAchievement;
    @BindView(R.id.project_role)
    TextView projectRole;
    @BindView(R.id.iv_project_role)
    ImageView ivProjectRole;
    @BindView(R.id.rl_project_role)
    RelativeLayout rlProjectRole;
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
    @BindView(R.id.delete)
    Button delete;


    private String projectName_ct = "";
    private String projectRole_ct = "";
    private String projectUrl_ct = "";
    private String projectDescribe_ct = "";
    private String startTime_ct = "";
    private String endTime_ct = "";
    private String projectAchievement_ct = "";
    private Resume.ProjectBackgroundOutputsBean bean;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        ButterKnife.bind(this);
        setRightText1("保存");
        setTitle("项目经验");
        Bundle bundle = getIntent().getExtras();
        bean = (Resume.ProjectBackgroundOutputsBean) bundle.get("bean");

        initView();
    }
    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectName_ct = projectName.getText().toString();
                projectRole_ct = projectRole.getText().toString();
                projectDescribe_ct = projectDescribe.getText().toString();
                startTime_ct = startTime.getText().toString();
                endTime_ct = endTime.getText().toString();
                projectUrl_ct = projectUrl.getText().toString();
                projectAchievement_ct = projectAchievement.getText().toString();

                if (TextUtils.isEmpty(projectName_ct)) {
                    AlertUtils.toast(context, "项目名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(projectRole_ct)) {
                    AlertUtils.toast(context, "项目角色不能为空");
                    return;
                }
                if (TextUtils.isEmpty(projectDescribe_ct)) {
                    AlertUtils.toast(context, "项目描述不能为空");
                    return;
                }
                if (TextUtils.isEmpty(startTime_ct)) {
                    AlertUtils.toast(context, "开始时间时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(endTime_ct)) {
                    AlertUtils.toast(context, "结束时间不能为空");
                    return;
                }
                Project project = new Project(projectName_ct, projectUrl_ct, projectRole_ct, projectDescribe_ct, projectAchievement_ct, startTime_ct, endTime_ct);


                if (bean != null) {
                    RequestBody body = new FormBody.Builder()
                            .add("Id", bean.getId() + "")
                            .add("ProjectName", project.getProjectName())
                            .add("Role", project.getRole())
                            .add("Description", project.getDescription())
                            .add("Performance", project.getPerformance())
                            .add("StartDate", project.getStartDate())
                            .add("EndDate", project.getEndDate())
                            .build();
                    upProject(body);
                } else {
                    List<Project> list = new ArrayList<Project>();
                    list.add(project);
                    addProject(gson.toJson(list));
                }


            }
        });


        if (bean != null) {
            projectName.setText(bean.getProjectName());
            projectUrl.setText(bean.getUrl());
            projectRole.setText(bean.getRole());
            startTime.setText(DateFormatUtil.parseDate(bean.getStartDate(), "yyyy-MM"));
            endTime.setText(DateFormatUtil.parseDate(bean.getEndDate(), "yyyy-MM"));
            projectDescribe.setText(bean.getDescription());
            projectAchievement.setText(bean.getPerformance());
            delete.setVisibility(View.VISIBLE);

        }
    }

    @OnClick({R.id.rl_project_name, R.id.rl_project_url, R.id.rl_project_describe, R.id.rl_project_achievement, R.id.rl_project_role, R.id.rl_start_time, R.id.rl_end_time, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_project_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "项目名字").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_PROJECT_NAME)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, projectName.getText().toString()));
                break;
            case R.id.rl_project_url:

                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "项目地址").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_PROJECT_URL)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, projectUrl.getText().toString()));
                break;
            case R.id.rl_project_describe:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "项目描述").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_PROJECT_DESCRIBE).
                        putExtra(ModifyContentActivity.HINT, "请输入项目描述").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, projectDescribe.getText().toString()));
                break;
            case R.id.rl_project_achievement:

                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "项目业绩").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_PROJECT_ACHIEVEMENT).
                        putExtra(ModifyContentActivity.HINT, "请输入项目业绩").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, projectAchievement.getText().toString()));
                break;
            case R.id.rl_project_role:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "项目角色").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_PROJECT_ROLE)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, projectRole.getText().toString()));
                break;
            case R.id.rl_start_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
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
                school_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                school_time.show();
                break;
            case R.id.rl_end_time:
                final TimePickerView graduation_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
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
                graduation_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                graduation_time.show();
                break;
            case R.id.delete:
                RequestBody body = new FormBody.Builder()
                        .add("Id", bean.getId() + "").build();
                deleteData(body);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_PROJECT_NAME: //send the video
                projectName.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_PROJECT_URL: //send the video
                projectUrl.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_PROJECT_ROLE: //send the video
                projectRole.setText(event.getContent());
                break;
            case ModifyContentActivity.TYPE_PROJECT_DESCRIBE: //send the video
                projectDescribe.setText(event.getContent());
                break;

            case ModifyContentActivity.TYPE_PROJECT_ACHIEVEMENT: //send the video
                projectAchievement.setText(event.getContent());
                break;

        }
    }


    //提交数据
    private void addProject(String json) {
        OkUtils.getInstances().postJson(context, JavaConstant.ProjectBackground, json, new TypeToken<EntityObject<Boolean>>() {
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
                        try {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    //更新数据
    private void upProject(RequestBody body) {
        OkUtils.getInstances().httput(body, context, JavaConstant.EducationBackground, JavaParamsUtils.getInstances().project(), new TypeToken<EntityObject<Boolean>>() {
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
        OkUtils.getInstances().httpDelete(body, context, JavaConstant.ProjectBackground + "?Id=" + bean.getId(), JavaParamsUtils.getInstances().project(), new TypeToken<EntityObject<Boolean>>() {
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
