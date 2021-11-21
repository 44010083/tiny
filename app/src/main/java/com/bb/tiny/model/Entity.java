package com.bb.tiny.model;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * * Copyright (C) 2021  zhong002
 *
 * @author zhong002
 * @version 1.0
 * @mail 44010083@qq.com
 * @description
 * @create: 2021-11-20
 **/
public class Entity<T> {
    private int code;
    private String msg;
    private T data;

    public Entity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static EntityBuilder builder(int status) {
        return EntityBuilder.create(status);
    }


    public static <T> ResponseEntity<Entity<T>> success(T data) {
        EntityBuilder builder = EntityBuilder.create(200);
        return builder.build(200, "", data);
    }


    public static <T> ResponseEntity<Entity<T>> failure(int code, String msg) {
        EntityBuilder builder = EntityBuilder.create(code);
        return builder.build(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class EntityBuilder {

        private final HttpHeaders headers = new HttpHeaders();
        private final HttpStatus status;

        private EntityBuilder(HttpStatus status) {
            this.status = status;
        }

        public static EntityBuilder create(int status) {
            return new EntityBuilder(HttpStatus.valueOf(status));
        }


        public <T> ResponseEntity<Entity<T>> build(int code, String msg, T data) {
            return new ResponseEntity<>(new Entity<>(code, msg, data), this.status);
        }

    }
}
