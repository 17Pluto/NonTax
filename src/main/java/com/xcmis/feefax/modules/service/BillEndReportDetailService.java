package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 * BillInStoore service
 */
import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.dao.BillDistributeListDao;
import com.xcmis.feefax.modules.dao.BillEndReportDetailDao;
import com.xcmis.feefax.modules.dao.BillNameDao;
import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.feefax.modules.entity.BillDistributeList;
import com.xcmis.feefax.modules.entity.BillEndReportDetail;
import com.xcmis.feefax.modules.entity.BillName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BillEndReportDetailService {

    @Autowired
    private BillEndReportDetailDao billEndReportDetailDao;

    @Autowired
    private BillNameDao billNameDao;

    @Autowired
    private BillDetailDao billDetailDao;

    public List<BillEndReportDetail> findAllList(BillEndReportDetail billEndReportDetail){
        try{
            List<BillEndReportDetail> list = billEndReportDetailDao.findAllList(billEndReportDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillEndReportDetail> findList(BillEndReportDetail billEndReportDetail){
        try{
            List<BillEndReportDetail> list = billEndReportDetailDao.findList(billEndReportDetail);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillEndReportDetail get(BillEndReportDetail billEndReportDetail){
        try{
            BillEndReportDetail bt = billEndReportDetailDao.get(billEndReportDetail);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillEndReportDetail billEndReportDetail){
        billEndReportDetail = billEndReportDetailDao.get(billEndReportDetail);

        int row = billEndReportDetailDao.delete(billEndReportDetail);
        if(row > 0) {
            String strFormat = "";

            BillName billName = new BillName();
            billName.setChrId(billEndReportDetail.getUntaxBilltypeId());
            billName = billNameDao.get(billName);

            int billLength = billName.getBillLength();
            for (int k = 0; k < billLength; k++) {
                strFormat += "0";
            }

            DecimalFormat decimalFormat = new DecimalFormat(strFormat);

            long bgnBillNo1 = Long.parseLong(billEndReportDetail.getBgnBillNo());
            long endBillNo1 = Long.parseLong(billEndReportDetail.getEndBillNo());

            for (long i = bgnBillNo1; i <= endBillNo1; i++) {

                BillDetail billDetail = new BillDetail();
                billDetail.setUntaxBillnameId(billEndReportDetail.getUntaxBilltypeId());
                billDetail.setBillNo(decimalFormat.format(i));
                billDetail.setState("0");
                billDetail.setIsEndReport("0");
                billDetailDao.updateIsEndReport(billDetail);
            }
            return true;
        }else {
            return false;
        }

    }
}