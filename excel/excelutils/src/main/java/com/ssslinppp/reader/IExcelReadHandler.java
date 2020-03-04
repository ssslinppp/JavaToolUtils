package com.ssslinppp.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * https://github.com/bingyulei007/bingexcel
 */
public interface IExcelReadHandler<T> {
    /**
     * 按SheetName读取指定Sheet
     * @param f
     * @param sheetName
     * @return
     */
    List<T> read(File f, String sheetName);

    /**
     * 按SheetName读取指定Sheet
     * @param filePath
     * @param sheetName
     * @return
     */
    List<T> read(String filePath, String sheetName);

    /**
     * 按SheetName读取指定Sheet
     * @param is
     * @param sheetName
     * @return
     */
    List<T> read(InputStream is, String sheetName);

    /**
     * 按SheetIndex读取指定Sheet
     * @param f
     * @param sheetIndex 从0开始
     * @return
     */
    List<T> read(File f, int sheetIndex);

    /**
     * 按SheetIndex读取指定Sheet
     * @param filePath
     * @param sheetIndex
     * @return
     */
    List<T> read(String filePath, int sheetIndex);

    /**
     * 按SheetIndex读取指定Sheet
     * @param is
     * @param sheetIndex
     * @return
     */
    List<T> read(InputStream is, int sheetIndex);

    /**
     * 读取所有Sheet的内容
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    List<T> readAllSheet(String filePath) throws FileNotFoundException;

    /**
     * 读取所有Sheet的内容
     * @param is
     * @return
     */
    List<T> readAllSheet(InputStream is);

    /**
     * 读取所有Sheet的内容
     * @param f
     * @return
     * @throws FileNotFoundException
     */
    List<T> readAllSheet(File f) throws FileNotFoundException;
}
