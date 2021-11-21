package com.bb.tiny.service.impl;

import com.bb.tiny.model.entity.Dict;
import com.bb.tiny.repo.DictRepo;
import com.bb.tiny.util.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

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
// 告诉JUnit使用PowerMockRunner进行测试
@RunWith(PowerMockRunner.class)
// 所有需要测试的类列在此处，适用于模拟final类或有final,private,static,native方法的类
@PrepareForTest({MockUtil.class})
// 为了解决使用powermock后，提示classloader错误
@PowerMockIgnore({"javax.*"})
public class DictServiceImplTest {
    @Mock
    DictRepo dictRepo;
    @InjectMocks
    TinyServiceImpl tinyServiceImpl;
    String errUrl = "1https://www.baidu.com";
    String emptyUrl = "";
    String url = "https://www.baidu.com";
    String newUrl = "https://aws.amazon.com/";
    String tiny = "6";
    String tokenName = "#id";
    String tokenValue = "5";

    @Before
    public void before() {
        Dict dict = new Dict();
        dict.setValue(url);
        dict.setName(tiny);
        PowerMockito.when(dictRepo.findByValue(url)).thenReturn(java.util.Optional.of(dict));
        PowerMockito.when(dictRepo.findByName(tiny)).thenReturn(java.util.Optional.of(dict));
        dict = new Dict();
        dict.setValue(tokenValue);
        dict.setName(tokenName);
        PowerMockito.when(dictRepo.findByName(tokenName)).thenReturn(java.util.Optional.of(dict));
    }

    @After
    public void after() {
    }


    @Test
    public void testNumber2tiny() {
        String expected = "1E";
        String tiny = tinyServiceImpl.number2tiny(100L, GlobalConstant.DECIMAL);
        assertEquals(expected, tiny);

        expected = "PFWWUluKq";
        tiny = tinyServiceImpl.number2tiny(10000000000000000L, GlobalConstant.DECIMAL);
        assertEquals(expected, tiny);
    }


    @Test
    public void testGetTiny() throws Exception {
        String result = tinyServiceImpl.getTiny(url);
        assertEquals(tiny, result);
    }

    @Test
    public void testGetTinyWithErrUrl() {
        try {
            tinyServiceImpl.getTiny(errUrl);
        } catch (Exception e) {
            assertEquals(GlobalConstant.EXPLORE_URL_ERROR + errUrl, e.getMessage());
        }
    }

    @Test
    public void testGetTinyWithEmptyUrl() {
        try {
            tinyServiceImpl.getTiny(emptyUrl);
        } catch (Exception e) {
            assertEquals(GlobalConstant.EMPTY_URL_ERROR, e.getMessage());
        }

    }

    @Test
    public void testGetTinyWithNewUrl() throws Exception {
        String result = tinyServiceImpl.getTiny(newUrl);
        assertEquals(tiny, result);
    }

    @Test
    public void testGetUrl() {
        String result = tinyServiceImpl.getUrl(tiny);
        assertEquals(url, result);
    }
} 
