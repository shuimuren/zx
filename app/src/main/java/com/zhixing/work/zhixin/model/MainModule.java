package com.zhixing.work.zhixin.model;


import com.zhixing.work.zhixin.presenter.MainContract;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MainModule {
    private MainContract.MainView mMainView;
    //Module的构造器，传入一个MainView， 提供给Component
    public MainModule(MainContract.MainView mMainView) {
        this.mMainView = mMainView;
    }
    //Provides注解代表提供的参数，为构造器传进来的
//    @Provides
//    public MainContract.MainView inject(){
//        return mMainView;
//    };
}
