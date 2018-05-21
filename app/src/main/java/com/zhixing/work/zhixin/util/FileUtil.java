package com.zhixing.work.zhixin.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**文件工具类
 *
 */

public class FileUtil {


    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File saveFile(File file, Bitmap photo) {

        FileOutputStream fOut = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fOut = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        photo.compress(Bitmap.CompressFormat.PNG, 100, fOut);// 把Bitmap对象解析成流
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
