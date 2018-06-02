package com.zhixing.work.zhixin.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class IndustryType {

    /**
     * Id : 1
     * ParentId : 0
     * Name : IT|通信|电子|互联网
     * Child : [{"Id":2,"ParentId":1,"Name":"互联网/电子商务","Child":[]},{"Id":3,"ParentId":1,"Name":"计算机软件","Child":[]},{"Id":4,"ParentId":1,"Name":"IT服务(系统/数据/维护)","Child":[]},{"Id":5,"ParentId":1,"Name":"电子技术/半导体/集成电路","Child":[]},{"Id":6,"ParentId":1,"Name":"计算机硬件","Child":[]},{"Id":7,"ParentId":1,"Name":"通信/电信/网络设备","Child":[]},{"Id":8,"ParentId":1,"Name":"通信/电信运营、增值服务","Child":[]},{"Id":9,"ParentId":1,"Name":"网络游戏","Child":[]}]
     */
    private int Id;
    private int ParentId;
    private String Name;
    private List<IndustryType> Child;

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

    public List<IndustryType> getChild() {
        return Child;
    }

    public void setChild(List<IndustryType> Child) {
        this.Child = Child;
    }

    public static class ChildBean {
        /**
         * Id : 2
         * ParentId : 1
         * Name : 互联网/电子商务
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
