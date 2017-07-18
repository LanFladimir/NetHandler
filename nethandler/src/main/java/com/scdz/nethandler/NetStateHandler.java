package com.scdz.nethandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ScDz on 2017/7/18.
 * ClassNote:
 */

public class NetStateHandler {
    public NetStateHandler() {

    }

    /**
     * 获取网络状态，返回网络状态Entity
     *
     * @return
     */
    public static NetState getNetState(Context context) {
        NetState state = new NetState(0, 0);
        if (!isConnected(context)) {
            return state;
        } else {
            if (isWifiConnected(context)) {
                Toast.makeText(context, "Wifi开启中~~~", Toast.LENGTH_SHORT).show();
                /*state.setLevel(getWifiLevel(context));
                state.setStatus(2);*/
            }


            return state;
        }
    }

    /**
     * 获取wifi信号强度
     *
     * @return
     */
    private static int getWifiLevel(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResult = manager.getScanResults();
        for (ScanResult result : scanResult) {
            Log.e("ScanResult", "level" + result.level);
            Log.e("ScanResult", "SSID" + result.SSID);
        }
        return manager.getScanResults().get(0).level;
    }

    /**
     * 是否有网络连接
     *
     * @param context
     * @return
     */
    private static boolean isConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        } else return false;
    }

    private static boolean isWifiConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable())
            return true;
        else return false;
    }
}
