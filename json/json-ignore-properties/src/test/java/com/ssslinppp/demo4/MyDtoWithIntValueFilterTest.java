package com.ssslinppp.demo4;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.io.IOException;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:57 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class MyDtoWithIntValueFilterTest {

    /**
     * {"stringValue":null,"booleanValue":false}
     *
     * @throws JsonParseException
     * @throws IOException
     */
    @Test
    public final void givenTypeHasFilterThatIgnoresFieldByName_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        //定义过滤器
        SimpleBeanPropertyFilter intValueFilter = SimpleBeanPropertyFilter.serializeAllExcept("intValue");
        SimpleBeanPropertyFilter stringValueFilter = SimpleBeanPropertyFilter.serializeAllExcept("stringValue");
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("intValueFilter", intValueFilter)
                .addFilter("stringValueFilter", stringValueFilter);
        //设置顾虑器
        mapper.setFilterProvider(filters);

        String dto1 = mapper.writeValueAsString(new MyDtoWithIntValueFilter());
        String dto2 = mapper.writeValueAsString(new MyDtoWithStringValueFilter());

        System.out.println("intValueFilter:" + dto1);
        System.out.println("stringValueFilter:" + dto2);
    }

}