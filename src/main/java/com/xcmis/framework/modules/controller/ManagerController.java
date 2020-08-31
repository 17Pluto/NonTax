package com.xcmis.framework.modules.controller;

import com.xcmis.feefax.modules.entity.Enterprise;
import com.xcmis.feefax.modules.entity.IncomeBank;
import com.xcmis.feefax.modules.entity.ManageBranch;
import com.xcmis.feefax.modules.entity.Menu;
import com.xcmis.feefax.modules.service.*;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.utils.DateTimeUtils;
import com.xcmis.framework.common.utils.MD5;
import com.xcmis.framework.common.utils.UserUtils;
import com.xcmis.framework.jwt.JwtTokenUtil;
import com.xcmis.framework.modules.entity.*;
import com.xcmis.framework.modules.service.*;
import com.xcmis.framework.modules.vo.UsermanagerVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.xcmis.framework.common.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping(value = "/sys")
@RestController
@CrossOrigin
public class ManagerController {
    //private static Logger logger = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRuleService userRuleService;

    @Autowired
    private UserRoleRuleService userRoleRuleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private IncomeBankService incomeBankService;

    @Autowired
    private ManageBranchService manageBranchService;

    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private MenuService menuService;

    @Value("${issubaccount}")
    private boolean isSubAccount;

    @ApiOperation(value = "用户登录", notes = "登录--不进行拦截")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<String> login(String userCode, String password) {
        String realPassword = "";

        User user = new User();
        user.setUserCode(userCode);
        //user.setUserName(userName);
        user = userService.get(user);

        if (user == null) {
            return new Result<String>("用户名或密码不正确", Globals.FAILED_CODE);
        } else {
            realPassword = user.getPassword();
            if (!realPassword.equals(MD5.MD5Encode(password))) {
                return new Result<String>("用户名或密码不正确", Globals.FAILED_CODE);
            } else {
                String token = JwtTokenUtil.sign(user.getUserName(), realPassword, user.getUserId());
                return new Result<String>(token, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }
        }
    }


    @ApiOperation(value = "获取用户角色", notes = "获取用户角色-拦截")
    @RequestMapping(value = "/getRolesByUser", method = RequestMethod.POST)
    public Result<String> getRolesByUser() {
        return new Result<String>(Globals.OP_FAILURE, Globals.FAILED_CODE);
    }


    @ApiOperation(value = "获取用户角色资源", notes = "获取用户角色资源-拦截")
    @RequestMapping("/getResourceListByUserName")
    public Result<Map<String,Object>>  getResourceListByUserName(){
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String username = JwtTokenUtil.getUsername(token);
        String userId = JwtTokenUtil.getUserId(token);

        Menu menu = new Menu();
        menu.setUserId(userId);
        List<Menu> list = menuService.getMenuListByUserId(menu);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("introduction", "");
        map.put("name", username);
        map.put("roles", list);

        return new Result<Map<String,Object>>(map, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/addUserDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addUserDo(@RequestBody User user) {
        try {
            user.setSetYear(DateTimeUtils.getCurrentYear());
            user.setLastVer(DateTimeUtils.getDateTimeStr1());
            user.setPassword(MD5.MD5Encode(user.getPassword()));
            user.setRgCode(regionService.get(null).getChrCode());
            boolean b = userService.insert(user);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping("/logout")
    public Result<String> logout(HttpServletResponse response) {
        Subject lvSubject = SecurityUtils.getSubject();
        lvSubject.logout();
        return new Result<String>("success", Globals.OP_SUCCESS, 1);
    }

    @RequestMapping(value = "/editPasswordDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editPasswordDo(User user) {
        try {
            user.setPassword(MD5.MD5Encode(user.getPassword()));
            user.setUserId(UserUtils.getUserId());
            boolean b = userService.editPassword(user);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }



    @RequestMapping(value = "/editUserDo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editUserDo(@RequestBody User user) {
        try {
            user.setSetYear(DateTimeUtils.getCurrentYear());
            user.setLastVer(DateTimeUtils.getDateTimeStr1());
            if(!user.getPassword().equals("")) {
                user.setPassword(MD5.MD5Encode(user.getPassword()));
            }
            user.setRgCode(regionService.get(null).getChrCode());
            boolean b = userService.update(user);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }

    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delUser(User user) {
        try {
            boolean b = userService.delete(user);
            if(b){
                return new Result<Boolean>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
            }else{
                return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new Result<Boolean>(Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }


    @RequestMapping(value = "/getIssubaccount", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> getIssubaccount() {
        return new Result<>(isSubAccount, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }

    @RequestMapping(value = "/validUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> validUserId(String createUser) {
        String userId = UserUtils.getUserId();
        if(createUser.equals(userId)){
            return new Result<>(Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
        }else{
            return new Result<>(false, "非本记录制单人无法操作!", Globals.SUCCESS_CODE);
        }
    }

    @RequestMapping(value = "/getUserName", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUserName(User user) {
        user = userService.get(user);

        Map<String,Object> mapOut = new HashMap<>();
        mapOut.put("url", "");//实际的行数
        mapOut.put("user", user);//要以JSON格式返回

        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



    @RequestMapping(value = "/getUserByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> getUserByUserId(User user) {
        user = userService.get(user);

        Map<String, Object> mapOut = new HashMap<>();
        if(user != null) {
            UserOrg userOrg = new UserOrg();
            userOrg.setUserId(user.getUserId());
            List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);
            user.setUserOrgList(userOrgList);

            UserRule userRule = new UserRule();
            userRule.setUserId(user.getUserId());
            List<UserRule> userRuleList = userRuleService.findAllList(userRule);
            user.setUserRuleList(userRuleList);

            UserRoleRule userRoleRule = new UserRoleRule();
            userRoleRule.setUserId(user.getUserId());
            List<UserRoleRule> userRoleList = userRoleRuleService.findAllList(userRoleRule);
            user.setUserRoleRuleList(userRoleList);

            mapOut.put("url", "");//实际的行数
            mapOut.put("user", user);//要以JSON格式返回
        }else{
            mapOut.put("url", "");//实际的行数
            mapOut.put("user", "");//要以JSON格式返回
        }
        return new Result<>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);

    }


    @RequestMapping("/getUsermanager")
    @ResponseBody
    public Result<Map<String,Object>>  getUsermanager(User user){
        List<UsermanagerVO> list = new ArrayList<>();
        Map<String, String> tmpMap = new HashMap<>();

       if(user.getBelongType().equals("002")){
            List<User> userList = userService.getBelongOrgList(user);
            for(User u : userList) {
                Enterprise enterprise = new Enterprise();
                enterprise.setChrId(u.getBelongOrg());
                List<Enterprise> enterpriseList = enterpriseService.getEnterpriseListByChrId(enterprise);
                for(Enterprise e : enterpriseList){
                    if(tmpMap.containsKey(e.getChrId())){
                        continue;
                    }else{
                        tmpMap.put(e.getChrId(),e.getChrId());
                        list.add(new UsermanagerVO(e));
                    }
                }
            }
        }else if(user.getBelongType().equals("005")) {
            List<User> userList = userService.getBelongOrgList(user);
            for (User u : userList) {
                IncomeBank incomeBank = new IncomeBank();
                incomeBank.setChrId(u.getBelongOrg());

                List<IncomeBank> incomeBankList = incomeBankService.getIncomeBankListByChrId(incomeBank);
                for (IncomeBank ib : incomeBankList) {
                    if (tmpMap.containsKey(ib.getChrId())) {
                        continue;
                    } else {
                        tmpMap.put(ib.getChrId(), ib.getChrId());
                        list.add(new UsermanagerVO(ib));
                    }
                }
            }
        }else if(user.getBelongType().equals("007")) {
            List<User> userList = userService.getBelongOrgList(user);
            for (User u : userList) {
                ManageBranch manageBranch = new ManageBranch();
                manageBranch.setChrId(u.getBelongOrg());

                List<ManageBranch> incomeBankList = manageBranchService.getManageBranchListByChrId(manageBranch);
                for (ManageBranch mb : incomeBankList) {
                    if (tmpMap.containsKey(mb.getChrId())) {
                        continue;
                    } else {
                        tmpMap.put(mb.getChrId(), mb.getChrId());
                        list.add(new UsermanagerVO(mb));
                    }
                }
            }
        }

        List<User> userList1 = userService.findAllList(user);
        for(User u : userList1){
            list.add(new UsermanagerVO(u));
        }

        Collections.sort(list);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list", list);
        return new Result<Map<String,Object>>(map, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }


    @RequestMapping("/getUserOrg")
    @ResponseBody
    public Result<Map<String,Object>>  getUserOrg(User user){
        List<UsermanagerVO> list = new ArrayList<>();
        List<UsermanagerVO> selectList = new ArrayList<>();
        Map<String, String> tmpMap = new HashMap<>();

        if(user.getOrgType().equals("002")){
            Enterprise enterprise = new Enterprise();
            List<Enterprise> enterpriseList = enterpriseService.findAllList(enterprise);
            for(Enterprise e : enterpriseList){
                list.add(new UsermanagerVO(e));
            }
        }else if(user.getOrgType().equals("005")) {
            IncomeBank incomeBank = new IncomeBank();
            List<IncomeBank> incomeBankList = incomeBankService.findAllList(incomeBank);
            for (IncomeBank ib : incomeBankList) {
                list.add(new UsermanagerVO(ib));
            }
        }else if(user.getOrgType().equals("007")) {
            ManageBranch manageBranch = new ManageBranch();
            List<ManageBranch> incomeBankList = manageBranchService.findAllList(manageBranch);
            for (ManageBranch mb : incomeBankList) {
                list.add(new UsermanagerVO(mb));
            }
        }

        if(user.getUserId() != null){
            UserOrg userOrg = new UserOrg();
            userOrg.setUserId(user.getUserId());
            List<UserOrg> userOrgList = userOrgService.findAllList(userOrg);
            for (UserOrg uo : userOrgList) {
                selectList.add(new UsermanagerVO(uo));
            }
        }


        //Collections.sort(list);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list", list);
        map.put("selectList", selectList);
        return new Result<Map<String,Object>>(map, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
    }



}
