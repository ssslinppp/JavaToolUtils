package com.ssslinppp.reader;

import com.bing.excel.reader.ExcelReadListener;
import com.bing.excel.reader.ExcelReaderFactory;
import com.bing.excel.reader.ReadHandler;
import com.bing.excel.vo.ListRow;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.ssslinppp.annotation.ExcelCellUtils;
import com.ssslinppp.exception.ExcelException;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@SuppressWarnings("Duplicates")
public class ExcelReadHandler<T> implements ExcelReadListener, IExcelReadHandler<T> {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<T> data; //读取文件时，保存数据的对象
    private ExcelCellUtils excelCellUtils = new ExcelCellUtils();
    private Class<T> clazz;

    public ExcelReadHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    private void clearData() {
        if (!CollectionUtils.isEmpty(this.data)) {
            this.data.clear();
        }
    }

    @Override
    public List<T> read(File f, String sheetName) {
        try {
            clearData();
            ReadHandler saxHandler = ExcelReaderFactory.create(f, this);
            saxHandler.readSheet(sheetName);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return data;
    }

    @Override
    public List<T> read(String filePath, String sheetName) {
        return read(new File(filePath), sheetName);
    }

    @Override
    public List<T> read(InputStream is, String sheetName) {
        return null;
    }

    @Override
    public List<T> read(File f, int sheetIndex) {
        try {
            clearData();
            ReadHandler saxHandler = ExcelReaderFactory.create(f, this);
            saxHandler.readSheet(sheetIndex);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return data;
    }

    @Override
    public List<T> read(String filePath, int sheetIndex) {
        return read(new File(filePath), sheetIndex);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<T> readAllSheet(File f) throws FileNotFoundException {
        try {
            clearData();
            ReadHandler saxHandler = ExcelReaderFactory.create(f, this);
            saxHandler.readSheets();
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return data;
    }

    @Override
    public List<T> readAllSheet(String filePath) throws FileNotFoundException {
        return readAllSheet(new File(filePath));
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<T> read(InputStream is, int sheetIndex) {
        try {
            clearData();
            ReadHandler saxHandler = ExcelReaderFactory.create(is, this);
            saxHandler.readSheet(sheetIndex);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return data;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<T> readAllSheet(InputStream is) {
        try {
            clearData();
            ReadHandler saxHandler = ExcelReaderFactory.create(is, this);
            saxHandler.readSheets();
        } catch (Exception e) {
            throw new ExcelException(e);
        }
        return data;
    }

    private T newInstance() {
        try {
            return this.clazz.newInstance();
        } catch (InstantiationException e) {
            throw new ExcelException(e);
        } catch (IllegalAccessException e) {
            throw new ExcelException(e);
        }
    }

    /**
     * 从Excel文件中读取一行单元格
     *
     * @param rowNum  行号，从0开始
     * @param rowList 行单元格的内容
     * @return
     */
    protected T readLine(int rowNum, ListRow rowList) {
        if (rowNum == 0) {//标题行，不处理
            return null;
        }

        T t = newInstance();

        List<Field> fields = excelCellUtils.getOrderedFields(t);
        String[] arr = rowList.toFullArray(fields.size());

        boolean empty = true;
        for (String s : arr) {
            if (!Strings.isNullOrEmpty(s)) {
                empty = false;
                break;
            }
        }
        if (empty) {
            return null;
        }

        int i = 0;
        for (Field field : fields) {
            if (arr[i] != null) {
                try {
                    field.setAccessible(true);
                    if (field.getType() == Double.class || field.getType() == double.class)
                        field.set(t, Double.valueOf(arr[i]));
                    else if (field.getType() == Boolean.class || field.getType() == boolean.class)
                        field.set(t, Boolean.valueOf(arr[i]));
                    else if (field.getType() == Integer.class || field.getType() == int.class)
                        field.set(t, Integer.valueOf(arr[i]));
                    else if (field.getType() == Long.class || field.getType() == long.class)
                        field.set(t, Long.valueOf(arr[i]));
                    else if (field.getType().isEnum() || field.getType() == Enum.class)
                        field.set(t, Enum.valueOf((Class<Enum>) field.getType(), arr[i]));
                    else if (field.getType() == String.class)
                        field.set(t, String.valueOf(arr[i]));
                    else if (field.getType() == Date.class)
                        field.set(t, sdf.parse(arr[i]));
                } catch (Exception e) {
                    throw new ExcelException(e);
                }
            }
            i++;
        }
        return t;
    }

    @Override
    public void optRow(int curRow, ListRow rowList) {
        T t = readLine(curRow, rowList);
        if (curRow == 0) {//标题行，不处理
            return;
        }
        if (t != null) {
            data.add(t);
        }
    }

    @Override
    public void startSheet(int sheetIndex, String name) {
        if (this.data == null) {
            this.data = Lists.newArrayList();
        }
    }

    @Override
    public void endSheet(int sheetIndex, String name) {

    }

    @Override
    public void endWorkBook() {

    }
}
