package com.ssslinppp.annotation;

import com.google.common.collect.Lists;
import com.ssslinppp.exception.ExcelException;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ExcelCellUtils<T extends Object> {
    private ExcelOrderedCellCache orderedCellCache = ExcelOrderedCellCache.instance;

    /**
     * 按优先级顺序，获取声明了{@code @ExcelCell}注解的属性值;
     *
     * @param t
     * @return
     */
    public List<Object> getOrderedValues(T t) {
        List<Field> fields = getOrderedFields(t);

        List<Object> values = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(fields)) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    values.add(field.get(t));
                } catch (IllegalAccessException e) {
                    throw new ExcelException("反射获取Field的值异常", e);
                }
            }
        }

        return values;
    }

    /**
     * 按优先级顺序，获取声明了{@code @ExcelCell}注解的属性的 cellTitle 值;
     *
     * @param t
     * @return
     */
    public List<String> getOrderedCellTitle(T t) {
        List<ExcelCell> cells = getOrderedExcelCells(t);

        List<String> cellTitles = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(cells)) {
            for (ExcelCell cell : cells) {
                cellTitles.add(cell.cellTitle());
            }
        }

        return cellTitles;
    }

    /**
     * 按优先级顺序，获取声明了{@code @ExcelCell}注解的属性;
     * <p>
     * 会向上遍历父类
     *
     * @param t
     * @return
     */
    @SuppressWarnings("Since15")
    private List<ExcelCell> getOrderedExcelCells(T t) {
        if (!CollectionUtils.isEmpty(orderedCellCache.getOrderedCells(t.getClass()))) {
            return orderedCellCache.getOrderedCells(t.getClass());
        }

        List<ExcelCell> cells = Lists.newArrayList();
        Class clz = t.getClass();

        while (clz != null) {
            for (Field field : clz.getDeclaredFields()) {
                ExcelCell annotation = field.getDeclaredAnnotation(ExcelCell.class);
                if (annotation != null) {
                    cells.add(annotation);
                }
            }

            clz = clz.getSuperclass();
        }

        cells.sort(new Comparator<ExcelCell>() {
            @Override
            public int compare(ExcelCell o1, ExcelCell o2) {
                return priorityCompare(o1, o2);
            }
        });
        orderedCellCache.addOrderedCells(t.getClass(), cells);

        return cells;
    }

    @SuppressWarnings("Since15")
    public List<Field> getOrderedFields(T t) {
        if (!CollectionUtils.isEmpty(orderedCellCache.getOrderedFields(t.getClass()))) {
            return orderedCellCache.getOrderedFields(t.getClass());
        }

        List<Field> fields = Lists.newArrayList();

        Class clz = t.getClass();
        while (clz != null) {
            for (Field field : clz.getDeclaredFields()) {
                ExcelCell annotation = field.getDeclaredAnnotation(ExcelCell.class);
                if (annotation != null) {
                    fields.add(field);
                }
            }

            clz = clz.getSuperclass();
        }

        fields.sort(new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                ExcelCell annotation1 = o1.getDeclaredAnnotation(ExcelCell.class);
                ExcelCell annotation2 = o2.getDeclaredAnnotation(ExcelCell.class);
                return priorityCompare(annotation1, annotation2);
            }
        });
        orderedCellCache.addOrderedFields(t.getClass(), fields);

        return fields;

    }

    private int priorityCompare(ExcelCell o1, ExcelCell o2) {
        return o1.priority() - o2.priority();
    }

}

















