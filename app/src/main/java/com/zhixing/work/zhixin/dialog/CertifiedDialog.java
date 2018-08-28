package com.zhixing.work.zhixin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;

/**
 * Created by lhj on 2018/7/25.
 * Description: 确认性提示Dialog
 */

public class CertifiedDialog extends BaseDialog implements View.OnClickListener {


    private OnItemClickListener listener;

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvDismiss;
    private Button btnCertified;


    public CertifiedDialog(Context context, OnItemClickListener listener) {
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
        btnCertified = findViewById(R.id.btn_certified);
        btnCertified.setOnClickListener(this);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvDismiss = findViewById(R.id.tv_dismiss);
        tvDismiss.setOnClickListener(this);
    }

    public void setView(String title, String content, String btnText, String dismissText) {
        tvTitle.setText(title);
        tvContent.setText(content);
        btnCertified.setText(btnText);
        tvDismiss.setText(dismissText);
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
