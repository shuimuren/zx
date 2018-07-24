package com.zhixing.work.zhixin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.widget.ClearEditText;


/**
 *
 */

public class NewDepartmentDialog extends BaseDialog implements View.OnClickListener {
    public static final int TYPE_OK = 0;
    public static final int TYPE_CANCEL = 1;
    private final Context context;
    private OnItemClickListener listener;
    public ClearEditText ed_text;
    private TextView title;

    public NewDepartmentDialog(Context context, OnItemClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_new_department);
        this.context = context;
        this.listener = listener;
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        title = (TextView) findViewById(R.id.title);
        ed_text = (ClearEditText) findViewById(R.id.ed_text);
        int sreemWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = (int) (sreemWidth * 0.8);
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.okBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okBtn:
                if (listener != null) {
                    listener.OnItemClick(TYPE_OK, this);
                }
                break;
            case R.id.cancelBtn:
                if (listener != null) {
                    dismiss();
                }
                break;
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int index, Dialog dialog);
    }
}
