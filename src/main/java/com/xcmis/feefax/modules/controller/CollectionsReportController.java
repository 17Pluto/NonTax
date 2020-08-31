package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.entity.Ienusermanag;
import com.xcmis.feefax.modules.entity.IncomeEnterprise;
import com.xcmis.feefax.modules.entity.UnitItem;
import com.xcmis.feefax.modules.service.CollectionsReportService;
import com.xcmis.feefax.modules.service.IenusermanagService;
import com.xcmis.feefax.modules.service.IncomeEnterpriseService;
import com.xcmis.feefax.modules.service.UnitItemService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.entity.UserOrg;
import com.xcmis.framework.modules.service.UserOrgService;
import com.xcmis.framework.modules.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fangzhe
 * @Date 2020/6/7 4:51 下午
 * @Version 1.0
 */

@RestController
@RequestMapping(value = "/feefax")
public class CollectionsReportController {
    @Autowired
    private CollectionsReportService collectionsReportService;

    @Autowired
    private IenusermanagService ienusermanagService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Autowired
    private UnitItemService unitItemService;

    @GetMapping("/getCollectionsReportListByCondition")
    public Result<Map<String,Object>> getCollectionsReportListByCondition(Collections collections){
        String belongType = "";
        List<IncomeEnterprise> list = new ArrayList<>();

        String userId = UserUtils.getUserId();

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);
        belongType = user.getBelongType();


        if(collections.getEnId().equals("")) {
            if(user.getBelongType().equals("007")){

            }else {
                Ienusermanag ienusermanag = new Ienusermanag();
                ienusermanag.setUserId(user.getUserId());
                List<Ienusermanag> IenusermanagList = ienusermanagService.findAllList(ienusermanag);


                for (Ienusermanag i : IenusermanagList) {
                    UserOrg userOrg = new UserOrg();
                    userOrg.setUserId(user.getUserId());
                    userOrg.setOrgId(i.getIenId());
                    List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);

                    if (userOrgList != null) {
                        if (userOrgList.size() > 0) {
                            IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                            incomeEnterprise.setChrId(i.getIenId());

                            List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                            for (IncomeEnterprise ie : incomeEnterpriseList) {
                                list.add(ie);
                            }
                        }
                    }
                }


                UnitItem unitItem = new UnitItem();
                unitItem.setUnitId(user.getBelongOrg());
                List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);


                for (UnitItem ui : unitItemList) {
                    IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                    incomeEnterprise.setChrId(ui.getIenId());
                    List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                    for (IncomeEnterprise ie : incomeEnterpriseList) {
                        boolean b = true;
                        if (list.size() == 0) {
                            list.add(ie);
                        } else {
                            for (IncomeEnterprise i : list) {
                                if (i.getChrId().equals(ie.getChrId())) {
                                    b = false;
                                    break;
                                }
                            }
                            if (b) {
                                list.add(ie);
                            }
                        }
                    }
                }

                java.util.Collections.sort(list);
            }
        }else{
            String[] enIds = collections.getEnId().split("#");
            for (String enId : enIds) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(enId);

                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    list.add(ie);
                }
            }
        }

        Map<String, Object> mapIn = new HashMap<>();
        Map<String,Object> mapOut = new HashMap<>();

        mapIn.put("collections", collections);
        mapIn.put("incomeEnterpriseList", list);
        mapIn.put("belongType", belongType);
        List<Collections> collectionsList = collectionsReportService.getCollectionsReportListByCondition(mapIn);
        mapOut.put("rows", collectionsList);//要以JSON格式返回
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 报表打印
     * @param response
     * @throws Exception
     */
    @GetMapping("/collectionsExport")
    public void export(HttpServletResponse response, Collections collections) throws Exception{
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet("sheet1");
        sheet.setDefaultRowHeightInPoints(10);// 设置缺省列高
        sheet.setDefaultColumnWidth(20);//设置缺省列宽x
        sheet.setColumnWidth(0,31*256);//设置第一列宽度
        sheet.setColumnWidth(1,15*256);
        sheet.setColumnWidth(3,6*256);//设置第四列宽度
        sheet.setColumnWidth(4,6*256);//设置第五列宽度
        sheet.setColumnWidth(5,6*256);//设置第六列宽度
        sheet.setColumnWidth(6,18*256);//设置第七列宽度
        sheet.setColumnWidth(7,18*256);
        sheet.setColumnWidth(8,18*256);
        sheet.setColumnWidth(9,14*256);
        sheet.setColumnWidth(10,32*256);
        sheet.setColumnWidth(11,30*256);
        sheet.setColumnWidth(14,55*256);//14 收入项目
        sheet.setColumnWidth(18,6*256);
        sheet.setColumnWidth(19,6*256);
        sheet.setColumnWidth(20,6*256);
        String fileName = "导出测试01.xls";// 设置要导出的文件的名字
        // 获取数据集合


        List<IncomeEnterprise> ieList = new ArrayList<>();
        if(collections.getEnId().equals("")) {
            String userId = UserUtils.getUserId();

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);


            Ienusermanag ienusermanag = new Ienusermanag();
            ienusermanag.setUserId(user.getUserId());
            List<Ienusermanag> IenusermanagList = ienusermanagService.findAllList(ienusermanag);


            for (Ienusermanag i : IenusermanagList) {
                UserOrg userOrg = new UserOrg();
                userOrg.setUserId(user.getUserId());
                userOrg.setOrgId(i.getIenId());
                List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);

                if (userOrgList != null) {
                    if (userOrgList.size() > 0) {
                        IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                        incomeEnterprise.setChrId(i.getIenId());

                        List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                        for (IncomeEnterprise ie : incomeEnterpriseList) {
                            ieList.add(ie);
                        }
                    }
                }
            }


            UnitItem unitItem = new UnitItem();
            unitItem.setUnitId(user.getBelongOrg());
            List<UnitItem> unitItemList = unitItemService.getUnitItemByUnitId(unitItem);


            for (UnitItem ui : unitItemList) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(ui.getIenId());
                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    boolean b = true;
                    if (ieList.size() == 0) {
                        ieList.add(ie);
                    } else {
                        for (IncomeEnterprise i : ieList) {
                            if (i.getChrId().equals(ie.getChrId())) {
                                b = false;
                                break;
                            }
                        }
                        if (b) {
                            ieList.add(ie);
                        }
                    }
                }
            }

            java.util.Collections.sort(ieList);
        }else{
            String[] enIds = collections.getEnId().split("#");
            for (String enId : enIds) {
                IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
                incomeEnterprise.setChrId(enId);

                List<IncomeEnterprise> incomeEnterpriseList = incomeEnterpriseService.getIncomeEnterpriseByChrId(incomeEnterprise);
                for (IncomeEnterprise ie : incomeEnterpriseList) {
                    ieList.add(ie);
                }
            }
        }

        Map<String, Object> mapIn = new HashMap<>();
        mapIn.put("collections", collections);
        mapIn.put("incomeEnterpriseList", ieList);
        List<Collections> list = collectionsReportService.getCollectionsReportListByCondition(mapIn);
        Collections entity=null;

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

        //-------------------------表格样式1--------------------------------------
        HSSFCellStyle style1 = workbook.createCellStyle();//设置字体样式
        style1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style1.setBorderBottom(BorderStyle.THIN);//设置边框
        style1.setBorderLeft(BorderStyle.THIN);//设置边框
        style1.setBorderRight(BorderStyle.THIN);//设置边框
        style1.setBorderTop(BorderStyle.THIN);//设置边框
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());//设置背景色
        HSSFFont font1 = workbook.createFont();//创建字体样式
        font1.setFontName("宋体");//设置字体为"宋体"
        font1.setFontHeightInPoints((short)9);//设置字体大小为9px
        style1.setFont(font1);
        //-------------------------表格样式1----------------------------------------

        //-------------------------表格样式2--------------------------------------
        HSSFCellStyle style2 = workbook.createCellStyle();//设置字体样式
        style2.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style2.setBorderBottom(BorderStyle.THIN);//设置边框
        style2.setBorderLeft(BorderStyle.THIN);//设置边框
        style2.setBorderRight(BorderStyle.THIN);//设置边框
        style2.setBorderTop(BorderStyle.THIN);//设置边框
        style2.setWrapText(true);
        HSSFFont font2 = workbook.createFont();//创建字体样式
        font2.setFontName("宋体");//设置字体为"宋体"
        font2.setFontHeightInPoints((short)9);//设置字体大小为9px
        style2.setFont(font1);
        //-------------------------表格样式2--------------------------------------

        //获取第一行
        HSSFRow row = sheet.createRow(0);//创建行
        HSSFCell cell = row.createCell(0);//创建单元格（列）
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));//合并单元格 24
        row.setHeightInPoints(40);//第一行宽度
        row.getCell(0).setCellValue("缴款书明细查询表（财政用）");//向第一行中插入标题
        cell.setCellStyle(titleStyle);//设置单元格样式
        //第二行为空行
        //获取第三行
        HSSFRow row1 = sheet.createRow(2);
        row1.setHeightInPoints(28);//第三行宽度
        row1.createCell(0).setCellValue("票据样式");
        row1.createCell(1).setCellValue("票据单号");
        row1.createCell(2).setCellValue("金额");
        row1.createCell(3).setCellValue("缴款");
        row1.createCell(4).setCellValue("对账");
        row1.createCell(5).setCellValue("核收");
        row1.createCell(6).setCellValue("单位开票日期");
        row1.createCell(7).setCellValue("银行收款日期");
        row1.createCell(8).setCellValue("财政核收日期");
        row1.createCell(9).setCellValue("单位编码");
        row1.createCell(10).setCellValue("单位名称");
        row1.createCell(11).setCellValue("缴款人");
        row1.createCell(12).setCellValue("缴款人开户行");
        row1.createCell(13).setCellValue("收款银行");
        row1.createCell(14).setCellValue("收入项目");
        row1.createCell(15).setCellValue("摘要");
        row1.createCell(16).setCellValue("经办人");
        row1.createCell(17).setCellValue("业务类型");
        row1.createCell(18).setCellValue("打印");
        row1.createCell(19).setCellValue("作废");
        row1.createCell(20).setCellValue("委托");
        for(int i = 0 ; i<=20 ; i++){
            HSSFCell cell1 = row1.getCell(i);
            cell1.setCellStyle(style1);
        }
        //在表中存放查询到的数据放入对应的列
        //按照每行内容进行循环
        Double totalmoney = 0.00;
        for (int i = 0; i < list.size(); i++) {
            entity = list.get(i);
            totalmoney = entity.getChargemoney()+totalmoney;
            row1 = sheet.createRow(i+3);
            row1.setHeightInPoints(30);
            row1.createCell(0).setCellValue(entity.getBillName());//票据样式
            row1.createCell(1).setCellValue(entity.getBillNo());//票据单号
            row1.createCell(2).setCellValue(entity.getChargemoney());//金额
            row1.createCell(3).setCellValue(entity.getPayflag()==1?"√":"×");//缴款
            row1.createCell(4).setCellValue(entity.getCheckflag()==1?"√":"×");//对账
            row1.createCell(5).setCellValue(entity.getCheckearne()==1?"√":"×");//核收
            row1.createCell(6).setCellValue(entity.getMakedate());//单位开票日期
            row1.createCell(7).setCellValue(entity.getBankIndate());//银行收款日期
            row1.createCell(8).setCellValue(entity.getCheckearnedate());//财政核收日期
            row1.createCell(9).setCellValue(entity.getEnCode());//单位编码
            row1.createCell(10).setCellValue(entity.getEnName());//单位名称
            row1.createCell(11).setCellValue(entity.getPayerName());//缴款人
            row1.createCell(12).setCellValue(entity.getPayerbank());//缴款人开户行
            row1.createCell(13).setCellValue(entity.getReceiverbank());//收款银行
            row1.createCell(14).setCellValue(entity.getIncitemNames());//收入项目
            row1.createCell(15).setCellValue(entity.getRemark());//摘要
            row1.createCell(16).setCellValue(entity.getInputername());//经办人
            row1.createCell(17).setCellValue(entity.getReceivetype()==1?"直接解缴":"集中汇缴");//业务类型
            row1.createCell(18).setCellValue(entity.getPrintflag()==1?"√":"×");//打印
            row1.createCell(19).setCellValue(entity.getEraseflag()==1?"√":"×");//作废
            row1.createCell(20).setCellValue(entity.getIsconsign()==1?"√":"×");
            for(int j = 0; j<=20 ;j++){
                HSSFCell cell2 = row1.getCell(j);
                cell2.setCellStyle(style2);
            }
        }

        //底部合计
        row1 = sheet.createRow(list.size()+3);
        for(int i = 0 ; i<=20 ; i++){
            row1.createCell(i);
            HSSFCell cell3 = row1.getCell(i);
            cell3.setCellStyle(style1);
        }
        row1.getCell(0).setCellValue("合计");

        row1.getCell(2).setCellValue(totalmoney);
        response.setContentType("application/octet-stream");
        response.setHeader("Cache-Control","attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.flushBuffer();

        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

}
