package com.zhixing.work.zhixin.base;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.LoadingDialog;
import com.zhixing.work.zhixin.dialog.NoInternetDialog;
import com.zhixing.work.zhixin.receiver.NetWorkStatusListener;
import com.zhixing.work.zhixin.receiver.NetworkConnectChangedReceiver;
import com.zhixing.work.zhixin.util.DensityUtils;
import com.zhixing.work.zhixin.util.NetUtils;
import com.zhixing.work.zhixin.util.ScreenUtils;
import com.zhixing.work.zhixin.util.SystemBarTintManager;
import com.zhixing.work.zhixin.widget.NoInternetPopup;


public abstract class BaseTitleActivity extends BaseControlActivity implements NetWorkStatusListener {
    protected RelativeLayout ll_root;
    protected RelativeLayout rlTitleBar;
    protected FrameLayout flContent;
    protected RelativeLayout ll_title_bar_container; // 标题部分
    // 左边标题部分
    protected LinearLayout llLeft;
    protected ImageView ivLeft;
    protected TextView tvLeftBack; // 返回文字 默认不可见
    // 中间标题部分
    protected LinearLayout llCenter;
    protected TextView tvTitle;

    // 右边标题部分
    protected LinearLayout llRight;
    protected ImageView ivRight; //右边图片
    protected TextView tvRight1; // 右边文字
    protected TextView tvRight2; // 右边文字

    // 标题下方分割线
    protected View titleBottomLine;
    private SystemBarTintManager mTintManager;
    protected InputMethodManager inputMethodManager;
    protected LoadingDialog loadingDialog;
    protected NoInternetDialog noInternetDialog;
    // 状态栏是否浮动在上面 默认为不浮
    private boolean isStatudBarFloat = false;
    //网络状态监听
    NetworkConnectChangedReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 用baseFragment的布局
        super.setContentView(R.layout.activity_base_title); // 用baseFragment的布局
        bindViews();
        setAboutTitle();
        setSystemBar();
        ZxApplication.getInstance().addActivity(this);
      //  registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetUtils.isConnected(context)) {
            if (noInternetDialog == null) {
                noInternetDialog = new NoInternetDialog(BaseTitleActivity.this);
            }
            noInternetDialog.setCancelable(true);
            noInternetDialog.setLoadingMessage("无法连接到网络请稍后再试");
            noInternetDialog.show();
            new Thread(new MyThread()).start();
        }
    }


    /**
     * 设置状态栏 相关
     */
    protected void setSystemBar() {
        ScreenUtils.setTranslucentStatus(true, this); // 设置状态栏为透明状态
        if (isStatudBarFloat()) {
            ScreenUtils.setBackOnStatusBar(this);
        } else {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            setStatusBarColor(getStatusBarColor()); // 默认为标题颜色
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

//            if (hasFocus) {
//                else {
//                    dismissNoInternetPopup();
//                }
//            }

    }

    private int getStatusBarColor() {
        return R.color.btn_write_per_data;
    }

    /**
     * 设置状态栏颜色 默认为titlebg
     */
    public void setStatusBarColor(int color) {
        if (mTintManager != null) {
            mTintManager.setTintResource(color);
        }
    }

    protected boolean isStatudBarFloat() {
        return isStatudBarFloat;
    }

    public void setIsStatudBarFloat(boolean isStatudBarFloat) {
        this.isStatudBarFloat = isStatudBarFloat;
    }


    protected NoInternetPopup noInternetPopup;

    /**
     * 显示无网络状态
     */
    protected void showNoInternetPopup() {
        if (noInternetPopup == null) {
            noInternetPopup = new NoInternetPopup(this);
            noInternetPopup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetUtils.openSetting(BaseTitleActivity.this);
                }
            });
        } else {
            noInternetPopup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetUtils.openSetting(BaseTitleActivity.this);
                }
            });
        }

        if (!noInternetPopup.isShowing()) {
            int[] location = new int[2];
            rlTitleBar.getLocationOnScreen(location);
            noInternetPopup.showAtLocation(ll_root, Gravity.TOP, 0, DensityUtils.dp2px(context, 50) + ScreenUtils.getStatusHeight(context));
        }
    }

    /**
     * 隐藏无网络状态
     */
    public void dismissNoInternetPopup() {
        if (noInternetPopup != null && noInternetPopup.isShowing()) {
            noInternetPopup.dismiss();
        }
    }

    private void bindViews() {
        ll_root = (RelativeLayout) findViewById(R.id.ll_root);
        ll_title_bar_container = (RelativeLayout) findViewById(R.id.ll_title_bar_container);
        rlTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bar_container);
        flContent = (FrameLayout) findViewById(R.id.fr_content);
        llLeft = (LinearLayout) findViewById(R.id.ll_left);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvLeftBack = (TextView) findViewById(R.id.tvLeftBack);
        llCenter = (LinearLayout) findViewById(R.id.ll_center);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llRight = (LinearLayout) findViewById(R.id.ll_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        tvRight1 = (TextView) findViewById(R.id.tvRight1);
        tvRight2 = (TextView) findViewById(R.id.tvRight2);
        titleBottomLine = findViewById(R.id.titleBottomLine);
    }

    public void setAboutTitle() {
        // 默认情况下设置左边的图标为返回 点击finish掉这个界面
        setLeftImage(R.drawable.left);
        setLeftOnClickListener(new BackListener());
    }

    public void setNoTitle() {
        rlTitleBar.setVisibility(View.GONE);
    }

    public void setShowTitle() {
        rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 标题底线
     *
     * @param isVisible
     */
    public void setTitlteButtomLineVisisble(boolean isVisible) {
        if (isVisible) {
            titleBottomLine.setVisibility(View.VISIBLE);
        } else {
            titleBottomLine.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setTitle(int resId) {
        if (tvTitle != null) {
            tvTitle.setText(resId);
        }
    }

    /**
     * 设置标题颜色
     */
    @Override
    public void setTitleColor(int id) {
        if (tvTitle != null) {
            tvTitle.setTextColor(id);
        }
    }

    /**
     * 设置标题背景颜色
     */
    public void setTitleBgColor(int id) {
        if (rlTitleBar != null) {
            rlTitleBar.setBackgroundColor(id);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }


    /**
     * 设置左边是否有文字 默认为返回
     *
     * @param text
     */
    protected void setLeftText(String text) {
        if (tvLeftBack != null) {
            tvLeftBack.setVisibility(View.VISIBLE);
            tvLeftBack.setText(text);
        }
        if (TextUtils.isEmpty(text)) {
            tvLeftBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边有返回文字
     */
    protected void setBackText() {
        if (tvLeftBack != null) {
            tvLeftBack.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置左边文字颜色
     */
    protected void setLeftTextColor(int color) {

        if (tvLeftBack != null) {
            if (tvLeftBack.getVisibility() == View.GONE) {
                tvLeftBack.setVisibility(View.VISIBLE);
            }

            tvLeftBack.setTextColor(color);
        }
    }

    /**
     * 设置标题左边的图片
     *
     * @param imageId
     */
    protected void setLeftImage(int imageId) {
        if (ivLeft != null) {
            if (ivLeft.getVisibility() == View.GONE) {
                ivLeft.setVisibility(View.VISIBLE);
            }
            ivLeft.setImageResource(imageId);
        }
    }

    /**
     * 设置标题左边是否可见
     */
    protected void setLeftImageIsVisible(boolean isVisible) {
        if (isVisible) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左边view的监听
     *
     * @param listener
     */
    protected void setLeftOnClickListener(OnClickListener listener) {
        llLeft.setOnClickListener(listener);
    }

    /**
     * 设置左边view的监听
     */
    protected void setLeftNotVisible() {

        if (llLeft != null) {
            llLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题右边的图片
     *
     * @param imageId
     */
    protected void setRightImage(int imageId) {
        if (ivRight != null) {
            if (ivRight.getVisibility() == View.GONE) {
                ivRight.setVisibility(View.VISIBLE);
            }

            ivRight.setImageResource(imageId);
        }
    }

    /**
     * 设置标题右边的图片监听
     */
    protected void setRightImageListener(OnClickListener listener) {

        if (ivRight != null) {
            if (ivRight.getVisibility() == View.GONE) {
                ivRight.setVisibility(View.VISIBLE);
            }
            ivRight.setOnClickListener(listener);
        }
    }

    /**
     * 设置标题右边的文字
     */
    protected void setRightText1(String text) {

        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
            }
            tvRight1.setText(text);
        }
    }

    /**
     * 设置标题右边的文字和字体大小
     */
    protected void setRightText1(String text, float size) {

        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
                tvRight1.setTextSize(size);
            }
            tvRight1.setText(text);
        }
    }

    /**
     * 设置标题右边的文字
     */
    protected void setRightText2(String text) {

        if (tvRight2 != null) {
            if (tvRight2.getVisibility() == View.GONE) {
                tvRight2.setVisibility(View.VISIBLE);
            }
            tvRight2.setText(text);
        }
    }

    /**
     * 设置标题右边的文字的监听
     */
    protected void setRightText1OnClickListener(OnClickListener listener) {
        tvRight1.setOnClickListener(listener);
    }

    /**
     * 设置标题右边的文字的监听
     */
    protected void setRightText2OnClickListener(OnClickListener listener) {
        tvRight2.setOnClickListener(listener);
    }

    /**
     * 设置标题右边的文字的点击事件
     */
    protected void setRightText1ClickListener(OnClickListener clickListener) {

        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
            }
            tvRight1.setOnClickListener(clickListener);
        }
    }

    /**
     * 整个右边的部分的监听
     *
     * @param clickListener
     */
    protected void setRightClickListener(OnClickListener clickListener) {
        if (llRight != null) {
            if (llRight.getVisibility() == View.GONE) {
                llRight.setVisibility(View.VISIBLE);
            }
            llRight.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置标题右边的文字的点击事件
     */
    protected void setRightText2ClickListener(OnClickListener clickListener) {

        if (tvRight2 != null) {
            if (tvRight2.getVisibility() == View.GONE) {
                tvRight2.setVisibility(View.VISIBLE);
            }
            tvRight2.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置标题右边的颜色
     */
    protected void setRightText1Color(int color) {

        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
            }
            tvRight1.setTextColor(color);
        }
    }

    /**
     * 设置标题右边的颜色选择器
     */
    protected void setRightText1Color(ColorStateList csl) {

        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
            }
            tvRight1.setTextColor(csl);
        }
    }

    /**
     * 设置标题右边的颜色
     */
    protected void setRightText2Color(int color) {

        if (tvRight2 != null) {
            if (tvRight2.getVisibility() == View.GONE) {
                tvRight2.setVisibility(View.VISIBLE);
            }
            tvRight2.setTextColor(color);
        }
    }

    /**
     * 设置标题右边的文字textView的背景 有一个默认背景 如果执行此方法 本来是无背景的
     */
    protected void setRightText1Background(int resId) {
        if (tvRight1 != null) {
            if (tvRight1.getVisibility() == View.GONE) {
                tvRight1.setVisibility(View.VISIBLE);
            }
            try {
                tvRight1.setBackgroundResource(resId);
                tvRight1.setPadding(25, 10, 25, 10); // 同时设置padding
            } catch (Exception e) {
//                tvRight1.setBackgroundResource(R.drawable.selecter_rectangle_gray_border);
                tvRight1.setPadding(25, 10, 25, 10);
            }
        }
    }

    /**
     * 重先为中间部分设置一个view
     */
    protected void setCenterView(int resId) {
        View center = getLayoutInflater().inflate(resId, llCenter, false);
        llCenter.removeAllViews();
        llCenter.addView(center, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    protected void setCenterView(View center) {
        llCenter.removeAllViews();
        llCenter.addView(center, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(int layoutResID) {
        View content = getLayoutInflater().inflate(layoutResID, flContent, false);
        flContent.removeAllViews();
        flContent.addView(content);
    }

    @Override
    public void setContentView(View view) {
        flContent.removeAllViews();
        flContent.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        flContent.removeAllViews();
        flContent.addView(view, params);
    }

    class BackListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                IBinder ibinder = getCurrentFocus().getWindowToken();
                if ((inputMethodManager != null) && (ibinder != null)) {
                    inputMethodManager.hideSoftInputFromWindow(ibinder, 0);
                }
            } catch (Exception e) {

            }
            finish();
        }
    }

    /**
     * 自定义显示内容和返回键状态
     *
     * @param content
     * @param cancelable
     */
    public void showLoading(String content, boolean cancelable) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMessage(content);
            loadingDialog.setCancelable(cancelable);
            return;
        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingMessage(content);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.show();
    }

    /**
     * 默认返回键取消
     *
     * @param content
     */
    public void showLoadingDialog(String content) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMessage(content);
            loadingDialog.setCancelable(true);
            return;
        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingMessage(content);
        loadingDialog.setCancelable(true);
        loadingDialog.show();
    }


    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 默认 message加载中 返回键可取消
     */
    public void showLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMessage("加载中");
            loadingDialog.setCancelable(true);
            return;
        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingMessage("加载中");
        loadingDialog.setCancelable(true);
        loadingDialog.show();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            noInternetDialog.dismiss();
        }
    };

    public class MyThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(1200);// 线程暂停10秒，单位毫秒
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// 发送消息
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void netWordStatusAndUsable(int status, boolean usable) {
        if (usable) {
            Logger.i(">>>", "当前网络可用");
        } else {
            Logger.i(">>>", "当前网络不可用");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
        }
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        networkReceiver = NetworkConnectChangedReceiver.getInstance();
        registerReceiver(networkReceiver, filter);
        networkReceiver.setNetWorkStatusListener(this);
    }


}
