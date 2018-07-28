package com.zhixing.work.zhixin.view.companyCard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.ALiImageUrlBean;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.event.BigEventRefreshEvent;
import com.zhixing.work.zhixin.event.ModifyEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.AddCompanyHistoryEventResult;
import com.zhixing.work.zhixin.network.response.EditCompanyHistoryEventResult;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.requestbody.EditCompanyHistoryEventBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.view.card.ModifyContentActivity;
import com.zhixing.work.zhixin.view.card.ModifyDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imagetool.lhj.com.ImageTool;
import imagetool.lhj.com.selector.utils.FileUtils;
import rx.Subscription;

public class AddEventActivity extends BaseTitleActivity {


    @BindView(R.id.tv_event_name)
    TextView tvEventName;
    @BindView(R.id.event_name_right)
    ImageView eventNameRight;
    @BindView(R.id.rl_event_name)
    RelativeLayout rlEventName;
    @BindView(R.id.event_time)
    TextView eventTime;
    @BindView(R.id.event_time_right)
    ImageView eventTimeRight;
    @BindView(R.id.rl_event_time)
    RelativeLayout rlEventTime;
    @BindView(R.id.event_synopsis)
    TextView eventSynopsis;
    @BindView(R.id.event_synopsis_right)
    ImageView eventSynopsisRight;
    @BindView(R.id.rl_event_synopsis)
    RelativeLayout rlEventSynopsis;
    @BindView(R.id.img_event)
    ImageView imgEvent;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.ll_save)
    RelativeLayout llSave;

    public static final String KEY_TYPE = "type";
    public static final String VALUE_EDIT_TYPE = "edit";
    public static final String VALUE_ADD_TYPE = "add";
    public static final String KEY_HISTORY_BEAN = "bean";

    private Subscription mAddEventSubscription;
    private Subscription mEditEventSubscription;
    private Subscription mGetImageUrlFromAliSubscription;
    private Subscription mUploadImageToAliSubscription;
    private History mHistory;
    private boolean isEdit;
    private String mName = "";
    private String mDate = "";
    private String mIntro = "";
    private String mImage = "";
    private String mImagePath ;

    private ImageTool mImageTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.company_history_event));
        getIntentData();
        initView();
        registerSubscription();
    }

    public void getIntentData() {
        Intent intent = getIntent();
        isEdit = intent.getStringExtra(KEY_TYPE).equals(VALUE_EDIT_TYPE);
        if (isEdit) {
            mHistory = (History) intent.getSerializableExtra(KEY_HISTORY_BEAN);
        }
    }

    private void initView() {
        mImageTool = new ImageTool(FileUtils.getCacheDirectory(this));
        if(isEdit){
            mImage = mHistory.getImage();
            if(!TextUtils.isEmpty(mImage)){
                getCompanyImage(mImage);
            }
            mName = mHistory.getName();
            mIntro = mHistory.getIntro();
            mDate = mHistory.getDate();
            tvEventName.setText(mName);
            eventTime.setText(DateFormatUtil.parseDate(mDate, "yyyy-mm"));
            eventSynopsis.setText(mIntro);
        }

    }

    private void registerSubscription() {
        mAddEventSubscription = RxBus.getInstance().toObservable(AddCompanyHistoryEventResult.class).subscribe(
                result -> handlerAddResult(result)
        );

        mEditEventSubscription = RxBus.getInstance().toObservable(EditCompanyHistoryEventResult.class).subscribe(
                result -> handlerEditResult(result)
        );

        mGetImageUrlFromAliSubscription = RxBus.getInstance().toObservable(ALiImageUrlBean.class).subscribe(
                result -> handlerImageUrlResult(result)
        );

        mUploadImageToAliSubscription = RxBus.getInstance().toObservable(ImageUploadResult.class).subscribe(
                result -> handlerImageUploadResult(result)
        );


    }

    /**
     * 图片上传成功
     * @param result
     */
    private void handlerImageUploadResult(ImageUploadResult result) {
        if (result.getCode() == ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_COMPANY_EVENT&& result.isSuccess()) {
            mImage = result.getUrl();
            if (isEdit) {
                editEvent();
            } else {
                addEvent();
            }

        }
    }


    /**
     * 从阿里云获取图片链接
     *
     * @param result
     */
    private void handlerImageUrlResult(ALiImageUrlBean result) {
        if(result.getBaseUrl() == null){
            return;
        }
        if (!TextUtils.isEmpty(mImage) && result.getBaseUrl().equals(mImage)) {
            GlideUtils.getInstance().loadGlideRoundTransform(AddEventActivity.this,result.getUrl(),imgEvent);
        }
    }

    private void getCompanyImage(String image) {
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_LOAD_BASE_URL, ALiYunFileURLBuilder.PUBLIC_END_POINT);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_MARK, image);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE, params);
    }

    /**
     * 添加
     * @param result
     */
    private void handlerAddResult(AddCompanyHistoryEventResult result) {
        hideLoadingDialog();
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            EventBus.getDefault().post(new BigEventRefreshEvent(true));
            AlertUtils.show(ResourceUtils.getString(R.string.add_event_success));
            this.finish();
        }else {
            AlertUtils.show(result.Message);
        }
    }

    /**
     * 编辑
     * @param result
     */
    private void handlerEditResult(EditCompanyHistoryEventResult result) {
        hideLoadingDialog();
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            AlertUtils.show(ResourceUtils.getString(R.string.edit_event_success));
            EventBus.getDefault().post(new BigEventRefreshEvent(true));
            this.finish();
        }else {
            AlertUtils.show(result.Message);
        }
    }

    private void editEvent() {
        Map map = new HashMap();
        EditCompanyHistoryEventBody body = new EditCompanyHistoryEventBody();
        body.setDate(mDate);
        body.setId(String.valueOf(mHistory.getId()));
        body.setImage(mImage);
        body.setIntro(mIntro);
        body.setName(mName);
        map.put(RequestConstant.KEY_REQUEST_BODY,body);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_EDIT_COMPANY_ENENT, map);
    }

    private void addEvent() {
        Map params = new HashMap();
        params.put(RequestConstant.KEY_NAME, mName);
        params.put(RequestConstant.KEY_DATE, mDate);
        params.put(RequestConstant.KEY_INTRO, mIntro);
        params.put(RequestConstant.KEY_IMAGE, mImage);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ADD_COMPANY_EVENT, params);
    }


    @OnClick({R.id.rl_event_name, R.id.rl_event_time, R.id.rl_event_synopsis, R.id.img_event, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_event_name:
                startActivity(new Intent(context, ModifyDataActivity.class).
                        putExtra(ModifyDataActivity.TYPE_TITLE, "事件名称").
                        putExtra(ModifyDataActivity.TYPE, ModifyDataActivity.EVENT_NAME).
                        putExtra(ModifyDataActivity.TYPE_CONTENT, tvEventName.getText().toString()));
                break;

            case R.id.rl_event_time:
                final TimePickerView school_time = new TimePickerBuilder(context, new OnTimeSelectListener() {
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = DateFormatUtil.getTimeYM(date2);
                        eventTime.setText(time);
                        mDate = time;
                    }
                })
                        .setType(new boolean[]{true, true, false, false, false, false})//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleText("事件时间")
                        .setContentTextSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                school_time.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                school_time.show();
                break;
            case R.id.rl_event_synopsis:
                startActivity(new Intent(context, ModifyContentActivity.class).
                        putExtra(ModifyContentActivity.TYPE_TITLE, "事件简介").
                        putExtra(ModifyContentActivity.TYPE, ModifyContentActivity.EVENT_SYNOPSIS).
                        putExtra(ModifyContentActivity.HINT, "事件简介").
                        putExtra(ModifyContentActivity.TYPE_CONTENT, eventSynopsis.getText().toString()));
                break;
            case R.id.img_event:
                mImageTool.reset().setAspectX_Y(1,1).start(AddEventActivity.this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if(uri != null){
                            mImagePath = uri.getPath();
                            GlideUtils.getInstance().loadGlideRoundTransform(AddEventActivity.this,uri.getPath(),imgEvent);
                        }
                    }
                });
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(mIntro)) {
                    AlertUtils.toast(context, "事件简介不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mName)) {
                    AlertUtils.toast(context, "事件名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mDate)) {
                    AlertUtils.toast(context, "事件时间不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mImagePath)) {
                    showLoading();
                   if(isEdit){
                       editEvent();
                   }else {
                       addEvent();
                   }
                }else{
                    upLoadCompanyImage(mImagePath);
                }

                break;
        }
    }

    /**
     * 上传图片到阿里云
     *
     * @param path
     */
    private void upLoadCompanyImage(String path) {
        showLoading(ResourceUtils.getString(R.string.uploading), false);
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE, ALiYunFileURLBuilder.COMPANYHISTORY);
        params.put(ALiYunFileURLBuilder.KEY_FILE_PATH, path);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_CODE, String.valueOf(ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_COMPANY_EVENT));
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD, params);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModifyEvent(ModifyEvent event) {
        switch (event.getType()) {
            case ModifyDataActivity.EVENT_NAME:
                tvEventName.setText(event.getContent());
                mName = event.getContent();
                break;
            case ModifyContentActivity.EVENT_SYNOPSIS:
                eventSynopsis.setText(event.getContent());
                mIntro = event.getContent();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageTool.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mAddEventSubscription, mEditEventSubscription,
                mGetImageUrlFromAliSubscription,mUploadImageToAliSubscription);
    }
}



