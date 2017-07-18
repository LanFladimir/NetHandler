package com.scdz.nethandler;

import android.content.Context;

/**
 * Created by ScDz on 2017/7/18.
 * ClassNote:
 */

public class NetManager {
    private static NetManager manager;
    private static Context mContext;

    public static NetManager getInstance(Context context) {
        manager = new NetManager();
        mContext = context;
        return manager;
    }

    public void setIp() {
        NetStateHandler.getNetState(mContext);
    }
}
