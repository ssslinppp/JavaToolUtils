package com.ssslinppp.demo1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 15:42 <br/>
 * To change this template use File | Settings | File Templates.
 */
public class ItemTest {
    /**
     * 序列化：
     * 只会序列化：@JsonView(Views.Public.class)声明为属性;
     * 而 @JsonView(Views.Internal.class)声明的属性不会被序列化；
     * <p>
     * output:
     * ########################
     * {"id":2,"itemName":"itemAA"}
     * ########################
     */
    @Test
    public void whenUsePublicView_thenOnlyPublicSerialized()
            throws JsonProcessingException {

        Map<String, User> users = Maps.newHashMap();
        users.put("u1", new User(1, "Tom"));
        users.put("u2", new User(2, "Jeyen"));

        Item item = Item.builder().
                id(2).
                itemName("itemAA").
                ownerName("ownerName").
                itemType(Item.ItemType.boys).
                users(users).build();

        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String result = mapper
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);

        System.out.println("########################");
        System.out.println(result);
        System.out.println("########################");
    }

    /**
     * 反序列化：
     * 仅仅反序列化@JsonView(Views.Public.class)声明为属性
     * <p>
     * output：
     * ########################
     * Item(id=2, itemName=itemAA, ownerName=null, itemType=null, users=null)
     * ########################
     *
     * @throws IOException
     */
    @Test
    public void whenUseJsonViewToDeserialize_thenCorrect() throws IOException {
        String json = "{\"id\":2,\"itemName\":\"itemAA\"}";

        ObjectMapper mapper = new ObjectMapper();
        Item item = mapper
                .readerWithView(Views.Public.class)
                .forType(Item.class)
                .readValue(json);
        System.out.println("########################");
        System.out.println(item);
        System.out.println("########################");
    }

    /**
     * 所有声明为 @JsonView(Views.Public.class) 和 @JsonView(Views.Internal.class)的属性，都会被序列化；
     * 原因： Internal继承自Public
     * <p>
     * output:
     * ########################
     * {"id":2,"itemName":"itemAA","ownerName":"ownerName"}
     * ########################
     */
    @Test
    public void whenUseInternalView_thenAllSerialized()
            throws JsonProcessingException {
        Map<String, User> users = Maps.newHashMap();
        users.put("u1", new User(1, "Tom"));
        users.put("u2", new User(2, "Jeyen"));

        Item item = Item.builder().
                id(2).
                itemName("itemAA").
                ownerName("ownerName").
                itemType(Item.ItemType.boys).
                users(users).build();

        ObjectMapper mapper = new ObjectMapper();

        String result = mapper
                .writerWithView(Views.Internal.class)
                .writeValueAsString(item);

        System.out.println("########################");
        System.out.println(result);
        System.out.println("########################");
    }

    /**
     * 仅会序列化：@JsonView(Views.NotImportant.class)的属性；
     * <p>
     * output:
     * ########################
     * 当user没有声明@JsonViews时，会返回所有属性
     * {"itemType":"boys","users":{"u1":{"id":1,"name":"Tom"},"u2":{"id":2,"name":"Jeyen"}}}
     * <p>
     * 当user声明@JsonViews时，只会返回声明了@JsonView(Views.NotImportant
     * {"itemType":"boys","users":{"u1":{"name":"Tom"},"u2":{"name":"Jeyen"}}}
     * .class)的属性
     * ########################
     */
    @Test
    public void whenUseNotImportantView_thenNotImportantSerialized()
            throws JsonProcessingException {
        Map<String, User> users = Maps.newHashMap();
        users.put("u1", new User(1, "Tom"));
        users.put("u2", new User(2, "Jeyen"));

        Item item = Item.builder().
                id(2).
                itemName("itemAA").
                ownerName("ownerName").
                itemType(Item.ItemType.boys).
                users(users).build();

        ObjectMapper mapper = new ObjectMapper();

        String result = mapper
                .writerWithView(Views.NotImportant.class)
                .writeValueAsString(item);

        System.out.println("########## 序列化结果 ##############");
        System.out.println(result);
        System.out.println("########################");
    }

    /**
     * 反序列化
     *
     * @throws IOException
     */
    @Test
    public void useJsonViewToDeserialize_thenCorrect() throws IOException {
        // whenUseNotImportantView_thenNotImportantSerialized()的结果
        String json = "{\"itemType\":\"boys\",\"users\":{\"u1\":{\"id\":1,\"name\":\"Tom\"},\"u2\":{\"id\":2,\"name\":\"Jeyen\"}}}";

        ObjectMapper mapper = new ObjectMapper();
        Item item = mapper
                .readerWithView(Views.NotImportant.class)
                .forType(Item.class)
                .readValue(json);
        System.out.println("######## 反序列化结果 ################");
        System.out.println(item);
        System.out.println("########################");
    }
}