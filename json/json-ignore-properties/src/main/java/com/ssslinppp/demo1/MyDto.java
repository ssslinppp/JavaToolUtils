package com.ssslinppp.demo1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Description：<br/>
 * User: liulin <br/>
 * Date: 2017/11/10 <br/>
 * Time: 17:44 <br/>
 * To change this template use File | Settings | File Templates.
 */
@Data
@JsonIgnoreProperties(value = {"intValue"})
public class MyDto {
    private String stringValue;
    private int intValue;
    private boolean booleanValue;
}
