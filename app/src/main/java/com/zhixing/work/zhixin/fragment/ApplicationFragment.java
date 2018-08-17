package com.zhixing.work.zhixin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.BaseMainFragment;
import com.zhixing.work.zhixin.constant.ResultConstant;
import com.zhixing.work.zhixin.util.GlideUtils;
import com.zhixing.work.zhixin.util.ResourceUtils;
import com.zhixing.work.zhixin.util.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主页-应用
 */
public class ApplicationFragment extends BaseMainFragment {


    @BindView(R.id.image)
    ImageView image;

    public static ApplicationFragment newInstance() {
        Bundle args = new Bundle();
        ApplicationFragment fragment = new ApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appicon, container, false);
        ButterKnife.bind(this, view);
        if (SettingUtils.getTokenBean().getRole() == ResultConstant.USER_TYPE_PERSONAL) {
            if (SettingUtils.getTokenBean().getCompanyId() == 0) {
                GlideUtils.getInstance().loadImage(getActivity(), ResourceUtils.getDrawable(R.drawable.img_application_out), image);
            } else {
                GlideUtils.getInstance().loadImage(getActivity(), ResourceUtils.getDrawable(R.drawable.img_application_in), image);
            }
        } else {
            GlideUtils.getInstance().loadImage(getActivity(), ResourceUtils.getDrawable(R.drawable.img_application_company), image);
        }

        return view;

    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
