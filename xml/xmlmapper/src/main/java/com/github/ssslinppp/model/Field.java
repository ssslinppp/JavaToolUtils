package com.github.ssslinppp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@AllArgsConstructor  /*  使用了{@code  @JacksonXmlText}时，不能使用{@code @AllArgsConstructor}，否则会报错 */
@NoArgsConstructor
public class Field {
    @JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
    private String name;

    @JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
    private String type;

    @JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
    private boolean required = true;

    /**
     * 特别说明：
     * 使用了{@code  @JacksonXmlText}时，不能使用{@code @AllArgsConstructor}，否则会报错，这时可以显示的指定构造函数，替代{@code @AllArgsConstructor}
     * <p>
     * [使用示例](https://stackoverflow.com/questions/23632419/how-to-deserialize-xml-with-annotations-using-fasterxml)
     */
    @JacksonXmlText
    private String desc;

    /**
     * 显示的构造函数，而不是使用{@code @AllArgsConstructor}，解决{@code  @JacksonXmlText}报错的bug；
     * <p>
     * [bug解决]https://stackoverflow.com/questions/40546508/jsoncreator-could-not-find-creator-property-with-name-even-with-ignoreunknown
     *
     * @param name
     * @param type
     * @param required
     * @param desc
     */
    public Field(String name, String type, boolean required, String desc) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.desc = desc;
    }

    public static Field getDemo() {
        return new Field("post_id", "Long", true, "post_id");
    }

    public static Field getDemo(String name, String type, boolean required, String desc) {
        return new Field(name, type, required, desc);
    }

    public static List<Field> getListDemo() {
        List<Field> fields = Lists.newArrayList();
        fields.add(getDemo("post_id", "Long", true, "唯一标示"));
        fields.add(getDemo("post_title", "String", true, "标题"));
        fields.add(getDemo("post_context", "Text", false, "内容"));
        return fields;
    }
}

