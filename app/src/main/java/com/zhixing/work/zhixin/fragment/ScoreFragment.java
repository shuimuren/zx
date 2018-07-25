package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.constant.RoleConstant;
import com.zhixing.work.zhixin.util.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 评分页面
 */
public class ScoreFragment extends BaseMainFragment {

    Unbinder unbinder;
    @BindView(R.id.title)
    TextView title;
    private Token mToken;
    private PersonalScoreFragment mPersonalScoreFragment;
    private CompanyScoreFragment mCompanyScoreFragment;

    public static ScoreFragment newInstance() {
        Bundle args = new Bundle();
        ScoreFragment fragment = new ScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mToken = SettingUtils.getTokenBean();
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        unbinder = ButterKnife.bind(this, view);
        title.setText("职业卡牌");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToken.getRole() == Integer.parseInt(RoleConstant.PERSONAL_ROLE)) {
            if (mPersonalScoreFragment == null) {
                mPersonalScoreFragment = PersonalScoreFragment.newInstance();
            }
            loadRootFragment(R.id.frame_score, mPersonalScoreFragment);
        } else {
            if (mCompanyScoreFragment == null) {
                mCompanyScoreFragment = CompanyScoreFragment.newInstance();
            }
            loadRootFragment(R.id.frame_score, mCompanyScoreFragment);
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
