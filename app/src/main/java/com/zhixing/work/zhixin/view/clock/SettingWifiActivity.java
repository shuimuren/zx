package com.zhixing.work.zhixin.view.clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.WifiListAdapter;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.WifiBean;
import com.zhixing.work.zhixin.event.AddWifiEvent;
import com.zhixing.work.zhixin.event.WifiListEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    public static final String INTENT_KEY_WIFI_LIST = "wifiList";

    public static void startSettingWifiActivity(Activity activity,List<WifiBean> list){
        Intent intent = new Intent(activity,SettingWifiActivity.class);
        intent.putExtra(INTENT_KEY_WIFI_LIST, (Serializable) list);
        activity.startActivity(intent);

    }

    private boolean isEdit;
    private WifiListAdapter wifiAdapter;
    private List<WifiBean> mWifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_wifi);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.setting_wifi));
        setRightText1(ResourceUtils.getString(R.string.edit));
        setRightClickListener(rightClickListener);
        intiView();
    }

    private void intiView() {
        mWifiList = new ArrayList<>();
        List<WifiBean> wifiBeans = (List<WifiBean>) getIntent().getSerializableExtra(INTENT_KEY_WIFI_LIST);
        if(wifiBeans.size() > 0){
            mWifiList.addAll(wifiBeans);
        }
        wifiAdapter = new WifiListAdapter(mWifiList);
        wifiRecycler.setLayoutManager(new LinearLayoutManager(this));
        wifiRecycler.setAdapter(wifiAdapter);
        wifiRecycler.setItemAnimator(new DefaultItemAnimator());
        wifiRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        wifiRecycler.setHasFixedSize(true);
        wifiAdapter.setItemSelectedInterface(new WifiListAdapter.ItemSelectedInterface() {
            @Override
            public void itemSelected(int position, WifiBean bean) {
                bean.setSelected(!bean.isSelected());
                wifiAdapter.notifyItemChanged(position);
            }
        });

    }

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isEdit = !isEdit;
            btnSave.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            btnDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);
            wifiAdapter.setIsEdit(isEdit);
            tvAddWifi.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            setRightText1(isEdit ? ResourceUtils.getString(R.string.edit_cancel) : ResourceUtils.getString(R.string.edit));
        }
    };

    @OnClick(R.id.tv_add_wifi)
    public void onViewClicked() {
        startActivity(new Intent(this, AddWifiActivity.class));
    }

    @Subscribe
    public void addWifiEvent(AddWifiEvent event) {
        int wifiListSize = mWifiList.size();
        boolean include = false;
        for (int i = 0; i < wifiListSize; i++) {
            if (mWifiList.get(i).getName().equals(event.getWifiName())) {
                include = true;
                break;
            }
        }
        if (!include) {
            mWifiList.add(new WifiBean(event.getWifiName(), event.getWifiDis(), false));
            wifiAdapter.setData(mWifiList);
        }
    }

    @OnClick({R.id.btn_save, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if(mWifiList.size() == 0){
                    AlertUtils.show(ResourceUtils.getString(R.string.alert_selector_usable_wifi));
                }else{
                    WifiListEvent event = new WifiListEvent();
                    event.setWifis(mWifiList);
                    EventBus.getDefault().post(event);
                    this.finish();
                }


                break;
            case R.id.btn_delete:
                Iterator<WifiBean> it = mWifiList.iterator();
                while (it.hasNext()) {
                    WifiBean bean = it.next();
                    if (bean.isSelected()) {
                        it.remove();
                    }
                }
                wifiAdapter.setData(mWifiList);
                break;
        }
    }
}
