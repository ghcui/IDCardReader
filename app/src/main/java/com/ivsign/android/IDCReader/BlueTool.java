package com.ivsign.android.IDCReader;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


public class BlueTool {
    byte[] cmd_SAM = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)3, (byte)18, (byte)-1, (byte)-18};
    byte[] cmd_find = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)3, (byte)32, (byte)1, (byte)34};
    byte[] cmd_selt = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)3, (byte)32, (byte)2, (byte)33};
    byte[] cmd_read = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)3, (byte)48, (byte)1, (byte)50};
    byte[] cmd_sleep = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)2, (byte)0, (byte)2};
    byte[] cmd_weak = new byte[]{(byte)-86, (byte)-86, (byte)-86, (byte)-106, (byte)105, (byte)0, (byte)2, (byte)1, (byte)3};
    byte[] recData = new byte[1600];
    int Readflage = -99;
    public Info info;
    static String TAG = "BlueTool";
    BlueToolListenr listenr;
    Context mContext;
    boolean isSleep = false;
    BluetoothAdapter mBluetoothAdapter = null;
    BluetoothServerSocket mServer = null;
    BluetoothSocket mSocket = null;
    private OutputStream out = null;
    private InputStream in = null;
    protected boolean isDiscovery;//是否发现蓝牙设备
    protected boolean isConnect;//是否连接上蓝牙设备
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println(action);
            if(action.equals("android.bluetooth.device.action.FOUND")) {
                Log.i(BlueTool.TAG, "发现蓝牙");
                isDiscovery=true;
                BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                String mac = device.getAddress();
                BlueTool.this.listenr.findForDevice(mac);
            }

        }
    };
    String DEVICE_NAME1 = "CVR-100B";
    String DEVICE_NAME2 = "IDCReader";
    String DEVICE_NAME3 = "COM2";
    String DEVICE_NAME4 = "BOLUTEK";

    public BlueTool(Context mContext, BluetoothAdapter mBluetoothAdapter) {
        this.mContext = mContext;
        this.mBluetoothAdapter = mBluetoothAdapter;
        if(!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

    }

    public void scanf() {
        isDiscovery=false;
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        if(this.mBluetoothAdapter.isDiscovering()) {
            this.mBluetoothAdapter.cancelDiscovery();
        }

        this.mBluetoothAdapter.startDiscovery();
    }

    public Boolean connect(String mac) {
        this.mBluetoothAdapter.cancelDiscovery();
        BluetoothDevice device = null;
        if(mac != "" && mac != null) {
            device = this.mBluetoothAdapter.getRemoteDevice(mac);
        }

        boolean returnValue;
        try {
            Method e = device.getClass().getMethod("createBond", new Class[0]);
            this.connect(device);
            this.listenr.BluetoothForState(Boolean.valueOf(true));
            returnValue = true;
        } catch (Exception var5) {
            returnValue = false;
        }
        isConnect=Boolean.valueOf(returnValue);
        return isConnect;
    }

    private boolean connect(BluetoothDevice device) {
        try {
            this.mSocket = device.createRfcommSocketToServiceRecord(this.MY_UUID);
            this.mSocket.connect();
            this.out = this.mSocket.getOutputStream();
            this.in = this.mSocket.getInputStream();
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public Boolean connect() {
        Set pairedDevices = this.mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            Iterator iterator = pairedDevices.iterator();
            if(iterator.hasNext()) {
                BluetoothDevice device = (BluetoothDevice)iterator.next();
                if(!this.DEVICE_NAME1.equals(device.getName()) && !this.DEVICE_NAME2.equals(device.getName()) && !this.DEVICE_NAME3.equals(device.getName()) && !this.DEVICE_NAME4.equals(device.getName())) {
                    return Boolean.valueOf(false);
                }

                try {
                    this.mBluetoothAdapter.enable();
                    Intent e = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
                    e.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 150);
                    this.mSocket = device.createRfcommSocketToServiceRecord(this.MY_UUID);
                    int sdk = Integer.parseInt(Build.VERSION.SDK);
                    if(sdk >= 10) {
                        this.mSocket = device.createInsecureRfcommSocketToServiceRecord(this.MY_UUID);
                    } else {
                        this.mSocket = device.createRfcommSocketToServiceRecord(this.MY_UUID);
                    }

                    this.mServer = this.mBluetoothAdapter.listenUsingRfcommWithServiceRecord("myServerSocket", this.MY_UUID);
                    this.mSocket.connect();
                    this.in = this.mSocket.getInputStream();
                    this.out = this.mSocket.getOutputStream();
                } catch (IOException var6) {
                    return Boolean.valueOf(false);
                }

                if(this.in != null && this.out != null) {
                    this.listenr.BluetoothForState(Boolean.valueOf(true));
                    return Boolean.valueOf(true);
                }

                return Boolean.valueOf(false);
            }
        }

        return null;
    }

    public String ReadSAM() {
        try {
            if(this.in != null && this.out != null) {
                Thread.sleep(200L);
                this.out.write(this.cmd_SAM);
                this.out.flush();
                Thread.sleep(200L);
                this.in.read(this.recData);
                if(this.recData[9] == -112) {
                    String e = this.AnalyzeSAM(this.recData);
                    return "模块号为：\r\n" + e;
                } else {
                    return null;
                }
            } else {
                this.Readflage = -2;
                return "连接异常";
            }
        } catch (Exception var2) {
            return "读取模块号出错";
        }
    }

    private String AnalyzeSAM(byte[] sambuffer) {
        if(sambuffer.length < 10) {
            return "";
        } else if(sambuffer[9] != -112) {
            return "";
        } else {
            byte[] samdate = new byte[4];
            System.arraycopy(sambuffer, 14, samdate, 0, 4);
            byte[] samtenid = new byte[4];
            System.arraycopy(sambuffer, 18, samtenid, 0, 4);
            byte[] samstr = new byte[4];
            System.arraycopy(sambuffer, 22, samstr, 0, 4);
            String samid = String.format("%02d.%02d-%010d-%010d", new Object[]{Byte.valueOf(sambuffer[10]), Byte.valueOf(sambuffer[12]), Long.valueOf(getLong(samdate)), Long.valueOf(getLong(samtenid))});
            return samid;
        }
    }

    private static long getLong(byte[] buf) {
        long i = 0L;
        long tmp = 0L;

        for(int j = 0; j < buf.length; ++j) {
            tmp = (long)((buf[j] & 255) << j * 8);
            i |= tmp;
        }

        return i;
    }

    public Boolean disconnect() {
        Boolean returnValue;
        try {
            if(this.out == null || this.in == null) {
                returnValue = Boolean.valueOf(false);
            }

            this.in.close();
            this.out.close();
            this.mSocket.close();
            this.listenr.BluetoothForState(Boolean.valueOf(false));
            returnValue = Boolean.valueOf(true);
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (Exception var3) {
            returnValue = Boolean.valueOf(false);
        }

        return returnValue;
    }

    public Info read() {
        if(this.isSleep) {
            return null;
        } else {
            this.info = new Info();
            byte readcount = 15;
            if(readcount > 1) {
                this.ReadCard();
                int readcount1 = readcount - 1;
                if(this.Readflage > 0) {
                    boolean readcount2 = false;
                    if(this.Readflage == 1) {
                        readcount2 = false;
                        return this.info;
                    } else {
                        readcount2 = false;
                        return this.info;
                    }
                } else {
                    return this.info;
                }
            } else {
                return this.info;
            }
        }
    }

    private void ReadCard() {
        try {
            if(this.in == null || this.out == null) {
                this.Readflage = -2;
                return;
            }

            this.out.write(this.cmd_find);
            Thread.sleep(100L);
            boolean e = false;
            this.out.write(this.cmd_selt);
            Thread.sleep(200L);
            int var17 = this.in.read(this.recData);
            this.out.write(this.cmd_read);
            Thread.sleep(1000L);
            byte[] tempData = new byte[1500];
            long startTime = System.currentTimeMillis();

            int flag;
            while(System.currentTimeMillis() - startTime < 10000L) {
                flag = this.in.available();
                if(flag > 0) {
                    var17 = this.in.read(tempData);
                    break;
                }

                Thread.sleep(100L);
            }

            flag = 0;
            int dataBuf;
            if(var17 < 1294) {
                System.err.println("读卡有问题");
                Thread.sleep(1000L);
                if(this.in.available() > 0) {
                    var17 = this.in.read(tempData);
                } else {
                    Thread.sleep(500L);
                    if(this.in.available() > 0) {
                        var17 = this.in.read(tempData);
                    }
                }

                for(dataBuf = 0; dataBuf < var17; ++flag) {
                    this.recData[flag] = tempData[dataBuf];
                    ++dataBuf;
                }
            } else {
                System.err.println("一次读取成功");

                for(dataBuf = 0; dataBuf < var17; ++flag) {
                    this.recData[flag] = tempData[dataBuf];
                    ++dataBuf;
                }
            }

            Object var18 = null;
            if(flag == 1295) {
                if(this.recData[9] == -112) {
                    byte[] var19 = new byte[256];

                    for(int TmpStr = 0; TmpStr < 256; ++TmpStr) {
                        var19[TmpStr] = this.recData[14 + TmpStr];
                    }

                    String var20 = new String(var19, "UTF16-LE");
                    var20 = new String(var20.getBytes("UTF-8"));
                    this.info.setName(var20.substring(0, 15));
                    this.info.setSex(var20.substring(15, 16));
                    this.info.setNationlity(var20.substring(16, 18));
                    this.info.setBirthdate(var20.substring(18, 26));
                    this.info.setAddress(var20.substring(26, 61));
                    this.info.setIdNo(var20.substring(61, 79));
                    this.info.setIdlssued(var20.substring(79, 94));
                    this.info.setStartDate(var20.substring(94, 102));
                    this.info.setEndDate(var20.substring(102, 110));

                    try {
                        int e1 = IDCReaderSDK.Init();
                        if(e1 == 0) {
                            byte[] datawlt = new byte[1384];
                            byte[] byLicData = new byte[]{(byte)5, (byte)0, (byte)1, (byte)0, (byte)91, (byte)3, (byte)51, (byte)1, (byte)90, (byte)-77, (byte)30, (byte)0};

                            int t;
                            for(t = 0; t < 1295; ++t) {
                                datawlt[t] = this.recData[t];
                            }

                            t = IDCReaderSDK.unpack(datawlt, byLicData);
                            if(t == 1) {
                                this.Readflage = 1;
                                FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/wltlib/zp.bmp");
                                Bitmap bmp = BitmapFactory.decodeStream(fis);
                                fis.close();
                                this.info.setBmp(bmp);
                            } else {
                                this.Readflage = 6;
                            }
                        } else {
                            this.Readflage = 6;
                        }
                    } catch (Exception var14) {
                        this.Readflage = 6;
                    }
                } else {
                    this.Readflage = -5;
                }
            } else {
                this.Readflage = -5;
            }
        } catch (IOException var15) {
            this.Readflage = -99;
        } catch (InterruptedException var16) {
            this.Readflage = -99;
        }

    }

    public void sleep() {
        try {
            if(this.in == null || this.out == null) {
                return;
            }

            this.out.write(this.cmd_sleep);
            this.isSleep = true;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void weak() {
        try {
            if(this.in == null || this.out == null) {
                return;
            }

            this.out.write(this.cmd_weak);
            this.isSleep = false;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void setListenr(BlueToolListenr listenr) {
        this.listenr = listenr;
    }
}
