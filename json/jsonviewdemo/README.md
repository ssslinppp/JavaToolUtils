# serialize/deserialize an object with @JsonView.
## 引入依赖   
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
该依赖会自动引用我们需要的Json库：
```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
</dependency>
```
---

## 示例1: 使用@JsonView(xxx.class) 可以只序列化/反序列化感兴趣的属性

```
public class Views {
  public static class Public {
  }

  public static class Internal extends Public {
  }

  public static class NotImportant {

  }
}

@JsonView(Views.Internal.class)
private String ownerName;

@JsonView(Views.NotImportant.class)
private ItemType itemType;

@JsonView(Views.NotImportant.class)
private Map<String, User> users = Maps.newHashMap();

```

---

## 示例2：Customize JSON Views 
估计不会用，没有细追究

## 示例3： 定义Restful API响应

```
@JsonView(Views.Public.class)
@RequestMapping("/items/{id}")
public Item getItemPublic(@PathVariable int id) {
    return ItemManager.getById(id);
}
```


## 参考链接
[Jackson JSON Views](http://www.baeldung.com/jackson-json-view-annotation)    









