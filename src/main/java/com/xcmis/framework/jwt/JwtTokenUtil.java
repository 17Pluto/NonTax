package com.xcmis.framework.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 
 */
public class JwtTokenUtil{
	// 过期时间
	private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

	/**
	 * 校验token是否正确
	 *
	 * @param token    密钥
	 * @param username 登录名
	 * @param password 密码
	 * @return
	 */
	public static boolean verify(String token, String username, String password, String userid) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(password);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).withClaim("userid", userid).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取登录名
	 *
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return "";
		}
	}

	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userid").asString();
		} catch (JWTDecodeException e) {
			return "";
		}
	}

	/**
	 * 生成签名
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public static String sign(String username, String password, String userid) {
		try {
			// 指定过期时间
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			//这里用password做SECRET，也可以在服务端自动生成一个
			Algorithm algorithm = Algorithm.HMAC256(password);
			return JWT.create()
					.withClaim("username", username)
					.withClaim("userid", userid)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
