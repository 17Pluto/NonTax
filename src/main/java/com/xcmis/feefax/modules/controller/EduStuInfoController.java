package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.*;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.ExcelUtils;
import com.xcmis.framework.common.utils.StringUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 学生信息导入
 * @Author fangzhe
 * @Date 2020/7/13 10:56 上午
 * @Version 1.4
 */
@RestController
@RequestMapping(value = "/feefax")
public class EduStuInfoController {
    //注入文件上传地址
    @Value("${web.file.path}")
    private String filePath;

    @Autowired
    private EduStuInfoService eduStuInfoService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private IncomeEnterpriseService incomeEnterpriseService;

    @Autowired
    private IncomeItemService incomeItemService;

    @Autowired
    private UnitItemService unitItemService;




    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadStuInfo")
    public Result<Boolean> uploadStuInfo(MultipartFile file,String chrId,String billtypeId,
                                         String pmId, String receiverid, String receiver,String receiveraccount, String receiverbank){

        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);

        UnitItem unitItem = new UnitItem();
        unitItem.setIenId(user.getBelongOrg());
        unitItem.setAccountId(receiverid);
        unitItem.setBilltypeId(billtypeId);
        unitItem.setCompareDate(DateTimeUtils.getDateTimeStr2());
        List<UnitItem> unitItemList = unitItemService.getUnitItemByEnIdAndBilltypeIdAndAccountId(unitItem);


        Map<String,Object> mapOut = new HashMap<>();




        List<EduImportStuInfo> result = new ArrayList<>();
        //获取文件名称
        String fileName = file.getOriginalFilename() ;

       // if(fileName.equals(""))
        //控制台输出文件名
        System.out.println("获取的文件名为：" + fileName);
        //上传路径
        File dest = new File(filePath + fileName);


        String incomeItem1 = null;
        String incomeItem2 = null;
        String incomeItem3 = null;
        String incomeItem4 = null;
        String incomeItem5 = null;
        String unititemId1 = null;
        String unititemId2 = null;
        String unititemId3 = null;
        String unititemId4 = null;
        String unititemId5 = null;
        boolean b = true;


        try {
            //获取输入流
            InputStream inputStream = file.getInputStream();
            //将文件保存至服务器
//            file.transferTo(dest);
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
            List<EduImportStuInfo> stuInfoList = new ArrayList<>();

            //先获取第一行的编号
            row = sheet.getRow(0);
            Cell cell = null;
            CellType cellType = null;
            //收费项目1
            cell = row.getCell(9);
            b = isCellEmpty(cell);
            if (!b){
                cellType = cell.getCellTypeEnum();
                if (cellType != CellType.BLANK){
                    cell.setCellType(CellType.STRING);
                    incomeItem1 = new BigDecimal(String.valueOf(cell.getStringCellValue())).toPlainString();
                    //此处目前缺少验证
                    IncomeItem incomeItem = new IncomeItem();
                    incomeItem.setChrCode(incomeItem1);
                    incomeItem = incomeItemService.get(incomeItem);

                    if (incomeItem == null){
                        return new Result<>(false,"收入项目1错误", Globals.SUCCESS_CODE);
                    }else {
                        String unitItemId = "";
                        for (UnitItem ui : unitItemList) {
                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                unitItemId = ui.getChrId();
                                unititemId1 = ui.getChrId();
                                break;
                            }
                        }
                        if (unitItemId.equals("")) {
                            return new Result<>(false,"收入项目1错误", Globals.SUCCESS_CODE);
                        }
                    }
                }else {
                    incomeItem1 = null;
                }
            }else {
                 incomeItem1 = null;
            }
            //收费项目2
            cell = row.getCell(10);
            b = isCellEmpty(cell);
            if (!b){
                cellType = cell.getCellTypeEnum();
                if (cellType != CellType.BLANK){
                    cell.setCellType(CellType.STRING);
                    incomeItem2 = new BigDecimal(String.valueOf(cell.getStringCellValue())).toPlainString();
                    IncomeItem incomeItem = new IncomeItem();
                    incomeItem.setChrCode(incomeItem2);
                    incomeItem = incomeItemService.get(incomeItem);
                    if (incomeItem == null){
                        return new Result<>(false,"收入项目2错误", Globals.SUCCESS_CODE);
                    }else {
                        String unitItemId = "";
                        for (UnitItem ui : unitItemList) {
                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                unitItemId = ui.getChrId();
                                unititemId2 = ui.getChrId();
                                break;
                            }
                        }
                        if (unitItemId.equals("")) {
                            return new Result<>(false,"收入项目2错误", Globals.SUCCESS_CODE);
                        }
                    }
                }else {
                    incomeItem2 = null;
                }
            }else {
                 incomeItem2 = null;
            }
            //收费项目3
            cell = row.getCell(11);
            b = isCellEmpty(cell);
            if (!b){
                cellType = cell.getCellTypeEnum();
                if (cellType != CellType.BLANK){
                    cell.setCellType(CellType.STRING);
                    incomeItem3 = new BigDecimal(String.valueOf(cell.getStringCellValue())).toPlainString();
                    IncomeItem incomeItem = new IncomeItem();
                    incomeItem.setChrCode(incomeItem3);
                    incomeItem = incomeItemService.get(incomeItem);
                    if (incomeItem == null){
                        return new Result<>(false,"收入项目3错误", Globals.SUCCESS_CODE);
                    }else {
                        String unitItemId = "";
                        for (UnitItem ui : unitItemList) {
                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                unitItemId = ui.getChrId();
                                unititemId3 = ui.getChrId();
                                break;
                            }
                        }
                        if (unitItemId.equals("")) {
                            return new Result<>(false,"收入项目3错误", Globals.SUCCESS_CODE);
                        }
                    }
                }else {
                    incomeItem3 = null;
                }
            }else {
                 incomeItem3 = null;
            }
            //收费项目4
            cell = row.getCell(12);
            b = isCellEmpty(cell);
            if (!b){
                cellType = cell.getCellTypeEnum();
                if (cellType != CellType.BLANK){
                    cell.setCellType(CellType.STRING);
                    incomeItem4 = new BigDecimal(String.valueOf(cell.getStringCellValue())).toPlainString();
                    IncomeItem incomeItem = new IncomeItem();
                    incomeItem.setChrCode(incomeItem4);
                    incomeItem = incomeItemService.get(incomeItem);
                    if (incomeItem == null){
                        return new Result<>(false,"收入项目4错误", Globals.SUCCESS_CODE);
                    }else {
                        String unitItemId = "";
                        for (UnitItem ui : unitItemList) {
                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                unitItemId = ui.getChrId();
                                unititemId4 = ui.getChrId();
                                break;
                            }
                        }
                        if (unitItemId.equals("")) {
                            return new Result<>(false,"收入项目4错误", Globals.SUCCESS_CODE);
                        }
                    }
                }else {
                    incomeItem4 = null;
                }
            }else {
                 incomeItem4 = null;
            }
            //收费项目5
            cell = row.getCell(13);
            b = isCellEmpty(cell);
            if (!b){
                cellType = cell.getCellTypeEnum();
                if (cellType != CellType.BLANK){
                    cell.setCellType(CellType.STRING);
                    incomeItem5 = new BigDecimal(String.valueOf(cell.getStringCellValue())).toPlainString();
                    IncomeItem incomeItem = new IncomeItem();
                    incomeItem.setChrCode(incomeItem5);
                    incomeItem = incomeItemService.get(incomeItem);
                    if (incomeItem == null){
                        return new Result<>(false,"收入项目5错误", Globals.SUCCESS_CODE);
                    }else {
                        String unitItemId = "";
                        for (UnitItem ui : unitItemList) {
                            if (incomeItem.getChrId().equals(ui.getItemId())) {
                                unitItemId = ui.getChrId();
                                unititemId5 = ui.getChrId();
                                break;
                            }
                        }
                        if (unitItemId.equals("")) {
                            return new Result<>(false,"收入项目5错误", Globals.SUCCESS_CODE);
                        }
                    }
                }else {
                    incomeItem5 = null;
                }
            }else {
                 incomeItem5 = null;
            }
            System.out.println(incomeItem1);
            System.out.println("lastCellNum:"+row.getLastCellNum());
            if (incomeItem1==null && incomeItem2==null && incomeItem3==null && incomeItem4==null && incomeItem5==null){
                return new Result<>(false,"请输入收入项目编码", Globals.SUCCESS_CODE);
            }
            //获取第二行的名称
//            row = sheet.getRow(1);


            for(int i = 2 ; i<sheet.getPhysicalNumberOfRows();i++){
                row = sheet.getRow(i);
                cell = null;
                cellType = null;
//                cell = row.getCell(row.getLastCellNum());
//                b = isCellEmpty(cell);
//                if (b){
//                    return new Result<>(false,"存在空列", Globals.SUCCESS_CODE);
//                }

                EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();

                List<EduImportStuInfoDetail> detailList = new ArrayList();
                for (int j = 0 ; j < row.getLastCellNum();j++){
                    switch (j){
                        case 0: //姓名
                            cell = row.getCell(0);
                            b = isCellEmpty(cell);
                            if (!b){
                                eduImportStuInfo.setStuName(cell.getStringCellValue());
                                break;
                            }
                            return new Result<>(false,"第"+(i+1)+"行，第"+(j+1)+"列错误", Globals.SUCCESS_CODE);
                        case 1: //编号
                            /**
                             * 校验规则：
                             * r+单位（2位）+中/小学（1位）+入学年份（2位）+班级（2位）+学号（2位）
                             *
                             * 年级=当前年份-入学年份
                             *
                             */
                            cell = row.getCell(1);
                            b = isCellEmpty(cell);
                            if (!b){
                                cell.setCellType(CellType.STRING);
                                String stuId = cell.getStringCellValue().trim();

                                if (stuId.length() != 10){
                                    return new Result<>(false,"第"+(i+1)+"行，第2列学号错误",Globals.SUCCESS_CODE);
                                }else {
                                    stuId.substring(0,1); //r
                                    stuId.substring(1,3); //单位
                                    stuId.substring(3,4); //中/小学
                                    stuId.substring(4,6); //入学年份
                                    stuId.substring(6,8); //班级
                                    stuId.substring(8); //学号
                                    String currentYear = DateTimeUtils.getCurrentYear();//当前年份
                                    int exportYear = Integer.parseInt(stuId.substring(4,6));
                                    int year = Integer.parseInt(currentYear.substring(2,4));
                                    String grade = String.valueOf(year - exportYear + 1);
                                    eduImportStuInfo.setStuDegree(stuId.substring(3,4));
                                    //存入届
                                    eduImportStuInfo.setStuGrade(stuId.substring(4,6));
                                }
                                eduImportStuInfo.setStuId(cell.getStringCellValue());
                                break;
                            }
                            return new Result<>(false,"第"+(i+1)+"行，第2列错误",Globals.SUCCESS_CODE);
                        case 2: //性别
                            cell = row.getCell( 2);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (cell.getStringCellValue().equals("男") ){
                                    eduImportStuInfo.setStuSex(1);
                                    break;
                                }else {
                                    eduImportStuInfo.setStuSex(0);
                                    break;
                                }
                            }
                            break;
                        case 3: //生日
                            cell = row.getCell(3);
                            b = isCellEmpty(cell);
                            if (!b){
                                eduImportStuInfo.setStuBirth(cell.getStringCellValue());
                                break;
                            }
                            break;
                        case 4: //身份证号
                            /**
                             * 暂时不校验⚠️⚠️⚠️
                             */
                            cell = row.getCell(4);
                            b = isCellEmpty(cell);
                            if (!b){
                                cell.setCellType(CellType.STRING);
                                eduImportStuInfo.setStuIdentity(cell.getStringCellValue());
                                break;
                            }
                            break;
                        case 5: //录入日期
                            cell = row.getCell(5);
                            b = isCellEmpty(cell);
                            if (!b){
                                eduImportStuInfo.setStuInserttime(cell.getStringCellValue());
                                break;
                            }
                            break;
                        case 6: //年级
                            cell = row.getCell(6);
                            b = isCellEmpty(cell);
                            if (!b){
//                                eduImportStuInfo.setStuGrade(cell.getStringCellValue());
                                break;
                            }
//                            return new Result<>(false,"第"+(i+1)+"行，第7列错误",Globals.SUCCESS_CODE);
                        case 7: //班级
                            cell = row.getCell(7);
                            b = isCellEmpty(cell);
                            if (!b){
                                cell.setCellType(CellType.STRING);
                                eduImportStuInfo.setStuClass(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            }
                            return new Result<>(false,"第"+(i+1)+"行，第8列错误",Globals.SUCCESS_CODE);
                        case 8: //备注
                            cell = row.getCell(8);
                            b = isCellEmpty(cell);
                            if (!b){
                                eduImportStuInfo.setRemark(cell.getStringCellValue());
                                break;
                            }
                            break;
                        case 9: //收费金额1
                            cell = row.getCell(9);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (incomeItem1 != null){
                                    EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                                    eduImportStuInfoDetail.setIncitemCode(incomeItem1);
                                    eduImportStuInfoDetail.setUnititemId(unititemId1);
                                    eduImportStuInfoDetail.setChargemoney(cell.getNumericCellValue());
                                    detailList.add(eduImportStuInfoDetail);
                                    break;
                                }
                                break;
                            }
                            break;
//                            return new Result<>(false,"第"+(i+1)+"行，金额1错误",Globals.SUCCESS_CODE);
                        case 10: //收费金额2
                            cell = row.getCell(10);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (incomeItem2 != null){
                                    EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                                    eduImportStuInfoDetail.setIncitemCode(incomeItem2);
                                    eduImportStuInfoDetail.setUnititemId(unititemId2);
                                    eduImportStuInfoDetail.setChargemoney(cell.getNumericCellValue());
                                    detailList.add(eduImportStuInfoDetail);
                                    break;
                                }
                                break;
                            }
                            break;
//                            return new Result<>(false,"第"+(i+1)+"行，金额2错误",Globals.SUCCESS_CODE);
                        case 11: //收费金额3
                            cell = row.getCell(11);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (incomeItem3 != null){
                                    EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                                    eduImportStuInfoDetail.setIncitemCode(incomeItem3);
                                    eduImportStuInfoDetail.setUnititemId(unititemId3);
                                    eduImportStuInfoDetail.setChargemoney(cell.getNumericCellValue());
                                    detailList.add(eduImportStuInfoDetail);
                                    break;
                                }
                                break;
                            }
                            break;
//                            return new Result<>(false,"第"+(i+1)+"行，金额4错误",Globals.SUCCESS_CODE);
                        case 12: //收费金额4
                            cell = row.getCell(12);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (incomeItem4 != null){
                                    EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                                    eduImportStuInfoDetail.setIncitemCode(incomeItem4);
                                    eduImportStuInfoDetail.setUnititemId(unititemId4);
                                    eduImportStuInfoDetail.setChargemoney(cell.getNumericCellValue());
                                    detailList.add(eduImportStuInfoDetail);
                                    break;
                                }
                                break;
                            }
                            break;
//                            return new Result<>(false,"第"+(i+1)+"行，金额4错误",Globals.SUCCESS_CODE);
                        case 13: //收费金额5
                            cell = row.getCell(13);
                            b = isCellEmpty(cell);
                            if (!b){
                                if (incomeItem5 != null){
                                    EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                                    eduImportStuInfoDetail.setIncitemCode(incomeItem5);
                                    eduImportStuInfoDetail.setUnititemId(unititemId5);
                                    eduImportStuInfoDetail.setChargemoney(cell.getNumericCellValue());
                                    detailList.add(eduImportStuInfoDetail);
                                    break;
                                }
                                break;
                            }
                            break;
//                            return new Result<>(false,"第"+(i+1)+"行，金额5错误",Globals.SUCCESS_CODE);
                        default:
                            break;
                    }
                    eduImportStuInfo.setEduImportStuInfoDetailList(detailList);
                }

                eduImportStuInfo.setBatchid(chrId);
                EduImportStuInfo eduImportStuInfo1 = eduStuInfoService.get(eduImportStuInfo);
                if (eduImportStuInfo1 != null){
                        return new Result<>(false,"第"+(i+1)+"行，学号重复",Globals.SUCCESS_CODE);
                }

                stuInfoList.add(eduImportStuInfo);
                workbook.close();
            }

            /**
             * 遍历list插入数据库
             */
            for (int i = 0 ; i < stuInfoList.size() ; i++){
                EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
                eduImportStuInfo = stuInfoList.get(i);
                eduImportStuInfo.setBatchid(chrId);
                eduImportStuInfo.setPmId(pmId);
                eduImportStuInfo.setBilltypeId(billtypeId);
                eduImportStuInfo.setReceiverid(receiverid);
                eduImportStuInfo.setEnId(user.getBelongOrg());
                eduImportStuInfo.setCreateDate(DateTimeUtils.getDateTimeStr1());
                eduImportStuInfo.setCreateUser(user.getBelongOrg());
                eduImportStuInfo.setReceiver(receiver);
                eduImportStuInfo.setReceiverbank(receiverbank);
                eduImportStuInfo.setReceiveraccount(receiveraccount);
                eduStuInfoService.stuInfoInsert(eduImportStuInfo);
            }

        }catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(true,"上传成功",Globals.SUCCESS_CODE);
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
     * 新增学生信息
     * @param eduImportStuInfo
     * @return
     */
    @PostMapping("/addStuInfoDo")
    public Result<Boolean> addStuInfoDo(@RequestBody EduImportStuInfo eduImportStuInfo){
        try {
            String stuId = eduImportStuInfo.getStuId();
            if (stuId.length() != 10){
                return new Result<>(false,"学号错误",Globals.SUCCESS_CODE);
            }else {
                stuId.substring(0,0); //r
                stuId.substring(1,2); //单位
                stuId.substring(3,3); //中/小学
                stuId.substring(4,5); //入学年份
                stuId.substring(6,7); //班级
                stuId.substring(8,9); //学号
                String currentYear = DateTimeUtils.getCurrentYear();//当前年份
                int exportYear = Integer.parseInt(stuId.substring(4,5));
                int year = Integer.parseInt(currentYear.substring(2,3));
                String grade = String.valueOf(year - exportYear + 1);
                eduImportStuInfo.setStuDegree(stuId.substring(3,3));
                //存入届
                eduImportStuInfo.setStuGrade(stuId.substring(4,5));
            }
            boolean b = eduStuInfoService.insert(eduImportStuInfo);

            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    /**
     * 根据chrId查询单条
     * @param chrId
     * @return
     */
    @GetMapping("/getEduStuInfoByChrId")
    public Result<Map<String,Object>> getEduStuInfoByChrId(String chrId){
        EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
        eduImportStuInfo = eduStuInfoService.getEduStuInfoByChrId(chrId);
        EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
        eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
        Map<String, Object> mapIn = new HashMap<>();
        mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
        List<EduImportStuInfoDetail> list = eduStuInfoService.getEduStuInfoDetailList(mapIn);
        eduImportStuInfo.setEduImportStuInfoDetailList(list);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("eduImportStuInfo",eduImportStuInfo);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 修改学生信息
     * @return
     */
    @PostMapping("/updateStuInfo")
    public Result<Boolean> updateStuInfo(@RequestBody EduImportStuInfo eduImportStuInfo){
        try{
            Subject subject = SecurityUtils.getSubject();
            String token = (String)subject.getPrincipal();
            String userId = JwtTokenUtil.getUserId(token);

            User user = new User();
            user.setUserId(userId);
            user = userService.get(user);
            eduImportStuInfo.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
            eduImportStuInfo.setLatestOpUser(user.getBelongOrg());
            boolean b = eduStuInfoService.updateStuInfo(eduImportStuInfo);
            if (b){
                return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @PostMapping("/updateStuInfoBillNo")
    public Result<Boolean> updateStuInfoBillNo(String billNo, String chrId, String billtypeId){
        try{
            EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
            eduImportStuInfo.setBillNo(billNo);
            eduImportStuInfo.setChrId(chrId);
            eduImportStuInfo.setBilltypeId(billtypeId);
            boolean b = eduStuInfoService.updateStuInfoBillNo(eduImportStuInfo);
            if (b){
                return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    /**
     * 获取学生信息上列表
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @param eduImportStuInfo
     * @return
     */
    @GetMapping("/getEduStuInfo")
    public Result<Map<String,Object>> getEduStuInfo(String page, String rows, String sort,
                                                    String order,EduImportStuInfo eduImportStuInfo){


        Map<String, Object> mapIn = new HashMap<>();
        String enCode = "";
        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        String unitId = user.getBelongOrg();

        IncomeEnterprise ie = new IncomeEnterprise();
        ie.setChrId(unitId);
        ie = incomeEnterpriseService.get(ie);
        enCode = ie.getChrCode();

        eduImportStuInfo.setEnId(user.getBelongOrg());
        eduImportStuInfo.setRgCode(regionService.get(null).getChrCode());

        long total = eduStuInfoService.getEduStuInfoListTotal(eduImportStuInfo);
        int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
        mapIn.put("startrow", startrow);
        mapIn.put("endrow", startrow + Integer.parseInt(rows));
        mapIn.put("sort", sort);
        mapIn.put("order", order);

        mapIn.put("EduImportStuInfo",eduImportStuInfo);
        List<EduImportStuInfo> list = eduStuInfoService.getEduStuInfoListByCondition(mapIn);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("total", total);
        mapOut.put("list",list);
        mapOut.put("enCode", enCode);
        mapOut.put("enId",user.getBelongOrg());
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 获取下列表
     * @param stuMainid
     * @return
     */
    @GetMapping("/getEduStuInfoDetailList")
    public Result<Map<String,Object>> getEduStuInfoDetailList(String stuMainid){
        EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
        eduImportStuInfoDetail.setStuMainid(stuMainid);
        Map<String, Object> mapIn = new HashMap<>();
        mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
        List<EduImportStuInfoDetail> list = eduStuInfoService.getEduStuInfoDetailList(mapIn);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("list",list);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 生成缴款识别码
     * @param chrIds 页面回传stu的chrIds
     * @return
     */
    @PostMapping("/addCollectionsForStuInfoDo")
    public Result<Boolean> addCollectionsForStuInfoDo(@RequestParam(value = "chrIds") String chrIds){
        try {
            String[] chrIdArr = chrIds.split(",");
            for(String chrId : chrIdArr){
                EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
                EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
                Boolean isExsist = true;
                Map<String, Object> mapIn = new HashMap<>();

                /**
                 * 根据选中的chrid查找对应学生信息
                 */
                eduImportStuInfo = eduStuInfoService.getEduStuInfoByChrId(chrId);

                eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
                mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
                isExsist  = eduStuInfoService.getEduStuInfoDetailByChrId(mapIn);
                if (!isExsist){
                    return new Result<>(false,"金额为0不允许生成", Globals.FAILED_CODE);
                }

                Collections collections = new Collections();
                collections.setSetYear(DateTimeUtils.getCurrentYear());
                collections.setCreateDate(DateTimeUtils.getDateTimeStr1());
                collections.setCreateUser(UserUtils.getUserId());
                collections.setLastestOpDate(DateTimeUtils.getDateTimeStr1());
                collections.setLastestOpUser(UserUtils.getUserId());
                collections.setLastVer(DateTimeUtils.getDateTimeStr1());
                collections.setRgCode(regionService.get(null).getChrCode());




                /**
                 * 存入学校信息
                 */
                collections.setReceivetype(2);
                //collections.setChrCode1(eduImportStuInfo.getStuIdentity());
                collections.setReceiver(eduImportStuInfo.getReceiver());
                collections.setReceiveraccount(eduImportStuInfo.getReceiveraccount());
                collections.setReceiverbank(eduImportStuInfo.getReceiverbank());

                collections.setChrCode2(eduImportStuInfo.getStuGrade());
                collections.setChrCode3(String.valueOf(eduImportStuInfo.getStuClass()));
                collections.setChrCode4(eduImportStuInfo.getChrId());
                collections.setPayerName(eduImportStuInfo.getStuName());
                collections.setEnId(eduImportStuInfo.getEnId());
                collections.setPmId(eduImportStuInfo.getPmId());
                collections.setReceiverid(eduImportStuInfo.getReceiverid());
                collections.setBilltypeId(eduImportStuInfo.getBilltypeId());
                collections.setEnId(eduImportStuInfo.getEnId());
                collections.setMakedate(DateTimeUtils.getDateTimeStr1());
                collections.setIsconsign(0);
                String maxReqBillNo = collectionsService.getMaxReqBillNo(collections);
                collections.setReqBillNo(StringUtils.getReqBillNo(maxReqBillNo, DateTimeUtils.getCurrentYear(), regionService.get(null).getChrCode()));

                boolean b = collectionsService.insertStuInfo(collections);

            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(false,Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
        return new Result<>(true,Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 学生信息删除
     * @param chrIds
     * @return
     */
    @PostMapping("/delStuInfo")
    public Result<Boolean> delStuInfo(String chrIds){
        try {
            boolean b = eduStuInfoService.delete(chrIds);
            if(b){
                return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    /**
     * 学生信息导出
     * @param response
     * @param
     */
    @GetMapping("/stuInfoExport")
    public void stuInfoExport(HttpServletResponse response ,  String stuGrade , String chrId) throws IOException {

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        response.setContentType("application/octet-stream");
        response.setHeader("Cache-Control","attachment;filename=" + java.net.URLEncoder.encode(stuGrade+"年级汇总数据","utf-8")+".zip");
        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);

        //选择年级分班导出
        if (!stuGrade.equals("全部") ){
            List<EduImportStuInfo> classList = getStuByClass(stuGrade,chrId);
            for (int i = 0 ; i < classList.size() ; i++){
                EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
                int stuClass = classList.get(i).getStuClass();
                eduImportStuInfo.setStuClass(stuClass);
                eduImportStuInfo.setStuGrade(stuGrade);
                String fileName = stuGrade+"年级"+stuClass+"班（缴款信息）.xls";// 设置要导出的文件的名字
                // 获取数据集合
                Map<String, Object> mapIn = new HashMap<>();
                eduImportStuInfo.setStateCode("902");
                eduImportStuInfo.setBatchid(chrId);
                eduImportStuInfo.setEnId(user.getBelongOrg());//绑定enId
                mapIn.put("EduImportStuInfo",eduImportStuInfo);
                List<EduImportStuInfo> list = eduStuInfoService.getEduStuInfoListByConditionForExport(mapIn);
                EduImportStuInfo entity = null;
//                HSSFWorkbook workbook = new HSSFWorkbook();
//                HSSFSheet sheet = workbook.createSheet("Sheet1");
//                sheet.setDefaultRowHeightInPoints(20);// 设置缺省列高
//                sheet.setDefaultColumnWidth(20);//设置缺省列宽x
//                sheet.setColumnWidth(0,31*256);//设置第一列宽度
//                sheet.setColumnWidth(1,15*256);
//                //获取第一行
//                HSSFRow row = sheet.createRow(0);//创建行
//                HSSFCell cell = row.createCell(0);//创建单元格（列）
//                sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));//合并单元格 24
//
//                //!!此处需要验证
//                row.setHeightInPoints(40);//第一行宽度
//                row.getCell(0).setCellValue("幼儿园缴款信息");//向第一行中插入标题
//                cell.setCellStyle(ExcelUtils.getTitleStyle(workbook));//设置单元格样式
//                //第二行为空行
//                //获取第三行
//                HSSFRow row1 = sheet.createRow(2);
//                row1.setHeightInPoints(28);//第三行宽度
//                row1.createCell(0).setCellValue("姓名");
//                row1.createCell(1).setCellValue("学生编号");
//                row1.createCell(2).setCellValue("年级");
//                row1.createCell(3).setCellValue("班级");
//                row1.createCell(4).setCellValue("缴款识别码");
//
//                for(int a = 0 ; a <= 4 ; a++){
//                    HSSFCell cell1 = row1.getCell(a);
//                    cell1.setCellStyle(ExcelUtils.getFormStyle1(workbook));
//                }
                HSSFWorkbook workbook = new HSSFWorkbook();
                String[] sheetNames = {"sheet1"};//表单名
                String[] sheetTitles = {"幼儿园缴款信息"};//标题
                int[] unionNumber = {4};//需要合并的第一行单元格
                String[] indexTitles = {"姓名","学生编号","年级","班级","缴款识别码"};//索引
                workbook = ExcelUtils.createExcelSheet(sheetNames,sheetTitles,workbook,unionNumber,indexTitles);
                HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row = null;

                //插入数据
                for (int j = 0; j < list.size(); j++) {
                    entity = list.get(j);
                    row = sheet.createRow(j+3);
                    row.setHeightInPoints(30);
                    row.createCell(0).setCellValue(entity.getStuName());//姓名
                    row.createCell(1).setCellValue(entity.getStuId());//编号
                    row.createCell(2).setCellValue(entity.getStuGrade());//年级
                    row.createCell(3).setCellValue(entity.getStuClass());//班级
                    row.createCell(4).setCellValue(entity.getReqBillNo());//缴款识别码
                    for(int k = 0; k<=4 ;k++){
                        HSSFCell cell2 = row.getCell(k);
                        cell2.setCellStyle(ExcelUtils.getFormStyle2(workbook));
                    }
                }
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                workbook.write(zipOutputStream);
            }
            zipOutputStream.flush();
            zipOutputStream.close();
            response.getOutputStream().close();
        }else {
            EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
            String fileName = "汇总（缴款信息）.xls";// 设置要导出的文件的名字
            // 获取数据集合
            Map<String, Object> mapIn = new HashMap<>();
            eduImportStuInfo.setStateCode("902");
            eduImportStuInfo.setBatchid(chrId);//批次的chrid
            eduImportStuInfo.setEnId(user.getBelongOrg());//绑定enId
            mapIn.put("EduImportStuInfo",eduImportStuInfo);
            List<EduImportStuInfo> list = eduStuInfoService.getEduStuInfoListByConditionForExport(mapIn);
            EduImportStuInfo entity = null;


            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            sheet.setDefaultRowHeightInPoints(10);// 设置缺省列高
            sheet.setDefaultColumnWidth(20);//设置缺省列宽x
            sheet.setColumnWidth(0,31*256);//设置第一列宽度
            sheet.setColumnWidth(1,15*256);

            //获取第一行
            HSSFRow row = sheet.createRow(0);//创建行
            HSSFCell cell = row.createCell(0);//创建单元格（列）
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));//合并单元格 24
            row.setHeightInPoints(40);//第一行宽度
            row.getCell(0).setCellValue("幼儿园缴款信息");//向第一行中插入标题
            cell.setCellStyle(ExcelUtils.getTitleStyle(workbook));//设置单元格样式
            //第二行为空行
            //获取第三行
            HSSFRow row1 = sheet.createRow(2);
            row1.setHeightInPoints(28);//第三行宽度
            row1.createCell(0).setCellValue("姓名");
            row1.createCell(1).setCellValue("学生编号");
            row1.createCell(2).setCellValue("年级");
            row1.createCell(3).setCellValue("班级");
            row1.createCell(4).setCellValue("缴款识别码");

            for(int a = 0 ; a <= 4 ; a++){
                HSSFCell cell1 = row1.getCell(a);
                cell1.setCellStyle(ExcelUtils.getFormStyle1(workbook));
            }


            for (int j = 0; j < list.size(); j++) {
                entity = list.get(j);
                row1 = sheet.createRow(j + 3);
                row1.setHeightInPoints(30);
                row1.createCell(0).setCellValue(entity.getStuName());//姓名
                row1.createCell(1).setCellValue(entity.getStuId());//编号
                row1.createCell(2).setCellValue(entity.getStuGrade());//年级
                row1.createCell(3).setCellValue(entity.getStuClass());//班级
                row1.createCell(4).setCellValue(entity.getReqBillNo());//缴款识别码
                for (int k = 0; k <= 4; k++) {
                    HSSFCell cell2 = row1.getCell(k);
                    cell2.setCellStyle(ExcelUtils.getFormStyle2(workbook));
                }
            }
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOutputStream.putNextEntry(zipEntry);
            workbook.write(zipOutputStream);

            zipOutputStream.flush();
            zipOutputStream.close();
            response.getOutputStream().close();
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


    /**
     * 获取全部年级
     */
    @GetMapping("/getStuByGrade")
    public Result<Map<String,Object>> getStuByGrade(EduImportStuInfo eduImportStuInfo){
        User user = new User();
        user.setUserId(UserUtils.getUserId());
        user = userService.get(user);
        //获取enId
        eduImportStuInfo.setEnId(user.getBelongOrg());
        List<EduImportStuInfo> list = eduStuInfoService.findAllGrade(eduImportStuInfo);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("list", list);//要以JSON格式返回
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    /**
     * 先获取指定的年级，再根据年级查找班级
     * 获取全部班级
     */
    @GetMapping("/getStuByClass")
    public List<EduImportStuInfo> getStuByClass(String stuGrade,String chrId){

        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);
        EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
        eduImportStuInfo.setStuGrade(stuGrade);
        eduImportStuInfo.setEnId(user.getBelongOrg());
        eduImportStuInfo.setBatchid(chrId);
        List<EduImportStuInfo> list = eduStuInfoService.findAllClass(eduImportStuInfo);
        return list;
    }

    /**
     * 更改缴款标志
     * @param chrId
     * @return
     */
    @GetMapping("/updateStuPayflag")
    public Result<Boolean> updateStuPayflag(String chrId){
        try{
            boolean b = eduStuInfoService.updateStuPayflag(chrId);
            if (b){
                return new Result<>( Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @GetMapping("/checkStuIdIsExist")
    public Result<String> checkStuIdIsExist(String stuId , String batchid){
        try{
            if (stuId.equals("")){
                return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }

            EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
            eduImportStuInfo.setBatchid(batchid);
            eduImportStuInfo.setStuId(stuId);
            eduImportStuInfo = eduStuInfoService.get(eduImportStuInfo);
            if(eduImportStuInfo == null){
                return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else {
                if (eduImportStuInfo.getStuId().equals("")) {
                    return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                } else {
                    return new Result<>(eduImportStuInfo.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
                }
            }

        }catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    /**
     * 学生信息打印
     * getEduStuInfoByChrIdForPrint
     * @param chrIds
     * @return
     */
    @GetMapping("/getEduStuInfoByChrIdForPrint")
    public Result<Map<String,Object>> getEduStuInfoByChrIdForPrint(String chrIds){
        String[] chrIdArr = chrIds.split(",");
        List<EduImportStuInfo> result = new ArrayList<>();
        for(String chrId : chrIdArr){
            EduImportStuInfo eduImportStuInfo = new EduImportStuInfo();
            eduImportStuInfo = eduStuInfoService.getEduStuInfoByChrId(chrId);
            EduImportStuInfoDetail eduImportStuInfoDetail = new EduImportStuInfoDetail();
            eduImportStuInfoDetail.setStuMainid(eduImportStuInfo.getChrId());
            Map<String, Object> mapIn = new HashMap<>();
            mapIn.put("EduImportStuInfoDetail",eduImportStuInfoDetail);
            List<EduImportStuInfoDetail> list = eduStuInfoService.getEduStuInfoDetailList(mapIn);
            eduImportStuInfo.setEduImportStuInfoDetailList(list);
            eduImportStuInfo.setQrCode("http://pay.jscz.gov.cn/fntop/qrcode?billId=" + eduImportStuInfo.getReqBillNo());
            result.add(eduImportStuInfo);
        }
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String userId = JwtTokenUtil.getUserId(token);

        User user = new User();
        user.setUserId(userId);
        user = userService.get(user);

        IncomeEnterprise incomeEnterprise = new IncomeEnterprise();
        incomeEnterprise.setChrId(user.getBelongOrg());

        incomeEnterprise = incomeEnterpriseService.get(incomeEnterprise);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("incomeEnterprise", incomeEnterprise);//要以JSON格式返回
        mapOut.put("result",result);
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }





}
