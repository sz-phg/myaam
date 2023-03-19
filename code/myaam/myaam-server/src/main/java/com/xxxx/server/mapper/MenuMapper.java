package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chengyulong
 * @since 2022-10-25
 */
@RestController
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 用户通过id查询菜单
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id) ;

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();
    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
