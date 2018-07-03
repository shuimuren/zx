package com.zhixing.work.zhixin.view.companyCard.back;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.Company;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.BasicRefreshEvent;
import com.zhixing.work.zhixin.event.CompanyIndustryEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.companyCard.CompanyIndustryActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CompanyPerfectCardActivity extends BaseTitleActivity {
    @BindView(R.id.company_logo)
    ImageView companyLogo;
    @BindView(R.id.iv_company_logo)
    ImageView ivCompanyLogo;
    @BindView(R.id.rl_company_logo)
    RelativeLayout rlCompanyLogo;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.iv_company_name)
    ImageView ivCompanyName;
    @BindView(R.id.rl_company_name)
    RelativeLayout rlCompanyName;
    @BindView(R.id.company_abbreviation)
    TextView companyAbbreviation;
    @BindView(R.id.iv_company_abbreviation)
    ImageView ivCompanyAbbreviation;
    @BindView(R.id.rl_company_abbreviation)
    RelativeLayout rlCompanyAbbreviation;
    @BindView(R.id.company_region)
    TextView companyRegion;
    @BindView(R.id.iv_company_region)
    ImageView ivCompanyRegion;
    @BindView(R.id.rl_company_region)
    RelativeLayout rlCompanyRegion;
    @BindView(R.id.company_address)
    TextView companyAddress;
    @BindView(R.id.iv_company_address)
    ImageView ivCompanyAddress;
    @BindView(R.id.rl_company_address)
    RelativeLayout rlCompanyAddress;
    @BindView(R.id.company_nature)
    TextView companyNature;
    @BindView(R.id.iv_company_nature)
    ImageView ivCompanyNature;
    @BindView(R.id.rl_company_nature)
    RelativeLayout rlCompanyNature;
    @BindView(R.id.company_industry)
    TextView companyIndustry;
    @BindView(R.id.iv_company_industry)
    ImageView ivCompanyIndustry;
    @BindView(R.id.rl_company_industry)
    RelativeLayout rlCompanyIndustry;
    @BindView(R.id.company_financing)
    TextView companyFinancing;
    @BindView(R.id.iv_company_financing)
    ImageView ivCompanyFinancing;
    @BindView(R.id.rl_company_financing)
    RelativeLayout rlCompanyFinancing;
    @BindView(R.id.company_scale)
    TextView companyScale;
    @BindView(R.id.iv_company_scale)
    ImageView ivCompanyScale;
    @BindView(R.id.rl_company_scale)
    RelativeLayout rlCompanyScale;
    @BindView(R.id.company_website)
    TextView companyWebsite;
    @BindView(R.id.iv_company_website)
    ImageView ivCompanyWebsite;
    @BindView(R.id.rl_company_website)
    RelativeLayout rlCompanyWebsite;
    @BindView(R.id.submit)
    Button submit;
    private Gson gson = new Gson();


    private String Logo = "";
    private String ShortName = "";
    private String Province = "";
    private String City = "";
    private String District = "";
    private String Address = "";
    private String NatureOfUnit = "";
    private String IndustryTypeId = "";
    private String FinancingStage = "";
    private String StaffSize = "";
    private String WebsiteUrl = "";
    private ArrayList<AddressJson> options1Items = new ArrayList<AddressJson>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private List<String> natureList = new ArrayList<String>();
    private List<String> scaleList = new ArrayList<String>();
    private List<String> financingList = new ArrayList<String>();

    private IndustryType industryType;

    public static final int REQUEST_CAMERA = 10; // 拍照
    private File photoFile;
    private Uri imageUri;
    private List<AlbumItem> selectedImages;//标记选中的图片
    private String upLoadImages = "";//上传图片组
    private File cropFilePath;
    private Uri outPutUri;
    private StsToken stsToken;
    private Company company;
    private String type;
    private String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_perfect_card);
        ButterKnife.bind(this);
        initJsonData();
        type = getIntent().getStringExtra("type");
        if (type.equals("edit")) {
            Bundle bundle = getIntent().getExtras();
            company = (Company) bundle.get("bean");
        }
        initView();
        getOssToken();
        setTitle("完善卡牌");
    }

    private void initView() {
        if (company != null) {
            companyName.setText(company.getFullName());
            if (!TextUtils.isEmpty(company.getLogo())) {
                GlideUtils.getInstance().loadCircleUserIconInto(context, ALiYunFileURLBuilder.getUserIconUrl(company.getLogo()), companyLogo);
                Logo = company.getLogo();
            }
            if (!TextUtils.isEmpty(company.getShortName())) {
                companyAbbreviation.setText(company.getShortName());
                ShortName = company.getShortName();
            }
            if (!TextUtils.isEmpty(company.getAddress())) {
                companyAddress.setText(company.getAddress());
                Address = company.getAddress();
            }

            if (company.getProvince() != null) {
                region = Utils.searchProvincial(company.getProvince());
                Province = company.getProvince() + "";
                region = region + Utils.searchCity(company.getCity());
                City = company.getCity() + "";
                region = region + Utils.searchArea(company.getDistrict());
                District = company.getDistrict() + "";
                companyRegion.setText(region);
            }

            if (!TextUtils.isEmpty(company.getNatureOfUnit())) {
                companyNature.setText(Utils.getNatureOfUnit(company.getNatureOfUnit()));
                NatureOfUnit = company.getNatureOfUnit();

            }
            if (!TextUtils.isEmpty(company.getIndustryTypeId())) {
                companyIndustry.setText(Utils.searchIndustry(Integer.parseInt(company.getIndustryTypeId())));
                IndustryTypeId = company.getIndustryTypeId();

            }
            if (!TextUtils.isEmpty(company.getStaffSize())) {
                companyScale.setText(Utils.getStaffSize(company.getStaffSize()));
                StaffSize = company.getStaffSize();
            }
            if (!TextUtils.isEmpty(company.getFinancingStage())) {
                companyFinancing.setText(Utils.getFinancingStage(company.getFinancingStage()));
                FinancingStage = company.getFinancingStage();
            }

            if (!TextUtils.isEmpty(company.getWebsiteUrl())) {
                WebsiteUrl = company.getWebsiteUrl();
                companyWebsite.setText(company.getWebsiteUrl());
            }

        }
    }

    @OnClick({R.id.rl_company_logo, R.id.rl_company_name, R.id.rl_company_abbreviation, R.id.rl_company_region, R.id.rl_company_address, R.id.rl_company_nature, R.id.rl_company_industry, R.id.rl_company_financing, R.id.rl_company_scale, R.id.rl_company_website, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_company_logo:
                SelectImageDialog imageDialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();

                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                photoFile = new File(Constant.CACHE_DIR_IMAGE + "/" + System.currentTimeMillis() + ".jpg");
                                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
                                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                startActivityForResult(intentCamera, REQUEST_CAMERA);
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_AVATAR);
                                break;
                        }
                    }
                });
                imageDialog.show();

                break;
            case R.id.rl_company_name:
//                startActivity(new Intent(context, ModifyContentActivity.class).
//                        putExtra(ModifyContentActivity.TYPE_TITLE, "公司名称").
//                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.CORPORATE_NAME).
//                        putExtra(ModifyContentActivity.HINT, "与营业照一致...").
//                        putExtra(ModifyContentActivity.TYPE_CONTENT, companyName.getText().toString()));
                break;
            case R.id.rl_company_abbreviation:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "公司简称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.CORPORATE_ABBREVIATION).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, companyAbbreviation.getText().toString()));
                break;
            case R.id.rl_company_region:

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
                        companyRegion.setText(tx);
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
            case R.id.rl_company_address:

                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "公司名地址").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.CORPORATE_ADDRESS).
                        putExtra(ModifyContentActivity.HINT, "请输入详细地址").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, companyAddress.getText().toString()));
                break;
            case R.id.rl_company_nature:
                natureList = Arrays.asList(getResources().getStringArray(R.array.nature));
                OptionsPickerView options = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = natureList.get(options1);
                        if (s.equals("其他")) {
                            NatureOfUnit = 0 + "";
                        } else {
                            NatureOfUnit = options1 + 1 + "";
                        }
                        companyNature.setText(s);
                    }
                })
                        .setTitleText("公司性质")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(natureList);
                options.show();
                break;
            case R.id.rl_company_industry:
                startActivity(new Intent(context, CompanyIndustryActivity.class));
                break;
            case R.id.rl_company_financing:


                financingList = Arrays.asList(getResources().getStringArray(R.array.financing));
                OptionsPickerView financingOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = financingList.get(options1);
                        FinancingStage = options1 + "";
                        companyFinancing.setText(s);
                    }
                })
                        .setTitleText("融资阶段")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                financingOptions.setPicker(financingList);
                financingOptions.show();

                break;
            case R.id.rl_company_scale:

                scaleList = Arrays.asList(getResources().getStringArray(R.array.scale));
                OptionsPickerView options1 = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String s = scaleList.get(options1);


                        StaffSize = (options1 + 1) * 10 + "";

                        companyScale.setText(s);
                    }
                })
                        .setTitleText("公司规模")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options1.setPicker(scaleList);
                options1.show();

                break;
            case R.id.rl_company_website:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "公司官网").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.COMPANY_WEBSITE).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, companyWebsite.getText().toString()));
                break;
            case R.id.submit:


                if (TextUtils.isEmpty(Province)) {
                    AlertUtils.toast(context, "请选择地区");
                }
                if (TextUtils.isEmpty(Address)) {
                    AlertUtils.toast(context, "请添加地址");
                }
                showLoading();
                if (cropFilePath == null) {
                    RequestBody body = new FormBody.Builder()
                            .add("Logo", Logo)
                            .add("ShortName", ShortName)
                            .add("Province", Province)
                            .add("City", City)
                            .add("District", District)
                            .add("Address", Address)
                            .add("NatureOfUnit", NatureOfUnit)
                            .add("IndustryTypeId", IndustryTypeId)
                            .add("FinancingStage", FinancingStage)
                            .add("StaffSize", StaffSize)
                            .add("WebsiteUrl", WebsiteUrl)
                            .build();
                    addCompany(body);
                } else {
                    upload(cropFilePath.getAbsolutePath());
                }


                break;
        }
    }



    private void addCompany(RequestBody body) {
        OkUtils.getInstances().httpPut(body, context, JavaConstant.Company, JavaParamsUtils.getInstances().setCompany(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent()) {
                        EventBus.getDefault().post(new BasicRefreshEvent(true));
                        finish();
                    } else {
                        AlertUtils.toast(context, response.getMessage());
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.COMPANY_WEBSITE:
                companyWebsite.setText(event.getContent());
                WebsiteUrl = event.getContent();
                break;
            case ModifyDataActivity.CORPORATE_ABBREVIATION:
                companyAbbreviation.setText(event.getContent());
                ShortName = event.getContent();

                break;

            case ModifyContentActivity.CORPORATE_ADDRESS:
                companyAddress.setText(event.getContent());
                Address = event.getContent();
                break;
            case ModifyContentActivity.CORPORATE_NAME:


                break;

        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cropFilePath != null) {
            if (!TextUtils.isEmpty(cropFilePath.getAbsolutePath())) {
                Utils. deleteDirWihtFile(new File(Environment.getExternalStorageDirectory().getPath()));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompanyIndustryEvent(CompanyIndustryEvent event) {

        industryType = event.getIndustryType();
        IndustryTypeId = industryType.getId() + "";
        companyIndustry.setText(event.getIndustryType().getName());
    }


    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 初始化剪裁图片的输出Uri
     */
    private void intCropUri() {
        if (outPutUri == null) {
            cropFilePath = new File(Environment.getExternalStorageDirectory().getPath(), "cutImage.png");
            try {
                cropFilePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outPutUri = Uri.fromFile(cropFilePath);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == SelectImageActivity.REQUEST_AVATAR) {
            //从相册选照片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            AlbumItem albumItem = selectedImages.get(0);
            imageUri = Uri.fromFile(new File(albumItem.getFilePath()));
            intCropUri();
            BitmapUtils.createPhotoCrop(this, imageUri, outPutUri);
        } else if (requestCode == REQUEST_CAMERA) {
            //拍照照片
            imageUri = Uri.fromFile(photoFile);
            intCropUri();
            BitmapUtils.createPhotoCrop(this, imageUri, outPutUri);
        } else if (requestCode == Constant.IMAGE_CROP) {

            GlideUtils.getInstance().loadIconInto(context, cropFilePath.getAbsolutePath(), companyLogo);
            //upload(cropFilePath.getAbsolutePath());

        }

    }


    /*保存界面状态，如果activity意外被系统killed，返回时可以恢复状态值*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putString("msg_con", htmlsource);
        if (photoFile != null) {
            if (photoFile.getAbsolutePath() != null) {
                savedInstanceState.putString("msg_camera_picname", photoFile.getAbsolutePath());
            }
        }


        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        photoFile = new File(savedInstanceState.getString("msg_camera_picname"));


    }

    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.getOSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == 10000) {
                    stsToken = response.getContent();

                }
            }
        });
    }

    //上传头像
    private void upload(final String resultpath) {

        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.COMPANYLOGO + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        Logger.i(TAG, "动态图片上传成功：" + objectKey);
                        Logo = objectKey;
                        RequestBody body = new FormBody.Builder()
                                .add("Logo", Logo)
                                .add("ShortName", ShortName)
                                .add("Province", Province)
                                .add("City", City)
                                .add("District", District)
                                .add("Address", Address)
                                .add("NatureOfUnit", NatureOfUnit)
                                .add("IndustryTypeId", IndustryTypeId)
                                .add("FinancingStage", FinancingStage)
                                .add("StaffSize", StaffSize)
                                .add("WebsiteUrl", WebsiteUrl)

                                .build();
                        Log.d(TAG, body.toString());
                        addCompany(body);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {
                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {

                        Logger.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }


}
