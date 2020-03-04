package com.ssslinppp.write;

import com.ssslinppp.exception.ExcelException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 */
public abstract class AbstractWriteHandler<T> implements WriteHandler<T> {
    private transient OutputStream outputStream;
    private Workbook wb;
    private Class<T> clazz;

    public AbstractWriteHandler(OutputStream outputStream, Workbook wb, Class<T> clazz) {
        this.outputStream = outputStream;
        this.wb = wb;
        this.clazz = clazz;
    }

    @Override
    public void write(String sheetName, List<T> list) {
        Sheet sheet = createSheet(this.wb, sheetName);

        int titleLineNum = 0;
        try {
            titleLineNum = writeHeader(this.wb, sheet, this.clazz.newInstance(), 0);
        } catch (InstantiationException e) {
            throw new ExcelException("writeHeader->clazz.newInstance()[InstantiationException]", e);
        } catch (IllegalAccessException e) {
            throw new ExcelException("writeHeader->clazz.newInstance()[IllegalAccessException]", e);
        }

        writeLine(this.wb, sheet, ++titleLineNum, list);
    }

    /**
     * 写入文件并close
     */
    @Override
    public void flush() {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.outputStream);
            wb.write(bufferedOutputStream);
            wb.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new ExcelException("write excel exception, file can not found", e);
        } catch (IOException e) {
            throw new ExcelException("write excel exception, IOException", e);
        }
    }

    /**
     * 将对象写入到Sheet中
     *
     * @param wb
     * @param sheet
     * @param startLineNum 从第startLineNum行开始写入
     * @param list
     */
    protected abstract void writeLine(Workbook wb, Sheet sheet, int startLineNum, List<T> list);

    /**
     * 向Sheet中写入标题，写入到第titleLineNum行
     *
     * @param wb
     * @param sheet
     * @param t
     * @param titleLineNum
     * @return
     */
    protected abstract int writeHeader(Workbook wb, Sheet sheet, T t, int titleLineNum);

    /**
     * 创建Sheet
     *
     * @param workbook
     * @param sheetName
     * @return 返回创建的SheetName
     */
    protected abstract Sheet createSheet(Workbook workbook, String sheetName);

}
