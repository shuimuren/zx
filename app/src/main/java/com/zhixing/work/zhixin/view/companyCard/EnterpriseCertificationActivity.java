package com.zhixing.work.zhixin.view.companyCard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.reflect.TypeToken;
import com.xmd.file.provider.FileProvider7;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.adapter.PublicEducationAdapter;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.event.UploadImageFinishEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.AppUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;
import com.zhixing.work.zhixin.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 公司认证
 */
public class EnterpriseCertificationActivity extends BaseTitleActivity {

    @BindView(R.id.corporate_name)
    TextView corporateName;
    @BindView(R.id.corporate_name_right)
    ImageView corporateNameRight;
    @BindView(R.id.rl_corporate_name)
    RelativeLayout rlCorporateName;
    @BindView(R.id.corporate_nature)
    TextView corporateNature;
    @BindView(R.id.corporate_nature_right)
    ImageView corporateNatureRight;
    @BindView(R.id.rl_corporate_nature)
    RelativeLayout rlCorporateNature;
    @BindView(R.id.name_ed)
    TextView nameEd;
    @BindView(R.id.name_right)
    ImageView nameRight;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.listview)
    RecyclerView listview;
    @BindView(R.id.submit)
    Button submit;


    private String FullName;
    private String NatureOfUnit;
    private String LegalPersonName;
    private String BusinessLicensePic;
    private List<String> natureList;
    private String natureType = "";


    private List<AlbumItem> publishImages;
    private PublicEducationAdapter adapter;
    private static final int MAX_UPLOAD_IMAGE = 1;//最多上传3张
    public static final int REQUEST_CAMERA = 106;
    private String token = "";
    private File photoFile;
    private String photoPath;
    private int isUploadCount;
    private String upLoadImages = "";//上传图片组
    private List<AlbumItem> selectedImages;
    private List<AlbumItem> upImages = new ArrayList<AlbumItem>();
    private StsToken stsToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_certification);
        ButterKnife.bind(this);
        setTitle("公司认证");
        publishImages = new ArrayList<>();
        publishImages.add(null);
        getOssToken();
        initView();
    }

    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, RequestConstant.GET_OSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == NetworkConstant.SUCCESS_CODE) {
                    stsToken = response.getContent();
                }
            }
        });
    }
    private void initView() {
        adapter = new PublicEducationAdapter(this, publishImages);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        listview.setLayoutManager(gridLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.publish_line));
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new PublicEducationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlbumItem item = publishImages.get(position);
                if (item == null) {
                    SelectImageDialog dialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                        @Override
                        public void onClick(SelectImageDialog dialog, int index) {
                            dialog.dismiss();
                            switch (index) {
                                case SelectImageDialog.TYPE_CAMERA:
                                    takeAPicture();
                                    break;
                                case SelectImageDialog.TYPE_PHOTO:
                                    Intent intent = new Intent(context, SelectImageActivity.class);
                                    intent.putExtra(SelectImageActivity.LIMIT, MAX_UPLOAD_IMAGE + 1 - publishImages.size());
                                    startActivityForResult(intent, SelectImageActivity.REQUEST_DYNAMIC);
                                    break;
                            }

                        }
                    });
                    dialog.show();
                } else {
                    if (publishImages.get(publishImages.size() - 1) == null) {
                        publishImages.remove(item);
                    } else {
                        publishImages.remove(item);
                        publishImages.add(null);
                    }
                    Logger.i(TAG, "images.SIZE:" + publishImages.size());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + "/" + AppUtils.getUUID();
        photoFile = new File(photoPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(context,photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {

            case ModifyDataActivity.TYPE_NAME:
                nameEd.setText(event.getContent());
                LegalPersonName = event.getContent();
                break;
            case ModifyContentActivity.CORPORATE_NAME:
                corporateName.setText(event.getContent());
                FullName = event.getContent();
                break;
        }
    }
    @OnClick({R.id.rl_corporate_name, R.id.rl_corporate_nature, R.id.rl_name, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_corporate_name:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "公司名称").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.CORPORATE_NAME).
                        putExtra(ModifyContentActivity.HINT, "与营业照一致...").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, corporateName.getText().toString()));
                break;
            case R.id.rl_corporate_nature:
                //公司性质
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
                        corporateNature.setText(s);
                    }
                })
                        .setTitleText("公司资质")
                        .setSubCalSize(20)//确定和取消文字大小
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .build();
                options.setPicker(natureList);
                options.show();
                break;
            case R.id.rl_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra("title", "填写名字").putExtra("type", "name")
                        .putExtra(ModifyDataActivity.TYPE_CONTENT, nameEd.getText().toString())
                );
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(FullName)) {
                    AlertUtils.toast(context, "企业名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(LegalPersonName)) {
                    AlertUtils.toast(context, "法人姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(NatureOfUnit)) {
                    AlertUtils.toast(context, "公司性质不能为空");
                    return;
                }
                if (null != publishImages && publishImages.size() >0) {
                    final List<String> list = new ArrayList<String>();
                    for (int i = 0; i < publishImages.size(); i++) {
                        AlbumItem albumItem = publishImages.get(i);
                        if (null != albumItem) {
                            list.add(albumItem.getFilePath());
                        }
                    }
                    if (list.isEmpty()) {
                        AlertUtils.toast(context, "还未选择图片");
                        return;
                    }
                    compressWithLs(list);
                } else {
                    AlertUtils.toast(context, "还未选择图片");
                }
                break;
        }
    }

    //压缩图片
    //* 压缩图片 Listener 方式

    private void compressWithLs(final List<String> photos) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Luban.with(context)
                        .load(photos)
                        .ignoreBy(100)
                        .setTargetDir(getPath())
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                showLoadingDialog("上传中...");
                            }

                            @Override
                            public void onSuccess(File file) {
                                Log.i(TAG, file.getAbsolutePath());
                                showResult(photos, file);
                            }

                            @Override
                            public void onError(Throwable e) {

                                Logger.d("错误", e.toString());
                            }
                        }).launch();

            }
        }).start();
    }


    private void showResult(List<String> photos, File file) {

        int[] thumbSize = computeSize(file.getAbsolutePath());
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);
        Logger.d("图", thumbArg);
        AlbumItem albumItem = new AlbumItem();
        albumItem.setFilePath(file.getAbsolutePath());
        upImages.add(albumItem);
        if (upImages.size() == photos.size()) {

            new Thread(new uploadRunnable()).start();
        }
    }


    /**********************
     * 以下为上传代码
     *********************************/
    private class uploadRunnable implements Runnable {

        @Override
        public void run() {
            isUploadCount = 0;
            for (int i = 0; i < upImages.size(); i++) {
                AlbumItem albumItem = upImages.get(i);
                if (null != albumItem) {
                    upload(albumItem, i);
                }
            }
        }
    }


    //上传阿里云
    private void upload(final AlbumItem albumItem, final int index) {
        String thumbnail = albumItem.getFilePath();
        String resultpath = thumbnail;
        String UUID = AppUtils.getUUID();
        final int size = publishImages.size();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_SECTET, ALiYunFileURLBuilder.BUSINESSLICENSE + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        isUploadCount++;
                        if (index == 0) {
                            upLoadImages = "";
                            upLoadImages = objectKey;
                        } else {
                            upLoadImages = upLoadImages + "," + objectKey;
                        }
                        if (isUploadCount == size - (publishImages.get(size - 1) == null ? 1 : 0)) {
                            Logger.i(TAG, "动态图片上传成功：" + upLoadImages);
                            RequestBody body = new FormBody.Builder()
                                    .add("FullName", FullName)
                                    .add("NatureOfUnit", NatureOfUnit)
                                    .add("LegalPersonName", LegalPersonName)
                                    .add("BusinessLicensePic", objectKey)
                                    .build();
                            putCertification(body);
                        }
                        Logger.i(TAG, "动态图片上传成功：" + objectKey);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {

                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {
                        hideLoadingDialog();
                        upImages.clear();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertUtils.toast(context, "动态图片上传失败");
                            }
                        });
                        Logger.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == SelectImageActivity.REQUEST_DYNAMIC) {
            //通过相册选择图片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            for (int i = 0; i < selectedImages.size(); i++) {
                SortImage(selectedImages.get(i));
            }
        } else if (requestCode == REQUEST_CAMERA) {
            //使用相机拍照
            final int rotateDegree = BitmapUtils.readPictureDegree(photoFile.getAbsolutePath());
            Logger.i(TAG, "拍照后旋转的角度：" + rotateDegree);
            AlbumItem image = new AlbumItem();
            image.setFilePath(photoFile.getAbsolutePath());
            image.setThumbnail(photoFile.getAbsolutePath());
            SortImage(image);
        }
    }

    private void SortImage(AlbumItem item) {
        // 将每一张图片插入null的最前面
        publishImages.add(publishImages.size() - 1, item);
        if (publishImages.size() == MAX_UPLOAD_IMAGE + 1) {
            publishImages.remove(publishImages.size() - 1);
        }
        adapter.notifyDataSetChanged();
    }


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


    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/zhixin/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private int[] computeSize(String srcImg) {
        int[] size = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg, options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        return size;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        //dir.delete();// 删除目录本身
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTradeAreaEvent(UploadImageFinishEvent event) {
        AlertUtils.toast(context, "资料上传成功,请耐心等待审核");
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_COMPANY_CERTIFICATION_STATUS);
        finish();
    }
    private void putCertification(RequestBody body) {
        OkUtils.getInstances().httpatch(body, context, RequestConstant.BUSINESS_LICENSE, JavaParamsUtils.getInstances().BusinessLicense(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, msg);
            }
            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                upImages.clear();
                String path = Environment.getExternalStorageDirectory() + "/zhixin/image/";
                deleteDir(path);
                EventBus.getDefault().post(new UploadImageFinishEvent(upLoadImages.toString()));

            }
        });
    }


}
