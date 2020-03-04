package com.ssslinppp.demo1;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 15:19 <br/>
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
public class User {
    @JsonView(Views.Public.class)
    public int id;

    @JsonView(Views.NotImportant.class)
    public String name;
}
