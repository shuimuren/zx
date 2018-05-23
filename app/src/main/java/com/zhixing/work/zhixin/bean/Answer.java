package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Answer {

    /**
     * TestPaperId : 1
     * QuizTime : 50
     * AnswerList : [1,3,0,4,0,1,2,3,1,0]
     */

    private int TestPaperId;
    private int QuizTime;
    private List<Integer> AnswerList;

    public int getTestPaperId() {
        return TestPaperId;
    }

    public void setTestPaperId(int TestPaperId) {
        this.TestPaperId = TestPaperId;
    }

    public int getQuizTime() {
        return QuizTime;
    }

    public void setQuizTime(int QuizTime) {
        this.QuizTime = QuizTime;
    }

    public List<Integer> getAnswerList() {
        return AnswerList;
    }

    public void setAnswerList(List<Integer> AnswerList) {
        this.AnswerList = AnswerList;
    }
}
