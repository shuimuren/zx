package com.zhixing.work.zhixin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zhixing.work.zhixin.R;

/**
 * Created by lhj on 2018/7/25.
 * Description: 考勤提醒
 */

public class AttendanceRemindDialog extends BaseDialog implements View.OnClickListener{



    private OnItemClickListener listener;



    public AttendanceRemindDialog(Context context, OnItemClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_certified);

        this.listener = listener;
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        int screenWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = (int) (screenWidth * 0.8);
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.btn_certified).setOnClickListener(this);
        findViewById(R.id.tv_dismiss).setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_certified:
                listener.OnItemClick(this);
                break;
            case R.id.tv_dismiss:
                this.dismiss();
                break;

        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Dialog dialog);
    }
}
