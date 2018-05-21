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

public class CardCompleteDialog extends BaseDialog implements View.OnClickListener {

    private final Context context;
    private OnItemClickListener listener;
    private TextView text;
    private TextView title;


    public CardCompleteDialog(Context context, OnItemClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_cdrd_complete);
        this.context = context;
        this.listener = listener;
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        int sreemWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = (int) (sreemWidth * 0.8);
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                listener.OnItemClick(this);
                break;

        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Dialog dialog);
    }
}
