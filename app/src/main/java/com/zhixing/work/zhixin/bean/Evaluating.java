package com.zhixing.work.zhixin.bean;

/**
 * Created by Administrator on 2018/5/23.
 */

public class Evaluating {


    /**
     * QuestionResultId : 7
     * QuizTime : 50
     * StartTime : 2018-05-23 16:08:52
     * EndTime : 2018-05-23 16:09:05
     * MindsetScore : 60.0000038
     * CovenantScore : 70.0
     * ComplianceScore : 70.0
     * ConnectionScore : 40.0
     * IntegrityScore : 50.0
     * TotalScore : 58.0
     */

    private int QuestionResultId;
    private int QuizTime;
    private String StartTime;
    private String EndTime;
    private double MindsetScore;
    private double CovenantScore;
    private double ComplianceScore;
    private double ConnectionScore;
    private double IntegrityScore;
    private double TotalScore;

    public int getQuestionResultId() {
        return QuestionResultId;
    }

    public void setQuestionResultId(int QuestionResultId) {
        this.QuestionResultId = QuestionResultId;
    }

    public int getQuizTime() {
        return QuizTime;
    }

    public void setQuizTime(int QuizTime) {
        this.QuizTime = QuizTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public double getMindsetScore() {
        return MindsetScore;
    }

    public void setMindsetScore(double MindsetScore) {
        this.MindsetScore = MindsetScore;
    }

    public double getCovenantScore() {
        return CovenantScore;
    }

    public void setCovenantScore(double CovenantScore) {
        this.CovenantScore = CovenantScore;
    }

    public double getComplianceScore() {
        return ComplianceScore;
    }

    public void setComplianceScore(double ComplianceScore) {
        this.ComplianceScore = ComplianceScore;
    }

    public double getConnectionScore() {
        return ConnectionScore;
    }

    public void setConnectionScore(double ConnectionScore) {
        this.ConnectionScore = ConnectionScore;
    }

    public double getIntegrityScore() {
        return IntegrityScore;
    }

    public void setIntegrityScore(double IntegrityScore) {
        this.IntegrityScore = IntegrityScore;
    }

    public double getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(double TotalScore) {
        this.TotalScore = TotalScore;
    }
}