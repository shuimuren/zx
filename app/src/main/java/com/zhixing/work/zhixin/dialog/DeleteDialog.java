package com.zhixing.work.zhixin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**
 *
 */

public class DeleteDialog extends BaseDialog implements View.OnClickListener {

    private final Context context;
    private OnItemClickListener listener;
    private TextView text;
    private TextView title;

    public DeleteDialog(Context context, String dialogTitle, String dialogText, OnItemClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_delete);
        this.context = context;
        this.listener = listener;
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        title=(TextView)findViewById(R.id.title);
        text=(TextView)findViewById(R.id.text);
        title.setText(dialogTitle);
        text.setText(dialogText);
        int sreemWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = (int) (sreemWidth * 0.8);
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.okBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okBtn:
                if (listener != null) {
                    listener.OnItemClick(1,this);
                }
                break;
            case R.id.cancelBtn:
                if (listener != null) {

                    listener.OnItemClick(0,this);
                }
                break;
        }
    }
    public interface OnItemClickListener {
        void OnItemClick(int index, Dialog dialog);
    }
}
