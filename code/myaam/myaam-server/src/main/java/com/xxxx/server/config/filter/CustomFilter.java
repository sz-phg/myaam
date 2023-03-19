package com.xxxx.server.config.filter;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析出请求所需角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation)o).getRequestUrl();
        System.out.println("requestUrl = " + requestUrl + "/");
        //获取菜单
        List<Menu> menus = menuService.getMenusWithRole();
        //System.out.println("menus = " + menus);
        String[] str = new String[0];

        for (Menu menu : menus) {
            if(menu.getUrl().equals(requestUrl)){
                str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                break;
            }
            //判断请求的url与菜单角色是否匹配
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                continue;
            }
        }
        if(str.length != 0){
            if("/system/cfg/menu".equals(requestUrl)){
                str = Arrays.copyOf(str, str.length + 1);
                str[str.length-1]="ROLE_LOGIN";

            }
            System.out.println(str);
            return SecurityConfig.createList(str);
        }
        //没匹配的url默认为登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}