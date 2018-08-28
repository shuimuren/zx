package com.zhixing.work.zhixin.view.card.back.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhixing.work.zhixin.R;
import com.zhixing.work.zhixin.base.SupportFragment;

/**
 * Created by lhj on 2018/8/27.
 * Description:职信月评-排行榜
 */

public class RankingStatementFragment extends SupportFragment {

    public static RankingStatementFragment getInstance(){
        RankingStatementFragment fragment = new RankingStatementFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ranking_statement,container,false);
    }

    @Override
    public void fetchData() {

    }
}
