package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;


/**

 */
public class ResidenceDialog extends BottomDialog {
    private TextView countryside;
    private TextView city;

    private LinearLayout llClose;
    private OnItemClickListener clickListener;
    public static final int TYPE_COUNTRYSIDE = 0;
    public static final int TYPE_CITY = 1;


    public interface OnItemClickListener {

        void onClick(ResidenceDialog dialog, int index);
    }

    public ResidenceDialog(Context context, OnItemClickListener clickListener) {
        super(context);
        this.clickListener = clickListener;
        setContentView(R.layout.dialog_residence);
        bindViews();
    }

    private void bindViews() {
        countryside = (TextView) findViewById(R.id.countryside);
        city = (TextView) findViewById(R.id.city);

        llClose = (LinearLayout) findViewById(R.id.llClose);
        llClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });

        countryside.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ResidenceDialog.this, TYPE_COUNTRYSIDE);
            }

        });
        city.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickListener.onClick(ResidenceDialog.this, TYPE_CITY);
            }

        });

    }
}
