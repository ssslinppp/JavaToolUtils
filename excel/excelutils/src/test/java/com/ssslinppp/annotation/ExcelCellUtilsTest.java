package com.ssslinppp.annotation;

import com.ssslinppp.model.Person;
import org.junit.Test;

public class ExcelCellUtilsTest {

    @Test
    public void getOrderedCellTitle() throws Exception {
        ExcelCellUtils<Person> excelCellUtils = new ExcelCellUtils<Person>();

        System.out.println(excelCellUtils.getOrderedCellTitle(Person.getDemoPerson()));
        System.out.println(excelCellUtils.getOrderedCellTitle(Person.getDemoPerson()));
    }

    @Test
    public void getOrderedValues() throws Exception {
        ExcelCellUtils<Person> excelCellUtils = new ExcelCellUtils<Person>();

        System.out.println(excelCellUtils.getOrderedValues(Person.getDemoPerson()));
        System.out.println(excelCellUtils.getOrderedValues(Person.getDemoPerson()));
    }


}
