package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**

 */
public class MarriageDialog extends ButtomDialog {
    private TextView married;
    private TextView secrecy;
    private TextView unmarried;
    private LinearLayout llClose;
    private OnItemClickListener clickListener;
    public static final int TYPE_UNMARRIED = 0;
    public static final int TYPE_MARRIED = 1;
    public static final int TYPE_SECRECY = 2;

    public interface OnItemClickListener {

        void onClick(MarriageDialog dialog, int index);
    }

    public MarriageDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        setContentView(R.layout.dialog_marriage);
        bindViews();
    }

    private void bindViews() {
        unmarried = (TextView) findViewById(R.id.unmarried);
        married = (TextView) findViewById(R.id.married);
        secrecy = (TextView) findViewById(R.id.secrecy);
        llClose = (LinearLayout) findViewById(R.id.llClose);
        llClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });
        unmarried.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(MarriageDialog.this, TYPE_UNMARRIED);
            }

        });
        married.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(MarriageDialog.this, TYPE_MARRIED);
            }

        });
        secrecy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(MarriageDialog.this, TYPE_SECRECY);
            }

        });
    }
}
