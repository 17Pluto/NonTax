package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Rule;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.RuleService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class RuleController {
	@Autowired
	private RuleService ruleService;

	@Autowired
	private RegionService regionService;

	//@Autowired
	//private RightGroupDao rightGroupDao;

	
	@RequestMapping(value = "/getRuleList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRuleList(Rule rule) {
		List<Rule> list = ruleService.findAllList(rule);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getRuleByRuleId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRuleByRuleId(Rule rule) {
		rule = ruleService.get(rule);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("rule", rule);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getRuleViewByRuleId", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getRuleViewByRuleId(Rule rule) {
		rule = ruleService.get(rule);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("rule", rule);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addRuleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addRuleDo(@RequestBody Rule rule) {
		try {
			rule.setLastVer(DateTimeUtils.getDateTimeStr1());
			rule.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
			rule.setRgCode(regionService.get(null).getChrCode());
			rule.setEnabled(1);
			if(rule.getRightGroup().getRightGroupDetailList().size() > 0){
				rule.setRightType(1);
			}else{
				rule.setRightType(0);
			}
			boolean b = ruleService.insert(rule);
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

	@RequestMapping(value = "/editRuleDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editRuleDo(@RequestBody Rule rule) {
		try {
            rule.setLastVer(DateTimeUtils.getDateTimeStr1());
            rule.setSetYear(Integer.parseInt(DateTimeUtils.getCurrentYear()));
            rule.setRgCode(regionService.get(null).getChrCode());
            rule.setEnabled(1);
			boolean b = ruleService.update(rule);

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

	@RequestMapping(value = "/delRule", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delRule(@RequestParam(value = "ruleId") String ruleId) {
		try {
			Rule rule = new Rule();
			rule.setRuleId(Long.parseLong(ruleId));
			boolean b = ruleService.delete(rule);
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
}
