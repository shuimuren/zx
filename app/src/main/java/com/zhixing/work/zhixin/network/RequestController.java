package com.zhixing.work.zhixin.network;import android.os.Message;import com.zhixing.work.zhixin.bean.AuthenticateBody;import com.zhixing.work.zhixin.bean.CertificationBody;import com.zhixing.work.zhixin.bean.EducationBody;import com.zhixing.work.zhixin.bean.UpDateInfoBody;import com.zhixing.work.zhixin.msgctrl.AbstractController;import com.zhixing.work.zhixin.msgctrl.MsgDef;import com.zhixing.work.zhixin.msgctrl.RxBus;import com.zhixing.work.zhixin.network.response.AuthenticateListResult;import com.zhixing.work.zhixin.network.response.CompanyCertificationStatusResult;import com.zhixing.work.zhixin.network.response.EvaluateResult;import com.zhixing.work.zhixin.network.response.ExpectedJobResult;import com.zhixing.work.zhixin.network.response.JudgeTelephoneUsableResult;import com.zhixing.work.zhixin.network.response.LoginResult;import com.zhixing.work.zhixin.network.response.PersonalCardBasicInfoResult;import com.zhixing.work.zhixin.network.response.RegisterResult;import com.zhixing.work.zhixin.network.response.SmsCodeResult;import com.zhixing.work.zhixin.network.response.SubmitAuthenticateResult;import com.zhixing.work.zhixin.network.response.UpdatePasswordResult;import com.zhixing.work.zhixin.requestbody.ExpectedJobBodyBean;import java.util.Map;import retrofit2.Call;/** * Created by lhj on 18/5/7 */public class RequestController extends AbstractController {    public RequestController() {        super();    }    private SpaService getSpaService() {        return RetrofitServiceFactory.getSpaService();    }    @Override    public boolean handleMessage(Message msg) {        switch (msg.what) {            case MsgDef.MSG_DEF_PERSONAL_AUTHENTICATES:                getPersonalAuthenticateList();                break;            case MsgDef.MSG_DEF_AUTHENTICATE_SUBMIT:                submitAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_EDUCATION_AUTHENTICATE_SUBMIT:                submitEducationAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_CERTIFICATION_AUTHENTICATE_SUBMIT:                submitCertificationAuthenticate((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_EVALUATE_INFO:                getEvaluateInfo();                break;            case MsgDef.MSG_DEF_UPDATE_EXPECTED_INFO:                doUpdateExpectedInfo((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_PERSONAL_CARD_BASIC_INFO:                getPersonalCardBasicInfo();                break;            case MsgDef.MSG_DEF_LOGIN:                userLogin((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_GET_VERIFICATION_CODE:                getVerificationCode((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_UPDATE_PASSWORD:                updatePassword((Map<String, Object>) msg.obj);                break;            case MsgDef.MSG_DEF_REGISTER:                userRegister((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_TELEPHONE_USABLE:                judgeTelephoneUsable((Map<String, String>) msg.obj);                break;            case MsgDef.MSG_DEF_COMPANY_CERTIFICATION_STATUS:                getCompanyCertificationStatus();                break;        }        return true;    }    /**     * 登录     * @param params     */    private void userLogin(Map<String, String> params) {        Call<LoginResult> call = getSpaService().doLogin(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_PASSWORD),params.get(RequestConstant.KEY_USER_ROLE_ENUM));        call.enqueue(new TokenCheckedCallback<LoginResult>() {            @Override            protected void postResult(LoginResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                super.postError(errorMessage);                LoginResult result = new LoginResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 更新密码     * @param obj     */    private void updatePassword(Map<String, Object> obj) {        Call<UpdatePasswordResult> call = getSpaService().upDateUserPassword((UpDateInfoBody) obj.get(RequestConstant.KEY_UPDATE_PSAAWORD_BODY));        call.enqueue(new TokenCheckedCallback<UpdatePasswordResult>() {            @Override            protected void postResult(UpdatePasswordResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取验证码     * @param params     */    private void getVerificationCode(Map<String, String> params) {        Call<SmsCodeResult> call = getSpaService().getVerificationCode(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_USER_ROLE_ENUM),                params.get(RequestConstant.KEY_SMS_CODE_TYPE_ENUM));        call.enqueue(new TokenCheckedCallback<SmsCodeResult>() {            @Override            protected void postResult(SmsCodeResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                SmsCodeResult result = new SmsCodeResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 注册     * @param params     */    private void userRegister(Map<String, String> params) {        Call<RegisterResult> call = getSpaService().userRegister(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_PASSWORD),params.get(RequestConstant.KEY_ROLE),                params.get(RequestConstant.KEY_VERIFY_CODE));        call.enqueue(new TokenCheckedCallback<RegisterResult>() {            @Override            protected void postResult(RegisterResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     *     * @param params     */    private void judgeTelephoneUsable(Map<String, String> params) {        Call<JudgeTelephoneUsableResult> call = getSpaService().judgeTelephoneUsable(params.get(RequestConstant.KEY_PHONE_NUMBER),                params.get(RequestConstant.KEY_ROLE));        call.enqueue(new TokenCheckedCallback<JudgeTelephoneUsableResult>() {            @Override            protected void postResult(JudgeTelephoneUsableResult result) {                RxBus.getInstance().post(result);            }            @Override            protected void postError(String errorMessage) {                JudgeTelephoneUsableResult result = new JudgeTelephoneUsableResult();                result.Code = NetworkConstant.SERVICE_ERROR_CODE;                result.setContent(true);                result.Message = errorMessage;                RxBus.getInstance().post(result);            }        });    }    /**     * 获取认证列表     */    public void getPersonalAuthenticateList() {        Call<AuthenticateListResult> call = getSpaService().getAuthenticates();        call.enqueue(new TokenCheckedCallback<AuthenticateListResult>() {            @Override            protected void postResult(AuthenticateListResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交个人认证     *     * @param params     */    public void submitAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitAuthenticate((AuthenticateBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交学历认证     *     * @param params     */    private void submitEducationAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitEducationAuthenticate((EducationBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 提交证书认证     *     * @param params     */    private void submitCertificationAuthenticate(Map<String, Object> params) {        Call<SubmitAuthenticateResult> call = getSpaService().submitCertificationAuthenticate((CertificationBody) params.get(RequestConstant.KEY_AUTHENTICATION_INFO));        call.enqueue(new TokenCheckedCallback<SubmitAuthenticateResult>() {            @Override            protected void postResult(SubmitAuthenticateResult result) {                RxBus.getInstance().post(result);            }        });    }    /**     * 获取资质信息     */    private void getEvaluateInfo() {        Call<EvaluateResult> call = getSpaService().evaluateResult(RequestConstant.KEY_BACK, RequestConstant.KEY_EVALUATE);        call.enqueue(new TokenCheckedCallback<EvaluateResult>() {            @Override            protected void postResult(EvaluateResult result) {                RxBus.getInstance().post(result);            }        });    }    private void doUpdateExpectedInfo(Map<String, Object> params) {        Call<ExpectedJobResult> call = getSpaService().updateExpectedJob(String.valueOf(params.get(RequestConstant.KEY_RESUME_ID)),                (ExpectedJobBodyBean[]) params.get(RequestConstant.KEY_REQUEST_BODY));        call.enqueue(new TokenCheckedCallback<ExpectedJobResult>() {            @Override            protected void postResult(ExpectedJobResult result) {                RxBus.getInstance().post(result);            }        });    }    public void getPersonalCardBasicInfo() {        Call<PersonalCardBasicInfoResult> call = getSpaService().getCardBasicInfo(RequestConstant.KEY_BACK, RequestConstant.KEY_BASIC_INFO);        call.enqueue(new TokenCheckedCallback<PersonalCardBasicInfoResult>() {            @Override            protected void postResult(PersonalCardBasicInfoResult result) {                RxBus.getInstance().post(result);            }        });    }    //企业认证状态    public void getCompanyCertificationStatus() {        Call<CompanyCertificationStatusResult> call = getSpaService().getCompanyCertificationStatus();        call.enqueue(new TokenCheckedCallback<CompanyCertificationStatusResult>() {            @Override            protected void postResult(CompanyCertificationStatusResult result) {                RxBus.getInstance().post(result);            }        });    }}