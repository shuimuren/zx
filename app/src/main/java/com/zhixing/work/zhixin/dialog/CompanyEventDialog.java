package com.zhixing.work.zhixin.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.bean.ALiImageUrlBean;
import com.zhixing.work.zhixin.bean.History;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.util.DateFormatUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by lhj on 2018/7/26.
 * Description:
 */

public class CompanyEventDialog extends BaseDialog {


    private History history;
    private ImageView imgEvent;
    private TextView tvEventName;
    private TextView tvEventTime;
    private TextView tvEventInfo;
    private Context context;
    private Subscription mGetImageUrlFromAliSubscription;


    public CompanyEventDialog(Context context, History history) {
        super(context);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        int screenWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        int windowWidth = (int) (screenWidth * 0.7);
        window.setLayout(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.history = history;
        //GlideUtils.getInstance().loadGlideRoundTransform(context, ALiYunOssFileLoader.gtePublic()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_company_event);


        imgEvent = findViewById(R.id.img_event);
        tvEventName = findViewById(R.id.tv_event_name);
        tvEventTime = findViewById(R.id.tv_event_time);
        tvEventInfo = findViewById(R.id.tv_event_info);

        tvEventName.setText(history.getName());
        tvEventTime.setText(DateFormatUtil.parseDate(history.getDate(), "yyyy-mm"));
        tvEventInfo.setText(history.getIntro());

        mGetImageUrlFromAliSubscription = RxBus.getInstance().toObservable(ALiImageUrlBean.class).subscribe(
                result -> handlerImageUrlResult(result)
        );

        if (!TextUtils.isEmpty(history.getImage())) {
            getCompanyImage(history.getImage());
        } else {
            Glide.with(context).load(ResourceUtils.getDrawable(R.drawable.img_company_default))
                    .transition(DrawableTransitionOptions.withCrossFade()).
                    apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(imgEvent);
        }
    }

    /**
     * 从阿里云获取图片链接
     *
     * @param result
     */
    private void handlerImageUrlResult(ALiImageUrlBean result) {
        if (result.getBaseUrl() == null) {
            return;
        }
        if (result.getBaseUrl().equals(history.getImage())) {
            GlideUtils.getInstance().loadGlideRoundTransform(context, result.getUrl(), imgEvent);
        }
    }

    private void getCompanyImage(String image) {
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_LOAD_BASE_URL, ALiYunFileURLBuilder.PUBLIC_END_POINT);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_MARK, image);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE, params);
    }

    @Override
    public void cancel() {
        super.cancel();
        RxBus.getInstance().unsubscribe(mGetImageUrlFromAliSubscription);
    }
}
