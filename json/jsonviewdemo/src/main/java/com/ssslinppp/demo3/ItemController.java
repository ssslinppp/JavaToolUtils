package com.ssslinppp.demo3;

import com.fasterxml.jackson.annotation.JsonView;
import com.ssslinppp.demo1.Item;
import com.ssslinppp.demo1.Views;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:14 <br/>
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    /**
     * 返回：
     * <pre>
     *     {
     * "id": 3,
     * "itemName": "itemAA"
     * }
     * </pre>
     *
     * @param id
     * @return
     */
    @JsonView(Views.Public.class)
    @RequestMapping("/public/{id}")
    public Item getItemPublic(@PathVariable int id) {
        return Item.getDemo(id);
    }

    /**
     * 返回：
     * <pre>
     *   {
     * "id": 3,
     * "itemName": "itemAA",
     * "ownerName": "ownerName"
     * }
     * </pre>
     *
     * @param id
     * @return
     */
    @JsonView(Views.Internal.class)
    @RequestMapping("/internal/{id}")
    public Item getItemInternal(@PathVariable int id) {
        return Item.getDemo(id);
    }

    /**
     * 返回：
     * <pre>
     * {
     * "itemType": "boys",
     * "users": {
     * "u1": {
     * "name": "Tom"
     * },
     * "u2": {
     * "name": "Jeyen"
     * }
     * }
     * }
     * </pre>
     *
     * @param id
     * @return
     */
    @JsonView(Views.NotImportant.class)
    @RequestMapping("/notimportant/{id}")
    public Item notimportant(@PathVariable int id) {
        Item item = Item.getDemo(id);

        return item;
    }
}
