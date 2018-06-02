package com.zhixing.work.zhixin.view.card;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressBean;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Addressctivity extends BaseTitleActivity {

    @BindView(R.id.provincial_urban_area)
    TextView provincialUrbanArea;
    @BindView(R.id.regional_selection)
    LinearLayout regionalSelection;
    @BindView(R.id.addressEd)
    EditText addressEd;
    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private Gson gson = new Gson();
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private String Province = "";
    private String City = "";
    private String District = "";
    private String Address = "";
    private String detailedAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressctivity);
        ButterKnife.bind(this);
        setTitle("居住地址");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        setRightText1("保存");
        initView();

    }

    private void initView() {
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address = addressEd.getText().toString();
                if (TextUtils.isEmpty(provincialUrbanArea.getText())) {
                    AlertUtils.toast(context, "请选择省市");
                    return;
                }
                if (TextUtils.isEmpty(Address)) {
                    AlertUtils.toast(context, "请填写地址");
                    return;


                }
                EventBus.getDefault().post(new AddressBean(Province, City, District, Address, detailedAddress + Address));
                finish();
            }
        });
    }

    private void ShowPickerView() {// 弹出选择器

               final OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx;
                if (options1Items.get(options1).getChild().size() == 0) {
                    tx = options1Items.get(options1).getName();
                    Province = options1Items.get(options1).getId() + "";
                } else {
                    tx = options1Items.get(options1).getName() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    Province = options1Items.get(options1).getId() + "";
                    City = options1Items.get(options1).getChild().get(options2).getId() + "";
                    District = options1Items.get(options1).getChild().get(options2).getChild().get(options3).getId() + "";
                }

                detailedAddress = tx;
                provincialUrbanArea.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
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

        ArrayList<AddressJson> jsonBean = gson.fromJson(JsonData, new TypeToken<List<AddressJson>>() {
        }.getType());

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            if (jsonBean.get(i).getChild().size() == 0) {
                CityList.add("");
                ArrayList<String> City_AreaList = new ArrayList<>();
                City_AreaList.add("");
                Province_AreaList.add(City_AreaList);
            }
            for (int c = 0; c < jsonBean.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getChild().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChild().get(c).getName() == null
                        || jsonBean.get(i).getChild().get(c).getChild().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getChild().get(c).getChild().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getChild().get(c).getChild().get(d).getName();

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }


            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    @OnClick(R.id.regional_selection)
    public void onViewClicked() {
        HideKeyboard();
        if (isLoaded) {
            ShowPickerView();
        } else {

        }

    }

    private void HideKeyboard() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager manager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }, 10);
    }
}
