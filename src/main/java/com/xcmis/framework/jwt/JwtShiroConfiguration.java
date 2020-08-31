package com.xcmis.framework.jwt;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能
 *
 * @author
 * @see
 */

@Configuration
public class JwtShiroConfiguration {
    /**
     * 先经过token过滤器，如果检测到请求头存在 token，则用 token 去 login，接着走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        //
        Map<String, String> filterRuleMap = new LinkedHashMap<>();

        // 放行不需要权限认证的接口
        //放行Swagger接口
        filterRuleMap.put("/v2/api-docs","anon");
        filterRuleMap.put("/swagger-resources/configuration/ui","anon");
        filterRuleMap.put("/swagger-resources","anon");
        filterRuleMap.put("/swagger-resources/configuration/security","anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/webjars/**","anon");


        filterRuleMap.put("/static/**","anon");

        //这个是原型创建controller中定义的接口路径，不做拦截（安全问题后面再说）
        filterRuleMap.put("/service/**", "anon");
        filterRuleMap.put("/activiti/**","anon");

        //activiti设计
        filterRuleMap.put("/activiti-ui.html", "anon");
        filterRuleMap.put("/modeler.html", "anon");
        filterRuleMap.put("/editor-app/**", "anon");
        filterRuleMap.put("/diagram-viewer/**", "anon");
        filterRuleMap.put("/images/**", "anon");

        //放行登录接口和其他不需要权限的接口
        filterRuleMap.put("/sys/login", "anon");
        filterRuleMap.put("/sys/getUserName", "anon");
        filterRuleMap.put("/unauthorized/**", "anon");
        filterRuleMap.put("/commonplatform/**", "anon");

        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;

    }

    /**
     * 注入 securityManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(JwtRealm jwtRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(jwtRealm);

        /*
         * 关闭shiro自带的session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
