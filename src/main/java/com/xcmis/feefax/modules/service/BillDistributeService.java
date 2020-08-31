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
public class BillDistributeService {

    @Autowired
    private BillDistributeDao billDistributeDao;

    @Autowired
    private BillDistributeListDao billDistributeListDao;

    @Autowired
    private BillDetailDao billDetailDao;

    @Autowired
    private BillNameDao billNameDao;

    @Transactional(rollbackFor=Exception.class)
    public boolean insert(BillDistribute billDistribute) {


        int row = billDistributeDao.insert(billDistribute);
        if(row > 0){
            String mainId = billDistribute.getChrId();
            for(BillDistributeList bdl : billDistribute.getBillDistributeList()){
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
                bdl.setOccurTime(billDistribute.getLastVer());
                billDistributeListDao.insert(bdl);
            }

            if(billDistribute.getIsend() == 1){
                if(billDistribute.getBillDistributeList() != null){
                    for(BillDistributeList bdl : billDistribute.getBillDistributeList()){
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
                            billDetail.setEnId(billDistributeDao.get(billDistribute).getEnId());
                            billDetail.setMainId(mainId);
                            billDetail.setBilldistributer(billDistributeDao.get(billDistribute).getBilldistributer());
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
    public String insertReturnId(BillDistribute billDistribute){
        try{
            int row = billDistributeDao.insert(billDistribute);
            if(row > 0){
                return billDistribute.getChrId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean update(BillDistribute billDistribute){
        int row = billDistributeDao.update(billDistribute);
        if(row > 0){
            String mainId = billDistribute.getChrId();
            BillDistributeList billDistributeList = new BillDistributeList();
            billDistributeList.setMainId(mainId);
            billDistributeListDao.delete(billDistributeList);

            for(BillDistributeList bpsl : billDistribute.getBillDistributeList()){
                bpsl.setMainId(mainId);
                bpsl.setOccurTime(billDistribute.getLastVer());
                billDistributeListDao.insert(bpsl);
            }

            BillDetail bd = new BillDetail();
            bd.setMainId(mainId);
            billDetailDao.delete(bd);

            if(billDistribute.getIsend() == 1){
                if(billDistribute.getBillDistributeList() != null){
                    for(BillDistributeList bdl : billDistribute.getBillDistributeList()){
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
                            billDetail.setEnId(billDistributeDao.get(billDistribute).getEnId());
                            billDetail.setBilldistributer(billDistributeDao.get(billDistribute).getBilldistributer());
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
    public boolean delete(BillDistribute billDistribute){
        int row = billDistributeDao.delete(billDistribute);
        if(row > 0){
            String mainId = billDistribute.getChrId();
            BillDistributeList billDistributeList = new BillDistributeList();
            billDistributeList.setMainId(mainId);
            billDistributeListDao.delete(billDistributeList);

            BillDetail bd = new BillDetail();
            bd.setMainId(mainId);
            billDetailDao.delete(bd);
            return true;
        }else{
            return false;
        }
    }

    public List<BillDistribute> findAllList(BillDistribute billDistribute){
        try{
            List<BillDistribute> list = billDistributeDao.findAllList(billDistribute);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDistribute> findList(BillDistribute billDistribute){
        try{
            List<BillDistribute> list = billDistributeDao.findList(billDistribute);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BillDistribute get(BillDistribute billDistribute){
        try{
            BillDistribute bt = billDistributeDao.get(billDistribute);
            return bt;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<BillDistribute> getBillDistributeListByCondition(Map<String, Object> map){
        try{
            List<BillDistribute> list = billDistributeDao.getBillDistributeListByCondition(map);
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getBillDistributeListTotal(BillDistribute billDistribute){
        try{
            long total = billDistributeDao.getBillDistributeListTotal(billDistribute);
            return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public String getMaxNo(BillDistribute billDistribute){
        try{
            String billserialNo = billDistributeDao.getMaxNo(billDistribute);
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
    public boolean checkBillDistribute(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.stateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillDistribute billDistribute = new BillDistribute();
            billDistribute.setIsend(1);
            billDistribute.setStateCode(stateCode);
            billDistribute.setChrId(chrId);
            row += billDistributeDao.checkBillDistribute(billDistribute);

            if(billDistribute.getIsend() == 1){
                BillDistributeList billDistributeList = new BillDistributeList();
                billDistributeList.setMainId(chrId);
                List<BillDistributeList> billDistributeListList = billDistributeListDao.findAllList(billDistributeList);
                if(billDistributeListList != null){
                    for(BillDistributeList bdl : billDistributeListList){
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
                            billDetail.setEnId(billDistributeDao.get(billDistribute).getEnId());
                            billDetail.setBilldistributer(billDistributeDao.get(billDistribute).getBilldistributer());
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
    public boolean returnBillDistribute(String chrIds, String stateCode){
        String[] chrIdArr = chrIds.split(",");
        stateCode = StringUtils.returnStateCodeStr(stateCode);
        int row = 0;
        for(String chrId : chrIdArr){
            BillDistribute billDistribute = new BillDistribute();
            billDistribute.setIsend(0);
            billDistribute.setStateCode(stateCode);
            billDistribute.setChrId(chrId);
            row += billDistributeDao.checkBillDistribute(billDistribute);

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


    public void testFindAllList(BillDistribute billDistribute){
        List<BillDistribute> list = billDistributeDao.testFindAllList(billDistribute);
        for(BillDistribute bd : list){
            String mainId = bd.getChrId();

            String strFormat = "";

            BillName billName = new BillName();
            billName.setChrId(bd.getUntaxBillnameId());
            billName = billNameDao.get(billName);

            int billLength = billName.getBillLength();
            for(int k = 0; k < billLength; k++){
                strFormat += "0";
            }

            DecimalFormat decimalFormat = new DecimalFormat(strFormat);

            long bgnBillNo1 = Long.parseLong(bd.getBgnBillNo());
            long endBillNo1 = Long.parseLong(bd.getEndBillNo());

            for(long i = bgnBillNo1; i <= endBillNo1; i++){
                BillDetail billDetail = new BillDetail();
                billDetail.setUntaxBillnameId(bd.getUntaxBillnameId());
                billDetail.setBillNo(decimalFormat.format(i));
                billDetail.setLastVer(DateTimeUtils.getDateTimeStr1());
                billDetail.setState("0");
                billDetail.setEnId(bd.getEnId());
                billDetail.setMainId(mainId);
                billDetail.setBilldistributer(bd.getBilldistributer());
                billDetailDao.insert(billDetail);
            }

        }
    }
}