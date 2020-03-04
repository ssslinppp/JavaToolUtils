package com.ssslinppp.write;

import com.google.common.base.Strings;
import com.ssslinppp.annotation.ExcelCellUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 */
public class DefaultWriteHandler<T> extends AbstractWriteHandler<T> {

    private ExcelCellUtils excelCellUtils = new ExcelCellUtils();

    public DefaultWriteHandler(OutputStream outputStream, Workbook wb, Class<T> clazz) {
        super(outputStream, wb, clazz);
    }

    @Override
    protected void writeLine(Workbook wb, Sheet sheet, int startLineNum, List<T> list) {
        CellStyle cellDateStyle = wb.createCellStyle();
        cellDateStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        if (!CollectionUtils.isEmpty(list)) {
            for (T t : list) {
                Row currentRow = sheet.createRow(startLineNum++);
                List<Object> values = excelCellUtils.getOrderedValues(t);

                int colNum = 0;
                for (Object value : values) {
//                    sheet.autoSizeColumn(colNum);TODO
                    Cell cell = currentRow.createCell(colNum++);
                    if (value instanceof Date) {
                        cell.setCellValue((Date) value);
                        cell.setCellStyle(cellDateStyle);
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue((Double) value);
                    } else if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Short) {
                        cell.setCellValue((Short) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else if (value instanceof Enum) {
                        cell.setCellValue(((Enum) value).name());
                    }
                }
            }
        }
    }

    @Override
    protected int writeHeader(Workbook wb, Sheet sheet, T t, int titleLineNum) {
        Row row = sheet.createRow(titleLineNum);
        row.setHeight((short) 0x180);

        int colNum = 0;
        CellStyle cellStyle = createHeadCellStyle(wb);
        List<String> cellTitles = excelCellUtils.getOrderedCellTitle(t);

        for (String title : cellTitles) {
//            sheet.autoSizeColumn(colNum);TODO
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }

        return titleLineNum;
    }

    @Override
    protected Sheet createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = null;

        if (Strings.isNullOrEmpty(sheetName)) {
            sheet = workbook.createSheet();
        } else {
            if (workbook.getSheet(sheetName) == null) {
                sheet = workbook.createSheet(sheetName);
            } else {
                int num = 1;
                while (workbook.getSheet(sheetName + "-" + num) != null) {
                    num++;
                }
                sheet = workbook.createSheet(sheetName + "-" + num);
            }
        }
        return sheet;
    }

    /**
     * 创建标题Style
     *
     * @param wb
     * @return
     */
    private CellStyle createHeadCellStyle(Workbook wb) {
        // 生成一个字体
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);

        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);

        return style;
    }
}
