package com.zhixing.work.zhixin.http.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 项目名称:HardTask
 * Created by lovezh
 * CreatedData: on 2018/4/17.
 */

public interface getToken {
    @GET
    Observable<UpdateTokenEntity> GetToken(@Query("token") String token);

}
