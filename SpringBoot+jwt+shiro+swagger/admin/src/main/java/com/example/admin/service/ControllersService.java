package com.example.admin.service;

import com.example.admin.entity.Controllers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 左侧菜单栏 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
public interface ControllersService extends IService<Controllers> {
    /* 查询权限名称 */
    List<String> getColumnName(List<Integer> ids) ;
}
