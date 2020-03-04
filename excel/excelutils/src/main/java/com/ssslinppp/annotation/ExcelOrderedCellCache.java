package com.ssslinppp.annotation;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 */
public enum ExcelOrderedCellCache {
    instance;

    private Map<Class, List<ExcelCell>> orderCellsMap = Maps.newConcurrentMap();
    private Map<Class, List<Field>> orderedFieldsMap = Maps.newConcurrentMap();

    public List<ExcelCell> getOrderedCells(Class clazz) {
        return this.orderCellsMap.get(clazz);
    }

    public void addOrderedCells(Class clazz, List<ExcelCell> excelCells) {
        if (!CollectionUtils.isEmpty(excelCells)) {
            this.orderCellsMap.put(clazz, excelCells);
        }
    }

    public List<Field> getOrderedFields(Class clazz) {
        return this.orderedFieldsMap.get(clazz);
    }

    public void addOrderedFields(Class clazz, List<Field> fields) {
        if (!CollectionUtils.isEmpty(fields)) {
            this.orderedFieldsMap.put(clazz, fields);
        }
    }
}




