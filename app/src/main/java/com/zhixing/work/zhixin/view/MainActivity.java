package com.zhixing.work.zhixin.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.presenter.MainContract;


public class MainActivity extends SupportActivity {
    MainContract.MainPresenter mPresenter;
    private long firstTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNoTitle();
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出职信", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    moveTaskToBack(false);
                    return super.onKeyDown(keyCode, event);
                }
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
