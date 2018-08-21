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
import com.zhixing.work.zhixin.bean.CompanyCardInfoBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.dialog.CardCompleteDialog;
import com.zhixing.work.zhixin.dialog.CertifiedDialog;
import com.zhixing.work.zhixin.event.BasicRefreshEvent;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.RequestConstant;
import com.zhixing.work.zhixin.network.response.CompanyCardInfoResult;
import com.zhixing.work.zhixin.network.response.ImageUploadResult;
import com.zhixing.work.zhixin.network.response.UploadCompanyAvatarResult;
import com.zhixing.work.zhixin.requestbody.CompanyAvatarBody;
import com.zhixing.work.zhixin.util.FileUtil;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.clock.ManagerClockInActivity;
import com.zhixing.work.zhixin.view.companyCard.CompanyCertificationActivity;
import com.zhixing.work.zhixin.view.companyCard.CreateCompanyCardActivity;

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
 * Description:主页-企业评分
 */

public class CompanyScoreFragment extends SupportFragment {


    Unbinder unbinder;
    @BindView(R.id.btn_create_company_card)
    Button btnCreateCompanyCard;
    @BindView(R.id.rl_company_card_empty)
    RelativeLayout rlCompanyCardEmpty;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.img_card_default)
    ImageView imgCardDefault;
    @BindView(R.id.image_company_picture)
    ImageView imageCompanyPicture;
    @BindView(R.id.img_company_logo)
    ImageView imageCompanyLogo;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.ll_company_address)
    LinearLayout llCompanyAddress;
    @BindView(R.id.enterprise_stars)
    ScaleRatingBar enterpriseStars;
    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;
    @BindView(R.id.rl_company_data)
    RelativeLayout rlCompanyData;
    @BindView(R.id.image_notify)
    ImageView imageNotify;
    @BindView(R.id.img_friend)
    ImageView imgFriend;
    @BindView(R.id.img_card)
    ImageView imgCard;
    @BindView(R.id.img_identity)
    ImageView imgIdentity;
    @BindView(R.id.more)
    ImageView more;


    private Subscription mCompanyCardInfoSubscription;
    private Subscription mGetAvatarSubscription;
    private Subscription mImageUploadSubscription;
    private Subscription mUploadCompanySubscription;

    private CompanyCardInfoBean mCompanyCard;
    private String mAvatarBaseUrl;
    private String mLogoBaseUrl;
    private ImageTool mImageTool;
    private String addressCity = "";

    public static CompanyScoreFragment newInstance() {
        Bundle args = new Bundle();
        CompanyScoreFragment fragment = new CompanyScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_score, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initSubscription();
        EventBus.getDefault().register(this);
        getCardInfo();
        return view;
    }

    private void initView() {
        mAvatarBaseUrl = "";
        mImageTool = new ImageTool(FileUtil.getDiskCachePath());
    }

    @Override
    public void fetchData() {

    }

    private void getCardInfo() {
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_COMPANY_CARD_INFO);
    }

    private void initSubscription() {
        mCompanyCardInfoSubscription = RxBus.getInstance().toObservable(CompanyCardInfoResult.class).subscribe(
                result -> handlerCompanyCardInfo(result)
        );

        mGetAvatarSubscription = RxBus.getInstance().toObservable(ALiImageUrlBean.class).subscribe(
                result -> handlerAvatarResult(result)
        );

        mImageUploadSubscription = RxBus.getInstance().toObservable(ImageUploadResult.class).subscribe(
                result -> handlerImageUploadToAli(result)
        );

        mUploadCompanySubscription = RxBus.getInstance().toObservable(UploadCompanyAvatarResult.class).subscribe(
                result -> handlerUploadResult(result)
        );
    }

    private void handlerCompanyCardInfo(CompanyCardInfoResult result) {
        if (result.getContent() == null) {
            rlCompanyCardEmpty.setVisibility(View.VISIBLE);
            rlCompanyData.setVisibility(View.GONE);
        } else {
            imgCard.setImageResource(R.drawable.icon_1_light);
            rlCompanyCardEmpty.setVisibility(View.GONE);
            rlCompanyData.setVisibility(View.VISIBLE);
            SettingUtils.saveCreateCard();
            mCompanyCard = result.getContent();

            if (!TextUtils.isEmpty(mCompanyCard.getLogo())) {
                mLogoBaseUrl = mCompanyCard.getLogo();
                getCompanyAvatar(mCompanyCard.getLogo());
            } else {
                mLogoBaseUrl = "";
            }
            if (!TextUtils.isEmpty(mCompanyCard.getCompanyPic())) {
                imgCardDefault.setVisibility(View.GONE);
                mAvatarBaseUrl = mCompanyCard.getCompanyPic();
                getCompanyAvatar(mCompanyCard.getCompanyPic());
            } else {
                imgCardDefault.setVisibility(View.VISIBLE);
            }
            if (mCompanyCard.getProvince() != null) {
                addressCity = Utils.searchProvincial(mCompanyCard.getProvince());
            }
            if (mCompanyCard.getCity() != null) {
                addressCity = addressCity + Utils.searchCity(mCompanyCard.getCity());
            }
            if (mCompanyCard.getDistrict() != null) {
                addressCity = addressCity + Utils.searchArea(mCompanyCard.getDistrict());
            }
            if (TextUtils.isEmpty(mCompanyCard.getAddress())) {
                tvCompanyAddress.setText(addressCity);
            } else {
                tvCompanyAddress.setText(addressCity + mCompanyCard.getAddress());
            }
            tvCompanyName.setText(mCompanyCard.getFullName());

            if (mCompanyCard.getBusinessLicenseStatus() != ResultConstant.AUTHENTICATE_STATUS_NULL
                    && mCompanyCard.getManagerIdCardStatus() != ResultConstant.AUTHENTICATE_STATUS_NULL) {
                imageNotify.setVisibility(View.INVISIBLE);
            } else {
                imageNotify.setVisibility(View.GONE);
            }
        }


    }

    /**
     * 从阿里云获取图片链接
     *
     * @param result
     */
    private void handlerAvatarResult(ALiImageUrlBean result) {
        if (result.getBaseUrl() != null && result.getBaseUrl().equals(mAvatarBaseUrl)) {
            Glide.with(getActivity()).load(result.getUrl()).into(imageCompanyPicture);
        } else if (mLogoBaseUrl.equals(result.getBaseUrl())) {
            GlideUtils.getInstance().loadCircleUserIconInto(getActivity(), result.getUrl(), imageCompanyLogo);
        }
    }

    /**
     * @param result
     */
    private void handlerImageUploadToAli(ImageUploadResult result) {
        hideLoadingDialog();
        if (result.isSuccess() && result.getCode() == ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_COMPANY_FRAGMENT) {
            CompanyAvatarBody body = new CompanyAvatarBody(result.getUrl());
            Map map = new HashMap();
            map.put(RequestConstant.KEY_AVATAR, body);
            MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_COMPANY_UPLOAD_AVATAR, map);
        }
    }

    private void handlerUploadResult(UploadCompanyAvatarResult result) {
        if (result.isContent()) {
            Logger.i(">>>", "企业图片上传服务器成功");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        RxBus.getInstance().unsubscribe(mCompanyCardInfoSubscription, mGetAvatarSubscription, mImageUploadSubscription, mUploadCompanySubscription);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageTool.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传图片到阿里云
     *
     * @param path
     */
    private void upLoadCompanyAvatar(String path) {
        showLoading(ResourceUtils.getString(R.string.uploading), false);
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_SUB_ITEM_CATALOGUE, ALiYunFileURLBuilder.COMPANYBACKGROUND);
        params.put(ALiYunFileURLBuilder.KEY_FILE_PATH, path);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_CODE, String.valueOf(ALiYunFileURLBuilder.IMAGE_DISCERN_CODE_COMPANY_FRAGMENT));
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_IMAGE_UPLOAD, params);
    }

    private void getCompanyAvatar(String avatar) {
        Map params = new HashMap();
        params.put(ALiYunFileURLBuilder.KEY_LOAD_BASE_URL, ALiYunFileURLBuilder.PUBLIC_END_POINT);
        params.put(ALiYunFileURLBuilder.KEY_BUCKET_NAME, ALiYunFileURLBuilder.BUCKET_PUBLIC);
        params.put(ALiYunFileURLBuilder.KEY_IMAGE_MARK, avatar);
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_ALI_YUN_GET_IMAGE, params);
    }

    @OnClick({R.id.btn_create_company_card, R.id.img_card_default, R.id.image_company_picture, R.id.rl_logo, R.id.image_notify, R.id.img_friend, R.id.img_card, R.id.img_identity, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_company_card:
                CreateCompanyCardActivity.startCompanyCard(getActivity());
                break;
            case R.id.img_card_default:
            case R.id.image_company_picture:
                mImageTool.reset().onlyPick(false).setAspectX_Y(4, 5).start(this, new ImageTool.ResultListener() {
                    @Override
                    public void onResult(String error, Uri uri, Bitmap bitmap) {
                        if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                            imgCardDefault.setVisibility(View.GONE);
                            imageCompanyPicture.setVisibility(View.VISIBLE);
                            Glide.with(getActivity()).load(uri).into(imageCompanyPicture);
                            upLoadCompanyAvatar(uri.getPath());
                        }
                    }
                });
                break;
            case R.id.rl_logo:

                break;
            case R.id.image_notify:
                CertifiedDialog dialog = new CertifiedDialog(getActivity(), new CertifiedDialog.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Dialog dialog) {
                        startActivity(new Intent(getActivity(), CompanyCertificationActivity.class));
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                break;
            case R.id.img_friend:

                break;
            case R.id.img_card:

                break;
            case R.id.img_identity:

                break;
            case R.id.more:
//                if (SettingUtils.createCardBefore()) {
//                    startActivity(new Intent(getActivity(), CompanyCardActivity.class));
//                } else {
//                    CreateCompanyCardActivity.startCompanyCard(getActivity());
//                }
                startActivity(new Intent(getActivity(),ManagerClockInActivity.class));
                break;
        }
    }

    //显示成就弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardCompleteEvent(CardCompleteEvent event) {
        if (event.isComplete()) {
            getCardInfo();
            CardCompleteDialog dialog = new CardCompleteDialog(getActivity(), new CardCompleteDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBasicRefreshEvent(BasicRefreshEvent event) {
        if (event.getRefresh()) {
            getCardInfo();
        }
    }


}
