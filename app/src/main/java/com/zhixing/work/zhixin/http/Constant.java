
package com.zhixing.work.zhixin.http;

import android.os.Environment;

import java.io.File;


/**
 * 公共常量类
 */
public class Constant {
    public static final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String BASE = absolutePath + File.separator + "zhixin/";
    // 图片缓存
    public static final String CACHE_DIR_IMAGE = BASE + "image/";
    // 缩略图缓存
    public static final String THUMBNAIL_DIR_IMAGE = BASE + "thumbnail/";
    // 地图截图缓存
    public static final String MAP_DIR_IMAGE = BASE + "map/";
    // 录音文件夹
    public static final String VOICE_DIR = BASE + "voice/";
    //数据库文件
    public static final String DB_DIR = BASE + "db/";
    //app下载文件
    public static final String UPDATE_FILE_DIR = BASE + "updateFile/";
    public static final String XML_PATH = BASE + "zhixindata/";
    //默认图片压缩质量
    public static final int IMAGE_QUALITY = 80;
    //图片裁剪
    public static final int IMAGE_CROP = 1001;



    static {
        File f1 = new File(CACHE_DIR_IMAGE);
        File f2 = new File(THUMBNAIL_DIR_IMAGE);
        File f3 = new File(MAP_DIR_IMAGE);
        File f4 = new File(VOICE_DIR);
        File f5 = new File(DB_DIR);
        File f6 = new File(UPDATE_FILE_DIR);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        if (!f2.exists()) {
            f2.mkdirs();
        }
        if (!f3.exists()) {
            f3.mkdirs();
        }
        if (!f4.exists()) {
            f4.mkdirs();
        }
        if (!f5.exists()) {
            f5.mkdirs();
        }
        if (!f6.exists()) {
            f6.mkdirs();
        }
    }


}
