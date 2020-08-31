package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Ienusermanag;
import com.xcmis.feefax.modules.service.IenusermanagService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class IenusermanagController {
	@Autowired
	private IenusermanagService ienusermanagService;

	@RequestMapping(value = "/getAllIenusermanagList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getAllIenusermanagList(String ienIds) {
		String[] ienIdsArr = ienIds.split(",");
		List<String> ienIdsList = new ArrayList<>();
		for(String ienId : ienIdsArr){
			ienIdsList.add(ienId);
		}

		Ienusermanag ienusermanag = new Ienusermanag();
		ienusermanag.setIenIds(ienIdsList);
		List<Ienusermanag> list = ienusermanagService.findList(ienusermanag);

		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getUserId().equals(list.get(i).getUserId())) {
					list.remove(j);
				}
			}
		}

		Map<String,Object> mapOut = new HashMap<>();
		mapOut.put("url", "");//实际的行数
		mapOut.put("list", list);//要以JSON格式返回

		return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	
	@RequestMapping(value = "/getIenusermanagList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getIenusermanagList(Ienusermanag ienusermanag) {
		List<Ienusermanag> list = ienusermanagService.findAllList(ienusermanag);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	@RequestMapping(value = "/addIenusermanagDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addIenusermanagDo(@RequestBody String list) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(list);
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("list"));
			List<Ienusermanag> elementList = JSONArray.toList(jsonArray, new Ienusermanag(), new JsonConfig());

			String ienId = jsonObject.getString("ienId");


			boolean b = ienusermanagService.insert(elementList, ienId);
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

	@RequestMapping(value = "/delIenusermanagDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delIenusermanagDo(@RequestBody String list) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(list);
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("list"));
			List<Ienusermanag> elementList = JSONArray.toList(jsonArray, new Ienusermanag(), new JsonConfig());
			boolean b = ienusermanagService.delete(elementList);
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
