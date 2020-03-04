# The Apache API Basics
There are two main prefixes which you will encounter when working with Apache POI:
- `HSSF`: denotes the API is for working with Excel 2003 and earlier.
- `XSSF`: denotes the API is for working with Excel 2007 and later.

And to get started the Apache POI API, you just need to understand and use the following 4 interfaces:
- `Workbook`: high level representation of an Excel workbook. Concrete implementations are: `HSSFWorkbook` and `XSSFWorkbook`.
- `Sheet`: high level representation of an Excel worksheet. Typical implementing classes are `HSSFSheet` and `XSSFSheet`.
- `Row`: high level representation of a row in a spreadsheet. `HSSFRow` and `XSSFRow` are two concrete classes.
- `Cell`: high level representation of a cell in a row. `HSSFCell` and `XSSFCell` are the typical implementing classes.

# Excel写示例
Person.java： 使用@ExcelCell注解
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @ExcelCell(priority = 14, cellTitle = "姓名")
    private String name;
    @ExcelCell(priority = 10, cellTitle = "age")
    private int age;
    @ExcelCell(priority = 11, cellTitle = "性别")
    private Sex sex;
    @ExcelCell(priority = 25, cellTitle = "生日")
    private Date birthDay;

    public static Person getDemoPerson() {
        Date date = new Date();
        date.setTime(date.getTime() - new Random().nextInt(10000) * 1000 * 10000);
        Person person = new Person("Tom", new Random().nextInt(100), Sex.man, date);
        return person;
    }

    public enum Sex {
        man,
        woman;
    }
}
```
下面是写测试类：
```java
public class AbstractWriteHandleFactoryTest {
    @Test
    public void createWriteHandle() throws Exception {

        List<Person> personList = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            personList.add(Person.getDemoPerson());
        }

        // 后面需要添加{}，表示是AbstractWriteHandleFactory的子类，在编译时会将Person编译进去，否则会产生泛型类型擦除
        AbstractWriteHandleFactory<Person> abstractWriteHandleFactory = new AbstractWriteHandleFactory<Person>(){};
        String filePath = "D:\\tempTempTemp\\MyExcel2.xlsx";
        WriteHandle writeHandler = abstractWriteHandleFactory.createWriteHandle(filePath);
        writeHandler.write("sheetName", personList);
        writeHandler.write("sheetName", new ArrayList<Person>());
        writeHandler.flush();
        System.out.println("######## Done ########");
    }
}
```

# Excel读示例
```java
public class AbstractReadHandlerFactoryTest {
    @Test
    public void createExcelReadHandler() throws Exception {
        String filePath = "D:\\tempTempTemp\\MyExcel2.xlsx";

        AbstractReadHandlerFactory<Person> abstractReadHandlerFactory = new AbstractReadHandlerFactory<Person>() {
        };
        IExcelReadHandler excelReadHandler = abstractReadHandlerFactory.createExcelReadHandler();

        //读取所有Sheet
        List<Person> personListAll = excelReadHandler.readAllSheet(filePath);
        System.out.println("All sheet,List size():" + personListAll.size());
        System.out.println(personListAll);
        System.out.println();

        // 按照SheetIndex读取文件
        List<Person> personList2 = excelReadHandler.read(filePath, 2);
        System.out.println("Sheet By sheetIndex,List size():" + personList2.size());
        System.out.println(personList2);
        System.out.println();

        // 按照SheetName读取文件
        List<Person> personList1 = excelReadHandler.read(filePath, "sheetBBB");
        System.out.println("Sheet by SheetName ,List size():" + personList1.size());
        System.out.println(personList2);
    }
}
```

## 性能测试
### 写入测试
- 写入100万条数据，耗时：7秒；
- 写入200万条数据，耗时：18秒；
```
######## Done,耗时:7(s) ########
######## Done,耗时:18(s) ########
```

### 读取测试
- 200万条：32秒
- 100万条：14秒；
```
All sheet,List size():2000000

######## Done,耗时:32(s) ########
Sheet By sheetIndex,List size():1000000

Sheet by SheetName ,List size():0
######## Done,耗时:46(s) ########
```

## 其他demo
1. ExcelReaderDemo.java：展示了通用的读，支持2003/2007，把数据一次性加载到内存中，占用JVM内存较大，不适合大数据量读取；
2. EventExample.java: 仅支持2003版，使用了 HSSFListener，采用逐行读取方式，读取效率更高，适合大数据量的读取；


