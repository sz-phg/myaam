package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author chengyulong
 * @since 2022-10-25
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 用户通过id查询菜单
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {

        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer roleId = admin.getRoles().get(0).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        System.out.println(admin.getRoles());
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + roleId);
//        System.out.println("获取menu_"+roleId);
        //如果menus为空去数据库获取
//        System.out.println(menus);

        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(admin.getId());
            //将数据设置到redis中
            valueOperations.set("menu_"+roleId,menus);
        }
        return menus;
    }
    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
