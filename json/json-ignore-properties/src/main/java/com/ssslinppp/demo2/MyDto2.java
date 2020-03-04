package com.ssslinppp.demo2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:47 <br/>
 * To change this template use File | Settings | File Templates.
 */
@Data
public class MyDto2 {
    private String stringValue;

    @JsonIgnore
    private int intValue;

    private boolean booleanValue;

}
