package com.zhixing.work.zhixin.view.companyCard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateCompanyCardActivity extends BaseTitleActivity {


    @BindView(R.id.corporate_name)
    TextView corporateName;
    @BindView(R.id.corporate_name_right)
    ImageView corporateNameRight;
    @BindView(R.id.rl_corporate_name)
    RelativeLayout rlCorporateName;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.region_right)
    ImageView regionRight;
    @BindView(R.id.rl_region)
    RelativeLayout rlRegion;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_right)
    ImageView addressRight;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.name_ed)
    TextView nameEd;
    @BindView(R.id.name_right)
    ImageView nameRight;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.card_sex_man)
    CheckBox cardSexMan;
    @BindView(R.id.card_sex_woman)
    CheckBox cardSexWoman;
    @BindView(R.id.mailbox)
    TextView mailbox;
    @BindView(R.id.mail_right)
    ImageView mailRight;
    @BindView(R.id.rl_mail)
    RelativeLayout rlMail;
    @BindView(R.id.btn_born_card)
    Button btnBornCard;
    private String name = "name";
    private String mail = "mail";
    private String address_tx = "address";
    private String region_tx = "region";
    private String gender = "gendr";
    private String corporate_name = "corporate_name";
    private Map<String, String> dataMap = new HashMap<>();
    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private Gson gson = new Gson();
    private String Province = "";
    private String City = "";
    private String District = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_card);
        ButterKnife.bind(this);
        initJsonData();
        setTitle("创建卡牌");
    }
    @OnClick({R.id.rl_corporate_name, R.id.card_sex_man, R.id.card_sex_woman, R.id.rl_region, R.id.rl_address, R.id.rl_name, R.id.rl_mail, R.id.btn_born_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_sex_man:
                cardSexWoman.setChecked(false);
                dataMap.put(gender, "0");
                isComplete();
                break;
            case R.id.card_sex_woman:
                cardSexMan.setChecked(false);
                dataMap.put(gender, "1");
                isComplete();
                break;
            case R.id.rl_corporate_name:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "公司名称").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.CORPORATE_NAME).
                        putExtra(ModifyContentActivity.HINT, "与营业照一致...").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, corporateName.getText().toString()));
                break;
            case R.id.rl_region:
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
                        dataMap.put(region_tx, tx);
                        isComplete();
                        region.setText(tx);
                    }
                })
                        .setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.rl_address:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "详细地址").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.CORPORATE_ADDRESS).
                        putExtra(ModifyContentActivity.HINT, "请输入详细地址").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, address.getText().toString()));
                break;
            case R.id.rl_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写名字").putExtra("type", "name")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nameEd.getText().toString())
                );
                break;
            case R.id.rl_mail:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写邮箱").
                        putExtra("type", "mail")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, mailbox.getText().toString())
                );
                break;

            case R.id.btn_born_card:
                CreateCard(dataMap.get(corporate_name), Province, City, District, dataMap.get(address_tx), dataMap.get(name), dataMap.get(gender), dataMap.get(mail));
                break;
        }
    }
    private void CreateCard(String FullName, String Province, String City, String District, String Address, String ManagerName, String ManagerSex, String ManagerEmail) {
        OkUtils.getInstances().httpTokenPost(context, RequestConstant.COMPANY, JavaParamsUtils.getInstances().
                Company(FullName, Province, City, District, Address, ManagerName, ManagerSex, ManagerEmail), new TypeToken<EntityObject<String>>() {
        }.getType(), new ResultCallBackListener<String>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<String> response) {
                hideLoadingDialog();
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    if (!TextUtils.isEmpty(response.getContent())){
                        AlertUtils.toast(context, "添加成功");
                        SettingUtils.putToken(response.getContent());
                        EventBus.getDefault().post(new CardCompleteEvent(true));
                        finish();
                    }
                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
    }


    //判断是否填写完毕
    private void isComplete() {
        if (dataMap.values().size() == 6) {
            btnBornCard.setEnabled(true);
            btnBornCard.setBackgroundResource(R.color.color_71aae0);

        } else {
            btnBornCard.setEnabled(false);
            btnBornCard.setBackgroundResource(R.color.hardtoast);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.TYPE_MAILBOX:
                mailbox.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;
            case ModifyDataActivity.TYPE_NAME:
                nameEd.setText(event.getContent());
                dataMap.put(event.getType(), event.getContent());
                isComplete();
                break;

            case ModifyContentActivity.CORPORATE_ADDRESS:
                address.setText(event.getContent());
                dataMap.put(address_tx, event.getContent());
                isComplete();
                break;
            case ModifyContentActivity.CORPORATE_NAME:
                corporateName.setText(event.getContent());
                dataMap.put(corporate_name, event.getContent());
                isComplete();
                break;

        }


    }

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
    }
}
