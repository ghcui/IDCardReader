package com.yunqi.cardreader.util;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class NationUtil {

    public static String decodeNation(int code) {
        String nation;
        switch (code) {
            case 1:
                nation = "汉";
                break;
            case 2:
                nation = "蒙古";
                break;
            case 3:
                nation = "回";
                break;
            case 4:
                nation = "藏";
                break;
            case 5:
                nation = "维吾尔";
                break;
            case 6:
                nation = "苗";
                break;
            case 7:
                nation = "彝";
                break;
            case 8:
                nation = "壮";
                break;
            case 9:
                nation = "布依";
                break;
            case 10:
                nation = "朝鲜";
                break;
            case 11:
                nation = "满";
                break;
            case 12:
                nation = "侗";
                break;
            case 13:
                nation = "瑶";
                break;
            case 14:
                nation = "白";
                break;
            case 15:
                nation = "土家";
                break;
            case 16:
                nation = "哈尼";
                break;
            case 17:
                nation = "哈萨克";
                break;
            case 18:
                nation = "傣";
                break;
            case 19:
                nation = "黎";
                break;
            case 20:
                nation = "傈僳";
                break;
            case 21:
                nation = "佤";
                break;
            case 22:
                nation = "畲";
                break;
            case 23:
                nation = "高山";
                break;
            case 24:
                nation = "拉祜";
                break;
            case 25:
                nation = "水";
                break;
            case 26:
                nation = "东乡";
                break;
            case 27:
                nation = "纳西";
                break;
            case 28:
                nation = "景颇";
                break;
            case 29:
                nation = "柯尔克孜";
                break;
            case 30:
                nation = "土";
                break;
            case 31:
                nation = "达斡尔";
                break;
            case 32:
                nation = "仫佬";
                break;
            case 33:
                nation = "羌";
                break;
            case 34:
                nation = "布朗";
                break;
            case 35:
                nation = "撒拉";
                break;
            case 36:
                nation = "毛南";
                break;
            case 37:
                nation = "仡佬";
                break;
            case 38:
                nation = "锡伯";
                break;
            case 39:
                nation = "阿昌";
                break;
            case 40:
                nation = "普米";
                break;
            case 41:
                nation = "塔吉克";
                break;
            case 42:
                nation = "怒";
                break;
            case 43:
                nation = "乌孜别克";
                break;
            case 44:
                nation = "俄罗斯";
                break;
            case 45:
                nation = "鄂温克";
                break;
            case 46:
                nation = "德昂";
                break;
            case 47:
                nation = "保安";
                break;
            case 48:
                nation = "裕固";
                break;
            case 49:
                nation = "京";
                break;
            case 50:
                nation = "塔塔尔";
                break;
            case 51:
                nation = "独龙";
                break;
            case 52:
                nation = "鄂伦春";
                break;
            case 53:
                nation = "赫哲";
                break;
            case 54:
                nation = "门巴";
                break;
            case 55:
                nation = "珞巴";
                break;
            case 56:
                nation = "基诺";
                break;
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            default:
                nation = "";
                break;
            case 97:
                nation = "其他";
                break;
            case 98:
                nation = "外国血统中国籍人士";
        }

        return nation;
    }

    public static String[] getAllNations() {
        List<String> listNation = new ArrayList<>();
        for (int i = 1; i < 99; i++) {
            String nation = decodeNation(i);
            if (!TextUtils.isEmpty(nation)) {
                listNation.add(nation);
            }
        }
        String[] arrayNation =listNation.toArray(new String[listNation.size()]);
        return arrayNation;
    }
}
