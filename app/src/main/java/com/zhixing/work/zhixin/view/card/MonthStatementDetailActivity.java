package com.zhixing.work.zhixin.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.StaffStatementAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.MonthStatementBean;
import com.zhixing.work.zhixin.bean.StaffStatementBean;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.widget.ArrayBottomPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/27.
 * Description: 月评详情
 */

public class MonthStatementDetailActivity extends BaseTitleActivity {

    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.img_department_down)
    ImageView imgDepartmentDown;
    @BindView(R.id.rl_department)
    RelativeLayout rlDepartment;
    @BindView(R.id.tv_user_code)
    TextView tvUserCode;
    @BindView(R.id.img_user_code_down)
    ImageView imgUserCodeDown;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    private static final String KEY_MONTH = "month";
    private List<MonthStatementBean> monthStatementBeans;
    private StaffStatementAdapter mAdapter;
    private List<String> mDepartmentList;
    private ArrayBottomPopupWindow mArrayDepartmentBottom;
    private String mSelectedDepartment;

    public static void startMonthStatementActivity(Activity activity, String month) {
        Intent intent = new Intent(activity, MonthStatementDetailActivity.class);
        intent.putExtra(KEY_MONTH, month);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_statement_detail);
        ButterKnife.bind(this);
        setTitle("七月月评");
        intiView();
    }

    private void intiView() {
        monthStatementBeans = new ArrayList<>();
        List<StaffStatementBean> list1 = new ArrayList<>();
        list1.add(new StaffStatementBean("", "西本", 7.6f));
        list1.add(new StaffStatementBean("", "李牧然", 8.3f));
        list1.add(new StaffStatementBean("", "张勾", 9.2f));
        list1.add(new StaffStatementBean("", "苏浙莫", 8.7f));
        monthStatementBeans.add(new MonthStatementBean("研发部", list1));
        List<StaffStatementBean> list2 = new ArrayList<>();
        list2.add(new StaffStatementBean("", "本末", 6.6f));
        list2.add(new StaffStatementBean("", "日安", 8.7f));
        list2.add(new StaffStatementBean("", "王素", 8.2f));
        list2.add(new StaffStatementBean("", "赵都", 7.7f));
        monthStatementBeans.add(new MonthStatementBean("运维部", list2));
        List<StaffStatementBean> list3 = new ArrayList<>();
        list3.add(new StaffStatementBean("", "任颂风", 8.6f));
        list3.add(new StaffStatementBean("", "李理想", 6.3f));
        monthStatementBeans.add(new MonthStatementBean("测试部", list3));
        List<StaffStatementBean> list4 = new ArrayList<>();
        list4.add(new StaffStatementBean("", "慕容德", 7.6f));
        list4.add(new StaffStatementBean("", "李群可", 8.3f));
        list4.add(new StaffStatementBean("", "林氜", 6.2f));
        monthStatementBeans.add(new MonthStatementBean("产品部", list4));
        List<StaffStatementBean> list5 = new ArrayList<>();
        list5.add(new StaffStatementBean("", "张屋面", 7.6f));
        list5.add(new StaffStatementBean("", "李阿里", 8.2f));
        list5.add(new StaffStatementBean("", "张森林", 9.3f));
        list5.add(new StaffStatementBean("", "曹度", 6.7f));
        list5.add(new StaffStatementBean("", "爱丽丝", 4.7f));
        list5.add(new StaffStatementBean("", "莫非", 8.5f));
        monthStatementBeans.add(new MonthStatementBean("销售部", list5));

        mAdapter = new StaffStatementAdapter(this, monthStatementBeans);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        recyclerList.setAdapter(mAdapter);

        tvSort.setSelected(true);
        mDepartmentList = new ArrayList<>();
        mDepartmentList.add("所有部门");
        mDepartmentList.add("研发部");
        mDepartmentList.add("运维部");
        mDepartmentList.add("测试部");
        mDepartmentList.add("产品部");
        mDepartmentList.add("销售部");

        mSelectedDepartment = "所有部门";
    }


    @OnClick({R.id.img_department_down, R.id.img_user_code_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_department_down:
                mArrayDepartmentBottom = new ArrayBottomPopupWindow(this, rlDepartment, null, Utils.dp2px(this, 100));
                mArrayDepartmentBottom.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSelectedDepartment = mDepartmentList.get(position);
                        tvDepartment.setText(mSelectedDepartment);
                        if (position == 0) {
                            mAdapter.setData(monthStatementBeans);
                            tvSort.setSelected(true);
                        } else {
                            List<MonthStatementBean> beans = new ArrayList<>();
                            beans.add(monthStatementBeans.get(position - 1));
                            mAdapter.setData(beans);
                            tvSort.setSelected(false);
                        }


                    }
                });
                mArrayDepartmentBottom.setDataSet(mDepartmentList, mSelectedDepartment);
                mArrayDepartmentBottom.showAsDown(0, 0);
                break;
            case R.id.img_user_code_down:

                break;
        }
    }
}
