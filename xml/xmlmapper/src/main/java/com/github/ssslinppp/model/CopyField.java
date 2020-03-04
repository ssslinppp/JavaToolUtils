package com.github.ssslinppp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CopyField {
    @JacksonXmlProperty(isAttribute = true)
    private String source;

    @JacksonXmlProperty(isAttribute = true)
    private String dest;

    public static List<CopyField> getListDemo() {
        List<CopyField> list = Lists.newArrayList();
        list.add(new CopyField("post_content", "text"));
        list.add(new CopyField("post_content", "text"));
        list.add(new CopyField("section_name", "text"));
        return list;
    }
}
