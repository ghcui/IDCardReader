package com.ivsign.android.IDCReader;

/**
 * Created by Administrator on 2017/6/5.
 */


import java.util.Locale;

public class SystemConvert {
    public static final char[] mChars = "0123456789ABCDEF".toCharArray();
    public static final String mHexStr = "0123456789ABCDEF";

    public SystemConvert() {
    }

    public static String bytesHexStr(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();

        for (int n = 0; n < iLen; ++n) {
            sb.append(mChars[(b[n] & 255) >> 4]);
            sb.append(mChars[b[n] & 15]);
            sb.append(' ');
        }

        return sb.toString().trim().toUpperCase(Locale.US);
    }

    public static byte[] HexStrbytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for (int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte) ("0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | "0123456789ABCDEF".indexOf(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 255;
        addr |= bytes[1] << 8 & '\uff00';
        addr |= bytes[2] << 16 & 16711680;
        addr |= bytes[3] << 25 & -16777216;
        return addr;
    }

    public static byte[] intToByte(int i) {
        byte[] abyte0 = new byte[]{(byte) (255 & i), (byte) (('\uff00' & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((-16777216 & i) >> 24)};
        return abyte0;
    }
}
