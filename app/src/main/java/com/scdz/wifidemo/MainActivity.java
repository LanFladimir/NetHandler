package com.scdz.wifidemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.scdz.wifidemo.Invokeutil.invokeStaticMethod;

public class MainActivity extends Activity {
    private Context mContext;
    TextView wifi_info;
    TextView linux_info;
    Button change;
    EditText et1, et2, et3, et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        change = (Button) findViewById(R.id.change);
        wifi_info = (TextView) findViewById(R.id.wifi_info);
        linux_info = (TextView) findViewById(R.id.linux_info);

<<<<<<< HEAD
        //getInfo();
=======
        getInfo();
>>>>>>> ec3dcff571d1887abad189557e6c3915ce883861

        wifi_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
//                getInfo();
                invoked();
=======
                getInfo();
>>>>>>> ec3dcff571d1887abad189557e6c3915ce883861
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIpInfo();
            }
        });


        //Test For Cmd
        List<String> cmdList = new ArrayList<>();
        cmdList.add("ip addr");
        cmdList.add("ifconfig");
        cmdList.add("ifconfig eth0");
        cmdList.add("ip addr show");
        for (String s : cmdList) {
            do_exec(s);
//            do_exec2(s);
            String resoult = new ExeCommand().run(s, 10000).getResult();
            Log.e("ExeCommand---" + s + "\n", resoult);
        }
    }

    /**
     * 修改
     */
    private void changeIpInfo() {
        String ip = et1.getText().toString();
        String mask = et2.getText().toString();
        /*new ExeCommand().run("ifconfig eth0 192.168.5.12 netmask 255.255.255.0", 10000);
        new ExeCommand().run("route add default gw 192.168.0.1 dev eth0", 10000);
        new ExeCommand().run("ifconfig eth0 192.168.0.173 netmask 255.0.0.0", 10000);
        */
        //new ExeCommand().run("ifconfig eth0 192.168.5.13 netmask 255.255.255.0", 10000);
        do_exec2("ifconfig eth0 " + ip + " netmask " + mask);
        //do_exec("route add default gw 192.168.0.1 dev eth0");
        getInfo();
    }

    private void getInfo() {
        //Wifi
        WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        DhcpInfo dInfo = manager.getDhcpInfo();
        wifi_info.setText("Ip:" + intToIp(wifiInfo.getIpAddress()) + "\n" +
                "Mac:" + wifiInfo.getMacAddress() + "\n" +
                "MASK:" + dInfo.netmask + "\n" +
                "Gateway:" + dInfo.gateway + "\n" +
                "DNS1:" + dInfo.dns1 + "\n" +
                "DNS2:" + dInfo.dns2 + "\n" +
                "BSSID:" + wifiInfo.getBSSID() + "\n" +
                "HiddenSSID:" + wifiInfo.getHiddenSSID() + "\n" +
                "Speed:" + wifiInfo.getLinkSpeed() + "\n" +
                "NetWork:" + wifiInfo.getNetworkId() + "\n" +
                "Rssi:" + wifiInfo.getRssi() + "\n" +
                "SupplicantState:" + wifiInfo.getSupplicantState() + "\n" +
                "SSID:" + wifiInfo.getSSID());
        //Linux
       /* String cmd = do_exec("ip addr");
        String ipInfo = cmd.substring(cmd.indexOf("BROADCAST"));

        String ips = ipInfo.substring(ipInfo.indexOf("192"));
        String brd = ipInfo.substring(ips.indexOf("/") - 2, ips.indexOf("/")).trim();
        String mask;
        switch (brd) {
            case "24":
                mask = "255.255.255.0";
                break;
            case "25":
                mask = "255.255.255.128";
                break;
            case "23":
                mask = "255.255.254.0";
                break;
            default:
                mask = "255.255.255.0";
        }

        linux_info.setText("IP:" + ips.substring(0, ips.indexOf("/")) + "\n" +
                "Mask:" + mask + "\n" +
                "if" + do_exec("ifconfig -lo"));*/
        String ifconfig = do_exec("ifconfig eth0");
        String ifconfigs = ifconfig.substring(ifconfig.indexOf("ip"));
        String ip = ifconfigs.substring(3, ifconfigs.indexOf("mask"));
        String mask = ifconfigs.substring(ifconfigs.indexOf("mask"), ifconfigs.indexOf("flags"));
        linux_info.setText("IP:" + ip + "\n" +
                "Mask:" + mask);
    }


    private String do_exec(String cmd) {
        String s = "\n";
        try {
            String[] cmdline = {"sh", "-c", cmd};
            Process p = Runtime.getRuntime().exec(cmdline);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                s += line + "\n";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("do_exec--->" + cmd, s);
        return s;
    }

    private String intToIp(int i) {//Formatter.inttoip();
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF) + "~~~" + Formatter.formatIpAddress(i);
    }


    private void do_exec2(String cmd) {
        try {
            Process localProcess;
            localProcess = Runtime.getRuntime().exec("su");
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes(cmd + "\n");
            localDataOutputStream.writeBytes("exit\n");

            localDataOutputStream.flush();
            localProcess.waitFor();

            System.out.println(localDataOutputStream);
            Log.e("do_exec2--->" + cmd, localDataOutputStream + "");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void GoML(View view){
        startActivity(new Intent(mContext,LingActivity.class));
    }
=======
>>>>>>> ec3dcff571d1887abad189557e6c3915ce883861

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

