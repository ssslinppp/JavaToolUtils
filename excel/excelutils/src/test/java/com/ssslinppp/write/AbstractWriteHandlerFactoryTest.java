package com.ssslinppp.write;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.ssslinppp.model.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * https://google.github.io/guava/releases/19.0/api/docs/com/google/common/reflect/TypeToken.html<br/>
 */
public class AbstractWriteHandlerFactoryTest {
    @Test
    public void createWriteHandle() throws Exception {

        Stopwatch stopwatch = Stopwatch.createStarted();

        List<Person> personList = Lists.newArrayList();
        for (int i = 0; i < 100 * 10000; i++) {
            personList.add(Person.getDemoPerson());
        }

        // 后面需要添加{}，表示是AbstractWriteHandleFactory的子类，在编译时会将Person编译进去，否则会产生泛型类型擦除
        AbstractWriteHandleFactory<Person> abstractWriteHandleFactory = new AbstractWriteHandleFactory<Person>() {
        };
        String filePath = "D:\\tempTempTemp\\MyExcel-100-10000-1.xlsx";
        WriteHandler writeHandler = abstractWriteHandleFactory.createWriteHandle(filePath);
        writeHandler.write("sheetName", personList);
        System.out.println("######## Done,耗时:" + stopwatch.elapsed(TimeUnit.SECONDS) + "(s) ########");
        writeHandler.write("sheetName", personList);
        writeHandler.write("sheetName", new ArrayList<Person>());
        writeHandler.flush();
        System.out.println("######## Done,耗时:" + stopwatch.stop().elapsed(TimeUnit.SECONDS) + "(s) ########");
    }

}
