package com.xcmis.framework.modules.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.xcmis.framework.modules.entity.BaseClass;
import com.xcmis.framework.modules.entity.BaseCode;

import com.xcmis.framework.modules.service.SystemService;


@Controller
@RequestMapping(value = "/sys")
public class DictionaryController {
	@Autowired
	private SystemService systemService;


	@RequestMapping(value = "/getBaseCodeListByBaseClass")
	@ResponseBody
	public Result<List<BaseCode>> getBaseCodeListByBaseClass(@RequestParam(value ="baseClassCode") String baseClassCode) {
		List<BaseCode> list = systemService.getBaseCodeListByBaseClass(baseClassCode);
		return new Result<List<BaseCode>>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}

	
	
	@RequestMapping(value = "/findBaseClassList")
	@ResponseBody
	public Result<List<BaseClass>> findBaseClassList(){
		List<BaseClass> list = systemService.findBaseClassList(null);
		return new Result<List<BaseClass>>(list, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/editBaseCode", method = RequestMethod.GET)
	public ModelAndView editBaseCode(@RequestParam(value = "id") String id) {
		ModelAndView mav = new ModelAndView(); 
		BaseCode baseCode = systemService.getBaseCodeById(id);
		mav.setViewName("/sys/basecode_edit");  
	    mav.addObject("baseCode", baseCode); 
		return mav;
	}
	
	@RequestMapping(value = "/addBaseCodeDo", method = RequestMethod.POST)
	@ResponseBody
	public String addBaseCodeDo(BaseCode baseCode) {
		try {
			baseCode.setId(String.valueOf(System.currentTimeMillis()));
			boolean b = systemService.insertBaseCode(baseCode);
			if (b) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "/editBaseCodeDo", method = RequestMethod.POST)
	@ResponseBody
	public String editBaseCodeDo(BaseCode baseCode) {
		try {

			boolean b = systemService.updateBaseCode(baseCode);
			if (b) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/delBaseCode", method = RequestMethod.POST)
	@ResponseBody
	public String delBaseCode(@RequestParam(value ="id") String id) {
		try {
			BaseCode baseCode = new BaseCode();
			baseCode.setId(id);
			boolean success = systemService.deleteBaseCode(baseCode);
			if(success){
				return "success";
			}else{
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "/delBaseCodes", method = RequestMethod.POST)
	@ResponseBody
	public String delBaseCodes(@RequestParam(value = "ids") String ids) {
		try {
			String[] id_array = ids.split(",");
			for(int i = 0; i < id_array.length; i++){
				BaseCode baseCode = new BaseCode();
				baseCode.setId(id_array[i]);
				systemService.deleteBaseCode(baseCode);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/getBaseCodeList", method = RequestMethod.POST)
	@ResponseBody
	public String getBaseCodeList(@RequestParam String aoData) {
		BaseCode baseCode = new BaseCode();
	    JSONArray jsonarray = JSONArray.fromObject(aoData);
	    String sEcho = "";
	    String sSortDir_0 = "";
	    String iSortCol_name = "";
	    
	    int iDisplayStart = 0; // 起始索引
	    int iDisplayLength = 0; // 每页显示的行数
	    
	    int iSortCol_0 = -1;
	 
	    for (int i = 0; i < jsonarray.size(); i++) {
	        JSONObject obj = (JSONObject) jsonarray.get(i);
	        if (obj.get("name").equals("sEcho")){
	            sEcho = obj.get("value").toString();
	        }
	        if (obj.get("name").equals("iDisplayStart")){
	            iDisplayStart = obj.getInt("value");
	        }
	        if (obj.get("name").equals("iDisplayLength")){
	            iDisplayLength = obj.getInt("value");
	        }
	        
	        if (obj.get("name").equals("iSortCol_0")){
	        	iSortCol_0 = obj.getInt("value");
	        }
	        
	        if (obj.get("name").equals("sSortDir_0")){
	        	sSortDir_0 = obj.get("value").toString();
	        }      
	    }
	    
	    for (int i = 0; i < jsonarray.size(); i++) {
	        JSONObject obj = (JSONObject) jsonarray.get(i);
	        
	        if (obj.get("name").equals("mDataProp_" + iSortCol_0)){
	        	iSortCol_name = obj.get("value").toString();
	        } 
	    }
		
		String sort = iSortCol_name;
		String order = sSortDir_0;
		
		
		Map<String, Object> mapIn = new HashMap<String, Object>();
	

		long total = systemService.getBaseCodeListTotal(baseCode);
		mapIn.put("startrow", iDisplayStart);
		mapIn.put("endrow", iDisplayStart + iDisplayLength);
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		List<BaseCode> list = systemService.getBaseCodeListByCondition(mapIn);
		
	    JSONObject getObj = new JSONObject();
	    getObj.put("sEcho", sEcho);
	    getObj.put("iTotalRecords", total);//实际的行数
	    getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
	     
	    getObj.put("aaData", list);//要以JSON格式返回
		return getObj.toString();
	}
}
