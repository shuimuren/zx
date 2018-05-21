package com.zhixing.work.zhixin.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


import com.zhixing.work.zhixin.app.ZxApplication;

public class AlertUtils {

    public static final String MSG = "loading....";
    private static final String TAG = "AlertUtils";
    static Handler handler = new Handler();
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

    /**
     *
     * @param ctx
     * @param txt
     *            吐司内容
     * @param iconResId
     *            吐司的图标的资源id
     */
//	public static void toast2(final Context ctx, final String txt,
//			final int iconResId) {
//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
//				Toast toast = new Toast(ctx);
//				View view = View.inflate(ctx, R.layout.toast2, null);
//				TextView tvToast = (TextView) view.findViewById(R.id.tv_toast);
//				ImageView icon = (ImageView) view.findViewById(R.id.icon);
//				icon.setImageResource(iconResId);
//				tvToast.setText(txt);
//				toast.setView(view);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.setDuration(Toast.LENGTH_LONG);
//				toast.show();
//			}
//		};
//		handler.post(runnable);
//	}


}
