package com.zhixing.work.zhixin.view.card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseTitleActivity;
import com.zhixing.work.zhixin.bean.PersonalJobCardBean;
import com.zhixing.work.zhixin.event.RefreshStaffInfoEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.PersonalJobCardInfoResult;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.back.PersonalJobInfoActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by lhj on 2018/8/14.
 * Description: 员工卡牌
 */

public class StaffCardActivity extends BaseTitleActivity {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.rl_name)
    LinearLayout rlName;
    @BindView(R.id.tv_job_nike_name)
    TextView tvJobNikeName;
    @BindView(R.id.tv_job_position)
    TextView tvJobPosition;
    @BindView(R.id.tv_job_department)
    TextView tvJobDepartment;
    @BindView(R.id.tv_user_mark)
    TextView tvUserMark;
    @BindView(R.id.ll_job_card_data)
    LinearLayout llJobCardData;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.rl_personal_info)
    RelativeLayout rlPersonalInfo;
    @BindView(R.id.img_personal_sex)
    ImageView imgPersonalSex;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.rl_personal_data)
    RelativeLayout rlPersonalData;

    public static final String INTENT_KEY_STAFF_ID = "staffId";

    public static void startStaffCardActivity(Activity activity, String staffId) {
        Intent intent = new Intent(activity, StaffCardActivity.class);
        intent.putExtra(INTENT_KEY_STAFF_ID, staffId);
        activity.startActivity(intent);
    }

    private Subscription mStaffInfoSubscription;
    private String mStaffId;
    private PersonalJobCardBean cardBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_card);
        ButterKnife.bind(this);
        setTitle(ResourceUtils.getString(R.string.staff_info_title));
        getIntentData();
        registerRequest();
    }

    public void getIntentData() {
        mStaffId = getIntent().getStringExtra(INTENT_KEY_STAFF_ID);
        if (TextUtils.isEmpty(mStaffId)) {
            this.finish();
        }
    }

    private void registerRequest() {
        mStaffInfoSubscription = RxBus.getInstance().toObservable(PersonalJobCardInfoResult.class).subscribe(
                result -> handlerStaffCardInfo(result)
        );
        getUserInfo();
    }

    private void getUserInfo() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_STAFF_CARD, mStaffId);
    }

    private void handlerStaffCardInfo(PersonalJobCardInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            cardBean = result.getContent();
            if (cardBean != null) {
                name.setText(ZxTextUtils.getTextWithDefault(cardBean.getRealName()));
                tvJobNikeName.setText(ZxTextUtils.getTextWithDefault(cardBean.getNickName()));
                tvJobDepartment.setText(ZxTextUtils.getTextWithDefault(cardBean.getDepartmentName()));
                tvJobPosition.setText(ZxTextUtils.getTextWithDefault(cardBean.getJobType()));
                tvUserMark.setText(ZxTextUtils.getTextWithDefault(cardBean.getMotto()));
            }
            GlideUtils.getInstance().loadPublicRoundTransformWithDefault(this,
                    ResourceUtils.getDrawable(R.drawable.img_company_default), cardBean.getCardAvatar(), avatar);
            GlideUtils.getInstance().loadPublicCircleWithDefault(this,
                    ResourceUtils.getDrawable(R.drawable.img_company_default), cardBean.getCompanyLogo(), imgPersonalSex);
        }
    }

    @OnClick(R.id.btn_more)
    public void onViewClicked() {
        PersonalJobInfoActivity.startPersonalJobInfoActivity(this, mStaffId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unsubscribe(mStaffInfoSubscription);
    }

    /**
     * 编辑完成个人信息进行刷新
     *
     * @param event
     */
    @Subscribe
    public void refreshStaffInfo(RefreshStaffInfoEvent event) {
        if (event.isBasicInfo()) {
            getUserInfo();
        }
    }


}
