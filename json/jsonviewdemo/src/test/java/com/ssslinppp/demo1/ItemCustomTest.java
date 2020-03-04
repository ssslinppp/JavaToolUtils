package com.ssslinppp.demo1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.google.common.collect.Maps;
import com.ssslinppp.demo2.MyBeanSerializerModifier;
import org.junit.Test;

import java.util.Map;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:03 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class ItemCustomTest {
    @Test
    public void whenUseCustomJsonViewToSerialize_thenCorrect()
            throws JsonProcessingException {
        Map<String, User> users = Maps.newHashMap();
        users.put("u1", new User(1, "Tom"));
        users.put("u2", new User(2, "Jeyen"));

        Item item = Item.builder().
                id(2).
                itemName("itemaa").
                ownerName("ownerName").
                itemType(Item.ItemType.boys).
                users(users).build();

        SerializerFactory serializerFactory = BeanSerializerFactory.instance.withSerializerModifier(new MyBeanSerializerModifier());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializerFactory(serializerFactory);

        String result = mapper
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);

        System.out.println(result);
    }

}