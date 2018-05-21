package com.zhixing.work.zhixin.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.reflect.TypeToken;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.bean.Card;
import com.zhixing.work.zhixin.bean.EntityObject;
import com.zhixing.work.zhixin.dialog.CardCompleteDialog;
import com.zhixing.work.zhixin.event.CardCompleteEvent;
import com.zhixing.work.zhixin.event.CardRefreshEvent;
import com.zhixing.work.zhixin.http.JavaConstant;
import com.zhixing.work.zhixin.http.JavaParamsUtils;
import com.zhixing.work.zhixin.http.okhttp.OkUtils;
import com.zhixing.work.zhixin.http.okhttp.ResultCallBackListener;
import com.zhixing.work.zhixin.util.AlertUtils;
import com.zhixing.work.zhixin.util.SettingUtils;
import com.zhixing.work.zhixin.view.card.CardCounterActivity;
import com.zhixing.work.zhixin.view.card.CreateCardActivity;
import com.zhixing.work.zhixin.view.card.PerfectCardDataActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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

    private Unbinder unbinder;
    private Context context;
    private Card card;
    private String study_abroad;

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

        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        EventBus.getDefault().register(this);
        initData();
        return view;
    }

    private void initData() {
        OkUtils.getInstances().httpTokenGet(context, JavaConstant.card, JavaParamsUtils.getInstances().getCard(), new TypeToken<EntityObject<Card>>() {
        }.getType(), new ResultCallBackListener<Card>() {
            @Override
            public void onFailure(int errorId, String msg) {
                rlCord.setVisibility(View.VISIBLE);
                rlData.setVerticalGravity(View.GONE);
                AlertUtils.toast(context, "服务器错误");
            }

            @Override
            public void onSuccess(EntityObject<Card> response) {
                if (response.getCode() == 10000) {
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

                        if (TextUtils.isEmpty(card.getMotto())) {

                            llMotto.setVisibility(View.GONE);

                        } else {
                            llMotto.setVisibility(View.VISIBLE);
                            motto.setText(card.getMotto());
                        }
                    }

                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.create_card, R.id.perfect_card, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_card:
                startActivity(new Intent(context, CreateCardActivity.class));
                break;
            case R.id.perfect_card:
                startActivity(new Intent(context, PerfectCardDataActivity.class));
                break;
            case R.id.more:
                startActivity(new Intent(context, CardCounterActivity.class));
                break;
        }
    }

    //显示成就弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardCompleteEvent(CardCompleteEvent event) {
        if (event.isComplete()) {
            initData();
            CardCompleteDialog dialog = new CardCompleteDialog(context, new CardCompleteDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    //显示成就弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardRefreshEvent(CardRefreshEvent event) {
        if (event.getRefresh()) {
            initData();
        }

    }
}
