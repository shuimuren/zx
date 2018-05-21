package com.zhixing.work.zhixin.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class AddressJson  implements IPickerViewData {

    /**
     * Id : 1
     * Type : 1
     * ParentId : 0
     * Name : 北京市
     * Child : [{"Id":2,"Type":2,"ParentId":1,"Name":"市辖区","Child":[{"Id":3,"Type":3,"ParentId":2,"Name":"东城区","Child":null},{"Id":4,"Type":3,"ParentId":2,"Name":"西城区","Child":null},{"Id":5,"Type":3,"ParentId":2,"Name":"朝阳区","Child":null},{"Id":6,"Type":3,"ParentId":2,"Name":"丰台区","Child":null},{"Id":7,"Type":3,"ParentId":2,"Name":"石景山区","Child":null},{"Id":8,"Type":3,"ParentId":2,"Name":"海淀区","Child":null},{"Id":9,"Type":3,"ParentId":2,"Name":"门头沟区","Child":null},{"Id":10,"Type":3,"ParentId":2,"Name":"房山区","Child":null},{"Id":11,"Type":3,"ParentId":2,"Name":"通州区","Child":null},{"Id":12,"Type":3,"ParentId":2,"Name":"顺义区","Child":null},{"Id":13,"Type":3,"ParentId":2,"Name":"昌平区","Child":null},{"Id":14,"Type":3,"ParentId":2,"Name":"大兴区","Child":null},{"Id":15,"Type":3,"ParentId":2,"Name":"怀柔区","Child":null},{"Id":16,"Type":3,"ParentId":2,"Name":"平谷区","Child":null},{"Id":17,"Type":3,"ParentId":2,"Name":"密云区","Child":null},{"Id":18,"Type":3,"ParentId":2,"Name":"延庆区","Child":null}]}]
     */

    private int Id;
    private int Type;
    private int ParentId;
    private String Name;
    private List<ChildBeanX> Child;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
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

    public List<ChildBeanX> getChild() {
        return Child;
    }

    public void setChild(List<ChildBeanX> Child) {
        this.Child = Child;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.Name;
    }

    public static class ChildBeanX {
        /**
         * Id : 2
         * Type : 2
         * ParentId : 1
         * Name : 市辖区
         * Child : [{"Id":3,"Type":3,"ParentId":2,"Name":"东城区","Child":null},{"Id":4,"Type":3,"ParentId":2,"Name":"西城区","Child":null},{"Id":5,"Type":3,"ParentId":2,"Name":"朝阳区","Child":null},{"Id":6,"Type":3,"ParentId":2,"Name":"丰台区","Child":null},{"Id":7,"Type":3,"ParentId":2,"Name":"石景山区","Child":null},{"Id":8,"Type":3,"ParentId":2,"Name":"海淀区","Child":null},{"Id":9,"Type":3,"ParentId":2,"Name":"门头沟区","Child":null},{"Id":10,"Type":3,"ParentId":2,"Name":"房山区","Child":null},{"Id":11,"Type":3,"ParentId":2,"Name":"通州区","Child":null},{"Id":12,"Type":3,"ParentId":2,"Name":"顺义区","Child":null},{"Id":13,"Type":3,"ParentId":2,"Name":"昌平区","Child":null},{"Id":14,"Type":3,"ParentId":2,"Name":"大兴区","Child":null},{"Id":15,"Type":3,"ParentId":2,"Name":"怀柔区","Child":null},{"Id":16,"Type":3,"ParentId":2,"Name":"平谷区","Child":null},{"Id":17,"Type":3,"ParentId":2,"Name":"密云区","Child":null},{"Id":18,"Type":3,"ParentId":2,"Name":"延庆区","Child":null}]
         */

        private int Id;
        private int Type;
        private int ParentId;
        private String Name;
        private List<ChildBean> Child;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
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

        public List<ChildBean> getChild() {
            return Child;
        }

        public void setChild(List<ChildBean> Child) {
            this.Child = Child;
        }

        public static class ChildBean {
            /**
             * Id : 3
             * Type : 3
             * ParentId : 2
             * Name : 东城区
             * Child : null
             */

            private int Id;
            private int Type;
            private int ParentId;
            private String Name;
            private Object Child;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
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

            public Object getChild() {
                return Child;
            }

            public void setChild(Object Child) {
                this.Child = Child;
            }
        }
    }
}
