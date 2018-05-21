package com.zhixing.work.zhixin.http.api.cardapi;




import com.zhixing.work.zhixin.bean.ResponseEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/17.
 */

public interface CardApi {

   //上传基本资料
    @FormUrlEncoded
    @POST("/PersonalInfo")
    Observable<ResponseEntity> PutData(@Header("token") String token, @Field("RealName") String RealName,
                                       @Field("Sex") String Sex,
                                       @Field("Email") String Email,
                                       @Field("UserIdentity") String UserIdentity
    );
   //上传完整资料
   @FormUrlEncoded
    @PUT("/personalinfo")
   Observable<ResponseEntity> PutExData(@Header("token") String token, @Field("NickName") String NickName, @Field("Birthday") String Birthday, @Field("Constellation") String Constellation,
                                        @Field("IdCard") String IdCard, @Field("StudyAbroad") String StudyAbroad, @Field("NativePlaceProvince") String NativePlaceProvince, @Field("NativePlaceCity") String NativePlaceCity,
                                        @Field("Province") String Province, @Field("City") String City, @Field("District") String District, @Field("Motto") String Motto
   );
//   //获取卡牌正面信息
//    @GET("/card")
//    Observable<PersonDataResponse> GetCardData(@Header("token") String token, @Query("frontOrBack") String front);
//   //上传学历资料
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @POST("/EducationBackground")
//    Observable<ResponseEntity> PutEducation(@Header("token") String token, @Body RequestBody body);
//
//   //上传工作资料
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @POST("/WorkBackground")
//    Observable<ResponseEntity> PutJob(@Header("token") String token, @Body RequestBody body);
//   //获取卡牌背面信息
//    @GET("/card")
//    Observable<CardBackDataEntity> GetCardBackData(@Header("token") String token, @Query("frontOrBack") String front, @Query("opt") String opt);

}
