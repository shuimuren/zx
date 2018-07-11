package com.zhixing.work.zhixin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by lhj on 2018/7/10.
 * Description: 资质,评分页面
 */

public class EvaluatingView extends View {
    private static final String TAG = "skillView";
    private int n; //数据个数
    private float R; //最外圈的半径，顶点到中心点的距离
    private int intervalCount; //间隔数量，把半径分为几段
    private float angle; //两条顶点到中线点的角度

    private Paint linePaint; //划线的笔；
    private Paint numberPaint; //画数字的笔；
    private Paint textPaint; //画文本的笔

    private int viewHeight; // 控件宽度；
    private int viewWidth; //控件高度;
    private int spaceWidth = 80;
    private  int screenWidth;

    private ArrayList<ArrayList<PointF>> pointsArrayList;  //存储多边形顶点数组的数组
    private ArrayList<PointF> abilityPoints;    //存储能力点的数组
    private ArrayList<PointF> textPoints;

    public EvaluatingView(Context context) {
        //这地方改为this,使得不管怎么初始化都会进入第三个构造函数中
        this(context, null);
    }

    public EvaluatingView(Context context, @Nullable AttributeSet attrs) {
        //这地方改为this,使得不管怎么初始化都会进入第三个构造函数中
        this(context, attrs, 0);
    }

    public EvaluatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSize(context);
    //    initPoints();
        initPaint(context);
    }

    private void initSize(Context context) {
        n = 6;
        R = dp2pxF(context, 100);
        intervalCount = 4;
        angle = (float) ((2 * Math.PI) / n);     //一周是2π,这里用π，因为进制的问题，不能用360度,画出来会有问题
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        viewWidth = screenWidth/2;
        viewHeight = screenWidth/2;
        abilityPoints = new ArrayList<>();
        textPoints = new ArrayList<>();
    }

    private void initPoints() {

    }

    private void initPaint(Context context) {
        //画线的笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置线宽度
        linePaint.setStrokeWidth(dp2px(context, 1f));

        //画文字的笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.parseColor("#FFbbaa"));//设置文字居中
        textPaint.setTextSize(sp2pxF(context, 14f));
        //画文字的笔
        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setTextAlign(Paint.Align.CENTER);  //设置文字居中
        numberPaint.setColor(Color.WHITE);
        numberPaint.setTextSize(sp2pxF(context, 14f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //    canvas.translate(viewWidth/2,viewHeight/2);
        drawPolygon(canvas);
        drawOutLine(canvas);
        drawAbilityLine(canvas);
        drawNumber(canvas);
        drawAbilityText(canvas);
    }


    private void drawPolygon(Canvas canvas) {
        canvas.save(); //保存画布当前状态
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int widR = 0;
        for (int i = 0; i < 3; i++) {  //循环、一层一层的绘制
            //每一层的颜色都都不同
            switch (i) {
                case 0:
                    linePaint.setColor(Color.parseColor("#88D4F0F3"));
                    linePaint.setStrokeWidth(dp2px(getContext(), 2f));
                    widR = viewWidth / 4;
                    break;
                case 1:
                    linePaint.setColor(Color.parseColor("#6699DCE2"));
                    widR = viewWidth / 5;
                    break;
                case 2:
                    linePaint.setColor(Color.parseColor("#5556C1C7"));
                    widR = viewWidth / 8;
                    break;
                case 3:
                    linePaint.setColor(Color.parseColor("#278891"));
                    widR = dp2px(getContext(), 50);
                    break;
            }

            canvas.drawCircle(screenWidth / 4, screenWidth / 4, widR, linePaint);
        }
        canvas.restore();

    }

    private void drawOutLine(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < abilityPoints.size(); i++) {
            canvas.drawCircle(abilityPoints.get(i).x, abilityPoints.get(i).y, 30, linePaint);
        }
        canvas.restore();
    }

    private void drawAbilityLine(Canvas canvas) {
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);  //设置空心的
        canvas.save();
        float r = viewWidth / 4;
        float x1 = viewWidth / 2;
        float y1 = viewWidth / 4;
        abilityPoints.add(new PointF(x1, y1));
        textPoints.add(new PointF(x1, y1 - spaceWidth));
        float x2 = viewWidth / 2 + (float) (r * 0.87);
        float y2 = viewWidth / 2 - (float) (r * 0.5);
        abilityPoints.add(new PointF(x2, y2));
        textPoints.add(new PointF(x2 + spaceWidth, y2));
        float x3 = viewWidth / 2 + (float) (r * 0.87);
        float y3 = viewWidth / 2 + (float) (r * 0.5);
        abilityPoints.add(new PointF(x3, y3));
        textPoints.add(new PointF(x3 + spaceWidth, y3));
        float x4 = viewWidth / 2;
        float y4 = viewWidth * 3 / 4;
        abilityPoints.add(new PointF(x4, y4));
        textPoints.add(new PointF(x4, y4 + spaceWidth));
        float x5 = viewWidth / 2 - (float) (r * 0.87);
        float y5 = viewWidth / 2 + (float) (r * 0.5);
        abilityPoints.add(new PointF(x5, y5));
        textPoints.add(new PointF(x5 - spaceWidth, y5));

        float x6 = viewWidth / 2 - (float) (r * 0.87);
        float y6 = viewWidth / 2 - (float) (r * 0.5);
        abilityPoints.add(new PointF(x6, y6));
        textPoints.add(new PointF(x6 - spaceWidth, y6));
        canvas.drawLine(x1, y1, x4, y4, linePaint);
        canvas.drawLine(x2, y2, x5, y5, linePaint);
        canvas.drawLine(x3, y3, x6, y6, linePaint);


        canvas.restore();
    }

    private void drawNumber(Canvas canvas) {
        canvas.save();
        Paint.FontMetrics metrics = numberPaint.getFontMetrics();
        String[] abilitys = {"1", "2", "3", "4", "5", "6"};
        for (int i = 0; i < n; i++) {
            float x = abilityPoints.get(i).x;
            //ascent:上坡度，是文字的基线到文字的最高处的距离
            //descent:下坡度,，文字的基线到文字的最低处的距离
            float y = abilityPoints.get(i).y - (metrics.ascent + metrics.descent) / 2;
            canvas.drawText(abilitys[i], x, y, numberPaint);
        }
        canvas.restore();
    }

    private void drawAbilityText(Canvas canvas) {
        canvas.save();
        Paint.FontMetrics metrics = numberPaint.getFontMetrics();
        String[] texts = {"技能", "诚信", "人脉", "合规", "履约", "心态"};
        for (int i = 0; i < n; i++) {
            float textX = textPoints.get(i).x;
            float textY = textPoints.get(i).y - (metrics.ascent + metrics.descent) / 2;
            canvas.drawText(texts[i], textX, textY, textPaint);
        }
        canvas.restore();
    }

    /**
     * 下面都是工具类，dp单位转px单位
     */
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

}

