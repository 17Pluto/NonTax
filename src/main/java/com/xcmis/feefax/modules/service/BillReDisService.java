package com.xcmis.feefax.modules.service;
/**
 * @author fangzhe
 * @date 2020-03-31
 * BillInStoore service
 */

import com.xcmis.feefax.modules.dao.BillDetailDao;
import com.xcmis.feefax.modules.dao.BillNameDao;
import com.xcmis.feefax.modules.dao.BillReDisDao;
import com.xcmis.feefax.modules.dao.BillReDisListDao;
import com.xcmis.feefax.modules.entity.BillDetail;
import com.xcmis.feefax.modules.entity.BillName;
import com.xcmis.feefax.modules.entity.BillReDis;
import com.xcmis.feefax.modules.entity.BillReDisList;
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
public class BillReDisService {

    @Autowired
    private BillReDisDao billReDisDao;

    @Autowired
    private BillReDisListDao billReDisListDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private BillNameDao billNameDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillReDis billReDis) {
        int row = billReDisDao.insert(billReDis);
        if(row > 0){
            String mainId = billReDis.getChrId();
            for(BillReDisList bdl : billReDis.getBillReDisList()){
                String strFormat = "";

                BillName billName = new BillName();
                billName.setChrId(bdl.getUntaxBillnameId());
                billName = billNameDao.get(billName);

                int billLength = billName.getBillLength();
                for(int k = 0; k < billLength; k++){
                    strFormat += "0";
                }

                DecimalFormat decimalFormat = new DecimalFormat(strFormat);


                bdl.setBgnBillNo(decimalFormat.format(Long.parseLong(bdl.getBgnBillNo())));
                bdl.setEndBillNo(decimalFormat.format(Long.parseLong(bdl.getEndBillNo())));
                bdl.setMainId(mainId);
                bdl.setOccurTime(billReDis.getLastVer());
                billReDisListDao.insert(bdl);
            }

            if(billReDis.getIsend() == 1){
                if(billReDis.getBillReDisList() != null){
                    for(BillReDisList bdl : billReDis.getBillReDisList()){
                        String strFormat = "";

                        BillName billName = new BillName();
                        billName.setChrId(bdl.getUntaxBillnameId());
                        billName = billNameDao.get(billName);

                        int billLength = billName.getBillLength();
                        for(int k = 0; k < billLength; k++){
                            strFormat += "0";
                        }

                        DecimalFormat decimalFormat = new DecimalFormat(strFormat);

                        long bgnBillNo1 = Long.parseLong(bdl.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(bdl.getEndBillNo());

                        for(long i = bgnBillNo1; i <= endBillNo1; i++){
                            BillDetail billDetail = new BillDetail();
                            billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                            billDetail.setBillNo(decimalFormat.format(i));
                            billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                            billDetail.setState("0");
                            billDetail.setEnId(billReDisDao.get(billReDis).getEnId());
                            billDetail.setMainId(mainId);
                            billDetail.setBilldistributer(billReDisDao.get(billReDis).getBilldistributer());
                            billDetailDao.insert(billDetail);
                        }

                    }
                }
            }

            return true;
        }else{
            return false;
        }
    }

    @Transactional(readOnly = false)
    public String insertReturnId(BillReDis billReDis){
        try{
            int row = billReDisDao.insert(billReDis);
            if(row > 0){
                return billReDis.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillReDis billReDis){
        int row = billReDisDao.update(billReDis);
        if(row > 0){
            String mainId = billReDis.getChrId();
            BillReDisList billReDisList = new BillReDisList();
            billReDisList.setMainId(mainId);
            billReDisListDao.delete(billReDisList);

            for(BillReDisList bpsl : billReDis.getBillReDisList()){
                bpsl.setMainId(mainId);
                bpsl.setOccurTime(billReDis.getLastVer());
                billReDisListDao.insert(bpsl);
            }

            BillDetail bd = new BillDetail();
            bd.setMainId(mainId);
            billDetailDao.delete(bd);

            if(billReDis.getIsend() == 1){
                if(billReDis.getBillReDisList() != null){
                    for(BillReDisList bdl : billReDis.getBillReDisList()){
                        String strFormat = "";
                        for(int k = 0; k < bdl.getBgnBillNo().length(); k++){
                            strFormat += "0";
                        }
                        DecimalFormat decimalFormat = new DecimalFormat(strFormat);
                        long bgnBillNo1 = Long.parseLong(bdl.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(bdl.getEndBillNo());

                        for(long i = bgnBillNo1; i <= endBillNo1; i++){
                            BillDetail billDetail = new BillDetail();
                            billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                            billDetail.setBillNo(decimalFormat.format(i));
                            billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                            billDetail.setState("0");
                            billDetail.setEnId(billReDisDao.get(billReDis).getEnId());
                            billDetail.setBilldistributer(billReDisDao.get(billReDis).getBilldistributer());
                            billDetail.setMainId(mainId);
                            billDetailDao.insert(billDetail);
                        }

                    }
                }
            }

            return true;
        }else{
            return false;
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean delete(BillReDis billReDis){
        int row = billReDisDao.delete(billReDis);
        if(row > 0){
            String mainId = billReDis.getChrId();
            BillReDisList billReDisList = new BillReDisList();
            billReDisList.setMainId(mainId);
            billReDisListDao.delete(billReDisList);

            BillDetail bd = new BillDetail();
            bd.setMainId(mainId);
            billDetailDao.delete(bd);
            return true;
        }else{
            return false;
        }
    }

    public List<BillReDis> findAllList(BillReDis billReDis){
        try{
            List<BillReDis> list = billReDisDao.findAllList(billReDis);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillReDis> findList(BillReDis billReDis){
        try{
            List<BillReDis> list = billReDisDao.findList(billReDis);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillReDis get(BillReDis billReDis){
        try{
            BillReDis bt = billReDisDao.get(billReDis);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillReDis> getBillReDisListByCondition(Map<String, Object> map){
        try{
            List<BillReDis> list = billReDisDao.getBillReDisListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillReDisListTotal(BillReDis billReDis){
        try{
            long total = billReDisDao.getBillReDisListTotal(billReDis);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public String getMaxNo(BillReDis billReDis){
        try{
            String billserialNo = billReDisDao.getMaxNo(billReDis);
            if(billserialNo == null){
                return "";
            }
            return billserialNo;
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean checkBillReDis(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillReDis billReDis = new BillReDis();
            billReDis.setIsend(1);
            billReDis.setStateCode(stateCode);
            billReDis.setChrId(chrId);
            row += billReDisDao.checkBillReDis(billReDis);

            if(billReDis.getIsend() == 1){
                BillReDisList billReDisList = new BillReDisList();
                billReDisList.setMainId(chrId);
                List<BillReDisList> billReDisListList = billReDisListDao.findAllList(billReDisList);
                if(billReDisListList != null){
                    for(BillReDisList bdl : billReDisListList){
                        String strFormat = "";
                        for(int k = 0; k < bdl.getBgnBillNo().length(); k++){
                            strFormat += "0";
                        }
                        DecimalFormat decimalFormat = new DecimalFormat(strFormat);
                        long bgnBillNo1 = Long.parseLong(bdl.getBgnBillNo());
                        long endBillNo1 = Long.parseLong(bdl.getEndBillNo());

                        for(long i = bgnBillNo1; i <= endBillNo1; i++){
                            BillDetail billDetail = new BillDetail();
                            billDetail.setUntaxBillnameId(bdl.getUntaxBillnameId());
                            billDetail.setBillNo(decimalFormat.format(i));
                            billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                            billDetail.setState("0");
                            billDetail.setEnId(billReDisDao.get(billReDis).getEnId());
                            billDetail.setBilldistributer(billReDisDao.get(billReDis).getBilldistributer());
                            billDetail.setMainId(chrId);
                            billDetailDao.insert(billDetail);
                        }

                    }
                }
            }
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }




    @Transactional(rollbackFor=Exception.class)
    public boolean returnBillReDis(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.returnStateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillReDis billReDis = new BillReDis();
            billReDis.setIsend(0);
            billReDis.setStateCode(stateCode);
            billReDis.setChrId(chrId);
            row += billReDisDao.checkBillReDis(billReDis);

            BillDetail bd = new BillDetail();
            bd.setMainId(chrId);
            billDetailDao.delete(bd);
        }
        if(row > 0){
            return true;
        }else{
            return false;
        }
    }
}