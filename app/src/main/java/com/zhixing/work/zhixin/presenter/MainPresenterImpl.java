package com.zhixing.work.zhixin.presenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2018/5/8.
 */


public class MainPresenterImpl implements MainContract.MainPresenter {
    private MainContract.MainView mMainView;
    private MainContract.MainModel mMainModel;
    @Inject
    public MainPresenterImpl(MainContract.MainView mainView) {
        mMainView = mainView;

    }
    @Override
    public void getMainData() {

    }
}
