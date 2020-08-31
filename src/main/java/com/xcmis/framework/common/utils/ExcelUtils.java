package com.xcmis.framework.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * POI封装工具类
 * @Author fangzhe
 * @Date 2020/7/19 3:09 下午
 * @Version 1.0
 */
public class ExcelUtils {

    /**
     * 创建 sheet
     * @param sheetNames 需要创建的表单集合
     * @param workbook
     * @param unionNumber 标题占用单元格数量集合
     * @param sheetTitles 需要插入的标题集合
     * @param indexTitles 第三行索引字段集合
     * @return
     */
    public static HSSFWorkbook createExcelSheet(String[] sheetNames,String[] sheetTitles,HSSFWorkbook workbook,
                                                int[] unionNumber,String[] indexTitles){

        for (int i = 0 ; i < sheetNames.length ; i++){
            HSSFSheet sheet = workbook.createSheet(sheetNames[i]);
            sheet.setDefaultRowHeightInPoints(20);// 设置缺省列高
            sheet.setDefaultColumnWidth(20);//设置缺省列宽x 索引行设置完毕后不生效
            //这是默认的第一行标题 和 第二行空白隔开
            sheet.setColumnWidth(0,31*256);//设置第一行宽度 （标题）
            sheet.setColumnWidth(1,15*256);//空白样式
            //获取第一行
            HSSFRow row = sheet.createRow(0);//创建行
            HSSFCell cell = row.createCell(0);//创建第一行第一列的标题单元格（列）
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,unionNumber[i]));//合并标题的单元格
            row.setHeightInPoints(40);//第一行宽度
            row.getCell(0).setCellValue(sheetTitles[i]);//向第一行中插入标题
            cell.setCellStyle(getTitleStyle(workbook));//将标题样式应用
            //第二行为空行
            //获取第三行
            HSSFRow row1 = sheet.createRow(2);
            row1.setHeightInPoints(28);//第三行宽度

            //将索引列写入
            for (int j = 0 ; j < indexTitles.length ; j++){
                row1.createCell(j).setCellValue(indexTitles[j]);
                HSSFCell cell1 = row1.getCell(j);
                cell1.setCellStyle(getFormStyle1(workbook));
            }
        }
        return workbook;
    }

    /**
     * 标题样式1
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook){
        //-------------------------标题样式begin--------------------------------------
        HSSFCellStyle titleStyle = workbook.createCellStyle();//设置标题样式
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        HSSFFont font = workbook.createFont();//创建字体样式
        font.setFontName("宋体");//设置字体为"宋体"
        font.setFontHeightInPoints((short)17);//设置字体大小为17px
        font.setBold(true);//设置字体加粗
        titleStyle.setFont(font);//将字体应用到当前样式
        //-------------------------标题样式end----------------------------------------
        return titleStyle;
    }

    /**
     * 表格样式1(第一行索引)
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getFormStyle1(HSSFWorkbook workbook){
        //-------------------------表格样式1--------------------------------------
        HSSFCellStyle formStyle1 = workbook.createCellStyle();//设置字体样式
        formStyle1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        formStyle1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        formStyle1.setBorderBottom(BorderStyle.THIN);//设置边框
        formStyle1.setBorderLeft(BorderStyle.THIN);//设置边框
        formStyle1.setBorderRight(BorderStyle.THIN);//设置边框
        formStyle1.setBorderTop(BorderStyle.THIN);//设置边框
        formStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        formStyle1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());//设置背景色
        HSSFFont font = workbook.createFont();//创建字体样式
        font.setFontName("宋体");//设置字体为"宋体"
        font.setFontHeightInPoints((short)9);//设置字体大小为9px
        formStyle1.setFont(font);
        //-------------------------表格样式1----------------------------------------
        return formStyle1;
    }

    /**
     * 表格样式2
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getFormStyle2(HSSFWorkbook workbook){

        //-------------------------表格样式2--------------------------------------
        HSSFCellStyle formStyle2 = workbook.createCellStyle();//设置字体样式
        formStyle2.setAlignment(HorizontalAlignment.CENTER);//水平居中
        formStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        formStyle2.setBorderBottom(BorderStyle.THIN);//设置边框
        formStyle2.setBorderLeft(BorderStyle.THIN);//设置边框
        formStyle2.setBorderRight(BorderStyle.THIN);//设置边框
        formStyle2.setBorderTop(BorderStyle.THIN);//设置边框
        formStyle2.setWrapText(true);
        HSSFFont font2 = workbook.createFont();//创建字体样式
        font2.setFontName("宋体");//设置字体为"宋体"
        font2.setFontHeightInPoints((short)9);//设置字体大小为9px
        formStyle2.setFont(font2);
        //-------------------------表格样式2--------------------------------------
        return formStyle2;
    }



}
