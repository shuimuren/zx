package com.zhixing.work.zhixin.view.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.Education;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddEducationActivity extends BaseTitleActivity {

    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.iv_education)
    ImageView ivEducation;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.graduate_college)
    TextView graduateCollege;
    @BindView(R.id.iv_graduate_college)
    ImageView ivGraduateCollege;
    @BindView(R.id.rl_graduate_college)
    RelativeLayout rlGraduateCollege;
    @BindView(R.id.school_time)
    TextView schoolTime;
    @BindView(R.id.iv_school_time)
    ImageView ivSchoolTime;
    @BindView(R.id.rl_school_time)
    RelativeLayout rlSchoolTime;
    @BindView(R.id.graduation_time)
    TextView graduationTime;
    @BindView(R.id.iv_graduation_time)
    ImageView ivGraduationTime;
    @BindView(R.id.rl_graduation_time)
    RelativeLayout rlGraduationTime;
    @BindView(R.id.major)
    TextView major;
    @BindView(R.id.iv_major)
    ImageView ivMajor;
    @BindView(R.id.rl_major)
    RelativeLayout rlMajor;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.iv_explain)
    ImageView ivExplain;
    @BindView(R.id.rl_explain)
    RelativeLayout rlExplain;
    private List<String> list;


    private int education_type;
    private String education_ct;
    private String graduate_college_ct;
    private String school_time_ct;
    private String graduation_time_ct;
    private String major_ct;
    public Context context;


    private String type;
    private Gson gson = new Gson();
    private Resume.EducationOutputsBean bean;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);
        ButterKnife.bind(this);
        context = this;
        type = getIntent().getStringExtra("type");
        Bundle bundle = getIntent().getExtras();
        bean = (Resume.EducationOutputsBean) bundle.get("bean");
        setTitle("教育经历");
        setRightText1("保存");
        initView();
    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                education_ct = education.getText().toString();
                graduate_college_ct = graduateCollege.getText().toString();
                school_time_ct = schoolTime.getText().toString();
                graduation_time_ct = graduationTime.getText().toString();
                major_ct = major.getText().toString();

                if (TextUtils.isEmpty(education_ct)) {
                    AlertUtils.toast(context, "学历信息不能为空");
                    return;
                }
                if (TextUtils.isEmpty(graduate_college_ct)) {
                    AlertUtils.toast(context, "毕业院校不能为空");
                    return;
                }
                if (TextUtils.isEmpty(school_time_ct)) {
                    AlertUtils.toast(context, "入学时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(graduation_time_ct)) {
                    AlertUtils.toast(context, "毕业时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(major_ct)) {
                    AlertUtils.toast(context, "专业信息不能为空");
                    return;
                }
                Education Education = new Education(education_type + "", graduate_college_ct, school_time_ct, graduation_time_ct, major_ct);

                if (type.equals("card")) {

                    EventBus.getDefault().post(Education);
                    finish();
                }
                if (type.equals("resume")) {

                    if (bean != null) {
                        RequestBody body = new FormBody.Builder()
                                .add("Id", bean.getId() + "")
                                .add("Education", Education.getEducation())
                                .add("School", Education.getSchool())
                                .add("StartDate", Education.getStartDate())
                                .add("EndDate", Education.getEndDate())
                                .add("Major", Education.getMajor())
                                .add("Experience", explain.getText().toString())
                                .build();
                        upEducation(body);
                    } else {
                        List<Education> list = new ArrayList<Education>();
                        Education.setExperience(explain.getText().toString());
                        list.add(Education);
                        addEducation(gson.toJson(list));
                    }

                }

            }
        });


        if (type.equals("resume")) {
            rlExplain.setVisibility(View.VISIBLE);
        }
        if (bean != null) {
            education.setText(Utils.getEducation(bean.getEducation()));
            graduateCollege.setText(bean.getSchool());
            schoolTime.setText(DateFormatUtil.parseDate(bean.getStartDate(), "yyyy-MM"));
            graduationTime.setText(DateFormatUtil.parseDate(bean.getEndDate(), "yyyy-MM"));
            major.setText(bean.getMajor());
            explain.setText(bean.getExperience());
            education_type = bean.getEducation();
            delete.setVisibility(View.VISIBLE);
            rlExplain.setVisibility(View.VISIBLE);
        }
    }
    @OnClick({R.id.rl_education, R.id.rl_graduate_college, R.id.rl_school_time, R.id.rl_graduation_time, R.id.rl_major, R.id.delete, R.id.rl_explain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_education:
                list = Arrays.asList(getResources().getStringArray(R.array.education));
                final OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = list.get(options1);
                        education_type = (options1 + 1) * 10;
                        education.setText(s);
                    }
                })
                        .setTitleText("学历")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
            case R.id.rl_graduate_college:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "毕业院校").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_SCHOOL)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, graduateCollege.getText().toString()));
                break;
            case R.id.rl_school_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        schoolTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("入校时间")
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
            case R.id.rl_graduation_time:
                final TimePickerView graduation_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTime(date2);
                        graduationTime.setText(time);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("毕业时间")
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
            case R.id.rl_major:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "专业").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.TYPE_MAJOR)
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, major.getText().toString())
                );
                break;
            case R.id.delete:
                RequestBody body = new FormBody.Builder()
                        .add("Id", bean.getId() + "").build();
                deleteData(body);
                break;
            case R.id.rl_explain:

                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "说明").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_EXPLAIN).
                        putExtra(ModifyContentActivity.HINT, "请输入说明").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, explain.getText().toString()));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_SCHOOL: //send the video
                graduateCollege.setText(event.getContent());
                break;
            case ModifyDataActivity.TYPE_MAJOR: //send the video
                major.setText(event.getContent());
                break;
            case ModifyContentActivity.TYPE_EXPLAIN: //send the video
                explain.setText(event.getContent());
                break;

        }
    }

    //提交数据
    private void addEducation(String json) {
        OkUtils.getInstances().postJson(context, RequestConstant.ADD_EDUCATION_EXPERIENCE, json, new TypeToken<EntityObject<Boolean>>() {
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
                            if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
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
    private void upEducation(RequestBody body) {
        OkUtils.getInstances().httpPut(body, context, RequestConstant.ADD_EDUCATION_EXPERIENCE, JavaParamsUtils.getInstances().upWork(), new TypeToken<EntityObject<Boolean>>() {
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
                        if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
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
        OkUtils.getInstances().httpDelete(context, RequestConstant.ADD_EDUCATION_EXPERIENCE + "?Id=" + bean.getId(), JavaParamsUtils.getInstances().deleteEducation(), new TypeToken<EntityObject<Boolean>>() {
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
