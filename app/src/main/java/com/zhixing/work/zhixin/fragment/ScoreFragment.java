package com.zhixing.work.zhixin.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.alibaba.sdk.android.oss.ServiceException;
import com.google.gson.reflect.TypeToken;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.aliyun.ALiYunFileURLBuilder;
import com.zhixing.work.zhixin.aliyun.ALiYunOssFileLoader;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.bean.Card;
import com.zhixing.work.zhixin.bean.CompanyCard;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.bean.StsToken;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.dialog.CardCompleteDialog;
import com.zhixing.work.zhixin.dialog.SelectImageDialog;
import com.zhixing.work.zhixin.domain.AlbumItem;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.event.CardRefreshEvent;
import com.zhixing.work.zhixin.event.ResumeRefreshEvent;
import com.zhixing.work.zhixin.http.Constant;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.AppUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.BitmapUtils;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.LOG;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.util.Utils;
import com.zhixing.work.zhixin.view.card.CreateCardActivity;
import com.zhixing.work.zhixin.view.card.PerfectCardDataActivity;
import com.zhixing.work.zhixin.view.card.back.CardMainActivity;
import com.zhixing.work.zhixin.view.companyCard.CreateCompanyCardActivity;
import com.zhixing.work.zhixin.view.companyCard.back.CompanyCardActivity;
import com.zhixing.work.zhixin.view.util.SelectImageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 *
 */
public class ScoreFragment extends BaseMainFragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.create_card)
    Button createCard;
    @BindView(R.id.img_card)
    ImageView img_card;
    @BindView(R.id.img_friend)
    ImageView imgFriend;
    @BindView(R.id.img_identity)
    ImageView imgIdentity;
    @BindView(R.id.perfect_card)
    Button perfectCard;
    @BindView(R.id.no_cord)
    LinearLayout noCord;
    @BindView(R.id.rl_cord)
    RelativeLayout rlCord;
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
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.stars)
    ScaleRatingBar stars;
    @BindView(R.id.have_cord)
    RelativeLayout haveCord;
    @BindView(R.id.type_iv)
    ImageView typeIv;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.nikename)
    TextView nikename;
    @BindView(R.id.motto)
    TextView motto;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.ll_nike_name)
    LinearLayout llNikeName;
    @BindView(R.id.ll_motto)
    LinearLayout llMotto;
    @BindView(R.id.default_avatar)
    ImageView defaultAvatar;
    @BindView(R.id.avatar_text)
    TextView avatarText;
    @BindView(R.id.constellation)
    ImageView constellation;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.create_enterprise_card)
    Button createEnterpriseCard;
    @BindView(R.id.rl_enterprise_nocard)
    RelativeLayout rlEnterpriseNocard;
    @BindView(R.id.enterprise_avatar)
    ImageView enterpriseAvatar;
    @BindView(R.id.enterprise_default_avatar)
    ImageView enterpriseDefaultAvatar;
    @BindView(R.id.enterprise_avatar_text)
    TextView enterpriseAvatarText;
    @BindView(R.id.rl_enterprise_avatar)
    RelativeLayout rlEnterpriseAvatar;
    @BindView(R.id.company_address)
    TextView companyAddress;
    @BindView(R.id.ll_company_address)
    LinearLayout llCompanyAddress;
    @BindView(R.id.enterprise_data)
    LinearLayout enterpriseData;
    @BindView(R.id.enterprise_cord)
    RelativeLayout enterpriseCord;
    @BindView(R.id.enterprise_stars)
    ScaleRatingBar enterpriseStars;
    @BindView(R.id.enterprise_iv)
    ImageView enterpriseIv;
    @BindView(R.id.rl_enterprise_data)
    RelativeLayout rlEnterpriseData;
    @BindView(R.id.ll_enterprise)
    LinearLayout llEnterprise;
    @BindView(R.id.company_name)
    TextView companyName;

    private Unbinder unbinder;
    private Context context;
    private Card card;
    private CompanyCard companyCard;
    private String study_abroad;

    private StsToken stsToken;
    public static final int REQUEST_CAMERA = 10; // 拍照

    private File photoFile;
    private Uri imageUri;
    private List<AlbumItem> selectedImages;//标记选中的图片
    private String upLoadImages = "";//上传图片组
    private File cropFilePath;
    private Uri outPutUri;
    public static final String TAG = "ScoreFragment";

    public static ScoreFragment instance;
    public Token token;
    private String addressct = "";

    public static ScoreFragment newInstance() {
        Bundle args = new Bundle();
        ScoreFragment fragment = new ScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        instance = this;
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        EventBus.getDefault().register(this);
        token = SettingUtils.getTokenBean();
        if (token.getRole() == 10) {
            initData();
            llUser.setVisibility(View.VISIBLE);
            llEnterprise.setVisibility(View.GONE);
        } else {
            getCompanyData();
            llEnterprise.setVisibility(View.VISIBLE);
            llUser.setVisibility(View.GONE);
        }
        return view;
    }

    private void getCompanyData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.CompanyCard, JavaParamsUtils.getInstances().getCompanyCard(), new TypeToken<EntityObject<CompanyCard>>() {
        }.getType(), new ResultCallBackListener<CompanyCard>() {
            @Override
            public void onFailure(int errorId, String msg) {
                rlEnterpriseNocard.setVisibility(View.VISIBLE);
                rlEnterpriseData.setVisibility(View.GONE);
                AlertUtils.toast(context, "服务器错误");
                getOssToken();
            }

            @Override
            public void onSuccess(EntityObject<CompanyCard> response) {
                if (response.getCode() == 10000) {
                    getOssToken();
                    if (response.getContent() == null) {
                        rlEnterpriseNocard.setVisibility(View.VISIBLE);
                        rlEnterpriseData.setVisibility(View.GONE);
                    } else {
                        rlEnterpriseNocard.setVisibility(View.GONE);
                        rlEnterpriseData.setVisibility(View.VISIBLE);
                        companyCard = response.getContent();
                        if (companyCard.getProvince() != null) {
                            addressct = Utils.searchProvincial(companyCard.getProvince());
                        }
                        if (companyCard.getCity() != null) {
                            addressct = addressct + Utils.searchCity(companyCard.getCity());
                        }
                        if (companyCard.getDistrict() != null) {
                            addressct = addressct + Utils.searchArea(companyCard.getDistrict());
                        }
                        if (TextUtils.isEmpty(companyCard.getAddress())) {
                            companyAddress.setText(addressct);
                        } else {
                            companyAddress.setText(addressct + companyCard.getAddress());
                        }
                        companyName.setText(companyCard.getFullName());
                    }

                }
            }
        });
    }

    private void initData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.card, JavaParamsUtils.getInstances().getCard(), new TypeToken<EntityObject<Card>>() {
        }.getType(), new ResultCallBackListener<Card>() {
            @Override
            public void onFailure(int errorId, String msg) {
                rlCord.setVisibility(View.VISIBLE);
                rlData.setVerticalGravity(View.GONE);
                AlertUtils.toast(context, "服务器错误");
                getOssToken();
            }

            @Override
            public void onSuccess(EntityObject<Card> response) {
                if (response.getCode() == 10000) {
                    getOssToken();
                    if (response.getContent() == null) {
                        rlCord.setVisibility(View.VISIBLE);
                        rlData.setVisibility(View.GONE);
                    } else {
                        card = response.getContent();
                        rlCord.setVisibility(View.GONE);
                        rlData.setVisibility(View.VISIBLE);
                        mail.setText(card.getEmail());
                        phone.setText(SettingUtils.getPhoneNumber());
                        name.setText(card.getRealName());
                        img_card.setImageResource(R.drawable.icon_1_light);
                        if (TextUtils.isEmpty(card.getNickName())) {
                            llNikeName.setVisibility(View.GONE);
                        } else {
                            llNikeName.setVisibility(View.VISIBLE);
                            nikename.setText(card.getNickName());
                        }
                        if (card.getConstellation() != null) {
                            constellation.setImageResource(Utils.getConstellationImage(card.getConstellation()));
                        }
                        if (TextUtils.isEmpty(card.getMotto())) {
                            llMotto.setVisibility(View.GONE);
                        } else {
                            llMotto.setVisibility(View.VISIBLE);
                            motto.setText(card.getMotto());
                        }
                        if (TextUtils.isEmpty(card.getAvatar())) {
                            avatar.setVisibility(View.GONE);
                            defaultAvatar.setVisibility(View.VISIBLE);
                            avatarText.setVisibility(View.VISIBLE);
                        } else {
                            avatar.setVisibility(View.VISIBLE);
                            defaultAvatar.setVisibility(View.GONE);
                            avatarText.setVisibility(View.GONE);
                        }
                    }

                }
            }
        });
    }

    @Override
    public void fetchData() {
    }

    //获取阿里云的凭证
    private void getOssToken() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.getOSS, JavaParamsUtils.getInstances().getOSS(), new TypeToken<EntityObject<StsToken>>() {
        }.getType(), new ResultCallBackListener<StsToken>() {
            @Override
            public void onFailure(int errorId, String msg) {
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<StsToken> response) {
                if (response.getCode() == 10000) {
                    stsToken = response.getContent();
                    if (card != null) {
                        if (!TextUtils.isEmpty(card.getAvatar())) {
                            String url = ALiYunOssFileLoader.gtePublic(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, card.getAvatar());
                            GlideUtils.getInstance().loadGlideRoundTransform(context, url, avatar);
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.create_card, R.id.perfect_card, R.id.more, R.id.rl_avatar, R.id.create_enterprise_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_card:
                startActivity(new Intent(context, CreateCardActivity.class));
                break;
            case R.id.create_enterprise_card:
                startActivity(new Intent(context, CreateCompanyCardActivity.class));
                break;
            case R.id.perfect_card:
                startActivity(new Intent(context, PerfectCardDataActivity.class));
                break;
            case R.id.more:
                if (token.getRole() == 10) {
                    startActivity(new Intent(context, CardMainActivity.class));
                } else {
                    startActivity(new Intent(context, CompanyCardActivity.class));
                }
                ////
                break;
            case R.id.rl_avatar:
                SelectImageDialog imageDialog = new SelectImageDialog(context, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();

                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                photoFile = new File(Constant.CACHE_DIR_IMAGE + "/" + System.currentTimeMillis() + ".jpg");
                                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
                                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                instance.startActivityForResult(intentCamera, REQUEST_CAMERA);
                                break;
                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(context, SelectImageActivity.class);
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                instance.startActivityForResult(intent, SelectImageActivity.REQUEST_AVATAR);
                                break;
                        }
                    }
                });
                imageDialog.show();
                break;
        }
    }

    //显示成就弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardCompleteEvent(CardCompleteEvent event) {
        if (event.isComplete()) {
            if (token.getRole() == 10) {
                initData();
            } else {
                getCompanyData();
            }
            CardCompleteDialog dialog = new CardCompleteDialog(context, new CardCompleteDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    //刷新数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardRefreshEvent(CardRefreshEvent event) {
        if (event.getRefresh()) {
            if (token.getRole() == 10) {
                initData();
            } else {
                getCompanyData();
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cropFilePath != null) {
            if (!TextUtils.isEmpty(cropFilePath.getAbsolutePath())) {
                Utils. deleteDirWihtFile(new File(Environment.getExternalStorageDirectory().getPath()));
            }
        }
    }
    /**
     * 初始化剪裁图片的输出Uri
     */
    private void intCropUri() {
        if (outPutUri == null) {
            cropFilePath = new File(Environment.getExternalStorageDirectory().getPath(), "cutImage.png");
            try {
                cropFilePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outPutUri = Uri.fromFile(cropFilePath);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == SelectImageActivity.REQUEST_AVATAR) {
            //从相册选照片
            selectedImages = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            AlbumItem albumItem = selectedImages.get(0);
            imageUri = Uri.fromFile(new File(albumItem.getFilePath()));
            intCropUri();
            BitmapUtils.createPhotoCropFragment(instance, imageUri, outPutUri);
        } else if (requestCode == REQUEST_CAMERA) {
            //拍照照片
            imageUri = Uri.fromFile(photoFile);
            intCropUri();
            BitmapUtils.createPhotoCropFragment(instance, imageUri, outPutUri);
        } else if (requestCode == Constant.IMAGE_CROP) {
            upload(cropFilePath.getAbsolutePath());

        }

    }


    /*保存界面状态，如果activity意外被系统killed，返回时可以恢复状态值*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putString("msg_con", htmlsource);
        if (photoFile != null) {
            if (photoFile.getAbsolutePath() != null) {
                savedInstanceState.putString("msg_camera_picname", photoFile.getAbsolutePath());
            }
        }

        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        photoFile = new File(savedInstanceState.getString("msg_camera_picname"));
    }


    //上传头像
    private void upload(final String resultpath) {
        String UUID = AppUtils.getUUID();
        ALiYunOssFileLoader.asyncUpload(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, ALiYunFileURLBuilder.PERSONALAVATAR + UUID,
                resultpath, new ALiYunOssFileLoader.OssFileUploadListener() {
                    @Override
                    public void onUploadSuccess(String objectKey) {
                        LOG.i("tou", "动态图片上传成功：" + objectKey);
                        RequestBody body = new FormBody.Builder()
                                .add("Avatar", objectKey)
                                .build();

                        upAvatar(body, objectKey);
                    }

                    @Override
                    public void onUploadProgress(String objectKey, long currentSize, long totalSize) {
                    }

                    @Override
                    public void onUploadFailure(String objectKey, ServiceException ossException) {
                        LOG.i(TAG, "动态图片上传失败：" + objectKey);
                    }
                });
    }

    private void upAvatar(RequestBody body, final String key) {
        OkUtils.getInstances().httpatch(body, context, JavaConstant.Avatar, JavaParamsUtils.getInstances().resumeAvatar(), new TypeToken<EntityObject<Boolean>>() {
        }.getType(), new ResultCallBackListener<Boolean>() {
            @Override
            public void onFailure(int errorId, String msg) {
                hideLoadingDialog();
                AlertUtils.toast(context, msg);
            }

            @Override
            public void onSuccess(EntityObject<Boolean> response) {
                hideLoadingDialog();
                if (response.getCode() == 10000) {
                    if (response.getContent() != null && response.getContent()) {
                        if (response.getContent()) {
                            EventBus.getDefault().post(new ResumeRefreshEvent(true));
                            String url = ALiYunOssFileLoader.gtePublic(context, stsToken, ALiYunFileURLBuilder.BUCKET_PUBLIC, key);
                            avatarText.setVisibility(View.GONE);
                            defaultAvatar.setVisibility(View.GONE);
                            GlideUtils.getInstance().loadGlideRoundTransform(context, url, avatar);
                        }
                    } else {
                        AlertUtils.toast(context, "修改失败");
                    }

                } else {
                    AlertUtils.toast(context, response.getMessage());
                }


            }
        });

    }

}
