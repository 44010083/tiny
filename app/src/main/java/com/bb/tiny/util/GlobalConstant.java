package com.bb.tiny.util;


/**
 * * Copyright (C) 2021  zhong002
 *
 * @author zhong002
 * @version 1.0
 * @mail 44010083@qq.com
 * @description
 * @create: 2021-11-20
 **/

public interface GlobalConstant {
    //定义62位编解码的字符
    char[] ARRAY =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                    'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                    'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                    'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
    //常用数字
    int ZERO = 0;
    int ONE = 1;
    int DECIMAL = 62;

    //数据字典中用来存储获得tiny的的自增token的KEY
    String TINY_TOKEN_ID_DICT_NAME = "#id";
    //数据字典表的名称
    String DICT_TABLE_NAME = "dict";

    //异常时，抛出的错误信息
    String EMPTY_URL_ERROR = "请输入URL地址(以http或https开头)";
    String EXPLORE_URL_ERROR = "访问输入的URL失败 -> ";
}
