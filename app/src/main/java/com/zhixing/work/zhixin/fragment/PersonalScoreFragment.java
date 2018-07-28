package com.zhixing.work.zhixin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.ALiImageUrlBean;
import com.zhixing.work.zhixin.bean.PersonalCardInfoBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.dialog.CardCompleteDialog;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.network.response.PersonalCardInfoResult;
import com.zhixing.work.zhixin.network.response.UploadPersonalAvatarResult;
import com.zhixing.work.zhixin.requestbody.AvatarBody;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.FileUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.util.ZxTextUtils;
import com.zhixing.work.zhixin.view.card.CreateCardActivity;
import com.zhixing.work.zhixin.view.card.PerfectCardDataActivity;
import com.zhixing.work.zhixin.view.card.back.CardMainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import imagetool.lhj.com.ImageTool;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/24.
 * Description: 主页-个人版评分
 */

public class PersonalScoreFragment extends SupportFragment {

    @BindView(R.id.create_card)
    Button createCard;
    @BindView(R.id.rl_card_empty)
    RelativeLayout rlCardEmpty;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.default_avatar)
    ImageView defaultAvatar;
    @BindView(R.id.avatar_text)
    TextView avatarText;
    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.rl_name)
    LinearLayout rlName;
    @BindView(R.id.constellation)
    ImageView constellation;
    @BindView(R.id.nikeName)
    TextView nikeName;
    @BindView(R.id.ll_nike_name)
    LinearLayout llNikeName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.motto)
    TextView motto;
    @BindView(R.id.ll_motto)
    LinearLayout llMotto;
    @BindView(R.id.perfect_card)
    Button perfectCard;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.rl_personal_info)
    RelativeLayout rlPersonalInfo;
    @BindView(R.id.img_personal_type)
    ImageView imgPersonalType;
    @BindView(R.id.rl_personal_data)
    RelativeLayout rlPersonalData;
    @BindView(R.id.img_card)
    ImageView imgCard;
    @BindView(R.id.img_friend)
    ImageView imgFriend;
    @BindView(R.id.img_identity)
    ImageView imgIdentity;
    @BindView(R.id.more)
    ImageView more;
    Unbinder unbinder;

    private Subscription mPersonalCardInfoSubscription;
    private Subscription mGetAvatarSubscription;
    private Subscription mImageUploadSubscription;
    private Subscription mUploadPersonalSubscription;
    private PersonalCardInfoBean mPersonalCard;
    private String mAvatarBaseUrl;
    private ImageTool mImageTool;


    public static PersonalScoreFragment newInstance() {
        Bundle args = new Bundle();
        PersonalScoreFragment fragment = new PersonalScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_score, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initSubscription();
        EventBus.getDefault().register(this);
        getPersonalCardInfo();
        return view;
    }

    private void initView() {
        mAvatarBaseUrl = "";
        mImageTool = new ImageTool(FileUtil.getDiskCachePath());
    }


    private void getPersonalCardInfo() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_PERSONAL_CARD_INFO);
    }

    private void initSubscription() {
        mPersonalCardInfoSubscription = RxBus.getInstance().toObservable(PersonalCardInfoResult.class).subscribe(
                result -> handlerPersonalCardInfo(result)
        );

        mGetAvatarSubscription = RxBus.getInstance().toObservable(ALiImageUrlBean.class).subscribe(
                result -> handlerAvatarResult(result)
        );

        mImageUploadSubscription = RxBus.getInstance().toObservable(ImageUploadResult.class).subscribe(
                result -> handlerImageUpload(result)
        );

        mUploadPersonalSubscription = RxBus.getInstance().toObservable(UploadPersonalAvatarResult.class).subscribe(
                result -> handlerUploadResult(result)
        );
    }

    /**
     * 上传图片到服务器
     *
     * @param result
     */
    private void handlerUploadResult(UploadPersonalAvatarResult result) {
        if (result.isContent()) {
            Logger.i(">>>", "图片上传服务器成功");
        }
    }

    private void handlerImageUpload(ImageUploadResult result) {
        hideLoadingDialog();
        if (result.isSuccess() && result.getCode() == ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_PERSONAL_FRAGMENT) {
            AvatarBody body = new AvatarBody(result.getUrl());
            Map map = new HashMap();
            map.put(RequestConstant.KEY_AVATAR, body);
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_UPLOAD_AVATAR, map);
        }
    }

    private void handlerAvatarResult(ALiImageUrlBean result) {
        if (result.getBaseUrl().equals(mAvatarBaseUrl)) {
            GlideUtils.getInstance().loadGlideRoundTransform(getActivity(), result.getUrl(), avatar);
        }
    }

    private void handlerPersonalCardInfo(PersonalCardInfoResult result) {
        if (result.Code == NetworkConstant.SUCCESS_CODE) {
            if (result.getContent() == null) {
                rlCardEmpty.setVisibility(View.VISIBLE);
                rlPersonalData.setVisibility(View.GONE);
                perfectCard.setVisibility(View.VISIBLE);
            } else {
                rlCardEmpty.setVisibility(View.GONE);
                rlPersonalData.setVisibility(View.VISIBLE);
                perfectCard.setVisibility(View.GONE);
                imgCard.setImageResource(R.drawable.icon_1_light);
                mPersonalCard = result.getContent();
                SettingUtils.saveCreateCard();
                if (mPersonalCard.getSex() == ResultConstant.USER_GERNAL_MAN) {
                    imgPersonalType.setImageDrawable(ResourceUtils.getDrawable(R.drawable.img_boy));
                } else {
                    imgPersonalType.setImageDrawable(ResourceUtils.getDrawable(R.drawable.img_girl));
                }
                mail.setText(mPersonalCard.getEmail());
                phone.setText(SettingUtils.getPhoneNumber());
                name.setText(mPersonalCard.getRealName());
                nikeName.setText(ZxTextUtils.getTextWithDefault(mPersonalCard.getNickName()));
                if (mPersonalCard.getConstellation() != null) {
                    constellation.setImageResource(Utils.getConstellationImage(mPersonalCard.getConstellation()));
                }
                motto.setText(ZxTextUtils.getTextWithDefault(mPersonalCard.getMotto()));
                if (TextUtils.isEmpty(mPersonalCard.getAvatar())) {
                    avatar.setVisibility(View.GONE);
                    defaultAvatar.setVisibility(View.VISIBLE);
                    avatarText.setVisibility(View.VISIBLE);
                } else {
                    avatar.setVisibility(View.VISIBLE);
                    defaultAvatar.setVisibility(View.GONE);
                    avatarText.setVisibility(View.GONE);
                    mAvatarBaseUrl = mPersonalCard.getAvatar();
                    getUserAvatar(mPersonalCard.getAvatar());
                }
            }
        }
    }

    private void getUserAvatar(String avatar) {
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_LOAD_BASE_URL, ALiYunFileURLBuilder.PUBLIC_END_POINT);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_MARK, avatar);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE, params);
    }


    private void upLoadAvatar(String avatar) {
        showLoading(ResourceUtils.getString(R.string.uploading), false);
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE, ALiYunFileURLBuilder.PERSONALAVATAR);
        params.put(ALiYunFileURLBuilder.KEY_FILE_PATH, avatar);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_CODE, String.valueOf(ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_PERSONAL_FRAGMENT));
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD, params);

    }

    @Override
    public void fetchData() {
        Logger.i(">>>", "personal fetch data");

    }


    @OnClick({R.id.create_card, R.id.avatar, R.id.default_avatar, R.id.avatar_text, R.id.perfect_card, R.id.img_card, R.id.img_friend, R.id.img_identity, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_card:
                startActivity(new Intent(getActivity(), CreateCardActivity.class));
                break;
            case R.id.default_avatar:
            case R.id.avatar:
                mImageTool.reset().onlyPick(false).setAspectX_Y(5, 4).start(this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                            defaultAvatar.setVisibility(View.GONE);
                            avatar.setVisibility(View.VISIBLE);
                            Glide.with(getActivity()).load(uri).into(avatar);
                            upLoadAvatar(uri.getPath());
                        }
                    }
                });
                break;

            case R.id.avatar_text:
                break;
            case R.id.perfect_card:
                startActivity(new Intent(getActivity(), PerfectCardDataActivity.class));
                break;
            case R.id.img_card:
                break;
            case R.id.img_friend:
                break;
            case R.id.img_identity:
                break;
            case R.id.more:
                if (SettingUtils.createCardBefore()) {
                    startActivity(new Intent(getActivity(), CardMainActivity.class));
                } else {
                    AlertUtils.show(ResourceUtils.getString(R.string.complete_user_information));
                    startActivity(new Intent(getActivity(), CreateCardActivity.class));
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageTool.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        RxBus.getInstance().unsubscribe(mPersonalCardInfoSubscription, mGetAvatarSubscription, mImageUploadSubscription,mUploadPersonalSubscription);
    }

    //显示成就弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardCompleteEvent(CardCompleteEvent event) {
        if (event.isComplete()) {
            getPersonalCardInfo();
            CardCompleteDialog dialog = new CardCompleteDialog(getActivity(), new CardCompleteDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

}
