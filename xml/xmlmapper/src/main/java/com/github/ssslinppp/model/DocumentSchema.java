package com.github.ssslinppp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 参考链接：
 * https://stackify.com/java-xml-jackson/
 * https://github.com/FasterXML/jackson-dataformat-xml
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "schema") /* 设置XML根节点名称 */
@JsonPropertyOrder({"uniqueKey", "types"})  /* 指定输出到XML文件时，各节点的顺序 */
public class DocumentSchema {
    @JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
    private String name;

    @JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
    private double version;

    /**
     * 注意与 {@code List<copyField>}的区别，使用了{@code  @JacksonXmlElementWrapper}
     * <p>
     * <li>
     * <ui>{@code  @JacksonXmlElementWrapper(localName = "fields")}: 用于控制外层节点的名称</ui>
     * <ui>{@code  @JsonProperty("field")}: 用于控制内层节点的名称</ui>
     * </li>
     * <p>
     * 输出结果如下：
     * <pre>
     * <fields>
     *      <field name="post_id" required="true" type="long_type"/>
     *      <field name="post_title" required="true" type="string_type"/>
     *      <field name="post_context" required="false" type="text_type"/>
     * </fields>
     * </pre>
     * <p>如果不设置{@code @JsonProperty("field")}，则输出结果为：
     * <pre>
     *     <fields>
     *       <fieldList name="post_id" required="true" type="long_type"/>
     *       <fieldList name="post_title" required="true" type="string_type"/>
     *       <fieldList name="post_context" required="false" type="text_type"/>
     * </fields>
     * </pre>
     * <p>
     */
    @JacksonXmlElementWrapper(localName = "fields")
    @JsonProperty("field")
    private List<Field> fieldList = Lists.newArrayList();

    private String uniqueKey;

    /**
     * 注意与 {@code List<Field>}的区别，没有使用{@code  @JacksonXmlElementWrapper}<br/>
     * <p>
     * <b>未</b>添加{@code @JsonProperty("copyField")},输出为：
     * <pre>
     * <copyFields dest="text" source="post_content"/>
     * <copyFields dest="text" source="post_content"/>
     * <copyFields dest="text" source="section_name"/>
     * </pre>
     * <p>
     * 添加{@code @JsonProperty("copyField")},输出为：
     * <pre>
     * <copyField dest="text" source="post_content"/>
     * <copyField dest="text" source="post_content"/>
     * <copyField dest="text" source="section_name"/>
     * </pre>
     */
       @JsonProperty("copyField")
    private List<CopyField> copyFields = Lists.newArrayList();

    @JsonProperty("types")
    private FieldTypeAnalyzer fieldTypeAnalyzer;

    /**
     * 输出到XML文件时，不包括map属性；
     * <p>
     * 当添加了注释{@code @JsonIgnore }后，该Map不会被序列化到XML中
     */
//    @JsonIgnore
    @JsonProperty("MyMap")
    private Map<String, String> map = Maps.newHashMap();

    public DocumentSchema(String name, double version, List<Field> fieldList, String uniqueKey, List<CopyField> copyFields,
                          FieldTypeAnalyzer fieldTypeAnalyzer, Map<String, String> map) {
        this.name = name;
        this.version = version;
        this.fieldList = fieldList;
        this.uniqueKey = uniqueKey;
        this.copyFields = copyFields;
        this.fieldTypeAnalyzer = fieldTypeAnalyzer;
        this.map = map;
    }

    public static DocumentSchema getDemo() {
        Map map = Maps.newHashMap();
        map.put("keyA", "valueA");
        map.put("keyB", "valueB");
        map.put("keyC", "valueC");

        DocumentSchema documentSchema = DocumentSchema.builder().name("post").uniqueKey("post_id").version(1.5)
                .map(map).fieldList(Field.getListDemo()).copyFields(CopyField.getListDemo())
                .fieldTypeAnalyzer(FieldTypeAnalyzer.getDemo()).build();

        return documentSchema;
    }
}
