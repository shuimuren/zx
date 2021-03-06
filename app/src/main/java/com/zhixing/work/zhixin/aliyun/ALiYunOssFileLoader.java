package com.zhixing.work.zhixin.aliyun;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.common.Logger;


/**
 * 阿里云上传工具类
 */

public class ALiYunOssFileLoader {
    public static final String TAG = ALiYunOssFileLoader.class.getSimpleName();

    /**
     * 文件异步上传
     *
     * @param bucketName     上传到Bucket的名字
     * @param objectKey      上传到OSS后的ObjectKey
     * @param uploadFilePath 上传文件的本地路径
     * @param listener       回调监听
     */
    public static void asyncUpload(Context context, StsToken stsToken, String bucketName, final String objectKey, String uploadFilePath, final OssFileUploadListener listener) {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSClient mOSSClient = new OSSClient(context, ALiYunFileURLBuilder.END_POINT, credentialProvider, conf);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadFilePath);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Logger.d(TAG + "PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                if (listener != null)
                    listener.onUploadProgress(objectKey, currentSize, totalSize);
            }
        });

        OSSAsyncTask task = mOSSClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Logger.d(TAG + "PutObject", "UploadSuccess");

                Logger.d(TAG + "ETag", result.getETag());
                Logger.d(TAG + "RequestId", result.getRequestId());
                if (listener != null)
                    listener.onUploadSuccess(request.getObjectKey());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Logger.e(TAG + "ErrorCode", serviceException.getErrorCode());
                    Logger.e(TAG + "RequestId", serviceException.getRequestId());
                    Logger.e(TAG + "HostId", serviceException.getHostId());
                    Logger.e(TAG + "RawMessage", serviceException.getRawMessage());
                }
                if (listener != null)
                    listener.onUploadFailure(request.getObjectKey(), serviceException);
            }
        });
    }

    public interface OssFileUploadListener {
        void onUploadSuccess(String objectKey);

        void onUploadProgress(String objectKey, long currentSize, long totalSize);

        void onUploadFailure(String objectKey, ServiceException ossException);
    }


    public static String gtePublic(Context context, StsToken stsToken, String bucketName, final String objectKey) {

        String url = "";
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSClient mOSSClient = new OSSClient(context, ALiYunFileURLBuilder.PUBLIC_END_POINT, credentialProvider, conf);
        conf.setHttpDnsEnable(true);
        url = mOSSClient.presignPublicObjectURL(bucketName, objectKey);
        return url;
    }


    public static String gteSecret(Context context, StsToken stsToken, String bucketName, final String objectKey) {

        String url = "";
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        conf.setHttpDnsEnable(true);
        OSSClient mOSSClient = new OSSClient(context, ALiYunFileURLBuilder.SECRET_END_POINT, credentialProvider, conf);
        try {
            url = mOSSClient.presignConstrainedObjectURL(bucketName, objectKey, 30 * 60);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return url;
    }

}
