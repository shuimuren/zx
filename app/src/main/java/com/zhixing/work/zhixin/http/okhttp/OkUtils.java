package com.zhixing.work.zhixin.http.okhttp;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;


import com.google.gson.Gson;
import com.zhixing.work.zhixin.BuildConfig;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.FailObject;
import com.zhixing.work.zhixin.http.HttpHeadUtils;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.Indicator;
import com.zhixing.work.zhixin.util.NetUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 网络请求封装类
 */

public class OkUtils<T> {
    private final static String TAG = "OkUtils";
    private final static String APP_VERSION = "version";
    private final static String SESSION_ID = "sessionId";
    private static String timer;
    private static String randoms;
    private static OkUtils instances;

    private String ASCII;



    private Gson gson = new Gson();
    public static final MediaType JSONType = MediaType.parse("application/json; charset=utf-8");


    public static OkUtils getInstances() {

        if (instances == null) {
            instances = new OkUtils();
        }

        timer = DateFormatUtil.getTimeZ();
        randoms = HttpHeadUtils.getRandom();
        return instances;
    }

    public static String buildParam(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        String result = key + "=";
        if (isEncode) {
            try {
                result += URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            result += value;
        }
        return result;
    }

    private void httpBase(boolean isPost, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {


        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            param.put(APP_VERSION, "" + AppUtils.getVersionCode(context));
//            param.put(SESSION_ID, "" + SettingUtils.getSessionId());
            if (param.size() == 0) {
                Log.i(TAG + "请求参数：", url);
            } else {
                Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            }
            String Session = SettingUtils.getFansAppApiSessionId();
            String Session2 = SettingUtils.getFansucenterSessionId();

            if (isPost) {

                OkHttpUtils.post().url(url).params(param)
                        .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms,HttpHeadUtils.ACCESS_SECRET)))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG + "返回失败= >", "" + e.getMessage());
                        callback.onFailure(32000, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i(TAG + "返回成功 = >", "" + response);
                        EntityObject<T> entity = null;
                        try {
                            entity = gson.fromJson(response, classType);
                        } catch (Exception e1) {
                            callback.onFailure(32000, "服务器错误");
                            Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                        }
                        if (entity != null) {
                            if (entity.getCode() > 0) {
                                //成功状态
                                callback.onSuccess(entity);

                            } else {
                                callback.onFailure(entity.getCode(), "服务器错误");
                                //不成功状态
                                //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                                if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                    logout();
                                }
                            }
                        } else {
                            callback.onFailure(0, "错误");
                        }

                    }
                });
            } else {
                OkHttpUtils.get().url(url).params(param)
                        .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG + "返回失败= >", "" + e.getMessage());
                        callback.onFailure(32000, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG + "返回成功 = >", "" + response);
                        EntityObject<T> entity = null;

                        try {
                            entity = gson.fromJson(response, classType);
                            Log.i("EntityObject", "已解析");
                        } catch (Exception e1) {
                            callback.onFailure(32000, "服务器错误");
                            Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                        }
                        if (entity != null) {
                            if (entity.getCode() > 0) {
                                //成功状态
                                callback.onSuccess(entity);
                            } else {
                                callback.onFailure(entity.getCode(), "服务器错误");
                                //不成功状态

                                //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                                if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                    logout();
                                }
                                callback.onFailure(entity.getCode(), entity.getMessage());
                            }
                        }

                    }
                });
            }
        } else {

        }
    }


    private void httpTokenBase(boolean isPost, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {


        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            param.put(APP_VERSION, "" + AppUtils.getVersionCode(context));
//            param.put(SESSION_ID, "" + SettingUtils.getSessionId());
            if (param.size() == 0) {
                Log.i(TAG + "请求参数：", url);
            } else {
                Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            }
            String Session = SettingUtils.getFansAppApiSessionId();
            String Session2 = SettingUtils.getFansucenterSessionId();

            if (isPost) {
                OkHttpUtils.post().url(url).params(param)
                        .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG + "返回失败= >", "" + e.getMessage());
                        callback.onFailure(32000, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.i(TAG + "返回成功 = >", "" + response);
                        EntityObject<T> entity = null;
                        try {
                            entity = gson.fromJson(response, classType);
                        } catch (Exception e1) {
                            callback.onFailure(32000, "服务器错误");
                            Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                            try {
                                FailObject entity1 = gson.fromJson(response, FailObject.class);
                                if (entity1.getCode() == 10005 && entity1.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");
                                    logout();
                                }
                            } catch (Exception e) {

                            }


                        }
                        if (entity != null) {
                            if (entity.getCode() > 0) {
                                //成功状态
                                callback.onSuccess(entity);

                            } else {
                                callback.onFailure(entity.getCode(), "服务器错误");
                                //不成功状态
                                //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                                if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");
                                    logout();
                                }

                            }
                        } else {
                            callback.onFailure(0, "错误");
                        }
                    }
                });
            } else {
                OkHttpUtils.get().url(url).params(param)
                        .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG + "返回失败= >", "" + e.getMessage());
                        callback.onFailure(32000, e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG + "返回成功 = >", "" + response);
                        EntityObject<T> entity = null;
                        try {
                            entity = gson.fromJson(response, classType);
                            Log.i("EntityObject", "已解析");
                        } catch (Exception e1) {
                            callback.onFailure(32000, "服务器错误");
                            Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                            try {
                                FailObject entity1 = gson.fromJson(response, FailObject.class);
                                if (entity1.getCode() == 10005 && entity1.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");
                                    logout();
                                }
                            } catch (Exception e) {

                            }
                        }
                        if (entity != null) {
                            if (entity.getCode() > 0) {
                                //成功状态
                                callback.onSuccess(entity);
                            } else {
                                callback.onFailure(entity.getCode(), "服务器错误");
                                //不成功状态
                                if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                    AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                    logout();
                                }
                                callback.onFailure(entity.getCode(), entity.getMessage());
                            }
                        }

                    }
                });
            }
        } else {

        }
    }


    private void httpPostBase(final Context context, final String url, Map<String, String> param, final String json, final Type classType, final ResultCallBackListener<T> callback) {
        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            //       String urls = url + "?" + buildParam(param);

            OkHttpUtils.postJson().url(url).addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                    addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                    addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                    addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                    addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                    addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET))).
                    content(json).mediaType(JSONType).build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG + "返回失败= >", "" + e.getMessage());
                    callback.onFailure(32000, e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i(TAG + "返回成功 = >", "" + response);
                    Log.i(TAG + "请求参数 = >", "" + json);

                    EntityObject<T> entity = null;
                    try {
                        JsonReader jsonReader = new JsonReader(new StringReader(response));//其中jsonContext为String类型的Json数据
                        jsonReader.setLenient(true);

                        entity = gson.fromJson(response, classType);
                    } catch (Exception e1) {
                        callback.onFailure(32000, "服务器错误");
                        Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());

                    }
                    if (entity != null) {
                        if (entity.getCode() == 10000) {
                            //成功状态
                            callback.onSuccess(entity);
                        } else {
                            //AlertUtils.toast(context, entity.getReturnMsg());
                            //不成功状态
                            //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                            callback.onFailure(entity.getCode(), entity.getMessage());
                        }
                    } else {

                    }

                }
            });
        }
    }

    public void httpGet(final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        httpBase(false, context, url, param, classType, callback);
    }

    public void httpatch(final RequestBody requestBody, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        patch(requestBody, context, url, param, classType, callback);
    }

    public void httpDelete( final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        delete( context, url, param, classType, callback);
    }

    public void httpPut(final RequestBody requestBody, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        put(requestBody, context, url, param, classType, callback);
    }

    public void httpPost(final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        httpBase(true, context, url, param, classType, callback);
    }

    public void httpTokenPost(final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        httpTokenBase(true, context, url, param, classType, callback);
    }

    public void httpTokenGet(final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        httpTokenBase(false, context, url, param, classType, callback);
    }
    public void httpPostString(final Context context, final String url, Map<String, String> param, String json, final Type classType, final ResultCallBackListener<T> callback) {
        httpPostBase(context, url, param, json, classType, callback);
    }
//    public void post(final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
//
//    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @param destFileDir 目标文件存储的文件夹路径
     * @param callBack
     */
    public void downLoadFile(String fileUrl, final String fileName, final String destFileDir, final ReqProgressCallBack<File> callBack) {
        File file = new File(destFileDir, fileName);
        OkHttpUtils.get().url(fileUrl).build().execute(new FileCallBack(destFileDir, fileName) {

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG + "返回失败 = >", "" + e.getMessage());
                callBack.onFailure(fileName, "下载失败!");
            }

            @Override
            public void onResponse(File response, int id) {
                Log.i(TAG + "返回成功 = >", "文件：" + response);
                callBack.onSuccess(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                Log.i(TAG + "返回成功= >", " progress:" + progress + "  total:" + total);
                callBack.onProgress(progress, total);
            }
        });


    }


    public String post(String url, String json, final Type classType) throws IOException {

        OkHttpUtils.postString().url(url).content(json).mediaType(JSONType).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG + "返回失败= >", "" + e.getMessage());

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG + "返回成功 = >", "" + response);


            }
        });

        return null;
    }


    public String getASCII(String accessId, String time, String radom, String accessSecret) {
        String[] tempArr = {accessId, time, radom, accessSecret};
        Arrays.sort(tempArr);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tempArr.length; i++) {
            sb.append(tempArr[i]);
        }
        ASCII = sb.toString();

        return ASCII;

    }

    //获取签名窜
    public String getSignature(String ASCII) {

        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(HttpHeadUtils.ACCESS_SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(ASCII.getBytes());
            hash = byteArrayToHexString(bytes);
            hash = hash.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return hash;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    private void patch(RequestBody requestBody, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {


        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            param.put(APP_VERSION, "" + AppUtils.getVersionCode(context));
//            param.put(SESSION_ID, "" + SettingUtils.getSessionId());
            if (param.size() == 0) {
                Log.i(TAG + "请求参数：", url);
            } else {
                Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            }
            OkHttpUtils.patch().url(url).requestBody(requestBody).
                    addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                    addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                    addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                    addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                    addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                    addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG + "返回失败= >", "" + e.getMessage());
                    callback.onFailure(32000, e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {

                    Log.i(TAG + "返回成功 = >", "" + response);
                    EntityObject<T> entity = null;
                    try {
                        entity = gson.fromJson(response, classType);
                    } catch (Exception e1) {
                        callback.onFailure(32000, "服务器错误");
                        Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                    }
                    if (entity != null) {
                        if (entity.getCode() > 0) {
                            //成功状态
                            callback.onSuccess(entity);
                        } else {
                            callback.onFailure(entity.getCode(), "服务器错误");
                            //不成功状态
                            //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                            if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                logout();
                            }

                        }
                    } else {
                        callback.onFailure(0, "错误");
                    }

                }
            });
        }


    }

    private void delete( final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {


        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            param.put(APP_VERSION, "" + AppUtils.getVersionCode(context));
//            param.put(SESSION_ID, "" + SettingUtils.getSessionId());
            if (param.size() == 0) {
                Log.i(TAG + "请求参数：", url);
            } else {
                Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            }
            OkHttpUtils.delete().url(url).
                    addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                    addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                    addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                    addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                    addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                    addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG + "返回失败= >", "" + e.getMessage());
                    callback.onFailure(32000, e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {

                    Log.i(TAG + "返回成功 = >", "" + response);
                    EntityObject<T> entity = null;
                    try {
                        entity = gson.fromJson(response, classType);
                    } catch (Exception e1) {
                        callback.onFailure(32000, "服务器错误");
                        Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                    }
                    if (entity != null) {
                        if (entity.getCode() > 0) {
                            //成功状态
                            callback.onSuccess(entity);
                        } else {
                            callback.onFailure(entity.getCode(), "服务器错误");
                            //不成功状态
                            //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                            if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                logout();
                            }

                        }
                    } else {
                        callback.onFailure(0, "错误");
                    }

                }
            });
        }

    }

    private void put(RequestBody requestBody, final Context context, final String url, Map<String, String> param, final Type classType, final ResultCallBackListener<T> callback) {
        if (NetUtils.isConnected(context)) {
            if (param == null) {
                param = new HashMap<String, String>();
            }
//            param.put(APP_VERSION, "" + AppUtils.getVersionCode(context));
//            param.put(SESSION_ID, "" + SettingUtils.getSessionId());
            if (param.size() == 0) {
                Log.i(TAG + "请求参数：", url);
            } else {
                Log.i(TAG + "请求参数：", url + "?" + buildParam(param));
            }
            OkHttpUtils.put().url(url).requestBody(requestBody).
                    addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("version", BuildConfig.VERSION_NAME).//打印版本
                    addHeader(HttpHeadUtils.KEY_TOKEN, SettingUtils.getToken()).
                    addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                    addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                    addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                    addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG + "返回失败= >", "" + e.getMessage());
                    callback.onFailure(32000, e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {

                    Log.i(TAG + "返回成功 = >", "" + response);
                    EntityObject<T> entity = null;
                    try {
                        entity = gson.fromJson(response, classType);
                    } catch (Exception e1) {
                        callback.onFailure(32000, "提交数据失败");
                        Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                    }
                    if (entity != null) {
                        if (entity.getCode() > 0) {
                            //成功状态
                            callback.onSuccess(entity);
                        } else {
                            callback.onFailure(entity.getCode(), "服务器错误");
                            //不成功状态
                            //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                            if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                                AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                                logout();
                            }

                        }
                    } else {
                        callback.onFailure(0, "错误");
                    }

                }
            });
        }


    }

    public void postJson(final Context context, final String url, final String json, final Type classType, final ResultCallBackListener<T> callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象

        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i(TAG + "请求参数：", url + "?" + json.toString());
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).addHeader("token", SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                .build();
        //发送请求获取响应
        Call call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(0, "错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                EntityObject<T> entity = null;
                try {
                    String data = response.body().string();
                    Log.i(TAG + "返回成功 = >", "" + data);
                    entity = gson.fromJson(data, classType);
                } catch (Exception e1) {

                    callback.onFailure(32000, "提交数据失败");
                    Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                }
                if (entity != null) {
                    if (entity.getCode() > 0) {

                        //成功状态
                        callback.onSuccess(entity);
                    } else {
                        callback.onFailure(entity.getCode(), "服务器错误");
                        //不成功状态
                        //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去

                        if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                            AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                            logout();
                        }
                    }
                } else {
                    callback.onFailure(0, "错误");
                }
            }
        });


    }

    public void putJson(final Context context, final String url, final String json, final Type classType, final ResultCallBackListener<T> callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i(TAG + "请求参数：", url + "?" + json.toString());
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody).addHeader("token", SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_ACCESSID,HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                .build();
        //发送请求获取响应
        Call call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(0, "错误");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                EntityObject<T> entity = null;
                try {
                    String data = response.body().string();
                    Log.i(TAG + "返回成功 = >", "" + data);
                    entity = gson.fromJson(data, classType);
                } catch (Exception e1) {

                    callback.onFailure(32000, "提交数据失败");
                    Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                }
                if (entity != null) {
                    if (entity.getCode() > 0) {

                        //成功状态
                        callback.onSuccess(entity);
                    } else {
                        callback.onFailure(entity.getCode(), "服务器错误");
                        //不成功状态
                        //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去

                        if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                            AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                            logout();
                        }
                    }
                } else {
                    callback.onFailure(0, "错误");
                }
            }
        });


    }

    public void patchJson(final Context context, final String url, final String json, final Type classType, final ResultCallBackListener<T> callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象

        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Log.i(TAG + "请求参数：", url + "?" + json.toString());
        Request request = new Request.Builder()
                .url(url)
                .patch(requestBody).addHeader("token", SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                .build();
        //发送请求获取响应
        Call call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(0, "错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                EntityObject<T> entity = null;
                try {
                    String data = response.body().string();
                    Log.i(TAG + "返回成功 = >", "" + data);
                    entity = gson.fromJson(data, classType);
                } catch (Exception e1) {
                    callback.onFailure(32000, "提交数据失败");
                    Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                }
                if (entity != null) {
                    if (entity.getCode() > 0) {

                        //成功状态
                        callback.onSuccess(entity);
                    } else {
                        callback.onFailure(entity.getCode(), "服务器错误");
                        //不成功状态
                        //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去

                        if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                            AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                            logout();
                        }
                    }
                } else {
                    callback.onFailure(0, "错误");
                }
            }
        });


    }

    public void deleteJson(final Context context, final String url, final RequestBody body, final Type classType, final ResultCallBackListener<T> callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        Request request = new Request.Builder().delete()
                .url(url).addHeader("token", SettingUtils.getToken()).
                        addHeader(HttpHeadUtils.KEY_ACCESSID, HttpHeadUtils.ACCESS_ID).
                        addHeader(HttpHeadUtils.KEY_TIMESTAMP, timer).
                        addHeader(HttpHeadUtils.KEY_NONCE, randoms).
                        addHeader(HttpHeadUtils.KEY_SIGNATURE, getSignature(getASCII(HttpHeadUtils.ACCESS_ID, timer, randoms, HttpHeadUtils.ACCESS_SECRET)))
                .build();
        //发送请求获取响应
        Call call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(0, "错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EntityObject<T> entity = null;
                try {
                    String data = response.body().string();
                    Log.i(TAG + "返回成功 = >", "" + data);
                    entity = gson.fromJson(data, classType);
                } catch (Exception e1) {
                    callback.onFailure(32000, "提交数据失败");
                    Log.i(TAG + "服务器返回失败 = >", "GSON解析异常---" + e1.getMessage());
                }
                if (entity != null) {
                    if (entity.getCode() > 0) {

                        //成功状态
                        callback.onSuccess(entity);
                    } else {
                        callback.onFailure(entity.getCode(), "服务器错误");
                        //不成功状态
                        if (entity.getCode() == 10005 && entity.getMessage().equals("Token已过期")) {
                            AlertUtils.toast(ZxApplication.getInstance().getApplicationContext(), "您的账号信息已过期，请重新登录");

                            logout();
                        }
                        //  sessionId 失效 导致重先登录 重新登录还是失败  那就跳到登录界面去
                    }
                } else {
                    callback.onFailure(0, "错误");
                }
            }
        });


    }

    public void clearLoginState() {

        SettingUtils.putSessionId(null);
        SettingUtils.putFansAppApiSessionId(null);
        SettingUtils.putFansAppApiSessionId(null);

        SettingUtils.putUserId(null);
        SettingUtils.putPass_Id(null);
        SettingUtils.putAvatar(null);
    }

    private void logout() {

        Indicator.goLogin(ZxApplication.getInstance());
        //ZxApplication.getInstance().finishAllActivity();
        MainActivity.instance.finish();
        clearLoginState();


    }

}
