/**
 * <p>
 * Copyright: Copyright (c) 2012
 * Company: ZTE
 * Description:网络信息类
 * </p>
 * @Title ClientNetworkInfo.java
 * @Package com.zte.iptvclient.android.common
 * @version 1.0
 * @author 0043200560
 * @date 2012-2-22
 */
package com.yunqi.cardreader.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;


/** 
 * 当前客户端网络信息类 
 * @ClassName:NetworkUtil
 * @Description: 当前客户端网络信息类 
 * @author: 0043200560
 * @date: 2012-2-22
 *  
 */
public class NetworkUtil
{
    /**日志标签*/
    private static final String LOG_TAG = "NetworkUtil";


    

    /**
     * 获取本地IP列表函数
     * @date 2012-2-22 
     * @return 本地IP字符串，当没有可用的网络地址的时候返回null
     * @throws SocketException SocketException
     */
    private static List<InetAddress> getLocalIPAddresses() throws SocketException
    {
        List<InetAddress> listInetAddrs = new ArrayList<InetAddress>();
        for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface
                .getNetworkInterfaces(); mEnumeration.hasMoreElements();)
        {
            NetworkInterface intf = mEnumeration.nextElement();
            for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr
                    .hasMoreElements();)
            {
                InetAddress inetAddress = enumIPAddr.nextElement();
                //如果不是回环地址
                if (!inetAddress.isLoopbackAddress())
                {
                    //将此地址加入到地址列表中。
                    listInetAddrs.add(inetAddress);
                }
            }
        }
        return listInetAddrs;
    }

    /**
     * 获取本地IP地址，优先获取IPV4地址。
     * 如果没有IPV4地址，则返回IPV6地址。如果没有IPV6地址，则返回0.0.0.0
     * @return ip地址字符串     * 
     * @throws SocketException Socket异常
     */
    public static String getLocalIPAddress() throws SocketException
    {
        //获取IPV4地址并返回
        for (InetAddress inetAddress : getLocalIPAddresses())
        {
            if (inetAddress instanceof Inet4Address)
            {
                return inetAddress.getHostAddress().toString();
            }
        }

        //获取IPV6地址并返回
        for (InetAddress inetAddress : getLocalIPAddresses())
        {
            if (inetAddress instanceof Inet6Address)
            {
                return inetAddress.getHostAddress().toString();
            }
        }

        //返回默认地址
        return "0.0.0.0";
    }

    /**
     * getLocalIP 获取wifi IPv4地址
     * 
     * @param wm
     * @return
     */
    public static String getLocalIP(WifiManager wm)
    {
        if (null == wm || !wm.isWifiEnabled())
        {
            Log.w(LOG_TAG, "WifiManager is null or not available");
            //返回默认mac地址
            return "0.0.0.0";
        }

        WifiInfo wifiInfo = wm.getConnectionInfo();
        int ipAddr = wifiInfo.getIpAddress();

        // Android仅支持IPv4
        String ip = (ipAddr & 0xFF) + "." + ((ipAddr >> 8) & 0xFF) + "."
            + ((ipAddr >> 16) & 0xFF) + "." + ((ipAddr >> 24) & 0xFF);

        return ip;
    }

    /**
     * 获取本机mac地址，如果没有合适的地址。则返回“00:00:00:00:00:00”
     * @date 2012-2-28 
     * @return 当前MAC地址
     */
    public String getLocalMacAddress(WifiManager wm)
    {
        if (null == wm || !wm.isWifiEnabled())
        {
            Log.w(LOG_TAG, "WifiManager is null or not available");
            //返回默认mac地址
            return "00:00:00:00:00:00";
        }

        //        WifiManager wifi = (WifiManager) m_ctxMainActivity
        //                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wm.getConnectionInfo();
        if (null != info)
        {
            String strMac = info.getMacAddress();
            if (!TextUtils.isEmpty(strMac))
            {
                return strMac;
            }
        }

        //返回默认mac地址
        return "00:00:00:00:00:00";
    }

    /**
     * 检查网络是否可用
     * @date 2012-2-22 
     * @return 如果为-1表示网络不可用；如果为其他整数，代表当前网络连接类型,请参考android.net.NetworkInfo中的网络类型。
     */
    public int checkNetworkInfo(ConnectivityManager cm)
    {
        if (null == cm)
        {
            Log.d(LOG_TAG, "ConnectivityManager is null. NetWork Unavailabel");
            return -1;
        }

        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        //网络信息无效返回-1
        if (null == netinfo || !netinfo.isAvailable())
        {
            return -1;
        }
        //网络信息有效，显示网络类型
        else
        {
            return netinfo.getType();
        }
    }

    /**
     * 获取本机mac地址
     * @date 2012-11-15 
     * @return 当前MAC地址
     */
    public String getMacAddressByWifi(WifiManager wm)
    {
        if (null == wm || !wm.isWifiEnabled())
        {
            Log.w(LOG_TAG, "WifiManager is null");
            return "";
        }

        String strMac = "";
        //        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wm.getConnectionInfo();
        if (null != info)
        {
            //首先取本地mac地址
            strMac = info.getMacAddress();
            //如果取不到，则检查wifi是否是关闭的，若为关闭的，则打开wifi，再次取mac地址
            if (TextUtils.isEmpty(strMac))
            {
                if (!(wm.isWifiEnabled()))
                {
                    //尝试打开wifi网络，再次读取mac地址
                    wm.setWifiEnabled(true);
                    strMac = info.getMacAddress();
                    //如果手动打开过wifi，需要手动关闭wifi
                    wm.setWifiEnabled(false);
                }
            }
        }

        //返回mac地址
        return strMac;
    }

    /**
     * 获得本地MAC地址
     * @date 2012-6-26 
     * @return  本地MAC地址
     */
    public String getMacAddressByCallCmd()
    {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig eth0", "HWaddr");

        //如果返回的result == null，则说明网络不可取
        if (result == null)
        {
            return null;
        }

        //对该行数据进行解析
        //例如：eth0      Link encap:Ethernet  HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true)
        {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);

            if (Mac.length() > 1)
            {
                Mac = Mac.replaceAll(" ", "");
                result = Mac;
                //                result = "";
                //                String[] tmp = Mac.split(":");
                //                for (int i = 0; i < tmp.length; ++i)
                //                {
                //                    result += tmp[i];
                //                }
            }
        }
        return result;
    }

    /**
     * 
     * 执行cmd
     * @date 2013-2-22 
     * @param cmd cmd指令串
     * @param filter 过滤标识
     * @return 返回执行结果中，含有过滤标识的信息
     */
    private String callCmd(String cmd, String filter)
    {
        String result = "";
        String line = "";
        try
        {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = proc.getInputStream();
            InputStreamReader is = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && line.contains(filter) == false)
            {
                //result += line;
            }

            result = line;

            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 
     * 判断是否有网络连接
     * <p>
     * Description: 不管是wifi，有线还是移动网络。
     * <p>
     * @date 2014年3月17日 
     * @return 有任意一个连接就返回true，其他情况返回false。
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * 
     * 判断是否有有线网络连接
     * <p>
     * Description: 判断是否有有线网络连接
     * <p>
     * @date 2014年3月17日 
     * @author jamesqiao10065075
     * @param cm 连接管理器实例
     * @return 已连接返回true，其他情况返回false。
     */
    public static boolean isCableNetworkAvailable(ConnectivityManager cm)
    {
        if (null == cm)
        {
            Log.w(LOG_TAG, "ConnectivityManager is null. Cablenetwork  Unavailabel");
            return false;
        }
        else
        {
            // 获取所有网络
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED
                        && info[i].getType() == ConnectivityManager.TYPE_ETHERNET)
                    {
                        // 只要有一个可用，就返回true
                        Log.i(LOG_TAG, "Cablenetwork Available");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 
     * 判断是否有Wifi网络连接
     * <p>
     * Description: 判断是否有Wifi网络连接
     * <p>
     * @date 2014年3月17日 
     * @author jamesqiao10065075
     * @param cm 连接管理器实例
     * @return 已连接返回true，其他情况返回false。
     */
    public static boolean isWifiNetworkAvailable(ConnectivityManager cm)
    {
        if (null == cm)
        {
            Log.w(LOG_TAG, "ConnectivityManager is null. NetWork Unavailabel");
            return false;
        }
        else
        {
            // 获取所有网络
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED
                        && info[i].getType() == ConnectivityManager.TYPE_WIFI)
                    {
                        // 只要有一个可用，就返回true
                        Log.i(LOG_TAG, "Wifi NetWork Available");
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
