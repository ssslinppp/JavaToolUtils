package com.ssslinppp.demo4;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:56 <br/>
 * To change this template use File | Settings | File Templates.
 */
@JsonFilter("stringValueFilter")
@Data
public class MyDtoWithStringValueFilter {
    private String stringValue;
    private int intValue;
    private boolean booleanValue;
}
