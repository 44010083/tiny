package com.bb.tiny.service.impl;


import com.alibaba.fastjson.JSON;
import com.bb.tiny.model.entity.Dict;
import com.bb.tiny.repo.DictRepo;
import com.bb.tiny.service.ITinyService;
import com.bb.tiny.util.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * * Copyright (C) 2021  zhong002
 *
 * @author zhong002
 * @version 1.0
 * @mail 44010083@qq.com
 * @description
 * @create: 2021-11-20
 **/

@Slf4j
@Service
public class TinyServiceImpl implements ITinyService {

    public static Map<Character, Integer> charMap = new HashMap<>();

    //初始化map
    static {
        for (int i = GlobalConstant.ZERO; i < GlobalConstant.ARRAY.length; i++) charMap.put(GlobalConstant.ARRAY[i], i);
    }

    @Resource
    DictRepo dictRepo;

    /**
     * 把数字转换成相对应的进制,目前支持(2-62)进制
     */
    public String number2tiny(long number, int decimal) {
        log.info(JSON.toJSONString(charMap));
        StringBuilder builder = new StringBuilder();
        while (number != GlobalConstant.ZERO) {
            builder.append(GlobalConstant.ARRAY[(int) (number - (number / decimal) * decimal)]);
            number /= decimal;
        }
        return builder.reverse().toString();
    }


    @Override
    public String getTiny(String url) throws Exception {
        //去除输入的首尾空格
        url = url.trim();
        //检查输入的url是否正确
        checkURL(url);
        //检查此url是否已经存在
        Optional<Dict> oldTinyDict = dictRepo.findByValue(url);
        //如果存在记录，则直接返回tiny
        if (oldTinyDict.isPresent() && !StringUtils.isEmpty(oldTinyDict.get().getName())) {
            return oldTinyDict.get().getName();
        }
        //获取保存的上次使用的加密数字
        Optional<Dict> lastIdDictOptional = dictRepo.findByName(GlobalConstant.TINY_TOKEN_ID_DICT_NAME);
        long lastId = Long.parseLong(String.valueOf(GlobalConstant.ONE));
        Dict lastIdDict = new Dict();
        lastIdDict.setName(GlobalConstant.TINY_TOKEN_ID_DICT_NAME);
        lastIdDict.setValue(String.valueOf(lastId));
        if (lastIdDictOptional.isPresent()) {
            lastIdDict = lastIdDictOptional.get();
            lastId = Long.parseLong(lastIdDict.getValue());
            //lastId自增
            lastId++;
            //保存lastId
            lastIdDict.setValue(String.valueOf(lastId));
        }
        dictRepo.save(lastIdDict);

        //计算获得tiny
        String tiny = number2tiny(lastId, GlobalConstant.DECIMAL);
        Dict tinyDict = new Dict();
        tinyDict.setName(tiny);
        tinyDict.setValue(url);
        //保存tinyDict
        dictRepo.save(tinyDict);
        return tiny;
    }

    @Override
    public String getUrl(String tiny) {
        //获取tiny记录
        Optional<Dict> oldTinyDict = dictRepo.findByName(tiny);
        //如果存在记录，则直接返回url
        if (oldTinyDict.isPresent() && !StringUtils.isEmpty(oldTinyDict.get().getValue())) {
            return oldTinyDict.get().getValue();
        }
        return null;
    }

    private void checkURL(String urlStr) throws Exception {
        if (StringUtils.isEmpty(urlStr)) {
            throw new Exception(GlobalConstant.EMPTY_URL_ERROR);
        }
        try {
            URL url = new URL(urlStr);
            url.openStream();
        } catch (IOException e) {
            throw new Exception(GlobalConstant.EXPLORE_URL_ERROR + urlStr);
        }
    }
}
