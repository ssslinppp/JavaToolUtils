package com.ssslinppp.reader;

import com.google.common.base.Stopwatch;
import com.ssslinppp.model.Person;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AbstractReadHandlerFactoryTest {
    @Test
    public void createExcelReadHandler() throws Exception {
        String filePath = "D:\\tempTempTemp\\MyExcel-100-10000-1.xlsx";

        AbstractReadHandlerFactory<Person> abstractReadHandlerFactory = new AbstractReadHandlerFactory<Person>() {
        };
        IExcelReadHandler excelReadHandler = abstractReadHandlerFactory.createExcelReadHandler();

        Stopwatch stopwatch = Stopwatch.createStarted();

        //读取所有Sheet
        List<Person> personListAll = excelReadHandler.readAllSheet(filePath);
        System.out.println("All sheet,List size():" + personListAll.size());
//        System.out.println(personListAll);
        System.out.println();
        System.out.println("######## Done,耗时:" + stopwatch.elapsed(TimeUnit.SECONDS) + "(s) ########");

        // 按照SheetIndex读取文件
        List<Person> personList1 = excelReadHandler.read(filePath, 1);
        System.out.println("Sheet By sheetIndex,List size():" + personList1.size());
//        System.out.println(personList2);
        System.out.println();

        // 按照SheetName读取文件
        List<Person> personList2 = excelReadHandler.read(filePath, "sheetName-2");
        System.out.println("Sheet by SheetName ,List size():" + personList2.size());
//        System.out.println(personList2);
        System.out.println("######## Done,耗时:" + stopwatch.stop().elapsed(TimeUnit.SECONDS) + "(s) ########");

    }
}
