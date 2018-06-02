package com.zhixing.work.zhixin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */

public class JobType  implements Serializable{


    /**
     * Id : 1
     * ParentId : 0
     * Name : 销售|客服|市场
     * Child : [{"Id":2,"ParentId":1,"Name":"销售业务","Child":[{"Id":3,"ParentId":2,"Name":"销售代表","Child":[]},{"Id":4,"ParentId":2,"Name":"客户代表","Child":[]},{"Id":5,"ParentId":2,"Name":"销售工程师","Child":[]},{"Id":6,"ParentId":2,"Name":"渠道/分销专员","Child":[]},{"Id":7,"ParentId":2,"Name":"区域销售专员/助理","Child":[]},{"Id":8,"ParentId":2,"Name":"业务拓展专员/助理","Child":[]},{"Id":9,"ParentId":2,"Name":"大客户销售代表","Child":[]},{"Id":10,"ParentId":2,"Name":"电话销售","Child":[]},{"Id":11,"ParentId":2,"Name":"网络/在线销售","Child":[]},{"Id":12,"ParentId":2,"Name":"团购业务员","Child":[]},{"Id":13,"ParentId":2,"Name":"销售业务跟单","Child":[]},{"Id":14,"ParentId":2,"Name":"医药代表","Child":[]},{"Id":15,"ParentId":2,"Name":"经销商","Child":[]},{"Id":16,"ParentId":2,"Name":"招商经理","Child":[]},{"Id":17,"ParentId":2,"Name":"招商主管","Child":[]},{"Id":18,"ParentId":2,"Name":"招商专员","Child":[]},{"Id":19,"ParentId":2,"Name":"会籍顾问","Child":[]},{"Id":20,"ParentId":2,"Name":"其他","Child":[]}]},{"Id":21,"ParentId":1,"Name":"销售管理","Child":[{"Id":22,"ParentId":21,"Name":"销售总监","Child":[]},{"Id":23,"ParentId":21,"Name":"销售经理","Child":[]},{"Id":24,"ParentId":21,"Name":"销售主管","Child":[]},{"Id":25,"ParentId":21,"Name":"客户总监","Child":[]},{"Id":26,"ParentId":21,"Name":"客户经理","Child":[]},{"Id":27,"ParentId":21,"Name":"客户主管","Child":[]},{"Id":28,"ParentId":21,"Name":"渠道/分销总监","Child":[]},{"Id":29,"ParentId":21,"Name":"渠道/分销经理/主管","Child":[]},{"Id":30,"ParentId":21,"Name":"区域销售总监","Child":[]},{"Id":31,"ParentId":21,"Name":"区域销售经理/主管","Child":[]},{"Id":32,"ParentId":21,"Name":"业务拓展经理/主管","Child":[]},{"Id":33,"ParentId":21,"Name":"大客户销售经理","Child":[]},{"Id":34,"ParentId":21,"Name":"团购经理/主管","Child":[]},{"Id":35,"ParentId":21,"Name":"医药销售经理/主管","Child":[]},{"Id":36,"ParentId":21,"Name":"其他","Child":[]}]},{"Id":37,"ParentId":1,"Name":"销售行政/商务","Child":[{"Id":38,"ParentId":37,"Name":"销售行政经理/主管","Child":[]},{"Id":39,"ParentId":37,"Name":"销售行政专员/助理","Child":[]},{"Id":40,"ParentId":37,"Name":"销售运营经理/主管","Child":[]},{"Id":41,"ParentId":37,"Name":"销售运营专员/助理","Child":[]},{"Id":42,"ParentId":37,"Name":"商务经理/主管","Child":[]},{"Id":43,"ParentId":37,"Name":"商务专员/助理","Child":[]},{"Id":44,"ParentId":37,"Name":"销售培训师/讲师","Child":[]},{"Id":45,"ParentId":37,"Name":"销售数据分析","Child":[]},{"Id":46,"ParentId":37,"Name":"业务分析经理/主管","Child":[]},{"Id":47,"ParentId":37,"Name":"业务分析专员/助理","Child":[]},{"Id":48,"ParentId":37,"Name":"其他","Child":[]}]},{"Id":49,"ParentId":1,"Name":"客服/售前/售后技术支持","Child":[{"Id":50,"ParentId":49,"Name":"客户服务总监","Child":[]},{"Id":51,"ParentId":49,"Name":"客户服务经理","Child":[]},{"Id":52,"ParentId":49,"Name":"客户服务主管","Child":[]},{"Id":53,"ParentId":49,"Name":"客户服务专员/助理","Child":[]},{"Id":54,"ParentId":49,"Name":"客户关系/投诉协调人员","Child":[]},{"Id":55,"ParentId":49,"Name":"客户咨询热线/呼叫中心人员","Child":[]},{"Id":56,"ParentId":49,"Name":"网络/在线客服","Child":[]},{"Id":57,"ParentId":49,"Name":"售前/售后技术支持管理","Child":[]},{"Id":58,"ParentId":49,"Name":"售前/售后技术支持工程师","Child":[]},{"Id":59,"ParentId":49,"Name":"VIP专员","Child":[]},{"Id":60,"ParentId":49,"Name":"呼叫中心客服","Child":[]},{"Id":61,"ParentId":49,"Name":"其他","Child":[]}]},{"Id":62,"ParentId":1,"Name":"市场","Child":[{"Id":63,"ParentId":62,"Name":"市场总监","Child":[]},{"Id":64,"ParentId":62,"Name":"市场经理","Child":[]},{"Id":65,"ParentId":62,"Name":"市场主管","Child":[]},{"Id":66,"ParentId":62,"Name":"市场专员/助理","Child":[]},{"Id":67,"ParentId":62,"Name":"市场营销经理","Child":[]},{"Id":68,"ParentId":62,"Name":"市场营销主管","Child":[]},{"Id":69,"ParentId":62,"Name":"市场营销专员/助理","Child":[]},{"Id":70,"ParentId":62,"Name":"业务拓展经理/主管","Child":[]},{"Id":71,"ParentId":62,"Name":"业务拓展专员/助理","Child":[]},{"Id":72,"ParentId":62,"Name":"产品经理","Child":[]},{"Id":73,"ParentId":62,"Name":"产品主管","Child":[]},{"Id":74,"ParentId":62,"Name":"产品专员/助理","Child":[]},{"Id":75,"ParentId":62,"Name":"品牌经理","Child":[]},{"Id":76,"ParentId":62,"Name":"品牌主管","Child":[]},{"Id":77,"ParentId":62,"Name":"品牌专员/助理","Child":[]},{"Id":78,"ParentId":62,"Name":"市场策划/企划经理/主管","Child":[]},{"Id":79,"ParentId":62,"Name":"市场策划/企划专员/助理","Child":[]},{"Id":80,"ParentId":62,"Name":"市场文案策划","Child":[]},{"Id":81,"ParentId":62,"Name":"活动策划","Child":[]},{"Id":82,"ParentId":62,"Name":"活动执行","Child":[]},{"Id":83,"ParentId":62,"Name":"促销主管/督导","Child":[]},{"Id":84,"ParentId":62,"Name":"促销员","Child":[]},{"Id":85,"ParentId":62,"Name":"网站推广","Child":[]},{"Id":86,"ParentId":62,"Name":"SEO/SEM","Child":[]},{"Id":87,"ParentId":62,"Name":"学术推广","Child":[]},{"Id":88,"ParentId":62,"Name":"选址拓展/新店开发","Child":[]},{"Id":89,"ParentId":62,"Name":"市场调研与分析","Child":[]},{"Id":90,"ParentId":62,"Name":"品牌策划","Child":[]},{"Id":91,"ParentId":62,"Name":"市场通路专员","Child":[]},{"Id":92,"ParentId":62,"Name":"促销经理","Child":[]},{"Id":93,"ParentId":62,"Name":"其他","Child":[]}]},{"Id":94,"ParentId":1,"Name":"公关/媒介","Child":[{"Id":95,"ParentId":94,"Name":"公关总监","Child":[]},{"Id":96,"ParentId":94,"Name":"公关经理/主管","Child":[]},{"Id":97,"ParentId":94,"Name":"公关专员/助理","Child":[]},{"Id":98,"ParentId":94,"Name":"媒介经理/主管","Child":[]},{"Id":99,"ParentId":94,"Name":"媒介专员/助理","Child":[]},{"Id":100,"ParentId":94,"Name":"媒介策划/管理","Child":[]},{"Id":101,"ParentId":94,"Name":"政府事务管理","Child":[]},{"Id":102,"ParentId":94,"Name":"媒介销售","Child":[]},{"Id":103,"ParentId":94,"Name":"活动执行","Child":[]},{"Id":104,"ParentId":94,"Name":"其他","Child":[]}]},{"Id":105,"ParentId":1,"Name":"广告/会展","Child":[{"Id":106,"ParentId":105,"Name":"广告创意/设计总监","Child":[]},{"Id":107,"ParentId":105,"Name":"广告创意/设计经理/主管","Child":[]},{"Id":108,"ParentId":105,"Name":"广告创意/设计师","Child":[]},{"Id":109,"ParentId":105,"Name":"广告文案策划","Child":[]},{"Id":110,"ParentId":105,"Name":"广告美术指导","Child":[]},{"Id":111,"ParentId":105,"Name":"广告制作执行","Child":[]},{"Id":112,"ParentId":105,"Name":"广告客户总监","Child":[]},{"Id":113,"ParentId":105,"Name":"广告客户经理","Child":[]},{"Id":114,"ParentId":105,"Name":"广告客户主管","Child":[]},{"Id":115,"ParentId":105,"Name":"广告客户代表","Child":[]},{"Id":116,"ParentId":105,"Name":"广告/会展业务拓展","Child":[]},{"Id":117,"ParentId":105,"Name":"会展策划/设计","Child":[]},{"Id":118,"ParentId":105,"Name":"会务经理/主管","Child":[]},{"Id":119,"ParentId":105,"Name":"会务专员/助理","Child":[]},{"Id":120,"ParentId":105,"Name":"广告/会展项目管理","Child":[]},{"Id":121,"ParentId":105,"Name":"企业/业务发展经理","Child":[]},{"Id":122,"ParentId":105,"Name":"其他","Child":[]}]}]
     */

    private int Id;
    private int ParentId;
    private String Name;
    private List<JobType> Child;

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    private int isSelect;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<JobType> getChild() {
        return Child;
    }

    public void setChild(List<JobType> Child) {
        this.Child = Child;
    }

    public static class ChildBeanX {
        /**
         * Id : 2
         * ParentId : 1
         * Name : 销售业务
         * Child : [{"Id":3,"ParentId":2,"Name":"销售代表","Child":[]},{"Id":4,"ParentId":2,"Name":"客户代表","Child":[]},{"Id":5,"ParentId":2,"Name":"销售工程师","Child":[]},{"Id":6,"ParentId":2,"Name":"渠道/分销专员","Child":[]},{"Id":7,"ParentId":2,"Name":"区域销售专员/助理","Child":[]},{"Id":8,"ParentId":2,"Name":"业务拓展专员/助理","Child":[]},{"Id":9,"ParentId":2,"Name":"大客户销售代表","Child":[]},{"Id":10,"ParentId":2,"Name":"电话销售","Child":[]},{"Id":11,"ParentId":2,"Name":"网络/在线销售","Child":[]},{"Id":12,"ParentId":2,"Name":"团购业务员","Child":[]},{"Id":13,"ParentId":2,"Name":"销售业务跟单","Child":[]},{"Id":14,"ParentId":2,"Name":"医药代表","Child":[]},{"Id":15,"ParentId":2,"Name":"经销商","Child":[]},{"Id":16,"ParentId":2,"Name":"招商经理","Child":[]},{"Id":17,"ParentId":2,"Name":"招商主管","Child":[]},{"Id":18,"ParentId":2,"Name":"招商专员","Child":[]},{"Id":19,"ParentId":2,"Name":"会籍顾问","Child":[]},{"Id":20,"ParentId":2,"Name":"其他","Child":[]}]
         */

        private int Id;
        private int ParentId;
        private String Name;
        private List<JobType> Child;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public List<JobType> getChild() {
            return Child;
        }

        public void setChild(List<JobType> Child) {
            this.Child = Child;
        }

        public static class ChildBean {
            /**
             * Id : 3
             * ParentId : 2
             * Name : 销售代表
             * Child : []
             */

            private int Id;
            private int ParentId;
            private String Name;
            private List<?> Child;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public List<?> getChild() {
                return Child;
            }

            public void setChild(List<?> Child) {
                this.Child = Child;
            }
        }
    }
}
