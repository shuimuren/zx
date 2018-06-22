package com.zhixing.work.zhixin.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportActivity;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.fragment.MainFragment;
import com.zhixing.work.zhixin.presenter.MainContract;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends SupportActivity {
    MainContract.MainPresenter mPresenter;
    private long firstTime = 0;
    private ArrayList<AddressJson> provincialList = new ArrayList<AddressJson>();
    private ArrayList<AddressJson.ChildBeanX> cityList = new ArrayList<AddressJson.ChildBeanX>();
    private ArrayList<AddressJson.ChildBeanX.ChildBean> areaList = new ArrayList<AddressJson.ChildBeanX.ChildBean>();
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNoTitle();
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        instance = this;
        if (TextUtils.isEmpty(SettingUtils.getProvincialList())) {
            mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据

                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:

                    break;

            }
        }
    };

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = Utils.getJson(this, "Area.json");//获取assets目录下的json文件数据

        SettingUtils.putProvincialList(JsonData);
        ArrayList<AddressJson> jsonBean = gson.fromJson(JsonData, new TypeToken<List<AddressJson>>() {
        }.getType());

        String cityData = Utils.getJson(context, "hotcity.json");//热门城市
        String Industrytype = Utils.getJson(context, "Industrytype.json");//行业
        String jobList = Utils.getJson(context, "Jobtype.json");//行业


        SettingUtils.putJobList(jobList);
        SettingUtils.putHotCityList(cityData);
        SettingUtils.putIndustry(Industrytype);
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        provincialList = jsonBean;
        Iterator iter = provincialList.iterator();
        //2、通过循环迭代
        //hasNext():判断是否存在下一个元素
        for (int i = 0; i < provincialList.size(); i++) {
            List<AddressJson.ChildBeanX> dataList = provincialList.get(i).getChild();
            if (dataList.size() == 0) {

            } else {
                cityList.addAll(dataList);
            }
        }
        for (int i = 0; i < cityList.size(); i++) {
            List<AddressJson.ChildBeanX.ChildBean> dataList = cityList.get(i).getChild();
            if (dataList.size() == 0) {

            } else {
                areaList.addAll(dataList);
            }
        }
        SettingUtils.putCityList(gson.toJson(cityList));
        SettingUtils.putAreaList(gson.toJson(areaList));


    }
}
