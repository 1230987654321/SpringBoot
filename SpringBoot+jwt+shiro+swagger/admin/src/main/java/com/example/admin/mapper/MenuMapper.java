package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 左侧菜单栏 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
