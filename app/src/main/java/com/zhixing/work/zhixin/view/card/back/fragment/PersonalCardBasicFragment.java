package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;
import com.zhixing.work.zhixin.bean.PersonalCardBasicInfoBean;
import com.zhixing.work.zhixin.common.Logger;
import com.zhixing.work.zhixin.msgctrl.MsgDef;
import com.zhixing.work.zhixin.msgctrl.MsgDispatcher;
import com.zhixing.work.zhixin.msgctrl.RxBus;
import com.zhixing.work.zhixin.network.NetworkConstant;
import com.zhixing.work.zhixin.network.response.PersonalCardBasicInfoResult;
import com.zhixing.work.zhixin.util.AlertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by lhj on 2018/7/14.
 * Description: 个人卡基础信息
 */

public class PersonalCardBasicFragment extends SupportFragment {

    @BindView(R.id.basics)
    TextView basics;
    @BindView(R.id.senior)
    TextView senior;
    @BindView(R.id.flCardContent)
    FrameLayout flCardContent;
    @BindView(R.id.llCardContent)
    LinearLayout llCardContent;
    @BindView(R.id.btnPerfectCard)
    Button btnPerfectCard;
    @BindView(R.id.llCardContentEmpty)
    LinearLayout llCardContentEmpty;
    Unbinder unbinder;

    public static PersonalCardBasicFragment newInstance() {
        Bundle args = new Bundle();
        PersonalCardBasicFragment fragment = new PersonalCardBasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SupportFragment[] mFragments = new SupportFragment[2];
    private static final int PERSONAL_CARD_BASIC_INFO = 0;
    private static final int PERSONAL_CARD_ADVANCED_INFO = 1;
    private PersonalCardBasicInfoBean result;
    private Subscription cardInfoSubscription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_card_basic, container, false);
        cardInfoSubscription = RxBus.getInstance().toObservable(PersonalCardBasicInfoResult.class).subscribe(
                result1 -> handlerResultInfo(result1)
        );
        MsgDispatcher.dispatchMessage(MsgDef.MSG_DEF_GET_PERSONAL_CARD_BASIC_INFO);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void handlerResultInfo(PersonalCardBasicInfoResult resultInfo) {
        if (resultInfo.Code == NetworkConstant.SUCCESS_CODE) {
            result = resultInfo.getContent();
            if (result != null && !TextUtils.isEmpty(result.getNickName())) {
                llCardContent.setVisibility(View.VISIBLE);
            } else {
                llCardContentEmpty.setVisibility(View.VISIBLE);
                llCardContent.setVisibility(View.GONE);
            }
        } else {
            AlertUtils.show(resultInfo.Message);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChildFragment();
    }

    private void initChildFragment() {
        SupportFragment firstFragment = findChildFragment(PersonalCardBasicInfoFragment.class);
        if (firstFragment == null) {
            mFragments[PERSONAL_CARD_BASIC_INFO] = PersonalCardBasicInfoFragment.newInstance();
            mFragments[PERSONAL_CARD_ADVANCED_INFO] = PersonalCardAdvancedInfoFragment.newInstance();
            loadMultipleRootFragment(R.id.flCardContent, PERSONAL_CARD_BASIC_INFO,
                    mFragments[PERSONAL_CARD_BASIC_INFO],
                    mFragments[PERSONAL_CARD_ADVANCED_INFO]);

        } else {
            mFragments[PERSONAL_CARD_BASIC_INFO] = firstFragment;
            mFragments[PERSONAL_CARD_ADVANCED_INFO] = findChildFragment(PersonalCardAdvancedInfoFragment.class);
        }
    }

    @Override
    public void fetchData() {
        Logger.i(">>>", "fetchData");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        RxBus.getInstance().unsubscribe(cardInfoSubscription);
    }

    @OnClick({R.id.basics, R.id.senior, R.id.btnPerfectCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.basics:
                showHideFragment(mFragments[PERSONAL_CARD_BASIC_INFO], mFragments[PERSONAL_CARD_ADVANCED_INFO]);
                break;
            case R.id.senior:
                showHideFragment(mFragments[PERSONAL_CARD_ADVANCED_INFO], mFragments[PERSONAL_CARD_BASIC_INFO]);
                break;
            case R.id.btnPerfectCard:

                break;
        }
    }
}
