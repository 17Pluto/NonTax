package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.IncomeItem;
import com.xcmis.feefax.modules.entity.UnitItem;
import com.xcmis.feefax.modules.service.IncomeItemService;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.UnitItemService;
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

import java.util.*;

@Controller
@RequestMapping(value = "/feefax")
public class IncomeItemController {
	@Autowired
	private IncomeItemService incomeItemService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private UnitItemService unitItemService;

	@RequestMapping(value = "/validIncomeItemChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validIncomeItemChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			IncomeItem incomeItem = new IncomeItem();
			incomeItem.setChrCode(chrCode);
			incomeItem = incomeItemService.get(incomeItem);
			if(incomeItem == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (incomeItem.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(incomeItem.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/validParentIncomeItem", method = RequestMethod.GET)
	@ResponseBody
	public Result<Boolean> validParentIncomeItem(String parentId) {
		try {
			if(parentId != null) {
				if(!parentId.equals("")) {
					UnitItem unitItem = new UnitItem();
					unitItem.setItemId(parentId);
					List<UnitItem> unitItemlist = unitItemService.findAllList(unitItem);
					if (unitItemlist != null) {
						if (unitItemlist.size() > 0) {
							return new Result<>(false, "父级收费项目已被关联!", Globals.SUCCESS_CODE);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
		return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/addIncomeItemDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addIncomeItemDo(IncomeItem incomeItem) {
		try {
			incomeItem.setSetYear(DateTimeUtils.getCurrentYear());
			incomeItem.setStateCode("001");
			incomeItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
			incomeItem.setCreateUser(UserUtils.getUserId());
			incomeItem.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			incomeItem.setLatestOpUser(UserUtils.getUserId());
			incomeItem.setLastVer(DateTimeUtils.getDateTimeStr1());
			incomeItem.setRgCode(regionService.get(null).getChrCode());

			incomeItem.setInItematName(null);
			if(incomeItem.getStateCode().equals("000")){
				incomeItem.setIsEnd(1);
			}
			incomeItem.setDispCode(incomeItem.getChrCode());
			String chrId = incomeItemService.insertReturnId(incomeItem);
			if(!chrId.equals("")){
				return new Result<>(incomeItem.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editIncomeItemDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editIncomeItemDo(IncomeItem incomeItem) {
		try {
			incomeItem.setChrCode(incomeItem.getDispCode());
			incomeItem.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			incomeItem.setLatestOpUser(UserUtils.getUserId());
			incomeItem.setLastVer(DateTimeUtils.getDateTimeStr1());
			incomeItem.setRgCode(regionService.get(null).getChrCode());

			//incomeItem.setCreateDate(DateTimeUtils.getDateTimeStr1());
			boolean b = incomeItemService.update(incomeItem);
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

	@RequestMapping(value = "/delIncomeItem", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delIncomeItem(@RequestParam(value = "chrId") String chrId) {
		try {
			IncomeItem incomeItem = new IncomeItem();
			incomeItem.setChrId(chrId);

			IncomeItem ii = new IncomeItem();
			ii.setParentId(chrId);
			List<IncomeItem> incomeItemlist = incomeItemService.findAllList(ii);
			if(incomeItemlist != null){
				if(incomeItemlist.size() > 0){
					return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
				}
			}

			UnitItem unitItem = new UnitItem();
			unitItem.setItemId(chrId);
			List<UnitItem> unitItemlist = unitItemService.findAllList(unitItem);
			if(unitItemlist != null){
				if(unitItemlist.size() > 0){
					return new Result<>(false, "收费项目已被关联,无法删除!", Globals.SUCCESS_CODE);
				}
			}

			boolean b = incomeItemService.delete(incomeItem);
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


	@RequestMapping(value = "/getIncomeItemListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeItemListByCondition(String page, String rows, String sort, String order, IncomeItem incomeItem) {
		Map<String, Object> mapIn = new HashMap<>();

		long total = incomeItemService.getIncomeItemListTotal(incomeItem);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("incomeItem", incomeItem);
		List<IncomeItem> list = incomeItemService.getIncomeItemListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getIncomeItemList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIncomeItemList(IncomeItem incomeItem) {
		if(incomeItem.getCompareDate() != null) {
			if (incomeItem.getCompareDate().equals("1")) {
				incomeItem.setCompareDate(DateTimeUtils.getDateTimeStr2());
			}
		}
		List<IncomeItem> list = incomeItemService.findAllList(incomeItem);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}


	@RequestMapping(value = "/getIncomeItemByItemId", method = RequestMethod.GET)
	@ResponseBody
	public Result<IncomeItem> getIncomeItemByItemId(String itemId) {
		try {
			IncomeItem incomeItem = new IncomeItem();
			incomeItem.setChrId(itemId);
			incomeItem = incomeItemService.get(incomeItem);
			return new Result<>(incomeItem, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}


	@RequestMapping(value = "/getIncomeItemByEnIdAndBilltypeIdAndAccountId", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<IncomeItem>> getIncomeItemByEnIdAndBilltypeIdAndAccountId(UnitItem unitItem) {
		List<IncomeItem> list = new ArrayList<>();
		try {
			unitItem.setCompareDate(DateTimeUtils.getDateTimeStr2());
			List<UnitItem> unitItemList = unitItemService.getUnitItemByEnIdAndBilltypeIdAndAccountId(unitItem);
			for(UnitItem ui : unitItemList){
				IncomeItem incomeItem = new IncomeItem();
				incomeItem.setChrId(ui.getItemId());
				List<IncomeItem> incomeItemList = incomeItemService.getIncomeItemListByChrId(incomeItem);
				if(list.size() == 0){
					for(IncomeItem ii : incomeItemList){
						ii.setUnitItemId(ui.getChrId());
						list.add(ii);
					}
				}else{
					for(IncomeItem ii : incomeItemList){
						boolean repeat = false;
						for(IncomeItem iitem : list){
							if(iitem.getChrId().equals(ii.getChrId())){
								repeat = true;
								break;
							}
						}
						if(!repeat){
							ii.setUnitItemId(ui.getChrId());
							list.add(ii);
						}
					}
				}
			}
			Collections.sort(list);
			return new Result<>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>(null, Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
}
