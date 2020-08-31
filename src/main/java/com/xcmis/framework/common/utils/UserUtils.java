package com.xcmis.framework.common.utils;

import com.xcmis.framework.jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtils {
	public static String getUserId(){
		Subject subject = SecurityUtils.getSubject();
		String token = (String)subject.getPrincipal();
		String userId = JwtTokenUtil.getUserId(token);
		return userId;
	}

	public static String getUserName(){
		Subject subject = SecurityUtils.getSubject();
		String token = (String)subject.getPrincipal();
		String userName = JwtTokenUtil.getUsername(token);
		return userName;
	}
}