package com.bb.tiny.api;

import com.alibaba.fastjson.JSON;
import com.bb.tiny.model.Entity;
import com.bb.tiny.model.entity.Dict;
import com.bb.tiny.model.vo.TinyVo;
import com.bb.tiny.service.ITinyService;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@Slf4j
// 告诉JUnit使用PowerMockRunner进行测试
@RunWith(PowerMockRunner.class)
// 所有需要测试的类列在此处，适用于模拟final类或有final,private,static,native方法的类
@PrepareForTest({MockUtil.class})
// 为了解决使用powermock后，提示classloader错误
public class TinyControllerTest {
    String errUrl = "1https://www.baidu.com";
    String url = "https://www.baidu.com";
    String tiny = "6";

    @InjectMocks
    TinyController tinyController;
    @Mock
    private ITinyService tinyService;

    @Before
    public void before() throws Exception {
        Dict dict = new Dict();
        dict.setValue(url);
        dict.setName(tiny);
        PowerMockito.when(tinyService.getTiny(url)).thenReturn(tiny);
        PowerMockito.when(tinyService.getTiny(tiny)).thenReturn(url);

        PowerMockito.when(tinyService.getTiny(errUrl)).thenThrow(new Exception(GlobalConstant.EXPLORE_URL_ERROR + errUrl));

    }

    @After
    public void after() {
    }

    /**
     * Method: getTiny(@ApiParam(value = "请求body，输入url参数") @RequestBody TinyVo tinyVo)
     */
    @Test
    public void testGetTiny() {
        TinyVo vo = new TinyVo();
        vo.setUrl(url);
        ResponseEntity<Entity<String>> reslut = tinyController.getTiny(vo);
        assertEquals(tiny, Objects.requireNonNull(reslut.getBody()).getData());
    }

    @Test
    public void testGetTinyWithErrUrl() {
        TinyVo vo = new TinyVo();
        vo.setUrl(errUrl);
        ResponseEntity<Entity<String>> reslut = tinyController.getTiny(vo);
        log.info(JSON.toJSONString(reslut));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), reslut.getStatusCodeValue());
        assertEquals(GlobalConstant.EXPLORE_URL_ERROR + errUrl, Objects.requireNonNull(reslut.getBody()).getMsg());
    }

    /**
     * Method: getUrl(@PathVariable String uri, HttpServletResponse response)
     */
    @Test
    public void testGetUrl() {
        ResponseEntity<Object> reslut = tinyController.getUrl(tiny);
        assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), reslut.getStatusCodeValue());
    }


} 
