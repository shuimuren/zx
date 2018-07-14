package com.zhixing.work.zhixin.network;import com.zhixing.work.zhixin.bean.AuthenticateBody;import com.zhixing.work.zhixin.bean.CertificationBody;import com.zhixing.work.zhixin.bean.EducationBody;import com.zhixing.work.zhixin.network.response.AuthenticateListResult;import com.zhixing.work.zhixin.network.response.EvaluateResult;import com.zhixing.work.zhixin.network.response.ExpectedJobResult;import com.zhixing.work.zhixin.network.response.StsTokenResult;import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;import com.zhixing.work.zhixin.requestbody.ExpectedJobBodyBean;import retrofit2.Call;import retrofit2.http.Body;import retrofit2.http.GET;import retrofit2.http.PUT;import retrofit2.http.Query;/** * */public interface SpaService {    @GET(RequestConstant.GET_OSS)    Call<StsTokenResult> getStsToken();    @GET(RequestConstant.AUTHENTICATES)    Call<AuthenticateListResult> getAuthenticates();    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitAuthenticate(@Body AuthenticateBody body);    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitEducationAuthenticate(@Body EducationBody body);    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitCertificationAuthenticate(@Body CertificationBody body);    @GET(RequestConstant.GET_EVALUATE_INFO)    Call<EvaluateResult> evaluateResult(@Query(RequestConstant.KEY_FRONT_OR_BACK) String id,                                        @Query(RequestConstant.KEY_OPT) String opt);    @PUT(RequestConstant.RESUME_EXPECT_JOB)    Call<ExpectedJobResult> updateExpectedJob(@Query(RequestConstant.KEY_RESUME_ID) String resumeId,                                              @Body ExpectedJobBodyBean[] body);}