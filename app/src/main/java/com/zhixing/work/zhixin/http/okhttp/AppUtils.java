package com.zhixing.work.zhixin.http.okhttp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.util.PreferenceUtils;

import java.io.File;

/**app相关工具类
 * Created by wangsuli on 2017/4/26.
 */

public class AppUtils {
    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static String getUUID(){
        return java.util.UUID.randomUUID().toString();
    }
    /**
     * 删除旧的app文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteOldUpdateFile(String filePath) {
        if(TextUtils.isEmpty(filePath)){
            return false;
        }
        File file = new File(Constant.UPDATE_FILE_DIR,filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**便于删除旧app文件
     * 版本更新文件名
     * @return
     */
    public static String getAppFile() {
        return  PreferenceUtils.getString("AppFile");
    }
    public static void setAppFile(String filePath) {
        PreferenceUtils.putString("AppFile",filePath);
    }

    /**
     * 更新对比大小
     * @param first
     * @param second
     * @return
     */
    public static boolean isUpdate(String first, String second) {
        int firstNum = Integer.parseInt(first.replace(".", ""));
        int secondNum = Integer.parseInt(second.replace(".", ""));
        return firstNum-secondNum>0;
    }
}
