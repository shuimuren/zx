package com.zhixing.work.zhixin.view.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/1.
 * Description: wifi设置
 */

public class SettingWifiActivity extends BaseTitleActivity {


    @BindView(R.id.wifi_recycler)
    RecyclerView wifiRecycler;
    @BindView(R.id.tv_add_wifi)
    TextView tvAddWifi;

    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_wifi);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.setting_wifi));
        setRightText1(ResourceUtils.getString(R.string.edit));
        setRightClickListener(rightClickListener);

    }

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isEdit){
                isEdit = !isEdit;
            }
        }
    };

    @OnClick(R.id.tv_add_wifi)
    public void onViewClicked() {
        startActivity(new Intent(this,AddWifiActivity.class));
    }
}
