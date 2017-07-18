package com.scdz.nethandler;

/**
 * Created by ScDz on 2017/7/18.
 * ClassNote:网络连接类型及强度
 */

public class NetState {
    //网络连接状态 1:GPRS 2:wifi 3:Net 0:无连接
    int status;
    //信号强度 0~100
    int level;

    public NetState(int status, int level) {
        this.status = status;
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
