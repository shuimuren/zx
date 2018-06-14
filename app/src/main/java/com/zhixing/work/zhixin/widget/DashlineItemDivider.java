package com.zhixing.work.zhixin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhixing.work.zhixin.R;

/**
 * Created by Administrator on 2018/6/9.
 */

public class DashlineItemDivider extends RecyclerView.ItemDecoration {

    private Context context;

    public DashlineItemDivider(Context context) {
        this.context = context;

    }


    public void onDrawOver(Canvas c, RecyclerView parent ,RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            //以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;

            //绘制虚线
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);

            paint.setColor(context.getResources().getColor(R.color.color_7d4a14));
            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right, top);
            PathEffect effects = new DashPathEffect(new float[]{15, 15, 15, 15}, 5);//此处单位是像素不是dp  注意 请自行转化为dp
            paint.setPathEffect(effects);
            c.drawPath(path, paint);


        }
    }



}
