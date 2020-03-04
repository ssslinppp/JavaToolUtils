package com.ssslinppp.write;

import java.io.FileNotFoundException;
import java.util.List;

/**
 */
public interface WriteHandler<T> {

    /**
     * 需要手动关闭，即调用flush
     *
     * @param sheetName
     * @param list
     * @throws FileNotFoundException
     */
    void write(String sheetName, List<T> list) throws FileNotFoundException;

    /**
     * 写入Excel文件，并关闭相关数据流等
     */
    void flush();

}
