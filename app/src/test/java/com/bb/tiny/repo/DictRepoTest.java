package com.bb.tiny.repo;

import com.alibaba.fastjson.JSON;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.bb.tiny.model.entity.Dict;
import com.bb.tiny.util.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@Slf4j
//为了测试往数据仓库读写数据Mapper是否正确，所以未使用PowerMockRunner进行测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DictRepo.class, com.bb.tiny.TinyApplication.class})
@WebAppConfiguration
public class DictRepoTest {
    @Resource
    DictRepo dictRepo;
    DynamoDBMapper dynamoDBMapper;
    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    @Before
    public void setup() {
        //初始化，创建表
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Dict.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(Long.parseLong(String.valueOf(GlobalConstant.ONE)), Long.parseLong(String.valueOf(GlobalConstant.ONE))));
        try {
            amazonDynamoDB.createTable(tableRequest);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

    }

    @Test
    public void testSave() {
        Dict dict = new Dict();
        dict.setName(String.valueOf(GlobalConstant.ONE));
        dict.setValue(GlobalConstant.EMPTY_URL_ERROR);

        dictRepo.save(dict);
        Dict result = dictRepo.findByName(String.valueOf(GlobalConstant.ONE)).get();
        assertEquals(GlobalConstant.EMPTY_URL_ERROR, result.getValue());
    }

    @Test
    public void testFindAll() {
        List<Dict> result = (List<Dict>) dictRepo.findAll();
        log.info(JSON.toJSONString(result));
        assertTrue(result.size() > 0);
    }

    @Test
    public void testFindByValue() {
        Dict result = dictRepo.findByValue(GlobalConstant.EMPTY_URL_ERROR).get();
        assertEquals(String.valueOf(GlobalConstant.ONE), result.getName());
    }
}
