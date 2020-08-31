package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Enterprise;
import com.xcmis.feefax.modules.service.EnterpriseService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.shiro.authc.AuthenticationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validEnterpriseChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validEnterpriseChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			Enterprise enterprise = new Enterprise();
			enterprise.setChrCode(chrCode);
			enterprise = enterpriseService.get(enterprise);
			if(enterprise == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (enterprise.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(enterprise.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/addEnterpriseDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addEnterpriseDo(Enterprise enterprise) {
		try {
			if(enterprise.getParentId().equals("allTree")){
				enterprise.setParentId("");
			}
			enterprise.setSetYear(DateTimeUtils.getCurrentYear());
			enterprise.setChrCode(enterprise.getDispCode());
			enterprise.setCreateDate(DateTimeUtils.getDateTimeStr1());
			enterprise.setCreateUser(UserUtils.getUserId());
			enterprise.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			enterprise.setLatestOpUser(UserUtils.getUserId());
			enterprise.setLastVer(DateTimeUtils.getDateTimeStr1());
			enterprise.setRgCode(regionService.get(null).getChrCode());
			String chrId = enterpriseService.insertReturnId(enterprise);
			if(!chrId.equals("")){
				return new Result<>(enterprise.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}



	@RequestMapping(value = "/editEnterpriseDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editEnterpriseDo(Enterprise enterprise) {
		try {
			enterprise.setChrCode(enterprise.getDispCode());

			boolean b = enterpriseService.update(enterprise);
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

	@RequestMapping(value = "/delEnterprise", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delBillKind(@RequestParam(value = "chrId") String chrId) {
		try {
			Enterprise enterprise = new Enterprise();
			enterprise.setChrId(chrId);


			long total = enterpriseService.getEnterpriseListTotal(enterprise);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}

			boolean b = enterpriseService.delete(enterprise);
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

	@RequestMapping(value = "/getEnterpriseList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getEnterpriseList(Enterprise enterprise) {
		//enterprise.setSetYear(2019);
		List<Enterprise> list = enterpriseService.findAllList(enterprise);
		Map<String,Object> mapOut = new HashMap<String,Object>();

		mapOut.put("url", "getEnterpriseListByCondition|addEnterpriseDo|editEnterpriseDo|delEnterprise|validEnterpriseChrCode");//实际的行数
		mapOut.put("list", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}



	@RequestMapping(value = "/getEnterpriseListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getEnterpriseListByCondition(String page, String rows, String sort, String order, Enterprise enterprise) {
		if(!enterprise.getChrCode().equals("allTree")){
			enterprise.setDispCode(enterprise.getChrCode());
		}else{
			enterprise.setChrCode("");
			enterprise.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = enterpriseService.getEnterpriseListTotal(enterprise);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("enterprise", enterprise);
		List<Enterprise> list = enterpriseService.getEnterpriseListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

}
