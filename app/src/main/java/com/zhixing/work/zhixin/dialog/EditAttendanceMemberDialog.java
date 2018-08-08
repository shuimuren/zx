package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.bean.MemberBean;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.Utils;

import java.util.List;

/**
 * Created by lhj on 2018/7/25.
 * Description: 修改考勤成员
 */

public class EditAttendanceMemberDialog extends BaseDialog implements View.OnClickListener{



    private OnItemClickListener listener;
    private TextView mText;
    private TextView mBtnCancel;
    private TextView mBtnMakSure;



    public EditAttendanceMemberDialog(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    public void setText(List<MemberBean> beans){
        //考勤成员....已在其他考勤组,是否进行覆盖到当前考勤组
        StringBuilder builder = new StringBuilder();
        builder.append("考勤成员");
        for (int i = 0; i < beans.size(); i++) {
            builder.append(beans.get(i).getRealName());
            builder.append("、");
        }
        builder.substring(0,builder.length() -1);
        builder.append("已在其他考勤组,是否进行覆盖到当前考勤组");
        if(mText != null){
            mText.setText(Utils.changeColor(builder.toString(), ResourceUtils.getColor(R.color.color_71aae0),
                    4,builder.indexOf("已")));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_attendance_member);
        mText = findViewById(R.id.text);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnMakSure = findViewById(R.id.btn_make_sure);
        mBtnCancel.setOnClickListener(this);
        mBtnMakSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_make_sure:
                listener.dialogMakeSure();
                break;
        }

    }


    public interface OnItemClickListener {
        void dialogMakeSure();
    }
}
