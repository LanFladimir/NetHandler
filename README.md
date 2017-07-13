# WifiDem

### Connect With Wifi:

```Java
WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//ip、Mac、Speed、、、
WifiInfo wifiInfo = manager.getConnectionInfo();
//Dns、Mask、GateWay、、、
DhcpInfo dInfo = manager.getDhcpInfo();
```

### Connect With GPRS

```Java
public String getLocalIpAddress()  
    {  
        try  
        {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
            {  
               NetworkInterface intf = en.nextElement();  
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
               {  
                   InetAddress inetAddress = enumIpAddr.nextElement();  
                   if (!inetAddress.isLoopbackAddress())  
                   {  
                       return inetAddress.getHostAddress().toString();  
                   }  
               }  
           }  
        }  
        catch (SocketException ex)  
        {  
            Log.e("WifiPreference IpAddress", ex.toString());  
        }  
        return null;  
    }  
```

### Check Connect Type

```Java
NetworkInfo info =getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
if-->info != null && info.isConnected()
  info.getType() == ConnectivityManager.TYPE_MOBILE-->Gprs
  info.getType() == ConnectivityManager.TYPE_WIFI  -->Wifi
```



### Permission

```X
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>  
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>  
<uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>  
<uses-permission android:name="android.permission.INTERNET"></uses-permission>  
```

