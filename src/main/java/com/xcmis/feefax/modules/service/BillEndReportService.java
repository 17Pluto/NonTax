package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 * BillInStoore service
 */

import com.xcmis.feefax.modules.dao.*;
import com.xcmis.feefax.modules.entity.*;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillEndReportService {

    @Autowired
    private BillEndReportDao billEndReportDao;

    @Autowired
    private BillEndReportDetailDao billEndReportDetailDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private BillNameDao billNameDao;


    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillEndReport billEndReport) {
        int row = billEndReportDao.insert(billEndReport);
        if(row > 0){
            String mainId = billEndReport.getChrId();
            for(BillEndReportDetail berd : billEndReport.getBillEndReportDetailList()){
                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(berd.getUntaxBilltypeId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);


                berd.setBgnBillNo(decimalFormat.format(Long.parseLong(berd.getBgnBillNo())));
                berd.setEndBillNo(decimalFormat.format(Long.parseLong(berd.getEndBillNo())));
                berd.setMainId(mainId);
                berd.setOccurtime(billEndReport.getLastVer());
                billEndReportDetailDao.insert(berd);
            }

            if(billEndReport.getIsend() == 1){
                if(billEndReport.getBillEndReportDetailList() != null){
                    for(BillEndReportDetail berd : billEndReport.getBillEndReportDetailList()){
                        String strFormat = "";

                        BillName billName = new BillName();
                        billName.setChrId(berd.getUntaxBilltypeId());
                        billName = billNameDao.get(billName);

                        int billLength = billName.getBillLength();
                        for(int k = 0; k < billLength; k++){
                            strFormat += "0";
                        }

                        DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                        long bgnBillNo1 = Long.parseLong(berd.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(berd.getEndBillNo());

                        for(long i = bgnBillNo1; i <= endBillNo1; i++){

                            BillDetail billDetail = new BillDetail();
                            billDetail.setUntaxBillnameId(berd.getUntaxBilltypeId());
                            billDetail.setBillNo(decimalFormat.format(i));
                            billDetail.setState("1");
                            billDetail.setIsEndReport("1");
                            billDetailDao.updateIsEndReport(billDetail);
                        }

                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }


    public String getMaxNo(BillEndReport billEndReport){
        try{
            String billserialNo = billEndReportDao.getMaxNo(billEndReport);
            if(billserialNo == null){
                return "";
            }
            return billserialNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }


    public List<BillEndReport> getBillEndReportListByCondition(Map<String, Object> map){
        try{
            List<BillEndReport> list = billEndReportDao.getBillEndReportListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillEndReportListTotal(BillEndReport billEndReport){
        try{
            long total = billEndReportDao.getBillEndReportListTotal(billEndReport);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public BillEndReport get(BillEndReport billEndReport){
        try{
            BillEndReport bt = billEndReportDao.get(billEndReport);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillEndReport billEndReport){
        int row = billEndReportDao.update(billEndReport);
        if(row > 0){
            String mainId = billEndReport.getChrId();

            BillEndReportDetail billEndReportDetail = new BillEndReportDetail();
            billEndReportDetail.setMainId(mainId);
            List<BillEndReportDetail> billEndReportDetailList = billEndReportDetailDao.findAllList(billEndReportDetail);
            for(BillEndReportDetail berd : billEndReportDetailList){
                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(berd.getUntaxBilltypeId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                long bgnBillNo1 = Long.parseLong(berd.getBgnBillNo());
                long endBillNo1 = Long.parseLong(berd.getEndBillNo());

                for(long i = bgnBillNo1; i <= endBillNo1; i++){

                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(berd.getUntaxBilltypeId());
                    billDetail.setBillNo(decimalFormat.format(i));
                    billDetail.setState("0");
                    billDetail.setIsEndReport("0");
                    billDetailDao.updateIsEndReport(billDetail);
                }
            }

            billEndReportDetailDao.delete(billEndReportDetail);

            for(BillEndReportDetail berd : billEndReport.getBillEndReportDetailList()){
                berd.setMainId(mainId);
                berd.setOccurtime(billEndReport.getLastVer());
                billEndReportDetailDao.insert(berd);

                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(berd.getUntaxBilltypeId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                long bgnBillNo1 = Long.parseLong(berd.getBgnBillNo());
                long endBillNo1 = Long.parseLong(berd.getEndBillNo());

                for(long i = bgnBillNo1; i <= endBillNo1; i++){

                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(berd.getUntaxBilltypeId());
                    billDetail.setBillNo(decimalFormat.format(i));
                    billDetail.setState("1");
                    billDetail.setIsEndReport("1");
                    billDetailDao.updateIsEndReport(billDetail);
                }
            }
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean checkBillEndReport(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillEndReport billEndReport = new BillEndReport();
            billEndReport.setStateCode(stateCode);
            billEndReport.setChrId(chrId);
            row += billEndReportDao.checkBillEndReport(billEndReport);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillEndReport billEndReport){
        int row = billEndReportDao.delete(billEndReport);
        if(row > 0){
            String mainId = billEndReport.getChrId();

            BillEndReportDetail billEndReportDetail = new BillEndReportDetail();
            billEndReportDetail.setMainId(mainId);
            List<BillEndReportDetail> billEndReportDetailList = billEndReportDetailDao.findAllList(billEndReportDetail);
            for(BillEndReportDetail berd : billEndReportDetailList){
                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(berd.getUntaxBilltypeId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                long bgnBillNo1 = Long.parseLong(berd.getBgnBillNo());
                long endBillNo1 = Long.parseLong(berd.getEndBillNo());

                for(long i = bgnBillNo1; i <= endBillNo1; i++){

                    BillDetail billDetail = new BillDetail();
                    billDetail.setUntaxBillnameId(berd.getUntaxBilltypeId());
                    billDetail.setBillNo(decimalFormat.format(i));
                    billDetail.setState("0");
                    billDetail.setIsEndReport("0");
                    billDetailDao.updateIsEndReport(billDetail);
                }
            }

            billEndReportDetailDao.delete(billEndReportDetail);
            return true;
        }else{
            return false;
        }
    }


    public boolean returnBillEndReport(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.returnStateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillEndReport billEndReport = new BillEndReport();
            billEndReport.setStateCode(stateCode);
            billEndReport.setChrId(chrId);
            row += billEndReportDao.checkBillEndReport(billEndReport);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }
}