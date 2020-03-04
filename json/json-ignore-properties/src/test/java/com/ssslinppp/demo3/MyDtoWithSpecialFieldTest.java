package com.ssslinppp.demo3;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:52 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class MyDtoWithSpecialFieldTest {
    @Test
    public final void givenFieldTypeIsIgnored_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        //重点： 忽略String[]类型
        mapper.addMixIn(String[].class, MyMixInForIgnoreType.class);

        MyDtoWithSpecialField dtoObject = new MyDtoWithSpecialField();
        dtoObject.setBooleanValue(true);

        String dtoAsString = mapper.writeValueAsString(dtoObject);

        System.out.println(dtoAsString);
    }
}