package com.ssslinppp.readerdemo;

import com.google.common.base.Stopwatch;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/hssf/usermodel/examples/HSSFReadWrite.java
 */
public class ExcelReaderDemo {
    @Test
    public void testRead() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String filePath = "D:\\tempTempTemp\\MyExcel1000-1.xls";
        read(filePath);
        System.out.println("Done ,耗时:" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " 毫秒");
    }

    public void read(String filePath) throws Exception {
        read(new File(filePath));
    }

    public void read(File file) throws Exception {
        Workbook wb = WorkbookFactory.create(file);

        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            System.out.println("===============  Sheet( " + k + " )==================");
            Sheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();
            System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows + " row(s).");
            for (int r = 0; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                System.out.println("\nROW " + row.getRowNum() + " has " + row.getPhysicalNumberOfCells() + " cell(s).");
                for (int c = 0; c < row.getLastCellNum(); c++) {
                    Cell cell = row.getCell(c);
                    String value;

                    if (cell != null) {
                        switch (cell.getCellTypeEnum()) {

                            case FORMULA:
                                value = "FORMULA value=" + cell.getCellFormula();
                                break;

                            case NUMERIC:
                                value = "NUMERIC value=" + cell.getNumericCellValue();
                                break;

                            case STRING:
                                value = "STRING value=" + cell.getStringCellValue();
                                break;

                            case BLANK:
                                value = "<BLANK>";
                                break;

                            case BOOLEAN:
                                value = "BOOLEAN value-" + cell.getBooleanCellValue();
                                break;

                            case ERROR:
                                value = "ERROR value=" + cell.getErrorCellValue();
                                break;
                            default:
                                value = "UNKNOWN value of type " + cell.getCellTypeEnum();
                        }
                        System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE=" + value);
                    }
                }
            }
        }
    }
}

