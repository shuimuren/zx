package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/10.
 * Description:
 */

public class EvaluateBean {
    public int getQuestionResultId() {
        return QuestionResultId;
    }

    public void setQuestionResultId(int questionResultId) {
        QuestionResultId = questionResultId;
    }

    public int getQuizTime() {
        return QuizTime;
    }

    public void setQuizTime(int quizTime) {
        QuizTime = quizTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public float getMindsetScore() {
        return MindsetScore;
    }

    public void setMindsetScore(float mindsetScore) {
        MindsetScore = mindsetScore;
    }

    public float getCovenantScore() {
        return CovenantScore;
    }

    public void setCovenantScore(float covenantScore) {
        CovenantScore = covenantScore;
    }

    public float getComplianceScore() {
        return ComplianceScore;
    }

    public void setComplianceScore(float complianceScore) {
        ComplianceScore = complianceScore;
    }

    public float getConnectionScore() {
        return ConnectionScore;
    }

    public void setConnectionScore(float connectionScore) {
        ConnectionScore = connectionScore;
    }

    public float getIntegrityScore() {
        return IntegrityScore;
    }

    public void setIntegrityScore(float integrityScore) {
        IntegrityScore = integrityScore;
    }

    public float getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(float totalScore) {
        TotalScore = totalScore;
    }

    /**
     * QuestionResultId : 1005
     * QuizTime : 50
     * StartTime : 2018-07-07 14:41:14
     * EndTime : 2018-07-07 14:41:39
     * MindsetScore : 40
     * CovenantScore : 30.0000019
     * ComplianceScore : 60.0000038
     * ConnectionScore : 60.0000038
     * IntegrityScore : 70
     * TotalScore : 52
     */


    private int QuestionResultId;
    private int QuizTime;
    private String StartTime;
    private String EndTime;
    private float MindsetScore;
    private float CovenantScore;
    private float ComplianceScore;
    private float ConnectionScore;
    private float IntegrityScore;
    private float TotalScore;


}
