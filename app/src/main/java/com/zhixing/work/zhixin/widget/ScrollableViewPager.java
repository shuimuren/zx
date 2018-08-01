package com.zhixing.work.zhixin.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by lhj on 2018/7/31.
 * Description:
 */

public class ScrollableViewPager extends ViewPager {

    public ScrollableViewPager(@NonNull Context context) {
        this(context,null);
    }

    public ScrollableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v instanceof HorizontalScrollView){
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
