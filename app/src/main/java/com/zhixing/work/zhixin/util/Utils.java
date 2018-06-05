package com.zhixing.work.zhixin.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.app.ZxApplication;
import com.zhixing.work.zhixin.bean.AddressJson;
import com.zhixing.work.zhixin.bean.HotCity;
import com.zhixing.work.zhixin.bean.IndustryType;
import com.zhixing.work.zhixin.bean.JobType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 大杂烩的工具方法
 *
 * @author Administrator
 */

/**
 * @author Administrator
 */
public class Utils {

    public static final String RED_COLOR = "#ff0066";
    private static final double EARTH_RADIUS = 6378137.0;
    public static final String RED_PREFIX = "<font color=\"" + Utils.RED_COLOR
            + "\">";

    public static boolean isToday(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH) + 1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }
        return false;
    }

    /**
     * 加密显示手机号码
     *
     * @param number
     * @return
     */
    public static String formatPhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(number.substring(0, 3));
        buffer.append("****");

        int length = number.length();
        if (length < 8) {
            return number;
        }

        buffer.append(number.substring(length - 4, length));
        number = buffer.toString();
        return number;
    }

    /**
     * 加密格式化显示真实姓名，保留前一位
     *
     * @param name
     * @return
     */
    public static String formatRealname(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        if (name.length() > 1) {
            buffer.append(name.substring(0, 1));
        } else {
            buffer.append(name);
        }

        buffer.append("****");

        return buffer.toString();
    }

    /**
     * 加密格式化显示用户名，保留用户名的前2位
     *
     * @param name
     * @return
     */
    public static String formatUsername(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        if (name.length() > 2) {
            buffer.append(name.substring(0, 2));
        } else {
            buffer.append(name);
        }

        buffer.append("****");

        return buffer.toString();
    }

    //返回json
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 加密显示身份证号码，保留身份证的前4位
     *
     * @param number
     * @return
     */
    public static String formatIdCardNo(String number) {

        if (TextUtils.isEmpty(number)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        if (number.length() > 4) {
            buffer.append(number.substring(0, 4));
        } else {
            buffer.append(number);
        }

        buffer.append("**************");
        number = buffer.toString();
        return number;
    }

    /**
     * 加密格式化显示用户邮箱
     *
     * @param email
     * @return
     */
    public static String formatMail(String email) {
        if (TextUtils.isEmpty(email)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(email.substring(0, email.lastIndexOf("@")));
        buffer.append("****");
        return buffer.toString();
    }

    /**
     * 加密显示银行账户卡号
     *
     * @param number
     * @return
     */
    public static String formatIdBankNo(String number) {

        if (TextUtils.isEmpty(number)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        int length = number.length();
        if (length > 11) {
            buffer.append(number.substring(0, 4));
            for (int i = 0; i < length - 8; i++) {
                buffer.append("*");
            }
            buffer.append(number.substring(length - 4, length));
        } else if (length > 8) {
            buffer.append(number.substring(0, 4));
            buffer.append("****");
            buffer.append(number.substring(length - 4, length));
        } else if (length > 4) {
            buffer.append(number.substring(0, 4));
            buffer.append("****");
        } else {
            buffer.append(number);
            buffer.append("****");
        }

        return buffer.toString();
    }

    /**
     * 分页查询的时候是否有下一页
     *
     * @param pageIndex
     * @param pageSize
     * @param count
     * @return
     */
    public static boolean hasMorePage(int pageIndex, int pageSize, int count) {
        if (pageIndex * pageSize >= count) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO1(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(147)|(166)|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 不超过15位的数字
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO2(String mobiles) {
        Pattern p = Pattern.compile("^\\d{1,15}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 限制电话号码为11位数
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }

        int length = mobiles.length();
        if (length == 11) {
            return true;
        }

        return false;
    }

    /**
     * 是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean isEmailValid(String mail) {
        Pattern pattern = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = pattern.matcher(mail);
        return m.matches();
    }


    //正则表达式
    public static boolean checkEmaile(String emaile) {
        /**
         *   ^匹配输入字符串的开始位置
         *   $结束的位置
         *   \转义字符 eg:\. 匹配一个. 字符  不是任意字符 ，转义之后让他失去原有的功能
         *   \t制表符
         *   \n换行符
         *   \\w匹配字符串  eg:\w不能匹配 因为转义了
         *   \w匹配包括字母数字下划线的任何单词字符
         *   \s包括空格制表符换行符
         *   *匹配前面的子表达式任意次
         *   .小数点可以匹配任意字符
         *   +表达式至少出现一次
         *   ?表达式0次或者1次
         *   {10}重复10次
         *   {1,3}至少1-3次
         *   {0,5}最多5次
         *   {0,}至少0次 不出现或者出现任意次都可以 可以用*号代替
         *   {1,}至少1次  一般用+来代替
         *   []自定义集合     eg:[abcd]  abcd集合里任意字符
         *   [^abc]取非 除abc以外的任意字符
         *   |  将两个匹配条件进行逻辑“或”（Or）运算
         *   [1-9] 1到9 省略123456789
         *    邮箱匹配 eg: ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
         *
         */
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配\
        return m.matches();
    }

    /**
     * 是否是身份证
     *
     * @param
     * @return
     */
    public static boolean isIdCardNo(String idCards) {

        String idCard2 = idCards.toUpperCase(Locale.CHINA);

        boolean flag = false;
        int idCardsSize = idCard2.length();
        Pattern pattern = null;
        Matcher matcher = null;
        if (idCardsSize == 15 || idCardsSize == 18) {
            if (idCardsSize == 18) {
                pattern = Pattern.compile("^[0-9]{17}([0-9]|X)$");
            } else if (idCardsSize == 15) {
                pattern = Pattern.compile("^[0-9]{15}$");
            } else {
                pattern = Pattern.compile("^[0-9]{1,17}$");
            }
            matcher = pattern.matcher(idCard2);
            // 进行有效性匹配
            if (matcher.matches()) {
                flag = true;
            }
        } else {
            flag = false;
        }
        return flag;
    }


    /**
     * 是否是身份证
     *
     * @param
     * @return
     */
    public static boolean isIdCardNo18(String idCards) {

        String idCard2 = idCards.toUpperCase(Locale.CHINA);

        boolean flag = false;
        int idCardsSize = idCard2.length();
        Pattern pattern = null;
        Matcher matcher = null;
        if (idCardsSize == 18) {
            if (idCardsSize == 18) {
                pattern = Pattern.compile("^[0-9]{17}([0-9]|X)$");
            } else if (idCardsSize == 15) {
                pattern = Pattern.compile("^[0-9]{15}$");
            } else {
                pattern = Pattern.compile("^[0-9]{1,17}$");
            }
            matcher = pattern.matcher(idCard2);
            // 进行有效性匹配
            if (matcher.matches()) {
                flag = true;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 复制数组
     *
     * @param src
     * @return
     */
    public static List<Integer> copyList(List<Integer> src) {
        List<Integer> dst = new ArrayList<Integer>();
        for (int i = 0; i < src.size(); i++) {
            dst.add(src.get(i));
        }
        return dst;
    }

    /**
     * 检查List是否为空
     *
     * @param ryBwBalls
     * @return
     */
    public static boolean isListEmpty(List<Integer> ryBwBalls) {
        if (ryBwBalls == null || ryBwBalls.isEmpty()) {
            return true;
        }
        return false;
    }


    /**
     * 格式化金额，保留小数点后2位
     *
     * @param money
     * @return
     */
    public static String formatMoney(double money) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(money);
        } catch (Exception e) {
            return "0.00";
        }
    }

    /**
     * 格式化显示订单状态
     *
     * @param state
     * @param tvState
     */
    public static void setOrderStateLabel(String state, TextView tvState) {
        if ("待处理".equals(state)) { // 蓝色
            tvState.setTextColor(0xff2065c2);
        } else if ("招募中".equals(state) || "进行中".equals(state)) { // 绿色
            tvState.setTextColor(0xff2bba3a);
        } else if ("已流产".equals(state) || "购买失败".equals(state)
                || "已撤单".equals(state)) { // 灰色
            tvState.setTextColor(0xff939393);
        } else {
            tvState.setTextColor(0xff333333);
        }
    }

    /**
     * 打开软盘
     *
     * @param editText
     */
    public static void openKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(editText, 0);
    }

    /**
     * 关闭软盘
     *
     * @param context
     */
    public static void closeKeyboard(Activity context) {
        if (context == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(context.getWindow().getDecorView()
                .getWindowToken(), 0);
    }

    /**
     * @param dp dp转像素
     * @return 像素值
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 将一个字符串数组转化为用逗号隔开的string字符串
     */
    public static String stringListToString(List<String> strings) {

        String result = "";

        if (strings == null) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        int j = 0;
        for (int i = 0; i < strings.size(); i++) {
            text.append(strings.get(i));
            j++;
            if (j < strings.size()) {
                text.append(",");
            }
        }

        result = text.toString();
        return result;
    }

    /**
     * 获取资源文件的字符串
     *
     * @param stringId
     * @return
     */
    public static String getStringById(int stringId) {

        String str = ZxApplication.getInstance().getResources()
                .getString(stringId);
        return str;
    }

    /**
     * 下拉列表 是否有更多的页
     *
     * @param count     总的数据条数
     * @param pageIndex 当前下载下来的页码
     * @param pageSize  每页的条数
     * @return
     */
    public static boolean hasMorePage(int count, String pageIndex,
                                      String pageSize) {

        int count1 = count;
        int pageIndex1 = Integer.valueOf(pageIndex);
        int pageSize1 = Integer.valueOf(pageSize);

        if (pageIndex1 * pageSize1 >= count1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否有网络连接
     *
     * @return
     */
    public static boolean isConnect() {
        ConnectivityManager manager = (ConnectivityManager) ZxApplication
                .getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (null == networkInfo) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 将map转化为URL请求格式字符串
     *
     * @param params
     * @return
     */
    public static String mapToUrlString(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return null;
        }

        StringBuilder text = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            text.append(key);
            text.append("=");
            text.append(params.get(key));
            i++;
            if (i < params.size()) {
                text.append("&");
            }
        }

        return text.toString();
    }

    /**
     * 代表整数的字符串相加 结果依然为字符串
     *
     * @param x
     * @return
     */
    public static String sumIntString(String... x) {

        String result = "";
        int sum = 0;
        for (String string : x) {
            if (TextUtils.isEmpty(string)) {
                string = "0";
            }
            int i = Integer.valueOf(string);
            sum += i;
        }

        result = String.valueOf(sum);
        return result;
    }


    /**
     * 返回时间的long类型比较大小
     *
     * @param
     * @return
     */

    public static Long getLongTime(String time) {
        time = time.replace("-", "");
        time = time.replace(":", "");
        time = time.replaceAll(" ", "");
        Long result = Long.parseLong(time);
        return result;
    }

    /**
     * 返回jav需要bean
     *
     * @param
     * @return
     */


    /**
     * 四舍五入 保留scale位小数点
     *
     * @param v
     * @param scale
     * @return
     */
    public static double roundForNumber(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留两位
     *
     * @param v
     * @return
     */
    public static double roundForNumberTwo(double v) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 搜索省
     */
    public static String searchProvincial(int id) {
        String provincial = "";
        List<AddressJson> list = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getProvincialList())) {
            list = gson.fromJson(SettingUtils.getProvincialList(), new TypeToken<List<AddressJson>>() {
            }.getType());
        }
        for (int i = 0; i < list.size(); i++) {
            AddressJson addressJson = list.get(i);
            if (addressJson.getId() == id) {
                provincial = addressJson.getName();
                break;
            }

        }
        return provincial;
    }

    /**
     * 搜索市
     */
    public static String searchCity(int id) {
        String city = "";


        List<AddressJson.ChildBeanX> list = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getCityList())) {
            list = gson.fromJson(SettingUtils.getCityList(), new TypeToken<List<AddressJson.ChildBeanX>>() {
            }.getType());
        }
        for (int i = 0; i < list.size(); i++) {
            AddressJson.ChildBeanX bean = list.get(i);
            if (bean.getId() == id) {
                city = bean.getName();
                break;
            }

        }
        return city;
    }


    /**
     * 搜索市
     */
    public static String searchHotCity(int id) {
        String city = "";

//
        List<HotCity> list = new ArrayList<HotCity>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getHotCityList())) {
            list = gson.fromJson(SettingUtils.getHotCityList(), new TypeToken<List<HotCity>>() {
            }.getType());
        }

       /* Gson gson = new Gson();
        String JsonData = Utils.getJson(context, "hotcity.json");//获取assets目录下的json文件数据
        List<HotCity> cityList = gson.fromJson(JsonData, new TypeToken<List<HotCity>>() {
        }.getType());*/
        for (int i = 0; i < list.size(); i++) {
            HotCity bean = list.get(i);
            if (bean.getId() == id) {
                city = bean.getName();
                break;
            }

        }
        return city;
    }


    /**
     * 搜索行业
     */
    public static String searchIndustry(int id) {
        String city = "";

//
        List<IndustryType> list = new ArrayList<IndustryType>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getIndustry())) {
            list = gson.fromJson(SettingUtils.getIndustry(), new TypeToken<List<IndustryType>>() {
            }.getType());
        }
        list = getIndustryTypeList(list);


        for (int i = 0; i < list.size(); i++) {
            IndustryType bean = list.get(i);
            if (bean.getId() == id) {
                city = bean.getName();
                break;
            }

        }
        return city;
    }


    /**
     * 搜索行业
     */
    public static String searchjob(int id) {
        String job = "";

//
        List<JobType> list = new ArrayList<JobType>();

        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getJobList())) {
            list = gson.fromJson(SettingUtils.getJobList(), new TypeToken<List<JobType>>() {
            }.getType());
        }


        list = getJobList(list);
        for (int i = 0; i < list.size(); i++) {
            JobType bean = list.get(i);
            if (bean.getId() == id) {
                job = bean.getName();
                break;
            }
        }
        return job;
    }

    /**
     * 搜索区
     */
    public static String searchArea(int id) {
        String area = "";

        List<AddressJson.ChildBeanX.ChildBean> list = new ArrayList<>();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(SettingUtils.getAreaList())) {
            list = gson.fromJson(SettingUtils.getAreaList(), new TypeToken<List<AddressJson.ChildBeanX.ChildBean>>() {
            }.getType());
        }
        for (int i = 0; i < list.size(); i++) {
            AddressJson.ChildBeanX.ChildBean bean = list.get(i);
            if (bean.getId() == id) {
                area = bean.getName();
                break;
            }

        }
        return area;
    }

    /**
     * 判断点击事件是否是连续点击
     *
     * @return
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000 * 3) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static Bitmap addFilePath(String path, int i_dp) {
        int dp = i_dp;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        int wratio = (int) Math.ceil(width / (float) dp);
        int hratio = (int) Math.ceil(height / (float) dp);
        int scale = 1;
        if (hratio > 1 || wratio > 1) {
            if (hratio > wratio)
                scale = hratio;
            else
                scale = wratio;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        // 设置加载边界信息为false
        o2.inJustDecodeBounds = false;
        Bitmap newbitmap = BitmapFactory.decodeFile(path, o2);

        return newbitmap;
    }


    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    //是否是数字
    public static boolean isAmount(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    //判断是否包含字母
    public static boolean judgeContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    public static void hidKeyBoard(Context context) {
        if (context == null) {
            return;
        }
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(context.INPUT_METHOD_SERVICE);
            if (((Activity) context).getCurrentFocus() != null) {
                if (((Activity) context).getCurrentFocus().getWindowToken() != null) {
                    final int flags = 2;
                    inputMethodManager.hideSoftInputFromWindow(
                            ((Activity) context).getCurrentFocus()
                                    .getWindowToken(), flags);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRendomNumber() {
        String number = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        System.out.println((int) ((Math.random() * 9 + 1) * 100000));
        return number;
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) ZxApplication.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    public static void showToast(final Activity activity, final String toastContent) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, toastContent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showLongToast(final Activity activity, final String toastContent) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, toastContent, Toast.LENGTH_LONG).show();
            }
        });
    }


    public static String getEducation(int id) {

        String education = "";
        switch (id) {
            case 10:
                education = "小学";
                break;
            case 20:
                education = "初中";
                break;
            case 30:
                education = "中专";
                break;
            case 40:
                education = "高中";
                break;
            case 50:
                education = "大专";
                break;
            case 60:
                education = " 本科";
                break;
            case 70:
                education = "硕士";
                break;
            case 80:
                education = "博士";
                break;
            case 90:
                education = "MBA";
                break;
        }
        return education;
    }

    public static String getConstellation(String constellation) {
        String education = "";
        switch (constellation) {
            case "白羊座":
                education = "0";
                break;
            case "金牛座":
                education = "1";
                break;
            case "双子座":
                education = "2";
                break;
            case "巨蟹座":
                education = "3";
                break;
            case "狮子座":
                education = "4";
                break;
            case "处女座":
                education = " 5";
                break;
            case "天秤座":
                education = "6";
                break;
            case "天蝎座":
                education = "7";
                break;
            case "射手座":
                education = "8";
                break;
            case "摩羯座":
                education = "9";
                break;
            case "水瓶座":
                education = "10";
                break;
            case "双鱼座":
                education = "11";
                break;
        }
        return education;
    }


    public static Integer getConstellationImage(int number) {
        int constellation = R.drawable.sheshou;
        switch (number) {
            case 0:
                constellation = R.drawable.baiy;
                break;
            case 1:
                constellation = R.drawable.jingn;
                break;
            case 2:
                constellation = R.drawable.shuangz;
                break;
            case 3:
                constellation = R.drawable.juxie;
                break;
            case 4:
                constellation = R.drawable.shizi;

                break;
            case 5:
                constellation = R.drawable.chunv;
                break;
            case 6:
                constellation = R.drawable.tianp;
                break;
            case 7:
                constellation = R.drawable.tianxie;
                break;
            case 8:
                constellation = R.drawable.sheshou;
                break;
            case 9:
                constellation = R.drawable.moxie;

                break;
            case 10:
                constellation = R.drawable.shuip;
                break;
            case 11:
                constellation = R.drawable.shuangyu;
                break;
        }
        return constellation;
    }

    public static String getPolitical(String constellation) {
        String political = "";
        switch (constellation) {
            case "普通公民":
                political = "0";
                break;
            case "中共党员":
                political = "1";
                break;
            case "共青团员":
                political = "2";
                break;
            case "民主党派人士":
                political = "3";
                break;
            case "无党派民主人士":
                political = "4";
                break;

        }
        return political;
    }

    public static String getEducationId(String constellation) {

        String education = "";
        switch (constellation) {
            case "小学":
                education = "10";
                break;
            case "初中":
                education = "20";
                break;
            case "中专":
                education = "30";
                break;
            case "高中":
                education = "40";
                break;
            case "大专":
                education = "50";
                break;
            case "本科":
                education = "60";
                break;
            case "硕士":
                education = "70";
                break;
            case "博士":
                education = "80";
                break;
            case "MBA":
                education = "90";
                break;

        }
        return education;
    }


    private static List<IndustryType> getIndustryTypeList(List<IndustryType> dataList) {
        List<IndustryType> list = new ArrayList<IndustryType>();
        for (int i = 0; i < dataList.size(); i++) {
            IndustryType industryType = dataList.get(i);
            list.add(industryType);
            for (int j = 0; j < industryType.getChild().size(); j++) {
                IndustryType bean = industryType.getChild().get(j);
                list.add(bean);
            }
        }
        return list;
    }


    private static List<JobType> getJobList(List<JobType> dataList) {
        List<JobType> list = new ArrayList<JobType>();
        for (int i = 0; i < dataList.size(); i++) {
            JobType jobType = dataList.get(i);
            list.add(jobType);
            for (int j = 0; j < jobType.getChild().size(); j++) {
                JobType bean = jobType.getChild().get(j);
                list.add(bean);
                for (int k = 0; k < bean.getChild().size(); k++) {
                    JobType bean1 = bean.getChild().get(k);
                    list.add(bean1);
                }
            }
        }
        return list;
    }


    // 加密
    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String getFromBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
