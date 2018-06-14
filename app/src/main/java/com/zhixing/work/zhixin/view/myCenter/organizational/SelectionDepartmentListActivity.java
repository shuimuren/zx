package com.zhixing.work.zhixin.view.myCenter.organizational;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.dialog.NewDepartmentDialog;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.util.AlertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectionDepartmentListActivity extends BaseTitleActivity {

    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.department_name)
    TextView departmentName;
    @BindView(R.id.new_department)
    TextView newDepartment;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.listview)
    RecyclerView listview;
    private NewDepartmentDialog newDepartmentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_department_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_add)
    public void onViewClicked() {
        newDepartmentDialog = new NewDepartmentDialog(context, new NewDepartmentDialog.OnItemClickListener() {
            @Override
            public void OnItemClick(int index, Dialog dialog) {

                switch (index) {
                    case NewDepartmentDialog.TYPE_OK:
                        if (TextUtils.isEmpty(newDepartmentDialog.ed_text.getText().toString())) {
                            AlertUtils.toast(context, "部门名字不能为空");
                            return;
                        }
                        newDepartmentDialog.dismiss();

                        break;
                }
            }
        });
        newDepartmentDialog.show();
    }
}
