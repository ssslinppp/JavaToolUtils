package com.ssslinppp.write;

import com.google.common.reflect.TypeToken;
import com.ssslinppp.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.regex.Pattern;

/**
 * Description：参考：{@code org.apache.poi.ss.usermodel.WorkbookFactory}<br/>
 * 使用示例：
 * <pre>
 *     {@code
 *   List<Person> personList = Lists.newArrayList();
 *   for (int i = 0; i < 100; i++) {
 *       personList.add(Person.getDemoPerson());
 *   }
 *
 *   // 后面需要添加{}，表示是AbstractWriteHandleFactory的子类，在编译时会将Person编译进去，否则会产生泛型类型擦除
 *   AbstractWriteHandleFactory<Person> abstractWriteHandleFactory = new AbstractWriteHandleFactory<Person>() {};
 *   String filePath = "D:\\tempTempTemp\\MyExcel10.xlsx";
 *   WriteHandler writeHandle = abstractWriteHandleFactory.createWriteHandle(filePath);
 *   writeHandle.write("sheetName", personList);
 *   writeHandle.write("sheetName", new ArrayList<Person>());
 *   writeHandle.flush();
 *   System.out.println("######## Done ########");
 *   }
 * </pre>
 * <p>
 * User: liulin <br/>
 * Date: 2017/11/15 <br/>
 * Time: 12:44 <br/>
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractWriteHandleFactory<T> {
    private static final Pattern OLD_EXCEL_PATH = Pattern.compile("^\\S*\\.xls$");
    private static final Pattern EXCEL_PATH = Pattern.compile("^\\S*\\.xlsx$");

    /**
     * 2003版以及之前的Excel（*.xls）
     *
     * @param path
     */
    private static boolean isOldPath(String path) {
        if (!OLD_EXCEL_PATH.matcher(path).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 2007版以及之后的Excel(*.xlsx)
     *
     * @param path
     */
    private static boolean isNewPath(String path) {
        if (!EXCEL_PATH.matcher(path).matches()) {
            return false;
        }
        return true;
    }

    public WriteHandler<T> createWriteHandle(String filePath) throws FileNotFoundException {
        return createWriteHandle(new File(filePath));
    }

    public WriteHandler<T> createWriteHandle(File file) throws FileNotFoundException {
        Workbook workbook = null;
        if (isOldPath(file.getAbsolutePath())) {
            workbook = new HSSFWorkbook();  //2003版及以前
        } else if (isNewPath(file.getAbsolutePath())) {
            // 内存中每100条就写入一次，显著提升写入速度
            workbook = new SXSSFWorkbook(100); //2007版及以后
        } else {
            throw new ExcelException("create WriteHandler failed, not support file:" + file.getAbsolutePath());
        }

        return new DefaultWriteHandler<T>(new FileOutputStream(file), workbook, getParameterType());
    }

    /**
     * 使用泛型时，会出现类型擦除，通过TypeToken来获取泛型类型
     *
     * @return
     */
    private Class<T> getParameterType() {
        final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
        };
        final Class<T> type = (Class<T>) typeToken.getRawType();
        return type;
    }
}
