package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.WorkBgsBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/16.
 * Description: 工作列表
 */

public class JobWorkListResult extends BaseResult {

   public List<WorkBgsBean> getContent() {
      return Content;
   }

   public void setContent(List<WorkBgsBean> content) {
      Content = content;
   }

   private List<WorkBgsBean> Content;
}
