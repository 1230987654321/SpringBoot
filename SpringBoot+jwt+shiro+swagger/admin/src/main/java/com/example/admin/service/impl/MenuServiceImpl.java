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
        return this.listObjs(wrapper, Object::toString);
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
        List<Menu> controllers = menuMapper.selectList(wrapper);
        return getTree(controllers);
    }

    /**
     * 根据id查询菜单栏
     *
     * @param id id
     * @return Menu 菜单栏
     */
    @Override
    public Menu getMenuById(Integer id) {
        CheckUtil.checkIntegerNotNull(id, "参数id不能为空");
        return menuMapper.selectById(id);
    }

    /**
     * 添加菜单栏
     *
     * @param menu 菜单栏
     * @return int 影响行数
     */
    @Override
    public int addMenu(Menu menu) {
        CheckUtil.checkStringNotEmpty(menu.getPath(), "参数path不能为空");
        CheckUtil.checkStringNotEmpty(menu.getTitle(), "参数title不能为空");
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class).eq(Menu::getPath, menu.getPath());
        Menu menu1 = menuMapper.selectOne(wrapper);
        CheckUtil.checkObjectNotNull(menu1, 500, "该菜单栏已存在");
        return menuMapper.insert(menu);
    }

    /**
     * 修改菜单栏
     *
     * @param menu 菜单栏
     * @return int 影响行数
     */
    @Override
    public int updateMenu(Menu menu) {
        CheckUtil.checkIntegerNotNull(menu.getId(), "参数id不能为空");
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
     */
    @Override
    public int updateMenuHidden(Integer id, Byte hidden) {
        CheckUtil.checkIntegerNotNull(id, "参数id不能为空");
        CheckUtil.checkIntegerNotNull(Integer.valueOf(hidden), "参数hidden不能为空");
        Menu menu = menuMapper.selectById(id);
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
     */
    @Override
    public int deleteMenu(Integer id) {
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
        for (Menu controller : menus) {
            if (controller.getPid() == 0) {
                tree.add(controller);
            } else {
                Menu parent = map.get(controller.getPid());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(controller);
                }
            }
        }
        return tree;
    }
}
