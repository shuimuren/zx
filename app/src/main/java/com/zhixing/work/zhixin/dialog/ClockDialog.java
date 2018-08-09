package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.util.ResourceUtils;

/**
 * Created by lhj on 2018/7/30.
 * Description: 打卡成功
 */

public class ClockDialog extends BaseDialog implements View.OnClickListener {

    private int type;//0:上班打卡,1:下班打卡
    private String time;
    private TextView tvClockType;
    private TextView tvClockTime;
    private TextView makeSure;

    public ClockDialog(Context context) {
        super(context);
    }

    public void setClockData(int type,String time){
        this.type = type;
        this.time = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_clock);
        tvClockTime = findViewById(R.id.tv_clock_time);
        tvClockType = findViewById(R.id.tv_clock_type);
        makeSure = findViewById(R.id.tv_make_sure);
        if(type == 0){
            tvClockType.setText(ResourceUtils.getString(R.string.clock_start));
        }else {
            tvClockType.setText(ResourceUtils.getString(R.string.clock_end));
        }
        if(!TextUtils.isEmpty(time)){
            tvClockTime.setText(time);
        }
        makeSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
