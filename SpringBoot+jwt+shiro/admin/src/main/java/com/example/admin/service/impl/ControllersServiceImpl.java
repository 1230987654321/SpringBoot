package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Controllers;
import com.example.admin.mapper.ControllersMapper;
import com.example.admin.service.ControllersService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 左侧菜单栏 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Service
public class ControllersServiceImpl extends ServiceImpl<ControllersMapper, Controllers> implements ControllersService {
    @Override
    public List<String> getColumnName(List<Integer> ids) {
        LambdaQueryWrapper<Controllers> wrapper = Wrappers.lambdaQuery(Controllers.class).select(Controllers::getName).in(Controllers::getId,ids);
        return this.listObjs(wrapper,Object::toString);
    }
}
