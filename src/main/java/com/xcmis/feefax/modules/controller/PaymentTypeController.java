package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.PaymentType;
import com.xcmis.feefax.modules.service.PaymentTypeService;
import com.xcmis.framework.common.global.Globals;
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
public class PaymentTypeController {
	@Autowired
	private PaymentTypeService paymentTypeService;
	

	@RequestMapping(value = "/addPaymentTypeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addPaymentTypeDo(PaymentType paymentType) {
		try {
			boolean b = paymentTypeService.insert(paymentType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	

	@RequestMapping(value = "/editPaymentTypeDo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editPaymentTypeDo(PaymentType paymentType) {
		try {
			boolean b = paymentTypeService.update(paymentType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}

	@RequestMapping(value = "/delPaymentType", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delPaymentType(@RequestParam(value = "id") String id) {
		try {
			PaymentType paymentType = new PaymentType();
			paymentType.setId(id);
			boolean b = paymentTypeService.delete(paymentType);
			if(b){
				return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
			}else{
				return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
		}
	}
	
	
	
	@RequestMapping(value = "/getPaymentTypeListByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getPaymentTypeListByCondition(String page, String rows, String sort, String order) {
		PaymentType paymentType = new PaymentType();

		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = paymentTypeService.getPaymentTypeListTotal(paymentType);
		mapIn.put("startrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
		mapIn.put("endrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows) + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);
		List<PaymentType> list = paymentTypeService.getPaymentTypeListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/getPaymentTypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<PaymentType> getPaymentTypeList() {
		List<PaymentType> list = paymentTypeService.findAllList(null);
		return list;	
	}
}
