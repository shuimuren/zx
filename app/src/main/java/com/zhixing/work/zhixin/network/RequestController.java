package com.zhixing.work.zhixin.network;import android.os.Message;import com.zhixing.work.zhixin.bean.AuthenticateBody;import com.zhixing.work.zhixin.bean.CertificationBody;import com.zhixing.work.zhixin.bean.EducationBody;import com.zhixing.work.zhixin.bean.UpDateInfoBody;import com.zhixing.work.zhixin.msgctrl.AbstractController;import com.zhixing.work.zhixin.msgctrl.MsgDef;import com.zhixing.work.zhixin.msgctrl.RxBus;import com.zhixing.work.zhixin.network.response.AddCompanyHistoryEventResult;import com.zhixing.work.zhixin.network.response.AddDepartmentResult;import com.zhixing.work.zhixin.network.response.AllDepartmentMemberResult;import com.zhixing.work.zhixin.network.response.AttendanceRecordMonthResult;import com.zhixing.work.zhixin.network.response.AttendanceResult;import com.zhixing.work.zhixin.network.response.AttendanceRuleResult;import com.zhixing.work.zhixin.network.response.AuthenticateListResult;import com.zhixing.work.zhixin.network.response.CheckStaffRuleResult;import com.zhixing.work.zhixin.network.response.ChildDepartmentResult;import com.zhixing.work.zhixin.network.response.CompanyCardInfoResult;import com.zhixing.work.zhixin.network.response.CompanyCertificationStatusResult;import com.zhixing.work.zhixin.network.response.CreateAttendanceRuleResult;import com.zhixing.work.zhixin.network.response.DeleteAttendanceRuleResult;import com.zhixing.work.zhixin.network.response.DeleteDepartmentResult;import com.zhixing.work.zhixin.network.response.DepartmentInviteResult;import com.zhixing.work.zhixin.network.response.DepartmentListResult;import com.zhixing.work.zhixin.network.response.DepartmentMemberInfoResult;import com.zhixing.work.zhixin.network.response.DepartmentSettingInfoResult;import com.zhixing.work.zhixin.network.response.EditAttendanceMemberResult;import com.zhixing.work.zhixin.network.response.EditCompanyHistoryEventResult;import com.zhixing.work.zhixin.network.response.EvaluateResult;import com.zhixing.work.zhixin.network.response.ExpectedJobResult;import com.zhixing.work.zhixin.network.response.JudgeTelephoneUsableResult;import com.zhixing.work.zhixin.network.response.LoginResult;import com.zhixing.work.zhixin.network.response.PersonalCardBasicInfoResult;import com.zhixing.work.zhixin.network.response.PersonalCardInfoResult;import com.zhixing.work.zhixin.network.response.RegisterResult;import com.zhixing.work.zhixin.network.response.SmsCodeResult;import com.zhixing.work.zhixin.network.response.StaffAttendanceRecordResult;import com.zhixing.work.zhixin.network.response.StatisticsDailyDetailResult;import com.zhixing.work.zhixin.network.response.StatisticsDailyResult;import com.zhixing.work.zhixin.network.response.StatisticsMonthDetailResult;import com.zhixing.work.zhixin.network.response.StatisticsMonthResult;import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;import com.zhixing.work.zhixin.network.response.UpdateChildDepartmentResult;import com.zhixing.work.zhixin.network.response.UpdateDepartmentResult;import com.zhixing.work.zhixin.network.response.UpdatePasswordResult;import com.zhixing.work.zhixin.network.response.UploadCompanyAvatarResult;import com.zhixing.work.zhixin.network.response.UploadPersonalAvatarResult;import com.zhixing.work.zhixin.network.response.WifiListResult;import com.zhixing.work.zhixin.requestbody.AttendanceRuleBody;import com.zhixing.work.zhixin.requestbody.AvatarBody;import com.zhixing.work.zhixin.requestbody.CompanyAvatarBody;import com.zhixing.work.zhixin.requestbody.DepartmentBody;import com.zhixing.work.zhixin.requestbody.EditCompanyHistoryEventBody;import com.zhixing.work.zhixin.requestbody.ExpectedJobBodyBean;import com.zhixing.work.zhixin.requestbody.StaffAttendanceBody;import java.util.Map;import retrofit2.Call;/** * Created by lhj on 18/5/7 */public class RequestController extends AbstractController {    public RequestController() {        super();    }    private SpaService getSpaService() {        return RetrofitServiceFactory.getSpaService();    }    @Override    public boolean handleMessage(Message msg) {        switch (msg.what) {            case MsgDef.MSG_DEF_PERSONAL_AUTHENTICATES:                getPersonalAuthenticateList();                break;            case MsgDef.MSG_DEF_AUTHENTICATE_SUBMIT:                submitAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_EDUCATION_AUTHENTICATE_SUBMIT:                submitEducationAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_CERTIFICATION_AUTHENTICATE_SUBMIT:                submitCertificationAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_EVALUATE_INFO:                getEvaluateInfo();                break;            case MsgDef.MSG_DEF_UPDATE_EXPECTED_INFO:                doUpdateExpectedInfo((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_PERSONAL_CARD_BASIC_INFO:                getPersonalCardBasicInfo();                break;            case MsgDef.MSG_DEF_LOGIN:                userLogin((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_VERIFICATION_CODE:                getVerificationCode((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_UPDATE_PASSWORD:                updatePassword((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_REGISTER:                userRegister((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_TELEPHONE_USABLE:                judgeTelephoneUsable((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_COMPANY_CERTIFICATION_STATUS:                getCompanyCertificationStatus();                break;            case MsgDef.MSG_DEF_GET_ALL_DEPARTMENT_MEMBER:                getAllDepartmentMember();                break;            case MsgDef.MSG_DEF_DELETE_DEPARTMENT:                deleteDepartment(msg.obj.toString());                break;            case MsgDef.MSG_DEF_DEPARTMENT_INVITE:                departmentInvite(msg.obj.toString());                break;            case MsgDef.MSG_DEF_UPDATE_DEPARTMENT:                updateDepartment(msg.obj.toString());                break;            case MsgDef.MSG_DEF_GET_ALL_DEPARTMENT:                getAllDepartment();                break;            case MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT:                getChildDepartment(msg.obj.toString());                break;            case MsgDef.MSG_DEF_GET_CHILD_DEPARTMENT_MEMBER:                getDepartmentMemberInfo(msg.obj.toString());                break;            case MsgDef.MSG_DEF_ADD_DEPARTMENT:                addDepartment((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_UPDATE_CHILD_DEPARTMENT:                updateChildDepartment((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_DEPARTMENT_SETTING_INFO:                getDepartmentSettingInfo(msg.obj.toString());                break;            case MsgDef.MSG_DEF_GET_COMPANY_CARD_INFO:                getCompanyCardInfo();                break;            case MsgDef.MSG_DEF_GET_PERSONAL_CARD_INFO:                getPersonalCardInfo();                break;            case MsgDef.MSG_DEF_UPLOAD_AVATAR:                uploadAvatar((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_COMPANY_UPLOAD_AVATAR:                uploadCompanyAvatar((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_ADD_COMPANY_EVENT:                addCompanyEvent((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_EDIT_COMPANY_ENENT:                editCompanyEvent((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_USER_ATTENDANCE_RECORD:                staffAttendanceRecord((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_USER_ATTENDANCE_WIFI_LIST:                getWifiList();                break;            case MsgDef.MSG_DEF_STAFF_ATTENDANCE:                attendanceRecord((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_ATTENDANCE_RECORD_STATISTICS_DAILY:                statisticsDaily(msg.obj.toString());                break;            case MsgDef.MSG_DEF_GET_ATTENDANCE_STATISTICS_DAILY_DETAIL:                statisticsDailyDetail((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_ATTENDANCE_RECORD_STATISTICS_MONTH:                statisticsMonth(msg.obj.toString());                break;            case MsgDef.MSG_DEF_GET_ATTENDANCE_STATISTICS_MONTH_DETAIL:                statisticsMonthDetail((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_STAFF_ATTENDANCE_RECORD_MONTH:                attendanceRecordMonth((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_CREATE_ATTENDANCE_RULE:                createAttendanceRule((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_EDIT_ATTENDANCE_RULE:                editAttendanceRule((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_CHECK_STAFF_ATTENDANCE_RULE:                checkStaffRule((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_ATTENDANCE_RULE:                getAttendanceRule();                break;            case MsgDef.MSG_DEF_DELETE_ATTENDANCE_RULE:                deleteAttendanceRule(msg.obj.toString());                break;            case MsgDef.MSG_DEF_EDIT_ATTENDANCE_MEMBER:                editAttendanceMember((Map<String, Object>) msg.obj);                break;        }        return true;    }    /**     * 获取公司下所有部门员工     * AllDepartmentMemberResult     */    public void getAllDepartmentMember() {        Call<AllDepartmentMemberResult> call = getSpaService().getAllDepartmentMember();        call.enqueue(new TokenCheckedCallback<AllDepartmentMemberResult>() {            @Override            protected void postResult(AllDepartmentMemberResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 删除部门     *     * @param departmentId DeleteDepartmentResult     */    private void deleteDepartment(String departmentId) {        Call<DeleteDepartmentResult> call = getSpaService().deleteDepartment(departmentId);        call.enqueue(new TokenCheckedCallback<DeleteDepartmentResult>() {            @Override            protected void postResult(DeleteDepartmentResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 邀请进入该部门     *     * @param departmentId 部门Id     */    private void departmentInvite(String departmentId) {        Call<DepartmentInviteResult> call = getSpaService().departmentInvite(departmentId);        call.enqueue(new TokenCheckedCallback<DepartmentInviteResult>() {            @Override            protected void postResult(DepartmentInviteResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 更换组织架构     *     * @param companyId 要切换的公司Id     */    private void updateDepartment(String companyId) {        Call<UpdateDepartmentResult> call = getSpaService().updateDepartment(companyId);        call.enqueue(new TokenCheckedCallback<UpdateDepartmentResult>() {            @Override            protected void postResult(UpdateDepartmentResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取账号下所有组织架构列表     * DepartmentListResult     */    public void getAllDepartment() {        Call<DepartmentListResult> call = getSpaService().getDepartmentList();        call.enqueue(new TokenCheckedCallback<DepartmentListResult>() {            @Override            protected void postResult(DepartmentListResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取子部门     *     * @param departmentId 部门ID     */    private void getChildDepartment(String departmentId) {        Call<ChildDepartmentResult> call = getSpaService().getChildDepartment(departmentId);        call.enqueue(new TokenCheckedCallback<ChildDepartmentResult>() {            @Override            protected void postResult(ChildDepartmentResult result) {                result.setDepartmentId(departmentId);                RxBus.getInstance().post(result);            }        });    }    /**     * DepartmentMemberInfoResult     *     * @param departmentId 组织ID     *                     获取部门下员工列表     */    private void getDepartmentMemberInfo(String departmentId) {        Call<DepartmentMemberInfoResult> call = getSpaService().getDepartmentMemberInfo(departmentId);        call.enqueue(new TokenCheckedCallback<DepartmentMemberInfoResult>() {            @Override            protected void postResult(DepartmentMemberInfoResult result) {                result.setDepartmentId(departmentId);                RxBus.getInstance().post(result);            }        });    }    /**     * 添加部门     *     * @param params AddDepartment     */    private void addDepartment(Map<String, String> params) {        Call<AddDepartmentResult> call = getSpaService().addDepartment(params.get(RequestConstant.KEY_PARENT_ID),                params.get(RequestConstant.KEY_NAME));        call.enqueue(new TokenCheckedCallback<AddDepartmentResult>() {            @Override            protected void postResult(AddDepartmentResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 修改部门     *     * @param param DepartmentBody     *              UpdateChildDepartmentResult     */    private void updateChildDepartment(Map<String, Object> param) {        Call<UpdateChildDepartmentResult> call = getSpaService().updateChildDepartment((DepartmentBody) param.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<UpdateChildDepartmentResult>() {            @Override            protected void postResult(UpdateChildDepartmentResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取设置信息     *     * @param departmentId     */    private void getDepartmentSettingInfo(String departmentId) {        Call<DepartmentSettingInfoResult> call = getSpaService().getDepartmentSettingInfo(departmentId);        call.enqueue(new TokenCheckedCallback<DepartmentSettingInfoResult>() {            @Override            protected void postResult(DepartmentSettingInfoResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 登录     *     * @param params     */    private void userLogin(Map<String, String> params) {        Call<LoginResult> call = getSpaService().doLogin(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_PASSWORD), params.get(RequestConstant.KEY_USER_ROLE_ENUM));        call.enqueue(new TokenCheckedCallback<LoginResult>() {            @Override            protected void postResult(LoginResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                super.postError(errorMessage);                LoginResult result = new LoginResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 更新密码     *     * @param params     */    private void updatePassword(Map<String, Object> params) {        Call<UpdatePasswordResult> call = getSpaService().upDateUserPassword((UpDateInfoBody) params.get(RequestConstant.KEY_UPDATE_PSAAWORD_BODY));        call.enqueue(new TokenCheckedCallback<UpdatePasswordResult>() {            @Override            protected void postResult(UpdatePasswordResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取验证码     *     * @param params     */    private void getVerificationCode(Map<String, String> params) {        Call<SmsCodeResult> call = getSpaService().getVerificationCode(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_USER_ROLE_ENUM),                params.get(RequestConstant.KEY_SMS_CODE_TYPE_ENUM));        call.enqueue(new TokenCheckedCallback<SmsCodeResult>() {            @Override            protected void postResult(SmsCodeResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                SmsCodeResult result = new SmsCodeResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 注册     *     * @param params     */    private void userRegister(Map<String, String> params) {        Call<RegisterResult> call = getSpaService().userRegister(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_PASSWORD), params.get(RequestConstant.KEY_ROLE),                params.get(RequestConstant.KEY_VERIFY_CODE));        call.enqueue(new TokenCheckedCallback<RegisterResult>() {            @Override            protected void postResult(RegisterResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * @param params     */    private void judgeTelephoneUsable(Map<String, String> params) {        Call<JudgeTelephoneUsableResult> call = getSpaService().judgeTelephoneUsable(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_ROLE));        call.enqueue(new TokenCheckedCallback<JudgeTelephoneUsableResult>() {            @Override            protected void postResult(JudgeTelephoneUsableResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                JudgeTelephoneUsableResult result = new JudgeTelephoneUsableResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.setContent(true);                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 获取认证列表     */    public void getPersonalAuthenticateList() {        Call<AuthenticateListResult> call = getSpaService().getAuthenticates();        call.enqueue(new TokenCheckedCallback<AuthenticateListResult>() {            @Override            protected void postResult(AuthenticateListResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交个人认证     *     * @param params     */    public void submitAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitAuthenticate((AuthenticateBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交学历认证     *     * @param params     */    private void submitEducationAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitEducationAuthenticate((EducationBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交证书认证     *     * @param params     */    private void submitCertificationAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitCertificationAuthenticate((CertificationBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取资质信息     */    private void getEvaluateInfo() {        Call<EvaluateResult> call = getSpaService().evaluateResult(RequestConstant.KEY_BACK, RequestConstant.KEY_EVALUATE);        call.enqueue(new TokenCheckedCallback<EvaluateResult>() {            @Override            protected void postResult(EvaluateResult result) {                RxBus.getInstance().post(result);            }        });    }    private void doUpdateExpectedInfo(Map<String, Object> params) {        Call<ExpectedJobResult> call = getSpaService().updateExpectedJob(String.valueOf(params.get(RequestConstant.KEY_RESUME_ID)),                (ExpectedJobBodyBean[]) params.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<ExpectedJobResult>() {            @Override            protected void postResult(ExpectedJobResult result) {                RxBus.getInstance().post(result);            }        });    }    public void getPersonalCardBasicInfo() {        Call<PersonalCardBasicInfoResult> call = getSpaService().getCardBasicInfo(RequestConstant.KEY_BACK, RequestConstant.KEY_BASIC_INFO);        call.enqueue(new TokenCheckedCallback<PersonalCardBasicInfoResult>() {            @Override            protected void postResult(PersonalCardBasicInfoResult result) {                RxBus.getInstance().post(result);            }        });    }    //企业认证状态    public void getCompanyCertificationStatus() {        Call<CompanyCertificationStatusResult> call = getSpaService().getCompanyCertificationStatus();        call.enqueue(new TokenCheckedCallback<CompanyCertificationStatusResult>() {            @Override            protected void postResult(CompanyCertificationStatusResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取公司卡牌正面信息     */    public void getCompanyCardInfo() {        Call<CompanyCardInfoResult> call = getSpaService().getCompanyCardInfo();        call.enqueue(new TokenCheckedCallback<CompanyCardInfoResult>() {            @Override            protected void postResult(CompanyCardInfoResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 个人卡牌正面信息     */    public void getPersonalCardInfo() {        Call<PersonalCardInfoResult> call = getSpaService().getPersonalCardInfo(RequestConstant.KEY_FRONT);        call.enqueue(new TokenCheckedCallback<PersonalCardInfoResult>() {            @Override            protected void postResult(PersonalCardInfoResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 更新个人卡牌头像     */    private void uploadAvatar(Map<String, Object> params) {        Call<UploadPersonalAvatarResult> call = getSpaService().uploadPersonalAvatar((AvatarBody) params.get(RequestConstant.KEY_AVATAR));        call.enqueue(new TokenCheckedCallback<UploadPersonalAvatarResult>() {            @Override            protected void postResult(UploadPersonalAvatarResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 更新企业卡牌头像     *     * @param params     */    private void uploadCompanyAvatar(Map<String, Object> params) {        Call<UploadCompanyAvatarResult> call = getSpaService().uploadCompanyAvatar((CompanyAvatarBody) params.get(RequestConstant.KEY_AVATAR));        call.enqueue(new TokenCheckedCallback<UploadCompanyAvatarResult>() {            @Override            protected void postResult(UploadCompanyAvatarResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 添加大事件     *     * @param params     */    private void addCompanyEvent(Map<String, String> params) {        Call<AddCompanyHistoryEventResult> call = getSpaService().addCompanyHistoryEvent(params.get(RequestConstant.KEY_NAME),                params.get(RequestConstant.KEY_DATE_DATE), params.get(RequestConstant.KEY_INTRO), params.get(RequestConstant.KEY_IMAGE));        call.enqueue(new TokenCheckedCallback<AddCompanyHistoryEventResult>() {            @Override            protected void postResult(AddCompanyHistoryEventResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 修改大事件     *     * @param params     */    private void editCompanyEvent(Map<String, Object> params) {        Call<EditCompanyHistoryEventResult> call = getSpaService().editCompanyHistoryEvent((EditCompanyHistoryEventBody) params.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<EditCompanyHistoryEventResult>() {            @Override            protected void postResult(EditCompanyHistoryEventResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取谋用户谋时间段内的打卡记录     *     * @param params     */    private void staffAttendanceRecord(Map<String, String> params) {        Call<StaffAttendanceRecordResult> call = getSpaService().staffAttendanceRecord(params.get(RequestConstant.KEY_STAFF_ID),                params.get(RequestConstant.KEY_START_DATE), params.get(RequestConstant.KEY_END_DATE));        call.enqueue(new TokenCheckedCallback<StaffAttendanceRecordResult>() {            @Override            protected void postResult(StaffAttendanceRecordResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 考勤组wifi列表     */    public void getWifiList() {        Call<WifiListResult> call = getSpaService().getWifiList();        call.enqueue(new TokenCheckedCallback<WifiListResult>() {            @Override            protected void postResult(WifiListResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 员工打卡     *     * @param params     */    private void attendanceRecord(Map<String, String> params) {        Call<AttendanceResult> call = getSpaService().staffAttendance(params.get(RequestConstant.KEY_BSS_ID),                params.get(RequestConstant.KEY_WIFI_NAME),params.get(RequestConstant.KEY_CLIENT_CODE));        call.enqueue(new TokenCheckedCallback<AttendanceResult>() {            @Override            protected void postResult(AttendanceResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 日统计     *     * @param params     */    private void statisticsDaily(String params) {        Call<StatisticsDailyResult> call = getSpaService().statisticsDaily(params);        call.enqueue(new TokenCheckedCallback<StatisticsDailyResult>() {            @Override            protected void postResult(StatisticsDailyResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 日统计 - 详细     *     * @param params     */    private void statisticsDailyDetail(Map<String, String> params) {        Call<StatisticsDailyDetailResult> call = getSpaService().statisticsDailyDetail(params.get(RequestConstant.KEY_DATE_DATE),                params.get(RequestConstant.KEY_CLOCK_STATUS), params.get(RequestConstant.KEY_PAGE_INDEX), params.get(RequestConstant.KEY_ROWS_COUNT));        call.enqueue(new TokenCheckedCallback<StatisticsDailyDetailResult>() {            @Override            protected void postResult(StatisticsDailyDetailResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 月统计     *     * @param date     */    private void statisticsMonth(String date) {        Call<StatisticsMonthResult> call = getSpaService().statisticsMonth(date);        call.enqueue(new TokenCheckedCallback<StatisticsMonthResult>() {            @Override            protected void postResult(StatisticsMonthResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 月统计 - 详细     *     * @param params     */    private void statisticsMonthDetail(Map<String, String> params) {        Call<StatisticsMonthDetailResult> call = getSpaService().statisticsMonthDetail(params.get(RequestConstant.KEY_DATE_DATE),                params.get(RequestConstant.KEY_CLOCK_STATUS), params.get(RequestConstant.KEY_PAGE_INDEX), params.get(RequestConstant.KEY_ROWS_COUNT));        call.enqueue(new TokenCheckedCallback<StatisticsMonthDetailResult>() {            @Override            protected void postResult(StatisticsMonthDetailResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 月统计 - 个人     *     * @param params     */    private void attendanceRecordMonth(Map<String, String> params) {        Call<AttendanceRecordMonthResult> call = getSpaService().personalRecordMonth(params.get(RequestConstant.KEY_STAFF_ID),                params.get(RequestConstant.KEY_DATE_DATE));        call.enqueue(new TokenCheckedCallback<AttendanceRecordMonthResult>() {            @Override            protected void postResult(AttendanceRecordMonthResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 新增考勤规则     *     * @param params     */    private void createAttendanceRule(Map<String, Object> params) {        Call<CreateAttendanceRuleResult> call = getSpaService().createAttendanceRule((AttendanceRuleBody) params.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<CreateAttendanceRuleResult>() {            @Override            protected void postResult(CreateAttendanceRuleResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 修改考勤规则     *     * @param params     */    private void editAttendanceRule(Map<String, Object> params) {        Call<CreateAttendanceRuleResult> call = getSpaService().editAttendanceRule((AttendanceRuleBody) params.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<CreateAttendanceRuleResult>() {            @Override            protected void postResult(CreateAttendanceRuleResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 检查考勤人员是否已经有其它规则     *     * @param params     */    private void checkStaffRule(Map<String, Object> params) {        Call<CheckStaffRuleResult> call = getSpaService().checkStaffRule(                (String)params.get(RequestConstant.KEY_ATTENDANCE_RULE_ID)                ,(Integer[]) params.get(RequestConstant.KEY_STAFF_IDS));        call.enqueue(new TokenCheckedCallback<CheckStaffRuleResult>() {            @Override            protected void postResult(CheckStaffRuleResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取考勤规则     */    public void getAttendanceRule() {        Call<AttendanceRuleResult> call = getSpaService().getAttendanceRule();        call.enqueue(new TokenCheckedCallback<AttendanceRuleResult>() {            @Override            protected void postResult(AttendanceRuleResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 删除考勤规则     *     * @param id     */    private void deleteAttendanceRule(String id) {        Call<DeleteAttendanceRuleResult> call = getSpaService().deleteAttendanceRule(id);        call.enqueue(new TokenCheckedCallback<DeleteAttendanceRuleResult>() {            @Override            protected void postResult(DeleteAttendanceRuleResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 修改考勤人员     *     * @param params     */    private void editAttendanceMember(Map<String, Object> params) {        Call<EditAttendanceMemberResult> call = getSpaService().editAttendanceMember((String) params.get(RequestConstant.KEY_ID),                (Integer[]) params.get(RequestConstant.KEY_REQUEST_ARRAY));        call.enqueue(new TokenCheckedCallback<EditAttendanceMemberResult>() {            @Override            protected void postResult(EditAttendanceMemberResult result) {                RxBus.getInstance().post(result);            }        });    }}