package com.xcmis.feefax.modules.service;

import com.xcmis.feefax.modules.dao.BankInfoDao;
import com.xcmis.feefax.modules.entity.BankInfo;
import com.xcmis.feefax.modules.entity.BillfundConfirm;
import com.xcmis.feefax.modules.entity.BizContent;
import com.xcmis.feefax.modules.entity.FlowConfirm;
import com.xcmis.framework.common.utils.DateTimeUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能
 *
 * @author
 * @see
 */
@Service
public class UntaxExchangeMessageService {
    @Autowired
    private RegionService regionService;

    @Autowired
    private BankInfoDao bankInfoDao;


    private String url = "http://10.64.134.41:5080";

    public boolean billSync(BizContent bizContent){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        JSONObject postData = new JSONObject();
        postData.put("zone_code", regionService.get(null).getChrCode());
        postData.put("method", "wp.bill.sync");
        postData.put("timestamp", sdf.format(date));
        postData.put("version", "1.0");

        postData.put("biz_content", bizContent);

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=GBK");
        headers.setContentType(type);

        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(postData.toString(), headers);

        try {
            BankInfo bi = new BankInfo();
            bi.setCreateType("out");
            bi.setCreateDate(DateTimeUtils.getDateTimeStr1());
            bi.setContent(postData.toString());
            bankInfoDao.insert(bi);

            String json = client.postForEntity(url, formEntity, String.class).getBody();
            bi.setCreateType("in");
            bi.setCreateDate(DateTimeUtils.getDateTimeStr1());
            bi.setContent(json);
            bankInfoDao.insert(bi);
            JSONObject jsonObject = JSONObject.fromObject(json);
            String returnCode = JSONObject.fromObject(jsonObject.getString("wp_bill_sync_response")).getString("code");
            if(!returnCode.equals("10000")){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }



    public boolean billfundConfirm(BillfundConfirm billfundConfirm, String bankindex){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        JSONObject postData = new JSONObject();
        postData.put("zone_code", regionService.get(null).getChrCode());
        postData.put("method", "wp.account.billfund.confirm");
        postData.put("timestamp", sdf.format(date));
        postData.put("bankindex", bankindex);
        postData.put("biz_content", billfundConfirm);

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=GBK");
        headers.setContentType(type);

        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(postData.toString(), headers);

        try {
            BankInfo bi = new BankInfo();
            bi.setCreateType("out");
            bi.setCreateDate(DateTimeUtils.getDateTimeStr1());
            bi.setContent(postData.toString());
            bankInfoDao.insert(bi);

            String json = client.postForEntity(url, formEntity, String.class).getBody();
            bi.setCreateType("in");
            bi.setCreateDate(DateTimeUtils.getDateTimeStr1());
            bi.setContent(json);
            bankInfoDao.insert(bi);
            JSONObject jsonObject = JSONObject.fromObject(json);
            String returnCode = JSONObject.fromObject(jsonObject.getString("wp_account_billfund_confirm_response")).getString("code");

            if(!returnCode.equals("10000")){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean flowConfirm(FlowConfirm flowConfirm, String bankindex){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        JSONObject postData = new JSONObject();
        postData.put("zone_code", regionService.get(null).getChrCode());
        postData.put("method", "wp.account.flow.confirm");
        postData.put("timestamp", sdf.format(date));
        postData.put("bankindex", bankindex);
        postData.put("biz_content", flowConfirm);

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=GBK");
        headers.setContentType(type);

        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(postData.toString(), headers);

        try {
            String json = client.postForEntity(url, formEntity, String.class).getBody();
            System.out.println(json);
            JSONObject jsonObject = JSONObject.fromObject(json);
            String returnCode = JSONObject.fromObject(jsonObject.getString("wp_account_flow_confirm_response")).getString("code");

            if(!returnCode.equals("10000")){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
