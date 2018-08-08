package com.zhixing.work.zhixin.network;import com.zhixing.work.zhixin.bean.AuthenticateBody;import com.zhixing.work.zhixin.bean.CertificationBody;import com.zhixing.work.zhixin.bean.EducationBody;import com.zhixing.work.zhixin.bean.UpDateInfoBody;import com.zhixing.work.zhixin.network.response.AddCompanyHistoryEventResult;import com.zhixing.work.zhixin.network.response.AddDepartmentResult;import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;import com.zhixing.work.zhixin.network.response.AttendanceResult;import com.zhixing.work.zhixin.network.response.AttendanceRuleResult;import com.zhixing.work.zhixin.network.response.AuthenticateListResult;import com.zhixing.work.zhixin.network.response.CheckStaffRuleResult;import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;import com.zhixing.work.zhixin.network.response.CompanyCardInfoResult;import com.zhixing.work.zhixin.network.response.CompanyCertificationStatusResult;import com.zhixing.work.zhixin.network.response.CreateAttendanceRuleResult;import com.zhixing.work.zhixin.network.response.DeleteAttendanceRuleResult;import com.zhixing.work.zhixin.network.response.DeleteDepartmentResult;import com.zhixing.work.zhixin.network.response.DepartmentInviteResult;import com.zhixing.work.zhixin.network.response.DepartmentListResult;import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;import com.zhixing.work.zhixin.network.response.DepartmentSettingInfoResult;import com.zhixing.work.zhixin.network.response.EditAttendanceMemberResult;import com.zhixing.work.zhixin.network.response.EditCompanyHistoryEventResult;import com.zhixing.work.zhixin.network.response.EvaluateResult;import com.zhixing.work.zhixin.network.response.ExpectedJobResult;import com.zhixing.work.zhixin.network.response.JudgeTelephoneUsableResult;import com.zhixing.work.zhixin.network.response.LoginResult;import com.zhixing.work.zhixin.network.response.PersonalCardBasicInfoResult;import com.zhixing.work.zhixin.network.response.PersonalCardInfoResult;import com.zhixing.work.zhixin.network.response.RegisterResult;import com.zhixing.work.zhixin.network.response.SmsCodeResult;import com.zhixing.work.zhixin.network.response.StaffAttendanceRecordResult;import com.zhixing.work.zhixin.network.response.StatisticsDailyDetailResult;import com.zhixing.work.zhixin.network.response.StatisticsDailyResult;import com.zhixing.work.zhixin.network.response.StatisticsMonthDetailResult;import com.zhixing.work.zhixin.network.response.StatisticsMonthResult;import com.zhixing.work.zhixin.network.response.StsTokenResult;import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;import com.zhixing.work.zhixin.network.response.UpdateChildDepartmentResult;import com.zhixing.work.zhixin.network.response.UpdateDepartmentResult;import com.zhixing.work.zhixin.network.response.UpdatePasswordResult;import com.zhixing.work.zhixin.network.response.UploadCompanyAvatarResult;import com.zhixing.work.zhixin.network.response.UploadPersonalAvatarResult;import com.zhixing.work.zhixin.network.response.WifiListResult;import com.zhixing.work.zhixin.requestbody.AttendanceRuleBody;import com.zhixing.work.zhixin.requestbody.AvatarBody;import com.zhixing.work.zhixin.requestbody.CompanyAvatarBody;import com.zhixing.work.zhixin.requestbody.DepartmentBody;import com.zhixing.work.zhixin.requestbody.EditCompanyHistoryEventBody;import com.zhixing.work.zhixin.requestbody.ExpectedJobBodyBean;import com.zhixing.work.zhixin.requestbody.StaffAttendanceBody;import retrofit2.Call;import retrofit2.http.Body;import retrofit2.http.DELETE;import retrofit2.http.Field;import retrofit2.http.FormUrlEncoded;import retrofit2.http.GET;import retrofit2.http.PATCH;import retrofit2.http.POST;import retrofit2.http.PUT;import retrofit2.http.Query;/** * */public interface SpaService {    @GET(RequestConstant.GET_OSS)    Call<StsTokenResult> getStsToken();    @GET(RequestConstant.AUTHENTICATES)    Call<AuthenticateListResult> getAuthenticates();    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitAuthenticate(@Body AuthenticateBody body);    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitEducationAuthenticate(@Body EducationBody body);    @PUT(RequestConstant.AUTHENTICATES)    Call<SubmitAuthenticateResult> submitCertificationAuthenticate(@Body CertificationBody body);    @GET(RequestConstant.GET_EVALUATE_INFO)    Call<EvaluateResult> evaluateResult(@Query(RequestConstant.KEY_FRONT_OR_BACK) String id,                                        @Query(RequestConstant.KEY_OPT) String opt);    @PUT(RequestConstant.RESUME_EXPECT_JOB)    Call<ExpectedJobResult> updateExpectedJob(@Query(RequestConstant.KEY_RESUME_ID) String resumeId,                                              @Body ExpectedJobBodyBean[] body);    @GET(RequestConstant.GET_EVALUATE_INFO)    Call<PersonalCardBasicInfoResult> getCardBasicInfo(@Query(RequestConstant.KEY_FRONT_OR_BACK) String id,                                                       @Query(RequestConstant.KEY_OPT) String opt);    @POST(RequestConstant.GO_LOGIN)    @FormUrlEncoded    Call<LoginResult> doLogin(@Field(RequestConstant.KEY_PHONE_NUMBER) String PhoneNum,                              @Field(RequestConstant.KEY_PASSWORD) String PassWord,                              @Field(RequestConstant.KEY_USER_ROLE_ENUM) String UserRoleEnum);    @GET(RequestConstant.GET_REGISTER_CODE)    Call<SmsCodeResult> getVerificationCode(@Query(RequestConstant.KEY_PHONE_NUMBER) String PhoneNum,                                            @Query(RequestConstant.KEY_USER_ROLE_ENUM) String UserRoleEnum,                                            @Query(RequestConstant.KEY_SMS_CODE_TYPE_ENUM) String SmsCodeTypeEnum);    @PATCH(RequestConstant.UPDATE_PASSWORD)    Call<UpdatePasswordResult> upDateUserPassword(@Body UpDateInfoBody body);    @POST(RequestConstant.GO_REGISTER)    @FormUrlEncoded    Call<RegisterResult> userRegister(@Field(RequestConstant.KEY_PHONE_NUMBER) String PhoneNum,                                      @Field(RequestConstant.KEY_PASSWORD) String PassWord,                                      @Field(RequestConstant.KEY_ROLE) String Role,                                      @Field(RequestConstant.KEY_VERIFY_CODE) String VerifyCode);    @GET(RequestConstant.GO_REGISTER)    Call<JudgeTelephoneUsableResult> judgeTelephoneUsable(@Query(RequestConstant.KEY_PHONE_NUMBER) String PhoneNum,                                                          @Query(RequestConstant.KEY_ROLE) String Role);    @GET(RequestConstant.COMPANY_CERTIFICATION_STATUS)    Call<CompanyCertificationStatusResult> getCompanyCertificationStatus();    @GET(RequestConstant.DEPARTMENT_MEMBER)    Call<AllDepartmentMemberResult> getAllDepartmentMember();    @DELETE(RequestConstant.DEPARTMENT)    Call<DeleteDepartmentResult> deleteDepartment(@Query(RequestConstant.KEY_DEPARTMENT_ID) String departmentId);    @GET(RequestConstant.INVITE)    Call<DepartmentInviteResult> departmentInvite(@Query(RequestConstant.KEY_DEPARTMENT_ID) String departmentId);    @PATCH(RequestConstant.DEPARTMENT)    Call<UpdateDepartmentResult> updateDepartment(@Query(RequestConstant.KEY_COMPANY_ID) String companyId);    @GET(RequestConstant.DEPARTMENT)    Call<DepartmentListResult> getDepartmentList();    @GET(RequestConstant.DEPARTMENT_CHILD)    Call<ChildDepartmentResult> getChildDepartment(@Query(RequestConstant.KEY_DEPARTMENT_ID) String departmentId);    @GET(RequestConstant.STAFF)    Call<DepartmentMemberInfoResult> getDepartmentMemberInfo(@Query(RequestConstant.KEY_DEPARTMENT_ID) String departmentId);    @POST(RequestConstant.DEPARTMENT)    @FormUrlEncoded    Call<AddDepartmentResult> addDepartment(@Field(RequestConstant.KEY_PARENT_ID) String ParentId,                                            @Field(RequestConstant.KEY_NAME) String name);    @PUT(RequestConstant.DEPARTMENT)    Call<UpdateChildDepartmentResult> updateChildDepartment(@Body DepartmentBody body);    @GET(RequestConstant.GET_DEPARTMENT_SETTING_INFO)    Call<DepartmentSettingInfoResult> getDepartmentSettingInfo(@Query(RequestConstant.KEY_DEPARTMENT_ID) String departmentId);    @GET(RequestConstant.GET_CARD_INFO)    Call<PersonalCardInfoResult> getPersonalCardInfo(@Query(RequestConstant.KEY_FRONT_OR_BACK) String frontOrBack);    @GET(RequestConstant.COMPANY_CARD)    Call<CompanyCardInfoResult> getCompanyCardInfo();    @PATCH(RequestConstant.AVATAR)    Call<UploadPersonalAvatarResult> uploadPersonalAvatar(@Body AvatarBody body);    @PATCH(RequestConstant.UPDATE_COMPANY_AVATAR)    Call<UploadCompanyAvatarResult> uploadCompanyAvatar(@Body CompanyAvatarBody body);    @POST(RequestConstant.COMPANY_HISTORY)    @FormUrlEncoded    Call<AddCompanyHistoryEventResult> addCompanyHistoryEvent(@Field(RequestConstant.KEY_NAME) String Name,                                                              @Field(RequestConstant.KEY_DATE_DATE) String Date,                                                              @Field(RequestConstant.KEY_INTRO) String Intro,                                                              @Field(RequestConstant.KEY_IMAGE) String Image);    @PUT(RequestConstant.COMPANY_HISTORY)    Call<EditCompanyHistoryEventResult> editCompanyHistoryEvent(@Body EditCompanyHistoryEventBody body);    @GET(RequestConstant.GET_USER_ATTENDANCE_RECORD)    Call<StaffAttendanceRecordResult> staffAttendanceRecord(@Query(RequestConstant.KEY_STAFF_ID) String staffId,                                                            @Query(RequestConstant.KEY_START_DATE) String startDate,                                                            @Query(RequestConstant.KEY_END_DATE) String endDate);    @GET(RequestConstant.GET_USER_ATTENDANCE_WIFI_LIST)    Call<WifiListResult> getWifiList();    @POST(RequestConstant.STAFF_ATTENDANCE)    Call<AttendanceResult> staffAttendance(@Body StaffAttendanceBody body);    @GET(RequestConstant.GET_ATTENDANCE_RECORD_STATISTICS_DAILY)    Call<StatisticsDailyResult> statisticsDaily(@Query(RequestConstant.KEY_DATE_DATE) String date);    @GET(RequestConstant.GET_ATTENDANCE_STATISTICS_DAILY_DETAIL)    Call<StatisticsDailyDetailResult> statisticsDailyDetail(@Query(RequestConstant.KEY_DATE_DATE) String date,                                                            @Query(RequestConstant.KEY_CLOCK_STATUS) String clockStatus,                                                            @Query(RequestConstant.KEY_PAGE_INDEX) String pageIndex,                                                            @Query(RequestConstant.KEY_ROWS_COUNT) String rowsCount);    @GET(RequestConstant.GET_ATTENDANCE_RECORD_STATISTICS_MONTH)    Call<StatisticsMonthResult> statisticsMonth(@Query(RequestConstant.KEY_DATE_DATE) String date);    @GET(RequestConstant.GET_ATTENDANCE_STATISTICS_MONTH_DETAIL)    Call<StatisticsMonthDetailResult> statisticsMonthDetail(@Query(RequestConstant.KEY_DATE_DATE) String date,                                                            @Query(RequestConstant.KEY_CLOCK_STATUS) String clockStatus,                                                            @Query(RequestConstant.KEY_PAGE_INDEX) String pageIndex,                                                            @Query(RequestConstant.KEY_ROWS_COUNT) String rowsCount);    @GET(RequestConstant.GET_STAFF_ATTENDANCE_RECORD_MONTH)    Call<AttendanceRecordMonthResult> personalRecordMonth(@Query(RequestConstant.KEY_STAFF_ID) String staffId,                                                          @Query(RequestConstant.KEY_DATE_DATE) String date);    @POST(RequestConstant.CREATE_ATTENDANCE_RULE)    Call<CreateAttendanceRuleResult> createAttendanceRule(@Body AttendanceRuleBody body);    @PUT(RequestConstant.EDIT_ATTENDANCE_RULE)    Call<CreateAttendanceRuleResult> editAttendanceRule(@Body AttendanceRuleBody body);    @GET(RequestConstant.GET_ATTENDANCE_RULE)    Call<AttendanceRuleResult> getAttendanceRule();    @DELETE(RequestConstant.DELETE_ATTENDANCE_RULE)    Call<DeleteAttendanceRuleResult> deleteAttendanceRule(@Query(RequestConstant.KEY_ID) String id);    @PUT(RequestConstant.EDIT_ATTENDANCE_MEMBER)    Call<EditAttendanceMemberResult> editAttendanceMember(@Query(RequestConstant.KEY_ID)String id,                                                          @Body Integer[] body);    @GET(RequestConstant.CHECK_STAFF_ATTENDANCE_RULE)    Call<CheckStaffRuleResult> checkStaffRule(@Query(RequestConstant.KEY_ATTENDANCE_RULE_ID)String attendanceRuleId,                                              @Query(RequestConstant.KEY_STAFF_IDS) Integer[] integers);}