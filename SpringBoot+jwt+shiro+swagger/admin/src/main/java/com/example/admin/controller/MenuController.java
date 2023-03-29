package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 左侧菜单栏 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
@ApiOperation(value = "菜单栏", tags = "菜单栏")
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
    @ApiOperation(value = "查询所有菜单栏", notes = "查询所有菜单栏")
    @GetMapping("/getAllMenu")
    public List<Menu> getAllMenu(Integer hidden) {
        return menuService.getAllMenu(hidden);
    }

    /**
     * 根据id查询菜单栏
     *
     * @param id id
     * @return Menu 菜单栏
     */
    @ApiOperation(value = "根据id查询菜单栏", notes = "根据id查询菜单栏")
    @GetMapping("/getMenuById")
    public Menu getMenuById(Integer id) {
        return menuService.getMenuById(id);
    }

    /**
     * 添加菜单栏
     */
    @ApiOperation(value = "添加菜单栏", notes = "添加菜单栏")
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
    @ApiOperation(value = "修改菜单栏", notes = "修改菜单栏")
    @PutMapping("/updateMenu")
    public Integer updateMenu(Menu menu) {
        return menuService.updateMenu(menu);
    }

    /**
     * 修改菜单栏是否隐藏
     *
     * @param id     id
     * @param hidden 是否隐藏 0:不隐藏 1:隐藏
     * @return int 1:成功 0:失败
     */
    @ApiOperation(value = "修改菜单栏是否隐藏", notes = "修改菜单栏是否隐藏")
    @PutMapping("/updateMenuHidden")
    public Integer updateMenuHidden(Integer id, Byte hidden) {
        return menuService.updateMenuHidden(id, hidden);
    }

    /**
     * 删除菜单栏
     *
     * @param id id
     * @return int 1:成功 0:失败
     */
    @ApiOperation(value = "删除菜单栏", notes = "删除菜单栏")
    @DeleteMapping("/deleteMenu")
    public Integer deleteMenu(Integer id) {
        return menuService.deleteMenu(id);
    }
}
