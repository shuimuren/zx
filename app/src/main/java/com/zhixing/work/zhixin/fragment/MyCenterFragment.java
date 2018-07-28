package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.bean.Token;
import com.zhixing.work.zhixin.constant.DiscernConstant;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 主页-我的
 */
public class MyCenterFragment extends BaseMainFragment {

    @BindView(R.id.frameCenter)
    FrameLayout frameCenter;
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;

    private Token mToken;
    private PersonalCenterFragment mPersonalCenterFragment;
    private CompanyCenterFragment mCompanyCenterFragment;

    public static MyCenterFragment newInstance() {
        Bundle args = new Bundle();
        MyCenterFragment fragment = new MyCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        title.setText(ResourceUtils.getString(R.string.my_center));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToken = SettingUtils.getTokenBean();
        if (mToken.getRole() == Integer.parseInt(DiscernConstant.PERSONAL_ROLE)) {
            if (mPersonalCenterFragment == null) {
                mPersonalCenterFragment = PersonalCenterFragment.newInstance();
            }
            loadRootFragment(R.id.frameCenter, mPersonalCenterFragment);
        } else {
            if (mCompanyCenterFragment == null) {
                mCompanyCenterFragment = CompanyCenterFragment.newInstance();
            }
            loadRootFragment(R.id.frameCenter, mCompanyCenterFragment);
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
