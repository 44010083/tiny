package com.bb.tiny.api;


import com.bb.tiny.model.Entity;
import com.bb.tiny.model.vo.TinyVo;
import com.bb.tiny.service.ITinyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@RestController
@Api(tags = {"短url管理"})
@RequestMapping("/")
public class TinyController {
    @Resource
    private ITinyService tinyService;

    @ApiOperation(value = "长url转位短url", notes = "长url转位短url")
    @RequestMapping(value = "/tiny", method = RequestMethod.POST)
    public ResponseEntity<Entity<String>> getTiny(
            @ApiParam(value = "请求body，输入url参数") @RequestBody TinyVo tinyVo) {
        try {
            return Entity.success(tinyService.getTiny(tinyVo.getUrl()));
        } catch (Exception e) {
            return Entity.builder(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    @ApiOperation(value = "短url访问，自动301到目标长url", notes = "短url访问，自动301到目标长url")
    @RequestMapping(value = "/t/{uri}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUrl(@PathVariable String uri) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, tinyService.getUrl(uri)).build();
    }

}
