package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.BillName;
import com.xcmis.feefax.modules.service.BillNameService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class BillNameController {
	@Autowired
	private BillNameService billNameService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validBillNameChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validBillNameChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			BillName billName = new BillName();
			billName.setChrCode(chrCode);
			billName = billNameService.get(billName);
			if(billName == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (billName.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(billName.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addBillNameDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addBillNameDo(BillName billName) {
		try {
			/*
			if(billName.getParentId().equals("allTree")){
				billName.setParentId("");
			}
			*/
			billName.setSetYear(DateTimeUtils.getCurrentYear());
			billName.setDispCode(billName.getChrCode());
			billName.setCreateDate(DateTimeUtils.getDateTimeStr1());
			billName.setCreateUser(UserUtils.getUserId());
			billName.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			billName.setLatestOpUser(UserUtils.getUserId());
			billName.setLastVer(DateTimeUtils.getDateTimeStr1());
			billName.setRgCode(regionService.get(null).getChrCode());
			billName.setBillKind(billName.getBillKindId());
			billName.setVerifyLength(0);
			billName.setStockUpper(999999999);
			billName.setStrict(0);
			billName.setIsmanage(0);
			billName.setIsprepay(0);

			String chrId = billNameService.insertReturnId(billName);
			if(!chrId.equals("")){
				return new Result<>(billName.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editBillNameDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editBillNameDo(BillName billName) {
		try {
			billName.setDispCode(billName.getChrCode());
			billName.setBillKind(billName.getBillKindId());
			boolean b = billNameService.update(billName);
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

	@RequestMapping(value = "/delBillName", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delBillName(@RequestParam(value = "chrId") String chrId) {
		try {
			BillName billName = new BillName();
			billName.setChrId(chrId);
			boolean b = billNameService.delete(billName);
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


	@RequestMapping(value = "/getBillNameListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBillNameListByCondition(String page, String rows, String sort, String order, BillName billName) {
		if(!billName.getChrCode().equals("allTree")){
			billName.setDispCode(billName.getChrCode());
		}else{
			billName.setChrCode("");
			billName.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = billNameService.getBillNameListTotal(billName);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("billName", billName);
		List<BillName> list = billNameService.getBillNameListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getBillNameList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBillNameList(BillName billName) {
		List<BillName> list = billNameService.findAllList(billName);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getBillNameListByCondition|addBillNameDo|editBillNameDo|delBillName|validBillNameChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getBillNameListByMainId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBillNameListByMainId(BillName billName) {
		List<BillName> list = billNameService.getBillNameListByMainId(billName);
		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
