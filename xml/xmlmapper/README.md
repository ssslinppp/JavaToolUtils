# 读写XML
## Step1：将Java POJO对象写入到XML文件，文件内容如下
```xml
<schema name="post" version="1.5">
	<uniqueKey>post_id</uniqueKey>
	<types>
		<fieldType className="java.lang.Long" name="Long"/>
		<fieldType className="java.lang.String" name="String"/>
		<fieldType className="xxx.yyy.text" name="Text"/>
		<analyzer tokenizerClass="solr.StandartTokenizerFactory" type="index"/>
		<analyzer tokenizerClass="solr.StandartTokenizerFactory" type="query"/>
	</types>
	<fields>
		<field name="post_id" required="true" type="Long">唯一标示</field>
		<field name="post_title" required="true" type="String">标题</field>
		<field name="post_context" required="false" type="Text">内容</field>
	</fields>
	<copyField dest="text" source="post_content"/>
	<copyField dest="text" source="post_content"/>
	<copyField dest="text" source="section_name"/>
	<MyMap>
		<keyA>valueA</keyA>
		<keyB>valueB</keyB>
		<keyC>valueC</keyC>
	</MyMap>
</schema>
```

## Step2: 读取Step1产生的XML到POJO对象中

---
# 具体步骤
## 依赖
XML转换使用的依赖
```
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.9.0</version>
</dependency>
<dependency>
    <groupId>org.codehaus.woodstox</groupId>
    <artifactId>woodstox-core-asl</artifactId>
    <version>4.4.1</version>
</dependency>
```
工具包：
```
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>19.0</version>
</dependency>
 <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.16.18</version>
    <scope>provided</scope>
</dependency>
```

## 构建XmlMapper
1. 构建JacksonXmlModule；
2. 设置JacksonXmlModule属性；
3. 创建XmlMapper，并设置JacksonXmlModule以及修改配置；
```
private ObjectMapper getXMLMapper() {
    //The Jackson XML module supports the full range of annotations that Jackson provides for annotating our POJOs.
    //The Jackson XML module adds some additional support for XML specific features
    JacksonXmlModule module = new JacksonXmlModule();
    module.setDefaultUseWrapper(false);  //As of version 2.9.0, this is the only configuration options for the XML Module
    
    ObjectMapper xmlMapper = new XmlMapper(module);
    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
    xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, Boolean.TRUE);
    xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);
    xmlMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, Boolean.TRUE);
    return xmlMapper;
}
```

## 主要注解
### @JacksonXmlRootElement(localName = "schema")
设置XML根节点名称
```
<schema>
   ...
</schema>
```
### @JacksonXmlProperty(isAttribute = true)
作为XML的属性存在，而不是节点存在
```
@JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
private String name;

@JacksonXmlProperty(isAttribute = true) /* 作为XML的属性存在，而不是节点存在 */
private double version;
```
对用的XML：
```
<schema name="post" version="1.5">
</schema>
```

### @JacksonXmlElementWrapper(localName = "fields")
主要用于集合，如List,Array,Collection等，
```
@JacksonXmlElementWrapper(localName = "fields")
@JsonProperty("field")
private List<Field> fieldList = Lists.newArrayList();
```
对应的XML
```
<fields>
	<field name="post_id" required="true" type="Long">唯一标示</field>
	<field name="post_title" required="true" type="String">标题</field>
	<field name="post_context" required="false" type="Text">内容</field>
</fields>
```

### @JsonProperty("copyField")
设置别名，没有使用 `@JacksonXmlElementWrapper`对List进行包装
```
@JsonProperty("copyField")
private List<CopyField> copyFields = Lists.newArrayList();
```
对应的XML
```
<copyField dest="text" source="post_content"/>
<copyField dest="text" source="post_content"/>
<copyField dest="text" source="section_name"/>
```

### @JsonPropertyOrder({"uniqueKey", "types"})  
指定输出到XML文件时，各节点的顺序

