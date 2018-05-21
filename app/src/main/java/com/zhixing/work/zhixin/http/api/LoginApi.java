package com.zhixing.work.zhixin.http.api;




import com.zhixing.work.zhixin.bean.PutPersonlDataEntity;
import com.zhixing.work.zhixin.bean.UpdateBean;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/8.
 */
 //

public interface LoginApi {
//    //是否注册
//    @GET("/User")
//    Observable<IsRegistEntity> IsRegist(@Query("PhoneNum") String PhoneNum, @Query("Role") String Role);
//   //获取注册短信验证码
//
//    @GET("/SmsCode")
//    Observable<RegistCodeEntity> getRegistCode(@Query("PhoneNum") String PhoneNum, @Query("UserRoleEnum") String UserRoleEnum, @Query("SmsCodeTypeEnum") String SmsCodeTypeEnum);
//
//
//    //去注册
//    @FormUrlEncoded
//    @POST("/User")
//    Observable<RegistResponseEntity> goRegist(@Field("PhoneNum") String PhoneNum,
//                                              @Field("Password") String Password,
//                                              @Field("Role") String Role,
//                                              @Field("VerifyCode") String VerifyCode
//    );
//    //登录
//    @FormUrlEncoded
//     @POST("/token")
//    Observable<LoginResponseEntity> goLogin(@Field("PhoneNum") String PhoneNum,
//                                            @Field("Password") String Password,
//                                            @Field("UserRoleEnum") String Role);

    //更新密码
    @HTTP(method ="PATCH", path = "/User", hasBody = true)
    Observable<PutPersonlDataEntity> UpdatePWD(@Body UpdateBean updateBean);

}
