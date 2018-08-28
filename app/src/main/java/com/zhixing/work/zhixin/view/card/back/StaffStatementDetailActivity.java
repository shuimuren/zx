package com.zhixing.work.zhixin.view.card.back;

import android.os.Bundle;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;

/**
 * Created by lhj on 2018/8/28.
 * Description: 评价详情 触发随机事件/没触发随机事件
 */

public class StaffStatementDetailActivity extends BaseTitleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_statement_detail);
        setTitle(ResourceUtils.getString(R.string.staff_statement_detail_title));
    }
}
