package com.zhixing.work.zhixin.view.score;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.SubjectAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.base.TestPaper;
import com.zhixing.work.zhixin.bean.Answer;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.Evaluating;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.card.PerfectCardCertificateActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InitialEvaluationActivity extends BaseTitleActivity {

    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.next)
    TextView next;
    private int number;
    private Set<Integer> set = new HashSet<Integer>();
    private TestPaper testPaper = new TestPaper();
    private SubjectAdapter adapter;
    private List<TestPaper.QuestionsBean> list = new ArrayList<TestPaper.QuestionsBean>();
    private Gson gson = new Gson();
    private List<Integer> answerList = new ArrayList<Integer>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_evaluation);
        ButterKnife.bind(this);
        setTitle("初始评测");
        showView();
        getData();
    }

    private void showView() {
        adapter = new SubjectAdapter(list, context);
        LinearLayoutManager commodityLayoutManager = new LinearLayoutManager(context);
        commodityLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(commodityLayoutManager);
        listview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        //addressAdapter.setOnItemClickListener(this);
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                set.add(position);
                numberProgressBar.setProgress(set.size() * 10);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    private void getData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.TestPaper, JavaParamsUtils.getInstances().TestPaper(), new TypeToken<EntityObject<TestPaper>>() {
        }.getType(), new ResultCallBackListener<TestPaper>() {
            @Override
            public void onFailure(int errorId, String msg) {

                if (!TextUtils.isEmpty(SettingUtils.getTestPaper())) {
                    testPaper = gson.fromJson(SettingUtils.getTestPaper(), TestPaper.class);
                    list = testPaper.getQuestions();
                    adapter.setList(list);
                } else {
                    AlertUtils.toast(context, msg);
                }
            }

            @Override
            public void onSuccess(EntityObject<TestPaper> response) {
                if (response.getCode() == 10000) {
                    testPaper = response.getContent();
                    list = response.getContent().getQuestions();
                    SettingUtils.putTestPaper(gson.toJson(testPaper));
                    adapter.setList(list);
                } else {
                    if (!TextUtils.isEmpty(SettingUtils.getTestPaper())) {
                        testPaper = gson.fromJson(SettingUtils.getTestPaper(), TestPaper.class);
                        list = testPaper.getQuestions();
                        adapter.setList(list);
                    }
                }
            }
        });
    }

    @OnClick({R.id.next, R.id.ll_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                answerList.clear();

                List<TestPaper.QuestionsBean> list = adapter.getList();

                Collections.sort(list, new Comparator<TestPaper.QuestionsBean>() {
                    public int compare(TestPaper.QuestionsBean o1, TestPaper.QuestionsBean o2) {
                        int i = o1.getQuestionId() - o2.getQuestionId();
                        return i;
                    }
                });
                for (int i = 0; i < list.size(); i++) {
                    TestPaper.QuestionsBean bean = list.get(i);
                    if (bean.getOption() != null) {
                        answerList.add(bean.getOption());
                    }
                }
                if (answerList.size() < 10) {
                    AlertUtils.toast(context, "请选择所有的题目");
                    return;
                }
                Answer answer = new Answer();
                answer.setQuizTime(50);
                answer.setTestPaperId(testPaper.getTestPaperId());
                answer.setAnswerList(answerList);
                showLoadingDialog("");
                submit(gson.toJson(answer));
                break;
            case R.id.ll_next:
                break;
        }
    }
    private void submit(String json) {
        OkUtils.getInstances().patchJson(context, JavaConstant.Evaluate, json, new TypeToken<EntityObject<Evaluating>>() {
        }.getType(), new ResultCallBackListener<Evaluating>() {
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
            public void onSuccess(final EntityObject<Evaluating> response) {
                hideLoadingDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response.getCode() == 10000) {
                                if (response.getContent() != null) {
                                    AlertUtils.toast(context, "提交成功");
                                    SettingUtils.putTestPaper("");
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


}
