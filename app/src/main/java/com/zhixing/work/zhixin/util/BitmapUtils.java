package com.zhixing.work.zhixin.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;


import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangsuli on 2017/4/28.
 */

public class BitmapUtils {


    private static String TAG = "BitmapUtils";

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,
                ScreenUtils.getScreenWidth(ZxApplication.getInstance()),
                ScreenUtils.getScreenHeight(ZxApplication.getInstance()));

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //把bitmap转换成String
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 压缩图片，处理某些手机拍照角度旋转的问题
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static String compressImage(String filePath, String fileName, int quality) throws FileNotFoundException {

        Bitmap bm = getSmallBitmap(filePath);
        Bitmap bit = getSmallBitmap(filePath);
        int size = getBitmapSize(bm);
        LOG.i(TAG, "方法图片压缩前大小" + size + "KB");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        LOG.i(TAG, "图片压缩前大小" + baos.toByteArray().length + "KB");
        int degree = readPictureDegree(filePath);
        if (degree != 0) {//旋转照片角度
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(fileName);
        Long length = outputFile.length() / 1024;
        int mQuality = quality;
        FileOutputStream out = new FileOutputStream(outputFile);
        do {

            bm.compress(Bitmap.CompressFormat.JPEG, mQuality, out);
            mQuality = mQuality - 5;
        } while (outputFile.length() / 1024 < 100 && mQuality > 20);
        LOG.i(TAG, "图片压缩后大小： " + (outputFile.length() / 1024) + "KB");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        LOG.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "bytes.length=  " + (bytes.length / 1024) + "KB"
                + "quality=" + quality);
        return outputFile.getPath();
    }


    /**
     * 判断照片角度
     *
     * @param path
     * @return
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    /**
     * 对图片进行裁剪功能 便于上传
     *
     * @param context
     * @param imageUri
     */
    public static void createPhotoCrop(Activity context, Uri imageUri, Uri outPutUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        /*

        */
        intent.setDataAndType(imageUri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale",true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outPutUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        context.startActivityForResult(intent, Constant.IMAGE_CROP);
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    public static String bitmapCompress(String filePath) {
        // 获取指定文件的指定单位的大小  param1：文件路径  param2：获取大小的类型  1为B、2为KB、3为MB、4为GB
        double bigSize = FileSizeUtil.getFileOrFilesSize(filePath, 3);
        LOG.i("图片压缩前的大小为：", " " + bigSize + "MB");

        // 当图片大于1M时，才进行压缩
        String smallImage;
        if (bigSize > 0.5) {
            smallImage = compress(filePath);
        } else {
            smallImage = filePath;
        }

        double smallSize = FileSizeUtil.getFileOrFilesSize(smallImage, 2);
        LOG.i("图片压缩后的大小为：", "" + smallSize + "KB");
        return smallImage;
    }
    private static String compress(String path) {
        if (path != null) {
            try {
                File file = new File(path);
                Bitmap bm = PictureUtil.getSmallBitmap(path);
                String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String dirPath = Constant.CACHE_DIR_IMAGE;
                String uuid= AppUtils.getUUID();
                FileOutputStream fos = new FileOutputStream(new File(dirPath,uuid+"jpg"));
                bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
                String newPath = dirPath+uuid+"jpg" ;
                return newPath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}
