package me.yokeyword.fragmentation;

import android.view.MotionEvent;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 *
 */

public interface ISupportActivity {
    SupportActivityDelegate getSupportDelegate();

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    FragmentAnimator onCreateFragmentAnimator();



    void onBackPressedSupport();

    boolean dispatchTouchEvent(MotionEvent ev);
}
