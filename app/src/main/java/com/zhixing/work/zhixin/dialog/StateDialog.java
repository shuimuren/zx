package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**
 *
 */
public class StateDialog extends ButtomDialog {
    private TextView quit;
    private TextView not_consider;
    private TextView consider;
    private LinearLayout llClose;
    private OnItemClickListener clickListener;
    public static final int TYPE_QUIT = 0;
    public static final int TYPE_NOT_CONSIDER = 1;
    public static final int TYPE_CONSIDER = 2;

    public interface OnItemClickListener {

        void onClick(StateDialog dialog, int index);
    }

    public StateDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        setContentView(R.layout.dialog_state);
        bindViews();
    }

    private void bindViews() {
        quit = (TextView) findViewById(R.id.quit);
        not_consider = (TextView) findViewById(R.id.not_consider);
        consider = (TextView) findViewById(R.id.consider);
        llClose = (LinearLayout) findViewById(R.id.llClose);
        llClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });
        quit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(StateDialog.this, TYPE_QUIT);
            }

        });
        not_consider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(StateDialog.this, TYPE_NOT_CONSIDER);
            }

        });
        consider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(StateDialog.this, TYPE_CONSIDER);
            }

        });
    }
}
