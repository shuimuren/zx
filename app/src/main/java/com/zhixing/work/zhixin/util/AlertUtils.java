package com.zhixing.work.zhixin.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.zhixing.work.zhixin.app.ZxApplication;

public class AlertUtils {

    public static final String MSG = "loading....";
    private static final String TAG = "AlertUtils";
    static Handler handler = new Handler();
    private static Toast mToast;
    private static Context mContext;
    private static Handler mHandler;
    public static String msg = "loading...";

    public static void toast(final Context ctx, final String txt) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZxApplication.getInstance(), txt,
                        Toast.LENGTH_SHORT).show();
                // Toast.makeText(ctx, txt, Toast.LENGTH_LONG).show();

                // Toast toast = new Toast(ctx);
                // View view = View.inflate(ctx, R.layout.toast, null);
                // TextView tvToast = (TextView)
                // view.findViewById(R.id.tv_toast);
                // tvToast.setText(txt);
                // toast.setView(view);
                // toast.setGravity(Gravity.CENTER, 0, 0);
                // toast.setDuration(1*1000);
                // toast.show();
            }
        });
    }

    public static void init(Context context, int iconId) {
        mContext = context;
        mHandler = new Handler(mContext.getMainLooper());
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        if (iconId > 0) {
            FrameLayout xToastView = new FrameLayout(context);

            View toastView = mToast.getView();
            FrameLayout.LayoutParams toastViewLayoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            toastViewLayoutParams.setMargins(16, 16, 0, 0);
            xToastView.addView(toastView, toastViewLayoutParams);
            //add icon
            FrameLayout.LayoutParams imageViewLayoutParams =
                    new FrameLayout.LayoutParams(48, 48);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(iconId);
            xToastView.addView(imageView, imageViewLayoutParams);
            mToast.setView(xToastView);
        }
    }

    public static void show(int messageResourceId) {
        showInUI(Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0, mContext.getString(messageResourceId));
    }

    public static void show(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        showInUI(Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0, message);
    }

    public static void showLong(String message) {
        showInUI(Toast.LENGTH_LONG, Gravity.CENTER, 0, 0, message);
    }

    public static void showInNotUILong(String message) {
        showInNotUI(Toast.LENGTH_LONG, Gravity.CENTER, 0, 0, message);
    }

    public static void showInNotUI(String message) {
        showInNotUI(Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0, message);
    }

    public static void showInUI(int duration, int gravity, int xOffset, int yOffset, String message) {
        mToast.setDuration(duration);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.setText(message);
        mToast.show();
    }

    public static void showInNotUI(final int duration, final int gravity, final int xOffset, final int yOffset, final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showInUI(duration, gravity, xOffset, yOffset, message);
            }
        });
    }


}
