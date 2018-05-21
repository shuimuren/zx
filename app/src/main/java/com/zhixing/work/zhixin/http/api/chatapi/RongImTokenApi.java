package com.zhixing.work.zhixin.http.api.chatapi;




import com.zhixing.work.zhixin.bean.UserTokenEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/8.
 */
 //融云请求用户Token

public interface RongImTokenApi {

    @FormUrlEncoded
    @POST("/User/getToken.json")
    Observable<UserTokenEntity> getRonUserToken(@Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri);

}
