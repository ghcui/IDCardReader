package com.yunqi.cardreader.parser;


import com.yunqi.cardreader.model.bean.Module;

import java.io.InputStream;
import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface IModuleParse {
    /**
     *
     * 解析输入流，获取Beauty列表
     * @param is
     * @return
     * @throws Exception
     */
    public List<Module> parse(InputStream is) throws Exception;

    /**
     *
     * 序列化Beauty对象集合，得到XML形式的字符串
     * @param beauties
     * @return
     * @throws Exception
     */
    public String serialize(List<Module> beauties) throws Exception;
}
