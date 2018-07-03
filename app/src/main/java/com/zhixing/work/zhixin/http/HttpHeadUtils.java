package com.zhixing.work.zhixin.http;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/12.
 */

public  class HttpHeadUtils {

    public  static String accessId="7568512625";
    public static String accessSecret="a02f3025-9c9b-4737-897d-7e94242797a0";

    //获取时间搓
    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        Calendar calendar = Calendar.getInstance();
        int offset = calendar.get(Calendar.ZONE_OFFSET);
        calendar.add(Calendar.MILLISECOND, -offset);
        Date date = calendar.getTime();
        String timer = sdf.format(date);
        return timer;
    }

    //获取随机数
    public static String getRandom(){

        float random=new Random().nextFloat();
        String randoms = String.valueOf(random);
        return randoms;

    }



}
