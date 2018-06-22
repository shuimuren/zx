package com.zhixing.work.zhixin.view.myCenter.resume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.ResumeCertificateAdapter;
import com.zhixing.work.zhixin.adapter.ResumeEducationAdapter;
import com.zhixing.work.zhixin.adapter.ResumeProjectAdapter;
import com.zhixing.work.zhixin.adapter.ResumeWorkAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Resume;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.dialog.StateDialog;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.view.card.AddCertificateActivity;
import com.zhixing.work.zhixin.view.card.AddEducationActivity;
import com.zhixing.work.zhixin.view.card.AddWorkActivity;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MyResumeActivity extends BaseTitleActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.user_info)
    TextView userInfo;
    @BindView(R.id.user_info_iv)
    ImageView userInfoIv;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.job_intention)
    TextView jobIntention;
    @BindView(R.id.job_intention_iv)
    ImageView jobIntentionIv;
    @BindView(R.id.rl_job_intention)
    RelativeLayout rlJobIntention;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.state_iv)
    ImageView stateIv;
    @BindView(R.id.ll_state)
    RelativeLayout llState;
    @BindView(R.id.advantage)
    TextView advantage;
    @BindView(R.id.advantage_iv)
    ImageView advantageIv;
    @BindView(R.id.rl_advantage)
    RelativeLayout rlAdvantage;
    @BindView(R.id.work_list)
    RecyclerView workList;
    @BindView(R.id.add_work)
    ImageView addWork;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.rl_add_work)
    RelativeLayout rlAddWork;
    @BindView(R.id.project_list)
    RecyclerView projectList;
    @BindView(R.id.add_project)
    ImageView addProject;
    @BindView(R.id.project)
    TextView project;
    @BindView(R.id.rl_add_project)
    RelativeLayout rlAddProject;
    @BindView(R.id.education_list)
    RecyclerView educationList;
    @BindView(R.id.add_education)
    ImageView addEducation;
    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.rl_add_education)
    RelativeLayout rlAddEducation;
    @BindView(R.id.certificate_list)
    RecyclerView certificateList;
    @BindView(R.id.add_certificate)
    ImageView addCertificate;
    @BindView(R.id.certificate)
    TextView certificate;
    @BindView(R.id.ll_add_certificate)
    RelativeLayout llAddCertificate;
    @BindView(R.id.preview)
    TextView preview;
    @BindView(R.id.avatar)
    ImageView avatar;


    private StsToken stsToken;
    private Resume resume;

    private String StartSalary = "";
    private String EndSalary = "";
    public final static String JOBHUNTINGSTATUS = "JobHuntingStatus";
    public final static String SELFINTRO = "SelfIntro";
    public final static String STARTSALARY = "StartSalary";
    public final static String ENDSALARY = "EndSalary";
    public final static String HIDDENRESUME = "HiddenResume";
    public final static String HIDDENEVALUATE = "HiddenEvaluate";

    private ResumeWorkAdapter workAdapter;
    private ResumeEducationAdapter educationAdapter;
    private ResumeCertificateAdapter certificateAdapter;
    private ResumeProjectAdapter projectAdapter;

    private List<Resume.WrokBackgroundOutputsBean> work_list = new ArrayList<Resume.WrokBackgroundOutputsBean>();
    private List<Resume.EducationOutputsBean> education_list = new ArrayList<Resume.EducationOutputsBean>();
    private List<Resume.CertificateOutputsBean> certificate_list = new ArrayList<Resume.CertificateOutputsBean>();
    private List<Resume.ProjectBackgroundOutputsBean> project_list = new ArrayList<Resume.ProjectBackgroundOutputsBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume);
        ButterKnife.bind(this);
        setTitle("我的简历");
        getOssToken();
        setRightText1("隐私设置");
        setOnClick();
        setListView();
    }


    private void setOnClick() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivacySettingsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", resume);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void setListView() {
        workAdapter = new ResumeWorkAdapter(work_list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workList.setLayoutManager(commodityLayoutManager);
        workList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        workList.setAdapter(workAdapter);
        workAdapter.setOnItemClickListener(new ResumeWorkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(context, AddWorkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", work_list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        educationAdapter = new ResumeEducationAdapter(education_list, context);
        LinearLayoutManager edLayoutManager = new LinearLayoutManager(context);
        edLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        educationList.setLayoutManager(edLayoutManager);
        educationList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        educationList.setAdapter(educationAdapter);


        educationAdapter.setOnItemClickListener(new ResumeEducationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, AddEducationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", education_list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        certificateAdapter = new ResumeCertificateAdapter(certificate_list, context);
        LinearLayoutManager cfLayoutManager = new LinearLayoutManager(context);
        cfLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        certificateList.setLayoutManager(cfLayoutManager);
        certificateList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        certificateList.setAdapter(certificateAdapter);
        certificateAdapter.setOnItemClickListener(new ResumeCertificateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, AddCertificateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", certificate_list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        projectAdapter = new ResumeProjectAdapter(project_list, context);
        LinearLayoutManager pjLayoutManager = new LinearLayoutManager(context);
        pjLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        projectList.setLayoutManager(pjLayoutManager);
        projectList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        projectList.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new ResumeProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, AddProjectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", project_list.get(position));
                intent.putExtra("type", "resume");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initView() {
        switch (resume.getJobHuntingStatus()) {
            case 0:
                state.setText("离职-随时到岗");
                break;
            case 1:
                state.setText("在职-暂不考虑");
                break;
            case 2:
                state.setText("在职-考虑机会");
                break;
        }

        if (!TextUtils.isEmpty(resume.getSelfIntro())) {
            advantage.setText(resume.getSelfIntro());
        }
        name.setText(resume.getPersonalInfo().getRealName());

        String url = ALiYunOssFileLoader.gteSecret(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, resume.getAvatar());

        GlideUtils.getInstance().loadCircleUserIconInto(context, url, avatar);

    }

//    获取简历
    private void getResume() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.Resume, JavaParamsUtils.getInstances().getResume(), new TypeToken<EntityObject<Resume>>() {
        }.getType(), new ResultCallBackListener<Resume>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<Resume> response) {
                if (response.getCode() == 10000) {
                    resume = response.getContent();
                    work_list = resume.getWrokBackgroundOutputs();
                    education_list = resume.getEducationOutputs();
                    certificate_list = resume.getCertificateOutputs();
                    project_list = resume.getProjectBackgroundOutputs();
                    workAdapter.setList(work_list);
                    educationAdapter.setList(education_list);
                    certificateAdapter.setList(certificate_list);
                    projectAdapter.setList(project_list);
                    if (resume != null) {
                        initView();
                    }
                }
            }
        });
    }

    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.getOSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == 10000) {
                    stsToken = response.getContent();
                    getResume();
                }
            }
        });
    }


    @OnClick({R.id.rl_user_info, R.id.rl_job_intention, R.id.ll_state, R.id.rl_advantage, R.id.rl_add_work, R.id.rl_add_project, R.id.rl_add_education, R.id.ll_add_certificate, R.id.preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_info:
                if (resume != null) {
                    Intent intent = new Intent(context, PersonalDataActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", resume);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            case R.id.rl_job_intention:
                if (resume != null) {
                    if (resume.getStartSalary() != null) {
                        StartSalary = resume.getStartSalary() + "";
                        EndSalary = resume.getEndSalary() + "";
                    }
                    startActivity(new Intent(context, JobIntensionActivity.class).putExtra("StartSalary", StartSalary).putExtra("EndSalary", EndSalary)
                            .putExtra("resumeId", resume.getId() + ""));
                }

                break;
            case R.id.ll_state:
                StateDialog dialog = new StateDialog(context, new StateDialog.OnItemClickListener() {

                    public void onClick(StateDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case StateDialog.TYPE_QUIT:
                                upUserData(JOBHUNTINGSTATUS, StateDialog.TYPE_QUIT + "");

                                break;
                            case StateDialog.TYPE_NOT_CONSIDER:
                                upUserData(JOBHUNTINGSTATUS, StateDialog.TYPE_NOT_CONSIDER + "");
                                break;
                            case StateDialog.TYPE_CONSIDER:
                                upUserData(JOBHUNTINGSTATUS, StateDialog.TYPE_CONSIDER + "");
                                break;
                        }
                    }
                });

                dialog.show();
                break;
            case R.id.rl_advantage:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "我的优势").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.TYPE_ADVANTAGE).
                        putExtra(ModifyContentActivity.HINT, "请输入工作优势").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, advantage.getText().toString()));
                break;
            case R.id.rl_add_work:

                startActivity(new Intent(context, AddWorkActivity.class).putExtra("type", "resume"));

                break;
            case R.id.rl_add_project:
                startActivity(new Intent(context, AddProjectActivity.class).putExtra("type", "resume"));
                break;
            case R.id.rl_add_education:
                startActivity(new Intent(context, AddEducationActivity.class).putExtra("type", "resume"));
                break;
            case R.id.ll_add_certificate:

                startActivity(new Intent(context, AddCertificateActivity.class).putExtra("type", "resume"));
                break;
            case R.id.preview:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyContentActivity.TYPE_ADVANTAGE: //send the video

                upUserData(SELFINTRO, event.getContent());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResumeRefreshEvent(ResumeRefreshEvent event) {
        if (event.getRefresh()) {
            getResume();
        }

    }

    //更新数据
    private void upUserData(final String type, final String value) {
        final RequestBody body = new FormBody.Builder()
                .add("Id", resume.getId() + "")
                .add(type, value).build();
        OkUtils.getInstances().httpatch(body, context, JavaConstant.Resume, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
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
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            switch (type) {
                                case JOBHUNTINGSTATUS:
                                    switch (Integer.parseInt(value)) {
                                        case 0:
                                            state.setText("离职-随时到岗");
                                            break;
                                        case 1:
                                            state.setText("在职-暂不考虑");
                                            break;
                                        case 2:
                                            state.setText("在职-考虑机会");
                                            break;
                                    }
                                    break;
                                case SELFINTRO:
                                    advantage.setText(value);
                                    break;
                            }

                        }
                    } else {
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
    }


}
