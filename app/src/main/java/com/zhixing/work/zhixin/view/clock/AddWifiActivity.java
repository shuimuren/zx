package com.zhixing.work.zhixin.view.clock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.event.AddWifiEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.NetworkUtil;
import com.zhixing.work.zhixin.util.ResourceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhj on 2018/8/1.
 * Description: 新增wifi
 */

public class AddWifiActivity extends BaseTitleActivity {
    @BindView(R.id.image_right)
    ImageView imageRight;
    @BindView(R.id.tv_wifi_name)
    TextView tvWifiName;
    @BindView(R.id.tv_wifi_id)
    TextView tvWifiId;
    @BindView(R.id.ll_wifi_detail)
    LinearLayout llWifiDetail;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.tv_wifi_connected)
    TextView tvWifiConnected;
    @BindView(R.id.tv_wifi_unconnected)
    TextView tvWifiUnconnected;

    private String wifiBisId;
    private String wifiSSId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wifi);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.setting_add_wifi));
        initView();
    }

    private void initView() {
        updateUi();
    }

    @Override
    public void netWordStatusAndUsable(int status, boolean usable) {
        super.netWordStatusAndUsable(status, usable);
        updateUi();
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(wifiSSId)) {
            EventBus.getDefault().post(new AddWifiEvent(wifiSSId, wifiBisId));
            this.finish();
        } else {
            AlertUtils.show(ResourceUtils.getString(R.string.alert_selector_wifi));
        }
    }

    public void updateUi() {
        if (NetworkUtil.isWifi(AddWifiActivity.this)) {
            tvWifiConnected.setVisibility(View.VISIBLE);
            tvWifiUnconnected.setVisibility(View.GONE);
            llWifiDetail.setVisibility(View.VISIBLE);
            wifiBisId = NetworkUtil.getWifiBssId(this);
            wifiSSId = NetworkUtil.getWifiSSId(this);
            if (!TextUtils.isEmpty(wifiBisId)) {
                tvWifiId.setText(wifiBisId);
            }
            if (!TextUtils.isEmpty(wifiSSId)) {
                tvWifiName.setText(wifiSSId);
            }
            save.setEnabled(true);
        } else {
            tvWifiConnected.setVisibility(View.GONE);
            tvWifiUnconnected.setVisibility(View.VISIBLE);
            llWifiDetail.setVisibility(View.GONE);
            save.setEnabled(false);
        }
    }
}
