package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Menu;

import java.util.List;

/**
 * <p>
 * 左侧菜单栏 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据id集合查询菜单栏
     *
     * @param ids id
     * @return List<Controllers> 菜单栏列表
     */
    List<String> getColumnName(List<Integer> ids);

    /**
     * 查询所有菜单栏
     *
     * @param hidden 是否查询隐藏的菜单栏 0:不查询 1:查询
     * @return List<Menu> 菜单栏列表
     */
    List<Menu> getAllMenu(Integer hidden);

    /**
     * 根据id查询菜单栏
     *
     * @param id id
     * @return Menu 菜单栏
     */
    Menu getMenuById(Integer id);

    /**
     * 添加菜单栏
     *
     * @param menu 菜单栏
     * @return int 1:成功 0:失败
     */
    int addMenu(Menu menu);

    /**
     * 修改菜单栏
     *
     * @param menu 菜单栏
     * @return int 1:成功 0:失败
     */
    int updateMenuById(Menu menu);

    /**
     * 修改菜单栏是否隐藏
     *
     * @param id     id
     * @param hidden 是否隐藏 0:不隐藏 1:隐藏
     * @return int 1:成功 0:失败
     */
    int updateMenuHiddenById(Integer id, Byte hidden);

    /**
     * 删除菜单栏
     *
     * @param id id
     * @return int 1:成功 0:失败
     */
    int deleteMenuById(Integer id);
}
