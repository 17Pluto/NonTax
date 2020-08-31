package com.xcmis.feefax.modules.controller;


import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.entity.Collections;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author fangzhe
 * @Date 2020/6/8 5:03 下午
 * @Version 1.0
 */



@Controller
@RequestMapping(value = "/feefax")
public class ImportExcelController {
    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private IncomeItemService incomeItemService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CollectionsGatherService collectionsGatherService;

    @Autowired
    private UnitItemService unitItemService;


    private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);

    @Value("${web.file.modelpath}")
    private String modelPath;

    //注入文件上传地址
    @Value("${web.file.path}")
    private String filePath;

    /**
     * 获取并解析excel文件，返回一个二维集合
     * @param file 上传的文件
     */

    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    @ResponseBody
    public Result<Map<String,Object>> readExcel(MultipartFile file, ImportBillDetail importBillDetail){
        Map<String,Object> mapOut = new HashMap<>();

        List<Collections> result = new ArrayList<>();
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //控制台输出文件名
        System.out.println("获取的文件名为：" + fileName);
        //上传路径
        File dest = new File(filePath + fileName);

        UnitItem unitItem = new UnitItem();
        unitItem.setIenId(importBillDetail.getEnId());
        unitItem.setAccountId(importBillDetail.getReceiverid());
        unitItem.setBilltypeId(importBillDetail.getBillNameId());
        unitItem.setCompareDate(DateTimeUtils.getDateTimeStr2());
        List<UnitItem> unitItemList = unitItemService.getUnitItemByEnIdAndBilltypeIdAndAccountId(unitItem);
        try {
            //获取输入流
            InputStream inputStream = file.getInputStream();
            //将文件保存至服务器
            //file.transferTo(dest);
            //创建excel工作簿
            Workbook workbook = null;
            //判断excel版本
            if (judegExcelEdition(fileName)) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }
            //获取第一张工作表
            Sheet sheet = workbook.getSheetAt(0);
            //获取文件总行数
            int totalRows = sheet.getLastRowNum();
            int pyhnum = sheet.getPhysicalNumberOfRows();
            System.out.println("获取excel的总行数为：" + totalRows);
            System.out.println("获取的物理行数为：" + pyhnum);

            //获取数据
            Row row = null;
            CellType cellType = null;
            Boolean b = true;

            outer:for (int i=1; i<sheet.getPhysicalNumberOfRows();i++) {
                row = sheet.getRow(i);
                Cell cell = null;
                Collections collections = new Collections();

                CollectionsGatherDetail collectionsGatherDetail = new CollectionsGatherDetail();
                List<CollectionsGatherDetail> incitem = new ArrayList();
                String cellValue = "";


              inner:for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j){
                        case 0: //票据号

                            cell = row.getCell(0);
                            collections.setBillNo(cell.getStringCellValue());
                            break ;
                        case 1: //开票日期
                            cell = row.getCell(1);
                            try {
                                cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");//格式化日期格式
                                collections.setMakedate(cellValue);
                            }catch(Exception ex){
                                collections.setErrorMsg("日期格式错误");
                            }

                            break ;
                        case 2: //缴款人
                            cell = row.getCell(2);
                            collections.setPayer(cell.getStringCellValue());
                            break ;
                        case 3: //缴款人开户行
                            cell = row.getCell(3);
                            if(cell != null) {
                                collections.setPayerbank(cell.getStringCellValue());
                            }
                            break ;
                        case 4: //缴款人账户
                            cell = row.getCell(4);
                            if(cell != null) {
                                collections.setPayeraccount(cell.getStringCellValue());
                            }
                            break ;
                        case 5: //备 注
                            cell = row.getCell(5);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collections.setRemark(cell.getStringCellValue());
                                }
                            }
                            break;
                        case 6: //收费项目1
                            collectionsGatherDetail = new CollectionsGatherDetail();
                            cell = row.getCell(6);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collectionsGatherDetail.setIncitemid(cell.getStringCellValue());
                                    IncomeItem incomeItem = new IncomeItem();
                                    incomeItem.setChrCode(cell.getStringCellValue());
                                    incomeItem = incomeItemService.get(incomeItem);
                                    if (incomeItem == null) {
                                        collections.setErrorMsg("收费项目1错误");
                                        collections.setCollectionsGatherDetailList(incitem);
                                    } else {
                                        String unitItemId = "";
                                        for (UnitItem ui : unitItemList) {
                                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                                unitItemId = ui.getChrId();
                                                break;
                                            }
                                        }
                                        if (unitItemId.equals("")) {
                                            collections.setErrorMsg("收费项目1错误");
                                            collections.setCollectionsGatherDetailList(incitem);
                                        } else {
                                            collectionsGatherDetail.setUnititemId(unitItemId);
                                        }
                                    }
                                }
                            }
                            break;
                        case 7: //收费金额1
                            cell = row.getCell(7);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collectionsGatherDetail.setChargemoney(cell.getNumericCellValue());
                                    incitem.add(collectionsGatherDetail);
                                    collections.setCollectionsGatherDetailList(incitem);

                                }
                            }
                            break ;
                        case 8: //收费项目2
                            collectionsGatherDetail = new CollectionsGatherDetail();
                            cell = row.getCell(8);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) { //判断单元格是否为空

                                    collectionsGatherDetail.setIncitemid(cell.getStringCellValue());
                                    IncomeItem incomeItem2 = new IncomeItem();
                                    incomeItem2.setChrCode(cell.getStringCellValue());
                                    incomeItem2 = incomeItemService.get(incomeItem2);
                                    if (incomeItem2 == null) {
                                        collections.setErrorMsg("收费项目2错误");
                                        collections.setCollectionsGatherDetailList(incitem);
                                    } else {
                                        String unitItemId = "";
                                        for (UnitItem ui : unitItemList) {
                                            if (incomeItem2.getChrId().equals(ui.getItemId())) {
                                                unitItemId = ui.getChrId();
                                                break;
                                            }
                                        }
                                        if (unitItemId.equals("")) {
                                            collections.setErrorMsg("收费项目1错误");
                                            collections.setCollectionsGatherDetailList(incitem);
                                        } else {
                                            collectionsGatherDetail.setUnititemId(unitItemId);
                                        }
                                    }


                                }
                            }
                            break;

                        case 9: //收费金额2
                            cell = row.getCell(9);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {

                                    collectionsGatherDetail.setChargemoney(cell.getNumericCellValue());
                                    incitem.add(collectionsGatherDetail);
                                    collections.setCollectionsGatherDetailList(incitem);

                                }
                            }
                            break ;
                        case 10: //收费项目3
                            collectionsGatherDetail = new CollectionsGatherDetail();
                            cell = row.getCell(10);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {

                                    collectionsGatherDetail.setIncitemid(cell.getStringCellValue());
                                    IncomeItem incomeItem3 = new IncomeItem();
                                    incomeItem3.setChrCode(cell.getStringCellValue());
                                    incomeItem3 = incomeItemService.get(incomeItem3);
                                    if (incomeItem3 == null) {
                                        collections.setErrorMsg("收费项目3错误");
                                        collections.setCollectionsGatherDetailList(incitem);
                                    } else {
                                        String unitItemId = "";
                                        for (UnitItem ui : unitItemList) {
                                            if (incomeItem3.getChrId().equals(ui.getItemId())) {
                                                unitItemId = ui.getChrId();
                                                break;
                                            }
                                        }
                                        if (unitItemId.equals("")) {
                                            collections.setErrorMsg("收费项目1错误");
                                            collections.setCollectionsGatherDetailList(incitem);
                                        } else {
                                            collectionsGatherDetail.setUnititemId(unitItemId);
                                        }
                                    }
                                }
                            }
                            break;
                        case 11: //收费金额3
                            cell = row.getCell(11);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {

                                    collectionsGatherDetail.setChargemoney(cell.getNumericCellValue());
                                    incitem.add(collectionsGatherDetail);
                                    collections.setCollectionsGatherDetailList(incitem);

                                }
                            }
                            break ;
                        case 12: //收费项目4
                            collectionsGatherDetail = new CollectionsGatherDetail();
                            cell = row.getCell(12);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {

                                    collectionsGatherDetail.setIncitemid(cell.getStringCellValue());
                                    IncomeItem incomeItem4 = new IncomeItem();
                                    incomeItem4.setChrCode(cell.getStringCellValue());
                                    incomeItem4 = incomeItemService.get(incomeItem4);
                                    if (incomeItem4 == null) {
                                        collections.setErrorMsg("收费项目4错误");
                                        collections.setCollectionsGatherDetailList(incitem);
                                    } else {
                                        String unitItemId = "";
                                        for (UnitItem ui : unitItemList) {
                                            if (incomeItem4.getChrId().equals(ui.getItemId())) {
                                                unitItemId = ui.getChrId();
                                                break;
                                            }
                                        }
                                        if (unitItemId.equals("")) {
                                            collections.setErrorMsg("收费项目1错误");
                                            collections.setCollectionsGatherDetailList(incitem);
                                        } else {
                                            collectionsGatherDetail.setUnititemId(unitItemId);
                                        }
                                    }
                                }
                            }
                            break;
                        case 13: //收费金额4
                            cell = row.getCell(13);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collectionsGatherDetail.setChargemoney(cell.getNumericCellValue());
                                    incitem.add(collectionsGatherDetail);
                                    collections.setCollectionsGatherDetailList(incitem);
                                }
                            }
                            break;
                        case 14: //收费项目5
                            collectionsGatherDetail = new CollectionsGatherDetail();
                            cell = row.getCell(14);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collectionsGatherDetail.setIncitemid(cell.getStringCellValue());
                                    IncomeItem incomeItem5 = new IncomeItem();
                                    incomeItem5.setChrCode(cell.getStringCellValue());
                                    incomeItem5 = incomeItemService.get(incomeItem5);
                                    if (incomeItem5 == null) {
                                        collections.setErrorMsg("收费项目5错误");
                                        collections.setCollectionsGatherDetailList(incitem);
                                    } else {
                                        String unitItemId = "";
                                        for (UnitItem ui : unitItemList) {
                                            if (incomeItem5.getChrId().equals(ui.getItemId())) {
                                                unitItemId = ui.getChrId();
                                                break;
                                            }
                                        }
                                        if (unitItemId.equals("")) {
                                            collections.setErrorMsg("收费项目1错误");
                                            collections.setCollectionsGatherDetailList(incitem);
                                        } else {
                                            collectionsGatherDetail.setUnititemId(unitItemId);
                                        }
                                    }
                                }
                            }
                            break;
                        case 15: //收费金额5
                            cell = row.getCell(15);
                            if(cell != null) {
                                cellType = cell.getCellTypeEnum();
                                if (cellType != CellType.BLANK) {
                                    collectionsGatherDetail.setChargemoney(cell.getNumericCellValue());
                                    incitem.add(collectionsGatherDetail);
                                    collections.setCollectionsGatherDetailList(incitem);

                                }
                            }
                            break;
                        default:
                            break ;
                    }

                    //cell = row.getCell(j);
                    //null的时候要creat才能get
                    /*
                    if(cell==null){
                        cell = row.createCell(j);
                        System.out.println("存在null异常");
                        break outer;
                    }
                    */
//                    String cellValue = "";
//
//                    //获取当前单元格的类型
//                    CellType cellType = cell.getCellTypeEnum();
//                    //判断不同的类型进行获取
//                    switch (cellType){
//                        case NUMERIC://金额，日期
//                            if(DateUtil.isCellDateFormatted(cell)){ //判断为日期格式的数字时
//                                cellValue = DateFormatUtils.format(cell.getDateCellValue(),"yyyy-MM-dd");//格式化日期格式
//                                list.add(cellValue);
//                            }else{
//                                Object inputValue = null;
//                                Long longVal = Math.round(cell.getNumericCellValue());
//                                Double doubleVal = cell.getNumericCellValue();
//                                if(Double.parseDouble(longVal + ".0") == doubleVal){//判断是否有小数位
//                                    inputValue = longVal;
//                                }else {
//                                    inputValue = doubleVal;
//                                }
//                                DecimalFormat df = new DecimalFormat("#,##0.00");//格式化小数
//                                cell.setCellType(CellType.STRING);
//                                cellValue = String.valueOf(df.format(inputValue));
//                                list.add(cellValue);
//                            }
//                            break;
//                        case STRING://字符串
//                            list.add(cell.getStringCellValue());
//                            break;
//                        case ERROR://错误
//                            cellValue = "非法字符！";
//                        case FORMULA:
//                            cellValue = String.valueOf(cell.getCellFormula());
//                            list.add(cellValue);
//                    }

//                    cell.setCellType(CellType.STRING);
//                    list.add(cell.getStringCellValue());

                }
                //对每一行进行校验
                String billtypeId = importBillDetail.getBillNameId();
                String enId = importBillDetail.getEnId();
                String receiverbank = importBillDetail.getReceiverbank();
                String receiver = importBillDetail.getReceiver();
                String receiveraccount = importBillDetail.getReceiveraccount();
                String pmId = importBillDetail.getPmId();
                String receiverid = importBillDetail.getReceiverid();
                collections.setBilltypeId(billtypeId);
                collections.setEnId(enId);
                collections.setReceiverbank(receiverbank);
                collections.setReceiver(receiver);
                collections.setReceiveraccount(receiveraccount);
                collections.setPmId(pmId);
                collections.setReceiverid(receiverid);
                Subject subject = SecurityUtils.getSubject();
                String token = (String)subject.getPrincipal();
                String userId = JwtTokenUtil.getUserId(token);

                BillDetail billDetail = new BillDetail();
                billDetail.setBilldistributer(userId + "#");
                billDetail.setEnId(enId);
                billDetail.setBillNo(collections.getBillNo());
                billDetail.setUntaxBillnameId(billtypeId);
                billDetail = billDetailService.isvalidBillNo(billDetail);
                if (billDetail == null){
                    collections.setErrorMsg("票据号码错误！");
                }else {
                    if(collections.getErrorMsg() == null) {
                        collections.setErrorMsg("");
                    }else if(collections.getErrorMsg().equals("")){
                        collections.setErrorMsg("");
                    }
                }
                //将每一行的对象插入大集合
                result.add(collections);
                //关闭资源
                workbook.close();

            }

            for (int i = 1 ;i<result.size();i++){
                for(int j = 0 ; j<i;j++){
                    if (result.get(i).getBillNo().equals(result.get(j).getBillNo())){
                        result.get(i).setErrorMsg("票号重复！！");
                    }
                }
            }
            mapOut.put("list",result);
//            System.out.println(result);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("===================未找到文件======================");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("===================上传失败======================");
        }
        return new Result<>(mapOut,Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 判断上传的excel文件版本（xls为2003，xlsx为2017）
     * @param fileName 文件路径
     * @return excel2007及以上版本返回true，excel2007以下版本返回false
     */
    private static boolean judegExcelEdition(String fileName){
        if (fileName.matches("^.+\\.(?i)(xls)$")){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 模版下载
     * @param response
     * @throws IOException
     */
    @GetMapping("/downloadModel")
    @ResponseBody
    public void downloadLoaclModel(HttpServletResponse response) throws IOException {
        //String path = ResourceUtils.getURL("classpath:").getPath() + "static/excelModel/批量开票导入模板.xls";
        String path = modelPath+"批量开票导入模板.xls";
        String fileName = path.substring(path.lastIndexOf("/") +1);
        File file = new File(path);
        if (!file.exists()){
            logger.error("路径有误，文件不存在！");
        }
        response.setHeader("Cache-Control","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/stream");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            /** 将流中内容写出去 .*/
            outputStream.write(buffer ,0 , len);
        }

        inputStream.close();
        outputStream.close();

    }

    /**
     * 模版导入
     */
    @RequestMapping(value = "/importSaveCollectionsGatherDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> importSaveCollectionsGatherDo(@RequestBody ImportCollectionsGather importCollectionsGather) {
        UnitItem unitItem = new UnitItem();
        unitItem.setIenId(importCollectionsGather.getEnId());
        unitItem.setAccountId(importCollectionsGather.getReceiverid());
        unitItem.setBilltypeId(importCollectionsGather.getBilltypeId());
        unitItem.setCompareDate(DateTimeUtils.getDateTimeStr2());
        List<UnitItem> unitItemList = unitItemService.getUnitItemByEnIdAndBilltypeIdAndAccountId(unitItem);



        for(int i = 0; i < importCollectionsGather.getCollectionsGatherList().size(); i++){
            for(int j = 0; j < importCollectionsGather.getCollectionsGatherList().get(i).getCollectionsGatherDetailList().size(); j++){
                String unitItemId = "", incitemId = "";
                IncomeItem ii = new IncomeItem();
                ii.setChrCode(importCollectionsGather.getCollectionsGatherList().get(i).getCollectionsGatherDetailList().get(j).getIncitemCode());
                ii = incomeItemService.get(ii);
                for(UnitItem ui : unitItemList){
                    if(ii.getChrId().equals(ui.getItemId())){
                        unitItemId = ui.getChrId();
                        incitemId = ui.getItemId();
                        break;
                    }
                }
                if(unitItemId.equals("")){
                    return new Result<>( false, "项目编码错误", Globals.SUCCESS_CODE);
                }else{
                    importCollectionsGather.getCollectionsGatherList().get(i).getCollectionsGatherDetailList().get(j).setUnititemId(unitItemId);
                    importCollectionsGather.getCollectionsGatherList().get(i).getCollectionsGatherDetailList().get(j).setIncitemid(incitemId);
                }
            }
        }
        boolean b = collectionsGatherService.importInsert(importCollectionsGather);
        if(b){
            return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }else{
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    /**
     * 判断当前cell是否为空
     * @param cell
     * @return
     */
    public boolean isCellEmpty(Cell cell){
        CellType cellType = null;
        if (cell != null){
            cellType = cell.getCellTypeEnum();
            if (cellType != CellType.BLANK){
                return false;
            }
        }
        return true;
    }



}
