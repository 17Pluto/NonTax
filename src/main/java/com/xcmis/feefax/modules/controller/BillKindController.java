package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.BillKind;
import com.xcmis.feefax.modules.service.BillKindService;
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
public class BillKindController {
	@Autowired
	private BillKindService billKindService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validBillKindChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validBillKindChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			BillKind billKind = new BillKind();
			billKind.setChrCode(chrCode);
			billKind = billKindService.getBillKind(billKind);
			if(billKind == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (billKind.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(billKind.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addBillKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addBillKindDo(BillKind billKind) {
		try {
			if(billKind.getParentId().equals("allTree")){
				billKind.setParentId("");
			}
			billKind.setSetYear(DateTimeUtils.getCurrentYear());
			billKind.setChrCode(billKind.getDispCode());
			billKind.setCreateDate(DateTimeUtils.getDateTimeStr1());
			billKind.setCreateUser(UserUtils.getUserId());
			billKind.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			billKind.setLatestOpUser(UserUtils.getUserId());
			billKind.setLastVer(DateTimeUtils.getDateTimeStr1());
			billKind.setRgCode(regionService.get(null).getChrCode());
			String chrId = billKindService.insertReturnId(billKind);
			if(!chrId.equals("")){
				return new Result<>(billKind.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editBillKindDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editBillKindDo(BillKind billKind) {
		try {
			billKind.setChrCode(billKind.getDispCode());

			boolean b = billKindService.update(billKind);
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

	@RequestMapping(value = "/delBillKind", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delBillKind(@RequestParam(value = "chrId") String chrId) {
		try {
			BillKind billKind = new BillKind();
			billKind.setChrId(chrId);

			long total = billKindService.getBillKindListTotal(billKind);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = billKindService.delete(billKind);
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


	@RequestMapping(value = "/getBillKindListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBillKindListByCondition(String page, String rows, String sort, String order, BillKind billKind) {
		if(!billKind.getChrCode().equals("allTree")){
			billKind.setDispCode(billKind.getChrCode());
		}else{
			billKind.setChrCode("");
			billKind.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = billKindService.getBillKindListTotal(billKind);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("billKind", billKind);
		List<BillKind> list = billKindService.getBillKindListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getBillKindList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getBillKindList(BillKind billKind) {
		List<BillKind> list = billKindService.findAllList(billKind);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getBillKindListByCondition|addBillKindDo|editBillKindDo|delBillKind|validBillKindChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
