package com.ssslinppp.demo1;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 测试类：ItemTest.java
 */
@Data
@Builder
public class Item {
    @JsonView(Views.Public.class)
    private int id;

    @JsonView(Views.Public.class)
    private String itemName;

    @JsonView(Views.Internal.class)
    private String ownerName;

    @JsonView(Views.NotImportant.class)
    private ItemType itemType;

    @JsonView(Views.NotImportant.class)
    private Map<String, User> users = Maps.newHashMap();

    public static Item getDemo(int id) {
        Map<String, User> users = Maps.newHashMap();
        users.put("u1", new User(1, "Tom"));
        users.put("u2", new User(2, "Jeyen"));

        Item item = Item.builder().
                id(id).
                itemName("itemAA").
                ownerName("ownerName").
                itemType(Item.ItemType.boys).
                users(users).build();

        return item;
    }

    @AllArgsConstructor
    public enum ItemType {
        boys("For boys"),
        audult("For audult");

        private String desc;
    }

}
