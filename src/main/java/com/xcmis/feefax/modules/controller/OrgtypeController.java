package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.Orgtype;
import com.xcmis.feefax.modules.service.OrgtypeService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class OrgtypeController {
	@Autowired
	private OrgtypeService orgtypeService;
	
	@RequestMapping(value = "/getOrgtypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getOrgtypeList(Orgtype orgtype) {
		List<Orgtype> list = orgtypeService.findAllList(orgtype);
        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("list", list);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
}
