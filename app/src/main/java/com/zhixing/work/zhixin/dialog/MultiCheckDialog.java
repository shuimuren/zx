package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zhixing.work.zhixin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhj on 2018/8/1.
 * Description: 工作时间选择
 */

public abstract class MultiCheckDialog extends BaseDialog implements CompoundButton.OnCheckedChangeListener{


    private Button mCancelButton;
    private Button mConfirmButton;

    private CheckBox mCheckMon;
    private CheckBox mCheckTue;
    private CheckBox mCheckWed;
    private CheckBox mCheckThu;
    private CheckBox mCheckFri;
    private CheckBox mCheckSat;
    private CheckBox mCheckSun;

    private Boolean[] checked = new Boolean[7];
    private List<Integer> mSelectedWork;

    public MultiCheckDialog(Context context) {
        super(context);
    }

    public void setDayRange(List<Integer> selected){
        mSelectedWork = selected;
        for (int i = 0; i < checked.length; i++) {
            checked[i] = isSelected(i);
        }
    }

    private  boolean isSelected(int i){
        switch (i){
            case 0:
                return mSelectedWork.contains(1);
            case 1:
                return mSelectedWork.contains(2);
            case 2:
                return mSelectedWork.contains(4);
            case 3:
                return mSelectedWork.contains(8);
            case 4:
                return mSelectedWork.contains(16);
            case 5:
                return mSelectedWork.contains(32);
            case 6:
                return mSelectedWork.contains(64);


            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_multi_check);

        mCheckMon = (CheckBox) findViewById(R.id.check_box_monday);
        mCheckMon.setChecked(checked[0]);
        mCheckMon.setOnCheckedChangeListener(this);

        mCheckTue = (CheckBox) findViewById(R.id.check_box_tuesday);
        mCheckTue.setChecked(checked[1]);
        mCheckTue.setOnCheckedChangeListener(this);

        mCheckWed = (CheckBox) findViewById(R.id.check_box_wednesday);
        mCheckWed.setChecked(checked[2]);
        mCheckWed.setOnCheckedChangeListener(this);

        mCheckThu = (CheckBox) findViewById(R.id.check_box_thursday);
        mCheckThu.setChecked(checked[3]);
        mCheckThu.setOnCheckedChangeListener(this);

        mCheckFri = (CheckBox) findViewById(R.id.check_box_friday);
        mCheckFri.setChecked(checked[4]);
        mCheckFri.setOnCheckedChangeListener(this);

        mCheckSat = (CheckBox) findViewById(R.id.check_box_saturday);
        mCheckSat.setChecked(checked[5]);
        mCheckSat.setOnCheckedChangeListener(this);

        mCheckSun = (CheckBox) findViewById(R.id.check_box_sunday);
        mCheckSun.setChecked(checked[6]);
        mCheckSun.setOnCheckedChangeListener(this);

        mCancelButton = (Button) findViewById(R.id.btn_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mConfirmButton = (Button) findViewById(R.id.btn_make_sure);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDaysConfirmMethod();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_box_monday:
                checked[0] = isChecked;
                break;
            case R.id.check_box_tuesday:
                checked[1] = isChecked;
                break;
            case R.id.check_box_wednesday:
                checked[2] = isChecked;
                break;
            case R.id.check_box_thursday:
                checked[3] = isChecked;
                break;
            case R.id.check_box_friday:
                checked[4] = isChecked;
                break;
            case R.id.check_box_saturday:
                checked[5] = isChecked;
                break;
            case R.id.check_box_sunday:
                checked[6] = isChecked;
                break;
            default:
                break;
        }
    }

    protected abstract void onSelectDaysConfirmMethod();

    protected List<Integer> onGetDayNameResult() {
        List<Integer> workList = new ArrayList<>();
        if (mCheckMon.isChecked()) {
            workList.add(1);
        }
        if (mCheckTue.isChecked()) {
            workList.add(2);
        }
        if (mCheckWed.isChecked()) {
            workList.add(4);
        }
        if (mCheckThu.isChecked()) {
            workList.add(8);
        }
        if (mCheckFri.isChecked()) {
            workList.add(16);
        }
        if (mCheckSat.isChecked()) {
            workList.add(32);
        }
        if (mCheckSun.isChecked()) {
            workList.add(64);
        }
        return workList;
    }

//    protected String onGetDayIdResult() {
//        StringBuilder sbDayId = new StringBuilder();
//        for (int i = 0; i < checked.length; i++) {
//            if (checked[i]) {
//                sbDayId.append(i + ",");
//            }
//        }
//        return (sbDayId.length() == 0) ? sbDayId.toString() : sbDayId.substring(0, sbDayId.length() - 1);
//    }
}
