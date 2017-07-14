package com.scdz.wifidemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by ScDz on 2017/7/14.
 * ClassNote:
 */

public class ShutDownActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fun1();
        fun2();
    }

    private void fun1() {
        String ACTION_REBOOT = "android.intent.action.REBOOT";
        final String ACTION_REQUEST_SHUTDOWN = "android.intent.action.ACTION_REQUEST_SHUTDOWN";

    }

    private void fun2() {
    }
}
