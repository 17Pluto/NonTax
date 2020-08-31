package com.xcmis.framework.jwt;

import com.xcmis.framework.modules.dto.LoginInfo;
import com.xcmis.framework.modules.entity.User;
import com.xcmis.framework.modules.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能
 *
 * @author
 * @see
 */
@Component
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 使用JWT替代原生Token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JwtTokenUtil.getUsername(principalCollection.toString());
        //MemberDTO member = shiroAuthService.getPrincipal(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //List<String> userPermissions = shiroAuthService.getPermissions(member.getId());
        List<String> userPermissions = new ArrayList<String>();
        // 基于Permission的权限信息
        info.addStringPermissions(userPermissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String userId = JwtTokenUtil.getUserId(token);
        User u = new User();
        u.setUserId(userId);
        User user = userService.get(u);
        // 用户不会空
        if (user != null) {
            // 判断是否禁用
            //if (member.getStatus().equals(MemberStatus.disableStatus)) {
                //throw new DisabledAccountException("901");
            //}
            // 密码验证
            if (!JwtTokenUtil.verify(token, u.getUserName(), user.getPassword(), user.getUserId())) {
                throw new UnknownAccountException("900");
            }

            return new SimpleAuthenticationInfo(token, token, "realm");
        } else {
            throw new UnknownAccountException("900");
        }
    }
}
