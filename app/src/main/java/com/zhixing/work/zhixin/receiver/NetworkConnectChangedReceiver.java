package com.zhixing.work.zhixin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lhj on 2018/7/7.
 * Description: 系统广播接受
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    //TYPE_MOBILE ,TYPE_WIFI,
    public static final int NETWORK_TYPE_NONE = -2;
    public static final int NETWORK_TYPE_CONNECTED_FAILED = -3;

    private NetWorkStatusListener netWorkStatusListener;

    public static NetworkConnectChangedReceiver getInstance() {
        return ReceiverHolder.networkConnectChangedReceiver;
    }

    public void setNetWorkStatusListener(NetWorkStatusListener listener) {
        this.netWorkStatusListener = listener;
    }

    private static class ReceiverHolder {
        public static final NetworkConnectChangedReceiver networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //网络连接管理
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null && netWorkStatusListener != null) {
                if (NetworkInfo.State.CONNECTED == info.getState()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
//                        WifiManager wifiManager = (WifiManager) ZxApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                        Logger.i(">>>", "bssId>" + wifiInfo.getBSSID());
//                        Logger.i(">>>", "macAddress" + wifiInfo.getMacAddress());
//                        Logger.i(">>>", "wifiInfo>" + wifiInfo.toString());
                        netWorkStatusListener.netWordStatusAndUsable(ConnectivityManager.TYPE_WIFI, info.isAvailable());
                    } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        netWorkStatusListener.netWordStatusAndUsable(ConnectivityManager.TYPE_MOBILE, info.isAvailable());
                    }
                } else {
                    netWorkStatusListener.netWordStatusAndUsable(NETWORK_TYPE_CONNECTED_FAILED, info.isAvailable());
                }
            } else {
                netWorkStatusListener.netWordStatusAndUsable(NETWORK_TYPE_NONE, false);
            }
//            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//
//                //获得ConnectivityManager对象
//                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                //获取WIFI连接的信息
//                NetworkInfo networkInfo;
//                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                //获取移动数据连接的信息
//                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                    Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//                } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//                    Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
//                } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                    Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
//                }
//                //API大于23时使用下面的方式进行网络监听
//            } else {
//
//                System.out.println("API level 大于23");
//                //获得ConnectivityManager对象
//                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                //获取所有网络连接的信息
//                Network[] networks = connMgr.getAllNetworks();
//                //用于存放网络连接信息
//                StringBuilder sb = new StringBuilder();
//                //通过循环将网络信息逐个取出来
//                for (int i = 0; i < networks.length; i++) {
//                    //获取ConnectivityManager对象对应的NetworkInfo对象
//                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
//                }
//              //  Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
//            }
        }

    }


}
