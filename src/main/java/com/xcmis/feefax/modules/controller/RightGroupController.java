package com.xcmis.feefax.modules.controller;

import com.xcmis.feefax.modules.entity.RightGroup;
import com.xcmis.feefax.modules.service.RightGroupService;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feefax")
public class RightGroupController {
    /*
    @RequestMapping(value = "/getRightGroupList", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getRightGroupList() {

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }
     */


}
