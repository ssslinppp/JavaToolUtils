package com.ssslinppp.demo2;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssslinppp.demo1.MyDto;
import org.junit.Test;

import java.io.IOException;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:48 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class MyDto2Test {
    /**
     * {"stringValue":null,"booleanValue":false}
     *
     * @throws JsonParseException
     * @throws IOException
     */
    @Test
    public void givenFieldIsIgnoredDirectly_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        MyDto dtoObject = new MyDto();

        String dtoAsString = mapper.writeValueAsString(dtoObject);

        System.out.println(dtoAsString);
    }
}