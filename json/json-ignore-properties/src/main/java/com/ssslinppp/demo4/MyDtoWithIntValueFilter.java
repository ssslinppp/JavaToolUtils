package com.ssslinppp.demo4;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

/**
 * 设置顾虑器
 */
@JsonFilter("intValueFilter")
@Data
public class MyDtoWithIntValueFilter {
    private String stringValue;
    private int intValue;
    private boolean booleanValue;
}
