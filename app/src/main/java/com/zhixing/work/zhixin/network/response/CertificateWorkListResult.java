package com.zhixing.work.zhixin.network.response;

import com.zhixing.work.zhixin.bean.CertificateBean;
import com.zhixing.work.zhixin.network.BaseResult;

import java.util.List;

/**
 * Created by lhj on 2018/8/17.
 * Description:
 */

public class CertificateWorkListResult extends BaseResult{
    public List<CertificateBean> getContent() {
        return Content;
    }

    public void setContent(List<CertificateBean> content) {
        Content = content;
    }

    List<CertificateBean> Content;
}
