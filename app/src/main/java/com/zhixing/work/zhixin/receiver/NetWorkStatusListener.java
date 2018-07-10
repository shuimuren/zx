package com.zhixing.work.zhixin.receiver;

/**
 * Created by lhj on 2018/7/7.
 * Description: 网络状态监听器
 */

public interface NetWorkStatusListener {
    void netWordStatusAndUsable(int status, boolean usable);
}
