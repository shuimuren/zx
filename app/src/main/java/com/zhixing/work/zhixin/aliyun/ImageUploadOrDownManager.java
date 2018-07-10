package com.zhixing.work.zhixin.aliyun;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhj on 2018/7/9.
 * Description:
 */

public class ImageUploadOrDownManager {

    /**
     *
     * @param imageCode : 上传图片标记为,用于返回数据后验证
     * @param objectKey 上传到OSS后的ObjectKey
     * @param filePath :图片本地路径
     * @param bucketName :主目录
     * @return
     */
    public static Map getUploadHashMap(int imageCode, String objectKey, String filePath, String bucketName) {
        Map hashMap = new HashMap();
        hashMap.put(ALiYunFileURLBuilder.KEY_IMAGE_CODE, String.valueOf(imageCode));
        hashMap.put(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE, objectKey);
        hashMap.put(ALiYunFileURLBuilder.KEY_FILE_PATH, filePath);
        hashMap.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, bucketName);
        return hashMap;

    }
}
