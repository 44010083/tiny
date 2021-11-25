package com.bb.tiny.model.vo;

import com.bb.tiny.util.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
public class TinyVoTest {
    TinyVo vo = new TinyVo();
    int voHashCode = 6421;
    TinyVo resultVo = new TinyVo();

    @Before
    public void before() {
        vo.setUrl(String.valueOf(GlobalConstant.ONE));
        vo.setTiny(String.valueOf(GlobalConstant.ONE));
    }

    @After
    public void after() {
    }

    @Test
    public void testGetAndSet() {
        assertEquals(String.valueOf(GlobalConstant.ONE), vo.getUrl());
        assertEquals(String.valueOf(GlobalConstant.ONE), vo.getTiny());

        vo.setUrl(String.valueOf(GlobalConstant.ZERO));
        vo.setTiny(String.valueOf(GlobalConstant.ZERO));
        assertEquals(String.valueOf(GlobalConstant.ZERO), vo.getUrl());
        assertEquals(String.valueOf(GlobalConstant.ZERO), vo.getTiny());
    }

    @Test
    public void testEqualObject() {
        assertFalse(vo.equals(resultVo));
    }

    @Test
    public void testCanEqualObject() {
        assertFalse(vo.canEqual(resultVo));
    }

    @Test
    public void testHashCode() {
        int hashCode = vo.hashCode();
        log.info("vo.hashCode() - > " + hashCode);
        assertEquals(voHashCode, hashCode);
    }
} 
