package com.gsp.springcloud.utils;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * @Author Don
 * @Description  导出Excel实现
 * @Date 2020/7/16 16:27
 **/
public class ExcelExportMyStylerImpl extends AbstractExcelExportStyler implements IExcelExportStyler {
    public ExcelExportMyStylerImpl(Workbook workbook) {
        super.createStyles(workbook);
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        // 加粗
        font.setBold(true);
        titleStyle.setFont(font);
        // 居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置颜色
        titleStyle.setFillForegroundColor(IndexedColors.AQUA.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    @SuppressWarnings("deprecation")
    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }

    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        // 加粗
        font.setBold(true);
        font.setColor(IndexedColors.RED.index);
        font.setFontHeightInPoints((short) 11);
        titleStyle.setFont(font);
        // 居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置颜色
        titleStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        // 垂直居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    @SuppressWarnings("deprecation")
    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }
}
