package com.zhixing.work.zhixin.widget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.Map;

/**
 * Created by lhj on 2018/8/27.
 * Description:
 */

public class BasePopupWindow implements PopupWindow.OnDismissListener {
    protected View mParentView;
    protected PopupWindow mPopupWindow;
    protected Activity mActivity;
    protected Map<String, String> mParams;

    protected int[] mWh;

    protected BasePopupWindow(Activity activity, @NonNull View parentView, @Nullable Map<String, String> params) {
        mActivity = activity;
        mParentView = parentView;
        mParams = params;
        mWh = new int[]{mParentView.getWidth(), mParentView.getHeight()};

    }

    protected void initPopupWindow(View contentView, int width, int height) {
        mPopupWindow = new PopupWindow(contentView, width, height);
        mPopupWindow.setBackgroundDrawable(ResourceUtils.getDrawable(R.drawable.shape_popup_window));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_bottom_to_top_style);
        mPopupWindow.setOnDismissListener(this);

    }

    protected void setAnimationStyle(int animId) {
        mPopupWindow.setAnimationStyle(animId);
    }

    @Override
    public void onDismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 默认显示在屏幕中央
     */
    public void show() {
        show(Gravity.CENTER, 0, 0);
    }

    public void showAtBottom() {
        show(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示在点击的View下方
     */
    public void showAsDropDown(int offsetX, int offsetY) {
        showPopupWindowAsDropDown(mActivity, mPopupWindow, mParentView, offsetX, offsetY);
    }

    /**
     * 显示在点击的View上方
     */
    public void showAsAbove(int offsetX, int offsetY) {
        int[] location = new int[2];
        mParentView.getLocationOnScreen(location);
        showPopupWindow(mActivity, mPopupWindow, mParentView, Gravity.START | Gravity.BOTTOM,
                location[0] - mPopupWindow.getWidth() / 2 + offsetX, mWh[1] - location[1] + offsetY, true);
    }

    public void showAsAboveCenter() {
        showAsAbove(mParentView.getWidth() / 2, 0);
    }

    public void showAsAboveLeft() {
        showAsAbove(mPopupWindow.getWidth() / 4, 0);
    }

    public void showAsDownCenter(boolean toMask) {
        showAsDown(mParentView.getWidth() / 2 - mPopupWindow.getWidth() / 2, -18);
    }

    public void showAsDownCenter() {
        showAsDown(mParentView.getWidth() / 2 - mPopupWindow.getWidth() / 2, 0);
    }

    /**
     * 显示在点击的View下方
     */
    public void showAsDown(int offsetX, int offsetY) {
        showPopupWindowAsDropDown(mActivity, mPopupWindow, mParentView, offsetX, offsetY);
    }

    private void showPopupWindowAsDropDown(Activity activity, PopupWindow popupWindow, View parent, int x,
                                           int y) {
        popupWindow.showAsDropDown(parent, x, y);

    }

    public void show(int gravity, int x, int y) {
        showPopupWindow(mActivity, mPopupWindow, mParentView, gravity, x, y, true);
    }

    private void showPopupWindow(Activity activity, PopupWindow popupWindow, View parent, int gravity, int x,
                                 int y, boolean toMask) {
        popupWindow.showAtLocation(parent, gravity, x, y);
    }
}
