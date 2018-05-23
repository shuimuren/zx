package com.zhixing.work.zhixin.base;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class TestPaper {


    /**
     * TestPaperId : 2
     * Questions : [{"QuestionId":1,"TopicA":"对不公平的事，我会表达不满","TopicB":"我只关注哪些事情做得正确"},{"QuestionId":2,"TopicA":"当我了解过去时，我才能继续向前","TopicB":"当目标明确时，我才能继续向前"},{"QuestionId":4,"TopicA":"我总能集中精力去完成任务","TopicB":"我言而有信，说到做到"},{"QuestionId":5,"TopicA":"我善于分析数据","TopicB":"我经常回忆见过的美好景物"},{"QuestionId":8,"TopicA":"我激励朋友有所作为","TopicB":"我能能使一起工作的人和睦相处"},{"QuestionId":7,"TopicA":"我能把握对别人的谈话要点，使他们感觉良好","TopicB":"我通过倾听，使人们感觉获得理解"},{"QuestionId":10,"TopicA":"我搜集大量信息寻找正确答案","TopicB":"我对答案和问题一目了然"},{"QuestionId":12,"TopicA":"我更加了解自身的长处","TopicB":"我更加了解自身的不足"},{"QuestionId":14,"TopicA":"成为他人的知己使我满足","TopicB":"我希望成为他人的领导"},{"QuestionId":15,"TopicA":"我期望公平公正，不讲特权","TopicB":"如果有人被排除在外，我会感到难过"}]
     */

    private int TestPaperId;
    private List<QuestionsBean> Questions;

    public int getTestPaperId() {
        return TestPaperId;
    }

    public void setTestPaperId(int TestPaperId) {
        this.TestPaperId = TestPaperId;
    }

    public List<QuestionsBean> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<QuestionsBean> Questions) {
        this.Questions = Questions;
    }

    public static class QuestionsBean {
        /**
         * QuestionId : 1
         * TopicA : 对不公平的事，我会表达不满
         * TopicB : 我只关注哪些事情做得正确
         */

        private int QuestionId;

        public Integer getOption() {
            return option;
        }

        public void setOption(Integer option) {
            this.option = option;
        }

        private Integer option;
        private String TopicA;
        private String TopicB;

        public int getQuestionId() {
            return QuestionId;
        }

        public void setQuestionId(int QuestionId) {
            this.QuestionId = QuestionId;
        }

        public String getTopicA() {
            return TopicA;
        }

        public void setTopicA(String TopicA) {
            this.TopicA = TopicA;
        }

        public String getTopicB() {
            return TopicB;
        }

        public void setTopicB(String TopicB) {
            this.TopicB = TopicB;
        }
    }
}
