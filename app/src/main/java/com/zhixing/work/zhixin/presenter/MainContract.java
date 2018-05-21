package com.zhixing.work.zhixin.presenter;

import com.zhixing.work.zhixin.bean.User;

import java.util.Observable;



/**
 * Created by Administrator on 2018/5/8.
 */

public class MainContract {
    //View 的接口
    public interface MainView {
        void showData(User retDataBean);

        void showProgressBar();
    }

    //presenter的接口
    public interface MainPresenter {
        void getMainData();
    }

    //Model的接口
    interface MainModel {
        Observable loadMainData();
    }
}
