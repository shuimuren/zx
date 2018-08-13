package com.zhixing.work.zhixin.aliyun;

import android.os.Message;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.bean.ALiImageUrlBean;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.msgctrl.AbstractController;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.RetrofitServiceFactory;
import com.zhixing.work.zhixin.network.TokenCheckedCallback;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.network.response.StsTokenResult;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.AppUtils;
import com.zhixing.work.zhixin.util.DateUtils;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by lhj on 2018/7/5.
 * Description:
 */

public class ALiYunController extends AbstractController {

    private StsToken oSSToken;
    //阿里云验签有效期
    private static final long OSS_PERIOD_OF_VALIDITY = 60 * 60 * 1000l;
    //  private

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            //上传图片
            case MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD:
                if (oSSToken != null && (DateUtils.doDate2Long(oSSToken.getExpiration()) - System.currentTimeMillis() < OSS_PERIOD_OF_VALIDITY)) {
                    uploadImage((Map<String, String>) msg.obj);
                } else {
                    getOSS((Map<String, String>) msg.obj);
                }

                break;
            case MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE:
                if (oSSToken != null && (DateUtils.doDate2Long(oSSToken.getExpiration()) - System.currentTimeMillis() < OSS_PERIOD_OF_VALIDITY)) {
                    loadImage((Map<String, String>) msg.obj);
                } else {
                    getOSS((Map<String, String>) msg.obj);
                }

                break;
        }
        return true;
    }


    /**
     * 获取验签
     */
    private void getOSS(Map<String, String> params) {
        Call<StsTokenResult> call = RetrofitServiceFactory.getSpaService().getStsToken();
        call.enqueue(new TokenCheckedCallback<StsTokenResult>() {
            @Override
            protected void postResult(StsTokenResult result) {
                oSSToken = result.getContent();
                if (params.containsKey(ALiYunFileURLBuilder.KEY_IMAGE_MARK)) {
                    loadImage(params);
                } else {
                    uploadImage(params);
                }

            }

            @Override
            protected void postError(String errorMessage) {
                Logger.e("获取验签失败");
            }
        });
    }

    /**
     * 上传图片
     * objectKey:子项目录
     * filePath:图片地址
     * bucketName:主目录
     *
     * @param params
     */
    private void uploadImage(Map<String, String> params) {
        String objectKey = params.get(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE) + AppUtils.getUUID();
        String filePath = params.get(ALiYunFileURLBuilder.KEY_FILE_PATH);
        String bucketName = params.get(ALiYunFileURLBuilder.KEY_BUCKET_NAME);
        String code = params.get(ALiYunFileURLBuilder.KEY_IMAGE_CODE);
        if (TextUtils.isEmpty(objectKey) || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(bucketName)) {
            AlertUtils.show("请确认参数无误");
            return;
        }
        asyncUpload(Integer.parseInt(code), oSSToken, bucketName, objectKey, filePath);
    }

    /**
     * 文件异步上传
     *
     * @param bucketName     上传到Bucket的名字
     * @param objectKey      上传到OSS后的ObjectKey
     * @param uploadFilePath 上传文件的本地路径
     */
    public static void asyncUpload(int code, StsToken stsToken, String bucketName, final String objectKey, String uploadFilePath) {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSClient mOSSClient = new OSSClient(ZxApplication.getInstance().getApplicationContext(), ALiYunFileURLBuilder.END_POINT, credentialProvider, conf);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadFilePath);
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType("image/png");
//
//        put.setMetadata(metadata);

//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//
//        });

        mOSSClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Logger.i(">>>", "图片上传成功。。。");
                Logger.i(">>>", "resut>" + result.toString());
                ImageUploadResult uploadResult = new ImageUploadResult(code, request.getObjectKey(), true);
                RxBus.getInstance().post(uploadResult);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                Logger.i(">>>", "图片上传失败。。。" + "client>" + clientException.getMessage() + "service" + serviceException.getMessage());
                ImageUploadResult uploadResult = new ImageUploadResult(code, "", false);
                RxBus.getInstance().post(uploadResult);
            }
        });
    }

    private void loadImage(Map<String, String> params) {
        String url = "";
        String baseUrl = params.get(ALiYunFileURLBuilder.KEY_LOAD_BASE_URL);
        String bucketName = params.get(ALiYunFileURLBuilder.KEY_BUCKET_NAME);
        String objectKey = params.get(ALiYunFileURLBuilder.KEY_IMAGE_MARK);
        if (TextUtils.isEmpty(bucketName) || TextUtils.isEmpty(objectKey) || TextUtils.isEmpty(baseUrl)) {
            AlertUtils.show("请确认参数无误");
        }
        //public static String gteSecret(Context context, StsToken stsToken, String bucketName, final String objectKey) {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(oSSToken.getAccessKeyId(), oSSToken.getAccessKeySecret(), oSSToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        conf.setHttpDnsEnable(true);
        OSSClient mOSSClient = new OSSClient(ZxApplication.getInstance().getApplicationContext(), baseUrl, credentialProvider, conf);
        if (bucketName.equals(ALiYunFileURLBuilder.BUCKET_SECTET)) {
            try {
                url = mOSSClient.presignConstrainedObjectURL(bucketName, objectKey, 30 * 60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            url = mOSSClient.presignPublicObjectURL(bucketName, objectKey);
        }

        ALiImageUrlBean bean = new ALiImageUrlBean(url, objectKey);
        RxBus.getInstance().post(bean);
        Logger.i(">>>", "url>" + url);

    }

    ALiYunOssFileLoader.OssFileUploadListener uploadListener = new ALiYunOssFileLoader.OssFileUploadListener() {
        @Override
        public void onUploadSuccess(String objectKey) {
            Logger.i(">>>", "成功>" + objectKey);

        }

        @Override
        public void onUploadProgress(String objectKey, long currentSize, long totalSize) {
            Logger.i(">>>", "上传中");
        }

        @Override
        public void onUploadFailure(String objectKey, ServiceException ossException) {
            Logger.i(">>>", "上传失败");

        }
    };

}
