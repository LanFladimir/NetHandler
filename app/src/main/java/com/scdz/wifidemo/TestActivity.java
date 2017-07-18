package com.scdz.wifidemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.scdz.nethandler.NetState;
import com.scdz.nethandler.NetStateHandler;

public class TestActivity extends ActionBarActivity {
    TextView test_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test_show = (TextView) findViewById(R.id.test_show);
        test_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetState state = NetStateHandler.getNetState(TestActivity.this);
                test_show.setText("状态" + state.getStatus() + "\n" + "连接强度" + state.getLevel());
            }
        });

    }

}
