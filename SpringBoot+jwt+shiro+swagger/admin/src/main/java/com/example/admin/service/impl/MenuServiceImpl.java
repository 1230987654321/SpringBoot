package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Menu;
import com.example.admin.mapper.MenuMapper;
import com.example.admin.service.MenuService;
import com.example.admin.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 左侧菜单栏 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**
     * 根据id集合查询菜单栏
     *
     * @param ids id
     * @return List<Menu> 菜单栏列表
     */
    @Override
    public List<String> getColumnName(List<Integer> ids) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class).select(Menu::getName).in(Menu::getId, ids);
        List<Menu> menuList = menuMapper.selectList(wrapper);
        // 判断菜单栏是否存在
        CheckUtil.checkListNotNull(menuList, 404, "菜单栏不存在");
        // 使用stream流将菜单栏名称取出
        return menuList.stream().map(Menu::getName).collect(Collectors.toList());
    }

    /**
     * 查询所有菜单栏并将其转换为树形结构
     */
    @Override
    public List<Menu> getAllMenu(Integer hidden) {
        CheckUtil.checkIntegerNotNull(hidden, "参数hidden不能为空");
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class);
        // 判断是否查询隐藏的菜单栏 0:不查询 1:查询
        if (hidden == 1) {
            wrapper.eq(Menu::getHidden, 1);
        }
        wrapper.orderByAsc(Menu::getSort).orderByDesc(Menu::getCreatedAt);
        List<Menu> menus = menuMapper.selectList(wrapper);
        // 判断菜单栏是否存在
        CheckUtil.checkListNotNull(menus, 404, "菜单栏不存在");
        // 将菜单栏转换为树形结构
        return getTree(menus);
    }

    /**
     * 根据id查询菜单栏
     *
     * @param id id
     * @return Menu 菜单栏
     */
    @Override
    public Menu getMenuById(Integer id) {
        // 参数校验
        CheckUtil.checkIntegerNotNull(id, "参数id不能为空");
        Menu menu = menuMapper.selectById(id);
        // 判断菜单栏是否存在
        CheckUtil.checkObjectNotNull(menu, 404, "该菜单栏不存在");
        return menu;
    }

    /**
     * 添加菜单栏
     *
     * @param menu 菜单栏
     * @return int 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public int addMenu(Menu menu) {
        // 参数校验
        CheckUtil.checkStringNotEmpty(menu.getPath(), "参数path不能为空");
        CheckUtil.checkStringNotEmpty(menu.getTitle(), "参数title不能为空");
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class).eq(Menu::getPath, menu.getPath());
        Menu menuInfo = menuMapper.selectOne(wrapper);
        // 判断菜单栏是否存在 存在则抛出异常 409:冲突
        CheckUtil.checkObjectNotNull(menuInfo, 409, "该菜单栏已存在");
        try {
            return menuMapper.insert(menu);
        } catch (Exception e) {
            log.error("创建菜单失败 =======>", e);
            throw new ServiceException(500, "创建菜单失败");
        }
    }

    /**
     * 修改菜单栏
     *
     * @param menu 菜单栏
     * @return int 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public int updateMenu(Menu menu) {
        // 参数校验
        CheckUtil.checkIntegerNotNull(menu.getId(), "参数id不能为空");
        CheckUtil.checkStringNotEmpty(menu.getPath(), "参数path不能为空");
        CheckUtil.checkStringNotEmpty(menu.getTitle(), "参数title不能为空");
        try {
            return menuMapper.updateById(menu);
        } catch (Exception e) {
            log.error("修改菜单栏失败 =======>", e);
            throw new ServiceException(500, "更新失败");
        }
    }

    /**
     * 修改菜单栏是否隐藏
     *
     * @param id id
     * @return int 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public int updateMenuHidden(Integer id, Byte hidden) {
        // 参数校验
        CheckUtil.checkIntegerNotNull(id, "参数id不能为空");
        CheckUtil.checkIntegerNotNull(Integer.valueOf(hidden), "参数hidden不能为空");
        Menu menu = menuMapper.selectById(id);
        // 判断菜单栏是否存在
        CheckUtil.checkObjectNotNull(menu, 404, "菜单栏不存在");
        menu.setHidden(hidden);
        try {
            return menuMapper.updateById(menu);
        } catch (Exception e) {
            log.error("修改菜单栏失败 =======>", e);
            throw new ServiceException(500, "修改失败");
        }
    }

    /**
     * 删除菜单栏
     *
     * @param id id
     * @return int 影响行数
     * @throws ServiceException 业务异常
     */
    @Override
    public int deleteMenu(Integer id) {
        // 参数校验
        CheckUtil.checkIntegerNotNull(id, "参数id不能为空");
        try {
            return menuMapper.deleteById(id);
        } catch (Exception e) {
            log.error("删除菜单栏 =======>", e);
            throw new ServiceException(500, "删除失败");
        }
    }

    /**
     * 将菜单栏转换为树形结构
     *
     * @param menus 菜单栏列表
     * @return List<Menu> 菜单栏列表
     */
    private List<Menu> getTree(List<Menu> menus) {
        List<Menu> tree = new ArrayList<>();
        Map<Integer, Menu> map = new HashMap<>();
        for (Menu menu : menus) {
            map.put(menu.getId(), menu);
        }
        for (Menu menu : menus) {
            if (menu.getPid() == 0) {
                tree.add(menu);
            } else {
                Menu parent = map.get(menu.getPid());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(menu);
                } else {
                    // 菜单栏转换为树形失败
                    throw new ServiceException(500, "菜单栏转换为树形失败");
                }
            }
        }
        return tree;
    }
}
