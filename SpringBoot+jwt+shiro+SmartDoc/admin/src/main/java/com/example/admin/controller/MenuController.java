package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 左侧菜单栏
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询所有菜单栏
     *
     * @param hidden 是否查询隐藏的菜单栏 0:不查询 1:查询
     * @return List<Menu> 菜单栏列表
     */
    @GetMapping("/getAllMenu")
    public List<Menu> getAllMenu(@RequestParam(name = "hidden") Integer hidden) {
        return menuService.getAllMenu(hidden);
    }

    /**
     * 查询所有菜单栏
     *
     * @param hidden 是否查询隐藏的菜单栏 0:不查询 1:查询
     * @return List<Menu> 菜单栏列表
     */
    @GetMapping("/getAllMenuList")
    public List<Menu> getAllMenuList(@RequestParam(name = "hidden") Integer hidden) {
        return menuService.getAllMenuList(hidden);
    }

    /**
     * 根据id查询菜单栏
     *
     * @param id id
     * @return Menu 菜单栏
     */
    @GetMapping("/getMenuById")
    public Menu getMenuById(@RequestParam(name = "id") Integer id) {
        return menuService.getMenuById(id);
    }

    /**
     * 添加菜单栏
     */
    @PostMapping("/addMenu")
    public Integer addMenu(Menu menu) {
        return menuService.addMenu(menu);
    }

    /**
     * 修改菜单栏
     *
     * @param menu 菜单栏
     * @return int 1:成功 0:失败
     */
    @PutMapping("/updateMenuById")
    public Integer updateMenuById(Menu menu) {
        return menuService.updateMenuById(menu);
    }

    /**
     * 修改菜单栏是否隐藏
     *
     * @param id     id
     * @param hidden 是否隐藏 0:不隐藏 1:隐藏
     * @return int 1:成功 0:失败
     */
    @PutMapping("/updateMenuHiddenById")
    public Integer updateMenuHiddenById(@RequestParam(name = "id") Integer id, @RequestParam(name = "hidden") Byte hidden) {
        return menuService.updateMenuHiddenById(id, hidden);
    }

    /**
     * 删除菜单栏
     *
     * @param id id
     * @return int 1:成功 0:失败
     */
    @DeleteMapping("/deleteMenuById")
    public Integer deleteMenuById(@RequestParam(name = "id") Integer id) {
        return menuService.deleteMenuById(id);
    }
}
