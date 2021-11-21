package com.bb.tiny.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.bb.tiny.util.GlobalConstant;

@DynamoDBTable(tableName = GlobalConstant.DICT_TABLE_NAME)
public class Dict {
    private String name;
    private String value;

    @DynamoDBHashKey
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
