package com.bb.tiny.model;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

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

public class EntityTest {


    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    /**
     * Method: builder(int status)
     */
    @Test
    public void testBuilder() {
        int code = 500;
        ResponseEntity<Entity<Object>> result = Entity.builder(code).build(code, null, null);
        log.info("Entity.builder with error code 500 -> " + JSON.toJSONString(result));
        assertEquals("预期返回:" + code + ",实际返回：" + Objects.requireNonNull(result.getBody()).getCode(), code, result.getBody().getCode());
    }

    /**
     * Method: success(T data)
     */
    @Test
    public void testSuccess() {
        String expected = "";
        ResponseEntity<Entity<String>> result = Entity.success(expected);
        assertEquals(expected, Objects.requireNonNull(result.getBody()).getData());
    }

    /**
     * Method: failure(int code, String msg)
     */
    @Test
    public void testFailure() {
        String expected = "";
        int code = 404;
        ResponseEntity<Entity<String>> result = Entity.failure(code, expected);
        assertEquals(code, Objects.requireNonNull(result.getBody()).getCode());
        assertEquals(expected, result.getBody().getMsg());
    }

} 
