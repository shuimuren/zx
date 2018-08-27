package com.zhixing.work.zhixin.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.util.ResourceUtils;

/**
 * Created by lhj on 2018/8/23.
 * Description: 定义奖惩View
 */

public class AwardOrPunishmentView extends LinearLayout {

    private View viewOne;
    private View ViewTwo;
    private View ViewThree;

    public AwardOrPunishmentView(Context context) {
        this(context, null);
    }

    public AwardOrPunishmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AwardOrPunishmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_award_or_punishment, null);
        addView(view);
        viewOne = findViewById(R.id.view1);
        ViewTwo = findViewById(R.id.view2);
        ViewThree = findViewById(R.id.view3);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AwardOrPunishmentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setViewStatus(int value) {
        switch (value) {
            case -3:
           viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_left));
           ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_middle));
           ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_right));
                break;
            case -2:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_right));
                break;
            case -1:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_lousy_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_right));
                break;
            case 0:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_right));
                break;
            case 1:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_right));
                break;
            case 2:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_normal_right));
                break;
            case 3:
                viewOne.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_left));
                ViewTwo.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_middle));
                ViewThree.setBackground(ResourceUtils.getDrawable(R.drawable.award_view_good_right));
                break;


        }
    }

}
