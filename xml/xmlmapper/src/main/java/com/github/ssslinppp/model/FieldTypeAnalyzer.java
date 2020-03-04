package com.github.ssslinppp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class FieldTypeAnalyzer {
    @JsonProperty("fieldType")
    private List<FieldType> fieldTypeList = Lists.newArrayList();

    @JsonProperty("analyzer")
    private List<TypeAnalyzer> typeAnalyzerList = Lists.newArrayList();

    public static FieldTypeAnalyzer getDemo() {
        FieldTypeAnalyzer fieldTypeAnalyzer = new FieldTypeAnalyzer();
        fieldTypeAnalyzer.getFieldTypeList().add(new FieldType("Long", "java.lang.Long"));
        fieldTypeAnalyzer.getFieldTypeList().add(new FieldType("String", "java.lang.String"));
        fieldTypeAnalyzer.getFieldTypeList().add(new FieldType("Text", "xxx.yyy.text"));
        fieldTypeAnalyzer.getTypeAnalyzerList().add(new TypeAnalyzer("index", "solr.StandartTokenizerFactory"));
        fieldTypeAnalyzer.getTypeAnalyzerList().add(new TypeAnalyzer("query", "solr.StandartTokenizerFactory"));

        return fieldTypeAnalyzer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldType {
        @JacksonXmlProperty(isAttribute = true)
        private String name;

        @JacksonXmlProperty(isAttribute = true)
        private String className;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeAnalyzer {
        @JacksonXmlProperty(isAttribute = true)
        private String type;

        @JacksonXmlProperty(isAttribute = true)
        private String tokenizerClass;
    }
}
