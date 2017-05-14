package com.yunqi.cardreader.parser;

import android.util.Xml;


import com.yunqi.cardreader.model.bean.Module;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public class ModuleParse implements IModuleParse {

    @Override
    public List<Module> parse(InputStream is) throws Exception {
        List<Module> mList = null;
        Module module = null;

        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser xpp = Xml.newPullParser();
        // 设置输入流 并指明编码方式
        xpp.setInput(is, "UTF-8");
        // 产生第一个事件
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                // 判断当前事件是否为文档开始事件
                case XmlPullParser.START_DOCUMENT:
                    mList = new ArrayList<Module>(); // 初始化books集合
                    break;
                // 判断当前事件是否为标签元素开始事件
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("Module")) { // 判断开始标签元素是否是book
                        module = new Module();
                    } else if (xpp.getName().equals("id")) {
                        eventType = xpp.next();//让解析器指向name属性的值
                        // 得到name标签的属性值，并设置beauty的name
                        module.id = xpp.getText();
                    } else if (xpp.getName().equals("name")) { // 判断开始标签元素是否是book
                        eventType = xpp.next();//让解析器指向age属性的值
                        // 得到age标签的属性值，并设置beauty的age
                        module.name = xpp.getText();
                    } else if (xpp.getName().equals("className")) { // 判断开始标签元素是否是book
                        eventType = xpp.next();//让解析器指向age属性的值
                        // 得到age标签的属性值，并设置beauty的age
                        module.className = xpp.getText();
                    } else if (xpp.getName().equals("icon")) { // 判断开始标签元素是否是book
                        eventType = xpp.next();//让解析器指向age属性的值
                        // 得到age标签的属性值，并设置beauty的age
                        module.icon = xpp.getText();
                    }
                    break;

                // 判断当前事件是否为标签元素结束事件
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("Module")) { // 判断结束标签元素是否是book
                        mList.add(module);
                        module = null;
                    }
                    break;
            }
            // 进入下一个元素并触发相应事件
            eventType = xpp.next();
        }
        return mList;
    }

    @Override
    public String serialize(List<Module> beauties) throws Exception {
        return null;
    }
}
