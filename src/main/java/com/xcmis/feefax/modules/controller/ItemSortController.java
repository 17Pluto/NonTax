package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.ItemSort;
import com.xcmis.feefax.modules.service.RegionService;
import com.xcmis.feefax.modules.service.ItemSortService;
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
public class ItemSortController {
	@Autowired
	private ItemSortService itemSortService;

	@Autowired
	private RegionService regionService;


	@RequestMapping(value = "/validItemSortChrCode", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> validItemSortChrCode(String chrCode) {
		try {
			if(chrCode.equals("")){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}
			ItemSort itemSort = new ItemSort();
			itemSort.setChrCode(chrCode);
			itemSort = itemSortService.get(itemSort);
			if(itemSort == null){
				return new Result<>("",Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else {
				if (itemSort.getChrId().equals("")) {
					return new Result<>("", Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				} else {
					return new Result<>(itemSort.getChrId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
				}
			}
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			return new Result<>("",Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	

	@RequestMapping(value = "/addItemSortDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addItemSortDo(ItemSort itemSort) {
		try {
			if(itemSort.getParentId().equals("allTree")){
				itemSort.setParentId("");
			}
			itemSort.setSetYear(DateTimeUtils.getCurrentYear());
			itemSort.setChrCode(itemSort.getDispCode());
			itemSort.setCreateDate(DateTimeUtils.getDateTimeStr1());
			itemSort.setCreateUser(UserUtils.getUserId());
			itemSort.setLatestOpDate(DateTimeUtils.getDateTimeStr1());
			itemSort.setLatestOpUser(UserUtils.getUserId());
			itemSort.setLastVer(DateTimeUtils.getDateTimeStr1());
			itemSort.setRgCode(regionService.get(null).getChrCode());
			String chrId = itemSortService.insertReturnId(itemSort);
			if(!chrId.equals("")){
				return new Result<>(itemSort.getChrCode() + "|" + chrId, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editItemSortDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editItemSortDo(ItemSort itemSort) {
		try {
			itemSort.setChrCode(itemSort.getDispCode());

			boolean b = itemSortService.update(itemSort);
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

	@RequestMapping(value = "/delItemSort", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delItemSort(@RequestParam(value = "chrId") String chrId) {
		try {
			ItemSort itemSort = new ItemSort();
			itemSort.setChrId(chrId);

			long total = itemSortService.getItemSortListTotal(itemSort);
			if(total > 1){
				return new Result<>(false, Globals.DEL_TREE_FAILURE, Globals.SUCCESS_CODE);
			}
			boolean b = itemSortService.delete(itemSort);
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


	@RequestMapping(value = "/getItemSortListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getItemSortListByCondition(String page, String rows, String sort, String order, ItemSort itemSort) {
		if(!itemSort.getChrCode().equals("allTree")){
			itemSort.setDispCode(itemSort.getChrCode());
		}else{
			itemSort.setChrCode("");
			itemSort.setChrId("");
		}
		Map<String, Object> mapIn = new HashMap<>();

		long total = itemSortService.getItemSortListTotal(itemSort);
		int startrow = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		mapIn.put("startrow", startrow);
		mapIn.put("endrow", startrow + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		mapIn.put("itemSort", itemSort);
		List<ItemSort> list = itemSortService.getItemSortListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getItemSortList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getItemSortList(ItemSort itemSort) {
		List<ItemSort> list = itemSortService.findAllList(itemSort);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "getItemSortListByCondition|addItemSortDo|editItemSortDo|delItemSort|validItemSortChrCode");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
