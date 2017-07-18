package com.scdz.wifidemo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.scdz.wifidemo.Invokeutil.invokeStaticMethod;

public class LingActivity extends ActionBarActivity {
    EditText ling_ming;
    TextView ling_res;

    List<String> cmdS = new ArrayList<>();
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ling);
        ling_ming = (EditText) findViewById(R.id.ling_ming);
        ling_res = (TextView) findViewById(R.id.ling_res);
        initDialog();
        invoked();//测试 反射
    }

    public void run(View view) {
        String cmd = ling_ming.getText().toString();
        do_exec(cmd);
        Log.e("LingActivity", cmd + "\n" + new ExeCommand().run(cmd, 10000).getResult());
        ling_res.setText(new ExeCommand().run(cmd, 10000).getResult());
    }

    private void do_exec(String cmd) {
        String line;
        StringBuffer result = new StringBuffer();
        try {
            Process localProcess;
            localProcess = Runtime.getRuntime().exec("su");
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes(cmd + "\n");
            localDataOutputStream.writeBytes("exit\n");

            localDataOutputStream.flush();
            localProcess.waitFor();

            BufferedReader successResult = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
            while ((line = successResult.readLine()) != null) {
                line += "\n";
                result.append(line);
            }
            Log.e("do_exec", new String(result));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initDialog() {
        cmdS.add("ip addr");
        cmdS.add("cat /proc/cpuinfo");//设备信息
        cmdS.add("cat /etc/resolv.conf");//DNS
        cmdS.add("ifconfig");
        cmdS.add("ifconfig eth0");
        cmdS.add("ifconfig eth0 192.168.2.123 netmask 255.255.255.0 up");
        cmdS.add("route add default gw 192.168.0.254");
        cmdS.add("vi /etc/resolv.conf");
        cmdS.add("netstat -rn");
        cmdS.add("uid =0 :root");
        cmdS.add("route -n");
        cmdS.add("apt-get update");
        cmdS.add("apt-get install gcc-multilib lib32z1 lib32stdc++6");
        cmdS.add("huijundeMacBook-Pro:app huijunzhang$ tools/aapt v");
        cmdS.add("-bash: tools/aapt: cannot execute binary file: Exec format error");
        cmdS.add("echo 1e > /sys/devices/platform/rk_time/dogtime");//set dogtime
        cmdS.add("echo 1 > /sys/devices/platform/rk_time/dogen");//start dogtime
        cmdS.add("echo 0 > /sys/devices/platform/rk_time/dogen");//stop dotime
        cmdS.add("echo 30420 > /sys/devices/platform/rk_time/weekstart");//set opentime
        cmdS.add("echo 0 > /sys/devices/platform/rk_time/weekstart");//clear opentime
        cmdS.add("#!/system/bin/sh reboot -p");
        cmdS.add("");


        ListView listView = new ListView(LingActivity.this);
        dialog = new AlertDialog.Builder(LingActivity.this).create();
        dialog.setTitle("点击调用Cmd命令");
        dialog.setView(listView);
        listView.setAdapter(new CmdAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ling_ming.setText(cmdS.get(i));
                dialog.dismiss();
                //执行
                String cmd = ling_ming.getText().toString();
                do_exec(cmd);
                Log.e("LingActivity", cmd + "\n" + new ExeCommand().run(cmd, 10000).getResult());
                ling_res.setText(new ExeCommand().run(cmd, 10000).getResult());
            }
        });
    }

    public void showCmdList(View view) {
        dialog.show();
    }

    class CmdAdapter extends BaseAdapter {

        /**
         * 返回当前有多少个条目
         *
         * @return
         */

        @Override
        public int getCount() {
            return cmdS.size();
        }

        /**
         * 返回当前position位置对应的条目的object对象
         *
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return cmdS.get(position);
        }

        /**
         * 返回当前position位置对应条目的id
         *
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 返回一个条目显示的具体内容
         * 计算当前界面 会有多少个条目出现
         * 1.得到每一个textview的高度
         * 2.得到listview的高度
         * 3.listview高度/textview高度=得到了一个屏幕显示textview的的个数
         * listview的每一个条目的显示都需要调用一次getView的方法
         * 屏幕上有多个item显示就会调用多少getview的方法
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//         TextView textView = new TextView(MyActivity.this);
//         textView.setText(persons.get(position).getName()+":"+persons.get(position).getAge());
//         return textView;
            LayoutInflater inflater = (LayoutInflater) LingActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.simple_item, null);
            TextView spitem_tx = (TextView) view.findViewById(R.id.spitem_tx);
            spitem_tx.setText(cmdS.get(position));
            return view;
        }
    }

    private void invoked() {
        Object mEthManager = null;
        Object mInterfaceInfo = null;
        try {
            mEthManager = invokeStaticMethod("android.net.ethernet.EthernetManager", "getInstance", null);
            Invokeutil.invokeMethod("android.net.ethernet.EthernetManager", mEthManager, "setEnabled", new Object[]{true});
            mInterfaceInfo = Invokeutil.invokeMethod("android.net.ethernet.EthernetManager", mEthManager, "getSavedConfig", null);

            Object invokeret = null;
            invokeret = Invokeutil.invokeMethod("android.net.ethernet.EthernetDevInfo", mInterfaceInfo, "getIpAddress", null);

            String ip = invokeret.toString();

            invokeret = Invokeutil.invokeMethod("android.net.ethernet.EthernetDevInfo", mInterfaceInfo, "getNetMask", null);
            String mask = invokeret.toString();

            invokeret = Invokeutil.invokeMethod("android.net.ethernet.EthernetDevInfo", mInterfaceInfo, "getGateWay", null);
            String gate = invokeret.toString();

            invokeret = Invokeutil.invokeMethod("android.net.ethernet.EthernetDevInfo", mInterfaceInfo, "getDnsAddr", null);
            String dns = invokeret.toString();

            invokeret = Invokeutil.invokeMethod("android.net.ethernet.EthernetDevInfo", mInterfaceInfo, "getHwaddr", null);
            String mac = " ";
            String ret = ip + ";" + mask + ";" + gate + ";" + dns + ";" + mac;
            Log.e("invoked", ret);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("invoked(error)", e.getMessage() + "|InvocationTargetException");
        }

    }
}

