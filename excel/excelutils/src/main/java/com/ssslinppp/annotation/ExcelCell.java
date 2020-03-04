package com.ssslinppp.annotation;

import java.lang.annotation.*;

/**
 * 添加在POJO对象的Field上面，用于excel导入导出
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {

    int priority() default 0;

    String cellTitle() default "";
}
